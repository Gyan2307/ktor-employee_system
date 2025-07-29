package FirstTask

import FirstTask.infrastructure.persistence.DbInterface
import FirstTask.infrastructure.persistence.configureSerialization
import FirstTask.presentation.configureRouting
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "debug")

    embeddedServer(Netty, port = 8082, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()

    val db = DbInterface()
    db.initDb()
    val employeeService = FirstTask.Application.EmployeeService(db)
    configureRouting(employeeService)
}
