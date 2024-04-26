package ru.romanow.todolist.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.util.*

data class CreateItemRequest(
    @field:NotNull(message = "{field.is.null}")
    val uid: UUID?,
    @field:NotBlank(message = "{field.is.empty}")
    @field:Size(max = 255, message = "{field.too.large}")
    val text: String?
)
