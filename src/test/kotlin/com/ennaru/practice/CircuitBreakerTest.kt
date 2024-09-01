package com.ennaru.practice

import com.ennaru.practice.circuitbreaker.CircuitBreakerService
import com.ennaru.practice.common.vo.logger
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@DisplayName("CircuitBreaker 테스트")
class CircuitBreakerTest @Autowired constructor(
    val circuitBreakerService: CircuitBreakerService
) {

    val log = logger()

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
    @DisplayName("failure_rate 테스트")
    fun failureRate() {

        for(i in 1..15) {
            circuitBreakerService.svc(i, true)
        }

        try {
            log.info("====test holding started====")
            Thread.sleep(2000)
            log.info("====test holding finished====")
        } catch(e: Exception) {
            log.error(e.message)
        }


        for(i in 20..30) {
            circuitBreakerService.svc(i,false)
        }

    }

    @Test
    @DisplayName("slow_rate 테스트")
    fun slowRate() {
        for(i in 1..10) {
            circuitBreakerService.svc(i, false, 500)
        }
        for(i in 1..5) {
            circuitBreakerService.svc(i, true)
        }
    }

}