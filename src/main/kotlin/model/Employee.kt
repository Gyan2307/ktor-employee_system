package FirstTask.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable //helps in easy conversion to JSON or XML
data class Employee (
    val id: Int,
    val name: String,
    val designation: String
)

const val MAX_VARCHAR_LENGTH = 128

object Employees : Table("employee") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", MAX_VARCHAR_LENGTH)
    val designation = varchar("designation", MAX_VARCHAR_LENGTH)
}