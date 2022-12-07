package ru.romanow.todolist.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.romanow.todolist.domain.Item
import java.util.*

interface ItemRepository : JpaRepository<Item, UUID>
