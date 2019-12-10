package com.invmgmt.excel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.ManagedBean;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.util.ResourceUtils;

import com.invmgmt.entity.BOQDetails;
import com.invmgmt.entity.BOQHeader;
import com.invmgmt.entity.BOQLineData;

@ManagedBean
public class ExcelWriter {

	public void writeExcel(ArrayList<BOQDetails> boqInventoryDetails) throws IOException {

		Workbook workbook = null;
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(ResourceUtils.getFile("classpath:BOQ_Template.xls"));

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

	public byte[] writeExcel(ArrayList<BOQLineData> boqLineDataDetails, String[] size, String[] quantity,
			String[] supplyRate, String[] erectionRate, String[] sypplyAmount, String[] erectionAmount,
			String boqNameRevisionStr, BOQHeader header) throws IOException {

		ArrayList<BOQLineData> processedInventory = new ArrayList<BOQLineData>();
		Workbook workBook = null;
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(ResourceUtils.getFile("classpath:BOQ_Template.xls"));
			workBook = WorkbookFactory.create(inputStream);
		} catch (EncryptedDocumentException | InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String sheetDetails = header.getSheetDetails();

		String[] sheets = sheetDetails.split(",");

		Map<String, String> sheetDetailsMap = new HashMap<String, String>();

		for (int i = 0; i < sheets.length; i++) {
			sheetDetailsMap.put(sheets[i], sheets[++i]);
		}

		Set<String> sheetNames = sheetDetailsMap.keySet();

		Iterator<String> sheetIterator = sheetNames.iterator();

		ArrayList<String> sheetNameList = new ArrayList<String>();
		while (sheetIterator.hasNext()) {
			String sheetNameStr = sheetIterator.next();
			sheetNameList.add(sheetNameStr);
			Sheet newSheet = workBook.createSheet(sheetNameStr);

			// copy "sheet1" as is

			/*
			 * Loop in the row data list, add each row data into the new sheet.
			 */
			/* Get sheet by name. */
			Sheet copySheet = workBook.getSheet("sheet1");

			int fRowNum = copySheet.getFirstRowNum();
			int lRowNum = copySheet.getLastRowNum();

			/*
			 * First row is excel file header, so read data from row next to it.
			 */
			for (int i = fRowNum + 1; i < lRowNum + 1; i++) {
				/* Only get desired row data. */
				if (i >= 0 && i <= 80) {
					Row row = copySheet.getRow(i);
					Row newRow = newSheet.createRow(i);

					int fCellNum = row.getFirstCellNum();
					int lCellNum = row.getLastCellNum();

					System.out.println("fCellNum is : "+fCellNum);
					System.out.println("lCellNum is : "+lCellNum);
					/* Loop in cells, add each cell value to the list. */
					for (int j = fCellNum; j < 8; j++) 
					{
						copyCell(row.getCell(j), newRow.createCell(j));
					}

				}
			}

		}

		int startIndex = 0;
		int lastIndex = 0;

		for (int s = 1; s < sheetNames.size(); s++) 
		{
		
			Sheet sheet = workBook.getSheetAt(s);
			int inventoryCount = Integer.parseInt(sheetDetailsMap.get(sheetNameList.get(s)));

			lastIndex = inventoryCount - 1;

			System.out.println("lastIndex is : " + lastIndex);

			// Sort the list
			if (header != null) {
				sheet.getRow(2).getCell(1).setCellValue(header.getClient());
				sheet.getRow(2).getCell(4).setCellValue(header.getUtility());

				sheet.getRow(3).getCell(1).setCellValue(header.getSite());
				sheet.getRow(3).getCell(4).setCellValue(header.getPressure());

				sheet.getRow(4).getCell(1).setCellValue(header.getProject());
				sheet.getRow(4).getCell(4).setCellValue(header.getTemp());

				sheet.getRow(5).getCell(1).setCellValue(header.getdName());
				sheet.getRow(5).getCell(4).setCellValue(header.getdNo());
			}

			int index = 0;
			int nextRow = 9;
			int i = 1;

			for (int invIndx = startIndex; invIndx < lastIndex; invIndx++) 
			{
				BOQLineData inventory = boqLineDataDetails.get(invIndx);

				int presentIndex = processedInventory.indexOf(inventory);

				System.out.println("presentIndex is : " + presentIndex);

				if (presentIndex != -1) {
					// int row = presentIndex * 7 + 9 + i;

					int row = nextRow;

					Cell cellToUpdate0 = sheet.getRow(row).getCell(2);
					cellToUpdate0.setCellValue(size[index]);

					Cell cellToUpdate5 = sheet.getRow(row).getCell(3);
					cellToUpdate5.setCellValue(quantity[index]);

					Cell cellToUpdate6 = sheet.getRow(row).getCell(4);

					if (supplyRate.length > 0)
						cellToUpdate6.setCellValue(supplyRate[index]);

					Cell cellToUpdate8 = sheet.getRow(row).getCell(5);

					if (erectionRate.length > 0)
						cellToUpdate8.setCellValue(erectionRate[index]);

					Cell cellToUpdate9 = sheet.getRow(row).getCell(6);

					if (sypplyAmount.length > 0)
						cellToUpdate9.setCellValue(sypplyAmount[index]);

					Cell cellToUpdate10 = sheet.getRow(row).getCell(7);

					if (erectionAmount.length > 0)
						cellToUpdate10.setCellValue(erectionAmount[index]);
					i++;
				} else {
					processedInventory.add(inventory);

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

					Cell cellToUpdate7 = sheet.getRow(++nextRow).getCell(1);
					cellToUpdate7.setCellValue(inventory.getMakesLine());
					nextRow = nextRow + 2 + i;
				}

				index++;
				startIndex = startIndex + inventoryCount;

				System.out.println("Next start index is : " + startIndex);
			}
		}
		
		workBook.removeSheetAt(0);
		
		inputStream.close();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			workBook.write(bos);
		} finally {
			bos.close();
		}
		byte[] bytes = bos.toByteArray();

		// Closing the workbook
		workBook.close();

		return bytes;
	}

	/*
	 * Get the data in excel file. Return: 2D String list contains specified row
	 * data. excelFilePath : The exist file path need to copy. excelSheetName :
	 * Which sheet to copy. startRow : Start row number. endRow : End row
	 * number.
	 */
	private List<List<String>> getExcelData(File excelFilePath, String excelSheetName, int startRow, int endRow) {
		List<List<String>> ret = new ArrayList();

		try {
			/* Open the file input stream. */
			FileInputStream fis = new FileInputStream(excelFilePath);

			/* Get workbook. */
			Workbook excelWookBook = new HSSFWorkbook(fis);

			/* Get sheet by name. */
			Sheet copySheet = excelWookBook.getSheet(excelSheetName);

			int fRowNum = copySheet.getFirstRowNum();
			int lRowNum = copySheet.getLastRowNum();

			/*
			 * First row is excel file header, so read data from row next to it.
			 */
			for (int i = fRowNum + 1; i < lRowNum + 1; i++) {
				/* Only get desired row data. */
				if (i >= startRow && i <= endRow) {
					Row row = copySheet.getRow(i);

					int fCellNum = row.getFirstCellNum();
					int lCellNum = row.getLastCellNum();

					/* Loop in cells, add each cell value to the list. */
					List<String> rowDataList = new ArrayList<String>();
					for (int j = fCellNum; j < lCellNum; j++) {
						String cValue = row.getCell(j).getStringCellValue();
						rowDataList.add(cValue);
					}

					ret.add(rowDataList);
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return ret;
	}

	private static void copyCell(Cell oldCell, Cell newCell) 
	{
		
		System.out.println("newCell is : " + newCell);
		System.out.println("oldCell is : " + oldCell);
		
		newCell.setCellStyle(oldCell.getCellStyle());

		switch (oldCell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			newCell.setCellValue(oldCell.getRichStringCellValue());
			break;
		case Cell.CELL_TYPE_NUMERIC:
			newCell.setCellValue(oldCell.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_BLANK:
			newCell.setCellType(Cell.CELL_TYPE_BLANK);
			break;
		case Cell.CELL_TYPE_FORMULA:
			newCell.setCellFormula(oldCell.getCellFormula());
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			newCell.setCellValue(oldCell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_ERROR:
			newCell.setCellErrorValue(oldCell.getErrorCellValue());
			break;
		default:
			break;
		}
	}
}
