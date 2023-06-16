package dev.gustavpersson.thorincompanies.data_access_layer.entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.date

object CompaniesTable: IntIdTable("thorin_companies") {
    val name  = varchar("name", 25)
    val founderUUID = char("founderUUID", 36)
    val createdAt = date("createdAt")
}
class CompanyEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<CompanyEntity>(CompaniesTable)
    var name by CompaniesTable.name
    var founderUUID by CompaniesTable.founderUUID
    var createdAt by CompaniesTable.createdAt
}
