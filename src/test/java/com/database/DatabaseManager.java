package com.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.utils.ConfigManager;
import com.api.utils.EnvUtil;
import com.api.utils.VaultDBConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.qameta.allure.Step;

public class DatabaseManager {

	private static final Logger LOGGER = LogManager.getLogger(DatabaseManager.class);
	
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
	
	@Step("Loading Database Secrets")
	public static String loadSecret(String key) {
		String value = null;
		// Value will get its value from either Vault or Env
		
		if (isVaultUp) {
			value = VaultDBConfig.getSecret(key);
			if (value == null) { // when something is wrong with Vault!
				LOGGER.error("Vault is Down!! or some issue with Vault");
				isVaultUp = false;
			} else {
				LOGGER.info("Reading the value for key {} from vault.......", key);
				return value; // Coming from Vault
			}
		}
		
		//We need to pick up data from Env!!
		LOGGER.info("READING VALUE FROM ENV.........");
		value = EnvUtil.getValue(key);
		return value;
	}
	
	@Step("Initializing the Database Pool Connection")
	private static void initializePool() {
		if (hikariDataSource == null) { // First check which all the parallel threads will enter
			LOGGER.warn("Database Connection is not available.... Creating HikariDataSource");
			
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
					LOGGER.info("Hikari Data Source is created!!!");
				}
			}
		}
	}
	
	@Step("Getting the Database Connection")
	public static Connection getConnection() throws SQLException {
		Connection connection = null;

		if (hikariDataSource == null) {
			LOGGER.info("Initializing the Database Conection using HikariCP!!!");
			initializePool(); // Automatic Initialization of HikariDataSource
		} else if (hikariDataSource.isClosed()) {
			LOGGER.error("HIKARI DATA SOURCE IS CLOSED");
			throw new SQLException("HIKARI DATA SOURCE IS CLOSED");
		}
		connection = hikariDataSource.getConnection();

		return connection;
	}
}
