package FirstTask.model
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class DbInterface {
    fun initDb(){
        Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver").also {
            transaction(it) {
                SchemaUtils.create(Employees)
            }
        }
    }
    fun allEmployees(): List<Employee>{
        var empList = mutableListOf<Employee>()
        transaction {
            Employees.selectAll().map{
                empList.add(
                    Employee(it[Employees.id], it[Employees.name], it[Employees.designation])
                )
            }
        }
        return empList
    }
    fun updateEmployee(employee: Employee): Int{
//        val index = employees.indexOfFirst {it.id == employee.id}
//        if(index != -1){
//            employees[index] = employee
//        }else{
//            throw IllegalStateException("Cannot find id")
//        }
        var rowsAffected = 0
        transaction {
            rowsAffected = Employees.update(
                {
                    Employees.id eq employee.id
                }
            ){
                it[Employees.designation] = employee.designation
                it[Employees.name] = employee.name
            }
        }
        return rowsAffected
    }
    fun employeesByName(name: String): Employee? {
        var emp: Employee? = null
        transaction {
            Employees.select {(Employees.name eq name)}
                .limit(1)
                .map{
                    emp = Employee(it[Employees.id],it[Employees.name], it[Employees.designation])
                }
        }
        return emp
    }
    fun employeesById(id: Int): Employee? {
        var emp: Employee? = null
        transaction {
            Employees.select {(Employees.id eq id)}
                .limit(1)
                .map{
                    emp = Employee(it[Employees.id],it[Employees.name], it[Employees.designation])
                }
        }
        return emp
    }
//    fun allEmployees(): List<Employee> = employees

//    fun employeesByName(name: String) = employees.filter {
//        it.name.equals(name, ignoreCase = true)
//    }
//    fun employeesById(id: Int) = employees.find {
//        it.id == id
//    }
}