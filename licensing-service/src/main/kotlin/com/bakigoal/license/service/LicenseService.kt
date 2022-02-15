package com.bakigoal.license.service

import com.bakigoal.license.client.OrganizationDiscoveryClient
import com.bakigoal.license.config.ServiceConfig
import com.bakigoal.license.dto.LicenseDto
import com.bakigoal.license.dto.OrganizationDto
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
    @Autowired val serviceConfig: ServiceConfig,
    @Autowired val organizationDiscoveryClient: OrganizationDiscoveryClient
) {

    fun getLicense(licenseId: String, organizationId: String, clientType: String): LicenseDto {
        val license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId)

        val dto = licenseDto(license)

        val organization = retrieveOrganization(organizationId, clientType)
        println("organization: $organization")
        organization?.let {
            dto.organizationName = organization.name
            dto.contactName = organization.contactName
            dto.contactEmail = organization.contactEmail
            dto.contactPhone = organization.contactPhone
        }

        return dto.apply { this.comment = serviceConfig.property }
    }

    private fun retrieveOrganization(organizationId: String, clientType: String): OrganizationDto? {
        return when (clientType) {
            "" -> organizationDiscoveryClient.getOrganization(organizationId)
            else -> organizationDiscoveryClient.getOrganization(organizationId)
        }
    }

    fun getLicense(licenseId: String, organizationId: String) =
        licenseDto(licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId))

    fun createLicense(licenseDto: LicenseDto): LicenseDto {
        val license = license(licenseDto)
        license.licenseId = UUID.randomUUID().toString()
        val saved = licenseRepository.save(license)
        return licenseDto(saved).apply { this.comment = serviceConfig.property }
    }


    fun updateLicense(licenseDto: LicenseDto): LicenseDto {
        val saved = licenseRepository.save(license(licenseDto))
        return licenseDto(saved).apply { this.comment = serviceConfig.property }
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

    private fun license(licenseDto: LicenseDto): License {
        return License(
            licenseId = licenseDto.licenseId,
            organizationId = licenseDto.organizationId,
            productName = licenseDto.productName,
            licenseType = licenseDto.licenseType,
            description = licenseDto.description
        )
    }

    private fun licenseDto(license: License): LicenseDto {
        return LicenseDto(
            licenseId = license.licenseId,
            organizationId = license.organizationId,
            productName = license.productName,
            licenseType = license.licenseType,
            description = license.description
        )
    }
}