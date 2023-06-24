package dev.gustavpersson.thorincompanies.business_logic_layer.enums

// These are the property key paths used in the config.yml file
enum class ConfigProp(val key: String) {
    MAX_COMPANIES_PER_PLAYER("max_companies_per_player"),
    COMPANY_STARTUP_COST("company_startup_cost"),
    DATABASE_HOST("database.hostname"),
    DATABASE_PORT("database.port"),
    DATABASE_USER("database.user"),
    DATABASE_NAME("database.dbname"),
    DATABASE_PASSWORD("database.password"),
    DATE_FORMAT("date_format"),
    CURRENCY_SUFFIX("currency_suffix")
}
