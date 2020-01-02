package com.invmgmt.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.view.AbstractView;

import com.invmgmt.entity.PODetails;

@Component("purchaseOrderView")
public class PurchaseOrderPDFView extends AbstractView {

    @Autowired
    Principal principal;

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	PODetails poDetails = (PODetails) model.get("poDetails");
	String poLineDetails = (String) model.get("poLineDetails");

	String[] poLines = poLineDetails.split(";");

	response.setHeader("Content-Disposition", "attachment; filename=" + poDetails.getPoNumber() + ".xlsx");
	
	try {
	 generatePOExcel(poDetails, poLineDetails, response);
	 
	/*PDDocument document = null;

	
	    File file = ResourceUtils.getFile("classpath:Purchase-Order-converted.pdf");
	    document = PDDocument.load(file);

	    document = principal.replaceText(document, "vendorName", poDetails.getVendorName());
	    document = principal.replaceText(document, "contactName", poDetails.getContactName());
	    document = principal.replaceText(document, "contactNo", poDetails.getContactNumber());
	    document = principal.replaceText(document, "contactEmail", poDetails.getContactEmail());
	    document = principal.replaceText(document, "poNumber", poDetails.getPoNumber());
	    document = principal.replaceText(document, "poDate", poDetails.getPoDate());

	    double cGstTotal = 0;
	    double subTotal = 0;
	    double grandTotal = 0;

	    for (int i = 0; i < poLines.length; i++) {
		String[] attrs = poLines[i].split(",");

		int j = i + 1;
		int k = 0;

		document = principal.replaceText(document, "sr" + j, attrs[k]);
		document = principal.replaceText(document, "des" + j, attrs[k + 1]);

		String qty = attrs[k + 2];
		String unitPrice = attrs[k + 3];

		document = principal.replaceText(document, "qty" + j, qty);
		document = principal.replaceText(document, "unitPrc" + j, unitPrice);

		double amount = Double.parseDouble(qty) * Double.parseDouble(unitPrice);
		double cgst = amount * 9 / 100;

		cGstTotal = cGstTotal + cgst;
		subTotal = subTotal + amount;

		grandTotal = cgst + amount;

		document = principal.replaceText(document, "cgst" + j, String.valueOf(cgst));
		document = principal.replaceText(document, "sGst" + j, String.valueOf(cgst));
		document = principal.replaceText(document, "amount" + j, String.valueOf(amount));
	    }

	    document = principal.replaceText(document, "cGstTotal", String.valueOf(cGstTotal));
	    document = principal.replaceText(document, "sGstTotal", String.valueOf(cGstTotal));

	    document = principal.replaceText(document, "amtInwrd1", "Amount1");
	    document = principal.replaceText(document, "amtInwrd2", "Amount2");

	    document = principal.replaceText(document, "subTotal", String.valueOf(subTotal));
	    document = principal.replaceText(document, "taxTotak", String.valueOf(cGstTotal * 2));
	    document = principal.replaceText(document, "grandTotal", String.valueOf(grandTotal));

	    String[] terms = poDetails.getTerm();

	    for (int l = 0; l < terms.length; l++) {
		int j = l + 1;
		document = principal.replaceText(document, "term" + j, terms[l]);
	    }

	    String addr = poDetails.getLocation();

	    document = principal.replaceText(document, "shipAddr1", addr.substring(0, addr.lastIndexOf(",") - 1));
	    document = principal.replaceText(document, "shipAddr2", addr.substring(addr.lastIndexOf(",") - 1));
	    document.save(response.getOutputStream());
	    document.close();*/
	} catch (Exception ex) {
	    ex.printStackTrace();
	}

    }
    
    public void generatePOExcel(PODetails poDetails, String poLineDetails, HttpServletResponse response) throws IOException
	{
    	String[] poLines = poLineDetails.split(";");
    	
		Workbook workBook = null;
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(ResourceUtils.getFile("classpath:PO_Template.xlsx"));
			workBook = WorkbookFactory.create(inputStream);
		} catch (EncryptedDocumentException | InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Sheet sheet = workBook.getSheetAt(0);

		// Sort the list

		Cell cellToUpdate1 = sheet.getRow(12).getCell(10);
		cellToUpdate1.setCellValue(poDetails.getPoNumber());
		Cell cellToUpdate2 = sheet.getRow(13).getCell(10);
		cellToUpdate2.setCellValue(poDetails.getPoDate());
		Cell cellToUpdate3 = sheet.getRow(14).getCell(2);
		cellToUpdate3.setCellValue(poDetails.getVendorName());
		Cell cellToUpdate4 = sheet.getRow(15).getCell(1);
		cellToUpdate4.setCellValue("Pune");
		Cell cellToUpdate5 = sheet.getRow(16).getCell(3);
		cellToUpdate5.setCellValue(poDetails.getContactName());
		Cell cellToUpdate6 = sheet.getRow(17).getCell(3);
		cellToUpdate6.setCellValue(poDetails.getContactNumber());
		Cell cellToUpdate7 = sheet.getRow(18).getCell(3);
		cellToUpdate7.setCellValue(poDetails.getContactEmail());

	    double cGstTotal = 0;
	    double subTotal = 0;
	    double grandTotal = 0;
	    
	    int currentRow = 22;
		for(int i=0; i < poLines.length; i++)
		{
			String[] values = poLines[i].split(",");
			
			Cell cellToUpdate8 = sheet.getRow(currentRow).getCell(1,MissingCellPolicy.CREATE_NULL_AS_BLANK);
			cellToUpdate8.setCellValue(values[0]);
			Cell cellToUpdate9 = sheet.getRow(currentRow).getCell(2,MissingCellPolicy.CREATE_NULL_AS_BLANK);
			cellToUpdate9.setCellValue("");
			Cell cellToUpdate10 = sheet.getRow(currentRow).getCell(4,MissingCellPolicy.CREATE_NULL_AS_BLANK);
			cellToUpdate10.setCellValue(values[1]);
			Cell cellToUpdate11 = sheet.getRow(currentRow).getCell(9,MissingCellPolicy.CREATE_NULL_AS_BLANK);
			cellToUpdate11.setCellValue(values[2]);
			Cell cellToUpdate12 = sheet.getRow(currentRow).getCell(10,MissingCellPolicy.CREATE_NULL_AS_BLANK);
			cellToUpdate12.setCellValue("NB");
			Cell cellToUpdate13 = sheet.getRow(currentRow).getCell(11,MissingCellPolicy.CREATE_NULL_AS_BLANK);
			cellToUpdate13.setCellValue(values[3]);
			
			
			double amount = Double.parseDouble(values[2]) * Double.parseDouble(values[3]);
			double cgst = amount * 9 / 100;

			cGstTotal = cGstTotal + cgst;
			subTotal = subTotal + amount;

			grandTotal = cgst + amount;
			
			Cell cellToUpdate14 = sheet.getRow(currentRow).getCell(12,MissingCellPolicy.CREATE_NULL_AS_BLANK);
			cellToUpdate14.setCellValue(cgst);
			Cell cellToUpdate15 = sheet.getRow(currentRow).getCell(13,MissingCellPolicy.CREATE_NULL_AS_BLANK);
			cellToUpdate15.setCellValue(cgst);
			Cell cellToUpdate16 = sheet.getRow(currentRow).getCell(14,MissingCellPolicy.CREATE_NULL_AS_BLANK);
			cellToUpdate16.setCellValue(amount);
		
			currentRow++;
			CellStyle cellStyle = workBook.createCellStyle();
			Font dataFont = workBook.createFont();
			dataFont.setFontName("Calibri");
			dataFont.setFontHeightInPoints((short) 8);
			
			cellStyle.setFont(dataFont);
			sheet.createRow(currentRow).setRowStyle(cellStyle);
		}
		
		Cell cellToUpdate17 = sheet.getRow(currentRow).getCell(12,MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cellToUpdate17.setCellValue(cGstTotal);
		Cell cellToUpdate18 = sheet.getRow(currentRow).getCell(13,MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cellToUpdate18.setCellValue(cGstTotal);
		
		
		//Move to next row
		currentRow++;
		
		Cell cellToUpdate19 = sheet.getRow(currentRow).getCell(4,MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cellToUpdate19.setCellValue("Amounts in words goes here");
		Cell cellToUpdate20 = sheet.getRow(currentRow).getCell(14,MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cellToUpdate20.setCellValue(subTotal);
		
		// Move to next row
		currentRow++;
		Cell cellToUpdate21 = sheet.getRow(currentRow).getCell(14,MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cellToUpdate21.setCellValue(cGstTotal * 2);

		// Move to next row
		currentRow++;
		Cell cellToUpdate22 = sheet.getRow(currentRow).getCell(14,MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cellToUpdate22.setCellValue((cGstTotal * 2) + subTotal);

		currentRow = currentRow+1;
		String[] terms = poDetails.getTerm();

		for (int i = 0; i < terms.length; i++)
		{
			Cell cellToUpdate = sheet.getRow(currentRow).getCell(1,MissingCellPolicy.CREATE_NULL_AS_BLANK);
			cellToUpdate.setCellValue(i+1);
			Cell cellToUpdateN = sheet.getRow(currentRow).getCell(2,MissingCellPolicy.CREATE_NULL_AS_BLANK);
			cellToUpdateN.setCellValue(terms[i]);
			
			currentRow++;
			CellStyle cellStyle = workBook.createCellStyle();
			Font dataFont = workBook.createFont();
			dataFont.setFontName("Calibri");
			dataFont.setFontHeightInPoints((short) 8);
			
			cellStyle.setFont(dataFont);
			
			sheet.createRow(currentRow).setRowStyle(cellStyle);
		}
		
		inputStream.close();

		
		workBook.write(response.getOutputStream());
		
		// Closing the workbook
		workBook.close();

	}
}
