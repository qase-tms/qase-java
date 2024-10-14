//package io.qase.api.aspects;
//
//import io.qase.commons.StepStorage;
//import io.qase.commons.annotation.Step;
//import io.qase.commons.models.domain.StepResult;
//import org.junit.jupiter.api.Test;
//
//import java.util.Arrays;
//import java.util.LinkedList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//class StepsAspectsTest {
//    @Step("Step {string}")
//    public void stepFailed(String string) {
//        throw new AssertionError();
//    }
//
//    @Step("Step {string} and {integer}")
//    public void step(String string, int integer) {
//    }
//
//    @Step("Step {values}")
//    void stepWithStringVararg(String... values) {
//    }
//
//    @Step("Step {values}")
//    void stepWithIntVararg(int... values) {
//    }
//
//    @Step("Step {values}")
//    void stepWithLongVararg(long... values) {
//    }
//
//    @Step("Step {values}")
//    void stepWithDoubleVararg(double... values) {
//    }
//
//    @Step("Step {values}")
//    void stepWithFloatVararg(float... values) {
//    }
//
//    @Step("Step {values}")
//    void stepWithBooleanVararg(boolean... values) {
//    }
//
//    @Step("Step {values}")
//    void stepWithShortVararg(short... values) {
//    }
//
//    @Step("Step {values}")
//    void stepWithCharVararg(char... values) {
//    }
//
//    @Step("Step {values}")
//    void stepWithByteVararg(byte... values) {
//    }
//
//    @Step("Step {values}")
//    void stepWithList(List<Object> values) {
//    }
//
//    @Test
//    void stepWithParameters() {
//        step("str", 1);
//        LinkedList<StepResult> steps = StepStorage.stopSteps();
//        assertEquals(1, steps.size());
//        assertEquals("Step str and 1", steps.get(0).data.action);
//    }
//
//    @Test
//    void stepWithStringVararg() {
//        stepWithStringVararg("one", "two", "three");
//        LinkedList<StepResult> steps = StepStorage.stopSteps();
//        assertEquals(1, steps.size());
//        assertEquals("Step one, two, three", steps.get(0).data.action);
//    }
//
//    @Test
//    void stepWithIntVararg() {
//        stepWithIntVararg(1, 2, 3);
//        LinkedList<StepResult> steps = StepStorage.stopSteps();
//        assertEquals(1, steps.size());
//        assertEquals("Step [1, 2, 3]", steps.get(0).data.action);
//    }
//
//    @Test
//    void stepWithLongVararg() {
//        stepWithLongVararg(1L, 2L, 3L);
//        LinkedList<StepResult> steps = StepStorage.stopSteps();
//        assertEquals(1, steps.size());
//        assertEquals("Step [1, 2, 3]", steps.get(0).data.action);
//    }
//
//    @Test
//    void stepWithDoubleVararg() {
//        stepWithDoubleVararg(1.1, 1.2, 1.3);
//        LinkedList<StepResult> steps = StepStorage.stopSteps();
//        assertEquals(1, steps.size());
//        assertEquals("Step [1.1, 1.2, 1.3]", steps.get(0).data.action);
//    }
//
//    @Test
//    void stepWithFloatVararg() {
//        stepWithFloatVararg(1.1f, 1.2f, 1.3f);
//        LinkedList<StepResult> steps = StepStorage.stopSteps();
//        assertEquals(1, steps.size());
//        assertEquals("Step [1.1, 1.2, 1.3]", steps.get(0).data.action);
//    }
//
//    @Test
//    void stepWithCharVararg() {
//        stepWithCharVararg('a', 'A', 'b', '2');
//        LinkedList<StepResult> steps = StepStorage.stopSteps();
//        assertEquals(1, steps.size());
//        assertEquals("Step [a, A, b, 2]", steps.get(0).data.action);
//    }
//
//    @Test
//    void stepWithBooleanVararg() {
//        stepWithBooleanVararg(true, false);
//        LinkedList<StepResult> steps = StepStorage.stopSteps();
//        assertEquals(1, steps.size());
//        assertEquals("Step [true, false]", steps.get(0).data.action);
//    }
//
//    @Test
//    void stepWithShortVararg() {
//        stepWithShortVararg((short) 1, (short) 2);
//        LinkedList<StepResult> steps = StepStorage.stopSteps();
//        assertEquals(1, steps.size());
//        assertEquals("Step [1, 2]", steps.get(0).data.action);
//    }
//
//    @Test
//    void stepWithByteVararg() {
//        stepWithByteVararg((byte) 1, (byte) 2);
//        LinkedList<StepResult> steps = StepStorage.stopSteps();
//        assertEquals(1, steps.size());
//        assertEquals("Step [1, 2]", steps.get(0).data.action);
//    }
//
//    @Test
//    void stepWithList() {
//        stepWithList(Arrays.asList("1", "2", "3"));
//        LinkedList<StepResult> steps = StepStorage.stopSteps();
//        assertEquals(1, steps.size());
//        assertEquals("Step [1, 2, 3]", steps.get(0).data.action);
//    }
//
//    @Test
//    void stepWithNull() {
//        stepWithStringVararg(null);
//        LinkedList<StepResult> steps = StepStorage.stopSteps();
//        assertEquals(1, steps.size());
//        assertEquals("Step null", steps.get(0).data.action);
//    }
//
//    @Test
//    void stepFailedTest() {
//        try {
//            stepFailed("string");
//        } catch (AssertionError ignore) {
//        }
//        LinkedList<StepResult> steps = StepStorage.stopSteps();
//        assertEquals(1, steps.size());
//        assertEquals("Step string", steps.get(0).data.action);
//    }
//}
