package com.bakigoal.license.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

@Component
class UserContextFilter: Filter {

    companion object{
        val logger: Logger = LoggerFactory.getLogger(UserContextFilter::class.java)
    }

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpServletRequest = request as HttpServletRequest
        UserContextHolder.getContext().apply {
            correlationId = httpServletRequest.getHeader(UserContext.CORRELATION_ID)
            userId = httpServletRequest.getHeader(UserContext.USER_ID)
            authToken = httpServletRequest.getHeader(UserContext.AUTH_TOKEN)
            organizationId = httpServletRequest.getHeader(UserContext.ORGANIZATION_ID)
        }

        logger.debug("UserContextFilter Correlation id: {}", UserContextHolder.getContext().correlationId)

        chain.doFilter(httpServletRequest, response)
    }
}