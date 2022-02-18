package com.bakigoal.gateway.filters

import com.bakigoal.gateway.utils.FilterUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.cloud.sleuth.Tracer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Mono

@Configuration
class ResponseFilter(
    @Autowired val tracer: Tracer
) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(ResponseFilter::class.java)
    }

    @Bean
    fun postGlobalFilter(): GlobalFilter {
        return GlobalFilter { exchange, chain ->

            val traceId = tracer.currentSpan()?.context()?.traceId()

            chain.filter(exchange).then(Mono.fromRunnable {
                logger.debug("Added the correlation id to the outbound headers. $traceId")
                exchange.response.headers.add(FilterUtils.CORRELATION_ID, traceId)
                logger.debug("Completing outgoing request for ${exchange.request.uri}.")
            })
        }
    }
}