package com.api.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.github.javafaker.Faker;

public class FakerDataGenerator {

	private static Faker faker = new Faker(new Locale("en-IND"));
	private static final String COUNTRY = "India";
	private static final Random RANDOM = new Random();
	private static final int MST_SERVICE_LOCATION_ID = 0;
	private static final int MST_PLATFORM_ID = 2;
	private static final int MST_WARRANTY_STATUS_ID = 1;
	private static final int MST_OEM_ID = 1;
	private static final int PRODUCT_ID = 1;
	private static final int MST_MODEL_ID = 1;

	private FakerDataGenerator() {

	}

	public static CreateJobPayload generateFakeCreateJobData() {
		Customer customer = generateFakeCustomerData();
		CustomerAddress customerAddress = generateFakeCustomerAddressData();
		CustomerProduct customerProduct = generateFakeCustomerProduct();
		List<Problems> problemsList = generateFakeProblemsList();
		CreateJobPayload payload = new CreateJobPayload(MST_SERVICE_LOCATION_ID, MST_PLATFORM_ID,
				MST_WARRANTY_STATUS_ID, MST_OEM_ID, customer, customerAddress, customerProduct, problemsList);
		return payload;
	}
	
	public static Iterator<CreateJobPayload> generateFakeCreateJobData(int count) {
		List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();
		for (int i = 1; i <= count; i++) {
			Customer customer = generateFakeCustomerData();
			CustomerAddress customerAddress = generateFakeCustomerAddressData();
			CustomerProduct customerProduct = generateFakeCustomerProduct();
			List<Problems> problemsList = generateFakeProblemsList();
			CreateJobPayload payload = new CreateJobPayload(MST_SERVICE_LOCATION_ID, MST_PLATFORM_ID,
					MST_WARRANTY_STATUS_ID, MST_OEM_ID, customer, customerAddress, customerProduct, problemsList);
			payloadList.add(payload);
		}
		return payloadList.iterator();
	}

	private static List<Problems> generateFakeProblemsList() {
		String fakeRemrk = faker.lorem().sentence(5);
		int problemId = RANDOM.nextInt(27) + 1;
		Problems problems = new Problems(problemId, fakeRemrk);

		List<Problems> problemList = new ArrayList<Problems>();

		problemList.add(problems);
		return problemList;
	}

	private static CustomerProduct generateFakeCustomerProduct() {
		String dop = DateTimeUtil.getTimeWithDaysAgo(10);
		String imeiSerialNumber = faker.numerify("###############");
		String popUrl = faker.internet().url();

		CustomerProduct customerProduct = new CustomerProduct(dop, imeiSerialNumber, imeiSerialNumber, imeiSerialNumber,
				popUrl, PRODUCT_ID, MST_MODEL_ID);
		return customerProduct;
	}

	private static CustomerAddress generateFakeCustomerAddressData() {
		String flatNumber = faker.numerify("###");
		String apartmentName = faker.address().streetName();
		String streetName = faker.address().streetName();
		String landmark = faker.address().streetName();
		String area = faker.address().streetName();
		String pinCode = faker.numerify("#####");

		String state = faker.address().state();

		CustomerAddress customerAddress = new CustomerAddress(flatNumber, apartmentName, streetName, landmark, area,
				pinCode, COUNTRY, state);
		return customerAddress;
	}

	private static Customer generateFakeCustomerData() {
		String firstName = faker.name().firstName();
		String lastName = faker.name().lastName();
		String mobileNumber = faker.numerify("70########");
		String alternateMobileNumber = faker.numerify("70########");
		String customerEmailAddress = faker.internet().emailAddress(firstName);
		String altCustomerEmailAddress = faker.internet().emailAddress(firstName + lastName);

		Customer customer = new Customer(firstName, lastName, mobileNumber, alternateMobileNumber, customerEmailAddress,
				altCustomerEmailAddress);
		return customer;

	}
}
