package dev.gustavpersson.thorincompanies.business_logic_layer.models

import java.util.*

class Company {
    var id = 0

    //The display name for the company
    var name: String? = null

    //The original founder
    var founderUUID: UUID? = null
    var createdAt: Date? = null
}
