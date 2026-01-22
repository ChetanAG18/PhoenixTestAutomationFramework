package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.DatabaseManager;
import com.database.model.CustomerProductDBModel;

public class CustomerProductDao {
	private static final String CUSTOMER_PRODUCT_QUERY = """
			select
			id,
			tr_customer_id,
			mst_model_id,
			dop,
			popurl,
			imei2,
			imei1,
			serial_number
			from tr_customer_product
			where id = ?
			""";

	public static CustomerProductDBModel getCustomerProductInfoFromDB(int tr_customer_product_id) {
		CustomerProductDBModel customerProductDBModel = null;
		try {
			Connection conn = DatabaseManager.getConnection();
			PreparedStatement ps = conn.prepareStatement(CUSTOMER_PRODUCT_QUERY);
			ps.setInt(1, tr_customer_product_id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				customerProductDBModel = new CustomerProductDBModel(rs.getInt("id"), rs.getInt("tr_customer_id"),
						rs.getInt("mst_model_id"), rs.getString("dop"), rs.getString("popurl"), rs.getString("imei2"),
						rs.getString("imei1"), rs.getString("serial_number"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return customerProductDBModel;
	}
}
