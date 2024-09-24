package com.ennaru.practice.kotlin

import org.junit.jupiter.api.Test

class LambdaTest {

    @Test
    fun testLambda() = baseLogging {
        println("aop 패턴으로 써봅니다.")
    }

    @Test
    fun testLambda2() {
        baseLogging(fun() {
            println("patterns")
        })
    }

    @Test
    fun testLambdaWithReturnValue() {
        println(withReturn())
    }

    private fun withReturn(): String = logging<String> {
        println("logging-inner function")
        return@logging "high"
    }

    /**
     * baseLogging without Return Value
     */
    private fun baseLogging(function: () -> Unit) {
        println("start : ")
        val result = function.invoke()
        println("end : ")
        return result
    }

    /**
     * logging with Return value
     */
    private fun <T> logging(function: () -> T): T {
        println("start : ")
        val result = function.invoke()
        println("end : ")
        return result
    }
}