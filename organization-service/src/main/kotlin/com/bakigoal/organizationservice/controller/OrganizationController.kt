package com.bakigoal.organizationservice.controller

import com.bakigoal.organizationservice.model.Organization
import com.bakigoal.organizationservice.service.OrganizationService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("v1/organization/{organizationId}")
class OrganizationController(@Autowired val organizationService: OrganizationService) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(OrganizationController::class.java)
    }

    @GetMapping
    fun getOrganization(
        @PathVariable("organizationId") organizationId: String,
        @RequestHeader headers: Map<String, String>
    ): ResponseEntity<Organization> {
        logger.info("received headers: $headers")
        return ResponseEntity.ok(organizationService.getOrganization(organizationId))
    }
}