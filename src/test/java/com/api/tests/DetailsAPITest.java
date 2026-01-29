package com.api.tests;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.*;

import static com.api.constant.Role.*;
import com.api.request.model.Details;
import com.api.services.DashboardService;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

import static com.api.utils.SpecUtil.*;

@Listeners(com.listeners.APITestListeners.class)
@Epic("Job Management")
@Feature("Job Details")
public class DetailsAPITest {
	
	private DashboardService dashboardService;
	private Details detailsPayload;
	
	@BeforeMethod(description = "Instantiating the DashboardService object and Creating he details payload")
	public void setup() {
		dashboardService = new DashboardService();
		detailsPayload = new Details("created_today");
	}
	
	@Story("Job Details should is shown corrently for FD")
	@Description("Verify if Details API is working properly")
	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Verify if Details API is working properly", groups = {"api", "smoke", "e2e"})
	public void detailsAPITest() {
		dashboardService.details(FD, detailsPayload)
		.then()
		.spec(responseSpec_OK())
		.body("message", equalTo("Success"));
	}
}
