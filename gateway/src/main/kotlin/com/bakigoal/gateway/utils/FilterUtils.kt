package com.bakigoal.gateway.utils

import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import java.util.*

const val CORRELATION_ID = "tmx-correlation-id"

@Component
class FilterUtils {

    fun getCorrelationId(headers: HttpHeaders): Optional<String> =
        Optional.ofNullable(headers[CORRELATION_ID])
            .flatMap { it.stream().findFirst() }

    fun setCorrelationId(exchange: ServerWebExchange, correlationId: String) =
        setRequestHeader(exchange, CORRELATION_ID, correlationId)

    fun setRequestHeader(exchange: ServerWebExchange, name: String, value: String): ServerWebExchange {
        val request = exchange.request.mutate()
            .header(name, value)
            .build()
        return exchange.mutate()
            .request(request)
            .build();
    }
}
