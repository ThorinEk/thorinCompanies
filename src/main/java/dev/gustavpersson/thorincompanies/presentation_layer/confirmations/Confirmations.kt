package dev.gustavpersson.thorincompanies.presentation_layer.confirmations

import dev.gustavpersson.thorincompanies.business_logic_layer.services.CompanyService
import org.bukkit.entity.Player

data class DeleteCompanyConfirmation(override val player: Player, val companyId: Int) : Confirmation() {
    private val companyService by lazy { CompanyService() }
    override fun confirm() {
        executeConfirmation { companyService.delete(companyId, player) }
    }
}

data class CreateCompanyConfirmation(override val player: Player, val companyName: String) : Confirmation() {
    private val companyService by lazy { CompanyService() }

    override fun confirm() {
        executeConfirmation { companyService.create(player, companyName) }
    }

}