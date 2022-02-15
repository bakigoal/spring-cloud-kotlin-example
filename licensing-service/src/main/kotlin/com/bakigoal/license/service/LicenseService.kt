package com.bakigoal.license.service

import com.bakigoal.license.config.ServiceConfig
import com.bakigoal.license.model.License
import com.bakigoal.license.repository.LicenseRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.stereotype.Service
import java.util.*
import java.util.UUID

@Service
class LicenseService(
    @Autowired val messages: MessageSource,
    @Autowired val licenseRepository: LicenseRepository,
    @Autowired val serviceConfig: ServiceConfig
) {

    fun getLicense(licenseId: String, organizationId: String) =
        licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId)

    fun createLicense(license: License): License {
        license.licenseId = UUID.randomUUID().toString()
        licenseRepository.save(license)
        return license.apply {
            this.comment = serviceConfig.property
        }
    }

    fun updateLicense(license: License): License {
        licenseRepository.save(license)
        return license.apply {
            this.comment = serviceConfig.property
        }
    }

    fun deleteLicense(licenseId: String?, locale: Locale?): String {
        val license = License()
        license.licenseId = licenseId
        licenseRepository.delete(license)
        return String.format(
            messages.getMessage("license.delete.message", null, locale ?: Locale.US),
            licenseId
        )
    }
}