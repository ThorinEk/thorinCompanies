package dev.gustavpersson.thorincompanies.business_logic_layer

import dev.gustavpersson.thorincompanies.ThorinCompanies
import dev.gustavpersson.thorincompanies.business_logic_layer.models.Company
import dev.gustavpersson.thorincompanies.data_access_layer.entities.CompanyEntity
import dev.gustavpersson.thorincompanies.data_access_layer.repositories.CompanyRepository
import org.bukkit.entity.Player
import org.modelmapper.ModelMapper
import java.sql.SQLException
import java.util.*
import java.util.stream.Collectors

class CompanyManager(plugin: ThorinCompanies?) {
    private val companyRepository: CompanyRepository
    private val modelMapper: ModelMapper

    init {
        companyRepository = CompanyRepository(plugin)
        modelMapper = ModelMapper()
    }

    @Throws(Exception::class)
    fun createCompany(founder: Player, name: String?) {
        val company = Company()
        company.setName(name)
        company.setFounderUUID(founder.uniqueId)
        company.createdAt = Date()
        val companyEntity = modelMapper.map(company, CompanyEntity::class.java)
        companyRepository.createCompany(companyEntity)
    }

    @get:Throws(SQLException::class)
    val allCompanies: List<Company>
        get() {
            val companyEntities = companyRepository.allCompanies
            return companyEntities!!.stream()
                    .map { entity: CompanyEntity? -> modelMapper.map(entity, Company::class.java) }
                    .collect(Collectors.toList())
        }
}
