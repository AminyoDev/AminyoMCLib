package dev.aminyo.aminyomclib.bungee.database.h2;

import dev.aminyo.aminyomclib.core.database.ISqlConnection;
import dev.aminyo.aminyomclib.core.database.h2.H2Config;
import net.md_5.bungee.api.ProxyServer;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2ConnectionBungee extends H2Config implements ISqlConnection {

    public H2ConnectionBungee(String fileLocation, String username, String password) {
        super(fileLocation, username, password);
        try {
            super.connection = openConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Connection openConnection() throws SQLException, ClassNotFoundException {
        if(checkConnection())
            return connection;

        String connectionURL = "jdbc:h2:file:" + super.getFileLocation();
        Class.forName("org.h2.Driver");
        connection = DriverManager.getConnection(connectionURL, super.getUser(), super.getPassword());

        if (connection == null) {
            ProxyServer.getInstance().getLogger().warning(" Creating H2 connection to file failed. " +
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
