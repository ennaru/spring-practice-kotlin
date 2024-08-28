package com.ennaru.practice.circuitbreaker.config

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.core.registry.EntryAddedEvent
import io.github.resilience4j.core.registry.EntryRemovedEvent
import io.github.resilience4j.core.registry.EntryReplacedEvent
import io.github.resilience4j.core.registry.RegistryEventConsumer
import org.springframework.context.annotation.Configuration

@Configuration
class CircuitBreakerConsumer : RegistryEventConsumer<CircuitBreaker> {

    override fun onEntryAddedEvent(entryAddedEvent: EntryAddedEvent<CircuitBreaker>) {
        entryAddedEvent.addedEntry.eventPublisher
            .onFailureRateExceeded { event ->
                println("[failure_rate] ${event.circuitBreakerName}, ${event.failureRate}")

            }
            .onError { event ->
                println("[error] ${event.circuitBreakerName}")
            }
            .onStateTransition { event ->
                println("${event.circuitBreakerName}, ${event.stateTransition}")
            }
    }

    override fun onEntryRemovedEvent(entryRemoveEvent: EntryRemovedEvent<CircuitBreaker>) {
        TODO("Not yet implemented")
    }

    override fun onEntryReplacedEvent(entryReplacedEvent: EntryReplacedEvent<CircuitBreaker>) {
        TODO("Not yet implemented")
    }
}