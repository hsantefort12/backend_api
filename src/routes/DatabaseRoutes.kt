package com.example.routes

import com.example.models.DatabaseController
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.Database
import javax.xml.crypto.Data

fun Route.createTables() {
    post("/create-tables") {
        println("/create-tables")
        val db = DatabaseController()
        db.createTables()
        call.respondText(
            "Tables created successfully",
            status = HttpStatusCode.Accepted
        )
    }
}

fun Route.dropTables() {
    post ("/drop-tables") {
        val db = DatabaseController()
        db.dropTables()
        call.respondText(
            "Successfully dropped tables",
            status = HttpStatusCode.Accepted
        )
    }
}

fun Route.test() {
    get ("/test") {
        val db = DatabaseController()
        println(db.toString())
    }
}

fun Application.registerDatabaseRoutes() {
    routing {
        authenticate("myBasicAuth") {
            createTables()
            dropTables()
            test()
        }
    }
}