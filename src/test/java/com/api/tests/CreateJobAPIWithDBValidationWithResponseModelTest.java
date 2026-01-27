package com.api.tests;

import static com.api.utils.DateTimeUtil.getTimeWithDaysAgo;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.constant.Model;
import com.api.constant.OEM;
import com.api.constant.Platform;
import com.api.constant.Problem;
import com.api.constant.Product;
import com.api.constant.Role;
import com.api.constant.ServiceLocation;
import com.api.constant.Warranty_Status;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.api.response.model.CreateJobResponseModel;
import com.api.services.JobService;
import com.database.dao.CustomerAddressDao;
import com.database.dao.CustomerDao;
import com.database.dao.CustomerProductDao;
import com.database.dao.JobHeadDao;
import com.database.dao.MapJobProblemDao;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.database.model.CustomerProductDBModel;
import com.database.model.JobHeadModel;
import com.database.model.MapJobProblemModel;

@Listeners(com.listeners.APITestListeners.class)
public class CreateJobAPIWithDBValidationWithResponseModelTest {
	
	private CreateJobPayload createJobPayload;
	private Customer customer;
	private CustomerAddress customerAddress;
	private CustomerProduct customerProduct;
	private JobService jobService;
	
	@BeforeMethod(description = "Creating create job api request payload and Instantiating the JobService object")
	public void setUp() {
		customer = new Customer("Chetan", "AG", "7090191744", "", "agchetan18@gmail.com", "");
		customerAddress = new CustomerAddress("D 404", "Vasant Galaxy", "Bangaru nagar", "Inorbit", "Mumbai", "411039", "India", "Maharashtra");
		customerProduct = new CustomerProduct(getTimeWithDaysAgo(10), "78925764368475", "78925764368475", "78925764368475", getTimeWithDaysAgo(10),
				Product.NEXUS_2.getCode(), Model.NEXUS_2_BLUE.getCode());
		
		Problems problems = new Problems(Problem.SMARTPHONE_IS_RUNNING_SLOW.getCode(), "Battery Issue");
		List<Problems>  problemsList = new ArrayList<Problems>();
		problemsList.add(problems);
		
		createJobPayload = new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(), Platform.FRONT_DESK.getCode(), Warranty_Status.IN_WARRANTY.getCode(), OEM.GOOGLE.getCode(), customer, customerAddress, customerProduct, problemsList);
		jobService = new JobService();
	}
	
	
	@Test(description = "Verifying if create job api is able to create inwarranty job",  groups =  {"api", "regression", "smoke"})
	public void createJobAPITest() {
		
		CreateJobResponseModel createJobResponseModel = jobService.create(Role.FD, createJobPayload)
		.then()
		.spec(responseSpec_OK())
		.body("message", equalTo("Job created successfully. "))
		.body("data.mst_service_location_id", equalTo(1))
		.body("data.job_number", startsWith("JOB_"))
		.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
		.extract().as(CreateJobResponseModel.class);
		
		int customerId = createJobResponseModel.getData().getTr_customer_id();
		
		CustomerDBModel customerDataFromDB = CustomerDao.getCustomerInfo(customerId);
		
		Assert.assertEquals(customer.first_name(), customerDataFromDB.getFirst_name());
		Assert.assertEquals(customer.last_name(), customerDataFromDB.getLast_name());
		Assert.assertEquals(customer.mobile_number(), customerDataFromDB.getMobile_number());
		Assert.assertEquals(customer.mobile_number_alt(), customerDataFromDB.getMobile_number_alt());
		Assert.assertEquals(customer.email_id(), customerDataFromDB.getEmail_id());
		Assert.assertEquals(customer.email_id_alt(), customerDataFromDB.getEmail_id_alt());
		
		CustomerAddressDBModel customerAddressDataFromDB = CustomerAddressDao.getCustomerAddressData(customerDataFromDB.getTr_customer_address_id());
		
		Assert.assertEquals(customerAddressDataFromDB.getFlat_number(), customerAddress.flat_number());
		Assert.assertEquals(customerAddressDataFromDB.getApartment_name(), customerAddress.apartment_name());
		Assert.assertEquals(customerAddressDataFromDB.getStreet_name(), customerAddress.street_name());
		Assert.assertEquals(customerAddressDataFromDB.getLandmark(), customerAddress.landmark());
		Assert.assertEquals(customerAddressDataFromDB.getArea(), customerAddress.area());
		Assert.assertEquals(customerAddressDataFromDB.getPincode(), customerAddress.pincode());
		Assert.assertEquals(customerAddressDataFromDB.getCountry(), customerAddress.country());
		Assert.assertEquals(customerAddressDataFromDB.getState(), customerAddress.state());
		
		int customerPeoductId = createJobResponseModel.getData().getTr_customer_product_id();		
		CustomerProductDBModel customerProductDBData = CustomerProductDao.getCustomerProductInfoFromDB(customerPeoductId);		
		Assert.assertEquals(customerProductDBData.getImei1(), customerProduct.imei1());
		Assert.assertEquals(customerProductDBData.getImei2(), customerProduct.imei2());
		Assert.assertEquals(customerProductDBData.getSerial_number(), customerProduct.serial_number());
		Assert.assertEquals(customerProductDBData.getDop(), customerProduct.dop());
		Assert.assertEquals(customerProductDBData.getPopurl(), customerProduct.popurl());
		Assert.assertEquals(customerProductDBData.getMst_model_id(), customerProduct.mst_model_id());
		
		int tr_job_head_id = createJobResponseModel.getData().getId();		
		MapJobProblemModel jobDataFromDB = MapJobProblemDao.getProblemDetails(tr_job_head_id);		
		Assert.assertEquals(jobDataFromDB.getMst_problem_id(), createJobPayload.problems().get(0).id());
		Assert.assertEquals(jobDataFromDB.getRemark(), createJobPayload.problems().get(0).remark());
		
		JobHeadModel jobHeadDataFromDB = JobHeadDao.getDataFromJobHead(customerId);
		Assert.assertEquals(jobHeadDataFromDB.getMst_oem_id(), createJobPayload.mst_oem_id());
		Assert.assertEquals(jobHeadDataFromDB.getMst_service_location_id(), createJobPayload.mst_service_location_id());
		Assert.assertEquals(jobHeadDataFromDB.getMst_warrenty_status_id(), createJobPayload.mst_warrenty_status_id());
		Assert.assertEquals(jobHeadDataFromDB.getMst_platform_id(), createJobPayload.mst_platform_id());
	}

}
