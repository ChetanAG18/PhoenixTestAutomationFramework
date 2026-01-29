package com.api.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.cdimascio.dotenv.Dotenv;
import io.qameta.allure.Step;

public class EnvUtil {

	private static Dotenv dotenv;
	private static final Logger LOGGER = LogManager.getLogger(EnvUtil.class);

	static {
		LOGGER.info("Loading the .env file.....");
		dotenv = Dotenv.load();
	}

	private EnvUtil() {

	}
	
	@Step("Retrieving the secret from the .env file")
	public static String getValue(String varName) {
		LOGGER.info("Reading the value of {} from .env", varName);
		return dotenv.get(varName);
	}
}
