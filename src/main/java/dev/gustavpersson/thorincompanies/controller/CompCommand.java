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
import java.sql.ResultSet;

public class CompCommand implements CommandExecutor {

    private final ThorinCompanies plugin;

    private Database database;

    public CompCommand(ThorinCompanies plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try{
            database = new Database(plugin);
            Player player = (Player) sender;

            Economy economy = plugin.getEconomy();

            if (args.length == 0) {
                player.sendMessage("ThorinTime plugin by ThorinEk");
                return true;
            }

            switch (args[0]) {
                case "bal" -> player.sendMessage("Ditt konto: " + economy.getBalance(player));
                case "create" -> createCommandHandler(player, args);
                case "list" -> listCommandHandler(player, args);

                default -> player.sendMessage("Invalido-argument");
            }

            return true;

        } catch (Exception exception){
            exception.printStackTrace();
            Chat.sendMessage((Player) sender, exception.getMessage());
            return false;
        }
    }

    private void createCommandHandler(Player player, String[] args) throws Exception {
        if (args.length < 2){
            Chat.sendMessage(player, "Du måste ange ett namn på företaget");
            return;
        }

        PreparedStatement query = database.getConnection().prepareStatement(
                "INSERT INTO companies (name) VALUES (?)");
        query.setString(1, args[1]);
        query.execute();

        Chat.sendMessage(player, "Företaget " + args[1] + " skapades");
    }

    private void listCommandHandler(Player player, String[] args) throws Exception {
        PreparedStatement query = database.getConnection().prepareStatement("SELECT * FROM companies");

        ResultSet companies = query.executeQuery();

        Chat.sendMessage(player, "&2Företag:");

        while(companies.next()){
            Chat.sendMessage(player, companies.getString("name"));
        }
    }

}
