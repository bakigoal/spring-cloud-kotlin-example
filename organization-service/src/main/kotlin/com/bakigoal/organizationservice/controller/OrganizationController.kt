package com.bakigoal.organizationservice.controller

import com.bakigoal.organizationservice.model.Organization
import com.bakigoal.organizationservice.service.OrganizationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/organization/{organizationId}")
class OrganizationController(@Autowired val organizationService: OrganizationService) {

    @GetMapping
    fun getOrganization(
        @PathVariable("organizationId") organizationId: String
    ): ResponseEntity<Organization> {
        return ResponseEntity.ok(organizationService.getOrganization(organizationId))
    }
}