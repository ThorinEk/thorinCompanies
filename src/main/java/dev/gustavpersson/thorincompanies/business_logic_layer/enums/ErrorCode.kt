package dev.gustavpersson.thorincompanies.business_logic_layer.enums

enum class ErrorCode {
    DB_INIT,
    DB_TABLE_GENERATION,
    COMPANY_START,
    UNEXPECTED,
    COMP_NAME_ALREADY_EXISTS,
    INSUFFICIENT_FUNDS_TO_START_COMP,
    MAX_COMPANIES_REACHED,
    ONLY_FOUNDER_CAN_DELETE
}