import dev.gustavpersson.thorincompanies.data_access_layer.entities.CompaniesTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseManager {
    companion object {
        fun initDatabase() {
            Database.connect(
                "jdbc:mysql://localhost/thorincompanies",
                driver = "com.mysql.jdbc.Driver",
                user = "root",
                password = "")

            transaction {
                SchemaUtils.create(CompaniesTable) // Create table if it does not exist
            }
        }
    }
}
