package com.api.services;

import static com.api.constant.Role.FD;
import static com.api.utils.SpecUtil.*;
import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constant.Role;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class DashboardService {

	private static final String COUNT_ENDPOINT = "/dashboard/count";
	private static final String DETAILS_ENDPOINT = "/dashboard/details";
	private static final Logger LOGGER = LogManager.getLogger(DashboardService.class);
	
	@Step("Making Count API Request for the role")
	public Response count(Role role) {
		LOGGER.info("Making request to the {} for the role {}", COUNT_ENDPOINT, role);
		return given().spec(requestSpecWithAuth(FD)).when().get(COUNT_ENDPOINT);
	}
	
	@Step("Making Count API Request without Auth token")
	public Response countWithNoAuth() {
		LOGGER.info("Making request to the {} with no Auth Token", COUNT_ENDPOINT);
		return given().spec(requestSpec()).when().get(COUNT_ENDPOINT);
	}
	
	@Step("Making Detils API Request")
	public Response details(Role role, Object paylaod) {
		LOGGER.info("Making request to the {} with the role {} and the payload {}", DETAILS_ENDPOINT, role, paylaod);
		return given().spec(requestSpecWithAuth(FD)).body(paylaod).when().post(DETAILS_ENDPOINT);
	}
}
