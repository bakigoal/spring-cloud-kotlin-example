package com.bakigoal.organizationservice.service

import com.bakigoal.organizationservice.events.ActionEnum
import com.bakigoal.organizationservice.events.EventPublisher
import com.bakigoal.organizationservice.model.Organization
import com.bakigoal.organizationservice.repository.OrganizationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class OrganizationService(
    @Autowired val organizationRepository: OrganizationRepository,
    @Autowired val eventPublisher: EventPublisher
) {
    fun getOrganization(organizationId: String): Organization {
        return organizationRepository.findById(organizationId).orElseThrow()
    }

    fun createOrganization(organization: Organization): Organization {
        organization.organizationId = UUID.randomUUID().toString()
        val saved = organizationRepository.save(organization)
        eventPublisher.publishOrganizationChange(ActionEnum.CREATED, saved.organizationId!!)
        return saved
    }

    fun deleteOrganization(organizationId: String): Organization {
        val organization = organizationRepository.findById(organizationId).orElseThrow()
        organizationRepository.delete(organization)
        return organization
    }
}