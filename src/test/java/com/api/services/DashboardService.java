package com.api.services;

import static com.api.constant.Role.FD;
import static com.api.utils.SpecUtil.*;
import static io.restassured.RestAssured.given;

import com.api.constant.Role;

import io.restassured.response.Response;

public class DashboardService {

	private static final String COUNT_ENDPOINT = "/dashboard/count";

	public Response count(Role role) {
		return given().spec(requestSpecWithAuth(FD)).when().get(COUNT_ENDPOINT);
	}
	
	public Response countWithNoAuth() {
		return given().spec(requestSpec()).when().get(COUNT_ENDPOINT);
	}
}
