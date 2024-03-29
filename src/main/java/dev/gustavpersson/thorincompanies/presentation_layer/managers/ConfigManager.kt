package dev.gustavpersson.thorincompanies.presentation_layer.managers

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
                messagesConfig.set(property.key, getDefaultMessage(property))
            }
        }
        saveMessagesConfig()
    }

    fun populateConfigFile() {
        val config = pluginConfig
        for (property in enumValues<ConfigProp>()) {
            if (!config.contains(property.key)) {
                config.set(property.key, getDefaultConfig(property))
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
        return message?: throw ThorinException(ErrorCode.MESSAGE_PROPERTY_NOT_FOUND)
    }

    companion object {
        private fun getDefaultMessage(property: MessageProp): String {
            return when (property) {
                MessageProp.COMPANY_CREATED -> "Company <aqua>%s</aqua> created with a startup capital of %s"
                MessageProp.COMPANY_DELETED -> "Company <aqua>%s<reset> liquidated. Starting capital of %s has been returned to your account"
                MessageProp.COMPANY_BALANCE -> "Company balance: %s"
                MessageProp.EXCEPTION_OCCURRED -> "An unexpected exception occurred with ThorinCompanies"
                MessageProp.CHAT_PREFIX -> "<gold>[<dark_purple>Companies<gold>]<reset> "
                MessageProp.SPECIFY_COMP_NAME -> "A name must be specified for the company"
                MessageProp.AWAITING_COMP_CREATION_CONFIRM -> "Awaiting company creation, costing %s. Click here or type /com confirm to proceed"
                MessageProp.INVALID_ARGUMENT -> "An invalid argument was specified"
                MessageProp.AWAIT_COMP_DELETION_CONFIRM -> "Awaiting liquidation of %s. Click here or type /com confirm to proceed."
                MessageProp.COMP_LIST_ITEM -> "%s, founded %s by %s."
                MessageProp.COMP_LIST_TITLE -> "Companies:"
                MessageProp.CLICK_TO_CONFIRM_HOVER -> "Click this message to confirm the action."
            }
        }

        private fun getDefaultConfig(property: ConfigProp): Any {
            return when (property) {
                ConfigProp.MAX_COMPANIES_PER_PLAYER -> 3
                ConfigProp.COMPANY_STARTUP_COST -> 25000
                ConfigProp.DATABASE_HOST -> "localhost"
                ConfigProp.DATABASE_PORT -> 3306
                ConfigProp.DATABASE_USER -> "root"
                ConfigProp.DATABASE_NAME -> "thorincompanies"
                ConfigProp.DATABASE_PASSWORD -> ""
                ConfigProp.DATE_FORMAT -> "yyyy-MM-dd"
                ConfigProp.CURRENCY_SUFFIX -> " SEK"
                ConfigProp.DECIMAL_FORMAT -> "#,##0.00"
                ConfigProp.DECIMAL_SEPARATOR -> ","
                ConfigProp.GROUPING_SEPARATOR -> " "
            }
        }
    }
}