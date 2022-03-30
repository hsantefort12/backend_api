package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable

@Serializable
data class Character(
    val key : Int,
    val name : String,
    val hitPoints : Int,
    val combatId : Int
)

object CharacterTable : IntIdTable() {
    val key = integer("key").autoIncrement()
    val name = varchar("name", 191)
    val hitPoints = integer("hitPoints")
    val combatId = integer("combatId")
}