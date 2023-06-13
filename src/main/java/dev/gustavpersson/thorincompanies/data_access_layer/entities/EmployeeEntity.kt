package dev.gustavpersson.thorincompanies.data_access_layer.entities

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.util.*

@DatabaseTable(tableName = "employees")
class EmployeeEntity {
    @DatabaseField(generatedId = true)
    private val id = 0

    @DatabaseField(foreign = true)
    var companyId: CompanyEntity? = null

    @DatabaseField
    var hiringDate: Date? = null
}
