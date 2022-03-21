package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV3 {

    // 이 포인트 컷을 여러 곳에서 사용할 수 있겠지?
    // hello.aop.order 패키지와 하위 패키지
    @Pointcut("execution(* hello.aop.order..*(..))")
    private void allOrder(){} // pointcut signature

    // 클래스 이름 패턴이 *Service => 비즈니스 로직을 시작할 때 트랜잭션을 걸고 비지느시 로직이 끝날 때 커밋, 롤백을 결정하기 때문에
    @Pointcut("execution(* *..*Service.*(..))")
    private void allService() {}

    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable{
        log.info("[log] {}", joinPoint.getSignature()); // join point 시그니처

        return joinPoint.proceed(); // target 호출 target invoke
    }

    // hello.aop.order 패키지와 하위 패키지 이면서 동시에 클래스 이름 패턴이 *Service 인 것
    @Around("allOrder() && allService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable{

        try {
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }
}
