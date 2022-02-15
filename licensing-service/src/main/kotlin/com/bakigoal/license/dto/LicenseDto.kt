package com.bakigoal.license.dto

import org.springframework.hateoas.RepresentationModel

data class LicenseDto(
    var licenseId: String? = null,
    var organizationId: String? = null,
    var productName: String? = null,
    var licenseType: String? = null,
    var description: String? = null,
    var comment: String? = null,
    var organizationName: String? = null,
    var contactName: String? = null,
    var contactEmail: String? = null,
    var contactPhone: String? = null
) : RepresentationModel<LicenseDto>()
