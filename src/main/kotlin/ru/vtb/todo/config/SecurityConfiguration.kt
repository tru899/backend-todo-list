package ru.vtb.todo.config

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest.toAnyEndpoint
import org.springframework.boot.actuate.health.HealthEndpoint
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.HttpMethod.OPTIONS
import org.springframework.http.HttpStatus.UNAUTHORIZED
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy.STATELESS
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import ru.vtb.todo.config.properties.ActuatorSecurityProperties
import ru.vtb.todo.config.properties.OAuthLoginProperties

@EnableWebSecurity
@EnableConfigurationProperties(value = [OAuthLoginProperties::class, ActuatorSecurityProperties::class])
class SecurityConfiguration {

    @Order(FIRST)
    @Configuration
    class OAuth2LoginConfiguration(
        private val properties: OAuthLoginProperties
    ) : WebSecurityConfigurerAdapter() {

        override fun configure(http: HttpSecurity) {
            http.requestMatchers { it.antMatchers("/oauth2/authorization/**", "/login/oauth2/code/**") }
                .oauth2Login { it.defaultSuccessUrl(properties.redirectUri, true) }
        }
    }

    @Order(SECOND)
    @Configuration
    class ActuatorSecurityConfiguration(
        private val properties: ActuatorSecurityProperties
    ) : WebSecurityConfigurerAdapter() {

        @Bean
        fun passwordEncoder(): PasswordEncoder {
            return BCryptPasswordEncoder()
        }

        override fun configure(http: HttpSecurity) {
            // @formatter:off
            http.requestMatcher(toAnyEndpoint())
                .authorizeRequests {
                    it.antMatchers("/manage/health").permitAll()
                        .anyRequest().hasRole(properties.role)
                }
                .csrf { it.disable() }
                .formLogin { it.disable() }
                .sessionManagement { it.sessionCreationPolicy(STATELESS) }
                .httpBasic()
                .and()
            // @formatter:on
        }

        override fun configure(auth: AuthenticationManagerBuilder) {
            val passwordEncoder: PasswordEncoder = passwordEncoder()
            auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder)
                .withUser(properties.user)
                .password(passwordEncoder.encode(properties.password))
                .roles(properties.role)
        }
    }

    @Order(THIRD)
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
        private const val THIRD = 3
    }
}