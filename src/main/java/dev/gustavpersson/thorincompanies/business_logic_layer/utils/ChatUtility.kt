package dev.gustavpersson.thorincompanies.business_logic_layer.utils

import dev.gustavpersson.thorincompanies.ThorinCompanies
import org.bukkit.ChatColor
import org.bukkit.entity.Player

object ChatUtility {
    fun sendMessage(player: Player, messageKey: String, vararg values: Any) {
        val message = ThorinCompanies.messagesConfig.getString(messageKey)
        val formattedMessage = String.format(message!!, *values)
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', formattedMessage))
    }
}