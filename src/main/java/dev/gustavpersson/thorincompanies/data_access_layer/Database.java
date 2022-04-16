package dev.gustavpersson.thorincompanies.data_access_layer;

import dev.gustavpersson.thorincompanies.ThorinCompanies;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Database {

    private final ThorinCompanies plugin;

    public Database(ThorinCompanies plugin) {
        this.plugin = plugin;
    }

    public Connection getConnection() throws Exception {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:" + plugin.getDataFolder().getAbsolutePath() + "/data/companies");
    }

    public void initializeDatabase() throws Exception {
        PreparedStatement query = getConnection().prepareStatement(
            "CREATE TABLE IF NOT EXISTS companies (" +
                    "id int NOT NULL AUTO_INCREMENT," +
                    "name char(36) NOT NULL," +
                    "createdAt date NOT NULL" +
                ")");

        query.execute();

    }

}
