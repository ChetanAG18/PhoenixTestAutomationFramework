package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.database.DatabaseManager;
import com.database.model.MapJobProblemModel;

public class MapJobProblemDao {
	
	private static final Logger LOGGER = LogManager.getLogger(MapJobProblemDao.class);
	private static final String PROBLEM_QUERY =
			"""
			select * from map_job_problem where tr_job_head_id = ?
			""";
	
	private MapJobProblemDao() {
		
	}
	
	public static MapJobProblemModel getProblemDetails(int tr_job_head_id) {
		MapJobProblemModel mapJobProblemModel = null;
		try {
			LOGGER.info("Getting the connection from the Database Manager");
			Connection conn = DatabaseManager.getConnection();
			PreparedStatement ps = conn.prepareStatement(PROBLEM_QUERY);
			ps.setInt(1, tr_job_head_id);
			
			LOGGER.info("Executing the SQL Query {}", PROBLEM_QUERY);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				mapJobProblemModel = new MapJobProblemModel(
						rs.getInt("id"),
						rs.getInt("tr_job_head_id"),
						rs.getInt("mst_problem_id"),
						rs.getString("remark"));
			}
		} catch (SQLException e) {
			LOGGER.error("Cannot convert the result set to the MapJobProblemModel bean", e);
			e.printStackTrace();
		}
		
		return mapJobProblemModel;
	}
}
