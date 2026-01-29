package com.api.tests;

import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.services.AuthService;
import com.dataproviders.api.bean.UserBean;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Listeners(com.listeners.APITestListeners.class)
@Epic("User Management")
@Feature("Authentication")
public class LoginAPITest {
	
	private UserBean userBean;
	private AuthService authService;
	
	@BeforeMethod(description = "Create the Payload for Login API and Instantiating the AuthService object")
	public void setUp() {
		userBean = new UserBean("iamfd", "password");
		authService = new AuthService();
	}
	
	@Story("Valid User should be able to login into the System")
	@Description("Verify if FD User is able to login via api")
	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Verifying if login api is working for FD user", groups = {"api", "regression", "smoke"} )
	public void loginAPITest() {
		
		authService.login(userBean)
		.then()
			.spec(responseSpec_OK())
			.body("message", equalTo("Success"))
		.and()
			.body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));
	}

}
