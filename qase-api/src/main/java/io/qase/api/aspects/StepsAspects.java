package io.qase.api.aspects;

import io.qase.api.StepStorage;
import io.qase.api.annotation.Step;
import io.qase.api.utils.IntegrationUtils;
import io.qase.client.model.ResultCreateSteps;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.HashMap;
import java.util.Map;

@Aspect
public final class StepsAspects {

    @Pointcut("@annotation(io.qase.api.annotation.Step)")
    public void withStepAnnotation() {
    }

    @Pointcut("execution(* *(..))")
    public void method() {
    }

    @AfterReturning(pointcut = "method() && withStepAnnotation()")
    public void stepFinished(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Step stepAnnotation = methodSignature.getMethod().getAnnotation(Step.class);
        String stepsTitle = stepAnnotation.value();

        stepsTitle = getTitle(joinPoint, stepsTitle);

        ResultCreateSteps step = new ResultCreateSteps()
                .action(stepsTitle)
                .status(ResultCreateSteps.StatusEnum.PASSED);
        StepStorage.addStep(step);
    }

    private String getTitle(JoinPoint joinPoint, String stepsTitle) {
        String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        Object[] args = joinPoint.getArgs();
        Map<String, String> params = new HashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            params.put(String.format("{%s}", parameterNames[i]), String.valueOf(args[i]));
        }
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (stepsTitle.contains(entry.getKey())) {
                stepsTitle = stepsTitle.replaceAll("\\" + entry.getKey(), entry.getValue());
            }
        }
        return stepsTitle;
    }

    @AfterThrowing(pointcut = "method() && withStepAnnotation()", throwing = "e")
    public void stepFailed(JoinPoint joinPoint, Throwable e) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Step stepAnnotation = methodSignature.getMethod().getAnnotation(Step.class);
        ResultCreateSteps step = new ResultCreateSteps()
                .action(stepAnnotation.value())
                .status(ResultCreateSteps.StatusEnum.FAILED)
                .addAttachmentsItem(IntegrationUtils.getStacktrace(e));
        StepStorage.addStep(step);
    }
}
