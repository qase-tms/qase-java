package io.qase.commons.reporters;

import io.qase.commons.QaseException;
import io.qase.commons.config.Mode;
import io.qase.commons.config.QaseConfig;
import io.qase.commons.models.domain.TestResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CoreReporterTest {

    private QaseConfig config;

    @BeforeEach
    void setUp() {
        config = new QaseConfig();
        config.mode = Mode.OFF;
        config.fallback = Mode.OFF;
    }

    @Test
    void testFallbackSwapOnPrimaryFailure() throws QaseException {
        InternalReporter primary = mock(InternalReporter.class);
        InternalReporter fallback = mock(InternalReporter.class);

        doThrow(new QaseException("API error")).when(primary).addResult(any());
        when(primary.getResults()).thenReturn(new ArrayList<>());

        CoreReporter reporter = new CoreReporter(config, primary, fallback);

        TestResult result = new TestResult();
        result.title = "test";
        reporter.addResult(result);

        verify(fallback).startTestRun();
        verify(fallback).setResults(any());
        verify(fallback).addResult(result);
    }

    @Test
    void testReporterDisabledAfterFailureWithoutFallback() throws QaseException {
        InternalReporter primary = mock(InternalReporter.class);

        doThrow(new QaseException("API error")).when(primary).addResult(any());

        CoreReporter reporter = new CoreReporter(config, primary, null);

        TestResult result1 = new TestResult();
        result1.title = "test1";
        reporter.addResult(result1);

        // After failure without fallback, reporter should be null — subsequent calls are no-ops
        TestResult result2 = new TestResult();
        result2.title = "test2";
        reporter.addResult(result2);

        verify(primary, times(1)).addResult(any());
    }

    @Test
    void testConcurrentAddResultAllSucceed() throws Exception {
        int threadCount = 50;
        InternalReporter primary = mock(InternalReporter.class);
        AtomicInteger callCount = new AtomicInteger(0);

        doAnswer(invocation -> {
            callCount.incrementAndGet();
            return null;
        }).when(primary).addResult(any());

        CoreReporter reporter = new CoreReporter(config, primary, null);

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int idx = i;
            executor.submit(() -> {
                try {
                    startLatch.await();
                    TestResult result = new TestResult();
                    result.title = "test-" + idx;
                    reporter.addResult(result);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    doneLatch.countDown();
                }
            });
        }

        startLatch.countDown();
        assertTrue(doneLatch.await(10, TimeUnit.SECONDS), "All threads should complete within timeout");
        executor.shutdown();

        assertEquals(threadCount, callCount.get(), "All results should be delivered");
    }

    @Test
    void testFallbackSwapIsAtomicUnderConcurrency() throws Exception {
        int threadCount = 20;
        InternalReporter primary = mock(InternalReporter.class);
        InternalReporter fallback = mock(InternalReporter.class);
        AtomicInteger fallbackStartCount = new AtomicInteger(0);

        doThrow(new QaseException("API error")).when(primary).addResult(any());
        when(primary.getResults()).thenReturn(new ArrayList<>());

        doAnswer(invocation -> {
            fallbackStartCount.incrementAndGet();
            return null;
        }).when(fallback).startTestRun();

        CoreReporter reporter = new CoreReporter(config, primary, fallback);

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int idx = i;
            executor.submit(() -> {
                try {
                    startLatch.await();
                    TestResult result = new TestResult();
                    result.title = "test-" + idx;
                    reporter.addResult(result);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    doneLatch.countDown();
                }
            });
        }

        startLatch.countDown();
        assertTrue(doneLatch.await(10, TimeUnit.SECONDS), "All threads should complete within timeout");
        executor.shutdown();

        assertEquals(1, fallbackStartCount.get(), "Fallback startTestRun should be called exactly once");
    }

    @Test
    void testNoDataRaceOnReporterField() throws Exception {
        int threadCount = 50;
        InternalReporter primary = mock(InternalReporter.class);

        CoreReporter reporter = new CoreReporter(config, primary, null);

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(threadCount);
        List<Throwable> errors = Collections.synchronizedList(new ArrayList<>());

        for (int i = 0; i < threadCount; i++) {
            final int idx = i;
            executor.submit(() -> {
                try {
                    startLatch.await();
                    if (idx % 3 == 0) {
                        TestResult result = new TestResult();
                        result.title = "test-" + idx;
                        reporter.addResult(result);
                    } else if (idx % 3 == 1) {
                        reporter.getTestCaseIdsForExecution();
                    } else {
                        reporter.uploadResults();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Throwable t) {
                    errors.add(t);
                } finally {
                    doneLatch.countDown();
                }
            });
        }

        startLatch.countDown();
        assertTrue(doneLatch.await(10, TimeUnit.SECONDS), "All threads should complete within timeout");
        executor.shutdown();

        assertTrue(errors.isEmpty(), "No exceptions should occur: " + errors);
    }
}
