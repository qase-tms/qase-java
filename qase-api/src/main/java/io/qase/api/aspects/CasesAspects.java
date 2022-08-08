package io.qase.api.aspects;

import io.qase.api.CasesStorage;
import io.qase.api.annotation.CaseId;
import io.qase.client.model.ResultCreate;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
@Slf4j
public final class CasesAspects {

    @Pointcut("@annotation(io.qase.api.annotation.CaseId)")
    public void withCaseIdAnnotation() {
    }

    @Pointcut("execution(* *(..))")
    public void method() {
    }

    @Before("method() && withCaseIdAnnotation()")
    public void caseStarting(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        CaseId caseIdAnnotation = methodSignature.getMethod().getAnnotation(CaseId.class);
        long caseId = caseIdAnnotation.value();

        CasesStorage.startCase(new ResultCreate().caseId(caseId));
    }
}
