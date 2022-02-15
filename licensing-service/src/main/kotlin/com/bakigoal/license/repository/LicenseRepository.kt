package com.bakigoal.license.repository

import com.bakigoal.license.model.License
import org.springframework.data.repository.CrudRepository

interface LicenseRepository : CrudRepository<License, String> {

    fun findByOrganizationId(organizationId: String): List<License>

    fun findByOrganizationIdAndLicenseId(organizationId: String, licenseId: String): License
}