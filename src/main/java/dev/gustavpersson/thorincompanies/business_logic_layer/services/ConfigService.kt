package dev.gustavpersson.thorincompanies.business_logic_layer.services

import dev.gustavpersson.thorincompanies.ThorinCompanies.Companion.getMessagesConfig
import dev.gustavpersson.thorincompanies.ThorinCompanies.Companion.pluginConfig
import dev.gustavpersson.thorincompanies.ThorinCompanies.Companion.saveMessagesConfig
import dev.gustavpersson.thorincompanies.ThorinCompanies.Companion.savePluginConfig
import dev.gustavpersson.thorincompanies.business_logic_layer.constants.ConfigKeys
import dev.gustavpersson.thorincompanies.business_logic_layer.constants.MessageKeys

class ConfigService {
    fun populateMessagesFile() {
        val messagesConfig = getMessagesConfig()
        for ((key, value) in messages) {
            if (!messagesConfig!!.contains(key!!)) {
                messagesConfig[key] = value
            }
        }
        saveMessagesConfig()
    }

    fun populateConfigFile() {
        val config = pluginConfig
        for ((key, value) in configProperties) {
            if (!config.contains(key!!)) {
                config[key] = value
            }
        }
        savePluginConfig()
    }

    companion object {
        private val messages: HashMap<String?, String?> = object : HashMap<String?, String?>() {
            init {
                put(MessageKeys.COMPANY_CREATED, "Company %s created")
                put(MessageKeys.COMPANY_BALANCE, "Company balance: %s")
                put(MessageKeys.EXCEPTION_OCCURRED, "&CAn unexpected exception occurred with ThorinCompanies")
            }
        }
        private val configProperties: HashMap<String?, Any?> = object : HashMap<String?, Any?>() {
            init {
                put(ConfigKeys.MAX_COMPANIES_PER_PLAYER, 3)
            }
        }
    }
}
