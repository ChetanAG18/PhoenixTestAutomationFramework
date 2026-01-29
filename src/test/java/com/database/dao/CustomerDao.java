package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.database.DatabaseManager;
import com.database.model.CustomerDBModel;

import io.qameta.allure.Step;

public class CustomerDao {
	
	private static final Logger LOGGER = LogManager.getLogger(CustomerDao.class);
	private static final String CUSTOMER_DETAILS_QUERY = """
				Select * from tr_customer where id = ?
			""";
	
	private CustomerDao() {
		
	}
	
	@Step("Retrieving the Customer informaton from Database for the specific customer id")
	public static CustomerDBModel getCustomerInfo(int customerId) {
		CustomerDBModel customerDBModel = null;
		try {
			LOGGER.info("Getting the connection from the Database Manager");
			Connection connection = DatabaseManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(CUSTOMER_DETAILS_QUERY);
			preparedStatement.setInt(1, customerId);
			
			LOGGER.info("Executing the SQL Query {}", CUSTOMER_DETAILS_QUERY);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				customerDBModel = new CustomerDBModel(
						resultSet.getInt("id"),
						resultSet.getString("first_name"),
						resultSet.getString("last_name"),
						resultSet.getString("mobile_number"),
						resultSet.getString("mobile_number_alt"),
						resultSet.getString("email_id"),
						resultSet.getString("email_id_alt"),
						resultSet.getInt("tr_customer_address_id"));
			}
		} catch (SQLException e) {
			LOGGER.error("Cannot convert the result set to the CustomerDBModel bean", e);
		}
		return customerDBModel;
	}

}
