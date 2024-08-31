package com.ennaru.practice

import com.ennaru.practice.common.vo.logger
import com.ennaru.practice.redis.config.RedisService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*
import kotlin.test.Test

@SpringBootTest
class RedisTest @Autowired constructor(
    val redisService: RedisService
) {

    val log = logger()

    @Test
    @DisplayName("Redis 기초 테스트")
    fun saveTest() {

        val key = "redis_test_" + UUID.randomUUID().toString()

        // redis set
        redisService.set(key, "metric")

        // redis get
        val value = redisService.get(key)
        valueCheck(value)

        // redis remove
        redisService.remove(key)

        // redis check
        val value2 = redisService.get(key)
        valueCheck(value2)

    }

    fun valueCheck(value: Any?) {
        if(value == null) {
            log.info("value is null")
        } else {
            log.info(value.toString())
        }
    }

}