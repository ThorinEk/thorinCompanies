package dev.gustavpersson.thorincompanies;

import dev.gustavpersson.thorincompanies.business_logic_layer.ConfigManager;
import dev.gustavpersson.thorincompanies.presentation_layer.CompCommand;
import dev.gustavpersson.thorincompanies.data_access_layer.Database;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;
import java.util.logging.Logger;

public final class ThorinCompanies extends JavaPlugin {

    private final Logger logger = Logger.getLogger("Minecraft");
    private Economy economy;

    File messageFile = new File(getDataFolder(), "messages.yml");
    FileConfiguration messagesConfig = YamlConfiguration.loadConfiguration(messageFile);

    File configFile = new File(getDataFolder(), "config.yml");
    FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

    public Economy getEconomy() {
        return economy;
    }

    public FileConfiguration getMessagesConfig() { return messagesConfig; }

    public File getMessageFile() { return messageFile; }

    public File getConfigFile() { return configFile; }

    @Override
    public void onEnable() {

        try {
            setupEconomy();

            if (!messageFile.exists()){
                saveResource("messages.yml", false);
            }
            ConfigManager.populateMessagesFile(this);

            if (!configFile.exists()){
                saveResource("config.yml", false);
            }
            ConfigManager.populateConfigFile(this);

            Database database = new Database(this);

            database.initializeDatabase();

            Objects.requireNonNull(this.getCommand("comp")).setExecutor(new CompCommand(this));

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

    @Override
    public void onDisable() {
        super.onDisable();
        // Plugin shutdown logic
    }

    @Override
    public FileConfiguration getConfig(){
        return config;
    }

}
