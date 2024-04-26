package ru.romanow.todolist

import com.codeborne.selenide.CollectionCondition.size
import com.codeborne.selenide.Condition.*
import com.codeborne.selenide.Selenide.open
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import ru.romanow.todolist.login.LoginPage
import ru.romanow.todolist.login.LoginPageResults
import ru.romanow.todolist.todo.TodoListPage
import ru.romanow.todolist.todo.TodoListPageResults

@TestMethodOrder(OrderAnnotation::class)
class TodoListTest {

    @BeforeEach
    fun prepare() {
        open(TODO_LIST_URL)
    }

    @Test
    @Order(1)
    fun login() {
        LoginPageResults().loginModal().exists()
        LoginPage().authorize()
    }

    @Test
    @Order(2)
    fun items() {
        val page = TodoListPage()
        val results = TodoListPageResults()

        results.loader().shouldBe(hidden)
        val items = results.itemsCount()
        val itemsCount = items.size()

        val item = page.addNewItem()
        items.shouldHave(size(itemsCount + 1))
        results.item(item.uid).shouldHave(text(item.text))

        page.removeItem(item)

        items.shouldHave(size(itemsCount))
        results.item(item.uid).shouldNot(exist)
    }

    companion object {
        private const val TODO_LIST_URL = "http://todo-list.ru"
    }
}
