package com.bakigoal.gateway.filters

import com.bakigoal.gateway.utils.FilterUtils
import org.json.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
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
        logger.debug("The authentication name from the token is ${getUsername(headers)}")

        val correlationId = filterUtils.getCorrelationId(headers)
        if (correlationId.isPresent) {
            logger.debug("tmx-correlation-id found in tracking filter: ${correlationId.get()}")
            return chain.filter(exchange)
        }

        val generatedCorrelationId = UUID.randomUUID().toString()
        logger.debug("tmx-correlation-id generated in tracking filter: $generatedCorrelationId")
        return chain.filter(filterUtils.setCorrelationId(exchange, generatedCorrelationId))
    }

    private fun getUsername(headers: HttpHeaders): String {
        var username = ""
        val auth = filterUtils.getAuthToken(headers)
        if (auth.isPresent) {
            val authToken = auth.get().replace("Bearer ", "")
            val json: JSONObject = decodeJwt(authToken)
            username = json.getString("preferred_username")
        }
        return username
    }

    private fun decodeJwt(token: String): JSONObject {
        val split = token.split(".")
        val base64EncodedBody = split[1]
        val body = String(Base64.getUrlDecoder().decode(base64EncodedBody))
        logger.debug("jwt decoded: $body")
        return JSONObject(body)
    }
}