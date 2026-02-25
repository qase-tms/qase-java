# Fix: Parallel execution result loss

## Context

Пользователи сообщают о потере результатов при параллельном запуске тестов. Анализ 3 лог-файлов выявил:

1. **Log (3)** (v4.1.30, batch=5): 82 результата добавлено, 80 загружено, **2 потеряно** — буфер не был flushed перед завершением JVM.
2. **Log from Qaseio** (v4.1.33, batch=5): 296 результатов, 110 загружено, **186 потеряно (63%)** — HTTP 404 "Run not found" → безвозвратный fallback.
3. **log (6)(1)** (v4.1.33, batch=1): 241 результат, 195 загружено, **46 потеряно** — SocketTimeoutException → безвозвратный fallback.

Корневые причины в коде:
- API-клиенты не имеют retry-логики — один сбой сразу пробрасывает exception
- `CoreReporter.executeWithFallback()` **навсегда** переключается на fallback при любой ошибке
- `TestopsReporter.completeTestRun()` не делает flush буфера перед завершением
- Флаг `complete: false` не проверяется — run всегда закрывается через API

## Changes

### 1. Создать `RetryHelper` утилиту для retry всех API-вызовов

**Новый файл:** `qase-java-commons/src/main/java/io/qase/commons/utils/RetryHelper.java`

Общая утилита для retry с exponential backoff. Используется обоими API-клиентами:
- Макс. 3 retry с задержками: 1с, 3с, 9с (backoff multiplier = 3)
- Retry при **транзиентных** ошибках:
  - `code == 0` — timeout/network error (SocketTimeoutException, ConnectException)
  - `code >= 500` — server errors (500, 502, 503, 504)
  - `code == 429` — rate limit
- **Не** retry при `4xx` (кроме 429) — клиентские ошибки (404, 401, 403)
- Работает с обеими ApiException (v1 и v2) через `extractHttpCode()`
- Логирует retry-попытки и успех после retry

```java
public class RetryHelper {
    static final int MAX_RETRIES = 3;
    static final int BASE_DELAY_MS = 1000;
    static final int BACKOFF_MULTIPLIER = 3;

    @FunctionalInterface
    public interface ThrowingSupplier<T> {
        T get() throws Exception;
    }

    @FunctionalInterface
    public interface ThrowingRunnable {
        void run() throws Exception;
    }

    public static <T> T retry(ThrowingSupplier<T> action, String actionName) throws Exception { ... }

    public static void retry(ThrowingRunnable action, String actionName) throws Exception { ... }

    static boolean isRetryable(Exception e) {
        int code = extractHttpCode(e);
        return code == 0 || code == 429 || code >= 500;
    }

    private static int extractHttpCode(Exception e) {
        if (e instanceof io.qase.client.v1.ApiException) {
            return ((io.qase.client.v1.ApiException) e).getCode();
        }
        if (e instanceof io.qase.client.v2.ApiException) {
            return ((io.qase.client.v2.ApiException) e).getCode();
        }
        return 0; // Network-level errors → retryable
    }
}
```

### 2. Добавить retry во все методы `ApiClientV1`

**Файл:** `qase-java-commons/src/main/java/io/qase/commons/client/ApiClientV1.java`

Обернуть API-вызовы в `RetryHelper.retry()`:
- `createTestRun()` — retry создания рана
- `completeTestRun()` — retry завершения рана
- `updateExternalIssue()` — retry обновления external issue
- `enablePublicReport()` — retry включения public report
- `getTestCaseIdsForExecution()` — retry получения ID кейсов
- `uploadAttachments()` — retry каждого batch-а аттачментов

Паттерн использования:
```java
public Long createTestRun() throws QaseException {
    // ... model setup ...
    try {
        return RetryHelper.retry(() ->
            Objects.requireNonNull(
                new RunsApi(client).createRun(project, model).getResult()
            ).getId(),
            "create test run"
        );
    } catch (Exception e) {
        throw wrapException("create test run", e);
    }
}

// Общий метод для оборачивания исключений
private QaseException wrapException(String action, Exception e) {
    if (e instanceof ApiException) {
        ApiException ae = (ApiException) e;
        return new QaseException(String.format("Failed to %s: code=%d, message=%s, body=%s",
            action, ae.getCode(), ae.getMessage(), ae.getResponseBody()), e);
    }
    return new QaseException("Failed to " + action + ": " + e.getMessage(), e);
}
```

### 3. Добавить retry в `ApiClientV2.uploadResults()`

**Файл:** `qase-java-commons/src/main/java/io/qase/commons/client/ApiClientV2.java`

Обернуть `uploadResults()` в `RetryHelper.retry()` + добавить success-лог:
```java
public void uploadResults(Long runId, List<TestResult> results) throws QaseException {
    // ... конвертация ...
    try {
        RetryHelper.retry(() -> {
            new ResultsApi(client).createResultsV2(project, runId, model);
        }, "upload results");
        logger.info("Results uploaded successfully: %d results", results.size());
    } catch (Exception e) {
        throw wrapException("upload test results", e);
    }
}
```

### 4. Flush буфера + respect `complete` flag в `TestopsReporter`

**Файл:** `qase-java-commons/src/main/java/io/qase/commons/reporters/TestopsReporter.java`

Изменить `completeTestRun()`:
```java
@Override
public void completeTestRun() throws QaseException {
    // Always flush remaining buffered results first
    uploadResults();

    // Respect the complete flag
    if (!this.config.run.complete) {
        logger.info("Test run %d: skipping completion (complete=false)", this.testRunId);
        return;
    }

    this.client.completeTestRun(this.testRunId);
    logger.info("Test run %d completed", this.testRunId);

    // Enable public report if configured (unchanged)
    ...
}
```

### 5. Тесты

**Новый файл:** `qase-java-commons/src/test/java/io/qase/commons/utils/RetryHelperTest.java`
- Успех с первой попытки — без retry
- Transient error (code=0) → retry → success
- Transient error (code=500) → 3 retry → exception
- Transient error (code=429) → retry → success
- Non-transient error (code=404) → сразу exception без retry
- Non-transient error (code=401) → сразу exception без retry
- `isRetryable()` для разных кодов

**Файл:** `qase-java-commons/src/test/java/io/qase/commons/reporters/TestopsReporterTest.java`
- `completeTestRun` flush-ит оставшиеся результаты из буфера
- `complete=false` → upload results + НЕ вызывает `client.completeTestRun()`
- `complete=true` → upload results + вызывает `client.completeTestRun()`

## Files to modify

| File | Change |
|------|--------|
| `qase-java-commons/.../utils/RetryHelper.java` | **New** — retry utility |
| `qase-java-commons/.../client/ApiClientV1.java` | Wrap all API calls in retry |
| `qase-java-commons/.../client/ApiClientV2.java` | Wrap uploadResults in retry + success log |
| `qase-java-commons/.../reporters/TestopsReporter.java` | Flush in completeTestRun + respect `complete` flag |
| `qase-java-commons/.../utils/RetryHelperTest.java` | **New** — retry unit tests |
| `qase-java-commons/.../reporters/TestopsReporterTest.java` | Tests for flush + complete flag |

## Verification

1. `mvn test -pl qase-java-commons` — запустить unit-тесты
2. Verify retry tests pass: timeout → retry → success, 404 → no retry
3. Verify `completeTestRun` flushes buffer before completing
4. Verify `complete=false` skips API completion but still uploads results
