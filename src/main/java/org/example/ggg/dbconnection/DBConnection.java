package org.example.ggg.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection dbConnection;
    private final Connection connection;

    private DBConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/wood", // Database URL
                "root",                              // Database username
                "1234"                               // Database password
        );
    }

    public static DBConnection getInstance() throws ClassNotFoundException, SQLException {
        if (dbConnection == null || dbConnection.connection.isClosed()) {
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }

    public Connection getConnection() {
        return connection;
    }
}
