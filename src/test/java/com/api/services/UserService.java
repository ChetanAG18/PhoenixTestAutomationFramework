package com.api.services;

import com.api.constant.Role;
import static com.api.utils.SpecUtil.*;

import static io.restassured.RestAssured.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class UserService {
	
	private static final String USERDETAILS_ENDPOINT = "/userdetails";
	private static final Logger LOGGER = LogManager.getLogger(UserService.class);
	
	@Step("Making UserDetails API Request")
	public Response userdetails(Role role) {
		LOGGER.info("Making request to {} with the role {}", USERDETAILS_ENDPOINT, role);
		Response response = given()
		.spec(requestSpecWithAuth(role))
	.when()
		.get(USERDETAILS_ENDPOINT);
		
		return response;
	}
}
