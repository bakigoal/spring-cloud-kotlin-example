package com.bakigoal.license.model

import org.springframework.hateoas.RepresentationModel

class License : RepresentationModel<License>() {
    var id: Int? = null
    var licenseId: String? = null
    var organizationId: String? = null
    var description: String? = null
    var productName: String? = null
    var licenseType: String? = null
}