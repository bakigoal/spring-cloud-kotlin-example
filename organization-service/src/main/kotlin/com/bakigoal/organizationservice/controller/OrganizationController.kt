package com.bakigoal.organizationservice.controller

import com.bakigoal.organizationservice.model.Organization
import com.bakigoal.organizationservice.service.OrganizationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.annotation.security.RolesAllowed

@RestController
@RequestMapping("v1/organization")
class OrganizationController(@Autowired val organizationService: OrganizationService) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(OrganizationController::class.java)
    }

    @RolesAllowed("ADMIN", "USER")
    @GetMapping("{organizationId}")
    @Operation(security = [SecurityRequirement(name = "bearerAuth")] )
    fun getOrganization(
        @PathVariable("organizationId") organizationId: String,
        @RequestHeader headers: Map<String, String>
    ): ResponseEntity<Organization> {
        logger.info("Organization Service received headers: $headers")
        return ResponseEntity.ok(organizationService.getOrganization(organizationId))
    }

    @RolesAllowed("ADMIN")
    @PostMapping
    @Operation(security = [SecurityRequirement(name = "bearerAuth")] )
    fun createOrganization(
        @RequestBody organization: Organization
    ): ResponseEntity<Organization> {
        return ResponseEntity.ok(organizationService.createOrganization(organization))
    }

    @RolesAllowed("ADMIN")
    @PutMapping("{organizationId}")
    @Operation(security = [SecurityRequirement(name = "bearerAuth")] )
    fun updateOrganization(
        @PathVariable("organizationId") organizationId: String,
        @RequestBody organization: Organization
    ): ResponseEntity<Organization> {
        organization.organizationId = organizationId
        return ResponseEntity.ok(organizationService.updateOrganization(organization))
    }


    @RolesAllowed("ADMIN")
    @DeleteMapping("{organizationId}")
    @Operation(security = [SecurityRequirement(name = "bearerAuth")] )
    fun deleteOrganization(
        @PathVariable("organizationId") organizationId: String
    ): ResponseEntity<Organization> {
        return ResponseEntity.ok(organizationService.deleteOrganization(organizationId))
    }

}