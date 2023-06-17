package dev.gustavpersson.thorincompanies.presentation_layer

import dev.gustavpersson.thorincompanies.ThorinCompanies
import dev.gustavpersson.thorincompanies.business_logic_layer.exceptions.ErrorCode
import dev.gustavpersson.thorincompanies.business_logic_layer.exceptions.ThorinException
import dev.gustavpersson.thorincompanies.business_logic_layer.services.CompanyService

sealed class Confirmation {
    abstract fun confirm()
}

data class DeleteCompanyConfirmation(val companyID: Int) : Confirmation() {
    private val companyService by lazy { CompanyService() }
    override fun confirm() {
        // Company deletion logic
        try {
            companyService.delete(companyID)
        }
        catch (exception: Exception) {
            ThorinCompanies.logger.severe(exception.message)
            exception.printStackTrace()
            throw ThorinException(ErrorCode.UNEXPECTED)
        }
    }
}