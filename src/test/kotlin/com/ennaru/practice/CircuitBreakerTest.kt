package com.ennaru.practice

import com.ennaru.practice.circuitbreaker.CircuitBreakerService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.core.KafkaTemplate

@SpringBootTest
class CircuitBreakerTest(
) {

    @Autowired
    //val circuitBreakerService: CircuitBreakerService = CircuitBreakerService(@Autowired val )

    @BeforeEach
    fun before() = println("circuitBreak TestBefore")

    @AfterEach
    fun after() = println("circuitBreak TestAfter")

    @Test
    @DisplayName("성공")
    fun circuitBreaker() {
        circuitBreakerService.svc(0,false)
    }

    @Test
    @DisplayName("fallback 호출 시 성공")
    fun circuitBreak_fallback() {

        for(i in 1..15) {
            circuitBreakerService.svc(i, true)
        }

        Thread.sleep(1200)

        for(i in 20..30) {
            circuitBreakerService.svc(i,false)
        }

    }


}