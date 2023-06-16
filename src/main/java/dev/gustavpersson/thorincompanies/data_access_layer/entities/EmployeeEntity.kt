package dev.gustavpersson.thorincompanies.data_access_layer.entities

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.date

object EmployeesTable: IntIdTable("thorin_employees") {
    val company = reference("company", CompaniesTable)
    val hiredDate = date("hiredDate")
}
