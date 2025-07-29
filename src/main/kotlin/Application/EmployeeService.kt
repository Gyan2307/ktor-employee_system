package FirstTask.Application

import FirstTask.domain.model.Employees
import FirstTask.domain.port.EmployeeRepository

class EmployeeService(private val employeeRepository: EmployeeRepository) {
    fun allEmployees() = employeeRepository.getAllEmployees()

    fun employeeEdit(employee: FirstTask.domain.model.Employee): Int {
        return employeeRepository.updateEmployee(employee)
    }

    fun employeeSearchByName(name: String): FirstTask.domain.model.Employee? {
        return employeeRepository.employeesByName(name) ?:
            throw IllegalStateException("Employee with name $name not found")
    }

    fun employeeSearchByID(id: Int): FirstTask.domain.model.Employee? {
        return employeeRepository.employeesById(id) ?:
            throw IllegalStateException("Employee with id $id not found")
    }

    fun employeeJoining(emp: FirstTask.domain.model.Employee): Int {
        return employeeRepository.addEmployee(emp)
    }
}