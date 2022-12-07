package ru.romanow.todolist.service

import org.springframework.data.domain.Example
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.romanow.todolist.domain.Item
import ru.romanow.todolist.exceptions.ItemAlreadyExistsException
import ru.romanow.todolist.model.CreateItemRequest
import ru.romanow.todolist.model.ListItem
import ru.romanow.todolist.repository.ItemRepository
import java.util.*

@Service
class TodoListServiceImpl(
    private val itemRepository: ItemRepository
) : TodoListService {

    @Transactional(readOnly = true)
    override fun findAll(userId: String) = itemRepository
        .findAll(Example.of(Item(userId = userId)))
        .map { ListItem(uid = it.id!!, text = it.text!!) }

    @Transactional
    override fun create(userId: String, request: CreateItemRequest) {
        val id = request.uid
        if (itemRepository.findById(id!!).isPresent) {
            throw ItemAlreadyExistsException("Item $id already exists")
        }
        val item = Item(id = id, text = request.text, userId = userId)
        itemRepository.save(item)
    }

    @Transactional
    override fun delete(userId: String, uid: UUID) {
        if (itemRepository.exists(Example.of(Item(id = uid, userId = userId)))) {
            itemRepository.deleteById(uid)
        }
    }
}