package ru.romanow.todolist.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "management.credentials")
data class ActuatorSecurityProperties(
    val user: String,
    val password: String,
    val role: String
)