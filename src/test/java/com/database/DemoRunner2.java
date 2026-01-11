package com.database;

import java.sql.SQLException;

public class DemoRunner2 {

	public static void main(String[] args) throws SQLException {
		
		DatabaseManagerOld.createConnection();
		long startTime = System.currentTimeMillis();
		
		for(int i = 1; i <= 10000; i++) {
			DatabaseManagerOld.createConnection();
			DatabaseManagerOld.createConnection();
			DatabaseManagerOld.createConnection();
			DatabaseManagerOld.createConnection();
	}
		long endTime = System.currentTimeMillis();
		
		System.out.println("Duration "+ (endTime - startTime) + " ms");
	}
}
