package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable

@Serializable
data class Character(
    val name : String,
    val hitPoints : Int,
    val movement : Int,
    val bonusMovement : Int,
    val npc : Boolean
)

object CharacterTable : IntIdTable() {
    val name = varchar("name", 191)
    val hitPoints = integer("hitPoints")
    val movement = integer("movement")
    val bonusMovement = integer("bonusMovement")
    val npc = bool("npc")
}