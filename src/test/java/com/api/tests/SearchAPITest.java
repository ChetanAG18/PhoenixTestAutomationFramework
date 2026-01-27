package com.api.tests;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.*;

import static com.api.constant.Role.*;
import com.api.request.model.Search;
import com.api.services.JobService;
import static com.api.utils.SpecUtil.*;

@Listeners(com.listeners.APITestListeners.class)
public class SearchAPITest {
	
	private JobService jobService;
	private static final String JOB_NUMBER = "JOB_166084";
	private Search searchPayload;
	
	@BeforeMethod(description = "Instantiating JobService object and Creating the search payload")
	public void setup() {
		jobService = new JobService();
		searchPayload = new Search(JOB_NUMBER);
	}
	
	@Test(description = "Verify if the search api is working properly", groups = {"e2e", "smoke", "api"})
	public void searchAPITest() {
		jobService.search(FD, searchPayload)
		.then()
		.spec(responseSpec_OK())
		.body("message", equalTo("Success"));
	}
}
