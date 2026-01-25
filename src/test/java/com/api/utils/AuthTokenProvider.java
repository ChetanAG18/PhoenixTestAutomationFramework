package com.api.utils;

import static com.api.constant.Role.ENG;
import static com.api.constant.Role.FD;
import static com.api.constant.Role.QC;
import static com.api.constant.Role.SUP;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.api.constant.Role;
import com.api.request.model.UserCredentials;

import io.restassured.http.ContentType;

public class AuthTokenProvider {
	
	private static Map<Role, String> tokenCache = new ConcurrentHashMap<Role, String>();
	
	private AuthTokenProvider() {
		
	}

	public static String getToken(Role role) {
		
		if(tokenCache.containsKey(role)) {
			return tokenCache.get(role);
		}
		
		UserCredentials userCredentials = null ;
		
		if (role == FD) {
			userCredentials = new UserCredentials("iamfd", "password");
		} 
		else if (role == SUP) {
			userCredentials = new UserCredentials("iamsup", "password");
		} 
		else if (role == ENG) {
			userCredentials = new UserCredentials("iameng", "password");
		} 
		else if (role == QC) {
			userCredentials = new UserCredentials("iamqc", "password");
		}
		
		String token = given().baseUri(ConfigManager.getProperty("BASE_URI"))
			.contentType(ContentType.JSON)
			.body(userCredentials)
		.when()
			.post("login")
		.then()
			.log().ifValidationFails()
			.statusCode(200)
			.body("message", equalTo("Success"))
			.extract().body()
			.jsonPath()
			.getString("data.token");
		
		tokenCache.put(role, token);
		return token;
		
	}

}
