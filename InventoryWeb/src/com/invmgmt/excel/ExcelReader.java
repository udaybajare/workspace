package com.invmgmt.excel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.ManagedBean;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellAddress;
import org.springframework.beans.factory.annotation.Autowired;

import com.invmgmt.dao.InventoryDao;
import com.invmgmt.entity.BOQHeader;
import com.invmgmt.entity.Inventory;
import com.invmgmt.entity.InventorySpec;

@ManagedBean
public class ExcelReader {

	@Autowired(required = true)
	private InventoryDao inventoryDao;

	// public static String fileToBeRead =
	// "C:\\Users\\Uday\\Desktop\\Projects\\Humdule\\Piping_BOQ_for_Thermic_Fluid.xls";
	public static String OWNERSHIP = "Ownership";
	public static String sheetName = "WebSite Contain-Working Plan";
	int columnNumber = 0;
	boolean isColumnFound = false;

	BOQHeader boqHeader = new BOQHeader();
	
	DataFormatter dataFormatter = new DataFormatter();

	public ArrayList readFile(String fileLocation) throws EncryptedDocumentException, InvalidFormatException, IOException {
	
	Workbook workbook = null;
	ArrayList invList = new ArrayList();
	
	try
	{
		workbook = WorkbookFactory.create(new File(fileLocation));

		CellAddress sellAddress = null;
		Set inventorySet = new HashSet<String>();
		
		Iterator<Sheet> sheet = workbook.sheetIterator();

		String column;
		int columnIndex=0;
		int rowIndex=0;
		while (sheet.hasNext()) {
			Sheet sheet1 = sheet.next();
			System.out.println("Working on sheet : "+sheet1.getSheetName());
			/*if (sheet1.getSheetName().equalsIgnoreCase("150NB Header")) 
			{*/
				Iterator<Row> row = sheet1.rowIterator();
				while (row.hasNext()) {
				    Row rowItem = row.next();
					Iterator<Cell> cell = rowItem.cellIterator();
					while (cell.hasNext()) {
						Cell cell1 = cell.next();
						
						String cellValue = dataFormatter.formatCellValue(cell1);

						
						  if(cellValue.trim().contains("Client"))
						      boqHeader.setClient(readValueInColumn(rowItem, cell1.getColumnIndex()+1));
						  else if(cellValue.trim().contains("Site"))
						      boqHeader.setSite(readValueInColumn(rowItem, cell1.getColumnIndex()+1));
						  else if(cellValue.trim().contains("Project"))
						      boqHeader.setProject(readValueInColumn(rowItem, cell1.getColumnIndex()+1));
						  else if(cellValue.trim().contains("D.Name"))
						      boqHeader.setdName(readValueInColumn(rowItem, cell1.getColumnIndex()+1));
						  else if(cellValue.trim().contains("Utility"))
						      boqHeader.setUtility(readValueInColumn(rowItem, cell1.getColumnIndex()+1));
						  else if(cellValue.trim().contains("Pressure"))
						      boqHeader.setPressure(readValueInColumn(rowItem, cell1.getColumnIndex()+1));
						  else if(cellValue.trim().contains("Temp"))
						      boqHeader.setTemp(readValueInColumn(rowItem, cell1.getColumnIndex()+1));
						  else if(cellValue.trim().contains("D.No"))
						      boqHeader.setdNo(readValueInColumn(rowItem, cell1.getColumnIndex()+1));
						
						  
						  invList.add(boqHeader);
						if (cellValue.trim().equalsIgnoreCase("Details")) {
							String srPosition = cell1.getAddress().formatAsString();
							column = Character.toString(srPosition.charAt(0));
							columnIndex = cell1.getColumnIndex();
							rowIndex = cell1.getRowIndex();
							
							for(int i=1;i<100;i++)
							{
								Row rowI = sheet1.getRow(rowIndex + i);
								Cell celli = null;
								if (rowI != null)
									celli = rowI.getCell(columnIndex);
								else
									break;
								
								String cellValue1 = dataFormatter.formatCellValue(celli);
								
								if (!(cellValue1.equals(""))) {
									System.out.println("Cell value is : " + cellValue1);
									System.out.println("inventoryDao is : " + inventoryDao);
									invList.add(mapToInventory(cellValue1));
								}
							}
							
						}
						
						
					}
				}
			/*}*/
		}
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
	finally
	{
		workbook.close();
	}
		return invList;
	}

	public Inventory mapToInventory(String rawString) {
		String[] values = rawString.split(",");

		InventorySpec invSprc = new InventorySpec(values[0], values[1], values[2], values[3], values[4],
				values[5], values[6]);

		Inventory inv = new Inventory();

		inv.setInventorySpec(invSprc);
		inv.setQuantity(Integer.valueOf(values[7]));

		return inv;
	}

	public Map readColumn(Workbook workbook, int columnIndex, int rowIndex) {
		Map<String, String> columnData = new HashMap<>();
		Iterator<Sheet> sheet = workbook.sheetIterator();
		while (sheet.hasNext()) {
			Sheet sheet1 = sheet.next();
			System.out.println("Working on sheet : " + sheet1.getSheetName());
			if (sheet1.getSheetName().equalsIgnoreCase("150NB Header")) 
			{
				Iterator<Row> row = sheet1.rowIterator();
				while (row.hasNext()) {
					Iterator<Cell> cell = row.next().cellIterator();
					while (cell.hasNext()) {
						Cell cell1 = cell.next();
						String cellValue = dataFormatter.formatCellValue(cell1);

						if (cell1.getColumnIndex() == columnIndex && cell1.getRowIndex() > rowIndex
								&& !cellValue.equals("")) {
							System.out.println(cell1.getColumnIndex());
							System.out.println(cell1.getRowIndex());
							System.out.println("Value is  : " + cellValue);
						}
					}
				}
			}

		}

		return columnData;
	}

	public String readValueInColumn(Row row, int columnIndex)
	{
	    String cellVal = row.getCell(columnIndex).getStringCellValue();	    
	    return cellVal;
	}
}
