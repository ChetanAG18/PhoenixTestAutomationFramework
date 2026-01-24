package com.database;

import java.sql.Connection;
import java.sql.SQLException;

import com.api.utils.ConfigManager;
import com.api.utils.EnvUtil;
import com.api.utils.VaultDBConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseManager {

//	private static final String DB_URL = VaultDBConfig.getSecret("DB_URL");
//	private static final String DB_USER_NAME = VaultDBConfig.getSecret("DB_USERNAME");
//	private static final String DB_PASSWORD = VaultDBConfig.getSecret("DB_PASSWORD");
	private static final int MAXIMUM_POOL_SIZE = Integer.parseInt(ConfigManager.getProperty("MAXIMUM_POOL_SIZE"));
	private static final int MINIMUM_IDLE_COUNT = Integer.parseInt(ConfigManager.getProperty("MINIMUM_IDLE_COUNT"));
	private static final int CONNECTION_TIMEOUT_IN_SECS = Integer.parseInt(ConfigManager.getProperty("CONNECTION_TIMEOUT_IN_SECS"));
	private static final int IDLE_TIMEOUT_IN_SECS = Integer.parseInt(ConfigManager.getProperty("IDLE_TIMEOUT_IN_SECS"));
	private static final int MAX_LIFETIME_IN_MINS = Integer.parseInt(ConfigManager.getProperty("MAX_LIFETIME_IN_MINS"));
	private static final String HIKARICP_POOL_NAME = ConfigManager.getProperty("HIKARICP_POOL_NAME");

	private static Connection con;

	private static HikariConfig hikariConfig;
	private volatile static HikariDataSource hikariDataSource;
	
	private static boolean isVaultUp = true;
	private static final String DB_URL = loadSecret("DB_URL");
	private static final String DB_USER_NAME = loadSecret("DB_USERNAME");
	private static final String DB_PASSWORD = loadSecret("DB_PASSWORD");
	
	public static String loadSecret(String key) {
		String value = null;
		// Value will get its value from either Vault or Env
		
		if (isVaultUp) {
			value = VaultDBConfig.getSecret(key);
			if (value == null) { // when something is wrong with Vault!
				System.err.println("Vault is Down!! or some issue with Vault");
				isVaultUp = false;
			} else {
				System.out.println("READING VALUE FROM VAULT........");
				return value; // Coming from Vault
			}
		}
		
		//We need to pick up data from Env!!
		System.out.println("READING VALUE FROM ENV.........");
		value = EnvUtil.getValue(key);
		return value;
	}

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
