package dev.gustavpersson.thorincompanies.business_logic_layer

import dev.gustavpersson.thorincompanies.ThorinCompanies
import dev.gustavpersson.thorincompanies.business_logic_layer.models.Company
import dev.gustavpersson.thorincompanies.data_access_layer.entities.CompaniesTable
import dev.gustavpersson.thorincompanies.data_access_layer.repositories.CompanyRepository
import org.bukkit.entity.Player
import org.modelmapper.ModelMapper
import java.util.*
import java.util.stream.Collectors

class CompanyService(plugin: ThorinCompanies?) {
    private val companyRepository: CompanyRepository
    private val modelMapper: ModelMapper

    init {
        companyRepository = CompanyRepository(plugin)
        modelMapper = ModelMapper()
    }

    fun createCompany(founder: Player, name: String?) {
        val company = Company()
        company.name = name
        company.founderUUID = founder.uniqueId
        company.createdAt = Date()
        val companyEntity = modelMapper.map(company, CompaniesTable::class.java)
        companyRepository.createCompany(companyEntity)
    }

    val allCompanies: List<Company>
        get() {
            val companyEntities = companyRepository.allCompanies
            return companyEntities.stream()
                    .map { entity: CompaniesTable? -> modelMapper.map(entity, Company::class.java) }
                    .collect(Collectors.toList())
        }
}
