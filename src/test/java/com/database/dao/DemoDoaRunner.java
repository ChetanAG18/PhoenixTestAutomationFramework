package com.database.dao;

import java.sql.SQLException;

import org.testng.Assert;

import com.api.request.model.Customer;
import com.database.model.CustomerDBModel;

public class DemoDoaRunner {

	public static void main(String[] args) throws SQLException {
		CustomerDBModel customerDBData = CustomerDao.getCustomerInfo();
		System.out.println(customerDBData);
		System.out.println(customerDBData.getFirst_name());
		System.out.println(customerDBData.getLast_name());
		System.out.println(customerDBData.getEmail_id());
		
		Customer customer = new Customer("Chetan", "Geddappanavar", "7090191744", "", "agchetan18@gmail.com", "");
		System.out.println(customer.first_name());
		Assert.assertEquals(customerDBData.getFirst_name(), customer.first_name());
	}

}
