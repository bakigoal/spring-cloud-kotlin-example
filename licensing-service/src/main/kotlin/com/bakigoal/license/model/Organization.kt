package com.bakigoal.license.model

import org.springframework.data.redis.core.RedisHash
import javax.persistence.Id

@RedisHash("organization")
data class Organization(
    @Id val id: String? = null,
    var name: String? = null,
    var contactName: String? = null,
    var contactEmail: String? = null,
    var contactPhone: String? = null
)