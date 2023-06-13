package dev.gustavpersson.thorincompanies.data_access_layer

import com.j256.ormlite.dao.Dao
import com.j256.ormlite.dao.DaoManager
import com.j256.ormlite.jdbc.JdbcConnectionSource
import com.j256.ormlite.table.TableUtils
import dev.gustavpersson.thorincompanies.ThorinCompanies
import dev.gustavpersson.thorincompanies.data_access_layer.entities.CompanyEntity
import dev.gustavpersson.thorincompanies.data_access_layer.entities.EmployeeEntity
import java.sql.SQLException

class Database(plugin: ThorinCompanies?) {
    private val connectionSource: JdbcConnectionSource?
    private val plugin: ThorinCompanies?

    init {
        connectionSource = JdbcConnectionSource(databaseUrl)
        if (connectionSource == null) {
            throw Exception("Connection to database could not be established, please check your database configuration properties.")
        }
        this.plugin = plugin
    }

    @Throws(SQLException::class)
    fun <T, ID> getDao(clazz: Class<T>?): Dao<T, ID>? {
        var dao = DaoManager.lookupDao<Dao<T, ID>, T>(connectionSource, clazz)
        if (dao == null) {
            dao = DaoManager.createDao(connectionSource, clazz)
        }
        return dao
    }

    @Throws(Exception::class)
    fun createTables() {
        TableUtils.createTableIfNotExists(connectionSource, CompanyEntity::class.java)
        TableUtils.createTableIfNotExists(connectionSource, EmployeeEntity::class.java)
    }

    companion object {
        private const val databaseUrl = "jdbc:mysql://root@localhost/thorincompanies"
    }
}
