package com.invmgmt.excel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
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

import com.invmgmt.entity.BOQHeader;
import com.invmgmt.entity.BOQLineData;

@ManagedBean
public class ExcelWriter {

	public byte[] writeExcel(ArrayList<BOQLineData> boqLineDataDetails, String[] size, String[] quantity,
			String[] supplyRate, String[] erectionRate, String[] sypplyAmount, String[] erectionAmount,
			String boqNameRevisionStr, BOQHeader header) throws IOException {

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

		if (null == sheetDetails || "".equals(sheetDetails)) {
			sheetDetails = "sheetDetails," + boqLineDataDetails.size();

			// This is for Inquiry generation. While generating
			// Inquiry we are creating only
			// one sheet which will hold all the elements
			// elements.
		}

		String[] sheets = sheetDetails.split(",");

		Map<String, String> sheetDetailsMap = new LinkedHashMap<String, String>();

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

					System.out.println("fCellNum is : " + fCellNum);
					System.out.println("lCellNum is : " + lCellNum);
					/* Loop in cells, add each cell value to the list. */
					for (int j = fCellNum; j < 8; j++) {
						copyCell(row.getCell(j), newRow.createCell(j));
					}

				}
			}

		}

		int startIndex = 0;
		int lastIndex = 0;

		int index = 0;

		for (int s = 1; s <= sheetNames.size(); s++) {
			ArrayList<BOQLineData> processedInventory = new ArrayList<BOQLineData>();
			Sheet sheet = workBook.getSheetAt(s);
			int inventoryCount = Integer.parseInt(sheetDetailsMap.get(sheetNameList.get(s - 1)));

			// Add blank rows to excel sheet
			for (int i = 79; i < 79 + (boqLineDataDetails.size() * 9); i++) {
				sheet.createRow(i);
				System.out.println("creating row : " + i);
			}

			lastIndex = lastIndex + inventoryCount;

			System.out.println("lastIndex is : " + lastIndex);

			// Sort the list
			if (header != null) {
				sheet.getRow(2).getCell(1).setCellValue(header.getClient());
				sheet.getRow(2).getCell(3).setCellValue(header.getUtility());

				sheet.getRow(3).getCell(1).setCellValue(header.getSite());
				sheet.getRow(3).getCell(3).setCellValue(header.getPressure());

				sheet.getRow(4).getCell(1).setCellValue(header.getProject());
				sheet.getRow(4).getCell(3).setCellValue(header.getTemp());

				sheet.getRow(5).getCell(1).setCellValue(header.getdName());
				sheet.getRow(5).getCell(3).setCellValue(header.getdNo());
			}

			int nextRow = 9;
			int i = 1;
			int pushBy = 0;

			for (int invIndx = startIndex; invIndx < lastIndex; invIndx++) {
				BOQLineData inventory = boqLineDataDetails.get(invIndx);

				int presentIndex = processedInventory.indexOf(inventory);
				// Reset pushBy to 0
				pushBy = 0;
				System.out.println("presentIndex is : " + presentIndex);

				if (presentIndex != -1) {
					// int row = presentIndex * 7 + 9 + i;

					int row = nextRow - 6 + index;

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

				} else {
					processedInventory.add(inventory);

					String invCategory = "null" != inventory.getCategory() ? inventory.getCategory() : "";
					Cell cellToUpdateInv = sheet.getRow(nextRow - 1).getCell(1);
					cellToUpdateInv.setCellValue(inventory.getInventoryName() + " " + invCategory);

					Cell cellToUpdate = sheet.getRow(nextRow).getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					cellToUpdate.setCellValue(inventory.getStdLine());

					Cell cellToUpdate0 = sheet.getRow(nextRow).getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					cellToUpdate0.setCellValue(size[index]);

					Cell cellToUpdate5 = sheet.getRow(nextRow).getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					cellToUpdate5.setCellValue(quantity[index]);

					Cell cellToUpdate6 = sheet.getRow(nextRow).getCell(4, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

					if (supplyRate.length > 0)
						cellToUpdate6.setCellValue(supplyRate[index]);

					Cell cellToUpdate8 = sheet.getRow(nextRow).getCell(5, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

					if (erectionRate.length > 0)
						cellToUpdate8.setCellValue(erectionRate[index]);

					Cell cellToUpdate9 = sheet.getRow(nextRow).getCell(6, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

					if (sypplyAmount.length > 0)
						cellToUpdate9.setCellValue(sypplyAmount[index]);

					Cell cellToUpdate10 = sheet.getRow(nextRow).getCell(7, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

					if (erectionAmount.length > 0)
						cellToUpdate10.setCellValue(erectionAmount[index]);

					if (null != inventory.getSpecLine() && !("".equals(inventory.getSpecLine()))) {
						Cell cellToUpdate3 = sheet.getRow(++nextRow).getCell(1,
								Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						cellToUpdate3.setCellValue(inventory.getSpecLine());
					} else {
						pushBy++;
					}

					if (null != inventory.getGrdLine() && !("".equals(inventory.getGrdLine()))) {
						Cell cellToUpdate1 = sheet.getRow(++nextRow).getCell(1,
								Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						cellToUpdate1.setCellValue(inventory.getGrdLine());
					} else {
						pushBy++;
					}

					if (null != inventory.getEndsLine() && !("".equals(inventory.getEndsLine()))) {
						Cell cellToUpdate4 = sheet.getRow(++nextRow).getCell(1,
								Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						cellToUpdate4.setCellValue(inventory.getEndsLine());
					} else {
						pushBy++;
					}

					if (null != inventory.getMakesLine() && !("".equals(inventory.getMakesLine()))) {
						Cell cellToUpdate7 = sheet.getRow(++nextRow).getCell(1,
								Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						cellToUpdate7.setCellValue(inventory.getMakesLine());
					} else {
						pushBy++;
					}

					nextRow = nextRow + pushBy + 2 + i;
				}

				index++;

				System.out.println("Next Row is : " + nextRow);
				System.out.println("Next start index is : " + startIndex);
			}

			startIndex = startIndex + inventoryCount;

			for (int j = 0; j < 8; j++) {
				sheet.autoSizeColumn(j);
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

	public void generatePO() {

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

	private static void copyCell(Cell oldCell, Cell newCell) {

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
