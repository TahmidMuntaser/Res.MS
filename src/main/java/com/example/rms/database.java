package com.example.rms;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database extends Configs {


    static Connection dbConnection;
    public static Connection connectDB() throws SQLException {
        String connectionString = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName + "?" + "sslmode=verify-full";
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPassword);
        return dbConnection;
    }
}
