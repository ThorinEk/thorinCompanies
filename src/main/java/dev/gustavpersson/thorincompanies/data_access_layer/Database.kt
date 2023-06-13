import dev.gustavpersson.thorincompanies.business_logic_layer.models.Companies
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseManager {
    companion object {
        fun initDatabase() {
            Database.connect("jdbc:mysql://root@localhost/thorincompanies", driver = "com.mysql.jdbc.Driver")

            transaction {
                SchemaUtils.create(Companies) // Create table if it does not exist
            }
        }
    }
}
