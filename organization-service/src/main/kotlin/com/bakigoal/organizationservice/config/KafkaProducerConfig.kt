package com.bakigoal.organizationservice.config

import com.bakigoal.organizationservice.events.OrganizationChangeModel
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer

@Configuration
class KafkaProducerConfig {

    @Value(value = "\${kafka.bootstrapAddress}")
    private val kafkaServer: String? = null

    @Bean
    fun producerFactory() = DefaultKafkaProducerFactory<String, OrganizationChangeModel>(senderProps())

    fun senderProps() = mapOf(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaServer,
        ProducerConfig.LINGER_MS_CONFIG to 10,
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java
    )

    @Bean
    fun kafkaTemplate(producerFactory: ProducerFactory<String, OrganizationChangeModel>) =
        KafkaTemplate(producerFactory)
}