package dev.gustavpersson.thorincompanies.business_logic_layer.enums

// These are the property key paths used in the messages.yml file

enum class MessageProp(val key: String) {
    COMPANY_CREATED("company_created"),
    COMPANY_BALANCE("company_balance"),
    EXCEPTION_OCCURRED("exception_occurred"),
    INVALID_ARGUMENT("invalid_argument"),
    CHAT_PREFIX("chat.prefix");
}

