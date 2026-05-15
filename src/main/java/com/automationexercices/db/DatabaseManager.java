package com.automationexercices.db;

import com.automationexercices.utils.dataReader.PropertyReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseManager {

    public Connection createConnection() throws Exception {
        return DriverManager.getConnection(
                PropertyReader.getProperty("dbURL"),
                PropertyReader.getProperty("dbUser"),
                PropertyReader.getProperty("dbPassword")
        );
    }

    public String getSingleStringValue(String query) throws Exception {
        try (Connection connection = createConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
            return null;
        }
    }

    public String getSingleStringValue(String query, String parameter) throws Exception {
        try (Connection connection = createConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, parameter);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString(1);
                }
                return null;
            }
        }
    }

    public int getSingleIntValue(String query, String parameter) throws Exception {
        try (Connection connection = createConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, parameter);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
                return 0;
            }
        }
    }
}
