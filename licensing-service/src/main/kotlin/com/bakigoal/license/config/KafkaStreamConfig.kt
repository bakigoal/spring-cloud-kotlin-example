package com.bakigoal.license.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.messaging.Sink
import org.springframework.context.annotation.Configuration


@Configuration
@EnableBinding(Sink::class)
class KafkaStreamConfig {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(KafkaStreamConfig::class.java)
    }

    @StreamListener(Sink.INPUT)
    fun loggerSink(orgChange: OrganizationChangeModel) {
        logger.info("Received an ${orgChange.action} event for organization id ${orgChange.organizationId}")
    }
}

data class OrganizationChangeModel(
    val type: String,
    val action: String,
    val organizationId: String,
    val correlationId: String?
)