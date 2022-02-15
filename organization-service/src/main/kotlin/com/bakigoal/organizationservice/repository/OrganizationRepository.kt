package com.bakigoal.organizationservice.repository

import com.bakigoal.organizationservice.model.Organization
import org.springframework.data.repository.CrudRepository

interface OrganizationRepository : CrudRepository<Organization, String> {
}