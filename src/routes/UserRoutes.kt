package com.example.routes

import com.example.models.DatabaseController
import com.example.models.User
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.postUser() {
    post("/new-user") {
        val db = DatabaseController()
        val user = call.receive<User>()
        val userId = db.createUser(user)
        call.respondText(
            "Successfully created user with id $userId",
            status = HttpStatusCode.Accepted
        )
    }
}

fun Route.deleteUser() {
    delete("/delete-user/{id}") {
        val db = DatabaseController()
        val id = call.parameters["id"] ?: return@delete call.respondText(
            "Bad Argument",
            status = HttpStatusCode.BadRequest
        )
        db.deleteUser(id.toInt())
    }
}

fun Route.findUserById() {
    get("/user/{id}") {
        val db = DatabaseController()
        val id = call.parameters["id"] ?: return@get call.respondText(
        "Bad Argument",
        status = HttpStatusCode.BadRequest
        )
        val user = db.getUser(id.toInt())
        call.respond(user)
    }
}

fun Application.registerUserRoutes() {
    routing {
        postUser()
        findUserById()
        deleteUser()
    }
}