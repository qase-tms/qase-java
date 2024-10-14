package io.qase.commons.aspects;

import io.qase.commons.StepStorage;
import io.qase.commons.annotation.Step;
import io.qase.commons.models.domain.StepResult;
import io.qase.commons.models.domain.StepResultStatus;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Aspect
public final class StepsAspects {
    private static final String DELIMITER = ", ";

    @Pointcut("@annotation(io.qase.api.annotation.Step)")
    public void withStepAnnotation() {
    }

    @Pointcut("execution(* *(..))")
    public void method() {
    }

    @Before("method() && withStepAnnotation()")
    public void stepStarting() {
        StepStorage.startStep();
    }

    @AfterReturning(pointcut = "method() && withStepAnnotation()")
    public void stepFinished(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Step stepAnnotation = methodSignature.getMethod().getAnnotation(Step.class);
        String stepsTitle = stepAnnotation.value();

        stepsTitle = getTitle(joinPoint, stepsTitle);

        StepResult step = StepStorage.getCurrentStep();
        step.data.action = stepsTitle;
        step.execution.status = StepResultStatus.PASSED;

        StepStorage.stopStep();
    }

    @AfterThrowing(pointcut = "method() && withStepAnnotation()", throwing = "e")
    public void stepFailed(JoinPoint joinPoint, Throwable e) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Step stepAnnotation = methodSignature.getMethod().getAnnotation(Step.class);
        String stepsTitle = stepAnnotation.value();
        stepsTitle = getTitle(joinPoint, stepsTitle);

        StepResult step = StepStorage.getCurrentStep();
        step.data.action = stepsTitle;
        step.execution.status = StepResultStatus.FAILED;
        step.throwable = e;

        StepStorage.stopStep();
    }

    private static String getTitle(JoinPoint joinPoint, String stepsTitle) {
        String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        Object[] args = joinPoint.getArgs();
        Map<String, String> params = new HashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            params.put(String.format("{%s}", parameterNames[i]), getString(args[i]));
        }
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (stepsTitle.contains(entry.getKey())) {
                stepsTitle = stepsTitle.replaceAll("\\" + entry.getKey(), entry.getValue());
            }
        }
        return stepsTitle;
    }

    private static String getString(Object args) {
        if (args == null) {
            return "null";
        }
        if (args.getClass().isArray()) {
            if (args instanceof int[]) {
                return Arrays.toString((int[]) args);
            } else if (args instanceof long[]) {
                return Arrays.toString((long[]) args);
            } else if (args instanceof double[]) {
                return Arrays.toString((double[]) args);
            } else if (args instanceof float[]) {
                return Arrays.toString((float[]) args);
            } else if (args instanceof boolean[]) {
                return Arrays.toString((boolean[]) args);
            } else if (args instanceof short[]) {
                return Arrays.toString((short[]) args);
            } else if (args instanceof char[]) {
                return Arrays.toString((char[]) args);
            } else if (args instanceof byte[]) {
                return Arrays.toString((byte[]) args);
            } else {
                return Arrays.stream(((Object[]) args)).map(String::valueOf).collect(Collectors.joining(DELIMITER));
            }
        }
        return String.valueOf(args);
    }
}
