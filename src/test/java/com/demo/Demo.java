package com.demo;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class Demo {
	
	private static Logger logger = LogManager.getLogger(Demo.class);

	public static void main(String[] args) {
		System.out.println("Inside the main method");
		logger.info("Inside the main method");
		int a= 10;
		System.out.println("Value of a is "+ a);
		logger.info("Value of a {} ", a);
		int b = 20;
		System.out.println("Value of b is "+ b);
		logger.info("Value of b {} ", b);
		
		int result = a+b;
		System.out.println("Result of addition " + result);
		logger.info("Result of addition {} ", result);
		System.out.println("Result is "+result);
		logger.info("Result is {} ", result);
		
		System.out.println("Program Ended!!!");
		logger.info("Program Ended!!!");
	}

}
