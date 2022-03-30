package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable

@Serializable
data class Attack(
    val key : Int,
    val characterId : Int,
    val die : String,
    val attackNumber : Int
)

object AttackTable : IntIdTable() {
    val key = integer("key").autoIncrement()
    val characterId = integer("characterId")
    val die = varchar("die", 191)
    val attackNumber = integer("attackNumber")
}