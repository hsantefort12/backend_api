package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable

@Serializable
data class Question(
    val name : String,
    val description : String
)

object QuestionTable : IntIdTable() {
    val name = varchar("name", 191)
    val description = text("description")
}