package com.ennaru.practice.health

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController {

    @GetMapping("/health")
    open fun healthCheck(): Map<String, String> = mapOf<String, String>("result" to "0")

}