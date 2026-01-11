package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.api.utils.ConfigManager;

public class DatabaseManager {

	private static final String DB_URL = ConfigManager.getProperty("DB_URL");
	private static final String DB_USER_NAME = ConfigManager.getProperty("DB_USER_NAME");
	private static final String DB_PASSWORD = ConfigManager.getProperty("DB_PASSWORD");
	private volatile static Connection con;

	public static void createConnection() throws SQLException {
		if (con == null) { //First check which all the parallel threads will enter
			synchronized (DatabaseManager.class) {
				if (con == null) { //only and only for the first Connection Request
					con = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD);
				}
				System.out.println(con);
			}
		}
	}
}
