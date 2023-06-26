package dev.gustavpersson.thorincompanies.business_logic_layer.enums

enum class Argument(val arg: String) {
    CREATE("create"),
    DELETE("delete"),
    LIQUIDATE("liquidate"),
    LIST("list"),
    BAL("bal"),
    CONFIRM("confirm"),
    RENAME("rename")
}
