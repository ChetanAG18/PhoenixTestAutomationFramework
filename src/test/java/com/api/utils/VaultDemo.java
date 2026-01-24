package com.api.utils;

import java.util.Map;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import com.bettercloud.vault.response.LogicalResponse;

public class VaultDemo {
	public static void main(String[] args) throws VaultException {
		
		String data = System.getenv("VAULT_SERVER");
		System.out.println(data);
		
		String data2 = System.getenv("VAULT_TOKEN");
		System.out.println(data2);
		
//		VaultConfig vautlConfig = new VaultConfig()
//				.address("http://13.49.75.181:8200")
//				.token("root")
//				.build();
//		
//		Vault vault = new Vault(vautlConfig);
//		
//		LogicalResponse response = vault.logical().read("secret/phoenix/qa/database");
//		Map<String, String> dataMap = response.getData();
//		
//		System.out.println(dataMap.get("DB_URL"));
//		System.out.println(dataMap.get("DB_USERNAME"));
//		System.out.println(dataMap.get("DB_PASSWORD"));
	}
}
