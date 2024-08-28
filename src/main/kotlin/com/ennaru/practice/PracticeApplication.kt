package com.ennaru.practice

import com.ennaru.practice.circuitbreaker.config.CircuitBreakerCustomProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@EnableConfigurationProperties(CircuitBreakerCustomProperties::class)
@SpringBootApplication
class PracticeApplication

fun main(args: Array<String>) {
	runApplication<PracticeApplication>(*args)
}
