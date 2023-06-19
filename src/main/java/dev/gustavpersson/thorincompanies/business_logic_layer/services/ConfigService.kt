package dev.gustavpersson.thorincompanies.business_logic_layer.services

import dev.gustavpersson.thorincompanies.ThorinCompanies.Companion.messagesConfig
import dev.gustavpersson.thorincompanies.ThorinCompanies.Companion.pluginConfig
import dev.gustavpersson.thorincompanies.ThorinCompanies.Companion.saveMessagesConfig
import dev.gustavpersson.thorincompanies.ThorinCompanies.Companion.savePluginConfig
import dev.gustavpersson.thorincompanies.business_logic_layer.enums.ConfigProp
import dev.gustavpersson.thorincompanies.business_logic_layer.constants.MessageKeys

class ConfigService {
    fun populateMessagesFile() {
        val messagesConfig = messagesConfig
        for ((key, value) in defaultMessages) {
            if (!messagesConfig.contains(key)) {
                messagesConfig[key] = value
            }
        }
        saveMessagesConfig()
    }

    fun populateConfigFile() {
        val config = pluginConfig
        for ((property, value) in defaultConfigProperties) {
            if (!config.contains(property.key)) {
                config[property.key] = value
            }
        }
        savePluginConfig()
    }

    fun <T> getConfig(key: ConfigProp): T {
        return pluginConfig.get(key.key) as T
    }

    fun <T> getMessage(key: String): T {
        return messagesConfig.get(key) as T ?: throw Exception("Message key $key not found or wrong type")
    }

    companion object {
        private val defaultMessages = mapOf(
            MessageKeys.COMPANY_CREATED to "Company %s created",
            MessageKeys.COMPANY_BALANCE to "Company balance: %s",
            MessageKeys.EXCEPTION_OCCURRED to "&CAn unexpected exception occurred with ThorinCompanies"
        )

        private val defaultConfigProperties = mapOf(
            ConfigProp.MAX_COMPANIES_PER_PLAYER to 3,
            ConfigProp.COMPANY_STARTUP_COST to 25000,
            ConfigProp.DATABASE_HOST to "localhost",
            ConfigProp.DATABASE_PORT to 3306,
            ConfigProp.DATABASE_USER to "root",
            ConfigProp.DATABASE_NAME to "thorincompanies",
            ConfigProp.DATABASE_PASSWORD to ""
        )
    }
}