package dev.gustavpersson.thorincompanies.presentation_layer.confirmations

import dev.gustavpersson.thorincompanies.business_logic_layer.enums.MessageProp
import dev.gustavpersson.thorincompanies.business_logic_layer.services.CompanyService
import dev.gustavpersson.thorincompanies.business_logic_layer.utils.Chat
import org.bukkit.entity.Player

data class DeleteCompanyConfirmation(override val player: Player, val companyId: Int) : Confirmation() {
    private val companyService by lazy { CompanyService() }
    override fun confirm() {
        val company = companyService.findById(companyId)
        executeConfirmation { companyService.delete(companyId, player) }
        company?.name?.let { Chat.sendMessage(player, MessageProp.COMPANY_DELETED, it) }
    }
}

data class CreateCompanyConfirmation(override val player: Player, val companyName: String) : Confirmation() {
    private val companyService by lazy { CompanyService() }

    override fun confirm() {
        executeConfirmation { companyService.create(player, companyName) }
        Chat.sendMessage(player, MessageProp.COMPANY_CREATED, companyName)
    }

}