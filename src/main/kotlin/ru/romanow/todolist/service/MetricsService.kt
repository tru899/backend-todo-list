package ru.romanow.todolist.service

import io.micrometer.core.instrument.Gauge
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.binder.BaseUnits
import io.micrometer.core.instrument.binder.MeterBinder
import org.springframework.stereotype.Service
import ru.romanow.todolist.repository.ItemRepository

@Service
class MetricsService(
    private val itemRepository: ItemRepository
) : MeterBinder {
    override fun bindTo(registry: MeterRegistry) {
        Gauge.builder("total.users") { usersCount() }
            .description("Total users")
            .baseUnit(BaseUnits.OBJECTS)
            .register(registry)

        Gauge.builder("total.items") { itemsCount() }
            .description("Total items")
            .baseUnit(BaseUnits.OBJECTS)
            .register(registry)
    }

    private fun usersCount(): Long = itemRepository.countDistinctByUser()

    private fun itemsCount(): Long = itemRepository.count()
}
