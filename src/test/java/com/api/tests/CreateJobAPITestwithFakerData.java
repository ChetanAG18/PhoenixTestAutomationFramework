package com.api.tests;

import static com.api.constant.Role.FD;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.services.JobService;
import com.api.utils.FakerDataGenerator;
import com.database.dao.CustomerAddressDao;
import com.database.dao.CustomerDao;
import com.database.dao.JobHeadDao;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.database.model.JobHeadModel;

@Listeners(com.listeners.APITestListeners.class)
public class CreateJobAPITestwithFakerData {
	
	private CreateJobPayload createJobPayload;
	private static final String COUNTRY = "India";
	private JobService jobService;
	
	@BeforeMethod(description = "Creating create job api request payload and Instantiating the JobService object")
	public void setUp() {
		
	    createJobPayload = FakerDataGenerator.generateFakeCreateJobData();
	    jobService = new JobService();
	}
	
	
	@Test(description = "Verifying if create job api is able to create inwarranty job",  groups =  {"api", "regression", "smoke"})
	public void createJobAPITest() {
		
		int customerId = jobService.create(FD, createJobPayload)
		.then()
		.spec(responseSpec_OK())
		.body("message", equalTo("Job created successfully. "))
		.body("data.mst_service_location_id", equalTo(1))
		.body("data.job_number", startsWith("JOB_"))
		.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
		.extract().body().jsonPath().getInt("data.tr_customer_id");
		
		Customer expectedCustomerData = createJobPayload.customer();
		CustomerDBModel actualCustomerDataInDB = CustomerDao.getCustomerInfo(customerId);
		
		Assert.assertEquals(expectedCustomerData.first_name(), actualCustomerDataInDB.getFirst_name());
		Assert.assertEquals(expectedCustomerData.last_name(), actualCustomerDataInDB.getLast_name());
		Assert.assertEquals(expectedCustomerData.mobile_number(), actualCustomerDataInDB.getMobile_number());
		Assert.assertEquals(expectedCustomerData.mobile_number_alt(), actualCustomerDataInDB.getMobile_number_alt());
		Assert.assertEquals(expectedCustomerData.email_id(), actualCustomerDataInDB.getEmail_id());
		Assert.assertEquals(expectedCustomerData.email_id_alt(), actualCustomerDataInDB.getEmail_id_alt());
		
		CustomerAddressDBModel customerAddressDataFromDB = CustomerAddressDao.getCustomerAddressData(actualCustomerDataInDB.getTr_customer_address_id());
		
		Assert.assertEquals(customerAddressDataFromDB.getFlat_number(), createJobPayload.customer_address().flat_number());
		Assert.assertEquals(customerAddressDataFromDB.getApartment_name(), createJobPayload.customer_address().apartment_name());
		Assert.assertEquals(customerAddressDataFromDB.getStreet_name(), createJobPayload.customer_address().street_name());
		Assert.assertEquals(customerAddressDataFromDB.getLandmark(), createJobPayload.customer_address().landmark());
		Assert.assertEquals(customerAddressDataFromDB.getArea(), createJobPayload.customer_address().area());
		Assert.assertEquals(customerAddressDataFromDB.getPincode(), createJobPayload.customer_address().pincode());
		Assert.assertEquals(customerAddressDataFromDB.getCountry(), createJobPayload.customer_address().country());
		Assert.assertEquals(customerAddressDataFromDB.getState(), createJobPayload.customer_address().state());
		
		JobHeadModel jobHeadDataFromDB = JobHeadDao.getDataFromJobHead(customerId);
		Assert.assertEquals(jobHeadDataFromDB.getMst_oem_id(), createJobPayload.mst_oem_id());
		Assert.assertEquals(jobHeadDataFromDB.getMst_service_location_id(), createJobPayload.mst_service_location_id());
		Assert.assertEquals(jobHeadDataFromDB.getMst_warrenty_status_id(), createJobPayload.mst_warrenty_status_id());
		Assert.assertEquals(jobHeadDataFromDB.getMst_platform_id(), createJobPayload.mst_platform_id());
	}

}
