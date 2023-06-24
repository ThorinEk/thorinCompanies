package dev.gustavpersson.thorincompanies

import dev.gustavpersson.thorincompanies.business_logic_layer.exceptions.ThorinException
import dev.gustavpersson.thorincompanies.presentation_layer.managers.ConfigManager
import dev.gustavpersson.thorincompanies.data_access_layer.Database
import dev.gustavpersson.thorincompanies.presentation_layer.CommandController
import dev.gustavpersson.thorincompanies.presentation_layer.ErrorTranslator
import net.milkbowl.vault.economy.Economy
import org.bukkit.configuration.InvalidConfigurationException
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException
import java.util.logging.Logger
import net.kyori.adventure.platform.bukkit.BukkitAudiences

class ThorinCompanies : JavaPlugin() {

    lateinit var messagesConfig: FileConfiguration
    private lateinit var messagesFile: File
    private var adventure: BukkitAudiences? = null

    override fun onEnable() {
        try {
            instance = this

            adventure = BukkitAudiences.create(this)

            saveDefaultConfig()
            createMessagesFile()
            ConfigManager().apply {
                populateConfigFile()
                populateMessagesFile()
            }
            setupEconomy()
            Database().apply { initDatabase() }
            getCommand("com")?.setExecutor(CommandController(this))
        } catch (e: ThorinException) {
            logger.severe(ErrorTranslator.getErrorMessage(e.code))
            this.onDisable()
        } catch (e: Exception) {
            logger.severe(e.message)
            e.printStackTrace()
            this.onDisable()
        }
    }

    override fun onDisable() {
        super.onDisable()
        // Plugin shutdown logic
        if (adventure != null) {
            adventure?.close()
            adventure = null
        }
    }

    private fun setupEconomy() {
        if (pluginManager.getPlugin("Vault") == null) {
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
        lateinit var economy: Economy

        val pluginConfig: FileConfiguration
            get() = instance.config

        fun savePluginConfig() {
            instance.saveConfig()
        }

        val messagesConfig: FileConfiguration
            get() = instance.messagesConfig

        fun saveMessagesConfig() {
            try {
                instance.messagesConfig.save(instance.messagesFile)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        val logger: Logger
            get() = instance.logger

        val pluginManager: PluginManager
            get() = instance.server.pluginManager

        val adventure: BukkitAudiences?
            get() = instance.adventure
    }

}
