package dev.gustavpersson.thorincompanies.business_logic_layer;

import dev.gustavpersson.thorincompanies.ThorinCompanies;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    public void populateMessagesFile() {
        FileConfiguration messagesConfig = ThorinCompanies.getMessagesConfig();
        for (Map.Entry<String, String> message : messages.entrySet()){
            if (!messagesConfig.contains(message.getKey())){
                messagesConfig.set(message.getKey(), message.getValue());
            }
        }
        ThorinCompanies.saveMessagesConfig();
    }

    private static final HashMap<String, String> messages = new HashMap<>() {
        {
            put(MessageKeys.COMPANY_CREATED, "Company %s created");
            put(MessageKeys.COMPANY_BALANCE, "Company balance: %s");
            put(MessageKeys.EXCEPTION_OCCURRED, "&CAn unexpected exception occurred with ThorinCompanies");
        }
    };

    public void populateConfigFile() {
        FileConfiguration config = ThorinCompanies.getPluginConfig();
        for (Map.Entry<String, Object> property : configProperties.entrySet()){
            if (!config.contains(property.getKey())){
                config.set(property.getKey(), property.getValue());
            }
        }
        ThorinCompanies.savePluginConfig();
    }

    private static final HashMap<String, Object> configProperties = new HashMap<>() {
        {
            put(ConfigKeys.MAX_COMPANIES_PER_PLAYER, 3);
        }
    };

}
