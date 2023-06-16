package dev.gustavpersson.thorincompanies.business_logic_layer.models

import dev.gustavpersson.thorincompanies.data_access_layer.entities.CompaniesTable
import java.time.LocalDate
import java.util.*

data class Company(
    val id: Int,
    val name: String,
    val founderUUID: UUID,
    val createdAt: LocalDate
)

data class NewCompany(
    val name: String,
    val founderUUID: UUID,
    val createdAt: LocalDate
)

data class UpdateCompanyRequest(
    val id: Int,
    val name: String
)
