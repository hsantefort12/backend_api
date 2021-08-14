package com.example.routes

import com.example.models.DatabaseController
import com.example.models.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.Database

fun Route.postCharacter() {
    post("/new-character") {
        val db = DatabaseController()
        val character = call.receive<Character>()
        val characterId = db.createCharacter(character)
        call.respondText(
            "Successfully created character with id $characterId",
            status = HttpStatusCode.Accepted
        )
    }
}

fun Route.getAllCharacters() {
    get("/characters") {
        val db = DatabaseController()
        val characters = db.getCharacters()
        call.respond(characters)
    }
}

fun Route.getCharacter() {
    get ("/character/{id}") {
        val db = DatabaseController()
        val id = call.parameters["id"] ?: return@get call.respondText(
            "Bad Argument",
            status = HttpStatusCode.BadRequest
        )
        val character = db.getCharacter(id.toInt())
        call.respond(character)
    }
}

fun Route.deleteCharacter() {
    delete ("/delete-character/{id}") {
        val db = DatabaseController()
        val id = call.parameters["id"] ?: return@delete call.respondText(
            "Bad Argument",
            status = HttpStatusCode.BadRequest
        )
        db.deleteCharacter(id.toInt())
    }
}

fun Application.registerCharacterRoutes() {
    routing {
        postCharacter()
        getAllCharacters()
        getCharacter()
        deleteCharacter()
    }
}