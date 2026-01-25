package com.api.services;

import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static io.restassured.RestAssured.given;

import com.api.constant.Role;

import io.restassured.response.Response;

public class JobService {

	private static final String CREATEJOB_ENDPOINT = "/job/create";

	public Response create(Role role, Object paylaod) {
		return given().spec(requestSpecWithAuth(Role.FD, paylaod)).when().post(CREATEJOB_ENDPOINT);
	}

}
