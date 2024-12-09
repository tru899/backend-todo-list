package ru.romanow.todolist.config

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.DELETE
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpMethod.OPTIONS
import org.springframework.http.HttpMethod.POST
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfiguration : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowCredentials(true)
            .allowedOriginPatterns(
                "http://localhost:[*]",
                "http://todo-list.ru",
                "https://*.romanow-alex.ru"
            )
            .allowedMethods(GET.name(), POST.name(), OPTIONS.name(), DELETE.name())
    }
}
