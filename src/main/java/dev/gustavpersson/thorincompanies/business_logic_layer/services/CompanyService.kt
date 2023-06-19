package dev.gustavpersson.thorincompanies.business_logic_layer.services

import dev.gustavpersson.thorincompanies.ThorinCompanies
import dev.gustavpersson.thorincompanies.business_logic_layer.enums.ConfigProp
import dev.gustavpersson.thorincompanies.business_logic_layer.enums.ErrorCode
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

    private val configService by lazy { ConfigService() }

    fun create(founder: Player, companyName: String): Company {

        val existingCompany = companyRepository.findByName(companyName)

        if (existingCompany != null) {
            throw ThorinException(ErrorCode.COMP_NAME_ALREADY_EXISTS)
        }

        // TODO check so that player has not reached max companies
        if (playerOwnsMaxCompanies(founder)) {
            throw ThorinException(ErrorCode.MAX_COMPANIES_REACHED)
        }

        if (!playerCanAffordToStartCompany(founder)) {
            throw ThorinException(ErrorCode.INSUFFICIENT_FUNDS_TO_START_COMP)
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

    fun delete(id: Int, performingPlayer: Player) {

        //Verify that player owns the company
        val company = findById(id)
        if (performingPlayer.uniqueId != company?.founderUUID){
            throw ThorinException(ErrorCode.ONLY_FOUNDER_CAN_DELETE)
        }

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

    private fun playerCanAffordToStartCompany(player: Player): Boolean {
        val economy = ThorinCompanies.economy
        val founderBalance = economy.getBalance(player)

        val companyStartupCost = configService.getConfig(ConfigProp.COMPANY_STARTUP_COST) as Double

        return founderBalance > companyStartupCost
    }

    private fun playerOwnsMaxCompanies(player: Player): Boolean {
        val companyCount = companyRepository.findByFounder(player.uniqueId).size
        val maxCompanies = configService.getConfig(ConfigProp.MAX_COMPANIES_PER_PLAYER) as Int
        return companyCount >= maxCompanies
    }

    private fun CompanyEntity.toCompany() = Company(id.value, name, UUID.fromString(founderUUID), createdAt)
}
