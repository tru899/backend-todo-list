package ru.romanow.todolist

import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selenide.open
import org.junit.jupiter.api.*
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.slf4j.LoggerFactory
import ru.romanow.todolist.login.LoginPage
import ru.romanow.todolist.login.LoginPageValidator
import ru.romanow.todolist.todo.State
import ru.romanow.todolist.todo.TodoListPage
import ru.romanow.todolist.todo.TodoListPageValidator

@TestMethodOrder(OrderAnnotation::class)
class TodoListTest {
    private val logger = LoggerFactory.getLogger(TodoListTest::class.java)

    @BeforeEach
    fun prepare() {
        open(TODO_LIST_URL)
    }

    @Test
    @Order(1)
    fun login() {
        LoginPageValidator().validate()
        LoginPage().authorize()
    }

    @Test
    @Order(2)
    fun items() {
        val validator = TodoListPageValidator()

        val itemsCount = validator.validate()
        val page = TodoListPage()

        val item = page.addNewItem()
        validator.validate(itemsCount + 1, item)
        page.removeItem(item)

        validator.validate(itemsCount, item, State.ABSENT)
    }

    companion object {
        private const val TODO_LIST_URL = "http://todo-list.ru"

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            Configuration.browser = "chrome"
        }
    }
}