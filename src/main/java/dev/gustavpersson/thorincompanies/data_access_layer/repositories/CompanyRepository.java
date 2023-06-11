package dev.gustavpersson.thorincompanies.data_access_layer.repositories;

import com.j256.ormlite.dao.Dao;
import dev.gustavpersson.thorincompanies.ThorinCompanies;
import dev.gustavpersson.thorincompanies.business_logic_layer.models.Company;
import dev.gustavpersson.thorincompanies.data_access_layer.Database;
import dev.gustavpersson.thorincompanies.data_access_layer.entities.CompanyEntity;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CompanyRepository {

    private final Dao companyDao;
    private final Database database;

    public CompanyRepository(ThorinCompanies plugin) throws SQLException {

        database = new Database(plugin);
        companyDao = database.getDao(CompanyEntity.class);
    }

    public void createCompany(Company company) throws Exception {
        PreparedStatement query = database.getConnection().prepareStatement(
                "INSERT INTO companies (name, ownerUuid, createdAt) VALUES (?, ?, ?)");
        query.setString(1, company.getName());
        query.setString(2, company.getOwnerUuid());
        query.setDate(3, company.getCreatedAt());
        query.execute();
        //companyDao.create()
    }

}
