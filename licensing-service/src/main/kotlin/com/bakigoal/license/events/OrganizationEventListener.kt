package com.bakigoal.license.events

import com.bakigoal.license.repository.OrganizationRedisRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class OrganizationEventListener(
    @Autowired val redisRepository: OrganizationRedisRepository
) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(OrganizationEventListener::class.java)
    }

    @KafkaListener(
        id = "organization-change-model",
        topics = ["organization_change_topic"]
    )
    fun organizationChanged(orgChange: OrganizationChangeModel) {
        logger.debug("Received change Event: $orgChange")
        redisRepository.deleteById(orgChange.organizationId)
        logger.debug("Removed cache for organization ${orgChange.organizationId}")
    }
}