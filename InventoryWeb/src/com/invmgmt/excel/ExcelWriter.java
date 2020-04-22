package com.invmgmt.excel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.ManagedBean;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.util.ResourceUtils;

import com.invmgmt.entity.BOQHeader;
import com.invmgmt.entity.BOQLineData;

@ManagedBean
public class ExcelWriter {

	public byte[] writeExcel(ArrayList<BOQLineData> boqLineDataDetails, String[] size, String[] quantity,
			String[] supplyRate, String[] erectionRate, String[] supplyAmount, String[] erectionAmount,
			String boqNameRevisionStr, BOQHeader header, boolean isOffer) throws IOException {

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
			for (int i = fRowNum; i < lRowNum + 1; i++) {
				/* Only get desired row data. */
				if (i >= 0 && i <= 80) {
					Row row = copySheet.getRow(i);
					Row newRow = newSheet.createRow(i);

					int fCellNum = row.getFirstCellNum();
					int lCellNum = row.getLastCellNum();

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
		
		

		CellStyle whiteBackGround = workBook.createCellStyle();

		whiteBackGround.setFillBackgroundColor(IndexedColors.WHITE.index);
		whiteBackGround.setFillForegroundColor(IndexedColors.WHITE.index);
		whiteBackGround.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		whiteBackGround.setBorderRight(BorderStyle.MEDIUM);
		whiteBackGround.setRightBorderColor(IndexedColors.BLACK.index);

		/*
		 * CellStyle whiteBackGroundBaseLine = workBook.createCellStyle();
		 * 
		 * whiteBackGroundBaseLine.setFillBackgroundColor(IndexedColors.WHITE.
		 * index);
		 * whiteBackGroundBaseLine.setFillForegroundColor(IndexedColors.WHITE.
		 * index); whiteBackGroundBaseLine.setFillPattern(FillPatternType.
		 * SOLID_FOREGROUND);
		 * 
		 * whiteBackGroundBaseLine.setBorderBottom(BorderStyle.MEDIUM);
		 * whiteBackGroundBaseLine.setBottomBorderColor(IndexedColors.BLACK.
		 * index);
		 */

		CellStyle whiteBackGroundTextCenter = workBook.createCellStyle();

		whiteBackGroundTextCenter.setFillBackgroundColor(IndexedColors.WHITE.index);
		whiteBackGroundTextCenter.setFillForegroundColor(IndexedColors.WHITE.index);
		whiteBackGroundTextCenter.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		whiteBackGroundTextCenter.setBorderRight(BorderStyle.MEDIUM);
		whiteBackGroundTextCenter.setRightBorderColor(IndexedColors.BLACK.index);
		whiteBackGroundTextCenter.setAlignment(HorizontalAlignment.CENTER);
		whiteBackGroundTextCenter.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle whiteBackGroundBaseLineRight = workBook.createCellStyle();
		whiteBackGroundBaseLineRight.setFillBackgroundColor(IndexedColors.WHITE.index);
		whiteBackGroundBaseLineRight.setFillForegroundColor(IndexedColors.WHITE.index);
		whiteBackGroundBaseLineRight.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		whiteBackGroundBaseLineRight.setBorderBottom(BorderStyle.MEDIUM);
		whiteBackGroundBaseLineRight.setBottomBorderColor(IndexedColors.BLACK.index);

		whiteBackGroundBaseLineRight.setBorderRight(BorderStyle.MEDIUM);
		whiteBackGroundBaseLineRight.setRightBorderColor(IndexedColors.BLACK.index);

		for (int s = 1; s <= sheetNames.size(); s++) {
			ArrayList<BOQLineData> processedInventory = new ArrayList<BOQLineData>();
			Sheet sheet = workBook.getSheetAt(s + 2);
			int inventoryCount = Integer.parseInt(sheetDetailsMap.get(sheetNameList.get(s - 1)));

			sheet.getRow(0).getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
					.setCellValue("Hamdule Industries Pvt Ltd");

			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 4));
			// sheet.addMergedRegion(new CellRangeAddress(0,1,5,7));

			sheet.addMergedRegion(new CellRangeAddress(2, 3, 0, 0));
			sheet.addMergedRegion(new CellRangeAddress(2, 3, 2, 4));

			sheet.addMergedRegion(new CellRangeAddress(2, 2, 5, 7));
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 5, 7));

			sheet.addMergedRegion(new CellRangeAddress(4, 4, 2, 3));
			sheet.addMergedRegion(new CellRangeAddress(4, 4, 4, 7));
			sheet.addMergedRegion(new CellRangeAddress(5, 5, 2, 3));
			sheet.addMergedRegion(new CellRangeAddress(5, 5, 4, 7));
			sheet.addMergedRegion(new CellRangeAddress(6, 6, 2, 3));

			lastIndex = lastIndex + inventoryCount;

			System.out.println("lastIndex is : " + lastIndex);

			// Sort the list
			if (header != null) {
				sheet.getRow(2).getCell(5).setCellValue(header.getClient());
				sheet.getRow(6).getCell(3).setCellValue(header.getUtility());

				sheet.getRow(3).getCell(5).setCellValue(header.getSite());
				sheet.getRow(6).getCell(4).setCellValue(header.getPressure());

				sheet.getRow(4).getCell(1).setCellValue(header.getProject());
				sheet.getRow(6).getCell(6).setCellValue(header.getTemp());

				sheet.getRow(5).getCell(1).setCellValue(header.getdName());
				sheet.getRow(5).getCell(4).setCellValue(header.getdNo());
			}

			int nextRow = 10;
			int i = 1;
			int pushBy = 0;

			int invRepeateCount = 0;
			int rowB4Push = 0;

			double supplyAmountTotal = 0;
			double erectionAmountTotal = 0;

			for (int invIndx = startIndex; invIndx < lastIndex; invIndx++) {
				BOQLineData inventory = boqLineDataDetails.get(invIndx);

				int presentIndex = processedInventory.indexOf(inventory);
				// Reset pushBy to 0
				pushBy = 0;
				boolean shouldEnd = true;
				System.out.println("presentIndex is : " + presentIndex);

				if (presentIndex != -1) {
					// int row = presentIndex * 7 + 9 + i;

					/*
					 * invRepeateCount ++; isNextInventory = false;
					 */

					shouldEnd = false;
					int row = nextRow - 8 + invRepeateCount;

					if (sheet.getRow(row) == null) {
						sheet.createRow(row);
					}

					sheet.getRow(row).getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
							.setCellStyle(whiteBackGround);
					sheet.getRow(row).getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
							.setCellStyle(whiteBackGround);

					Cell cellToUpdate0 = sheet.getRow(row).getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					cellToUpdate0.setCellValue(size[index]);
					cellToUpdate0.setCellStyle(whiteBackGroundTextCenter);

					Cell cellToUpdate5 = sheet.getRow(row).getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					cellToUpdate5.setCellValue(quantity[index]);
					cellToUpdate5.setCellStyle(whiteBackGroundTextCenter);

					Cell cellToUpdate6 = sheet.getRow(row).getCell(4, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

					if (supplyRate.length > 0) {
						cellToUpdate6.setCellValue(supplyRate[index]);

					}
					cellToUpdate6.setCellStyle(whiteBackGroundTextCenter);

					Cell cellToUpdate9 = sheet.getRow(row).getCell(5, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

					if (supplyAmount.length > 0) {
						cellToUpdate9.setCellValue(supplyAmount[index]);

						if (!(supplyAmount[index].isEmpty())) {
							supplyAmountTotal = supplyAmountTotal + Double.parseDouble(supplyAmount[index]);
						}
					}
					cellToUpdate9.setCellStyle(whiteBackGroundTextCenter);

					Cell cellToUpdate8 = sheet.getRow(row).getCell(6, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					if (erectionRate.length > 0) {
						cellToUpdate8.setCellValue(erectionRate[index]);

					}
					cellToUpdate8.setCellStyle(whiteBackGroundTextCenter);

					Cell cellToUpdate10 = sheet.getRow(row).getCell(7, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

					if (erectionAmount.length > 0) {
						cellToUpdate10.setCellValue(erectionAmount[index]);
						if (!(erectionAmount[index].isEmpty())) {
							erectionAmountTotal = erectionAmountTotal + Double.parseDouble(erectionAmount[index]);
						}

					}
					cellToUpdate10.setCellStyle(whiteBackGroundTextCenter);

					invRepeateCount++;

				} else {

					rowB4Push = 0;
					processedInventory.add(inventory);

					/*
					 * if(invRepeateCount>6) { nextRow = nextRow +
					 * (inventoryCount-6); }
					 */
					sheet.createRow(nextRow - 2);
					sheet.createRow(nextRow - 1);
					sheet.createRow(nextRow);

					/*
					 * if (invIndx != 0) { sheet.createRow(nextRow - 2); for
					 * (int n = 0; n < 8; n++) { Cell cell0 =
					 * sheet.getRow(nextRow - 2).getCell(n,
					 * Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					 * cell0.setCellStyle(whiteBackGround); } }
					 */

					for (int n = 0; n < 8; n++) {
						Cell cell0 = sheet.getRow(nextRow - 2).getCell(n, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						cell0.setCellStyle(whiteBackGround);
					}
					
					for (int n = 0; n < 8; n++) {
						Cell cell0 = sheet.getRow(nextRow - 1).getCell(n, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						cell0.setCellStyle(whiteBackGround);
					}

					for (int n = 0; n < 8; n++) {
						Cell cell0 = sheet.getRow(nextRow).getCell(n, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						cell0.setCellStyle(whiteBackGround);
					}

					Cell serialNumberCell = sheet.getRow(nextRow - 1).getCell(0,
							Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					serialNumberCell.setCellValue(index + 1);

					String invCategory = null != inventory.getCategory() ? inventory.getCategory() : "";
					Cell cellToUpdateInv = sheet.getRow(nextRow - 1).getCell(1,
							Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					cellToUpdateInv.setCellValue(inventory.getInventoryName() + " " + invCategory);
					cellToUpdateInv.setCellStyle(whiteBackGround);

					sheet.getRow(nextRow).getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
							.setCellStyle(whiteBackGround);

					Cell cellToUpdate = sheet.getRow(nextRow).getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					cellToUpdate.setCellValue(inventory.getStdLine());
					cellToUpdate.setCellStyle(whiteBackGround);

					Cell cellToUpdate0 = sheet.getRow(nextRow).getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					cellToUpdate0.setCellValue(size[index]);
					cellToUpdate0.setCellStyle(whiteBackGroundTextCenter);

					Cell cellToUpdate5 = sheet.getRow(nextRow).getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					cellToUpdate5.setCellValue(quantity[index]);
					cellToUpdate5.setCellStyle(whiteBackGroundTextCenter);

					Cell cellToUpdate6 = sheet.getRow(nextRow).getCell(4, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

					if (supplyRate.length > 0) {
						cellToUpdate6.setCellValue(supplyRate[index]);
						if (!(supplyRate[index].isEmpty())) {
							supplyAmountTotal = supplyAmountTotal + Double.parseDouble(supplyAmount[index]);
						}

					}
					cellToUpdate6.setCellStyle(whiteBackGroundTextCenter);

					Cell cellToUpdate9 = sheet.getRow(nextRow).getCell(5, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

					if (supplyAmount.length > 0)
						cellToUpdate9.setCellValue(supplyAmount[index]);
					cellToUpdate9.setCellStyle(whiteBackGroundTextCenter);

					Cell cellToUpdate8 = sheet.getRow(nextRow).getCell(6, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

					if (erectionRate.length > 0) {
						cellToUpdate8.setCellValue(erectionRate[index]);
						if (!(erectionRate[index].isEmpty())) {
							erectionAmountTotal = erectionAmountTotal + Double.parseDouble(erectionAmount[index]);
						}
					}
					cellToUpdate8.setCellStyle(whiteBackGroundTextCenter);

					Cell cellToUpdate10 = sheet.getRow(nextRow).getCell(7, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

					if (erectionAmount.length > 0)
						cellToUpdate10.setCellValue(erectionAmount[index]);
					cellToUpdate10.setCellStyle(whiteBackGroundTextCenter);

					if (null != inventory.getSpecLine() && !("".equals(inventory.getSpecLine()))) {
						sheet.getRow(nextRow).getCell(0).setCellStyle(whiteBackGround);
						sheet.createRow(++nextRow);
						Cell cellToUpdate3 = sheet.getRow(nextRow).getCell(1,
								Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						cellToUpdate3.setCellValue(inventory.getSpecLine());
						cellToUpdate3.setCellStyle(whiteBackGround);

						for (int l = 0; l < 8; l++) {
							sheet.getRow(nextRow).getCell(l, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
									.setCellStyle(whiteBackGround);
						}
					} else {
						pushBy++;
					}

					if (null != inventory.getGrdLine() && !("".equals(inventory.getGrdLine()))) {
						sheet.getRow(nextRow).getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
								.setCellStyle(whiteBackGround);
						sheet.createRow(++nextRow);
						Cell cellToUpdate1 = sheet.getRow(nextRow).getCell(1,
								Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						cellToUpdate1.setCellValue(inventory.getGrdLine());
						cellToUpdate1.setCellStyle(whiteBackGround);

						for (int l = 0; l < 8; l++) {
							sheet.getRow(nextRow).getCell(l, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
									.setCellStyle(whiteBackGround);
						}
					} else {
						pushBy++;
					}

					if (null != inventory.getEndsLine() && !("".equals(inventory.getEndsLine()))) {
						sheet.getRow(nextRow).getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
								.setCellStyle(whiteBackGround);
						sheet.createRow(++nextRow);
						Cell cellToUpdate4 = sheet.getRow(nextRow).getCell(1,
								Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						cellToUpdate4.setCellValue(inventory.getEndsLine());
						cellToUpdate4.setCellStyle(whiteBackGround);

						for (int l = 0; l < 8; l++) {
							sheet.getRow(nextRow).getCell(l, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
									.setCellStyle(whiteBackGround);
						}
					} else {
						pushBy++;
					}

					if (null != inventory.getMakesLine() && !("".equals(inventory.getMakesLine()))) {
						sheet.getRow(nextRow).getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
								.setCellStyle(whiteBackGround);
						sheet.createRow(++nextRow);
						Cell cellToUpdate7 = sheet.getRow(nextRow).getCell(1,
								Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						cellToUpdate7.setCellValue(inventory.getMakesLine());
						cellToUpdate7.setCellStyle(whiteBackGround);

						for (int l = 0; l < 8; l++) {
							sheet.getRow(nextRow).getCell(l, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
									.setCellStyle(whiteBackGround);
						}
					} else {
						pushBy++;
					}

					rowB4Push = nextRow;
					nextRow = nextRow + pushBy + 4 + i;
				}

				if (shouldEnd || (invIndx == (lastIndex - 1))) {
					int count = rowB4Push + pushBy + 3;
					int start = rowB4Push + 1;

					if (invRepeateCount > 6) {
						count = count + (invRepeateCount - 6);
						start = start + (invRepeateCount - 6);
					}

					for (int p = start; p < count; p++) {
						sheet.createRow(p);

						if (p != (count - 1)) {
							for (int k = 0; k < 8; k++) {

								Cell lastButOneCell = sheet.getRow(p).getCell(k,
										Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
								lastButOneCell.setCellStyle(whiteBackGround);
							}
						} else {
							for (int k = 0; k < 8; k++) {
								Cell lastCell = sheet.getRow(p).getCell(k, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
								lastCell.setCellStyle(whiteBackGroundBaseLineRight);

							}
						}
					}
					invRepeateCount = 0;
				}

				index++;

				System.out.println("Next Row is : " + nextRow);
				System.out.println("Next start index is : " + startIndex);
			}

			sheet.createRow(nextRow - 2);

			sheet.getRow(nextRow - 2).getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue("SubTotal");
			sheet.getRow(nextRow - 2).getCell(5, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
					.setCellValue(supplyAmountTotal);
			sheet.getRow(nextRow - 2).getCell(7, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
					.setCellValue(erectionAmountTotal);

			

			Sheet annexture = workBook.getSheetAt(1);

			annexture.getRow(4 + s).getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(s);
			annexture.getRow(4 + s).getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
					.setCellValue(sheet.getSheetName());
			annexture.getRow(4 + s).getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
					.setCellValue(supplyAmountTotal);
			annexture.getRow(4 + s).getCell(4, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
					.setCellValue(erectionAmountTotal);

			Cell supplyTotalCell = annexture.getRow(15).getCell(3);
			Cell erectionTotalCell = annexture.getRow(15).getCell(4);

			FormulaEvaluator evaluator = workBook.getCreationHelper().createFormulaEvaluator();
			
			evaluator.evaluateFormulaCell(supplyTotalCell);
			evaluator.evaluateFormulaCell(erectionTotalCell);

			for (int k = 0; k < 8; k++) {
				Cell lastCell = sheet.getRow(nextRow - 2).getCell(k, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
				lastCell.setCellStyle(whiteBackGroundBaseLineRight);
			}

			startIndex = startIndex + inventoryCount;

			for (int j = 0; j < 8; j++) {
				sheet.autoSizeColumn(j);
			}
		}

		if (isOffer) {
			workBook.removeSheetAt(0);
			workBook.removeSheetAt(0);
			workBook.removeSheetAt(0);
		} else {

			Sheet cover = workBook.getSheetAt(0);

			cover.getRow(6).getCell(7, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
					.setCellValue(new SimpleDateFormat("dd-MMMM-yyyy").format(new Date()));
			cover.getRow(7).getCell(7, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue("");

			cover.getRow(7).getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(header.getClient());
			cover.getRow(8).getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(header.getSite());

			cover.getRow(10).getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
					.setCellValue(cover.getRow(10).getCell(2).getStringCellValue() + "");

			cover.getRow(12).getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
					.setCellValue(cover.getRow(12).getCell(2).getStringCellValue() + header.getClient());
			
			FormulaEvaluator evaluator1 = workBook.getCreationHelper().createFormulaEvaluator();
			
			evaluator1.evaluateFormulaCell(cover.getRow(23).getCell(6, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK));
			evaluator1.evaluateFormulaCell(cover.getRow(23).getCell(7, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK));
			evaluator1.evaluateFormulaCell(cover.getRow(24).getCell(6, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK));
			evaluator1.evaluateFormulaCell(cover.getRow(24).getCell(7, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK));
			evaluator1.evaluateFormulaCell(cover.getRow(25).getCell(6, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK));
			evaluator1.evaluateFormulaCell(cover.getRow(26).getCell(6, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK));
			evaluator1.evaluateFormulaCell(cover.getRow(27).getCell(6, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK));

			workBook.removeSheetAt(2);
		}

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
