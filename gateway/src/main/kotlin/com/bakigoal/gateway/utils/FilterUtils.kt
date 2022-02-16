package com.bakigoal.gateway.utils

import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import java.util.*

@Component
class FilterUtils {

    companion object{
        const val CORRELATION_ID = "tmx-correlation-id"
        const val AUTH_TOKEN ="Authorization"
    }

    fun getCorrelationId(headers: HttpHeaders): Optional<String> = getRequestHeader(headers, CORRELATION_ID)

    fun getAuthToken(headers: HttpHeaders): Optional<String> = getRequestHeader(headers, AUTH_TOKEN)

    fun getRequestHeader(headers: HttpHeaders, name: String): Optional<String> =
        Optional.ofNullable(headers[name]).flatMap { it.stream().findFirst() }

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
