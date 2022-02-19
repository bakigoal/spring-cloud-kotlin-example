package com.bakigoal.organizationservice.service

import com.bakigoal.organizationservice.aop.ZipkinSpan
import com.bakigoal.organizationservice.aop.ZipkinTag
import com.bakigoal.organizationservice.events.ActionEnum
import com.bakigoal.organizationservice.events.EventPublisher
import com.bakigoal.organizationservice.model.Organization
import com.bakigoal.organizationservice.repository.OrganizationRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class OrganizationService(
    @Autowired val organizationRepository: OrganizationRepository,
    @Autowired val eventPublisher: EventPublisher
) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(OrganizationService::class.java)
    }

    @ZipkinSpan(
        name = "getOrgDbCall",
        tags = [
            ZipkinTag(key = "peer.service", value = "Postgres")
        ],
        event = "Get organization from Postgres DB"
    )
    fun getOrganization(organizationId: String): Organization {
        val organizationOptional = organizationRepository.findById(organizationId)
        if (!organizationOptional.isPresent) {
            val message = "Unable to find organization with id = $organizationId"
            logger.error(message)
            throw IllegalArgumentException(message)
        }
        val organization = organizationOptional.get()
        logger.debug("Retrieving Organization Info: $organization")
        return organization
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