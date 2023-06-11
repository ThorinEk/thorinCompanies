package dev.gustavpersson.thorincompanies.data_access_layer.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

@DatabaseTable(tableName = "companies")
public class CompanyEntity {

    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField()
    public String name;

    //The original founder
    @DatabaseField()
    public UUID founderUUID;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getFounderUUID() {
        return founderUUID;
    }

    public void setFounderUUID(UUID founderUUID) {
        this.founderUUID = founderUUID;
    }
}
