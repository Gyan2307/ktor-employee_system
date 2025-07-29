package FirstTask.infrastructure.model

import org.jetbrains.exposed.sql.Table

const val MAX_VARCHAR_LENGTH = 128

object EmployeeTable : Table("employee") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", MAX_VARCHAR_LENGTH)
    val designation = varchar("designation", MAX_VARCHAR_LENGTH)
}
