package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class AspectV6Advice {

/*
    @Around("hello.aop.order.aop.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            // @Before
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            // @AfterReturning
            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            // @AfterThrowing
            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            // @After
            log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }
*/

    // target 을 실행해줘야 하지 않나용??
    // 이 @Before 는 개발자가 실행하는 것이 아니라. 그냥 실행해주는거에요.
    // 실행하기 이전에 들어갈 로직, 부가 기능을 구현해주면 된다.
    @Before("hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) { // @Before 에서는 ProceedingJoinPoint 를 사용할 수 없음.
        // joinPoint 를 굳이 인자로 넘겨받지 않아도 proceed 가 수행됨.
        log.info("[before] {}", joinPoint.getSignature());
    }

    // @AfterReturning 에 있는 returning 의 이름과 인자의 result 가 매칭됨
    @AfterReturning(value = "hello.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result) {
        // 받아온 result 를 삶아 먹든, 구워 먹든 상관은 없지만
        // result 라는 식재료 그 자체를 바꿀 수는 없다.
        log.info("[return] {} return={}", joinPoint.getSignature(), result);
    }

    // @AfterReturning 에 있는 returning 의 이름과 인자의 result 가 매칭됨
    @AfterReturning(value = "hello.aop.order.aop.Pointcuts.allOrder()", returning = "result")
    public void doReturn2(JoinPoint joinPoint, Object result) {
        // 받아온 result 를 삶아 먹든, 구워 먹든 상관은 없지만
        // result 라는 식재료 그 자체를 바꿀 수는 없다.
        log.info("[return2] {} return={}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(value = "hello.aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex) {
        log.info("[ex] {} message={}", ex, ex.getMessage());
    }

    @After(value = "hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("[after] {}", joinPoint.getSignature());
    }
}
