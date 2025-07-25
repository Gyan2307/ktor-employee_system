package FirstTask

import FirstTask.model.DbInterface
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
    configureRouting(db)
}
