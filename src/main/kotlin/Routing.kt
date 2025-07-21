package FirstTask

import FirstTask.model.Employee
import FirstTask.model.EmployeeDb
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.SerializationException

fun Application.configureRouting() {
    routing {
        staticResources("/static", "static")

        route("/employees") {
//            call.respond( //respond is part of server-side app, extension func of applicationcall which is used to send HTTP response to the client, call- represents a single request response within the server
            get {
                val employees = EmployeeDb.allEmployees()
                call.respond(employees)
            }
            get("/byID/{id}") {
                val id = call.parameters["id"]
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }
                val emp = EmployeeDb.employeesById(id.toInt())
                if (emp == null) {
                    call.respond(HttpStatusCode.NotFound)
                    return@get
                }
                call.respond(emp)
            }
            get("/byName/{name}") {
                val name = call.parameters["name"]
                if (name == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }
                val emp = EmployeeDb.employeesByName(name)
                if (emp == null) {
                    call.respond(HttpStatusCode.NotFound)
                    return@get
                }
                call.respond(emp)
            }
            post{
                try{
                    val emp = call.receive<Employee>()
                    EmployeeDb.addEmployee(emp)
                    call.respond(HttpStatusCode.Created)
                } catch (ex: IllegalStateException){
                    call.respond(HttpStatusCode.BadRequest)
                } catch (ex: SerializationException){
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
            put("/{id}"){
                val id = call.parameters["id"]
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@put
                }
                val update = call.receive<Employee>()
                if(id.toInt() != update.id){
                    call.respond(HttpStatusCode.BadRequest)
                }
                if(EmployeeDb.employeesById(id.toInt()) != null){
                    EmployeeDb.updateEmployee(update)
                    call.respond(HttpStatusCode.Created)
                } else try{
                    EmployeeDb.addEmployee(update)
                } catch (ex: IllegalStateException){
                    call.respond(HttpStatusCode.BadRequest)
                } catch (ex: SerializationException){
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
        }
    }
}

