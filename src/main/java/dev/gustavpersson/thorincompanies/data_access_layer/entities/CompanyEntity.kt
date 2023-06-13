package dev.gustavpersson.thorincompanies.data_access_layer.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;
import java.util.UUID;

@DatabaseTable(tableName = "companies")
public class CompanyEntity {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField()
    private String name;

    //The original founder
    @DatabaseField()
    private UUID founderUUID;

    @DatabaseField()
    private Date createdAt;

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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
