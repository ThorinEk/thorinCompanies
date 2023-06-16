package dev.gustavpersson.thorincompanies.data_access_layer.repositories

import dev.gustavpersson.thorincompanies.business_logic_layer.models.NewCompany
import dev.gustavpersson.thorincompanies.business_logic_layer.models.UpdateCompanyRequest
import dev.gustavpersson.thorincompanies.data_access_layer.entities.CompanyEntity
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

class CompanyRepository {
    fun create(newCompany: NewCompany): CompanyEntity {
        return transaction {
            CompanyEntity.new {
                this.name = newCompany.name
                this.founderUUID = newCompany.founderUUID.toString()
                this.createdAt = LocalDate.now()
            }
        }
    }

    fun update(request: UpdateCompanyRequest): CompanyEntity {
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

    fun findAll(): List<CompanyEntity> {
        return transaction {
            CompanyEntity.all().toList()
        }
    }
}
