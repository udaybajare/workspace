package com.invmgmt.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.ManagedBean;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.invmgmt.entity.BOQInventoryDetails;
import com.invmgmt.entity.Inventory;
import com.invmgmt.entity.InventorySpec;

@ManagedBean
public class ExcelWriter {


	public static void writeExcel(ArrayList<BOQInventoryDetails> boqInventoryDetails) throws IOException {

		Workbook workbook = null;
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File("C:\\Users\\Uday\\Desktop\\Projects\\Humdule\\BOQ_Template.xls"));
			workbook = WorkbookFactory.create(inputStream);
		} catch (EncryptedDocumentException | InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Sheet sheet = workbook.getSheetAt(0);

		int nextRow = 9;
		for (BOQInventoryDetails inventory : boqInventoryDetails)
		{
			
				Cell cellToUpdate = sheet.getRow(nextRow).getCell(1);
				cellToUpdate.setCellValue("Standard/Type : "+inventory.getStandardType());
				
				Cell cellToUpdate0 = sheet.getRow(nextRow).getCell(2);
				cellToUpdate0.setCellValue(inventory.getSize());

				Cell cellToUpdate5 = sheet.getRow(nextRow).getCell(3);
				cellToUpdate5.setCellValue(inventory.getNetQuantity());
				
				Cell cellToUpdate6 = sheet.getRow(nextRow).getCell(4);
				cellToUpdate6.setCellValue(inventory.getSupplyRate());
				
				Cell cellToUpdate1 = sheet.getRow(++nextRow).getCell(1);
				cellToUpdate1.setCellValue("Grade/Class : "+inventory.getGrade());
				
				Cell cellToUpdate2 = sheet.getRow(++nextRow).getCell(1);
				cellToUpdate2.setCellValue("Schedule : "+inventory.getSchedule());
		
				Cell cellToUpdate3 = sheet.getRow(++nextRow).getCell(1);
				cellToUpdate3.setCellValue("Material Spec : "+inventory.getMaterialSpec());
				
				Cell cellToUpdate4 = sheet.getRow(++nextRow).getCell(1);
				cellToUpdate4.setCellValue("Ends : "+inventory.getEnds());
				
				nextRow = nextRow+2;
		}
		
		
		
		/*Font headerFont = workbook.createFont();
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(headerFont);
		*/
		
		inputStream.close();
		
		FileOutputStream fileOut = new FileOutputStream("C:\\Users\\Uday\\Desktop\\Projects\\Humdule\\BOQs\\BOQ_For_ABC.xls");
		workbook.write(fileOut);
		fileOut.close();

		// Closing the workbook
		workbook.close();
	}

	/*public static void main(String[] args) {
		
		try {
			//writeExcel();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}
