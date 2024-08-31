package com.ennaru.practice.circuitbreaker

import com.ennaru.practice.common.vo.Response
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CircuitBreakerController(
    val circuitBreakerService: CircuitBreakerService
) {

    @GetMapping("/circuit/call")
    fun call(): Response {
        return circuitBreakerService.svc(1, false)
    }

    @GetMapping("/circuit/call/fallback")
    fun call2(): Response {
        return circuitBreakerService.svc(1, true)
    }

}