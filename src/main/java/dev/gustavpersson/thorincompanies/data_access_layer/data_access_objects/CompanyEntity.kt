package dev.gustavpersson.thorincompanies.data_access_layer.data_access_objects

import dev.gustavpersson.thorincompanies.business_logic_layer.models.Company
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.jodatime.datetime
import java.util.*

object CompanyEntity: IntIdTable("thorin_companies") {

    val name  = varchar("name", 25)
    val founderUUID = char("founderUUID", 36)
    val createdAt = datetime("createdAt")

}
