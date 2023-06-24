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
import dev.gustavpersson.thorincompanies.presentation_layer.managers.ConfigManager
import org.bukkit.entity.Player
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

class CompanyService {
    private val companyRepository by lazy { CompanyRepository() }

    private val configManager by lazy { ConfigManager() }

    fun create(founder: Player, companyName: String): Company {

        val existingCompany = companyRepository.findByName(companyName)

        if (existingCompany != null) {
            throw ThorinException(ErrorCode.COMP_NAME_ALREADY_EXISTS)
        }

        if (playerOwnsMaxCompanies(founder)) {
            throw ThorinException(ErrorCode.MAX_COMPANIES_REACHED)
        }

        if (!playerCanAffordToStartCompany(founder)) {
            throw ThorinException(ErrorCode.INSUFFICIENT_FUNDS_TO_START_COMP)
        }

        val companyStartupCost = getCostToStartCompany()

        val newCompany = NewCompany(
            name = companyName,
            founderUUID = founder.uniqueId,
            startupCapital = companyStartupCost,
            createdAt = LocalDate.now()
        )
        val entity = companyRepository.create(newCompany)

        ThorinCompanies.economy.withdrawPlayer(founder, companyStartupCost.toDouble())

        return entity.toCompany()
    }

    fun update(id: Int, name: String): Company {
        val request = UpdateCompanyRequest(id, name)
        val entity = companyRepository.update(request)
        return entity.toCompany()
    }

    fun findByName(name: String): Company? {
        return companyRepository.findByName(name)?.toCompany()
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

        return founderBalance.toBigDecimal() > getCostToStartCompany()
    }

    private fun playerOwnsMaxCompanies(player: Player): Boolean {
        val companyCount = companyRepository.findByFounder(player.uniqueId).size
        val maxCompanies = configManager.getConfig(ConfigProp.MAX_COMPANIES_PER_PLAYER) as Int
        return companyCount >= maxCompanies
    }

    fun getCostToStartCompany(): BigDecimal {
        return when (val costObject = configManager.getConfig(ConfigProp.COMPANY_STARTUP_COST)) {
            is Int -> BigDecimal.valueOf(costObject.toLong())
            is Double -> BigDecimal.valueOf(costObject)
            else -> throw IllegalArgumentException("Invalid ${ConfigProp.COMPANY_STARTUP_COST.key} value")
        }
    }

    private fun CompanyEntity.toCompany() =
        Company(id.value, name, UUID.fromString(founderUUID), startupCapital, createdAt)
}
