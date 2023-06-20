package dev.gustavpersson.thorincompanies.presentation_layer

import dev.gustavpersson.thorincompanies.ThorinCompanies.Companion.messagesConfig
import dev.gustavpersson.thorincompanies.ThorinCompanies.Companion.pluginConfig
import dev.gustavpersson.thorincompanies.ThorinCompanies.Companion.saveMessagesConfig
import dev.gustavpersson.thorincompanies.ThorinCompanies.Companion.savePluginConfig
import dev.gustavpersson.thorincompanies.business_logic_layer.enums.ConfigProp
import dev.gustavpersson.thorincompanies.business_logic_layer.enums.ErrorCode
import dev.gustavpersson.thorincompanies.business_logic_layer.enums.MessageProp
import dev.gustavpersson.thorincompanies.business_logic_layer.exceptions.ThorinException

class ConfigManager {
    fun populateMessagesFile() {
        val messagesConfig = messagesConfig
        for (property in enumValues<MessageProp>()) {
            if (!messagesConfig.contains(property.key)) {
                messagesConfig[property.key] = property
            }
        }
        saveMessagesConfig()
    }

    fun populateConfigFile() {
        val config = pluginConfig
        for (property in enumValues<ConfigProp>()) {
            if (!config.contains(property.key)) {
                config[property.key] = getConfig(property)
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
        private fun getMessage(property: MessageProp): String {
            return when (property) {
                MessageProp.COMPANY_CREATED -> "Company &B%s&R created"
                MessageProp.COMPANY_DELETED -> "Company &B%s&R liquidated. Starting capital of %s has been returned to your account"
                MessageProp.COMPANY_BALANCE -> "Company balance: %s"
                MessageProp.EXCEPTION_OCCURRED -> "&CAn unexpected exception occurred with ThorinCompanies"
                MessageProp.CHAT_PREFIX -> "&6[&5Companies&6]&F "
                MessageProp.SPECIFY_COMP_NAME -> "A name must be specified for the company"
                MessageProp.AWAIT_COMP_CREATION_CONFIRM -> "Awaiting company creation. Type /com confirm to proceed"
                MessageProp.INVALID_ARGUMENT -> "An invalid argument was specified"
            }
        }

        private fun getConfig(property: ConfigProp): Any {
            return when (property) {
                ConfigProp.MAX_COMPANIES_PER_PLAYER -> 3
                ConfigProp.COMPANY_STARTUP_COST -> 25000
                ConfigProp.DATABASE_HOST -> "localhost"
                ConfigProp.DATABASE_PORT -> 3306
                ConfigProp.DATABASE_USER -> "root"
                ConfigProp.DATABASE_NAME -> "thorincompanies"
                ConfigProp.DATABASE_PASSWORD -> ""
            }
        }
    }
}