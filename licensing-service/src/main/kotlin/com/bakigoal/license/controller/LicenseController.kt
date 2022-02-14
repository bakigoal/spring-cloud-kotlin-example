package com.bakigoal.license.controller

import com.bakigoal.license.model.License
import com.bakigoal.license.service.LicenseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("v1/organization/{organizationId}/license")
class LicenseController(@Autowired val licenseService: LicenseService) {

    @GetMapping("/{licenseId}")
    fun getLicense(
        @PathVariable("organizationId") organizationId: String,
        @PathVariable("licenseId") licenseId: String
    ): ResponseEntity<License> {
        val license = licenseService.getLicense(licenseId, organizationId)
        addHateoasLinks(license, organizationId)
        return ResponseEntity.ok(license)
    }

    @PostMapping
    fun createLicense(
        @PathVariable("organizationId") organizationId: String,
        @RequestBody license: License,
        @RequestHeader(value = "Accept-Language", required = false) locale: Locale?
    ): ResponseEntity<String> {
        return ResponseEntity.ok(licenseService.createLicense(license, organizationId, locale))
    }

    @PutMapping
    fun updateLicense(
        @PathVariable("organizationId") organizationId: String,
        @RequestBody license: License,
        @RequestHeader(value = "Accept-Language", required = false) locale: Locale?
    ): ResponseEntity<String> {
        return ResponseEntity.ok(licenseService.updateLicense(license, organizationId, locale))
    }

    @DeleteMapping("/{licenseId}")
    fun deleteLicense(
        @PathVariable("organizationId") organizationId: String,
        @PathVariable("licenseId") licenseId: String,
        @RequestHeader(value = "Accept-Language", required = false) locale: Locale?
    ): ResponseEntity<String> {
        return ResponseEntity.ok(licenseService.deleteLicense(licenseId, organizationId, locale))
    }

    private fun addHateoasLinks(license: License, organizationId: String) {
        license.add(
            linkTo(
                methodOn(LicenseController::class.java)
                    .getLicense(organizationId, license.licenseId!!)
            ).withSelfRel(),
            linkTo(
                methodOn(LicenseController::class.java)
                    .createLicense(organizationId, license, null)
            ).withRel("createLicense"),
            linkTo(
                methodOn(LicenseController::class.java)
                    .updateLicense(organizationId, license, null)
            ).withRel("updateLicense"),
            linkTo(
                methodOn(LicenseController::class.java)
                    .deleteLicense(organizationId, license.licenseId!!, null)
            ).withRel("deleteLicense")
        )
    }
}