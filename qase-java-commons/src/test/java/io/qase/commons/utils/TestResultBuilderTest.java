package io.qase.commons.utils;

import io.qase.commons.annotation.*;
import io.qase.commons.cucumber.CucumberTestCaseAdapter;
import io.qase.commons.models.annotation.Field;
import io.qase.commons.models.domain.Relations;
import io.qase.commons.models.domain.SuiteData;
import io.qase.commons.models.domain.TestResult;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TestResultBuilderTest {

    // -------------------------------------------------------------------------
    // Test helper annotations and methods
    // -------------------------------------------------------------------------

    @QaseIgnore
    void methodWithIgnore() {
    }

    @QaseId(42)
    void methodWithQaseId() {
    }

    @QaseId(42)
    @QaseSuite("A\tB")
    void methodWithQaseIdAndSuite() {
    }

    @QaseId(42)
    void methodWithQaseIdNoSuite() {
    }

    @QaseId(99)
    @QaseTitle("Custom Title")
    void methodWithCustomTitle() {
    }

    @QaseId(7)
    @QaseFields({@Field(name = "severity", value = "critical")})
    void methodWithFields() {
    }

    // -------------------------------------------------------------------------
    // fromMethod() tests
    // -------------------------------------------------------------------------

    @Test
    void fromMethod_withQaseIgnore_returnsIgnoreTrue() throws Exception {
        Method method = TestResultBuilderTest.class.getDeclaredMethod("methodWithIgnore");
        TestResult result = TestResultBuilder.fromMethod(method, Collections.<String, String>emptyMap(), 1000L);

        assertTrue(result.ignore);
    }

    @Test
    void fromMethod_withQaseIgnore_noOtherFieldsPopulated() throws Exception {
        Method method = TestResultBuilderTest.class.getDeclaredMethod("methodWithIgnore");
        TestResult result = TestResultBuilder.fromMethod(method, Collections.<String, String>emptyMap(), 1000L);

        assertTrue(result.ignore);
        // Early return — testopsIds should remain null (not populated)
        assertNull(result.testopsIds);
        assertNull(result.title);
    }

    @Test
    void fromMethod_withQaseId_setsTestopsIdsAndExecutionFields() throws Exception {
        Method method = TestResultBuilderTest.class.getDeclaredMethod("methodWithQaseId");
        long startTime = 12345L;
        Map<String, String> params = Collections.<String, String>emptyMap();

        TestResult result = TestResultBuilder.fromMethod(method, params, startTime);

        assertFalse(result.ignore);
        assertNotNull(result.testopsIds);
        assertEquals(1, result.testopsIds.size());
        assertEquals(42L, (long) result.testopsIds.get(0));
        assertEquals(startTime, result.execution.startTime);
        assertEquals(Thread.currentThread().getName(), result.execution.thread);
    }

    @Test
    void fromMethod_withQaseSuite_setsRelationsWithTwoEntries() throws Exception {
        Method method = TestResultBuilderTest.class.getDeclaredMethod("methodWithQaseIdAndSuite");
        TestResult result = TestResultBuilder.fromMethod(method, Collections.<String, String>emptyMap(), 0L);

        assertNotNull(result.relations);
        assertNotNull(result.relations.suite);
        assertEquals(2, result.relations.suite.data.size());
        assertEquals("A", result.relations.suite.data.get(0).title);
        assertEquals("B", result.relations.suite.data.get(1).title);
    }

    @Test
    void fromMethod_withoutQaseSuite_fallsBackToDeclaringClassName() throws Exception {
        Method method = TestResultBuilderTest.class.getDeclaredMethod("methodWithQaseIdNoSuite");
        TestResult result = TestResultBuilder.fromMethod(method, Collections.<String, String>emptyMap(), 0L);

        assertNotNull(result.relations);
        assertEquals(1, result.relations.suite.data.size());
        assertEquals(TestResultBuilderTest.class.getName(), result.relations.suite.data.get(0).title);
    }

    @Test
    void fromMethod_withParameters_setsParamsAndSignature() throws Exception {
        Method method = TestResultBuilderTest.class.getDeclaredMethod("methodWithQaseId");
        Map<String, String> params = new HashMap<>();
        params.put("key", "val");

        TestResult result = TestResultBuilder.fromMethod(method, params, 0L);

        assertEquals(params, result.params);
        assertNotNull(result.signature);
        assertTrue(result.signature.contains("key"));
    }

    @Test
    void fromMethod_withQaseTitle_setsCustomTitle() throws Exception {
        Method method = TestResultBuilderTest.class.getDeclaredMethod("methodWithCustomTitle");
        TestResult result = TestResultBuilder.fromMethod(method, Collections.<String, String>emptyMap(), 0L);

        assertEquals("Custom Title", result.title);
    }

    @Test
    void fromMethod_withQaseFields_setsFieldsMap() throws Exception {
        Method method = TestResultBuilderTest.class.getDeclaredMethod("methodWithFields");
        TestResult result = TestResultBuilder.fromMethod(method, Collections.<String, String>emptyMap(), 0L);

        assertNotNull(result.fields);
        assertEquals("critical", result.fields.get("severity"));
    }

    // -------------------------------------------------------------------------
    // fromAnnotationReader() tests
    // -------------------------------------------------------------------------

    /**
     * A simple test AnnotationReader backed by a map of annotation type → annotation instance.
     * Used for fromAnnotationReader() unit tests without any JUnit4 dependency.
     */
    static final class MapAnnotationReader implements AnnotationReader {
        private final Map<Class<?>, Annotation> annotations;

        MapAnnotationReader(Map<Class<?>, Annotation> annotations) {
            this.annotations = annotations;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
            return (T) annotations.get(annotationType);
        }
    }

    private static QaseIgnore newQaseIgnore() {
        return new QaseIgnore() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return QaseIgnore.class;
            }
        };
    }

    private static QaseId newQaseId(final long value) {
        return new QaseId() {
            @Override
            public long value() {
                return value;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return QaseId.class;
            }
        };
    }

    private static QaseIds newQaseIds(final long... values) {
        return new QaseIds() {
            @Override
            public long[] value() {
                return values;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return QaseIds.class;
            }
        };
    }

    private static CaseId newCaseId(final long value) {
        return new CaseId() {
            @Override
            public long value() {
                return value;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return CaseId.class;
            }
        };
    }

    private static QaseTitle newQaseTitle(final String value) {
        return new QaseTitle() {
            @Override
            public String value() {
                return value;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return QaseTitle.class;
            }
        };
    }

    @Test
    void fromAnnotationReader_withQaseIgnore_returnsIgnoreTrue() {
        Map<Class<?>, Annotation> map = new HashMap<>();
        map.put(QaseIgnore.class, newQaseIgnore());
        AnnotationReader reader = new MapAnnotationReader(map);

        TestResult result = TestResultBuilder.fromAnnotationReader(reader, "com.example.MyTest", "testFoo",
                null, 1000L);

        assertTrue(result.ignore);
    }

    @Test
    void fromAnnotationReader_withQaseId_resolvesViaReader() {
        Map<Class<?>, Annotation> map = new HashMap<>();
        map.put(QaseId.class, newQaseId(55L));
        AnnotationReader reader = new MapAnnotationReader(map);

        TestResult result = TestResultBuilder.fromAnnotationReader(reader, "com.example.MyTest", "testFoo",
                null, 999L);

        assertFalse(result.ignore);
        assertNotNull(result.testopsIds);
        assertEquals(1, result.testopsIds.size());
        assertEquals(55L, (long) result.testopsIds.get(0));
        assertEquals(999L, result.execution.startTime);
    }

    @Test
    void fromAnnotationReader_fallsBackToCaseId_whenQaseIdAndQaseIdsAbsent() {
        Map<Class<?>, Annotation> map = new HashMap<>();
        map.put(CaseId.class, newCaseId(77L));
        AnnotationReader reader = new MapAnnotationReader(map);

        TestResult result = TestResultBuilder.fromAnnotationReader(reader, "com.example.MyTest", "testFoo",
                null, 0L);

        assertNotNull(result.testopsIds);
        assertEquals(1, result.testopsIds.size());
        assertEquals(77L, (long) result.testopsIds.get(0));
    }

    @Test
    void fromAnnotationReader_usesClassNameAsSuiteFallbackAndBuildsSignature() {
        Map<Class<?>, Annotation> map = new HashMap<>();
        map.put(QaseId.class, newQaseId(1L));
        AnnotationReader reader = new MapAnnotationReader(map);

        TestResult result = TestResultBuilder.fromAnnotationReader(reader, "com.example.MyTest", "testFoo",
                null, 0L);

        // Suite fallback = className
        assertNotNull(result.relations);
        assertEquals(1, result.relations.suite.data.size());
        assertEquals("com.example.MyTest", result.relations.suite.data.get(0).title);

        // Signature must be set
        assertNotNull(result.signature);
        // JUnit4 signature uses className dot-to-colon: "com:example:mytest"
        assertTrue(result.signature.contains("com:example:mytest"));
        // And the method name part
        assertTrue(result.signature.contains("testfoo"));
    }

    @Test
    void fromAnnotationReader_withNullParameters_doesNotThrow() {
        Map<Class<?>, Annotation> map = new HashMap<>();
        map.put(QaseId.class, newQaseId(2L));
        AnnotationReader reader = new MapAnnotationReader(map);

        assertDoesNotThrow(() ->
                TestResultBuilder.fromAnnotationReader(reader, "com.example.Test", "testBar", null, 0L)
        );
    }

    // -------------------------------------------------------------------------
    // fromCucumber() tests
    // -------------------------------------------------------------------------

    static final class StubAdapter implements CucumberTestCaseAdapter {
        private final List<String> tags;
        private final String name;
        private final List<String> uriPathParts;

        StubAdapter(List<String> tags, String name, List<String> uriPathParts) {
            this.tags = tags;
            this.name = name;
            this.uriPathParts = uriPathParts;
        }

        @Override
        public List<String> getTags() {
            return tags;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public List<String> getUriPathParts() {
            return uriPathParts;
        }

        @Override
        public Map<String, String> getParameters() {
            return Collections.<String, String>emptyMap();
        }
    }

    @Test
    void fromCucumber_withQaseIgnoreTag_returnsIgnoreTrue() {
        StubAdapter adapter = new StubAdapter(
                Arrays.asList("@QaseIgnore"),
                "My Scenario",
                Arrays.asList("features", "login")
        );

        TestResult result = TestResultBuilder.fromCucumber(adapter, Collections.<String, String>emptyMap(), 1000L);

        assertTrue(result.ignore);
    }

    @Test
    void fromCucumber_withoutQaseTitleTag_useAdapterGetName() {
        StubAdapter adapter = new StubAdapter(
                Collections.<String>emptyList(),
                "Login scenario",
                Arrays.asList("features", "login")
        );

        TestResult result = TestResultBuilder.fromCucumber(adapter, Collections.<String, String>emptyMap(), 0L);

        assertFalse(result.ignore);
        assertEquals("Login scenario", result.title);
    }

    @Test
    void fromCucumber_usesCucumberUtilsForTagBasedExtraction() {
        StubAdapter adapter = new StubAdapter(
                Arrays.asList("@QaseId=123", "@QaseTitle=Tag Title"),
                "Scenario Name",
                Arrays.asList("features")
        );

        TestResult result = TestResultBuilder.fromCucumber(adapter, Collections.<String, String>emptyMap(), 500L);

        assertFalse(result.ignore);
        assertNotNull(result.testopsIds);
        assertFalse(result.testopsIds.isEmpty());
        assertEquals(123L, (long) result.testopsIds.get(0));
        // Title from tag overrides adapter.getName()
        assertEquals("Tag Title", result.title);
        assertEquals(500L, result.execution.startTime);
        assertEquals(Thread.currentThread().getName(), result.execution.thread);
        assertNotNull(result.signature);
    }

    @Test
    void fromCucumber_generatesSignatureFromUriPartsAndTitle() {
        StubAdapter adapter = new StubAdapter(
                Arrays.asList("@QaseId=42"),
                "Login scenario",
                Arrays.asList("features", "login", "auth.feature")
        );
        Map<String, String> params = new HashMap<>();
        params.put("user", "admin");

        TestResult result = TestResultBuilder.fromCucumber(adapter, params, 0L);

        assertNotNull(result.signature);
        // Signature should contain URI path parts + title
        assertTrue(result.signature.contains("features"));
        assertTrue(result.signature.contains("login"));
        // Params should be part of signature
        assertTrue(result.signature.contains("user"));
    }

    @Test
    void fromCucumber_buildsRelationsFromSuiteTagOrFallsBackToUriPathParts() {
        // With no @QaseSuite tag, should use URI path parts
        StubAdapter adapter = new StubAdapter(
                Collections.<String>emptyList(),
                "My Scenario",
                Arrays.asList("features", "login", "auth")
        );

        TestResult result = TestResultBuilder.fromCucumber(adapter, Collections.<String, String>emptyMap(), 0L);

        assertNotNull(result.relations);
        assertNotNull(result.relations.suite);
        // URI path parts become suite entries
        assertEquals(3, result.relations.suite.data.size());
        assertEquals("features", result.relations.suite.data.get(0).title);
        assertEquals("login", result.relations.suite.data.get(1).title);
        assertEquals("auth", result.relations.suite.data.get(2).title);
    }
}
