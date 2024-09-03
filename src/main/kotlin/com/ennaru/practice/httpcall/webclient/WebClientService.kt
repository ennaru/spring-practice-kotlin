package com.ennaru.practice.httpcall.webclient

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class WebClientService {

    private val LOCAL_HOST = "http://127.0.0.1:8080"

    fun getTest() {
        val client = WebClient.builder()
            .baseUrl(LOCAL_HOST)
            .build()

        client.get().uri("/health").retrieve().bodyToMono(String::class.java)
    }
}