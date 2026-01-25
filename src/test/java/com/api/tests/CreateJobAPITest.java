package com.api.tests;

import static com.api.constant.Role.FD;
import static com.api.utils.DateTimeUtil.getTimeWithDaysAgo;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constant.Model;
import com.api.constant.OEM;
import com.api.constant.Platform;
import com.api.constant.Problem;
import com.api.constant.Product;
import com.api.constant.ServiceLocation;
import com.api.constant.Warranty_Status;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.api.services.JobService;

public class CreateJobAPITest {
	
	private CreateJobPayload createJobPayload;
	private JobService jobService;
	
	@BeforeMethod(description = "Creating create job api request payload and Instantiating the JobService object")
	public void setUp() {
		Customer customer = new Customer("Chetan", "AG", "7090191744", "", "agchetan18@gmail.com", "");
		CustomerAddress customerAddress = new CustomerAddress("D 404", "Vasant Galaxy", "Bangaru nagar", "Inorbit", "Mumbai", "411039", "India", "Maharashtra");
		CustomerProduct customerProduct = new CustomerProduct(getTimeWithDaysAgo(10), "19920302986569", "19920302986569", "19920302986569", getTimeWithDaysAgo(10),
				Product.NEXUS_2.getCode(), Model.NEXUS_2_BLUE.getCode());
		
		Problems problems = new Problems(Problem.SMARTPHONE_IS_RUNNING_SLOW.getCode(), "Battery Issue");
		List<Problems>  problemsList = new ArrayList<Problems>();
		problemsList.add(problems);
		
		createJobPayload = new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(), Platform.FRONT_DESK.getCode(), Warranty_Status.IN_WARRANTY.getCode(), OEM.GOOGLE.getCode(), customer, customerAddress, customerProduct, problemsList);
		jobService = new JobService();
	}
	
	
	@Test(description = "Verifying if create job api is able to create inwarranty job",  groups =  {"api", "regression", "smoke"})
	public void createJobAPITest() {
		
		jobService.create(FD, createJobPayload)
		.then()
		.spec(responseSpec_OK())
		.body("message", equalTo("Job created successfully. "))
		.body("data.mst_service_location_id", equalTo(1))
		.body("data.job_number", startsWith("JOB_"))
		.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"));
		
	}

}
