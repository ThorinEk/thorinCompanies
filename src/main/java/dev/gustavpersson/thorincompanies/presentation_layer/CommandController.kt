package dev.gustavpersson.thorincompanies.presentation_layer

import dev.gustavpersson.thorincompanies.ThorinCompanies
import dev.gustavpersson.thorincompanies.business_logic_layer.constants.Arguments
import dev.gustavpersson.thorincompanies.business_logic_layer.constants.MessageKeys
import dev.gustavpersson.thorincompanies.business_logic_layer.exceptions.ThorinException
import dev.gustavpersson.thorincompanies.business_logic_layer.services.CompanyService
import dev.gustavpersson.thorincompanies.business_logic_layer.utils.ChatUtility
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import java.util.*
import kotlin.collections.HashMap

class CommandController(private val plugin: ThorinCompanies) : TabExecutor {
    private val companyService by lazy { CompanyService() }

    // Map to store actions awaiting confirmation
    private val confirmationMap = HashMap<UUID, String>()

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        return try {
            val player = sender as Player
            val economy = ThorinCompanies.economy
            when {
                args.isEmpty() -> {
                    ChatUtility.sendMessage(player, "Base-command for the ThorinCompanies plugin by ThorinEk")
                    return true
                }
                args[0] == "bal" -> ChatUtility.sendMessage(player,"Ditt konto: " + economy.getBalance(player))
                args[0] == "create" -> createCompanyHandler(player, args)
                args[0] == "list" -> listCompaniesHandler(player, args)
                args[0] == "confirm" -> confirmHandler(player, args)
                else -> ChatUtility.sendMessage(player, MessageKeys.INVALID_ARGUMENT)
            }
            true
        } catch (exception: Exception) {
            when (exception) {
                is ThorinException -> {
                    ChatUtility.sendMessage(sender as Player, exception.message.toString())
                }
                else -> {
                    plugin.logger.severe(exception.toString())
                    exception.printStackTrace()
                    ChatUtility.sendMessage(sender as Player, MessageKeys.EXCEPTION_OCCURRED)
                }
            }
            false
        }
    }

    private fun createCompanyHandler(player: Player, args: Array<String>) {
        if (args.size < 2) {
            ChatUtility.sendMessage(player, "Du måste ange ett namn på företaget")
            return
        }
        val companyName = args[1]

        // TODO check if player has enough money to start


        // Store company creation in the confirmation map
        confirmationMap[player.uniqueId] = companyName
        ChatUtility.sendMessage(player, "Företaget " + args[1] + " kommer att skapas. Bekräfta med /com confirm")
    }

    private fun listCompaniesHandler(player: Player, args: Array<String>) {
        val companies = companyService.findAll()
        ChatUtility.sendMessage(player, "&2Företag:")
        for (company in companies) {
            ChatUtility.sendMessage(
                player,
                "${company.name}, " +
                "Grundat ${company.createdAt} av " +
                "${Bukkit.getOfflinePlayer(company.founderUUID).name }}"
            )
        }
    }

    private fun confirmHandler(player: Player, args: Array<String>) {
        val companyName = confirmationMap[player.uniqueId]
        if (companyName == null) {
            ChatUtility.sendMessage(player, "Inget företag att bekräfta")
            return
        }
        // Proceed with company creation
        companyService.create(player, companyName)
        ChatUtility.sendMessage(player, "Företaget $companyName skapades")
        // Remove player's action from the confirmation map
        confirmationMap.remove(player.uniqueId)
    }

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<String>): List<String> {
        return if (args.size == 1) {
            listOf(Arguments.CREATE, Arguments.LIST, Arguments.BAL)
        } else emptyList()
    }
}