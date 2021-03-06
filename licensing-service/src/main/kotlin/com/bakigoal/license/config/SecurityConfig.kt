package com.bakigoal.license.config

import org.keycloak.adapters.KeycloakConfigResolver
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper
import org.springframework.security.core.session.SessionRegistryImpl
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy
import org.keycloak.adapters.springsecurity.client.KeycloakClientRequestFactory
import org.springframework.context.annotation.ComponentScan


@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = [KeycloakSecurityComponents::class])
@EnableGlobalMethodSecurity(jsr250Enabled = true) // Enables @RoleAllowed
class SecurityConfig : KeycloakWebSecurityConfigurerAdapter() {

    @Bean
    override fun sessionAuthenticationStrategy(): SessionAuthenticationStrategy =
        RegisterSessionAuthenticationStrategy(SessionRegistryImpl())

    @Autowired
    var keycloakClientRequestFactory: KeycloakClientRequestFactory? = null

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    fun keycloakRestTemplate(): KeycloakRestTemplate{
        return KeycloakRestTemplate(keycloakClientRequestFactory)
    }

    override fun configure(http: HttpSecurity) {
        super.configure(http)
        http.authorizeRequests()
            .antMatchers("/actuator/**", "/api-docs", "/api-docs/**", "/swagger-ui*/**")
            .permitAll()
            .anyRequest()
            .authenticated()
        http.csrf().disable()
    }

    @Autowired
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        val keycloakAuthenticationProvider = keycloakAuthenticationProvider()
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(SimpleAuthorityMapper())
        auth.authenticationProvider(keycloakAuthenticationProvider)
    }
}

@Configuration
class KeycloakResolverConfig {

    @Bean
    fun keycloakConfigResolver(): KeycloakConfigResolver = KeycloakSpringBootConfigResolver()
}