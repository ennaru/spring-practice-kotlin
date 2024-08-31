package com.ennaru.practice.kafka

import com.ennaru.practice.common.vo.logger
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerEvent
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnFailureRateExceededEvent
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnStateTransitionEvent
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaService(
    private val kafkaTemplate: KafkaTemplate<String, Any>,
) {

    @Value("\${kafka.send}")
    lateinit var sendYn: String

    val log = logger()

    companion object {
        const val EVENT_TOPIC: String = "event-topic"
        const val CIRCUIT_BREAKER: String = "circuit-breaker"
    }

    fun kafkaMessageTemplate(map: Map<String, String>): String {
        return Json.encodeToString(KafkaVO(CIRCUIT_BREAKER, map))
    }

    fun circuitBreakerEvent(event: CircuitBreakerEvent) {
        val map = mutableMapOf<String, String>(
            "circuit-breaker-name" to event.circuitBreakerName,
            "circuit-breaker-cause" to event.javaClass.simpleName
        )

        if(event is CircuitBreakerOnStateTransitionEvent) {
            map["circuit-breaker-transition"] = event.stateTransition.toString()
        }

        try {
            // kafka와 통신이 되지 않을 때를 대비한 임시코딩
            if("false" != sendYn) {
                kafkaTemplate.send(EVENT_TOPIC, kafkaMessageTemplate(map))
            } else {
                log.info(kafkaMessageTemplate(map))
            }
        } catch(e: Exception) {

        }
    }

}