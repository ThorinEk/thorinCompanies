package dev.gustavpersson.thorincompanies.business_logic_layer.services

import dev.gustavpersson.thorincompanies.ThorinCompanies.Companion.messagesConfig
import dev.gustavpersson.thorincompanies.ThorinCompanies.Companion.pluginConfig
import dev.gustavpersson.thorincompanies.ThorinCompanies.Companion.saveMessagesConfig
import dev.gustavpersson.thorincompanies.ThorinCompanies.Companion.savePluginConfig
import dev.gustavpersson.thorincompanies.business_logic_layer.constants.ConfigKeys
import dev.gustavpersson.thorincompanies.business_logic_layer.constants.MessageKeys

class ConfigService {
    fun populateMessagesFile() {
        val messagesConfig = messagesConfig
        for ((key, value) in messages) {
            if (!messagesConfig.contains(key)) {
                messagesConfig[key] = value
            }
        }
        saveMessagesConfig()
    }

    fun populateConfigFile() {
        val config = pluginConfig
        for ((key, value) in configProperties) {
            if (!config.contains(key)) {
                config[key] = value
            }
        }
        savePluginConfig()
    }

    fun <T> getConfig(key: ConfigKeys): T {
        return pluginConfig.get(key) as Any
    }

    fun <T> getMessage(key: String): T {
        return messagesConfig.get(key) as T ?: throw Exception("Message key $key not found or wrong type")
    }

    companion object {
        private val messages = mapOf(
            MessageKeys.COMPANY_CREATED to "Company %s created",
            MessageKeys.COMPANY_BALANCE to "Company balance: %s",
            MessageKeys.EXCEPTION_OCCURRED to "&CAn unexpected exception occurred with ThorinCompanies"
        )

        private val configProperties = mapOf(
            ConfigKeys.MAX_COMPANIES_PER_PLAYER to 3,
            ConfigKeys.COMPANY_STARTUP_COST to 25000,
            ConfigKeys.DATABASE_HOST to "localhost",
            ConfigKeys.DATABASE_PORT to 3306,
            ConfigKeys.DATABASE_USER to "root",
            ConfigKeys.DATABASE_NAME to "thorincompanies",
            ConfigKeys.DATABASE_PASSWORD to ""
        )
    }
}