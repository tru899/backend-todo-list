package ru.vtb.todo.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.HttpMethod.OPTIONS
import org.springframework.http.HttpStatus.UNAUTHORIZED
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import ru.vtb.todo.config.properties.OAuthLoginProperties

@EnableWebSecurity
@EnableConfigurationProperties(OAuthLoginProperties::class)
class SecurityConfiguration {

    @Order(FIRST)
    @Configuration
    class OAuth2LoginConfiguration(
        private val properties: OAuthLoginProperties
    ) : WebSecurityConfigurerAdapter() {

        override fun configure(http: HttpSecurity) {
            http.requestMatchers{ it.antMatchers("/oauth2/authorization/**", "/login/oauth2/code/**") }
                .oauth2Login { it.defaultSuccessUrl(properties.redirectUri, true) }
        }
    }

    @Order(SECOND)
    @Configuration
    class ProtectedResourceConfiguration : WebSecurityConfigurerAdapter() {

        override fun configure(http: HttpSecurity) {
            http.authorizeRequests { it.antMatchers(OPTIONS).permitAll().anyRequest().authenticated() }
                .exceptionHandling { it.authenticationEntryPoint(HttpStatusEntryPoint(UNAUTHORIZED)) }
                .csrf { it.disable() }
                .cors()
        }
    }

    companion object {
        private const val FIRST = 1
        private const val SECOND = 2
    }
}