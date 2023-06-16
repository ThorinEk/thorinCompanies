package dev.gustavpersson.thorincompanies

import dev.gustavpersson.thorincompanies.business_logic_layer.ConfigService
import dev.gustavpersson.thorincompanies.data_access_layer.Database
import dev.gustavpersson.thorincompanies.presentation_layer.CompCommand
import net.milkbowl.vault.economy.Economy
import org.bukkit.configuration.InvalidConfigurationException
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException
import java.util.logging.Logger

class ThorinCompanies : JavaPlugin() {

    private val logger = Logger.getLogger("Minecraft")

    lateinit var economy: Economy
    lateinit var messagesConfig: FileConfiguration
    private lateinit var messagesFile: File
    override fun onEnable() {
        try {
            saveDefaultConfig()
            createMessagesFile()
            ConfigService().apply {
                populateConfigFile()
                populateMessagesFile()
            }
            setupEconomy()
            Database(this).apply { createTables() }
            getCommand("comp")?.setExecutor(CompCommand(this))
        } catch (e: Exception) {
            logger.severe(e.message)
        }
    }

    override fun onDisable() {
        super.onDisable()
        // Plugin shutdown logic
    }

    private fun setupEconomy() {
        if (server.pluginManager.getPlugin("Vault") == null) {
            throw Exception("Vault plugin not found")
        }
        val economyServiceProvider = server.servicesManager.getRegistration(Economy::class.java)
                ?: throw Exception("No economy service provider found")
        economy = economyServiceProvider.provider
    }

    private fun createMessagesFile() {
        messagesFile = File(dataFolder, "messages.yml").apply {
            if (!exists()) {
                parentFile.mkdirs()
                saveResource("messages.yml", false)
            }
        }
        messagesConfig = YamlConfiguration().apply {
            try {
                load(messagesFile)
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: InvalidConfigurationException) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        lateinit var instance: ThorinCompanies

        val pluginConfig: FileConfiguration
            get() = instance.config

        fun savePluginConfig() {
            instance.saveConfig()
        }

        fun getMessagesConfig(): FileConfiguration? {
            return instance?.messagesConfig
        }

        fun saveMessagesConfig() {
            try {
                instance?.messagesConfig?.save(instance.messagesFile)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

    }

}
