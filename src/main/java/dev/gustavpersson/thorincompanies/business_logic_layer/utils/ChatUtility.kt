package dev.gustavpersson.thorincompanies.business_logic_layer.utils

import dev.gustavpersson.thorincompanies.business_logic_layer.enums.ErrorCode
import dev.gustavpersson.thorincompanies.business_logic_layer.enums.MessageProp
import dev.gustavpersson.thorincompanies.presentation_layer.ErrorTranslator
import dev.gustavpersson.thorincompanies.presentation_layer.managers.ConfigManager
import org.bukkit.ChatColor
import org.bukkit.entity.Player

object ChatUtility {
    private val configManager by lazy { ConfigManager() }

    fun sendMessage(player: Player, message: String, vararg values: Any) {
        val formattedMessage = String.format(message, *values)
        val prefix = configManager.getMessage(MessageProp.CHAT_PREFIX) as String
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + formattedMessage))
    }

    fun sendMessage(player: Player, messageProp: MessageProp, vararg values: Any) {
        val message = configManager.getMessage(messageProp) as String
        val formattedMessage = String.format(message, *values)
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', formattedMessage))
    }

    fun sendMessage(player: Player, errorCode: ErrorCode) {
        val errorMessage = ErrorTranslator.getErrorMessage(errorCode)
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', errorMessage))
    }

}