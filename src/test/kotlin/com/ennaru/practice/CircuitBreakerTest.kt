package com.ennaru.practice

import com.ennaru.practice.circuitbreaker.CircuitBreakerService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CircuitBreakerTest() {

    @Autowired
    val circuitBreakerService: CircuitBreakerService = CircuitBreakerService()

    @BeforeEach
    fun before() = println("circuitBreak TestBefore")

    @AfterEach
    fun after() = println("circuitBreak TestAfter")

    @Test
    @DisplayName("성공")
    fun circuitBreaker() {
        circuitBreakerService.svc(false)
    }

    @Test
    @DisplayName("fallback 호출 시 성공")
    fun circuitBreak_fallback() {

        for(i in 1..15) {
            circuitBreakerService.svc(true)
        }

        Thread.sleep(1200)

        for(i in 1..5) {
            circuitBreakerService.svc(false)
        }

    }


}