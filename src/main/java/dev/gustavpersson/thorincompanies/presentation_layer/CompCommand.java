package dev.gustavpersson.thorincompanies.presentation_layer;

import dev.gustavpersson.thorincompanies.ThorinCompanies;
import dev.gustavpersson.thorincompanies.business_logic_layer.Arguments;
import dev.gustavpersson.thorincompanies.data_access_layer.Database;
import dev.gustavpersson.thorincompanies.business_logic_layer.Chat;
import dev.gustavpersson.thorincompanies.business_logic_layer.Company;
import dev.gustavpersson.thorincompanies.data_access_layer.CompanyRepository;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CompCommand implements TabExecutor {

    private final ThorinCompanies plugin;

    private Database database;

    CompanyRepository companyRepository;

    public CompCommand(ThorinCompanies plugin){
        this.plugin = plugin;
        this.companyRepository = new CompanyRepository(plugin);
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

        Company company = new Company();
        company.setName(args[1]);
        company.setOwnerUuid(player.getUniqueId().toString());

        companyRepository.createCompany(company);

        Chat.sendMessage(player, "Företaget " + args[1] + " skapades");
    }

    private void listCommandHandler(Player player, String[] args) throws Exception {
        PreparedStatement query = database.getConnection().prepareStatement("SELECT * FROM companies");

        ResultSet companies = query.executeQuery();

        Chat.sendMessage(player, "&2Företag:");

        while(companies.next()){
            Chat.sendMessage(player, companies.getString("name") + ", Grundat " + companies.getDate("createdAt").toString());
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
