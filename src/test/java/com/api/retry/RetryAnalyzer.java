package com.api.retry;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

	private static final int MAX_ATTEMPTS = 2;
	private int count = 1;

	@Override
	public boolean retry(ITestResult result) {
		if (count <= MAX_ATTEMPTS) {
			count++;
			return true;
		}
		return false;
	}

}
