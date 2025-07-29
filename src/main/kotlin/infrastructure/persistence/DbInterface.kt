package FirstTask.infrastructure.persistence

import FirstTask.domain.model.Employee
import FirstTask.domain.port.EmployeeRepository
import FirstTask.infrastructure.model.EmployeeTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.name
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import kotlin.text.get

class DbInterface: EmployeeRepository {
    fun initDb() {
        Database.Companion.connect(
//            "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;", //this was for h2 connection
//            driver = "org.h2.Driver",
//            user = "sa",
//            password = ""
            url = "jdbc:postgresql://localhost:5432/my_database",
            user = "postgres",
            password = ""
        )

        transaction {
            SchemaUtils.create(EmployeeTable)
            EmployeeTable.insert {
                it[name] = "Jane Dev"
                it[designation] = "Developer"
            }
            println("Initialized shared in-memory PQ DB")
        }
    }

    override fun getAllEmployees(): List<Employee>{
        var empList = mutableListOf<Employee>()
        transaction {
            EmployeeTable.selectAll().map {
                empList.add(
                    Employee(it[EmployeeTable.id], it[EmployeeTable.name], it[EmployeeTable.designation])
                )
            }
        }
        return empList
    }
    override fun updateEmployee(employee: Employee): Int{
//        val index = employees.indexOfFirst {it.id == employee.id}
//        if(index != -1){
//            employees[index] = employee
//        }else{
//            throw IllegalStateException("Cannot find id")
//        }
        var rowsAffected = 0
        transaction {
            rowsAffected = EmployeeTable.update(
                {
                    EmployeeTable.id eq employee.id
                }
            ) {
                it[EmployeeTable.designation] = employee.designation
                it[EmployeeTable.name] = employee.name
            }
        }
        return rowsAffected
    }
    override fun employeesByName(name: String): Employee? {
        var emp: Employee? = null
        transaction {
            EmployeeTable.select { (EmployeeTable.name eq name) }
                .limit(1)
                .map {
                    emp = Employee(it[EmployeeTable.id], it[EmployeeTable.name], it[EmployeeTable.designation])
                }
        }
        return emp
    }
    override fun employeesById(id: Int): Employee? {
        var emp: Employee? = null
        transaction {
            EmployeeTable.select { (EmployeeTable.id eq id) }
                .limit(1)
                .map {
                    emp = Employee(it[EmployeeTable.id], it[EmployeeTable.name], it[EmployeeTable.designation])
                }
        }
        return emp
    }
    override fun addEmployee(emp: Employee): Int{
        var id = -1
        transaction {
            id = EmployeeTable.insert {
                it[name] = emp.name
                it[designation] = emp.designation
            } get EmployeeTable.id
        }
        return id
    }
//    fun allEmployees(): List<Employee> = employees

//    fun employeesByName(name: String) = employees.filter {
//        it.name.equals(name, ignoreCase = true)
//    }
//    fun employeesById(id: Int) = employees.find {
//        it.id == id
//    }
}