package com.bakigoal.organizationservice.config

import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Source
import org.springframework.context.annotation.Configuration

@Configuration
@EnableBinding(Source::class)
class KafkaStreamConfig {
}