package dev.gustavpersson.thorincompanies;

import dev.gustavpersson.thorincompanies.business_logic_layer.ConfigManager;
import dev.gustavpersson.thorincompanies.business_logic_layer.ThorinException;
import dev.gustavpersson.thorincompanies.presentation_layer.CompCommand;
import dev.gustavpersson.thorincompanies.data_access_layer.Database;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

public class ThorinCompanies extends JavaPlugin {

    private static ThorinCompanies instance;
    private final Logger logger = Logger.getLogger("Minecraft");
    private Economy economy;

    private File messagesFile;
    private FileConfiguration messagesConfig;

    @Override
    public void onEnable() {
        try {
            instance = this;

            saveDefaultConfig();

            createMessagesFile();

            ConfigManager configManager = new ConfigManager();
            configManager.populateConfigFile();
            configManager.populateMessagesFile();

            setupEconomy();

            Database database = new Database(this);

            database.createTables();

            Objects.requireNonNull(this.getCommand("comp")).setExecutor(new CompCommand(this));

        } catch (Exception e) {
            logger.severe(e.getMessage());
        }


    }

    @Override
    public void onDisable() {
        super.onDisable();
        // Plugin shutdown logic
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

    public static FileConfiguration getPluginConfig() {
        return instance.getConfig();
    }

    public static void savePluginConfig() {
        instance.saveConfig();
    }

    private void createMessagesFile() {
        messagesFile = new File(getDataFolder(), "messages.yml");
        if (!messagesFile.exists()) {
            messagesFile.getParentFile().mkdirs();
            saveResource("messages.yml", false);
        }

        messagesConfig = new YamlConfiguration();
        try {
            messagesConfig.load(messagesFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static FileConfiguration getMessagesConfig() {
        return instance.messagesConfig;
    }

    public static void saveMessagesConfig() {
        try {
            instance.messagesConfig.save(instance.messagesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
