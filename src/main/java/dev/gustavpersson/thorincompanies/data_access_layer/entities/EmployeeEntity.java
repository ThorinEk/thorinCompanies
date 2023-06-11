package dev.gustavpersson.thorincompanies.data_access_layer.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "employees")
public class EmployeeEntity {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true)
    private CompanyEntity companyId;

    @DatabaseField()
    private Date hiringDate;

}
