package com.ennaru.practice.kafka

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

    fun kafkaMessageTemplate(map: Map<String, String>): String = Json.encodeToString(map)

    fun circuitBreaker(e: Exception) {
        val map = mapOf<String, String>(
            "type" to CIRCUIT_BREAKER,
            "name" to e.javaClass.simpleName,
            "cause" to e.message.toString()
        )
        kafkaTemplate.send(EVENT_TOPIC, kafkaMessageTemplate(map))
    }

}