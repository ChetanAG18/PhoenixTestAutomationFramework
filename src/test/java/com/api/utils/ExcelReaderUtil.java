package com.api.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReaderUtil {

	public static void main(String[] args) throws IOException {
		
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("testData/PhoenixTestData.xlsx");
		XSSFWorkbook myWorkBook = new XSSFWorkbook(is);
		XSSFSheet mySheet =  myWorkBook.getSheet("LoginTestData");
		XSSFRow row;
		XSSFCell cell;
		
		int lastRowIndex = mySheet.getLastRowNum();
		
		XSSFRow rowHeader = mySheet.getRow(0);
		int lastIndexOfColumn = rowHeader.getLastCellNum() - 1;
		
		for(int rowIndex = 0; rowIndex <= lastRowIndex; rowIndex++) {
			for(int colIndex = 0; colIndex <= lastIndexOfColumn; colIndex++) {
				row = mySheet.getRow(rowIndex);
				cell = row.getCell(colIndex);
				System.out.print(cell + " ");
			}
			System.out.println();
		}
	}

}
