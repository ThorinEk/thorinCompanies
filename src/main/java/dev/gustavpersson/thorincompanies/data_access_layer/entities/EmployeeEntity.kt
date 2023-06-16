package dev.gustavpersson.thorincompanies.data_access_layer.entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.date

object EmployeesTable: IntIdTable("thorin_employees") {
    val company = reference("company", CompaniesTable)
    val hiredDate = date("hiredDate")
}
class EmployeeEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<EmployeeEntity>(EmployeesTable)
    var company by EmployeesTable.company
    var hiredDate by EmployeesTable.hiredDate
}
