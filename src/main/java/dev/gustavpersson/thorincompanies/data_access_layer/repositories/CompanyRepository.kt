package dev.gustavpersson.thorincompanies.data_access_layer.repositories

import com.j256.ormlite.dao.Dao
import dev.gustavpersson.thorincompanies.ThorinCompanies
import dev.gustavpersson.thorincompanies.data_access_layer.Database
import dev.gustavpersson.thorincompanies.data_access_layer.entities.CompanyEntity
import java.sql.SQLException

class CompanyRepository(plugin: ThorinCompanies?) {
    private val companyDao: Dao<*, *>?
    private val database: Database

    init {
        database = Database(plugin)
        companyDao = database.getDao<CompanyEntity, Any>(CompanyEntity::class.java)
    }

    @Throws(Exception::class)
    fun createCompany(company: CompanyEntity?) {
        companyDao.create(company)
    }

    @get:Throws(SQLException::class)
    val allCompanies: List<CompanyEntity>
        get() = companyDao!!.queryForAll()
}
