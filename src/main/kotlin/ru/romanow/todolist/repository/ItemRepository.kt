package ru.romanow.todolist.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.romanow.todolist.domain.Item
import java.util.*

interface ItemRepository : JpaRepository<Item, UUID> {
    @Query("select count(distinct i.userId) from Item i")
    fun countDistinctByUser(): Long
}
