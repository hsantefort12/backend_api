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

fun Route.deleteAnswer() {
    delete("/answer") {
        val db = DatabaseController()
        val answer = call.receive<Answer>()
        db.deleteAnswer(answer)
        call.respondText(
            "Successfully deleted answer with name ${answer.name}",
            status = HttpStatusCode.Accepted
        )
    }
}

fun Route.getAnswers() {
    get("/answers") {
        val db = DatabaseController()
        val answers = db.getAnswers()
        call.respond(answers)
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

fun Route.deleteQuestion() {
    delete("question") {
        val db = DatabaseController()
        val question = call.receive<Question>()
        db.deleteQuestion(question)
        call.respondText(
            "Successfully deleted question with name ${question.name}"
        )
    }
}

fun Route.getQuestions() {
    get("/questions") {
        val db = DatabaseController()
        val questions = db.getQuestions()
        call.respond(questions)
    }
}

fun Application.registerQuestionAnswerRoutes() {
    routing {
        postAnswer()
        deleteAnswer()
        getAnswers()
        postQuestion()
        deleteQuestion()
        getQuestions()
    }
}