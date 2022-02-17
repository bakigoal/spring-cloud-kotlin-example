package com.bakigoal.organizationservice.events

import com.bakigoal.organizationservice.util.UserContextHolder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import org.springframework.util.concurrent.ListenableFutureCallback


@Component
class EventPublisher(
    @Autowired val kafkaTemplate: KafkaTemplate<String, String>
) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(EventPublisher::class.java)
    }

    @Value(value = "\${kafka.orgChangeTopic}")
    private val orgChangeTopic: String? = null

    fun publishOrganizationChange(action: ActionEnum, organizationId: String) {
        logger.info("Sending Kafka message {} for Organization Id: {}", action, organizationId)
        val model = createMessage(action, organizationId)

        val future = kafkaTemplate.send(orgChangeTopic!!, organizationId, action.name)

        future.addCallback(object : ListenableFutureCallback<SendResult<String, String>> {
            override fun onSuccess(result: SendResult<String, String>?) {
                logger.info("Successfully sent message $organizationId")
            }

            override fun onFailure(ex: Throwable) {
                logger.info("Error while sending message $organizationId")
            }
        })
    }

    private fun createMessage(action: ActionEnum, organizationId: String) = OrganizationChangeModel(
        OrganizationChangeModel::class.java.typeName,
        action.toString(),
        organizationId,
        UserContextHolder.getContext().correlationId
    )

}

enum class ActionEnum {
    GET, CREATED, UPDATED, DELETED
}