package dev.gustavpersson.thorincompanies.business_logic_layer.models

import dev.gustavpersson.thorincompanies.data_access_layer.data_access_objects.CompanyEntity
import org.joda.time.DateTime
import java.util.*

data class Company(
    val id: Int,
    val name: String,
    val founderUUID: UUID,
    val createdAt: DateTime
) {
    companion object {
        fun fromEntity(entity: CompanyEntity): Company {
            return Company(
                id = entity.id,
                name = entity.name,
                founderUUID = entity.founderUUID,
                createdAt = entity.createdAt
            )
        }
    }
}