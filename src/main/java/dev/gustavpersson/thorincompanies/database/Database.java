package dev.gustavpersson.thorincompanies.database;

import dev.gustavpersson.thorincompanies.ThorinCompanies;
import org.bukkit.Bukkit;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {

    private final ThorinCompanies plugin;

    public Database(ThorinCompanies plugin) {
        this.plugin = plugin;
    }

    public Connection getConnection() {

        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:" + plugin.getDataFolder().getAbsolutePath() + "/data/companies");
        } catch(SQLException | ClassNotFoundException exception) {
            plugin.getLogger().severe(exception.toString());
            plugin.getLogger().severe("There was an issue establishing a connection to the database.");
        }

        return connection;
    }

    public void initializeDatabase() {

        String companiesTableQuery = """
                    CREATE TABLE IF NOT EXISTS companies (
                        id int NOT NULL AUTO_INCREMENT,
                        name varchar(25),
                        creationDate DATE DEFAULT CURRENT_DATE()
                    )
                    """;

        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(companiesTableQuery);

            preparedStatement.execute();

            preparedStatement.close();

            connection.close();

        } catch(SQLException exception) {
            plugin.getLogger().severe("Not able to initialize database.");
        }

    }

}
