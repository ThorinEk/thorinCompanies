package dev.gustavpersson.thorincompanies.presentation_layer

import org.bukkit.entity.Player
import java.util.*

object ConfirmationManager {
    private val confirmationMap = mutableMapOf<UUID, Confirmation>()

    fun addConfirmation(player: Player, confirmation: Confirmation) {
        confirmationMap[player.uniqueId] = confirmation
    }

    fun removeConfirmation(player: Player) {
        confirmationMap.remove(player.uniqueId)
    }

    fun getConfirmation(player: Player): Confirmation? {
        return confirmationMap[player.uniqueId]
    }
}
