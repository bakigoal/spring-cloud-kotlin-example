package com.bakigoal.license.client

import com.bakigoal.license.dto.OrganizationDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class OrganizationDiscoveryClient(
    /**
     * To use DiscoveryClient you should enable it
     * @EnableDiscoveryClient
     */
    @Autowired val discoveryClient: DiscoveryClient
) {

    fun getOrganization(organizationId: String): OrganizationDto? {
        val restTemplate = RestTemplate()
        val instances = discoveryClient.getInstances("organization-service")

        if (instances.size == 0) {
            return null
        }

        val serviceUri = "${instances[0].uri}/v1/organization/$organizationId"
        val exchange =
            restTemplate.exchange(serviceUri, HttpMethod.GET, null, OrganizationDto::class.java, organizationId)
        return exchange.body
    }
}