package com.ennaru.practice.kotlin

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Pre-condition 테스트")
class PreconditionFuncTest {

    // pre-condition 함수는 조건식 확인하여
    // IllegalArgumentException / IllegalStateException을 던집니다.

    @DisplayName("require() 함수")
    @Test
    fun requireTest() {
        val limit = 10

        // require() 함수는 조건식이 false라면 IllegalArgumentException을 반환시킵니다.
        // lambda 안에 string을 던지면 Exception의 Message가 됩니다.
        require(limit > 11) {
            print(limit)
            "limit exceeded 11"
        }
    }

    @DisplayName("require() 함수")
    @Test
    fun requireTest2() {
        val limit = 10

        // require() 함수는 조건식이 false라면 IllegalArgumentException을 반환시킵니다.
        // lambda 안에 string을 던지면 Exception의 Message가 됩니다.
        require(limit > 11, { "illegal" })
    }

    @DisplayName("require() 함수")
    @Test
    fun requireTest3() {
        val limit = 10

        // require() 함수는 조건식이 false라면 IllegalArgumentException을 반환시킵니다.
        // lambda 안에 string을 던지면 Exception의 Message가 됩니다.
        require(limit > 11, fun() {
            println("return")
        })
    }

    @DisplayName("check() 함수")
    @Test
    fun checkTest() {
        val booleans = false
        // check() 함수는 조건식이 false이면 IllegalStateException이 반환됩니다.
        check(booleans, fun() {
            print(booleans)
        })
    }

    @DisplayName("error() 함수")
    @Test
    fun errorTest() {
        // error() 함수는 IllegalStateException을 반환시킵니다.
        error("status 에러 발생")

        // 따라서 이 위치에는 도달할 수 없습니다.
        println("함수")
    }

}