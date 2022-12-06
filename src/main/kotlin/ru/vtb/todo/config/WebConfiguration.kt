package ru.vtb.todo.config

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.*
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@EnableWebMvc
@Configuration
class WebConfiguration : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowCredentials(true)
            .allowedOrigins("http://localhost:3000")
            .allowedMethods(GET.name, POST.name, OPTIONS.name, DELETE.name)
    }
}