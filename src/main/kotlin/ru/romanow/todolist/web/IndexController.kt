package ru.romanow.todolist.web

import io.swagger.v3.oas.annotations.Hidden
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Hidden
@RestController
@ConditionalOnProperty(prefix = "index-page", name = ["enabled"], havingValue = "true")
class IndexController(
    @Value("\${spring.application.name}")
    private val applicationName: String,
) {

    @GetMapping(produces = [MediaType.TEXT_PLAIN_VALUE])
    fun index() = "Application $applicationName"
}
