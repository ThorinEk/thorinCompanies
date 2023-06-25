package dev.gustavpersson.thorincompanies.business_logic_layer.utils

import dev.gustavpersson.thorincompanies.business_logic_layer.enums.ErrorCode
import dev.gustavpersson.thorincompanies.business_logic_layer.enums.MessageProp
import dev.gustavpersson.thorincompanies.presentation_layer.ErrorTranslator
import dev.gustavpersson.thorincompanies.presentation_layer.managers.ConfigManager
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.entity.Player

object ChatUtility {
    private val configManager by lazy { ConfigManager() }
    private val miniMessage by lazy { MiniMessage.miniMessage() }

    fun sendMessage(player: Player, message: String, vararg values: Any) {
        val formattedMessage = String.format(message, *values)
        val finalMessage = miniMessage.deserialize(getPrefix() + formattedMessage)
        player.sendMessage(finalMessage)
    }

    fun sendMessage(player: Player, messageProp: MessageProp, vararg values: Any) {
        val message = configManager.getMessage(messageProp) as String
        val formattedMessage = String.format(message, *values)
        val finalMessage = miniMessage.deserialize(getPrefix() + formattedMessage)
        player.sendMessage(finalMessage)
    }

    fun sendMessage(player: Player, errorCode: ErrorCode) {
        val errorMessage = ErrorTranslator.getErrorMessage(errorCode)
        val finalMessage = miniMessage.deserialize(getPrefix() + "<red>" + errorMessage)
        player.sendMessage(finalMessage)
    }

    fun sendConfirmableMessage(player: Player, messageProp: MessageProp, vararg values: Any) {

        val line = Component.text(configManager.getMessage(MessageProp.LINE_SEPARATOR) as String)
        val message = configManager.getMessage(messageProp) as String
        val formattedMessage = Component.text(String.format(message, *values))
            .clickEvent(ClickEvent.runCommand("/com confirm"))

        val fullMessage = line
            .append(Component.newline())
            .append(formattedMessage)
            .append(Component.newline())
            .append(line)

        player.sendMessage(fullMessage)
    }

    private fun getPrefix(): String {
        return configManager.getMessage(MessageProp.CHAT_PREFIX) as String
    }

}