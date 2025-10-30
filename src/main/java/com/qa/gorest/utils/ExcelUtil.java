package com.qa.gorest.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {

	private final static String TEST_DATA_SHEET_PATH = "src/test/resources/testData/UsersData.xlsx";
	private static Workbook book;
	private static Sheet sheet;

	public static Object[][] getTestDataFromSheet(String sheetName) {
		Object obj[][] = null;

		try {
			FileInputStream fp = new FileInputStream(TEST_DATA_SHEET_PATH);
			try {

				book = WorkbookFactory.create(fp);
				sheet = book.getSheet(sheetName);
				
			       int rows = sheet.getPhysicalNumberOfRows();
			        int cols = 3;

				obj = new Object[rows-1][cols];

				System.out.println("Total length is : "+obj.length);
				
				for (int i = 1; i < rows; i++) {
		            Row row = sheet.getRow(i);
		            for (int j = 0; j < cols; j++) {
		                Cell cell = row.getCell(j);
		                obj[i - 1][j] = (cell == null) ? "" : cell.toString();
		            }
		        }
			} catch (EncryptedDocumentException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return obj;

	}

}
