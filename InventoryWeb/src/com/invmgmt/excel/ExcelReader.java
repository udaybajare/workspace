package com.invmgmt.excel;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelReader {

	public static String fileToBeRead = "C:\\Users\\Uday\\Downloads\\ToDoList_24022018.xlsx";
	public static String OWNERSHIP = "Ownership";
	public static String sheetName = "WebSite Contain-Working Plan";
	int columnNumber = 0;
	boolean isColumnFound = false;
	
	public Set readFile() throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		Workbook workbook = WorkbookFactory.create(new File(fileToBeRead));

		DataFormatter dataFormatter = new DataFormatter();
		
		Set inventorySet = new HashSet<String>();
		
		workbook.forEach(sheet -> {
			sheet.forEach(row -> {
				row.forEach(cell -> {
					String cellValue = dataFormatter.formatCellValue(cell);
					
					if(sheet.getSheetName().trim().equals(sheetName) && !isColumnFound)
					{
						columnNumber++;
					}
					
					if (sheet.getSheetName().trim().equals(sheetName) && cellValue.equalsIgnoreCase(OWNERSHIP)) 
					{
						isColumnFound = true;
						System.out.println("#############" + sheet.getSheetName().trim());
						System.out.println("#############" + cellValue);
						
					}
				});
			});
		});
		
		System.out.println("################ "+columnNumber);
		
		workbook.forEach(sheet -> {
			sheet.forEach(row -> {
				if(sheet.getSheetName().trim().equals(sheetName))
				{
					Cell cell = row.getCell(columnNumber-1);
					String cellValue = dataFormatter.formatCellValue(cell);
					System.out.println(cellValue);
					if(cellValue!=null && !cellValue.equals("") && !cellValue.equals("Ownership"))
						inventorySet.add(cellValue);	
				}
				
			});
		});

		return inventorySet;
	}
}
