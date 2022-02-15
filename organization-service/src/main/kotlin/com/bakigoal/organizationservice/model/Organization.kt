package com.bakigoal.organizationservice.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "organizations")
data class Organization(

    @Id
    @Column(name = "organization_id", nullable = false)
    var organizationId: String? = null,
    @Column(name = "name")
    var name: String? = null,
    @Column(name = "contact_name")
    var contactName: String? = null,
    @Column(name = "contact_email")
    var contactEmail: String? = null,
    @Column(name = "contact_phone")
    var contactPhone: String? = null

)