package com.bakigoal.license.events

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class OrganizationEventListener {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(OrganizationEventListener::class.java)
    }

    @KafkaListener(
        id = "organization-change-model",
        topics = ["organization_change_topic"]
    )
    fun organizationChanged(orgChange: String) {
        logger.info("Received an $orgChange event ")
    }
}