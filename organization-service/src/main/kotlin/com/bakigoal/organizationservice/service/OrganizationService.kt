package com.bakigoal.organizationservice.service

import com.bakigoal.organizationservice.events.ActionEnum
import com.bakigoal.organizationservice.events.EventPublisher
import com.bakigoal.organizationservice.model.Organization
import com.bakigoal.organizationservice.repository.OrganizationRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.sleuth.Tracer
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException
import java.util.*

@Service
class OrganizationService(
    @Autowired val organizationRepository: OrganizationRepository,
    @Autowired val eventPublisher: EventPublisher,
    @Autowired val tracer: Tracer
) {

    companion object{
        val logger: Logger = LoggerFactory.getLogger(OrganizationService::class.java)
    }

    fun getOrganization(organizationId: String): Organization {
        val span = tracer.startScopedSpan("getOrgDBCall")
        try {
            val organizationOptional = organizationRepository.findById(organizationId)
            if (!organizationOptional.isPresent){
                val message = "Unable to find organization with id = $organizationId"
                logger.error(message)
                throw IllegalArgumentException(message)
            }
            val organization = organizationOptional.get()
            logger.debug("Retrieving Organization Info: $organization")
            return organization
        } finally {
            span.tag("peer.service", "postgres")
            span.event("Client received")
            span.end()
        }
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