package dev.gustavpersson.thorincompanies.data_access_layer;

import dev.gustavpersson.thorincompanies.ThorinCompanies;
import dev.gustavpersson.thorincompanies.business_logic_layer.Company;

import java.sql.Date;
import java.sql.PreparedStatement;

public class CompanyRepository {

    private final Database database;

    public CompanyRepository(ThorinCompanies plugin){
        database = new Database(plugin);
    }

    public void createCompany(Company company) throws Exception {
        Date date = new Date(System.currentTimeMillis());

        PreparedStatement query = database.getConnection().prepareStatement(
                "INSERT INTO companies (name, ownerUuid, createdAt) VALUES (?, ?, ?)");
        query.setString(1, company.getName());
        query.setString(2, company.getOwnerUuid());
        query.setDate(3, date);
        query.execute();
    }

}
