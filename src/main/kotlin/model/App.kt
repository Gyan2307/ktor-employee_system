package FirstTask.model

import org.jetbrains.exposed.sql.*
fun main() {


        val taskId = Employees.insert {
            it[name] = "aarav"
            it[designation] = "intern"
        } get Employees.id

        val secondTaskId = Employees.insert {
            it[name] = "balraj"
            it[designation] = "sde"
        } get Employees.id

        println("Created new employees with ids $taskId and $secondTaskId.")
}