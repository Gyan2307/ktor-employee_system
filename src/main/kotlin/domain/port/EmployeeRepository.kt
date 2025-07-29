package FirstTask.domain.port

interface EmployeeRepository {
    fun getAllEmployees(): List<FirstTask.domain.model.Employee>
    fun updateEmployee(employee: FirstTask.domain.model.Employee): Int
    fun employeesByName(name: String): FirstTask.domain.model.Employee?
    fun employeesById(id: Int): FirstTask.domain.model.Employee?
    fun addEmployee(emp: FirstTask.domain.model.Employee): Int
}