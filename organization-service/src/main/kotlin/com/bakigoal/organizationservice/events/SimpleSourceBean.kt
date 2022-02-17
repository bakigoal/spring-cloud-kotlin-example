package com.bakigoal.organizationservice.events

import com.bakigoal.organizationservice.util.UserContext
import com.bakigoal.organizationservice.util.UserContextHolder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.stream.messaging.Source
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component

@Component
class SimpleSourceBean(
    @Autowired val source: Source
) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(SimpleSourceBean::class.java)
    }

    fun publishOrganizationChange(action: ActionEnum, organizationId: String) {
        logger.info("Sending Kafka message {} for Organization Id: {}", action, organizationId)
        val change = OrganizationChangeModel(
            OrganizationChangeModel::class.java.typeName,
            action.toString(),
            organizationId,
            UserContextHolder.getContext().correlationId
        )
        source.output().send(MessageBuilder.withPayload(change).build())
    }

}

enum class ActionEnum {
    GET, CREATED, UPDATED, DELETED
}