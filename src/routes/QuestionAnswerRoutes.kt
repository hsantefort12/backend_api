package com.example.routes

import com.example.models.DatabaseController
import com.example.models.Question
import com.example.models.Answer
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.postAnswer() {
    post("/answer") {
        val db = DatabaseController()
        val answer = call.receive<Answer>()
        val answerId = db.createAnswer(answer)
        call.respondText(
            "Successfully created answer with id $answerId",
            status = HttpStatusCode.Accepted
        )
    }
}

fun Route.postQuestion() {
    post("/question") {
        val db = DatabaseController()
        val question = call.receive<Question>()
        val questionId = db.createQuestion(question)
        call.respondText(
            "{ \"id\": $questionId }",
            status = HttpStatusCode.Accepted
        )
    }
}

fun Application.registerQuestionAnswerRoutes() {
    routing {
        postAnswer()
        postQuestion()
    }
}