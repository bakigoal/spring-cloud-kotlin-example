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
        val created = organizationRepository.save(organization)
        eventPublisher.publishOrganizationChange(ActionEnum.CREATED, created.organizationId!!)
        return created
    }

    fun updateOrganization(organization: Organization): Organization {
        val updated = organizationRepository.save(organization)
        eventPublisher.publishOrganizationChange(ActionEnum.UPDATED, updated.organizationId!!)
        return updated
    }

    fun deleteOrganization(organizationId: String): Organization {
        val deleted = organizationRepository.findById(organizationId).orElseThrow()
        organizationRepository.delete(deleted)
        eventPublisher.publishOrganizationChange(ActionEnum.DELETED, deleted.organizationId!!)
        return deleted
    }
}