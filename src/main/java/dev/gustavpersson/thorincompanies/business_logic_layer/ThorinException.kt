package dev.gustavpersson.thorincompanies.business_logic_layer

class ThorinException(code: ErrorCode, message: String? = null) : Exception(message)

enum class ErrorCode {
    DB_INIT,
    DB_TABLE_GENERATION,
    COMPANY_START,
    // add other error codes here
}
