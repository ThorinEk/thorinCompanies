package dev.gustavpersson.thorincompanies.data_access_layer.repositories

import dev.gustavpersson.thorincompanies.business_logic_layer.models.NewCompany
import dev.gustavpersson.thorincompanies.business_logic_layer.models.RenameCompanyRequest
import dev.gustavpersson.thorincompanies.data_access_layer.entities.CompaniesTable
import dev.gustavpersson.thorincompanies.data_access_layer.entities.CompanyEntity
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate
import java.util.UUID

class CompanyRepository {
    fun create(newCompany: NewCompany): CompanyEntity {
        return transaction {
            CompanyEntity.new {
                this.name = newCompany.name
                this.founderUUID = newCompany.founderUUID.toString()
                this.startupCapital = newCompany.startupCapital
                this.createdAt = LocalDate.now()
            }
        }
    }

    fun update(request: RenameCompanyRequest): CompanyEntity {
        return transaction {
            val entity = CompanyEntity.findById(request.id) ?: throw Exception("Company not found")
            entity.name = request.name
            entity
        }
    }

    fun delete(id: Int) {
        transaction {
            CompanyEntity.findById(id)?.delete() ?: throw Exception("Company not found")
        }
    }

    fun findById(id: Int): CompanyEntity? {
        return transaction {
            CompanyEntity.findById(id)
        }
    }

    fun findByName(name: String): CompanyEntity? {
        return transaction {
            CompanyEntity.find { CompaniesTable.name eq name }.singleOrNull()
        }
    }

    fun findByFounder(founderUUID: UUID): List<CompanyEntity> {
        return transaction {
            CompanyEntity.find { CompaniesTable.founderUUID eq founderUUID.toString() }.toList()
        }
    }

    fun findAll(): List<CompanyEntity> {
        return transaction {
            CompanyEntity.all().toList()
        }
    }
}
