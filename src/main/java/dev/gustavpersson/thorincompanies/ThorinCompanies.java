package dev.gustavpersson.thorincompanies;

import dev.gustavpersson.thorincompanies.commands.CompBalance;
import dev.gustavpersson.thorincompanies.database.Database;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class ThorinCompanies extends JavaPlugin {

    private static Economy economy = null;

    @Override
    public void onEnable() {

        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        Database database = new Database(this);

        database.initializeDatabase();

        //Register commands
        this.getCommand("compBalance").setExecutor(new CompBalance());

    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return true;
    }

    public static Economy getEconomy() {
        return economy;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
