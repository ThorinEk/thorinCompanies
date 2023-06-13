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

    public CompanyEntity getCompanyId() {
        return companyId;
    }

    public void setCompanyId(CompanyEntity companyId) {
        this.companyId = companyId;
    }

    public Date getHiringDate() {
        return hiringDate;
    }

    public void setHiringDate(Date hiringDate) {
        this.hiringDate = hiringDate;
    }

    @DatabaseField()
    private Date hiringDate;

}
