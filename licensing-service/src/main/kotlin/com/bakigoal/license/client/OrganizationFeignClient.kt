package com.bakigoal.license.client

import com.bakigoal.license.dto.OrganizationDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient("organization-service")
interface OrganizationFeignClient {

    @GetMapping
    fun getOrganization(
        @PathVariable("organizationId") organizationId: String
    ): OrganizationDto
}