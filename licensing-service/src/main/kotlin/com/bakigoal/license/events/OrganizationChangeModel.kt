package com.bakigoal.license.events

data class OrganizationChangeModel(
    val type: String,
    val action: String,
    val organizationId: String,
    val correlationId: String?
)