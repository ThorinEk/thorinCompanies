package dev.gustavpersson.thorincompanies.database;

import dev.gustavpersson.thorincompanies.ThorinCompanies;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {

    private ThorinCompanies plugin;

    public Database(ThorinCompanies _plugin) {
        this.plugin = _plugin;
    }

    public static Connection getConnection() {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:h2:" + plugin.getDataFolder().getAbsolutePath() + "/data/companies");
        } catch(SQLException exception) {
            plugin.getLogger().severe("There was an issue establishing the connection to the database.");
        }

        return connection;
    }

    public static void initializeDatabase() {

        PreparedStatement preparedStatement;
        try {
            preparedStatement = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS ")
        } catch(SQLException exception) {
            plugin.getLogger().severe("Not able to initialize database.");
        }

    }

}
