package com.ennaru.practice

import com.ennaru.practice.httpcall.resttemplate.RestTemplateService
import com.ennaru.practice.httpcall.webclient.WebClientService
import org.aspectj.apache.bcel.classfile.ExceptionTable
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@DisplayName("Web 전송을 위한 클라이언트들")
class WebSendTest @Autowired constructor(
    val restTemplateService: RestTemplateService,
    val webClientService: WebClientService
) {

    @Test
    @DisplayName("restTemplate를 사용해본 테스트")
    fun restTemplate() {
        // parameter X 테스트
        try {
            restTemplateService.getTest()
        } catch (e: Exception) {
            println(e)
        }

        // parameter O 테스트
        val parameters = mapOf(
            "val1" to "test1",
            "val2" to "test2"
        )
        try {
            restTemplateService.getTest(parameters)
        } catch (e: Exception) {
            println(e)
        }


    }

    @Test
    @DisplayName("webClient를 사용한 테스트")
    fun webClientTest() {
        webClientService.getTest()
    }

}