package com.bakigoal.license.mapper

import com.bakigoal.license.dto.LicenseDto
import com.bakigoal.license.model.License
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

internal class LicenseMapperKtTest {

    @Test
    fun `map License to LicenseDto using extension function`() {
        val licenseId = UUID.randomUUID().toString()
        val organizationId = UUID.randomUUID().toString()
        val license = License(
            licenseId = licenseId,
            organizationId = organizationId,
            productName = "Super Important License",
            licenseType = "License Type",
            description = "There is some Description"
        )

        val dto = license.toDto()

        assertAll(
            { assertEquals(licenseId, dto.licenseId) },
            { assertEquals(organizationId, dto.organizationId) },
            { assertEquals("Super Important License", dto.productName) },
            { assertEquals("License Type", dto.licenseType) },
            { assertEquals("There is some Description", dto.description) }
        )
    }

    @Test
    fun `map LicenseDto to License using extension function`() {
        val licenseId = UUID.randomUUID().toString()
        val organizationId = UUID.randomUUID().toString()
        val dto = LicenseDto(
            licenseId = licenseId,
            organizationId = organizationId,
            productName = "Super Important License",
            licenseType = "License Type",
            description = "There is some Description",
            organizationName = "organization"
        )

        val license = dto.toModel()

        assertAll(
            { assertEquals(licenseId, license.licenseId) },
            { assertEquals(organizationId, license.organizationId) },
            { assertEquals("Super Important License", license.productName) },
            { assertEquals("License Type", license.licenseType) },
            { assertEquals("There is some Description", license.description) }
        )
    }
}