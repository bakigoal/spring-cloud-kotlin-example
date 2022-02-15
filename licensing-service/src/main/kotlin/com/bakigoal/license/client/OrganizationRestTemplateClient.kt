package com.bakigoal.license.client

import com.bakigoal.license.dto.OrganizationDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class OrganizationRestTemplateClient(
    /**
     *
     * @Bean
     * @LoadBalanced
     * fun restTemplate(): RestTemplate {
     *    return RestTemplate()
     * }
     */
    @Autowired val restTemplate: RestTemplate
) {

    fun getOrganization(organizationId: String): OrganizationDto? {
        val serviceUri = "http://organization-service/v1/organization/$organizationId"
        val exchange = restTemplate.exchange(
            serviceUri,
            HttpMethod.GET, null, OrganizationDto::class.java, organizationId
        )
        return exchange.body
    }
}