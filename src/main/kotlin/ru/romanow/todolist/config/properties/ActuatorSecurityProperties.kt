package ru.romanow.todolist.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "management.credentials")
data class ActuatorSecurityProperties(
    val user: String,
    val password: String,
    val role: String
)