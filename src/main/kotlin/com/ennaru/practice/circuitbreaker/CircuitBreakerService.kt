package com.ennaru.practice.circuitbreaker

import com.ennaru.practice.circuitbreaker.config.CircuitBreakerConfiguration
import com.ennaru.practice.common.vo.Response
import com.ennaru.practice.common.vo.logger
import com.ennaru.practice.kafka.KafkaConfiguration
import com.ennaru.practice.kafka.KafkaService
import io.github.resilience4j.circuitbreaker.CallNotPermittedException
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class CircuitBreakerService {

    @CircuitBreaker(name = CircuitBreakerConfiguration.DEFAULT_CIRCUIT, fallbackMethod = "fallback")
    fun svc(counter: Int, fallbackYn: Boolean, wait: Long = 0L): Response {
        if(wait != 0L) {
            Thread.sleep(wait)
        }
        if(fallbackYn) {
            throw RuntimeException("임의로 발생시키는 fallback")
        }
        return Response()
    }

    private fun fallback(e: CallNotPermittedException): Response {
        return Response("2", "CircuitBreaker에 의해 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.")
    }

    private fun fallback(e: RuntimeException): Response {
        return Response("1", "강제로 발생한 오류입니다.")
    }

}