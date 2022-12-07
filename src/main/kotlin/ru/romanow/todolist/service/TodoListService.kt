package ru.romanow.todolist.service

import ru.romanow.todolist.model.CreateItemRequest
import ru.romanow.todolist.model.ListItem
import java.util.*

interface TodoListService {
    fun findAll(userId: String): List<ListItem>
    fun create(userId: String, request: CreateItemRequest)
    fun delete(userId: String, uid: UUID)
}
