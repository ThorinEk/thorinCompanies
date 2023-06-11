package dev.gustavpersson.thorincompanies.presentation_layer;

import dev.gustavpersson.thorincompanies.ThorinCompanies;
import dev.gustavpersson.thorincompanies.business_logic_layer.*;
import dev.gustavpersson.thorincompanies.business_logic_layer.models.Company;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.*;

public class CompCommand implements TabExecutor {

    private final ThorinCompanies plugin;

    CompanyManager companyManager;

    public CompCommand(ThorinCompanies plugin) throws SQLException, Exception {
        this.plugin = plugin;
        this.companyManager = new CompanyManager(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try{
            Player player = (Player) sender;

            Economy economy = plugin.getEconomy();

            if (args.length == 0) {
                player.sendMessage("Base-command for the ThorinCompanies plugin by ThorinEk");
                return true;
            }

            switch (args[0]) {
                case "bal" -> player.sendMessage("Ditt konto: " + economy.getBalance(player));
                case "create" -> createCompanyHandler(player, args);
                case "list" -> listCompaniesHandler(player, args);

                default -> player.sendMessage(Objects.requireNonNull(plugin.getMessagesConfig().getString(MessageKeys.INVALID_ARGUMENT)));
            }

            return true;

        } catch (Exception exception){
            if (exception instanceof ThorinException){
                Chat.sendMessage((Player) sender, exception.toString());
            } else {
                plugin.getLogger().severe(exception.toString());
                exception.printStackTrace();
                Chat.sendMessage((Player) sender, plugin.getMessagesConfig().getString(MessageKeys.EXCEPTION_OCCURRED));
            }
            return false;
        }
    }

    private void createCompanyHandler(Player player, String[] args) throws Exception {
        if (args.length < 2){
            Chat.sendMessage(player, "Du måste ange ett namn på företaget");
            return;
        }
        String companyName = args[1];

        companyManager.createCompany(player, companyName);

        Chat.sendMessage(player, "Företaget " + args[1] + " skapades");
    }

    private void listCompaniesHandler(Player player, String[] args) throws Exception {

        List<Company> companies = companyManager.getAllCompanies();

        Chat.sendMessage(player, "&2Företag:");

        for (Company company : companies) {
            Chat.sendMessage(player, company.getName() + ", Grundat " + company.getCreatedAt().toString() + " av " + Bukkit.getOfflinePlayer(company.getFounderUUID()));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1){
            return new ArrayList<>(Arrays.asList(Arguments.CREATE, Arguments.LIST, Arguments.BAL));
        }

        return Collections.emptyList();
    }
}
