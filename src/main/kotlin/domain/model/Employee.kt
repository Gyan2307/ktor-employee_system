package FirstTask.domain.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable //helps in easy conversion to JSON or XML
data class Employee (
    val id: Int,
    val name: String,
    val designation: String
)