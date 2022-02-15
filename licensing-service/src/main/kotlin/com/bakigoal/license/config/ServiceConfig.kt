package com.bakigoal.license.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "example")
class ServiceConfig {
    var property: String = ""
}