package dev.gustavpersson.thorincompanies.presentation_layer.confirmations

import dev.gustavpersson.thorincompanies.business_logic_layer.services.CompanyService
import org.bukkit.entity.Player

data class DeleteCompanyConfirmation(val companyId: Int, val performingPlayer: Player) : Confirmation() {
    private val companyService by lazy { CompanyService() }
    override fun confirm() {
        executeConfirmation { companyService.delete(companyId, performingPlayer) }
    }
}

data class CreateCompanyConfirmation(val companyName: String, val founder: Player) : Confirmation() {
    private val companyService by lazy { CompanyService() }

    override fun confirm() {
        executeConfirmation { companyService.create(founder, companyName) }
    }

}