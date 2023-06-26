package dev.gustavpersson.thorincompanies.presentation_layer.command_handlers

import org.bukkit.entity.Player

interface CommandHandler {
    fun execute(player: Player, args: Array<String>)
}
