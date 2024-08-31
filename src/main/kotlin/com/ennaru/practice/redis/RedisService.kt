package com.ennaru.practice.redis

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

    /**
     * setIfAbsent 를 가공한 LOCK
     * => redis에 값이 없으면 true, 값이 있으면 false
     */
    fun setLock(key: String): Boolean? {
        return redisTemplate.opsForValue().setIfAbsent(key+"LCK", "LOCK", Duration.ofSeconds(10L))
    }

    /**
     * setLock을 사용한 key release
     */
    fun releaseLock(key: String) {
        redisTemplate.delete(key+"LCK")
    }

}