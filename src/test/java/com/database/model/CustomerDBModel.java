package com.database.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CustomerDBModel {
	private String first_name;
	private String last_name;
	private String mobile_number;
	private String mobile_number_alt;
	private String email_id;
	private String email_id_alt;

}
