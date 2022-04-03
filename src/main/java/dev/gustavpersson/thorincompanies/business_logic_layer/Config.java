package dev.gustavpersson.thorincompanies.business_logic_layer;

import dev.gustavpersson.thorincompanies.ThorinCompanies;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Config {

    public static void populateMessagesFile(ThorinCompanies plugin) throws IOException {

        for (Map.Entry<String, String> message : messages.entrySet()){
            plugin.getMessagesConfig().set(message.getKey(), message.getValue());
        }
        plugin.getMessagesConfig().save(plugin.getMessageFile());
    }

    private static final HashMap<String, String> messages = new HashMap<String, String>() {
        {
            put("Hello", "Testing testing 2");
            put("your_balance", "Ditt konto: %s");
        }
    };
}
