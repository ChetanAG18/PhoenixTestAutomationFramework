package com.api.services;

import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static io.restassured.RestAssured.given;

import com.api.constant.Role;
import com.api.utils.SpecUtil;

import io.restassured.response.Response;

public class JobService {

	private static final String CREATEJOB_ENDPOINT = "/job/create";
	private static final String SEARCH_ENDPOINT = "/job/search";

	public Response create(Role role, Object paylaod) {
		return given().spec(requestSpecWithAuth(Role.FD, paylaod)).when().post(CREATEJOB_ENDPOINT);
	}
	
	public Response search(Role role, Object payload) {
		return given().spec(SpecUtil.requestSpecWithAuth(role, payload)).post(SEARCH_ENDPOINT);
	}

}
