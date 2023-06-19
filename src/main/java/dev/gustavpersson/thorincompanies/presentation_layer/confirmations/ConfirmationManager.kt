package dev.gustavpersson.thorincompanies.presentation_layer.confirmations

import dev.gustavpersson.thorincompanies.ThorinCompanies
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitTask
import java.util.*

object ConfirmationManager {
    private val confirmationMap = mutableMapOf<UUID, Confirmation>()
    private val taskMap = mutableMapOf<UUID, BukkitTask>()

    fun addConfirmation(confirmation: Confirmation) {
        confirmationMap[confirmation.player.uniqueId] = confirmation
        // Cancel any existing task for this player
        taskMap[confirmation.player.uniqueId]?.cancel()
        // Schedule the confirmation to be removed after one minute
        val task = Bukkit.getScheduler().runTaskLater(ThorinCompanies.instance, Runnable {
            confirmationMap.remove(confirmation.player.uniqueId)
            taskMap.remove(confirmation.player.uniqueId)
        }, 1200L) // 20 ticks = 1 second, so 1200 ticks = 60 seconds
        taskMap[confirmation.player.uniqueId] = task
    }

    fun removeConfirmation(player: Player) {
        confirmationMap.remove(player.uniqueId)
        taskMap[player.uniqueId]?.cancel()
        taskMap.remove(player.uniqueId)
    }

    fun getConfirmation(player: Player): Confirmation? {
        return confirmationMap[player.uniqueId]
    }
}

