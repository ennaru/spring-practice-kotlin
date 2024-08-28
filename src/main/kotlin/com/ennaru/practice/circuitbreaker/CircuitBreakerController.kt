package com.ennaru.practice.circuitbreaker

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CircuitBreakerController(
    val circuitBreakerService: CircuitBreakerService
) {

    @RequestMapping("/api/v1/call")
    fun call() : String {
        circuitBreakerService.svc(false)
        return "{ 'result' : 'ok' }"
    }

    @RequestMapping("/api/v1/call2")
    fun call2() : String {
        circuitBreakerService.svc(true)
        return "{ 'result' : 'ok' }"
    }

}