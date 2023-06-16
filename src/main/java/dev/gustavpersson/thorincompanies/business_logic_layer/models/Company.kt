package dev.gustavpersson.thorincompanies.business_logic_layer.models

import dev.gustavpersson.thorincompanies.data_access_layer.entities.CompaniesTable
import org.joda.time.DateTime
import java.util.*

data class Company(
    val id: Int,
    val name: String,
    val founderUUID: UUID,
    val createdAt: DateTime
) {
    companion object {
        fun fromEntity(entity: CompaniesTable): Company {
            return Company(
                id = entity.id,
                name = entity.name,
                founderUUID = entity.founderUUID,
                createdAt = entity.createdAt
            )
        }
    }
}