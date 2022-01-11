package dev.gustavpersson.thorincompanies;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.maxgamer.quickshop.api.QuickShopAPI;
import org.maxgamer.quickshop.api.shop.Shop;

public final class ThorinCompanies extends JavaPlugin {

    @Override
    public void onEnable() {
        Plugin quickshopPlugin = Bukkit.getPluginManager().getPlugin("QuickShop");
        if(quickshopPlugin != null && quickshopPlugin.isEnabled()){
            QuickShopAPI quickshopApi = (QuickShopAPI)quickshopPlugin;
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
