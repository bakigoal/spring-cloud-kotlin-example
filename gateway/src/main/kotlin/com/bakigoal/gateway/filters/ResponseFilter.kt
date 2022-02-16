package com.bakigoal.gateway.filters

import com.bakigoal.gateway.utils.FilterUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Mono

@Configuration
class ResponseFilter(
    @Autowired val filterUtils: FilterUtils
) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(ResponseFilter::class.java)
    }

    @Bean
    fun postGlobalFilter(): GlobalFilter = GlobalFilter { exchange, chain ->
        chain.filter(exchange).then(Mono.fromRunnable {
            val headers = exchange.request.headers
            val correlationId = filterUtils.getCorrelationId(headers)
            logger.debug("Added the correlation id to the outbound headers. ${correlationId.get()}")
            exchange.response.headers.add(FilterUtils.CORRELATION_ID, correlationId.get())
            logger.debug("Completing outgoing request for ${exchange.request.uri}.")
        })
    }
}