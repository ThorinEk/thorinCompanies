package dev.gustavpersson.thorincompanies.database;

import dev.gustavpersson.thorincompanies.ThorinCompanies;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {

    private static ThorinCompanies plugin;

    public static Connection getConnection() {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:h2:" + plugin.getDataFolder().getAbsolutePath() + "/data/companies");
        } catch(SQLException exception) {
            Bukkit.getLogger().severe("There was an issue establishing the connection to the database.");
        }

        return connection;
    }

    public static void initializeDatabase() {

        String companiesTableQuery = """
                    CREATE TABLE IF NOT EXISTS companies (
                        id int NOT NULL AUTO_INCREMENT,
                        name varchar(25),
                        creationDate DATE DEFAULT CURRENT_DATE()
                    )
                    """;

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(companiesTableQuery);

            preparedStatement.execute();

            preparedStatement.close();

        } catch(SQLException exception) {
            Bukkit.getLogger().severe("Not able to initialize database.");
        }

    }

}
