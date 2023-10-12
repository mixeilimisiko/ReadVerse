package dao;

import constants.ConnectionConstants;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static ConnectionManager instance;
    private BasicDataSource dataSource;

    private ConnectionManager() {
        // Set up the connection pool parameters
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(ConnectionConstants.URL);
        dataSource.setUsername(ConnectionConstants.USERNAME);
        dataSource.setPassword(ConnectionConstants.PASSWORD);

    }

    public static synchronized ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    public Connection getConnection(boolean isTest) throws SQLException {
        if(isTest) {
            return getTestConnection();
        }
        return dataSource.getConnection();
    }

    private Connection getTestConnection() throws SQLException {
        return DriverManager.getConnection(ConnectionConstants.TEST_URL,
                ConnectionConstants.TEST_USERNAME, ConnectionConstants.TEST_PASSWORD);
    }

    public void releaseConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}