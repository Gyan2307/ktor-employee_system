package FirstTask.model

object EmployeeDb { //obj is singleton instance
    private val employees = mutableListOf(
        Employee(1,"aarav", "intern"),
        Employee(2,"ben", "intern"),
        Employee(3,"carl", "sde"),
        Employee(4,"Dap", "prod"),
        Employee(id = 5, "earn", "sde")
    )
    fun allEmployees(): List<Employee> = employees

    fun employeesByName(name: String) = employees.filter {
        it.name.equals(name, ignoreCase = true)
    }
    fun employeesById(id: Int) = employees.find {
        it.id == id
    }
    fun addEmployee(employee: Employee){
        if (employeesById(employee.id) != null){
            throw IllegalStateException("Cannot have duplicate ID")
        }
        employees.add(employee)
    }
    fun updateEmployee(employee: Employee){
        val index = employees.indexOfFirst {it.id == employee.id}
        if(index != -1){
            employees[index] = employee
        }else{
            throw IllegalStateException("Cannot find id")
        }
    }
}