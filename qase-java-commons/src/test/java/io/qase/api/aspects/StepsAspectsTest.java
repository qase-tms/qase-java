package io.qase.api.aspects;

import io.qase.api.StepStorage;
import io.qase.api.annotation.Step;
import io.qase.client.v1.models.TestStepResultCreate;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StepsAspectsTest {
    @Step("Step {string}")
    public void stepFailed(String string) {
        throw new AssertionError();
    }

    @Step("Step {string} and {integer}")
    public void step(String string, int integer) {
    }

    @Step("Step {values}")
    void stepWithStringVararg(String... values) {
    }

    @Step("Step {values}")
    void stepWithIntVararg(int... values) {
    }

    @Step("Step {values}")
    void stepWithLongVararg(long... values) {
    }

    @Step("Step {values}")
    void stepWithDoubleVararg(double... values) {
    }

    @Step("Step {values}")
    void stepWithFloatVararg(float... values) {
    }

    @Step("Step {values}")
    void stepWithBooleanVararg(boolean... values) {
    }

    @Step("Step {values}")
    void stepWithShortVararg(short... values) {
    }

    @Step("Step {values}")
    void stepWithCharVararg(char... values) {
    }

    @Step("Step {values}")
    void stepWithByteVararg(byte... values) {
    }

    @Step("Step {values}")
    void stepWithList(List<Object> values) {
    }

    @Test
    void stepWithParameters() {
        step("str", 1);
        LinkedList<TestStepResultCreate> steps = StepStorage.stopSteps();
        assertEquals(1, steps.size());
        assertEquals("Step str and 1", steps.get(0).getAction());
    }

    @Test
    void stepWithStringVararg() {
        stepWithStringVararg("one", "two", "three");
        LinkedList<TestStepResultCreate> steps = StepStorage.stopSteps();
        assertEquals(1, steps.size());
        assertEquals("Step one, two, three", steps.get(0).getAction());
    }

    @Test
    void stepWithIntVararg() {
        stepWithIntVararg(1, 2, 3);
        LinkedList<TestStepResultCreate> steps = StepStorage.stopSteps();
        assertEquals(1, steps.size());
        assertEquals("Step [1, 2, 3]", steps.get(0).getAction());
    }

    @Test
    void stepWithLongVararg() {
        stepWithLongVararg(1L, 2L, 3L);
        LinkedList<TestStepResultCreate> steps = StepStorage.stopSteps();
        assertEquals(1, steps.size());
        assertEquals("Step [1, 2, 3]", steps.get(0).getAction());
    }

    @Test
    void stepWithDoubleVararg() {
        stepWithDoubleVararg(1.1, 1.2, 1.3);
        LinkedList<TestStepResultCreate> steps = StepStorage.stopSteps();
        assertEquals(1, steps.size());
        assertEquals("Step [1.1, 1.2, 1.3]", steps.get(0).getAction());
    }

    @Test
    void stepWithFloatVararg() {
        stepWithFloatVararg(1.1f, 1.2f, 1.3f);
        LinkedList<TestStepResultCreate> steps = StepStorage.stopSteps();
        assertEquals(1, steps.size());
        assertEquals("Step [1.1, 1.2, 1.3]", steps.get(0).getAction());
    }

    @Test
    void stepWithCharVararg() {
        stepWithCharVararg('a', 'A', 'b', '2');
        LinkedList<TestStepResultCreate> steps = StepStorage.stopSteps();
        assertEquals(1, steps.size());
        assertEquals("Step [a, A, b, 2]", steps.get(0).getAction());
    }

    @Test
    void stepWithBooleanVararg() {
        stepWithBooleanVararg(true, false);
        LinkedList<TestStepResultCreate> steps = StepStorage.stopSteps();
        assertEquals(1, steps.size());
        assertEquals("Step [true, false]", steps.get(0).getAction());
    }

    @Test
    void stepWithShortVararg() {
        stepWithShortVararg((short) 1, (short) 2);
        LinkedList<TestStepResultCreate> steps = StepStorage.stopSteps();
        assertEquals(1, steps.size());
        assertEquals("Step [1, 2]", steps.get(0).getAction());
    }

    @Test
    void stepWithByteVararg() {
        stepWithByteVararg((byte) 1, (byte) 2);
        LinkedList<TestStepResultCreate> steps = StepStorage.stopSteps();
        assertEquals(1, steps.size());
        assertEquals("Step [1, 2]", steps.get(0).getAction());
    }

    @Test
    void stepWithList() {
        stepWithList(Arrays.asList("1", "2", "3"));
        LinkedList<TestStepResultCreate> steps = StepStorage.stopSteps();
        assertEquals(1, steps.size());
        assertEquals("Step [1, 2, 3]", steps.get(0).getAction());
    }

    @Test
    void stepWithNull() {
        stepWithStringVararg(null);
        LinkedList<TestStepResultCreate> steps = StepStorage.stopSteps();
        assertEquals(1, steps.size());
        assertEquals("Step null", steps.get(0).getAction());
    }

    @Test
    void stepFailedTest() {
        try {
            stepFailed("string");
        } catch (AssertionError ignore) {
        }
        LinkedList<TestStepResultCreate> steps = StepStorage.stopSteps();
        assertEquals(1, steps.size());
        assertEquals("Step string", steps.get(0).getAction());
    }
}
