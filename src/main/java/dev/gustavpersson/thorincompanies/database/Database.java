package dev.gustavpersson.thorincompanies.database;

import dev.gustavpersson.thorincompanies.ThorinCompanies;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {

    private final ThorinCompanies plugin;

    public Database(ThorinCompanies plugin) {
        this.plugin = plugin;
    }

    public Connection getConnection() throws Exception {
        try {
            Class.forName("org.h2.Driver");
            return DriverManager.getConnection("jdbc:h2:" + plugin.getDataFolder().getAbsolutePath() + "/data/companies");
        } catch(SQLException exception) {
            throw new Exception("There was an issue establishing the connection to the database.");
        }
    }

    public void initializeDatabase() throws Exception {

        try {
            PreparedStatement query = getConnection().prepareStatement(
                "CREATE TABLE IF NOT EXISTS companies (" +
                        "id int NOT NULL AUTO_INCREMENT," +
                        "name varchar(255) NOT NULL" +
                    ")");

            query.execute();

        } catch(Exception exception) {
            throw new Exception("Not able to initialize database.");
        }

    }

}
