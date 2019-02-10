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

import com.invmgmt.entity.BOQDetails;
import com.invmgmt.entity.BOQLineData;

@ManagedBean
public class ExcelWriter {

    public void writeExcel(ArrayList<BOQDetails> boqInventoryDetails) throws IOException {

	Workbook workbook = null;
	FileInputStream inputStream = null;
	try {
	    inputStream = new FileInputStream(
		    new File("C:\\Users\\Uday\\Desktop\\Projects\\Humdule\\BOQ_Template.xls"));
	    workbook = WorkbookFactory.create(inputStream);
	} catch (EncryptedDocumentException | InvalidFormatException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	Sheet sheet = workbook.getSheetAt(0);

	int nextRow = 9;
	for (BOQDetails inventory : boqInventoryDetails) {
	    /*
	     * Cell cellToUpdate = sheet.getRow(nextRow).getCell(1);
	     * cellToUpdate.setCellValue("Standard/Type : "+inventory.
	     * getStandardType());
	     * 
	     * Cell cellToUpdate0 = sheet.getRow(nextRow).getCell(2);
	     * cellToUpdate0.setCellValue(inventory.getSize());
	     * 
	     * Cell cellToUpdate5 = sheet.getRow(nextRow).getCell(3);
	     * cellToUpdate5.setCellValue(inventory.getQuantity());
	     * 
	     * Cell cellToUpdate6 = sheet.getRow(nextRow).getCell(4);
	     * cellToUpdate6.setCellValue(inventory.getSupplyRate());
	     * 
	     * Cell cellToUpdate1 = sheet.getRow(++nextRow).getCell(1);
	     * cellToUpdate1.setCellValue("Grade/Class : "+inventory.getGrade()+
	     * ","+inventory.getSchedule());
	     * 
	     * Cell cellToUpdate3 = sheet.getRow(++nextRow).getCell(1);
	     * cellToUpdate3.setCellValue("Material Spec : "+inventory.
	     * getMaterialSpec());
	     * 
	     * Cell cellToUpdate4 = sheet.getRow(++nextRow).getCell(1);
	     * cellToUpdate4.setCellValue("Ends : "+inventory.getEnds());
	     * 
	     * nextRow = nextRow+2;
	     */}

	/*
	 * Font headerFont = workbook.createFont();
	 * headerFont.setColor(IndexedColors.BLACK.getIndex());
	 * 
	 * CellStyle cellStyle = workbook.createCellStyle();
	 * cellStyle.setFont(headerFont);
	 */

	inputStream.close();

	FileOutputStream fileOut = new FileOutputStream(
		"C:\\Users\\Uday\\Desktop\\Projects\\Humdule\\BOQs\\BOQ_For_ABC.xls");
	workbook.write(fileOut);
	fileOut.close();

	// Closing the workbook
	workbook.close();
    }

    public void writeExcel(ArrayList<BOQLineData> boqLineDataDetails, String[] size, String[] quantity,
	    String[] supplyRate, String[] erectionRate, String[] sypplyAmount, String[] erectionAmount,
	    String boqNameRevisionStr) throws IOException {

	Workbook workbook = null;
	FileInputStream inputStream = null;
	try {
	    inputStream = new FileInputStream(
		    new File("C:\\Users\\Uday\\Desktop\\Projects\\Humdule\\BOQ_Template.xls"));
	    workbook = WorkbookFactory.create(inputStream);
	} catch (EncryptedDocumentException | InvalidFormatException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	Sheet sheet = workbook.getSheetAt(0);

	int index = 0;
	int nextRow = 9;
	for (BOQLineData inventory : boqLineDataDetails) {
	    Cell cellToUpdate = sheet.getRow(nextRow).getCell(1);
	    cellToUpdate.setCellValue(inventory.getStdLine());

	    Cell cellToUpdate0 = sheet.getRow(nextRow).getCell(2);
	    cellToUpdate0.setCellValue(size[index]);

	    Cell cellToUpdate5 = sheet.getRow(nextRow).getCell(3);
	    cellToUpdate5.setCellValue(quantity[index]);

	    Cell cellToUpdate6 = sheet.getRow(nextRow).getCell(4);

	    if (supplyRate.length > 0)
		cellToUpdate6.setCellValue(supplyRate[index]);

	    Cell cellToUpdate8 = sheet.getRow(nextRow).getCell(5);

	    if (erectionRate.length > 0)
		cellToUpdate8.setCellValue(erectionRate[index]);

	    Cell cellToUpdate9 = sheet.getRow(nextRow).getCell(6);

	    if (sypplyAmount.length > 0)
		cellToUpdate9.setCellValue(sypplyAmount[index]);

	    Cell cellToUpdate10 = sheet.getRow(nextRow).getCell(7);

	    if (erectionAmount.length > 0)
		cellToUpdate10.setCellValue(erectionAmount[index]);

	    Cell cellToUpdate3 = sheet.getRow(++nextRow).getCell(1);
	    cellToUpdate3.setCellValue(inventory.getSpecLine());

	    Cell cellToUpdate1 = sheet.getRow(++nextRow).getCell(1);
	    cellToUpdate1.setCellValue(inventory.getGrdLine());

	    Cell cellToUpdate4 = sheet.getRow(++nextRow).getCell(1);
	    cellToUpdate4.setCellValue(inventory.getEndsLine());

	    System.out.println("Line 134 is " + sheet.getRow(++nextRow));

	    Cell cellToUpdate7 = sheet.getRow(++nextRow).getCell(1);
	    cellToUpdate7.setCellValue(inventory.getMakesLine());

	    nextRow = nextRow + 2;
	    index++;
	}

	inputStream.close();

	FileOutputStream fileOut = new FileOutputStream(
		"C:\\Users\\Uday\\Desktop\\Projects\\Humdule\\BOQs\\"+boqNameRevisionStr+".xls");
	workbook.write(fileOut);
	fileOut.close();

	// Closing the workbook
	workbook.close();
    }
}
