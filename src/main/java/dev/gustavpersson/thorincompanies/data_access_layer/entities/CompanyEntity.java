package dev.gustavpersson.thorincompanies.data_access_layer.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

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

}
