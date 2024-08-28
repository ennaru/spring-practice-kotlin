package com.ennaru.practice.circuitbreaker

import com.ennaru.practice.circuitbreaker.config.CircuitBreakerConfiguration
import io.github.resilience4j.circuitbreaker.CallNotPermittedException
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.springframework.stereotype.Service

@Service
class CircuitBreakerService {

    @CircuitBreaker(name = CircuitBreakerConfiguration.DEFAULT_CIRCUIT, fallbackMethod = "fallback")
    fun svc(fallbackYn: Boolean) {
        if(fallbackYn) {
            throw RuntimeException("임의로 발생시키는 fallback")
        }
        println("[log] succeed.")
    }

    fun fallback(e: CallNotPermittedException) {
        println("[log] circuit-break opened.")
    }

    fun fallback(e: RuntimeException) {
        println("[log] failed _ with RuntimeException")
    }

}