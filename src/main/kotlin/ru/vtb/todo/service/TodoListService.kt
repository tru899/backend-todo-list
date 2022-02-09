package ru.vtb.todo.service

import ru.vtb.todo.model.CreateItemRequest
import ru.vtb.todo.model.ListItem
import java.util.*

interface TodoListService {
    fun findAll(userId: String): List<ListItem>
    fun create(userId: String, request: CreateItemRequest)
    fun delete(userId: String, uid: UUID)
}
