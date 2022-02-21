package com.bakigoal.admin

import de.codecentric.boot.admin.server.config.EnableAdminServer
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.cloud.client.discovery.EnableDiscoveryClient


@SpringBootApplication
@EnableAdminServer
@EnableDiscoveryClient
class AdminApplication

fun main(args: Array<String>) {
//    runApplication<AdminApplication>(*args)
    SpringApplicationBuilder(AdminApplication::class.java)
        .web(WebApplicationType.REACTIVE)
        .run(*args)
}
