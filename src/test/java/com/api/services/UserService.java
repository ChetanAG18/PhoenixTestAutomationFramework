package com.api.services;

import com.api.constant.Role;
import static com.api.utils.SpecUtil.*;

import static io.restassured.RestAssured.*;
import io.restassured.response.Response;

public class UserService {
	
	private static final String USERDETAILS_ENDPOINT = "/userdetails";
	
	public Response userdetails(Role role) {
		Response response = given()
		.spec(requestSpecWithAuth(role))
	.when()
		.get(USERDETAILS_ENDPOINT);
		
		return response;
	}
}
