package com.api.tests;

import static com.api.constant.Role.FD;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.services.UserService;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Listeners(com.listeners.APITestListeners.class)
@Epic("User Management")
@Feature("Uer Details")
public class UserDetailsAPITest {
	
	private UserService userService;
	
	@BeforeMethod(description = "Instantiating the UserService object")
	public void setup() {
		userService = new UserService();
	}
	
	@Story("User Details should be shown")
	@Description("Verify if the Userdetails API response if shown correctly")
	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Verify if the Userdetails API response is shown correctly", groups =  {"api", "regression", "smoke"})
	public void userDetailsAPITest() {
		
		userService.userdetails(FD)
		.then()
			.spec(responseSpec_OK())
			.body(matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"));
			
			
	}

}
