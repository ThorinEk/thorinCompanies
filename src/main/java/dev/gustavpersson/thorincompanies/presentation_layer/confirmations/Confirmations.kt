package dev.gustavpersson.thorincompanies.presentation_layer.confirmations

import dev.gustavpersson.thorincompanies.business_logic_layer.enums.ErrorCode
import dev.gustavpersson.thorincompanies.business_logic_layer.enums.MessageProp
import dev.gustavpersson.thorincompanies.business_logic_layer.exceptions.ThorinException
import dev.gustavpersson.thorincompanies.business_logic_layer.services.CompanyService
import dev.gustavpersson.thorincompanies.business_logic_layer.utils.Chat
import org.bukkit.entity.Player

data class DeleteCompanyConfirmation(override val player: Player, val companyId: Int) : Confirmation() {
    private val companyService by lazy { CompanyService() }
    override fun confirm() {
        val company = companyService.findById(companyId) ?: throw ThorinException(ErrorCode.COMP_NAME_NOT_FOUND)
        executeConfirmation { companyService.delete(companyId, player) }
        Chat.sendMessage(player, MessageProp.COMPANY_DELETED, company.name, company.startupCapital)
    }
}

data class CreateCompanyConfirmation(override val player: Player, val companyName: String) : Confirmation() {
    private val companyService by lazy { CompanyService() }

    override fun confirm() {
        executeConfirmation { companyService.create(player, companyName) }

        val startupCost = companyService.getCostToStartCompany()
        Chat.sendMessage(player, MessageProp.COMPANY_CREATED, companyName, Chat.currency(startupCost))
    }

}