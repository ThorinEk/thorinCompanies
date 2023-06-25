package dev.gustavpersson.thorincompanies.presentation_layer

import dev.gustavpersson.thorincompanies.business_logic_layer.enums.ErrorCode

object ErrorTranslator {
    fun getErrorMessage(errorCode: ErrorCode): String {
        return when (errorCode) {
            ErrorCode.DB_INIT -> "Could not connect to the MySQL database. Please verify your database properties"
            ErrorCode.DB_TABLE_GENERATION -> "Error generating database tables"
            ErrorCode.COMPANY_START -> "Error starting company"
            ErrorCode.UNEXPECTED -> "An unexpected error occurred"
            ErrorCode.COMP_NAME_ALREADY_EXISTS -> "Company name already exists"
            ErrorCode.INSUFFICIENT_FUNDS_TO_START_COMP -> "Insufficient funds to start company"
            ErrorCode.MAX_COMPANIES_REACHED -> "Maximum number of companies reached"
            ErrorCode.ONLY_FOUNDER_CAN_DELETE -> "Only the founder can delete the company"
            ErrorCode.CONFIG_PROPERTY_NOT_FOUND -> "Config property not found"
            ErrorCode.MESSAGE_PROPERTY_NOT_FOUND -> "Message property not found"
            ErrorCode.COMP_NAME_NOT_SPECIFIED -> "Company name needs to be specified"
            ErrorCode.COMP_NAME_NOT_FOUND -> "No company with that name was found"
            ErrorCode.CONFIG_PROPERTY_WRONG_TYPE -> "The configuration property has the wrong data type"
            ErrorCode.NO_ACTIVE_CONFIRMATION -> "You have no pending action to confirm"
            ErrorCode.COMP_ID_NOT_FOUND -> "No company with that ID could be found"
        }
    }
}
