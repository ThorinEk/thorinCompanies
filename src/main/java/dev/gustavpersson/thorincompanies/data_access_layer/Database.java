package dev.gustavpersson.thorincompanies.data_access_layer;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import dev.gustavpersson.thorincompanies.ThorinCompanies;
import dev.gustavpersson.thorincompanies.data_access_layer.entities.CompanyEntity;
import dev.gustavpersson.thorincompanies.data_access_layer.entities.EmployeeEntity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static String databaseUrl = "jdbc:mysql://root@localhost/thorincompanies";

    private JdbcConnectionSource connectionSource;

    private final ThorinCompanies plugin;

    public Database(ThorinCompanies plugin) {
        this.plugin = plugin;
    }

    public <T, ID> Dao<T, ID> getDao(Class<T> clazz) throws SQLException {
        Dao<T, ID> dao = DaoManager.lookupDao(connectionSource, clazz);
        if (dao == null) {
            dao = DaoManager.createDao(connectionSource, clazz);
        }
        return dao;
    }

    public void initializeDatabase() throws Exception {
        connectionSource = new JdbcConnectionSource(databaseUrl);
        Dao<CompanyEntity, Integer> companyDao = DaoManager.createDao(connectionSource, CompanyEntity.class);
        TableUtils.createTableIfNotExists(connectionSource, CompanyEntity.class);

    }

}
