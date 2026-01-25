package com.api.tests.datadriven;

import static com.api.utils.SpecUtil.requestSpec;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import com.api.services.AuthService;

public class LoginAPIJSONDataDrivenTest {
	
	private AuthService authService;
	
	@BeforeMethod(description = "Initializing the Auth Service")
	public void setup() {
		authService = new AuthService();
	}
	
	@Test(description = "Verifying if login api is working for FD user",
			groups = {"api", "regression", "datadriven"},
			dataProviderClass = com.dataproviders.DataProviderUtils.class,
			dataProvider = "LoginAPIJsonDataProvider"
			)
	public void loginAPITest(UserCredentials userCredentials) {
		
		authService.login(userCredentials)
		.then()
			.spec(responseSpec_OK())
			.body("message", equalTo("Success"))
		.and()
			.body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));
	}

}
