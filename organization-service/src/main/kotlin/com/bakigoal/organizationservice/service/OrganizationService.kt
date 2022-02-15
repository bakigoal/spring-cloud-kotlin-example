package com.bakigoal.organizationservice.service

import com.bakigoal.organizationservice.model.Organization
import com.bakigoal.organizationservice.repository.OrganizationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrganizationService(
    @Autowired val organizationRepository: OrganizationRepository,
) {
    fun getOrganization(organizationId: String): Organization {
        return organizationRepository.findById(organizationId).orElseThrow()
    }
}