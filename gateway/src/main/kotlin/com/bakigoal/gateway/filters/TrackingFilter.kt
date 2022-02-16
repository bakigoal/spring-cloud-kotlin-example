package com.bakigoal.gateway.filters

import com.bakigoal.gateway.utils.FilterUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.util.*

@Component
@Order(1)
class TrackingFilter(
    @Autowired val filterUtils: FilterUtils
) : GlobalFilter {

    private val logger: Logger = LoggerFactory.getLogger(TrackingFilter::class.java)

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        val headers = exchange.request.headers
        val correlationId = filterUtils.getCorrelationId(headers)
        if (correlationId.isPresent) {
            logger.debug("tmx-correlation-id found in tracking filter: ${correlationId.get()}")
            return chain.filter(exchange)
        }

        val generatedCorrelationId = UUID.randomUUID().toString()
        logger.debug("tmx-correlation-id generated in tracking filter: $generatedCorrelationId")
        return chain.filter(filterUtils.setCorrelationId(exchange, generatedCorrelationId))
    }
}