package com.api.tests;

import static com.api.constant.Role.FD;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.notNullValue;

import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class MasterAPITest {
	
	@Test(description = "Verifying if master api is giving correct response",  groups =  {"api", "regression", "smoke"})
	public void masterAPITest() {
		given()
			.spec(requestSpecWithAuth(FD))
		.when()
			.post("master")  //default content-tpe application/url-formencoded
		.then()
			.spec(responseSpec_OK())
			.body("message", equalTo("Success"))
			.body("data", notNullValue())
			.body("data", hasKey("mst_oem"))
			.body("data", hasKey("mst_model"))
			.body("$", hasKey("message"))
			.body("$", hasKey("data"))
			.body("data.mst_oem.size()", equalTo(2))
			.body("data.mst_model.size()", greaterThan(0))
			.body("data.mst_oem.id", everyItem(notNullValue()))
			.body("data.mst_oem.name", everyItem(notNullValue()))
			.body(matchesJsonSchemaInClasspath("response-schema/MasterAPIResponseSchema.json"));
			
	}
	
	@Test(description = "Verifying if master api is giving correct status code for invalid token",  groups =  {"api", "negative", "regression", "smoke"})
	public void invalidTokenMasterAPITest() {
		given()
			.spec(requestSpec())
		.when()
			.post("master")
		.then()
			.spec(responseSpec_TEXT(401));
	}

}
