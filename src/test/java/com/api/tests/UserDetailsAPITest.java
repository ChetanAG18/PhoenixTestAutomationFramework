package com.api.tests;

import static com.api.utils.ConfigManager.getProperty;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

import org.testng.annotations.Test;

import static com.api.constant.Role.*;

import static com.api.utils.AuthTokenProvider.*;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;

public class UserDetailsAPITest {
	
	@Test
	public void userDetailsAPITest() {
		Header authHeader = new Header("Authorization", getToken(FD));
		given()
			.baseUri(getProperty("BASE_URI"))
		.and()
			.header(authHeader)
		.and()
			.accept(ContentType.JSON)
			.log().uri()
			.log().method()
			.log().headers()
			.log().body()
		.when()
			.get("userdetails")
		.then()
			.log().all()
			.statusCode(200)
			.time(lessThan(1000L))
		.and()
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"));
			
			
	}

}
