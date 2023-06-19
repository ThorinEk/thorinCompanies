package dev.gustavpersson.thorincompanies.data_access_layer

import dev.gustavpersson.thorincompanies.ThorinCompanies
import dev.gustavpersson.thorincompanies.business_logic_layer.enums.ErrorCode
import dev.gustavpersson.thorincompanies.business_logic_layer.exceptions.ThorinException
import dev.gustavpersson.thorincompanies.data_access_layer.entities.CompaniesTable
import dev.gustavpersson.thorincompanies.data_access_layer.entities.EmployeesTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class Database {
    fun initDatabase() {
        try {
            Database.connect(
                "jdbc:mysql://localhost/thorincompanies",
                driver = "com.mysql.jdbc.Driver",
                user = "root",
                password = "")
        }
        catch (exception: Exception) {
            ThorinCompanies.logger.severe("Could not connect to the MySQL database. Please verify your database properties.")
            ThorinCompanies.pluginManager.disablePlugin(ThorinCompanies.instance)
            return
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
