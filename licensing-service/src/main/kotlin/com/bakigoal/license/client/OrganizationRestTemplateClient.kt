package com.bakigoal.license.client

import com.bakigoal.license.dto.OrganizationDto
import com.bakigoal.license.mapper.toDto
import com.bakigoal.license.mapper.toModel
import com.bakigoal.license.model.Organization
import com.bakigoal.license.repository.OrganizationRedisRepository
import com.bakigoal.license.util.UserContextHolder
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.sleuth.Tracer
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import java.util.*

@Component
class OrganizationRestTemplateClient(
    @Autowired val restTemplate: KeycloakRestTemplate,
    @Autowired val redisRepository: OrganizationRedisRepository,
    @Autowired val tracer: Tracer
) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(OrganizationRestTemplateClient::class.java)
    }

    fun getOrganization(organizationId: String): OrganizationDto? {
        logger.debug("In Licensing Service.getOrganization: {}", UserContextHolder.getContext().correlationId);
        val fromCache = getFromCache(organizationId)
        if (fromCache.isPresent) {
            logger.debug("Successfully retrieved an organization $organizationId from the redis cache: ${fromCache.get()}");
            return fromCache.get().toDto()
        }
        logger.debug("Unable to get organization from the redis cache: $organizationId.");
        val dto = getFromOrganizationService(organizationId)
        if (dto != null) {
            putToCache(dto.toModel())
        }
        return dto
    }

    private fun getFromCache(organizationId: String): Optional<Organization> {
        val span = tracer.startScopedSpan("readLicensingDataFromRedis")
        try {
            return redisRepository.findById(organizationId)
        } catch (e: Exception) {
            logger.error("Error encountered while trying to retrieve organization $organizationId in Redis. Exception $e")
        } finally {
            span.tag("peer.service", "redis")
            span.event("Client received")
            span.end()
        }
        return Optional.empty()
    }

    private fun putToCache(organization: Organization) {
        try {
            redisRepository.save(organization)
        } catch (e: Exception) {
            logger.error("Unable to cache organization ${organization.id} in Redis. Exception $e}")
        }
    }

    private fun getFromOrganizationService(organizationId: String): OrganizationDto? {
        val serviceUri = "http://gateway:8072/organization/v1/organization/$organizationId"
        val exchange = restTemplate.exchange(
            serviceUri,
            HttpMethod.GET, null, OrganizationDto::class.java, organizationId
        )
        return exchange.body
    }
}