package dev.gustavpersson.thorincompanies.business_logic_layer.services

import dev.gustavpersson.thorincompanies.business_logic_layer.exceptions.ErrorCode
import dev.gustavpersson.thorincompanies.business_logic_layer.exceptions.ThorinException
import dev.gustavpersson.thorincompanies.business_logic_layer.models.Company
import dev.gustavpersson.thorincompanies.business_logic_layer.models.NewCompany
import dev.gustavpersson.thorincompanies.business_logic_layer.models.UpdateCompanyRequest
import dev.gustavpersson.thorincompanies.data_access_layer.entities.CompanyEntity
import dev.gustavpersson.thorincompanies.data_access_layer.repositories.CompanyRepository
import org.bukkit.entity.Player
import java.time.LocalDate
import java.util.*

class CompanyService {
    private val companyRepository by lazy { CompanyRepository() }

    fun create(founder: Player, companyName: String): Company {

        //TODO verify that company with same name does not exist

        val existingCompany = companyRepository.findByName(companyName)

        if (existingCompany != null) {
            throw ThorinException(ErrorCode.COMP_NAME_ALREADY_EXISTS)
        }

        val newCompany = NewCompany(
            name = companyName,
            founderUUID = founder.uniqueId,
            createdAt = LocalDate.now()
        )
        val entity = companyRepository.create(newCompany)
        return entity.toCompany()
    }

    fun update(id: Int, name: String): Company {
        val request = UpdateCompanyRequest(id, name)
        val entity = companyRepository.update(request)
        return entity.toCompany()
    }

    fun delete(id: Int) {
        companyRepository.delete(id)
    }

    fun findById(id: Int): Company? {
        val entity = companyRepository.findById(id)
        return entity?.toCompany()
    }

    fun findAll(): List<Company> {
        val entities = companyRepository.findAll()
        return entities.map { it.toCompany() }
    }

    private fun CompanyEntity.toCompany() = Company(id.value, name, UUID.fromString(founderUUID), createdAt)
}
