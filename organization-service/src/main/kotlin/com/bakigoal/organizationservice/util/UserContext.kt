package com.bakigoal.organizationservice.util

import org.springframework.stereotype.Component

@Component
class UserContext {

    companion object {
        const val CORRELATION_ID = "tmx-correlation-id"
        const val AUTH_TOKEN = "Authorization"
        const val USER_ID = "tmx-user-id"
        const val ORGANIZATION_ID = "tmx-org-id"
    }

    var correlationId: String? = null
    var authToken: String? = null
    var userId: String? = null
    var organizationId: String? = null
}