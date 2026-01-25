package com.api.tests;

import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import com.api.services.AuthService;

public class LoginAPITest {
	
	private UserCredentials userCredentials;
	private AuthService authService;
	
	@BeforeMethod(description = "Create the Payload for Login API and Instantiating the AuthService object")
	public void setUp() {
		userCredentials = new UserCredentials("iamfd", "password");
		authService = new AuthService();
	}
	
	@Test(description = "Verifying if login api is working for FD user", groups = {"api", "regression", "smoke"} )
	public void loginAPITest() {
		
		authService.login(userCredentials)
		.then()
			.spec(responseSpec_OK())
			.body("message", equalTo("Success"))
		.and()
			.body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));
	}

}
