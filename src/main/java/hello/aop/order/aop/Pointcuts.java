package hello.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    // hello.aop.order 패키지와 하위 패키지
    @Pointcut("execution(* hello.aop.order..*(..))")
    public void allOrder() {} // pointcut signature

    // 클래스 이름 패턴이 *Service => 비즈니스 로직을 시작할 때 트랜잭션을 걸고 비지느시 로직이 끝날 때 커밋, 롤백을 결정하기 때문에
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService() {}

    // allOrder && allService
    @Pointcut("allOrder() && allService()")
    public void orderAndService() {}
}
