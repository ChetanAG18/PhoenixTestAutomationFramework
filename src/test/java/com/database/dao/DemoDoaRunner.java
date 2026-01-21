package com.database.dao;

import static com.api.utils.DateTimeUtil.getTimeWithDaysAgo;

import java.sql.SQLException;

import org.testng.Assert;

import com.api.constant.Model;
import com.api.constant.Product;
import com.api.request.model.Customer;
import com.api.request.model.CustomerProduct;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;

public class DemoDoaRunner {

	public static void main(String[] args) throws SQLException {
		//System.out.println(CustomerAddressDao.getCustomerAddressData(162303));
		
		System.out.println(CustomerProductDao.getCustomerProductInfoFromDB(162875));
		
		CustomerProduct customerProduct = new CustomerProduct(getTimeWithDaysAgo(10), "78920576984787", "78920576984787", "78920576984787", getTimeWithDaysAgo(10),
				Product.NEXUS_2.getCode(), Model.NEXUS_2_BLUE.getCode());
		
		System.out.println(customerProduct);
	}

}
