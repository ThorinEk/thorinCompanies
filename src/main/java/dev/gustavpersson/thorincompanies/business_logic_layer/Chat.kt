package dev.gustavpersson.thorincompanies.business_logic_layer

import org.bukkit.ChatColor
import org.bukkit.entity.Player

object Chat {
    fun sendMessage(player: Player, message: String?) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message!!))
    }
}
