package com.ennaru.practice.circuitbreaker.config

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CircuitBreakerConfiguration(
    val customProperties: CircuitBreakerCustomProperties,
    val consumer: CircuitBreakerConsumer
){
    companion object {
        const val DEFAULT_CIRCUIT: String = "DEFAULT_CIRCUIT"
    }

    @Bean
    fun customCircuitBreakerRegistry(circuitBreakerConsumer: CircuitBreakerConsumer) : CircuitBreakerRegistry =
        CircuitBreakerRegistry.of(CircuitBreakerConfig
            .custom()
            .failureRateThreshold(customProperties.failureRateThreshold)                 // @CircuitBreaker 실패 임계값, 임계값보다 크면 Circuit state=[OPEN]으로 변경합니다.
            .slowCallDurationThreshold(customProperties.slowCallDurationThreshold)       // @CircuitBreaker 어노테이션이 붙은 함수 실행 시 설정된 시간보다 느려지면 [slow_call] 로 판단
            .slowCallRateThreshold(customProperties.slowCallRateThreshold)               // [slow_call] 비중
            .waitDurationInOpenState(customProperties.waitDurationInOpenState)           // STATE=[OPEN]->[HALF_OPEN] 전환 전 대기시간
            .slidingWindowSize(customProperties.slidingWindowSize)
            .build(), consumer)
//
//    @Bean
//    fun customCircuitBreaker(customCircuitBreakerRegistry: CircuitBreakerRegistry) : CircuitBreaker =
//        customCircuitBreakerRegistry
//            .circuitBreaker(DEFAULT_CIRCUIT, CircuitBreakerConfig
//                .custom()
//                .failureRateThreshold(customProperties.failureRateThreshold)                 // @CircuitBreaker 실패 임계값, 임계값보다 크면 Circuit state=[OPEN]으로 변경합니다.
//                .slowCallDurationThreshold(customProperties.slowCallDurationThreshold)       // @CircuitBreaker 어노테이션이 붙은 함수 실행 시 설정된 시간보다 느려지면 [slow_call] 로 판단
//                .slowCallRateThreshold(customProperties.slowCallRateThreshold)               // [slow_call] 비중
//                .waitDurationInOpenState(customProperties.waitDurationInOpenState)           // STATE=[OPEN]->[HALF_OPEN] 전환 전 대기시간
//                .slidingWindowSize(customProperties.slidingWindowSize)
//                .build()
//            )
}