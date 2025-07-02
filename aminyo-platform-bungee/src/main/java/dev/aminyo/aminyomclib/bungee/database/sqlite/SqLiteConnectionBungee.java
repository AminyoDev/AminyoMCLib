package dev.aminyo.aminyomclib.bungee.database.sqlite;

import dev.aminyo.aminyomclib.bungee.utils.ConsoleColorUtil;
import dev.aminyo.aminyomclib.core.database.ISqlConnection;
import dev.aminyo.aminyomclib.core.database.sqlite.SqLiteConfig;
import net.md_5.bungee.api.ProxyServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqLiteConnectionBungee extends SqLiteConfig implements ISqlConnection {
    public SqLiteConnectionBungee(String fileLocation) {
        super(fileLocation);
        try {
            super.connection = openConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection openConnection() throws SQLException, ClassNotFoundException {
        if (checkConnection()) {
            return connection;
        }
        String connectionURL = "jdbc:sqlite:" + super.getFileLocation();
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection(connectionURL);

        if (connection == null) {
            ProxyServer.getInstance().getLogger().warning(" Creating SQLite connection to file failed. " +
                    "Check if given location is correct and required access is given: " + super.getFileLocation() + "");
        }
        return connection;
    }

    @Override
    public boolean closeConnection() throws SQLException {
        if (connection == null) {
            return false;
        }
        connection.close();
        return true;
    }

    @Override
    public boolean checkConnection() throws SQLException {
        return connection != null && !connection.isClosed();
    }
    @Override
    public Connection getConnection() {
        return connection;
    }
}
