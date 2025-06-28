package dev.aminyo.aminyomclib.core.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface SqlConnection {
    Connection openConnection() throws SQLException, ClassNotFoundException;

    boolean closeConnection() throws SQLException;

    boolean checkConnection() throws SQLException;

    Connection getConnection();
}
