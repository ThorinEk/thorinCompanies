package dev.gustavpersson.thorincompanies.presentation_layer

import dev.gustavpersson.thorincompanies.ThorinCompanies
import dev.gustavpersson.thorincompanies.business_logic_layer.enums.Argument
import dev.gustavpersson.thorincompanies.business_logic_layer.enums.ErrorCode
import dev.gustavpersson.thorincompanies.business_logic_layer.enums.MessageProp
import dev.gustavpersson.thorincompanies.business_logic_layer.exceptions.ThorinException
import dev.gustavpersson.thorincompanies.business_logic_layer.services.CompanyService
import dev.gustavpersson.thorincompanies.business_logic_layer.utils.Chat
import dev.gustavpersson.thorincompanies.presentation_layer.managers.ConfirmationManager
import dev.gustavpersson.thorincompanies.presentation_layer.confirmations.CreateCompanyConfirmation
import dev.gustavpersson.thorincompanies.presentation_layer.confirmations.DeleteCompanyConfirmation
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class CommandController(private val plugin: ThorinCompanies) : TabExecutor {
    private val companyService by lazy { CompanyService() }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player){
            sender.sendMessage("This command can only be run by a player.")
            return false
        }

        return try {
            val economy = ThorinCompanies.economy
            when {
                args.isEmpty() -> {
                    Chat.sendMessage(sender, "Base-command for the ThorinCompanies plugin by ThorinEk")
                    return true
                }
                args[0] == Argument.BAL.arg -> Chat.sendMessage(
                    sender,
                    "Ditt konto: " + economy.getBalance(sender)
                )
                args[0] == Argument.CREATE.arg -> createCompanyHandler(sender, args)
                args[0] == Argument.LIST.arg -> listCompaniesHandler(sender, args)
                args[0] == Argument.CONFIRM.arg -> confirmHandler(sender, args)
                args[0] == Argument.LIQUIDATE.arg -> deleteCompanyHandler(sender, args)
                else -> Chat.sendMessage(sender, MessageProp.INVALID_ARGUMENT)
            }
            true
        } catch (exception: Exception) {
            when (exception) {
                is ThorinException -> {
                    Chat.sendMessage(sender, exception.code)
                }
                else -> {
                    plugin.logger.severe(exception.toString())
                    exception.printStackTrace()
                    Chat.sendMessage(sender, MessageProp.EXCEPTION_OCCURRED)
                }
            }
            false
        }
    }

    private fun createCompanyHandler(player: Player, args: Array<String>) {
        if (args.size < 2) {
            throw ThorinException(ErrorCode.COMP_NAME_NOT_SPECIFIED)
        }

        val companyName = args[1]

        val confirmation = CreateCompanyConfirmation(player, companyName)
        ConfirmationManager.addConfirmation(confirmation)
        val startupCost = companyService.getCostToStartCompany()
        Chat.sendConfirmableMessage(player, MessageProp.AWAITING_COMP_CREATION_CONFIRM, Chat.currency(startupCost))
    }

    private fun deleteCompanyHandler(player: Player, args: Array<String>) {
        if (args.size < 2) {
            throw ThorinException(ErrorCode.COMP_NAME_NOT_SPECIFIED)
        }

        val companyName = args[1]

        val company = companyService.findByName(companyName) ?: throw ThorinException(ErrorCode.COMP_NAME_NOT_FOUND)

        val confirmation = DeleteCompanyConfirmation(player, company.id)
        ConfirmationManager.addConfirmation(confirmation)
        Chat.sendConfirmableMessage(player, MessageProp.AWAIT_COMP_DELETION_CONFIRM, company.name)

    }

    private fun listCompaniesHandler(player: Player, args: Array<String>) {
        val companies = companyService.findAll()
        Chat.sendMessage(player, MessageProp.COMP_LIST_TITLE)
        for (company in companies) {
            val founderName = Bukkit.getOfflinePlayer(company.founderUUID).name ?: ""
            Chat.sendMessage(player, MessageProp.COMP_LIST_ITEM, company.name, company.createdAt, founderName)
        }
    }

    private fun confirmHandler(player: Player, args: Array<String>) {
        val confirmation = ConfirmationManager.getConfirmation(player)
        if (confirmation != null) {
            confirmation.confirm()
        } else {
            Chat.sendMessage(player, ErrorCode.NO_ACTIVE_CONFIRMATION)
        }
    }

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<String>): List<String> {
        if (args.size == 1) {
            return listOf(
                Argument.CREATE.arg,
                Argument.LIST.arg,
                Argument.BAL.arg,
                Argument.LIQUIDATE.arg)
        } else if (args.size == 2) {
            if (args[0] == Argument.LIQUIDATE.arg) {
                val foundedCompanies = companyService.findCompaniesFoundedByPlayer(sender as Player)
                return foundedCompanies.map { it.name }
            }

        }
        return emptyList()
    }
}