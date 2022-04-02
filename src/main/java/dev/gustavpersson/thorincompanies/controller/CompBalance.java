package dev.gustavpersson.thorincompanies.controller;

import dev.gustavpersson.thorincompanies.ThorinCompanies;
import dev.gustavpersson.thorincompanies.database.Database;
import dev.gustavpersson.thorincompanies.model.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;

public class CompBalance implements CommandExecutor {

    private final ThorinCompanies plugin;

    public CompBalance(ThorinCompanies _plugin){
        plugin = _plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try{
            Player player = (Player) sender;

            Economy economy = plugin.getEconomy();

            if (args.length == 0) {
                player.sendMessage("ThorinTime plugin by ThorinEk");
                return true;
            }

            switch (args[0]) {
                case "bal" -> player.sendMessage("Ditt konto: " + economy.getBalance(player));
                case "create" -> createCommandHandler(player, args);

                default -> player.sendMessage("Invalido-argument");
            }

            return true;

        } catch (Exception exception){
            Chat.sendMessage((Player) sender, exception.getMessage());
            return false;
        }
    }

    private void createCommandHandler(Player player, String[] args) throws Exception {
        if (args[1].isEmpty()){
            Chat.sendMessage(player, "Du måste ange ett namn på företaget");
            return;
        }

        Database database = new Database(plugin);

        PreparedStatement query = database.getConnection().prepareStatement(
                "INSERT INTO companies (name) VALUES (?)");
        query.setString(0, args[1]);
        query.execute();
    }
}
