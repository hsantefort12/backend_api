package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
@Serializable
data class Question(
    val key : Int,
    val name : String,
    val description : String
)

object QuestionTable : IntIdTable() {
    val key = integer("key").autoIncrement()
    val name = varchar("name", 191)
    val description = text("description")
}