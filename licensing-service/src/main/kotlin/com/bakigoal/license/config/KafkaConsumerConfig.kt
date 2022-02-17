package com.bakigoal.license.config

import com.bakigoal.license.events.OrganizationChangeModel
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer


@Configuration
@EnableKafka
class KafkaConsumerConfig {

    @Value(value = "\${kafka.bootstrapAddress}")
    private val kafkaServer: String? = null

    @Value(value = "\${kafka.consumer.groupid}")
    private val kafkaGroupId: String? = null

    @Bean
    fun kafkaListenerContainerFactory(consumerFactory: ConsumerFactory<String, OrganizationChangeModel>) =
        ConcurrentKafkaListenerContainerFactory<String, OrganizationChangeModel>().also {
            it.consumerFactory = consumerFactory
        }

    @Bean
    fun consumerFactory(): DefaultKafkaConsumerFactory<String, OrganizationChangeModel> {
        val jsonDeserializer = JsonDeserializer(OrganizationChangeModel::class.java)
        jsonDeserializer.setRemoveTypeHeaders(false);
        jsonDeserializer.addTrustedPackages("*");
        jsonDeserializer.setUseTypeMapperForKey(true);

        return DefaultKafkaConsumerFactory(
            consumerProps(),
            StringDeserializer(),
            jsonDeserializer
        )
    }

    fun consumerProps() = mapOf(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaServer,
        ConsumerConfig.GROUP_ID_CONFIG to kafkaGroupId,
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest"
    )
}