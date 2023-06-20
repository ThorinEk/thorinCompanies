package dev.gustavpersson.thorincompanies.presentation_layer.confirmations

import dev.gustavpersson.thorincompanies.ThorinCompanies
import dev.gustavpersson.thorincompanies.business_logic_layer.enums.ErrorCode
import dev.gustavpersson.thorincompanies.business_logic_layer.exceptions.ThorinException
import org.bukkit.entity.Player

abstract class Confirmation {

    abstract val player: Player
    abstract fun confirm()

    protected fun executeConfirmation(body: () -> Unit) {
        return try {
            body()
        } catch (exception: ThorinException) {
            ThorinCompanies.logger.warning(exception.message)
            throw exception
        } catch (exception: Exception) {
            ThorinCompanies.logger.severe(exception.message)
            exception.printStackTrace()
            throw ThorinException(ErrorCode.UNEXPECTED)
        } finally {
            ConfirmationManager.removeConfirmation(player)
        }
    }
}