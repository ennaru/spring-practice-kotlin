package com.ennaru.practice

import com.ennaru.practice.common.vo.logger
import com.ennaru.practice.session.SessionUtils
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest
class SessionTest {

    val log = logger()

    @Test
    @DisplayName("세션 기초 테스트")
    fun sessionBasic() {

        // 적재
        SessionUtils.setAttribute("key", "value")

        // 로그 확인
        log.info(SessionUtils.getAttribute("key").toString())

    }

    // TestCode 에서 사용하는 Session Class는 MockHttpSession입니다.
    @Test
    @DisplayName("세션 메모리 테스트")
    fun sessionMemory() {

        // 세션의 attribute도 결국 Map에 담기 때문에 해지 시 자동으로 메모리에서 해지되지 않습니다.

        // ExecutorService 생성
        val count = 20000

        val executorService = Executors.newFixedThreadPool(32)
        val latch = CountDownLatch(count)

        log.info("==================Init======================")
        log.info("FREE_MEMORY: ${Runtime.getRuntime().freeMemory()}")
        log.info("USED_MEMORY: ${Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()}")

        // 메모리 설정
        for(i in 1..count) {
            executorService.submit {
                try {
                    SessionUtils.setAttribute(i.toString(), "session_memory_test")
                } catch (e: InterruptedException) {
                    throw RuntimeException(e)
                } finally {
                    latch.countDown()
                }
            }
        }
        latch.await()

        log.info("==================After Set======================")
        log.info("FREE_MEMORY: ${Runtime.getRuntime().freeMemory()}")
        log.info("USED_MEMORY: ${Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()}")

        // session remove
        // removeAttribute 는 [map]의 remove 함수를 호출하지만
        for(i in 1..count) {
            executorService.submit {
                try {
                    SessionUtils.removeAttribute(i.toString())
                } catch (e: InterruptedException) {
                    throw RuntimeException(e)
                } finally {
                    latch.countDown()
                }
            }
        }
        latch.await()

        log.info("==================After Remove======================")
        log.info("FREE_MEMORY: ${Runtime.getRuntime().freeMemory()}")
        log.info("USED_MEMORY: ${Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()}")

        // invalidate를 호출하면 clearAttributes() 를 호출하는데, attribute 들을 전부 해지시킴
        (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request.session?.invalidate()

        log.info("==================After Invalidate======================")
        log.info("FREE_MEMORY: ${Runtime.getRuntime().freeMemory()}")
        log.info("USED_MEMORY: ${Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()}")

    }

}