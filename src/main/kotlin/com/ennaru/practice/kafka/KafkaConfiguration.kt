package com.ennaru.practice.kafka

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
class KafkaConfiguration {

    @Value("\${kafka.host}")
    lateinit var host: String

    @Value("\${kafka.port}")
    lateinit var port: String

    @Bean
    fun producerFactory() : ProducerFactory<String, Any> {
        return DefaultKafkaProducerFactory(mapOf<String, Any>(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to "http://${host}:${port}",
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java
        ))
    }

    @Bean
    fun kafkaTemplate() : KafkaTemplate<String, Any> = KafkaTemplate<String, Any>(producerFactory())

}