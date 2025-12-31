package com.api.tests;

import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.request.model.CreateJobPayload;
import com.api.utils.FakerDataGenerator;

public class CreateJobAPITestwithFakerData {
	
	private CreateJobPayload createJobPayload;
	private static final String COUNTRY = "India";
	
	@BeforeMethod(description = "Creating create job api request payload")
	public void setUp() {
		
	    createJobPayload = FakerDataGenerator.generateFakeCreateJobData();
	    
	}
	
	
	@Test(description = "Verifying if create job api is able to create inwarranty job",  groups =  {"api", "regression", "smoke"})
	public void createJobAPITest() {
		
	
		
		given()
		.spec(requestSpecWithAuth(Role.FD, createJobPayload))
		.when()
		.post("/job/create")
		.then()
		.spec(responseSpec_OK())
		.body("message", equalTo("Job created successfully. "))
		.body("data.mst_service_location_id", equalTo(1))
		.body("data.job_number", startsWith("JOB_"))
		.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"));
		
	}

}
