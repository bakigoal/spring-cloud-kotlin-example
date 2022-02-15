package com.bakigoal.license.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "licenses")
data class License(
    @Id
    @Column(name = "license_id", nullable = false)
    var licenseId: String? = null,
    @Column(name = "organization_id", nullable = false)
    var organizationId: String? = null,
    @Column(name = "product_name", nullable = false)
    var productName: String? = null,
    @Column(name = "license_type", nullable = false)
    var licenseType: String? = null,
    var description: String? = null
)