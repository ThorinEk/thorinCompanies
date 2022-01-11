package dev.gustavpersson.thorincompanies.commands;

import dev.gustavpersson.thorincompanies.ThorinCompanies;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CompBalance implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            Economy economy = ThorinCompanies.getEconomy();

            if (args.length == 0) {
                player.sendMessage(ChatColor.GREEN + "Current balance: " + economy.getBalance(player));
            }
        }

        return true;
    }
}
