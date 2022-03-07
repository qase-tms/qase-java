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
        ResultCreateSteps step = new ResultCreateSteps()
                .action(stepAnnotation.value())
                .status(ResultCreateSteps.StatusEnum.PASSED);
        StepStorage.addStep(step);
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
