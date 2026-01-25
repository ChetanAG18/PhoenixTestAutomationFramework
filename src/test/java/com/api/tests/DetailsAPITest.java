package com.api.tests;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.*;

import static com.api.constant.Role.*;
import com.api.request.model.Details;
import com.api.services.DashboardService;
import static com.api.utils.SpecUtil.*;

public class DetailsAPITest {
	
	private DashboardService dashboardService;
	private Details detailsPayload;
	
	@BeforeMethod(description = "Instantiating the DashboardService object and Creating he details payload")
	public void setup() {
		dashboardService = new DashboardService();
		detailsPayload = new Details("created_today");
	}
	
	@Test(description = "Verify if Details API is working properly", groups = {"api", "smoke", "e2e"})
	public void detailsAPITest() {
		dashboardService.details(FD, detailsPayload)
		.then()
		.spec(responseSpec_OK())
		.body("message", equalTo("Success"));
	}
}
