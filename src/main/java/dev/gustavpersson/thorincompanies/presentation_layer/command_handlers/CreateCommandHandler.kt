package dev.gustavpersson.thorincompanies.presentation_layer.command_handlers

import dev.gustavpersson.thorincompanies.business_logic_layer.enums.ErrorCode
import dev.gustavpersson.thorincompanies.business_logic_layer.enums.MessageProp
import dev.gustavpersson.thorincompanies.business_logic_layer.exceptions.ThorinException
import dev.gustavpersson.thorincompanies.business_logic_layer.services.CompanyService
import dev.gustavpersson.thorincompanies.business_logic_layer.utils.Chat
import dev.gustavpersson.thorincompanies.presentation_layer.confirmations.CreateCompanyConfirmation
import dev.gustavpersson.thorincompanies.presentation_layer.managers.ConfirmationManager
import org.bukkit.entity.Player

class CreateCommandHandler : CommandHandler {
    private val companyService by lazy { CompanyService() }

    override fun execute(player: Player, args: Array<String>) {
        if (args.size < 2) {
            throw ThorinException(ErrorCode.COMP_NAME_NOT_SPECIFIED)
        }

        val companyName = args[1]
        val confirmation = CreateCompanyConfirmation(player, companyName)
        ConfirmationManager.addConfirmation(confirmation)
        val startupCost = companyService.getCostToStartCompany()
        Chat.sendConfirmableMessage(player, MessageProp.AWAITING_COMP_CREATION_CONFIRM, Chat.currency(startupCost))
    }
}
