package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.database.DatabaseManager;
import com.database.model.CustomerAddressDBModel;

import io.qameta.allure.Step;

public class CustomerAddressDao {
	
	private static final Logger LOGGER = LogManager.getLogger(CustomerAddressDao.class);
	
	private static final String CUSTOMER_ADDRESS_QUERY = 
			"""
			  select 
			    id,
				flat_number,
				apartment_name,
				street_name,
				landmark,
				area,
				pincode,
				country,
				state
			    from tr_customer_address
			   where id = ?
			""";
	
	private CustomerAddressDao() {
		
	}
	
	@Step("Retrieving the Customer Address data from Database for the specific customer address id")
	public static CustomerAddressDBModel getCustomerAddressData(int customerAddressId) {
		CustomerAddressDBModel customerAddressDBModel = null;
		try {
			LOGGER.info("Getting the connection from the Database Manager");
			Connection conn = DatabaseManager.getConnection();
			PreparedStatement ps = conn.prepareStatement(CUSTOMER_ADDRESS_QUERY);
			ps.setInt(1, customerAddressId);
			
			LOGGER.info("Executing the SQL Query {}", CUSTOMER_ADDRESS_QUERY);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				customerAddressDBModel = new CustomerAddressDBModel(
						rs.getInt("id"),
						rs.getString("flat_number"),
						rs.getString("apartment_name"), 
						rs.getString("street_name"), 
						rs.getString("landmark"), 
						rs.getString("area"), 
						rs.getString("pincode"), 
						rs.getString("country"),
						rs.getString("state"));
			}
		} catch (SQLException e) {
			LOGGER.error("Cannot convert the result set to the CustomerAddressDBModel bean", e);
			e.printStackTrace();
		}
		
		return customerAddressDBModel;
	}
}
