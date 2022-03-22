package swt6.spring.basics.aop.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TraceAdvice {

    @Before("execution(public * swt6.spring.basics.aop.logic..*.*(..))")
    public void traceBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getTarget().getClass().getName() + "." +
                joinPoint.getSignature().getName();
        System.out.println("---> " + methodName);
    }

    @After("execution(public * swt6.spring.basics.aop.logic..*.*(..))")
    public void traceAfter(JoinPoint joinPoint) {
        String methodName = joinPoint.getTarget().getClass().getName() + "." +
                joinPoint.getSignature().getName();
        System.out.println("<--- " + methodName);
    }

    @Around("execution(public * swt6.spring.basics.aop.logic..*.find*ById*(..))")
    public Object traceAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String methodName = proceedingJoinPoint.getTarget().getClass().getName() + "." +
                proceedingJoinPoint.getSignature().getName();

        System.out.println("===> " + methodName);

        Object result = proceedingJoinPoint.proceed();

        System.out.println("<=== " + methodName);

        return result;
    }

    @AfterThrowing(pointcut = "execution(public * swt6.spring.basics.aop.logic..*.find*ById*(..))", throwing = "exception")
    public void traceException(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getTarget().getClass().getName() + "." +
                joinPoint.getSignature().getName();

        System.out.printf("##> %s%n threw exception %s%n ", methodName, exception);
    }
}
