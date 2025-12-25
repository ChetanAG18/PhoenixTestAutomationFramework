package com.demo.csv;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class ReadCSVFile {

	public static void main(String[] args) throws IOException, CsvException {
		
//		File csvFile = new File("C:\\Users\\Admin\\Desktop\\SDET with Jatin\\Java Module\\PhoenixTestAutomationFramework\\src\\main\\resources\\testData\\logincreds.csv");
//		FileReader fileReader = new FileReader(csvFile);
		
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("testData/logincreds.csv");
		InputStreamReader isr = new InputStreamReader(is);
		CSVReader csvReader = new CSVReader(isr);
		
		List<String []> dataList = csvReader.readAll();
		
		for(String [] data :dataList) {
			System.out.println(data[0]);
			System.out.println(data[1]);
		}
	}

}
