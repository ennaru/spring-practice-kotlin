package com.ennaru.practice.redis.config

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class RedisService(
    val redisTemplate: RedisTemplate<String, Any>
) {

    /**
     * Redis Set without timeout
     */
    fun set(key: String, obj: Any) {
        redisTemplate.opsForValue().set(key, obj)
    }

    /**
     * Redis Set with timeout
     */
    fun set(key: String, obj: Any, second: Long) {
        redisTemplate.opsForValue().set(key, obj)
        redisTemplate.expire(key, Duration.ofSeconds(second))
    }

    fun get(key: String): Any? = redisTemplate.opsForValue().get(key)

    fun getExpire(key: String): Long = redisTemplate.getExpire(key)

    fun remove(key: String) = redisTemplate.delete(key)

}