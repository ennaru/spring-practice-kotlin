package com.ennaru.practice.kafka

import kotlinx.serialization.Serializable

@Serializable
data class KafkaVO (
    val type: String,
    val data: Map<String, String>
)