package dev.gustavpersson.thorincompanies.business_logic_layer.services

import dev.gustavpersson.thorincompanies.business_logic_layer.models.Company
import dev.gustavpersson.thorincompanies.business_logic_layer.models.NewCompany
import dev.gustavpersson.thorincompanies.business_logic_layer.models.UpdateCompanyRequest
import dev.gustavpersson.thorincompanies.data_access_layer.entities.CompanyEntity
import dev.gustavpersson.thorincompanies.data_access_layer.repositories.CompanyRepository
import org.bukkit.entity.Player
import java.time.LocalDate
import java.util.*

class CompanyService {
    private val repository by lazy { CompanyRepository() }

    fun create(founder: Player, companyName: String): Company {
        val newCompany = NewCompany(
            name = companyName,
            founderUUID = founder.uniqueId,
            createdAt = LocalDate.now()
        )
        val entity = repository.create(newCompany)
        return entity.toCompany()
    }

    fun update(id: Int, name: String): Company {
        val request = UpdateCompanyRequest(id, name)
        val entity = repository.update(request)
        return entity.toCompany()
    }

    fun delete(id: Int) {
        repository.delete(id)
    }

    fun findById(id: Int): Company? {
        val entity = repository.findById(id)
        return entity?.toCompany()
    }

    fun findAll(): List<Company> {
        val entities = repository.findAll()
        return entities.map { it.toCompany() }
    }

    private fun CompanyEntity.toCompany() = Company(id.value, name, UUID.fromString(founderUUID), createdAt)
}
