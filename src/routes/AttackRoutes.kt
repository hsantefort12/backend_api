package com.example.routes

import com.example.models.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.postAttack() {
    post("/new-attack") {
        val db = DatabaseController()
        val attack = call.receive<Attack>()
        val attackId = db.createAttack(attack)
        call.respondText(
            "Successfully created attack with id $attackId",
            status = HttpStatusCode.Accepted
        )
    }
}

fun Route.getAllAttacks() {
    get("/attacks") {
        val db = DatabaseController()
        val attacks = db.getAttacks()
        call.respond(attacks)
    }
}

fun Route.deleteAttack() {
    delete("/delete-attack") {
        val db = DatabaseController()
        val attack = call.receive<Attack>()
        db.deleteAttack(attack.key)
        call.respondText(
            "Successfully deleted attack with key ${attack.key}"
        )
    }
}

fun Application.registerAttackRoutes() {
    routing {
        postAttack()
        getAllAttacks()
        deleteAttack()
    }
}