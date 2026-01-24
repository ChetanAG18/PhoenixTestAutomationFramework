package com.api.utils;

import java.util.Map;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import com.bettercloud.vault.response.LogicalResponse;

public class VaultDBConfig {

	private static VaultConfig vaultConfig;
	private static Vault vault;

	static {
		try {
			vaultConfig = new VaultConfig()
					.address(System.getenv("VAULT_SERVER"))
					.token(System.getenv("VAULT_TOKEN"))
					.build();
		} catch (VaultException e) {
			e.printStackTrace();
		}

		vault = new Vault(vaultConfig);
	}

	private VaultDBConfig() {

	}

	public static String getSecret(String key) {
		LogicalResponse response = null;
		try {
			response = vault.logical().read("secret/phoenix/qa/database");
		} catch (VaultException e) {
			e.printStackTrace();
			return null;  //if something goes wrong return null
		}
		Map<String, String> dataMap = response.getData();
		String secretKey = dataMap.get(key);
		return secretKey;
	}

}
