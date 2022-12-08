package ru.romanow.todolist.web

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.headers.Header
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.created
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest
import ru.romanow.todolist.model.CreateItemRequest
import ru.romanow.todolist.model.ErrorResponse
import ru.romanow.todolist.model.ListItem
import ru.romanow.todolist.model.ValidationErrorResponse
import ru.romanow.todolist.service.TodoListService
import java.util.*
import javax.validation.Valid

@Tag(
    name = "TODO list Controller",
    description = "Операции создания, получения списка и удаления над записями TODO-листа"
)
@SecurityScheme(type = SecuritySchemeType.OAUTH2)
@RestController
@RequestMapping("/api/v1/public/items")
class TodoListController(
    private val todoListService: TodoListService,
) {
    @Operation(
        summary = "Получение списка записей",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Список всех записей TODO-листа",
                content = [Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = ArraySchema(schema = Schema(implementation = ListItem::class))
                )]
            )
        ]
    )
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun items(token: JwtAuthenticationToken): List<ListItem> {
        val userId = token.tokenAttributes["email"] as String
        return todoListService.findAll(userId)
    }

    @Operation(
        summary = "Создание новой записи",
        responses = [
            ApiResponse(
                responseCode = "201",
                description = "Новая запись успешно добавлена",
                headers = [Header(name = "Location", description = "Ссылка на список всех записей")]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Ошибка валидации",
                content = [Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = ValidationErrorResponse::class)
                )]
            ),
            ApiResponse(
                responseCode = "409",
                description = "Запись с таким uid уже существует",
                content = [Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = ErrorResponse::class)
                )]
            )
        ]
    )
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(token: JwtAuthenticationToken, @Valid @RequestBody request: CreateItemRequest): ResponseEntity<Void> {
        val userId = token.tokenAttributes["email"] as String
        todoListService.create(userId, request)
        return created(fromCurrentRequest()
            .build()
            .toUri())
            .build()
    }

    @Operation(
        summary = "Удаление записи",
        responses = [ApiResponse(responseCode = "204", description = "Запись успешно удалена")]
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uid}")
    fun delete(token: JwtAuthenticationToken, @PathVariable uid: UUID) {
        val userId = token.tokenAttributes["email"] as String
        todoListService.delete(userId, uid)
    }
}