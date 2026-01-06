package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.poiji.bind.Poiji;

public class ExcelReaderUtil2 {
	
	private ExcelReaderUtil2() {
		
	}

	public static <T> Iterator<T> loadTestData(String pathOfExcelFile, String sheetName, Class<T> clazz){
		
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(pathOfExcelFile);
		XSSFWorkbook myWorkBook = null;
		try {
			myWorkBook = new XSSFWorkbook(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		XSSFSheet mySheet =  myWorkBook.getSheet(sheetName); 
		
		List<T> dataList = Poiji.fromExcel(mySheet, clazz);
		return dataList.iterator();
	}

}
