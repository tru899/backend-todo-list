package ru.romanow.todolist.config

import io.micrometer.core.aop.TimedAspect
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.observation.Observation
import io.micrometer.observation.ObservationPredicate
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.server.observation.ServerRequestObservationContext

@Configuration
class ObservabilityConfiguration {

    @Bean
    fun timedAspect(registry: MeterRegistry): TimedAspect {
        return TimedAspect(registry)
    }

    @Bean
    @ConditionalOnProperty("management.tracing.enabled", havingValue = "true")
    fun noActuatorObservations() = ObservationPredicate { name: String, context: Observation.Context? ->
        if (name == "http.server.requests" && context is ServerRequestObservationContext) {
            !context.carrier.servletPath.startsWith("/backend/manage")
        } else {
            true
        }
    }

    @Bean
    @ConditionalOnProperty("management.tracing.enabled", havingValue = "true")
    fun noSecurityObservations() =
        ObservationPredicate { name: String, _: Observation.Context? -> !name.startsWith("spring.security.") }
}
