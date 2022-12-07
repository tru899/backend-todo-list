package ru.romanow.todolist.model

data class ValidationErrorResponse(
    val message: String,
    val errors: List<ErrorDescription>
)