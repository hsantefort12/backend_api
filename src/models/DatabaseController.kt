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

            SchemaUtils.create(CharacterTable, QuestionTable, AnswerTable, AttackTable)
        }
    }

    fun dropTables() {
        transaction {
            addLogger(StdOutSqlLogger)

            SchemaUtils.drop(CharacterTable, QuestionTable, AnswerTable, AttackTable)
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

    fun deleteQuestion(question : Question) {
        transaction {
            QuestionTable.deleteWhere {
                QuestionTable.key eq question.key
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

    fun getAnswers() : List<Answer> {
        return transaction {
            AnswerTable.selectAll().map {
                Answer(
                    it[AnswerTable.name],
                    it[AnswerTable.questionId],
                    it[AnswerTable.description],
                    it[AnswerTable.correct]
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

    fun deleteAnswer(answer : Answer) {
        transaction {
            AnswerTable.deleteWhere {
                AnswerTable.name eq answer.name
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
                it[combatId] = character.combatId
            }
        }
    }

    fun getCharacters() : List<Character> {
        return transaction {
            CharacterTable.selectAll().map {
                Character(
                    it[CharacterTable.key],
                    it[CharacterTable.name],
                    it[CharacterTable.hitPoints],
                    it[CharacterTable.combatId]
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
            return Character(0,
                "",
                0,
                0
            )
        }
        val result = results[0]
        return Character(
            result[CharacterTable.key],
            result[CharacterTable.name],
            result[CharacterTable.hitPoints],
            result[CharacterTable.combatId]
        )
    }

    fun deleteCharacter(id : Int) {
        transaction {
            CharacterTable.deleteWhere { CharacterTable.id eq id }
        }
    }
    /* End Character Functions */

    /* Attack Functions */

    fun createAttack(attack : Attack) : EntityID<Int> {
        return transaction {
            AttackTable.insertAndGetId {
                it[characterId] = attack.characterId
                it[die] = attack.die
                it[attackNumber] = attack.attackNumber
            }
        }
    }

    fun getAttacks() : List<Attack> {
        return transaction {
            AttackTable.selectAll().map {
                Attack(
                    it[AttackTable.key],
                    it[AttackTable.characterId],
                    it[AttackTable.die],
                    it[AttackTable.attackNumber]
                )
            }
        }
    }

    fun deleteAttack(id : Int) {
        transaction {
            AttackTable.deleteWhere { AttackTable.id eq id }
        }
    }

    /* End Attack Functions */

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