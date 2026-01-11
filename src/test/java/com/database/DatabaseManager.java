package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.api.utils.ConfigManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseManager {

	private static final String DB_URL = ConfigManager.getProperty("DB_URL");
	private static final String DB_USER_NAME = ConfigManager.getProperty("DB_USER_NAME");
	private static final String DB_PASSWORD = ConfigManager.getProperty("DB_PASSWORD");
	private static final int MAXIMUM_POOL_SIZE = Integer.parseInt(ConfigManager.getProperty("MAXIMUM_POOL_SIZE"));
	private static final int MINIMUM_IDLE_COUNT = Integer.parseInt(ConfigManager.getProperty("MINIMUM_IDLE_COUNT"));
	private static final int CONNECTION_TIMEOUT_IN_SECS = Integer
			.parseInt(ConfigManager.getProperty("CONNECTION_TIMEOUT_IN_SECS"));
	private static final int IDLE_TIMEOUT_IN_SECS = Integer.parseInt(ConfigManager.getProperty("IDLE_TIMEOUT_IN_SECS"));
	private static final int MAX_LIFETIME_IN_MINS = Integer.parseInt(ConfigManager.getProperty("MAX_LIFETIME_IN_MINS"));
	private static final String HIKARICP_POOL_NAME = ConfigManager.getProperty("HIKARICP_POOL_NAME");

	private static Connection con;

	private static HikariConfig hikariConfig;
	private volatile static HikariDataSource hikariDataSource;

	private static void initializePool() {
		if (hikariDataSource == null) { // First check which all the parallel threads will enter
			synchronized (DatabaseManager.class) {
				if (hikariDataSource == null) { // only and only for the first Connection Request
					hikariConfig = new HikariConfig();
					hikariConfig.setJdbcUrl(DB_URL);
					hikariConfig.setUsername(DB_USER_NAME);
					hikariConfig.setPassword(DB_PASSWORD);
					hikariConfig.setMaximumPoolSize(MAXIMUM_POOL_SIZE);
					hikariConfig.setMinimumIdle(MINIMUM_IDLE_COUNT);
					hikariConfig.setConnectionTimeout(CONNECTION_TIMEOUT_IN_SECS * 1000); // 10 secs
					hikariConfig.setIdleTimeout(IDLE_TIMEOUT_IN_SECS * 1000);
					hikariConfig.setMaxLifetime(MAX_LIFETIME_IN_MINS * 60 * 1000); // 30 Mins
					hikariConfig.setPoolName("Phoenix Test Automation Framework Pool");

					hikariDataSource = new HikariDataSource(hikariConfig);
				}
			}
		}
	}

	public static Connection getConnection() throws SQLException {
		Connection connection = null;

		if (hikariDataSource == null) {
			initializePool(); // Automatic Initialization of HikariDataSource
		} else if (hikariDataSource.isClosed()) {
			throw new SQLException("HIKARI DATA SOURCE IS CLOSED");
		}
		connection = hikariDataSource.getConnection();

		return connection;
	}
}
