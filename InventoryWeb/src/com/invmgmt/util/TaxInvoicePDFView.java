package com.invmgmt.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.view.AbstractView;

import com.invmgmt.entity.TaxInvoiceDetails;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Component("taxInvoiceView")
public class TaxInvoicePDFView extends AbstractView {

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		TaxInvoiceDetails taxInvoiceDetails = (TaxInvoiceDetails) model.get("taxInvoiceDetails");

		response.setHeader("Content-Disposition",
				"attachment; filename=" + taxInvoiceDetails.getTaxInvoiceNo() + ".pdf");

		createInvoice(response, taxInvoiceDetails);

	}

	public void createInvoice(HttpServletResponse response, TaxInvoiceDetails taxInvoiceDetails) {

		try {

			Document document = new Document();

			PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
			document.open();

			document.add(new Paragraph(70, "\u00a0"));

			// Table1
			PdfPTable table = createNewTable(1);
			PdfPCell firstCell = createNewCell(new Paragraph("TAX INVOICE", blackBGLBi14), 20);
			firstCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(firstCell);
			document.add(table);

			// Table2
			PdfPTable table2 = createNewTable(3);
			float[] tableWidths = { 2f, 1f, 1f };

			table2.setWidths(tableWidths);

			PdfPCell addressedToCell = createNewCell();
			addressedToCell.setRowspan(6);

			addressedToCell.addElement(new Paragraph("To,", boldBlackBGLB11));
			addressedToCell.addElement(new Paragraph(taxInvoiceDetails.getAddressedto1(), boldBlackBGLB11));
			addressedToCell.addElement(new Paragraph(10, "\u00a0"));
			addressedToCell.addElement(new Paragraph("Gst No.-" + taxInvoiceDetails.getGstNo(), boldBlackBGLB11));

			table2.addCell(addressedToCell);

			table2.addCell(createNewCell(new Paragraph("Tax Invoice No.", boldBlackBGLB11)));
			table2.addCell(createNewCell(new Paragraph(taxInvoiceDetails.getTaxInvoiceNo(), blackBGLB11)));
			table2.addCell(createNewCell(new Paragraph("Date           ", boldBlackBGLB11)));
			table2.addCell(createNewCell(new Paragraph(taxInvoiceDetails.getTaxInvoiceDate(), blackBGLB11)));
			table2.addCell(createNewCell(new Paragraph("order no.      ", boldBlackBGLB11)));
			table2.addCell(createNewCell(new Paragraph(taxInvoiceDetails.getOrderNo(), blackBGLB11)));
			table2.addCell(createNewCell(new Paragraph("order date.    ", boldBlackBGLB11)));
			table2.addCell(createNewCell(new Paragraph(taxInvoiceDetails.getOrderDate(), blackBGLB11)));
			table2.addCell(createNewCell(new Paragraph("kind attn:     ", boldBlackBGLB11)));
			table2.addCell(createNewCell(new Paragraph(taxInvoiceDetails.getContactName(), blackBGLB11)));
			table2.addCell(createNewCell(new Paragraph("Mobile no.     ", boldBlackBGLB11)));
			table2.addCell(createNewCell(new Paragraph(taxInvoiceDetails.getMobileNo(), blackBGLB11)));

			document.add(table2);

			// Table3
			PdfPTable table3 = createNewTable(7);

			float[] table3Widths = { 1f, 2f, 5f, 1f, 2f, 1f, 3f };
			table3.setWidths(table3Widths);

			table3.addCell(createNewCell(new Paragraph("Sr no      ", boldBlackBGLB11)));
			table3.addCell(createNewCell(new Paragraph("HSN/SAC    ", boldBlackBGLB11)));
			table3.addCell(createNewCell(new Paragraph("Description", boldBlackBGLB11)));
			table3.addCell(createNewCell(new Paragraph("Qty.       ", boldBlackBGLB11)));
			table3.addCell(createNewCell(new Paragraph("Rate       ", boldBlackBGLB11)));
			table3.addCell(createNewCell(new Paragraph("Unit       ", boldBlackBGLB11)));
			table3.addCell(createNewCell(new Paragraph("amount     ", boldBlackBGLB11)));

			PdfPCell srnoCell = createNewCell(new Paragraph("1	      ", blackBGLB11));
			srnoCell.setFixedHeight(250);
			table3.addCell(srnoCell);
			table3.addCell(createNewCell(new Paragraph(taxInvoiceDetails.getHsnOrSac(), blackBGLB11)));
			table3.addCell(createNewCell(new Paragraph("structural steel erection services as per attached annexure", blackBGLB11)));
			table3.addCell(createNewCell(new Paragraph("1       		", blackBGLB11)));
			table3.addCell(createNewCell(new Paragraph(taxInvoiceDetails.getMiscCharges(), blackBGLB11)));
			table3.addCell(createNewCell(new Paragraph("Nos			", blackBGLB11)));
			table3.addCell(createNewCell(new Paragraph(taxInvoiceDetails.getMiscCharges(), blackBGLB11)));

			document.add(table3);

			// Table4
			PdfPTable table4 = createNewTable(4);

			float[] table4Widths = { 3f, 5f, 3f, 3f };
			table4.setWidths(table4Widths);

			Paragraph amtInWords = new Paragraph("Amt in Words:", blackBGLB11);
			amtInWords.setAlignment(Element.ALIGN_CENTER);

			PdfPCell amtInWordscell = createNewCell();
			amtInWordscell.addElement(amtInWords);
			amtInWordscell.setRowspan(5);
			table4.addCell(amtInWordscell);

			Paragraph amtInWordsVal = new Paragraph(taxInvoiceDetails.getAmtInwrd1() + taxInvoiceDetails.getAmtInwrd2(),
					blackBGLB11);
			amtInWordsVal.setAlignment(Element.ALIGN_CENTER);

			PdfPCell amtInWordsValCell = createNewCell();
			amtInWordsValCell.addElement(amtInWordsVal);
			amtInWordsValCell.setRowspan(5);

			table4.addCell(amtInWordsValCell);

			table4.addCell(createNewCell(new Paragraph("sub total:", boldBlackBGLB11)));
			table4.addCell(createNewCell(new Paragraph(taxInvoiceDetails.getMiscCharges(), blackBGLB11)));
			table4.addCell(createNewCell(new Paragraph("cgst 9%	 ", boldBlackBGLB11)));
			table4.addCell(createNewCell(new Paragraph(
					String.valueOf(Double.parseDouble(taxInvoiceDetails.getMiscCharges()) * 9 / 100), blackBGLB11)));
			table4.addCell(createNewCell(new Paragraph("SGST 9%	 ", boldBlackBGLB11)));
			table4.addCell(createNewCell(new Paragraph(
					String.valueOf(Double.parseDouble(taxInvoiceDetails.getMiscCharges()) * 9 / 100), blackBGLB11)));
			table4.addCell(createNewCell(new Paragraph("IGST 18%	 ", boldBlackBGLB11)));
			table4.addCell(createNewCell(new Paragraph("-", blackBGLB11)));
			table4.addCell(createNewCell(new Paragraph("total	 ", boldBlackBGLB11)));
			table4.addCell(
					createNewCell(new Paragraph(
							String.valueOf(Double.parseDouble(taxInvoiceDetails.getMiscCharges())
									+ 2 * (Double.parseDouble(taxInvoiceDetails.getMiscCharges()) * 9 / 100)),
							blackBGLB11)));

			document.add(table4);

			// Table5

			PdfPTable table5 = createNewTable(2);

			float[] table5Widths = { 4f, 2f };
			table5.setWidths(table5Widths);

			table5.addCell(createNewCell(new Paragraph("Company's Bank Details ", blackBGLB11)));
			table5.addCell(createNewCell(new Paragraph("Hamdule Industries", boldBlackBGLB11)));
			table5.addCell(createNewCell(new Paragraph("Bank Name: HDFC Bank Ltd ", blackBGLB11)));

			PdfPCell signCell = createNewCell(70);
			signCell.setRowspan(3);
			try {

				File file = ResourceUtils.getFile("classpath:sign.png");
				// init array with file length
				byte[] bytesArray = new byte[(int) file.length()];

				FileInputStream fis = new FileInputStream(file);
				fis.read(bytesArray); // read file into bytes[]
				fis.close();

				Image signImg = Image.getInstance(bytesArray);

				float width = signCell.getWidth();
				signImg.setWidthPercentage(width);
				signImg.setScaleToFitHeight(true);

				signCell.addElement(signImg);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			table5.addCell(signCell);

			table5.addCell(createNewCell(new Paragraph("A/c No.: 50200007593854 ", blackBGLB11)));
			table5.addCell(createNewCell(new Paragraph("IFSC Code: HDFC0000437 ", blackBGLB11)));

			table5.addCell(createNewCell(new Paragraph("Branch:  Pimpri, Pune ", blackBGLB11)));
			table5.addCell(createNewCell(new Paragraph("AUTHORIZED SIGNATORY ", boldBlackBGLB11)));
			table5.addCell(createNewCell(new Paragraph(
					"*intrest rate at 24% per annum applicable for late payment after due date", blackBGLB11), 30));

			PdfPCell stampCell = createNewCell(80);

			stampCell.setRowspan(3);

			try {
				File file = ResourceUtils.getFile("classpath:stamp.png");
				// init array with file length
				byte[] bytesArray = new byte[(int) file.length()];

				FileInputStream fis = new FileInputStream(file);
				fis.read(bytesArray); // read file into bytes[]
				fis.close();

				Image img = Image.getInstance(bytesArray);

				float width = stampCell.getWidth();
				img.setWidthPercentage(width);
				img.setScaleToFitHeight(true);

				stampCell.addElement(img);

			} catch (Exception ex) {
				ex.printStackTrace();
			}

			table5.addCell(stampCell);

			table5.addCell(createNewCell(new Paragraph("SUBJECT TO PUNE JURISDICTION ONLY. ", blackBGLB11)));
			table5.addCell(createNewCell(new Paragraph("GST NO.: 27AEBPH1001B1ZM ", blackBGLB11)));

			table5.addCell(createNewCell(new Paragraph("PAN NO.: AEBPH1001B ", blackBGLB11)));
			table5.addCell(createNewCell(new Paragraph("STAMP ", boldBlackBGLB11)));

			try {
				File file = ResourceUtils.getFile("classpath:background.png");
				// init array with file length
				byte[] bytesArray = new byte[(int) file.length()];

				FileInputStream fis = new FileInputStream(file);
				fis.read(bytesArray); // read file into bytes[]
				fis.close();

				Image background = Image.getInstance(bytesArray);

				float width = document.getPageSize().getWidth();
				float height = document.getPageSize().getHeight();
				writer.getDirectContentUnder().addImage(background, width, 0, 0, height, 0, 0);

			} catch (Exception ex) {
				ex.printStackTrace();
			}

			document.add(table5);

			document.close();
			writer.close();

		} catch (Exception ex) {

			ex.printStackTrace();
		}
	}

	protected PdfPTable createNewTable(int numberOfColumns) {
		PdfPTable table = new PdfPTable(numberOfColumns);
		table.setWidthPercentage(95);

		return table;
	}

	protected PdfPCell createNewCell(Paragraph ph) {
		PdfPCell cell = new PdfPCell(ph);
		cell.setFixedHeight(15);
		return cell;
	}

	protected PdfPCell createNewCell(Paragraph ph, int fixedHeight) {
		PdfPCell cell = new PdfPCell(ph);
		cell.setFixedHeight(fixedHeight);
		return cell;
	}

	protected PdfPCell createNewCell(int fixedHeight) {
		PdfPCell cell = new PdfPCell(new Paragraph());
		cell.setFixedHeight(fixedHeight);
		return cell;
	}

	protected PdfPCell createNewCell() {
		return createNewCell(new Paragraph());
	}

	public static final Font blackBGLBi14 = new Font(Font.getFamily("BankGothic Lt BT Light"), 14, Font.BOLD,
			BaseColor.BLACK);
	public static final Font blackBGLB9 = new Font(Font.getFamily("BankGothic Lt BT Light"), 9, Font.NORMAL,
			BaseColor.BLACK);
	public static final Font blackBGLB10 = new Font(Font.getFamily("BankGothic Lt BT Light"), 10, Font.NORMAL,
			BaseColor.BLACK);
	public static final Font blackBGLB11 = new Font(Font.getFamily("BankGothic Lt BT Light"), 11, Font.NORMAL,
			BaseColor.BLACK);
	public static final Font boldBlackBGLB11 = new Font(Font.getFamily("BankGothic Lt BT Light"), 11, Font.BOLD,
			BaseColor.BLACK);
	public static final Font boldBlackBGLB10 = new Font(Font.getFamily("BankGothic Lt BT Light"), 10, Font.BOLD,
			BaseColor.BLACK);
}
