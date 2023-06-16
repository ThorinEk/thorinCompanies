package dev.gustavpersson.thorincompanies.presentation_layer

import dev.gustavpersson.thorincompanies.ThorinCompanies
import dev.gustavpersson.thorincompanies.business_logic_layer.*
import dev.gustavpersson.thorincompanies.business_logic_layer.constants.Arguments
import dev.gustavpersson.thorincompanies.business_logic_layer.constants.MessageKeys
import dev.gustavpersson.thorincompanies.business_logic_layer.services.CompanyService
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class CompCommandController(private val plugin: ThorinCompanies) : TabExecutor {
    private val companyService by lazy { CompanyService() }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        return try {
            val player = sender as Player
            val economy = plugin.economy
            when {
                args.isEmpty() -> {
                    player.sendMessage("Base-command for the ThorinCompanies plugin by ThorinEk")
                    return true
                }
                args[0] == "bal" -> player.sendMessage("Ditt konto: " + economy.getBalance(player))
                args[0] == "create" -> createCompanyHandler(player, args)
                args[0] == "list" -> listCompaniesHandler(player, args)
                else -> player.sendMessage(ThorinCompanies.getMessagesConfig()?.getString(MessageKeys.INVALID_ARGUMENT))
            }
            true
        } catch (exception: Exception) {
            when (exception) {
                is ThorinException -> {
                    Chat.sendMessage(sender as Player, exception.toString())
                }
                else -> {
                    plugin.logger.severe(exception.toString())
                    exception.printStackTrace()
                    Chat.sendMessage(sender as Player, ThorinCompanies.getMessagesConfig()?.getString(MessageKeys.EXCEPTION_OCCURRED))
                }
            }
            false
        }
    }

    private fun createCompanyHandler(player: Player, args: Array<String>) {
        if (args.size < 2) {
            Chat.sendMessage(player, "Du måste ange ett namn på företaget")
            return
        }
        val companyName = args[1]
        companyService.create(player, companyName)
        Chat.sendMessage(player, "Företaget " + args[1] + " skapades")
    }

    private fun listCompaniesHandler(player: Player, args: Array<String>) {
        val companies = companyService.findAll()
        Chat.sendMessage(player, "&2Företag:")
        for (company in companies) {
            Chat.sendMessage(player, "${company.name}, Grundat ${company.createdAt} av ${Bukkit.getOfflinePlayer(company.founderUUID) }}")
        }
    }

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<String>): List<String> {
        return if (args.size == 1) {
            listOf(Arguments.CREATE, Arguments.LIST, Arguments.BAL)
        } else emptyList()
    }
}