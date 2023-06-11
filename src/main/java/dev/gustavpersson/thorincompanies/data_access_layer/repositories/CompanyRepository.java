package dev.gustavpersson.thorincompanies.data_access_layer.repositories;

import com.j256.ormlite.dao.Dao;
import dev.gustavpersson.thorincompanies.ThorinCompanies;
import dev.gustavpersson.thorincompanies.data_access_layer.Database;
import dev.gustavpersson.thorincompanies.data_access_layer.entities.CompanyEntity;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.List;

public class CompanyRepository {
    private final Dao companyDao;
    private final Database database;

    public CompanyRepository(ThorinCompanies plugin) throws SQLException {

        database = new Database(plugin);
        companyDao = database.getDao(CompanyEntity.class);
    }

    public void createCompany(CompanyEntity company) throws Exception {
        companyDao.create(company);
    }

    public List<CompanyEntity> getAllCompanies() throws SQLException {
        return companyDao.queryForAll();
    }

}
