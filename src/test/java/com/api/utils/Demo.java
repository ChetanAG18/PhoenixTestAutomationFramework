package com.api.utils;

import java.io.File;

public class Demo {

	public static void main(String[] args) {
		File file = new File(System.getProperty("user.dir"));
		System.out.println(file);
	}

}
