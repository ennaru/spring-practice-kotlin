package com.ennaru.practice.circuitbreaker.config

import com.ennaru.practice.kafka.KafkaService
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.core.registry.EntryAddedEvent
import io.github.resilience4j.core.registry.EntryRemovedEvent
import io.github.resilience4j.core.registry.EntryReplacedEvent
import io.github.resilience4j.core.registry.RegistryEventConsumer
import org.springframework.context.annotation.Configuration

@Configuration
class CircuitBreakerConsumer(
    val kafkaService: KafkaService
) : RegistryEventConsumer<CircuitBreaker> {

    override fun onEntryAddedEvent(entryAddedEvent: EntryAddedEvent<CircuitBreaker>) {
        entryAddedEvent.addedEntry.eventPublisher
            /**
             * event [circuitBreakerName, creationTime]는 공통변수임
             */
            .onError { event ->
                /** CircuitBreakerOnErrorEvent : circuitBreaker 어노테이션이 붙은 함수에서 Exception 발생 시 호출
                 * - throwable
                 * - elapsedDuration
                 */
                kafkaService.circuitBreakerEvent(event)
            }
            .onFailureRateExceeded { event ->
                /** CircuitBreakerOnFailureRateExceededEvent circuitBreaker failureRate 초과 시 호출
                 * - failureRate
                 */
                kafkaService.circuitBreakerEvent(event)
            }
            .onSlowCallRateExceeded { event ->
                /** CircuitBreakerOnSlowCallRateExceededEvent circuitBreaker 어노테이션이 붙은 함수가 임계값 내에 종료되지 않을 때 호출
                 * - slowCallRate
                 */
                kafkaService.circuitBreakerEvent(event)
            }
            .onStateTransition { event ->
                /** CircuitBreakerOnStateTransitionEvent circuitBreaker transition 변경 시 호출
                 * - stateTransition
                 *  - fromState
                 *  - toState
                 *  - name
                 *  - ordinal
                 */
                kafkaService.circuitBreakerEvent(event)
            }
    }

    override fun onEntryRemovedEvent(entryRemoveEvent: EntryRemovedEvent<CircuitBreaker>) {
        TODO("Not yet implemented")
    }

    override fun onEntryReplacedEvent(entryReplacedEvent: EntryReplacedEvent<CircuitBreaker>) {
        TODO("Not yet implemented")
    }
}