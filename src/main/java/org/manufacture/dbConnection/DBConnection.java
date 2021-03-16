package org.manufacture.dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {

    Connection connection = null;

    private static DBConnection dbConnection;

    public static DBConnection getConnection() {
        if (dbConnection == null) {
            return new DBConnection();
        }
        return dbConnection;
    }

    private DBConnection() {
    }

    public boolean tryConnect() {

        System.out.println("Testing connection to PostgreSQL JDBC");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
            return false;
        }
        System.out.println("PostgreSQL JDBC Driver successfully connected");
        connection = null;

        try {
            connection = DriverManager
                    .getConnection(ConnectConstants.DB_URL, ConnectConstants.USER, ConnectConstants.PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
            return false;
        }

        if (connection != null) {
            System.out.println("You successfully connected to database now");
        } else {
            System.out.println("Failed to make connection to database");
            return false;
        }
        return true;
    }

}