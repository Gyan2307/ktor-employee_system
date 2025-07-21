package FirstTask.model

import kotlin.text.insert
//import Employees
import org.jetbrains.exposed.v1.core.StdOutSqlLogger
import org.jetbrains.exposed.v1.core.count
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.addLogger
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.select
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

fun main() {
    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
    transaction {
        addLogger(StdOutSqlLogger)
        SchemaUtils.create(Employees)

        val taskId = Employees.insert {
            it[name] = "aarav"
            it[designation] = "intern"
        } get Employees.id

        val secondTaskId = Employees.insert {
            it[name] = "sde"
            it[designation] = "Read the first two chapters of The Hobbit"
        } get Employees.id

        println("Created new employees with ids $taskId and $secondTaskId.")

        Employees.select(Employees.id.count(), Employees.designation).groupBy(Employees.designation).forEach {
            println("${it[Employees.designation]}: ${it[Employees.id.count()]} ")
        }
    }
}