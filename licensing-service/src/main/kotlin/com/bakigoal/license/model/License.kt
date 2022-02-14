package com.bakigoal.license.model

import org.springframework.hateoas.RepresentationModel

data class License(
    var id: Int? = null,
    var licenseId: String? = null,
    var organizationId: String? = null,
    var description: String? = null,
    var productName: String? = null,
    var licenseType: String? = null
) : RepresentationModel<License>()