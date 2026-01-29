package com.api.services;

import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constant.Role;
import com.api.utils.SpecUtil;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class JobService {

	private static final String CREATEJOB_ENDPOINT = "/job/create";
	private static final String SEARCH_ENDPOINT = "/job/search";
	private static final Logger LOGGER = LogManager.getLogger(JobService.class);
	
	@Step("Creating Inwarranty Job with Create Job API")
	public Response create(Role role, Object payload) {
		LOGGER.info("Making request to {} with the role {} and the payload {}", CREATEJOB_ENDPOINT, role, payload);
		return given().spec(requestSpecWithAuth(Role.FD, payload)).when().post(CREATEJOB_ENDPOINT);
	}
	
	@Step("Making Search API Request")
	public Response search(Role role, Object payload) {
		LOGGER.info("Making request to {} with the role {} and the payload {}", SEARCH_ENDPOINT, role, payload);
		return given().spec(SpecUtil.requestSpecWithAuth(role, payload)).post(SEARCH_ENDPOINT);
	}

}
