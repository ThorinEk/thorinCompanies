package dev.gustavpersson.thorincompanies.business_logic_layer.utils

import dev.gustavpersson.thorincompanies.business_logic_layer.enums.ConfigProp
import dev.gustavpersson.thorincompanies.business_logic_layer.enums.ErrorCode
import dev.gustavpersson.thorincompanies.business_logic_layer.enums.MessageProp
import dev.gustavpersson.thorincompanies.presentation_layer.ErrorTranslator
import dev.gustavpersson.thorincompanies.presentation_layer.managers.ConfigManager
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.entity.Player
import java.math.BigDecimal
import java.math.RoundingMode

object Chat {
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
        val message = configManager.getMessage(messageProp) as String
        val formattedMessage = String.format(message, *values)

        val line = Component.text()
            .content("------------------------------")
            .decoration(TextDecoration.STRIKETHROUGH, true)
            .color(NamedTextColor.AQUA)
            .build()

        val interactiveMessage = Component.text(formattedMessage)
            .clickEvent(ClickEvent.runCommand("/com confirm"))
            .hoverEvent(HoverEvent.showText(Component.text()
                .content("Click this message to confirm the action.")
                .color(NamedTextColor.GREEN))
            )
            .color(NamedTextColor.GREEN)

        val fullMessage = Component.empty()
            .append(line)
            .append(Component.newline()) // Reset formatting
            .append(interactiveMessage)
            .append(Component.newline())
            .append(line)

        player.sendMessage(fullMessage)
    }

    fun currency(amount: BigDecimal): String {
        val currencySuffix = configManager.getConfig(ConfigProp.CURRENCY_SUFFIX)
        val formattedAmount = amount.setScale(2, RoundingMode.HALF_EVEN)
        return "$formattedAmount$currencySuffix"
    }

    private fun getPrefix(): String {
        return configManager.getMessage(MessageProp.CHAT_PREFIX) as String
    }

}