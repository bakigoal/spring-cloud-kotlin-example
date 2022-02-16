package com.bakigoal.license.service

import com.bakigoal.license.client.OrganizationDiscoveryClient
import com.bakigoal.license.client.OrganizationFeignClient
import com.bakigoal.license.client.OrganizationRestTemplateClient
import com.bakigoal.license.config.ServiceConfig
import com.bakigoal.license.dto.LicenseDto
import com.bakigoal.license.dto.OrganizationDto
import com.bakigoal.license.mapper.toDto
import com.bakigoal.license.mapper.toModel
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
    @Autowired val organizationDiscoveryClient: OrganizationDiscoveryClient,
    @Autowired val organizationRestTemplateClient: OrganizationRestTemplateClient,
    @Autowired val organizationFeignClient: OrganizationFeignClient
) {

    fun getLicense(licenseId: String, organizationId: String, clientType: String): LicenseDto {
        val license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId)

        val dto = license.toDto()

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
            "discovery" -> organizationDiscoveryClient.getOrganization(organizationId)
            "rest" -> organizationRestTemplateClient.getOrganization(organizationId)
            "feign" -> organizationFeignClient.getOrganization(organizationId)
            else -> throw IllegalArgumentException("No rest client for $clientType")
        }
    }

    fun getLicense(licenseId: String, organizationId: String) =
        licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId).toDto()

    fun createLicense(licenseDto: LicenseDto): LicenseDto {
        val license = licenseDto.toModel()
        license.licenseId = UUID.randomUUID().toString()
        val saved = licenseRepository.save(license)
        return saved.toDto().apply { this.comment = serviceConfig.property }
    }


    fun updateLicense(licenseDto: LicenseDto): LicenseDto {
        val saved = licenseRepository.save(licenseDto.toModel())
        return saved.toDto().apply { this.comment = serviceConfig.property }
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