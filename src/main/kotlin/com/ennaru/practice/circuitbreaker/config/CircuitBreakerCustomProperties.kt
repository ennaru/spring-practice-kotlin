package com.ennaru.practice.circuitbreaker.config

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties(prefix = "resilience4j.circuitbreaker.configs.custom")
data class CircuitBreakerCustomProperties (
    val failureRateThreshold : Float,
    val slowCallDurationThreshold: Duration,
    val slowCallRateThreshold: Float,
    val waitDurationInOpenState: Duration,
    val slidingWindowSize: Int
)