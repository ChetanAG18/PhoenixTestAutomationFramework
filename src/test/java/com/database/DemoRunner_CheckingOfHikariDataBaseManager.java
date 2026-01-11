package com.database;

import java.sql.Connection;
import java.sql.SQLException;

public class DemoRunner_CheckingOfHikariDataBaseManager {

	public static void main(String[] args) throws SQLException {
		
		Connection conn = DatabaseManager.getConnection();
		System.out.println(conn);
	}
}
