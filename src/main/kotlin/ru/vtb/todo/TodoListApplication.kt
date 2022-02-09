package ru.vtb.todo

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties

@SpringBootApplication
class TodoListApplication

fun main(args: Array<String>) {
    SpringApplication.run(TodoListApplication::class.java, *args)
}