package dev.gustavpersson.thorincompanies.business_logic_layer.utils

import dev.gustavpersson.thorincompanies.business_logic_layer.enums.MessageProp
import dev.gustavpersson.thorincompanies.business_logic_layer.services.ConfigService
import org.bukkit.ChatColor
import org.bukkit.entity.Player

object ChatUtility {
    private val configService by lazy { ConfigService() }

    fun sendMessage(player: Player, message: String, vararg values: Any) {
        val formattedMessage = String.format(message, *values)
        val prefix = configService.getMessage(MessageProp.CHAT_PREFIX) as String
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + formattedMessage))
    }

    fun sendMessage(player: Player, messageProp: MessageProp, vararg values: Any) {
        val message = configService.getMessage(messageProp) as String
        val formattedMessage = String.format(message, *values)
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', formattedMessage))
    }
}