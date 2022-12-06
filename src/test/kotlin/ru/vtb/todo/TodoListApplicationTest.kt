package ru.vtb.todo

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import ru.vtb.todo.config.DatabaseTestConfiguration

@ActiveProfiles("test")
@SpringBootTest
@Import(DatabaseTestConfiguration::class)
internal class TodoListApplicationTest {

    @Test
    fun testApp() {
    }
}