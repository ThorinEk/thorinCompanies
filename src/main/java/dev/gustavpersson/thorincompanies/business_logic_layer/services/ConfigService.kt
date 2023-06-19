package dev.gustavpersson.thorincompanies.business_logic_layer.services

import dev.gustavpersson.thorincompanies.ThorinCompanies.Companion.messagesConfig
import dev.gustavpersson.thorincompanies.ThorinCompanies.Companion.pluginConfig
import dev.gustavpersson.thorincompanies.ThorinCompanies.Companion.saveMessagesConfig
import dev.gustavpersson.thorincompanies.ThorinCompanies.Companion.savePluginConfig
import dev.gustavpersson.thorincompanies.business_logic_layer.enums.ConfigProp
import dev.gustavpersson.thorincompanies.business_logic_layer.enums.ErrorCode
import dev.gustavpersson.thorincompanies.business_logic_layer.enums.MessageProp
import dev.gustavpersson.thorincompanies.business_logic_layer.exceptions.ThorinException

class ConfigService {
    fun populateMessagesFile() {
        val messagesConfig = messagesConfig
        for ((property, value) in defaultMessages) {
            if (!messagesConfig.contains(property.key)) {
                messagesConfig[property.key] = value
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

    fun getConfig(property: ConfigProp): Any {
        val configValue = pluginConfig.get(property.key)
        return configValue ?: throw ThorinException(ErrorCode.CONFIG_PROPERTY_NOT_FOUND)
    }

    fun getMessage(property: MessageProp): Any {
        val message = messagesConfig.get(property.key)
        return message ?: throw ThorinException(ErrorCode.MESSAGE_PROPERTY_NOT_FOUND)
    }

    companion object {
        private val defaultMessages = mapOf(
            MessageProp.COMPANY_CREATED to "Company %s created",
            MessageProp.COMPANY_BALANCE to "Company balance: %s",
            MessageProp.EXCEPTION_OCCURRED to "&CAn unexpected exception occurred with ThorinCompanies",
            MessageProp.CHAT_PREFIX to "&6[&5Companies&6]&F"
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