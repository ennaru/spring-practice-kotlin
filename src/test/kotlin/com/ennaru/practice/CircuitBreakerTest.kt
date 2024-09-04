package com.ennaru.practice

import com.ennaru.practice.circuitbreaker.CircuitBreakerService
import com.ennaru.practice.common.vo.logger
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.CompletionStage

@SpringBootTest
@DisplayName("CircuitBreaker 테스트")
class CircuitBreakerTest @Autowired constructor(
    val circuitBreakerService: CircuitBreakerService
) {

    val log = logger()

    @BeforeEach
    fun before() = println("circuitBreak TestBefore")

    @AfterEach
    fun after() = println("circuitBreak TestAfter")

    /**
     * [resilience4j-circuitbreaker]는 @CircuitBreaker 어노테이션을 사용하여 컨트롤할 수 있습니다.
     * - dependency : resilience4j-spring
     *
     * [소스코드 로직]
     * @CircuitBreaker 어노테이션은 CircuitBreakerAspect.java 파일에서 읽어와 컨트롤합니다.
     * 따라서 @Aspect 어노테이션을 사용하므로 'spring-boot-starter-aop' dependency 추가는 필수입니다.
     * - 1. circuitBreakerAroundAdvice() 함수가 @CircuitBreaker 어노테이션 값, 함수 정보를 긁어옵니다.
     * - 2. getOrCreateCircuitBreaker(methodName, backend)가 호출되면 CircuitBreaker을 상속받는 CircuitBreakerStateMachine 클래스가 반환됩니다.
     * - 3. 내부 함수인 proceed()가 호출되어 비즈니스 로직이 수행됩니다.
     * - 4. proceed() 함수에서는
     *  - circuitBreakerAspectExtList 존재유무에 따라 circuitBreakerAspectExt.handle() 함수를 호출합니다.
     *  - 함수의 반환 타입이 [CompletionStage]라면 handleJoinPointCompletableFuture() 함수를 호출합니다.
     *  - 그 외의 경우는 defaultHandling() 함수를 호출합니다.
     * - 4. circuitBreaker 클래스에 정의된 함수 executeCheckedSupplier() -> decorateCheckedSupplier() 순으로 호출됩니다.
     * - 5. 수행 결과에 따라 onResult(), onError()을 호출합니다.
     */

    /**
     * @CircuitBreaker 어노테이션이 붙은 함수 실행 시 호출 결과는 slidingWindow 에 집계됩니다.
     * 실패 결과는 CircuitBreakerStateMachine.java 의 ClosedState class가 관리합니다.
     */

    @Test
    @DisplayName("success test")
    fun circuitBreakerSuccess() {
        circuitBreakerService.svc(0,false)
    }

    /**
     * @CircuitBreaker 가 붙은 함수에서 Exception 발생 시
     * 'FAILURE_RATE'에 영향을 줍니다.
     */
    @Test
    @DisplayName("failure test")
    fun circuitBreakerFailure() {
        circuitBreakerService.svc(0,true)
    }

    /**
     * @CircuitBreaker 가 붙은 함수 실행 시
     * 소요시간이 Config에서 설정한 slowCallDurationThreshold 보다 늦으면
     * Exception을 떨구지 않고 SUCCESS로 판단하지만
     * 'SLOW_CALL_RATE'에 영향을 줍니다.
     */
    @Test
    @DisplayName("failure test")
    fun circuitBreakerSlowCall() {
        circuitBreakerService.svc(0, false, 5000)
    }

    /**
     * @CircuitBreaker 는 아래 이벤트에 의해 Circuit State가 ('CLOSED' -> 'OPEN')으로 변합니다.
     * 'FAILURE_RATE_EXCEEDED' => Exception 빈도
     * 'SLOW_CALL_RATE_EXCEEDED' => 함수 실행시간 초과 빈도
     */

    @Test
    @DisplayName("failure_rate 테스트")
    fun failureRate() {

        // config에 설정한 failureRateThreshold 비율을 초과하면 'FAILURE_RATE_EXCEEDED'이 발생합니다.
        // failureRateThreshold = [failure] / [slidingWindowSize]
        // 단, minimumNumberOfCalls 를 만족하지 않으면 'FAILURE_RATE_EXCEEDED'가 발생하지 않으나 slidingWindowSize가 최대치가 되면 발생합니다.

        for(i in 1..2) {
            circuitBreakerService.svc(i, false)
        }

        for(i in 1..7) {
            circuitBreakerService.svc(i, true)
        }

        log.info("==== 일반 반복 호출 종료 ====")
        circuitBreakerService.svc(100,false)
        log.info("==== 일반 반복 호출 종료 ====")

        try {

            log.info("========================= test holding started =========================")
            Thread.sleep(2000)
            log.info("========================= test holding finished =========================")
        } catch(e: Exception) {
            log.error(e.message)
        }


        for(i in 20..30) {
            circuitBreakerService.svc(i,false)
        }

    }

    @Test
    @DisplayName("slow_rate 테스트")
    fun slowRate() {

        // config에 설정한 slowCallRateThreshold 비율을 초과하면 'SLOW_CALL_RATE_EXCEEDED'이 발생합니다.
        // slowCallRateThreshold = [failure] / [slidingWindowSize]
        // 단, minimumNumberOfCalls 를 만족하지 않으면 'SLOW_CALL_RATE_EXCEEDED'가 발생하지 않으나 slidingWindowSize가 최대치가 되면 발생합니다.

        for(i in 1..10) {
            circuitBreakerService.svc(i, false, 500)
        }
        for(i in 1..5) {
            circuitBreakerService.svc(i, true)
        }
    }

}