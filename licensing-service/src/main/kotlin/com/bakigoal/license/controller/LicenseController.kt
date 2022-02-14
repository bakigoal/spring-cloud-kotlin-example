package com.bakigoal.license.controller

import com.bakigoal.license.model.License
import com.bakigoal.license.service.LicenseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("v1/organization/{organizationId}/license")
class LicenseController (@Autowired val licenseService: LicenseService) {

    @GetMapping("/{licenseId}")
    fun getLicense(
        @PathVariable("organizationId") organizationId: String,
        @PathVariable("licenseId") licenseId: String
    ): ResponseEntity<License> {
        return ResponseEntity.ok(licenseService.getLicense(licenseId, organizationId))
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

}