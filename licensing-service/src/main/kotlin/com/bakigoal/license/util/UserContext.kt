package com.bakigoal.license.util

import org.springframework.stereotype.Component

@Component
class UserContext {

    companion object {
        const val CORRELATION_ID = "tmx-correlation-id"
        const val AUTH_TOKEN = "tmx-auth-token"
        const val USER_ID = "tmx-user-id"
        const val ORGANIZATION_ID = "tmx-organization-id"
    }

    var correlationId: String? = null
    var authToken: String? = null
    var userId: String? = null
    var organizationId: String? = null
}