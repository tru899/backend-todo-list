package ru.romanow.todolist.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "items")
data class Item(

    @Id
    @Column(name = "id", nullable = false, unique = true)
    val id: UUID? = null,

    @Column(name = "text", nullable = false, length = 255)
    val text: String? = null,

    @Column(name = "user_id", nullable = false, length = 80)
    val userId: String? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Item

        if (id != other.id) return false
        if (text != other.text) return false
        if (userId != other.userId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (text?.hashCode() ?: 0)
        result = 31 * result + (userId?.hashCode() ?: 0)
        return result
    }
}
