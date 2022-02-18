package com.bakigoal.license.mapper

import com.bakigoal.license.dto.OrganizationDto
import com.bakigoal.license.model.Organization

fun Organization.toDto() = OrganizationDto(
    organizationId = this.id,
    name = this.name,
    contactName = this.contactName,
    contactEmail = this.contactEmail,
    contactPhone = this.contactPhone
)

fun OrganizationDto.toModel() = Organization(
    id = this.organizationId,
    name = this.name,
    contactName = this.contactName,
    contactEmail = this.contactEmail,
    contactPhone = this.contactPhone
)