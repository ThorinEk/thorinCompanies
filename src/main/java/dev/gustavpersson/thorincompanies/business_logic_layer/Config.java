package dev.gustavpersson.thorincompanies.business_logic_layer;

import dev.gustavpersson.thorincompanies.ThorinCompanies;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Config {

    public static void populateMessagesFile(ThorinCompanies plugin) throws IOException {

        for (Map.Entry<String, String> message : messages.entrySet()){
            if (!plugin.getMessagesConfig().contains(message.getKey())){
                plugin.getMessagesConfig().set(message.getKey(), message.getValue());
            } else {
                plugin.getMessagesConfig().set(message.getKey(), null);
            }
        }
        plugin.getMessagesConfig().save(plugin.getMessageFile());
    }

    private static final HashMap<String, String> messages = new HashMap<>() {
        {
            put(MessageKeys.COMPANY_CREATED, "Company %s created");
            put("your_balance", "Ditt konto: %s");
        }
    };

    public static void populateConfigFile(ThorinCompanies plugin) throws IOException {

        for (Map.Entry<String, Object> property : configProperties.entrySet()){
            if (!plugin.getConfig().contains(property.getKey())){
                plugin.getConfig().set(property.getKey(), property.getValue());
            } else {
                plugin.getConfig().set(property.getKey(), null);
            }
        }
        plugin.getConfig().save(plugin.getConfigFile());
    }

    private static final HashMap<String, Object> configProperties = new HashMap<>() {
        {
            put(ConfigKeys.MAX_COMPANIES_PER_PLAYER, 3);
        }
    };

}
