package com.bakigoal.gateway.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springdoc.core.SwaggerUiConfigParameters
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
@OpenAPIDefinition(
    info = Info(title = "Gateway API", version = "1.0", description = "Documentation Gateway API v1.0")
)
class OpenApiConfig(
    @Autowired val locator: RouteLocator,
    @Autowired val swaggerUiConfigs: SwaggerUiConfigParameters
) {

    @PostConstruct
    fun initGroups() {
        locator.routes.toStream()
            .filter { it.id.matches(".*-SERVICE".toRegex()) }
            .map { it.id.replace("-SERVICE".toRegex(), "")
                .replace("ReactiveCompositeDiscoveryClient_", "")
                .replace("LICENSING", "license")
                .lowercase() }
            .forEach { swaggerUiConfigs.addGroup(it) }
    }
}