package com.ennaru.practice.circuitbreaker

import com.ennaru.practice.circuitbreaker.config.CircuitBreakerConfiguration
import com.ennaru.practice.kafka.KafkaConfiguration
import com.ennaru.practice.kafka.KafkaService
import io.github.resilience4j.circuitbreaker.CallNotPermittedException
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class CircuitBreakerService(
    val kafkaService: KafkaService
) {

    @CircuitBreaker(name = CircuitBreakerConfiguration.DEFAULT_CIRCUIT, fallbackMethod = "fallback")
    fun svc(counter: Int, fallbackYn: Boolean) {
        if(fallbackYn) {
            throw RuntimeException("임의로 발생시키는 fallback")
        }
        println("[log] ${counter} succeed.")
    }

    fun fallback(e: CallNotPermittedException) {
        println("[log] circuit-break opened.")
        kafkaService.circuitBreaker(e)
    }

    fun fallback(e: RuntimeException) {
        println("[log] failed _ with RuntimeException")
        kafkaService.circuitBreaker(e)
    }

}