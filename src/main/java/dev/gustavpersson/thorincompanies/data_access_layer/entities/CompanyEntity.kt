package dev.gustavpersson.thorincompanies.data_access_layer.entities

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.util.*

@DatabaseTable(tableName = "companies")
class CompanyEntity {
    @DatabaseField(generatedId = true)
    private val id = 0

    @DatabaseField
    var name: String? = null

    //The original founder
    @DatabaseField
    var founderUUID: UUID? = null

    @DatabaseField
    var createdAt: Date? = null
}
