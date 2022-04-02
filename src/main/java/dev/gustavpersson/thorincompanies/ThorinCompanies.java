package dev.gustavpersson.thorincompanies;

import dev.gustavpersson.thorincompanies.controller.CompBalance;
import dev.gustavpersson.thorincompanies.database.Database;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public final class ThorinCompanies extends JavaPlugin {

    private final Logger logger = Logger.getLogger("Minecraft");
    private Economy economy = null;

    @Override
    public void onEnable() {

        try {
            setupEconomy();

            Objects.requireNonNull(this.getCommand("comp")).setExecutor(new CompBalance(this));

            Database database = new Database(this);

            database.initializeDatabase();

        } catch (Exception e) {
            logger.severe(e.getMessage());
        }

    }

    private void setupEconomy() throws Exception {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            throw new Exception("Vault plugin not found");
        }

        RegisteredServiceProvider<Economy> economyServiceProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if (economyServiceProvider == null) {
            throw new Exception("No economy service provider found");
        }
        economy = economyServiceProvider.getProvider();
    }

    public Economy getEconomy() {
        return economy;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        // Plugin shutdown logic
    }
}
