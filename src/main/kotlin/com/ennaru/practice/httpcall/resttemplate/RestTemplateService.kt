package com.ennaru.practice.httpcall.resttemplate

import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

/**
 * RestTemplate 예시입니다.
 * Maintenance 단계라 사용을 권장하지 않습니다.
 */
@Service
class RestTemplateService {

    private val LOCAL_HOST = "http://127.0.0.1:8080"

    fun getTest() {
        val restTemplate = RestTemplate()

        println(restTemplate.getForObject("$LOCAL_HOST/health", String::class.java))
    }

    fun getTest(parameters : Map<String, String>) {
        val restTemplate = RestTemplate()
        println(restTemplate.getForObject("$LOCAL_HOST/health", String::class.java, parameters))
    }

    fun postTest() {

    }


}