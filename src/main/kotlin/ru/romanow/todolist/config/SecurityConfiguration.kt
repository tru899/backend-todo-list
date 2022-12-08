package ru.romanow.todolist.config

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest.toAnyEndpoint
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.core.annotation.Order
import org.springframework.http.HttpMethod.OPTIONS
import org.springframework.http.HttpStatus.UNAUTHORIZED
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy.STATELESS
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerAuthenticationManagerResolver
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import ru.romanow.todolist.config.properties.ActuatorSecurityProperties
import ru.romanow.todolist.config.properties.OAuthLoginProperties


@EnableWebSecurity
@EnableConfigurationProperties(value = [OAuthLoginProperties::class, ActuatorSecurityProperties::class])
class SecurityConfiguration(
    private val actuatorSecurityProperties: ActuatorSecurityProperties,
) {

    @Bean
    @Order(FIRST)
    fun tokenSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .requestMatchers {
                it.antMatchers("/oauth2/authorization/**", "/login/oauth2/code/**")
            }
            .oauth2Login {
                it.defaultSuccessUrl("/callback", true)
            }
            .build()
    }

    @Bean
    @Order(SECOND)
    fun managementSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .requestMatcher(toAnyEndpoint())
            .authorizeRequests {
                it.antMatchers("/manage/health/**").permitAll()
                    .anyRequest().hasRole(actuatorSecurityProperties.role)
            }
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(STATELESS) }
            .httpBasic()
            .and()
            .build()
    }

    @Bean
    @Order(THIRD)
    fun protectedResourceSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        val authenticationManagerResolver = JwtIssuerAuthenticationManagerResolver(
            "https://accounts.google.com",
            "https://romanowalex.eu.auth0.com/"
        )
        return http
            .authorizeRequests {
                it.antMatchers(OPTIONS).permitAll().anyRequest().authenticated()
            }
            .oauth2ResourceServer {
                it.authenticationManagerResolver(authenticationManagerResolver)
            }
            .exceptionHandling {
                it.authenticationEntryPoint(HttpStatusEntryPoint(UNAUTHORIZED))
            }
            .csrf { it.disable() }
            .cors()
            .and()
            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun users(passwordEncoder: PasswordEncoder): UserDetailsService {
        val user = User.builder()
            .username(actuatorSecurityProperties.user)
            .password(passwordEncoder.encode(actuatorSecurityProperties.password))
            .roles(actuatorSecurityProperties.role)
            .build()
        return InMemoryUserDetailsManager(user)
    }

    companion object {
        private const val FIRST = 1
        private const val SECOND = 2
        private const val THIRD = 3
    }
}