package com.bakigoal.organizationservice.util

import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse

class UserContextInterceptor : ClientHttpRequestInterceptor {

    override fun intercept(
        request: HttpRequest,
        body: ByteArray,
        execution: ClientHttpRequestExecution
    ): ClientHttpResponse {
        val headers = request.headers
        headers.add(UserContext.CORRELATION_ID, UserContextHolder.getContext().correlationId)
        headers.add(UserContext.AUTH_TOKEN, UserContextHolder.getContext().authToken)
        return execution.execute(request, body)
    }
}