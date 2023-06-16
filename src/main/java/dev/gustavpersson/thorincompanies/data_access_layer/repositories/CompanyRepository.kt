package dev.gustavpersson.thorincompanies.data_access_layer.repositories

import dev.gustavpersson.thorincompanies.ThorinCompanies
import dev.gustavpersson.thorincompanies.data_access_layer.Database
import dev.gustavpersson.thorincompanies.data_access_layer.entities.CompaniesTable
import java.sql.SQLException

class CompanyRepository(plugin: ThorinCompanies?) {
    private val companyDao: Dao<*, *>?
    private val database: Database

    init {
        database = Database(plugin)
        companyDao = database.getDao<CompaniesTable, Any>(CompaniesTable::class.java)
    }

    @Throws(Exception::class)
    fun createCompany(company: CompaniesTable) {
        companyDao.create(company)
    }

    @get:Throws(SQLException::class)
    val allCompanies: List<CompaniesTable>
        get() = companyDao!!.queryForAll()
}
