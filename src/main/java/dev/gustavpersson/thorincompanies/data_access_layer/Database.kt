package dev.gustavpersson.thorincompanies.data_access_layer

import dev.gustavpersson.thorincompanies.ThorinCompanies
import dev.gustavpersson.thorincompanies.business_logic_layer.enums.ConfigProp
import dev.gustavpersson.thorincompanies.business_logic_layer.enums.ErrorCode
import dev.gustavpersson.thorincompanies.business_logic_layer.exceptions.ThorinException
import dev.gustavpersson.thorincompanies.data_access_layer.entities.CompaniesTable
import dev.gustavpersson.thorincompanies.data_access_layer.entities.EmployeesTable
import dev.gustavpersson.thorincompanies.presentation_layer.managers.ConfigManager
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class Database {
    private val configManager by lazy { ConfigManager() }

    fun initDatabase() {
        try {
            val host = configManager.getConfig(ConfigProp.DATABASE_HOST) as String
            val port = configManager.getConfig(ConfigProp.DATABASE_PORT) as Int
            val dbName = configManager.getConfig(ConfigProp.DATABASE_NAME) as String
            val user = configManager.getConfig(ConfigProp.DATABASE_USER) as String
            val password = configManager.getConfig(ConfigProp.DATABASE_PASSWORD) as String

            Database.connect(
                "jdbc:mysql://$host:$port/$dbName",
                driver = "com.mysql.cj.jdbc.Driver",
                user = user,
                password = password)
        }
        catch (exception: Exception) {
            ThorinCompanies.logger.severe(exception.message)
            exception.printStackTrace()
            ThorinCompanies.logger.severe("Could not connect to the MySQL database. Please verify your database properties.")
            throw ThorinException(ErrorCode.DB_INIT)
        }

        try {
            transaction {
                SchemaUtils.create(CompaniesTable)
                SchemaUtils.create(EmployeesTable)
            }
        }
        catch(exception: Exception) {
            throw ThorinException(ErrorCode.DB_TABLE_GENERATION)
        }

    }
}
