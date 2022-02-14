package com.bakigoal.license.service

import com.bakigoal.license.model.License
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class LicenseService {

    fun getLicense(licenseId: String, organizationId: String) = License(
        id = Random(1000).nextInt(1000),
        licenseId = licenseId,
        organizationId = organizationId,
        description = "Software Product",
        productName = "Ostock",
        licenseType = "full"
    )

    fun createLicense(license: License?, organizationId: String): String {
        var responseMessage = ""
        license?.apply {
            this.organizationId = organizationId
            responseMessage = "This is the POST and the object is: $license"
        }
        return responseMessage
    }

    fun updateLicense(license: License?, organizationId: String): String {
        var responseMessage = ""
        license?.apply {
            this.organizationId = organizationId
            responseMessage = "This is the PUT and the object is: $license"
        }
        return responseMessage
    }

    fun deleteLicense(licenseId: String, organizationId: String): String {
        return "Deleting license with id $licenseId for the organization $organizationId"
    }

}