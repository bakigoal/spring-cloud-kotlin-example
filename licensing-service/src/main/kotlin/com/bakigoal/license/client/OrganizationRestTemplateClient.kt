package com.bakigoal.license.client

import com.bakigoal.license.dto.OrganizationDto
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component

@Component
class OrganizationRestTemplateClient(
    @Autowired val restTemplate: KeycloakRestTemplate
) {

    fun getOrganization(organizationId: String): OrganizationDto? {
        val serviceUri = "http://gateway:8072/organization/v1/organization/$organizationId"
        val exchange = restTemplate.exchange(
            serviceUri,
            HttpMethod.GET, null, OrganizationDto::class.java, organizationId
        )
        return exchange.body
    }
}