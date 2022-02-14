package com.bakigoal.license.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import java.util.*

@Configuration
class LocalizationConfig {

    @Bean
    fun localeResolver(): LocaleResolver {
        val localeResolver = SessionLocaleResolver()
        localeResolver.setDefaultLocale(Locale.US)
        return localeResolver
    }

    @Bean
    fun messages(): ResourceBundleMessageSource {
        val messageSource = ResourceBundleMessageSource()
        messageSource.setUseCodeAsDefaultMessage(true)
        messageSource.setBasename("messages/messages")
        messageSource.setDefaultEncoding("UTF-8")
        return messageSource
    }
}