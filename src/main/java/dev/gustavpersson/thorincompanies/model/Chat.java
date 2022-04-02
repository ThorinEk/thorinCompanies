package dev.gustavpersson.thorincompanies.model;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Chat {

    public static void sendMessage(Player player, String message) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

}
