package com.bakigoal.license.mapper

import com.bakigoal.license.dto.LicenseDto
import com.bakigoal.license.model.License

fun License.toDto() =  LicenseDto(
    licenseId = this.licenseId,
    organizationId = this.organizationId,
    productName = this.productName,
    licenseType = this.licenseType,
    description = this.description
)

fun LicenseDto.toModel() = License(
    licenseId = this.licenseId,
    organizationId = this.organizationId,
    productName = this.productName,
    licenseType = this.licenseType,
    description = this.description
)