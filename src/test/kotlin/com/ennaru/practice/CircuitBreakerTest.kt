package com.ennaru.practice

import com.ennaru.practice.circuitbreaker.CircuitBreakerService
import com.ennaru.practice.common.vo.logger
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.core.KafkaTemplate

@SpringBootTest
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
    @DisplayName("fallback 호출 시 성공")
    fun circuitBreak_fallback() {

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


}