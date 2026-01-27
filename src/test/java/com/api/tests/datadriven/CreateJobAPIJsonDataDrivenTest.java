package com.api.tests.datadriven;

import static com.api.constant.Role.FD;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.request.model.CreateJobPayload;
import com.api.services.JobService;

@Listeners(com.listeners.APITestListeners.class)
public class CreateJobAPIJsonDataDrivenTest {
	
	private JobService jobService;
	
	@BeforeMethod(description = "Instantiating the JobService object")
	public void setUp() {
	    jobService = new JobService();
	}
	
	@Test(description = "Verifying if create job api is able to create inwarranty job",
			groups =  {"api", "regression", "datadriven", "faker"},
			dataProviderClass = com.dataproviders.DataProviderUtils.class,
			dataProvider = "CreateJobAPIJsonDataProvider")
	public void createJobAPITest(CreateJobPayload createJobPayload) {
		
		jobService.create(FD, createJobPayload)
		.then()
		.spec(responseSpec_OK())
		.body("message", equalTo("Job created successfully. "))
		.body("data.mst_service_location_id", equalTo(1))
		.body("data.job_number", startsWith("JOB_"))
		.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"));
		
	}

}
