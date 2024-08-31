package com.ennaru.practice.redis.config

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class RedisService(
    val redisTemplate: RedisTemplate<String, Any>
) {

    fun set(key: String, obj: Any) {
        redisTemplate.opsForValue().set(key, obj)
    }

    fun get(key: String): Any? = redisTemplate.opsForValue().get(key)

    fun remove(key: String) = redisTemplate.delete(key)

}