package com.ennaru.practice

import com.ennaru.practice.common.vo.logger
import com.ennaru.practice.redis.RedisService
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import kotlin.test.Test


@SpringBootTest
@DisplayName("Redis Test")
class RedisTest @Autowired constructor(
    val redisService: RedisService
) {

    val log = logger()

    @Test
    @DisplayName("Redis 기초 테스트 : set, get and remove")
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

    @Test
    @DisplayName("redis timeout 테스트")
    fun timeoutTest() {

        val key = "redis_test_" + UUID.randomUUID().toString()

        // expire_time 설정
        redisService.set(key, "value", 2L)

        log.info("expire_second: ${redisService.getExpire(key)}")

        // value check
        valueCheck(redisService.get(key))

        // 4초 holding
        Thread.sleep(4000)

        // expire_time 이후 value 확인
        valueCheck(redisService.get(key))
    }


    fun valueCheck(value: Any?) {
        if(value == null) {
            log.info("value is null")
        } else {
            log.info(value.toString())
        }
    }

    @Test
    @DisplayName("Lettuce를 활용한 lock test")
    fun lockTest() {

        // ExecutorService 생성
        val executorService = Executors.newFixedThreadPool(32)
        val latch = CountDownLatch(80)

        // 공유 메모리 값 설정
        redisService.set("stock", "80", 30L)

        for(i in 1..80) {
            executorService.submit {
                try {
                    lockTest_decrease("stock")
                } catch (e: InterruptedException) {
                    throw RuntimeException(e)
                } finally {
                    latch.countDown()
                }
            }
        }
        latch.await()

        valueCheck(redisService.get("stock"))
    }

    /**
     * lettuce로 구현하는 redis_lock
     * 1. 자원에 접근하기 전에 lock을 먼저 걸음
     * 2. thread 분기
     *  2-1. lock을 건 thread
     *      트랜잭션 처리 후 lock 잠금 해지
     *  2-2. 그 외의 thread
     *      lock을 제어하는 key에 접근하여 lock 여부 확인
     */
    private fun lockTest_decrease(key: String) {

        log.info(Thread.currentThread().name)

        // holding
        while(redisService.setLock(key) != true) {
            Thread.sleep(10)
        }

        val stock = redisService.get("stock")
        if(stock != null && stock is String) {
            val decreased = stock.toInt() - 1
            redisService.set("stock", decreased.toString())
        }

        redisService.releaseLock(key)
    }

}