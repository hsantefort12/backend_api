package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable

@Serializable
data class User(
    val firstName : String,
    val lastName : String,
    val email : String,
    val password : String
)

object UserTable : IntIdTable() {
    val firstName = varchar("firstName", 191)
    val lastName = varchar("lastName", 191)
    val email = varchar("email", 191)
    val password = varchar("password", 191)
}