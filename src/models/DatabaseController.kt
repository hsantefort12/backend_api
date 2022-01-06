package com.example.models

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.net.URI

class DatabaseController {
    init {
        val uri = URI(System.getenv("DATABASE_URL"))
        val username = uri.userInfo.split(":")[0]
        val password = uri.userInfo.split(":")[1]

        Database.connect(
            "jdbc:postgresql://" + uri.host + ":" + uri.port + uri.path + "?sslmode=require",
            driver = "org.postgresql.Driver",
            user = username,
            password = password
        )
    }

    /* Table Functions */
    fun createTables() {
        transaction {
            addLogger(StdOutSqlLogger)

            SchemaUtils.create(UserTable, CharacterTable, QuestionTable, AnswerTable)
        }
    }

    fun dropTables() {
        transaction {
            addLogger(StdOutSqlLogger)

            SchemaUtils.drop(UserTable, CharacterTable, QuestionTable, AnswerTable)
        }
    }
    /* End Table Functions */

    /* Question and Answer Functions */
    fun createQuestion(question : Question) : EntityID<Int> {
        return transaction {
            QuestionTable.insertAndGetId {
                it[name] = question.name
                it[description] = question.description
            }
        }
    }

    fun getQuestions() : List<Question> {
        return transaction {
            QuestionTable.selectAll().map {
                Question(
                    it[QuestionTable.key],
                    it[QuestionTable.name],
                    it[QuestionTable.description]
                )
            }
        }
    }

    fun createAnswer(answer : Answer) : EntityID<Int> {
        return transaction {
            AnswerTable.insertAndGetId {
                it[name] = answer.name
                it[questionId] = answer.questionId
                it[description] = answer.description
                it[correct] = answer.correct
            }
        }
    }

    /* End Question and Answer Functions */

    /* Character Functions */
    fun createCharacter(character : Character) : EntityID<Int> {
        return transaction {
            CharacterTable.insertAndGetId {
                it[name] = character.name
                it[hitPoints] = character.hitPoints
                it[movement] = character.movement
                it[bonusMovement] = character.bonusMovement
                it[npc] = character.npc
            }
        }
    }

    fun getCharacters() : List<Character> {
        return transaction {
            CharacterTable.selectAll().map {
                Character(
                    it[CharacterTable.name],
                    it[CharacterTable.hitPoints],
                    it[CharacterTable.movement],
                    it[CharacterTable.bonusMovement],
                    it[CharacterTable.npc]
                )
            }
        }
    }

    fun getCharacter(id : Int) : Character {
        val results = transaction {
            CharacterTable.select {
                CharacterTable.id eq id
            }.toList()
        }
        if (results.isEmpty()) {
            println("No character found with id $id")
            return Character("",
                0,
                0,
                0,
                false
            )
        }
        val result = results[0]
        return Character(result[CharacterTable.name],
            result[CharacterTable.hitPoints],
            result[CharacterTable.movement],
            result[CharacterTable.bonusMovement],
            result[CharacterTable.npc]
        )
    }

    fun deleteCharacter(id : Int) {
        transaction {
            CharacterTable.deleteWhere { CharacterTable.id eq id }
        }
    }
    /* End Character Functions */

    /* User Functions */

    fun createUser(user: User): EntityID<Int> {
        return transaction {
            UserTable.insertAndGetId {
                it[firstName] = user.firstName
                it[lastName] = user.lastName
                it[email] = user.email
                it[password] = user.password
            }
        }
    }

    fun getUser(id : Int) : User {
        val results = transaction {
            UserTable.select {
                UserTable.id eq id
            }.toList()
        }
        if (results.isEmpty()) {
            println("No user found with id $id")
            return User("",
                        "",
                        "",
                        ""
            )
        }
        val result = results[0]
        return User(result[UserTable.firstName],
            result[UserTable.lastName],
            result[UserTable.email],
            result[UserTable.password]
        )
    }

    fun deleteUser(id : Int) {
        transaction {
            UserTable.deleteWhere { UserTable.id eq id }
        }
    }
    /* End User Functions */
}