package com.ennaru.practice.kafka

import io.github.resilience4j.circuitbreaker.event.CircuitBreakerEvent
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnFailureRateExceededEvent
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnStateTransitionEvent
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaService(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) {

    companion object {
        const val EVENT_TOPIC: String = "event-topic"
        const val CIRCUIT_BREAKER: String = "CIRCUIT_BREAKER"
    }

    fun kafkaMessageTemplate(map: Map<String, String>): String {
        return Json.encodeToString(KafkaVO("circuit-breaker", map))
    }

    fun circuitBreakerEvent(event: CircuitBreakerEvent) {
        val map = mapOf<String, String>(
            "circuit-breaker-name" to event.circuitBreakerName,
            "circuit-breaker-cause" to event.javaClass.simpleName
        )
        try {
            kafkaTemplate.send(EVENT_TOPIC, kafkaMessageTemplate(map))
        } catch(e: Exception) {

        }
    }

}