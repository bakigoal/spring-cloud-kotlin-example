package com.bakigoal.license.config

import com.bakigoal.license.util.UserContextInterceptor
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class LoadBalancedRestTemplateConfig {

    @Bean
    @LoadBalanced
    fun restTemplate(): RestTemplate {
        val restTemplate = RestTemplate()
        restTemplate.interceptors.add(UserContextInterceptor())
        return restTemplate
    }
}