package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable

@Serializable
data class Answer(
    val name : String,
    val questionId : Int,
    val description : String,
    val correct : Boolean
)

object AnswerTable : IntIdTable() {
    val name = varchar("name", 191)
    val questionId = integer("questionId")
    val description = text("description")
    val correct = bool("correct")
}