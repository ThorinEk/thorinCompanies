package dev.gustavpersson.thorincompanies.presentation_layer.command_handlers

import dev.gustavpersson.thorincompanies.business_logic_layer.enums.ErrorCode
import dev.gustavpersson.thorincompanies.business_logic_layer.enums.MessageProp
import dev.gustavpersson.thorincompanies.business_logic_layer.exceptions.ThorinException
import dev.gustavpersson.thorincompanies.business_logic_layer.services.CompanyService
import dev.gustavpersson.thorincompanies.business_logic_layer.utils.Chat
import dev.gustavpersson.thorincompanies.presentation_layer.confirmations.DeleteCompanyConfirmation
import dev.gustavpersson.thorincompanies.presentation_layer.managers.ConfirmationManager
import org.bukkit.entity.Player

class DeleteCompanyHandler : CommandHandler {
    private val companyService by lazy { CompanyService() }

    override fun execute(player: Player, args: Array<String>) {
        if (args.size < 2) {
            throw ThorinException(ErrorCode.COMP_NAME_NOT_SPECIFIED)
        }

        val companyName = args[1]

        val company = companyService.findByName(companyName) ?: throw ThorinException(ErrorCode.COMP_NAME_NOT_FOUND)

        val confirmation = DeleteCompanyConfirmation(player, company.id)
        ConfirmationManager.addConfirmation(confirmation)
        Chat.sendConfirmableMessage(player, MessageProp.AWAIT_COMP_DELETION_CONFIRM, company.name)
    }
}