package ru.romanow.todolist.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("oauth2.login")
data class OAuthLoginProperties(
    val redirectUri: String? = null
)
