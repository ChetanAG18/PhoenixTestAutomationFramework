package com.api.tests;

import static com.api.constant.Role.FD;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.services.UserService;

public class UserDetailsAPITest {
	
	private UserService userService;
	
	@BeforeMethod(description = "Instantiating the UserService object")
	public void setup() {
		userService = new UserService();
	}
	
	@Test(description = "Verify if the Userdetails API response is shown correctly", groups =  {"api", "regression", "smoke"})
	public void userDetailsAPITest() {
		
		userService.userdetails(FD)
		.then()
			.spec(responseSpec_OK())
			.body(matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"));
			
			
	}

}
