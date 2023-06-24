package dev.gustavpersson.thorincompanies.business_logic_layer.enums

// These are the property key paths used in the messages.yml file

enum class MessageProp(val key: String) {
    COMPANY_CREATED("company_created"),
    COMPANY_DELETED("company_deleted"),
    COMPANY_BALANCE("company_balance"),
    EXCEPTION_OCCURRED("exception_occurred"),
    INVALID_ARGUMENT("invalid_argument"),
    CHAT_PREFIX("chat.prefix"),
    SPECIFY_COMP_NAME("specify_comp_name"),
    AWAITING_COMP_CREATION_CONFIRM("await_comp_creation_confirm"),
    AWAIT_COMP_DELETION_CONFIRM("await_comp_deletion_confirm"),
    COMP_LIST_ITEM("comp_list_item"),
    COMP_LIST_TITLE("comp_list_title"),
    LINE_SEPARATOR("line_separator")
}

