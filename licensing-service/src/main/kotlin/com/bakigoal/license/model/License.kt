package com.bakigoal.license.model

data class License(
    val id: Int? = null,
    val licenseId: String? = null,
    var organizationId: String? = null,
    var description: String? = null,
    var productName: String? = null,
    var licenseType: String? = null,
)