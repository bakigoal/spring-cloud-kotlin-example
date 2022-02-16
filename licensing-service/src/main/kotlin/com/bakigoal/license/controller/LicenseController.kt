package com.bakigoal.license.controller

import com.bakigoal.license.dto.LicenseDto
import com.bakigoal.license.service.LicenseService
import com.bakigoal.license.util.UserContextFilter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("v1/organization/{organizationId}/license")
class LicenseController(@Autowired val licenseService: LicenseService) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(LicenseController::class.java)
    }

    @GetMapping
    fun getLicenses(
        @PathVariable("organizationId") organizationId: String
    ): List<LicenseDto> {
        return licenseService.getLicensesByOrganization(organizationId)
    }

    @GetMapping("/{licenseId}/{clientType}")
    fun getLicenseWithClient(
        @PathVariable("organizationId") organizationId: String,
        @PathVariable("licenseId") licenseId: String,
        @PathVariable("clientType") clientType: String,
        @RequestHeader headers: Map<String, String>
    ): LicenseDto {
        logger.info("received headers: $headers")
        return licenseService.getLicense(licenseId, organizationId, clientType)
    }

    @GetMapping("/{licenseId}")
    fun getLicense(
        @PathVariable("organizationId") organizationId: String,
        @PathVariable("licenseId") licenseId: String
    ): ResponseEntity<LicenseDto> {
        val license = licenseService.getLicense(licenseId, organizationId)
        addHateoasLinks(license, organizationId)
        return ResponseEntity.ok(license)
    }

    @PostMapping
    fun createLicense(
        @PathVariable("organizationId") organizationId: String,
        @RequestBody license: LicenseDto
    ): ResponseEntity<LicenseDto> {
        return ResponseEntity.ok(licenseService.createLicense(license))
    }

    @PutMapping("/{licenseId}")
    fun updateLicense(
        @PathVariable("organizationId") organizationId: String,
        @PathVariable("licenseId") licenseId: String,
        @RequestBody license: LicenseDto
    ): ResponseEntity<LicenseDto> {
        license.licenseId = licenseId
        return ResponseEntity.ok(licenseService.updateLicense(license))
    }

    @DeleteMapping("/{licenseId}")
    fun deleteLicense(
        @PathVariable("organizationId") organizationId: String,
        @PathVariable("licenseId") licenseId: String,
        @RequestHeader(value = "Accept-Language", required = false) locale: Locale?
    ): ResponseEntity<String> {
        return ResponseEntity.ok(licenseService.deleteLicense(licenseId, locale))
    }

    private fun addHateoasLinks(license: LicenseDto, organizationId: String) {
        license.add(
            linkTo(
                methodOn(LicenseController::class.java)
                    .getLicense(organizationId, license.licenseId!!)
            ).withSelfRel(),
            linkTo(
                methodOn(LicenseController::class.java)
                    .createLicense(organizationId, license)
            ).withRel("createLicense"),
            linkTo(
                methodOn(LicenseController::class.java)
                    .updateLicense(organizationId, "", license)
            ).withRel("updateLicense"),
            linkTo(
                methodOn(LicenseController::class.java)
                    .deleteLicense(organizationId, license.licenseId!!, null)
            ).withRel("deleteLicense")
        )
    }
}