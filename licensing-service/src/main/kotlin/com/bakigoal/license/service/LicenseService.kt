package com.bakigoal.license.service

import com.bakigoal.license.model.License
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.stereotype.Service
import java.util.*
import kotlin.random.Random

@Service
class LicenseService(@Autowired val messages: MessageSource) {

    fun getLicense(licenseId: String, organizationId: String) = License(
        id = Random(1000).nextInt(1000),
        licenseId = licenseId,
        organizationId = organizationId,
        description = "Software Product",
        productName = "Ostock",
        licenseType = "full"
    )

    fun createLicense(license: License?, organizationId: String, locale: Locale?): String {
        var responseMessage = ""
        license?.apply {
            this.organizationId = organizationId
            responseMessage = String.format(
                messages.getMessage("license.create.message", null, locale ?: Locale.US),
                license
            )
        }
        return responseMessage
    }

    fun updateLicense(license: License?, organizationId: String, locale: Locale?): String {
        var responseMessage = ""
        license?.apply {
            this.organizationId = organizationId
            responseMessage = String.format(
                messages.getMessage("license.update.message", null, locale ?: Locale.US),
                license
            )
        }
        return responseMessage
    }

    fun deleteLicense(licenseId: String, organizationId: String): String {
        return "Deleting license with id $licenseId for the organization $organizationId"
    }

}