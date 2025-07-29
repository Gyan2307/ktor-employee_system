//this is the presentation layer of the application, it handles the HTTP requests and responses
// it is responsible for routing the requests to the appropriate handlers and sending the responses back to the client
// it uses the DbInterface to interact with the database and perform CRUD operations on the Employee

package FirstTask.presentation

import FirstTask.Application.EmployeeService
import FirstTask.infrastructure.persistence.DbInterface
import FirstTask.domain.model.Employee
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.SerializationException

fun Application.configureRouting(employeeService: EmployeeService) {
    routing {
        staticResources("/static", "static")

        route("/employees") {
//            call.respond( //respond is part of server-side app, extension func of applicationcall which is used to send HTTP response to the client, call- represents a single request response within the server
            get {
                val employees = employeeService.allEmployees()
                call.respond(employees)
            }
            get("/byID/{id}") {
                val id = call.parameters["id"]
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }
                val emp = employeeService.employeeSearchByID(id.toInt())
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
                val emp = employeeService.employeeSearchByName(name)
                if (emp == null) {
                    call.respond(HttpStatusCode.NotFound)
                    return@get
                }
                call.respond(emp)
            }
            post{
                try{
                    val emp = call.receive<Employee>()
                    employeeService.employeeJoining(emp)
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
                if(employeeService.employeeSearchByID(id.toInt()) != null){
                    employeeService.employeeEdit(update)
                    call.respond(HttpStatusCode.Created)
                } else try{
                    employeeService.employeeJoining(update)
                } catch (ex: IllegalStateException){
                    call.respond(HttpStatusCode.BadRequest)
                } catch (ex: SerializationException){
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
        }
    }
}


