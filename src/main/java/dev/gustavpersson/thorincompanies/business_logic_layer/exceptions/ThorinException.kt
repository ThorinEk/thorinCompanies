package dev.gustavpersson.thorincompanies.business_logic_layer.exceptions

class ThorinException(code: ErrorCode, message: String? = null) : Exception(message)

enum class ErrorCode {
    DB_INIT,
    DB_TABLE_GENERATION,
    COMPANY_START,
    UNEXPECTED,
    // add other error codes here
}
