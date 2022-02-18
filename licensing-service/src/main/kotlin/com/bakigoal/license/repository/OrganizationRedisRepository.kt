package com.bakigoal.license.repository

import com.bakigoal.license.model.Organization
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface OrganizationRedisRepository: CrudRepository<Organization, String> {
}