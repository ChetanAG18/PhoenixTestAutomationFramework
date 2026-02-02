package com.api.utils;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import com.bettercloud.vault.response.LogicalResponse;

import io.qameta.allure.Step;

public class VaultDBConfig {

	private static VaultConfig vaultConfig;
	private static Vault vault = null;
	private static final Logger LOGGER = LogManager.getLogger(VaultDBConfig.class);

	static {
		try {
			vaultConfig = new VaultConfig()
					.address(System.getenv("VAULT_SERVER"))
					.token(System.getenv("VAULT_TOKEN"))
					.build();
		} catch (VaultException e) {
			LOGGER.error("Something went wrong with the Vault Config", e);
			e.printStackTrace();
		}

		vault = new Vault(vaultConfig);
	}

	private VaultDBConfig() {

	}
	
	@Step("Retrieving the secret from the vault")
	public static String getSecret(String key) {
		LogicalResponse response = null;
		try {
			response = vault.logical().read("secret/phoenix/qa/database");
		} catch (VaultException e) {
			LOGGER.error("Something went wrong reading of the Vault response", e);
			e.printStackTrace();
			return null;  //if something goes wrong return null
		}
		Map<String, String> dataMap = response.getData();
		String secretKey = dataMap.get(key);
		LOGGER.info("Secret found in the vault");
		return secretKey;
	}

}
