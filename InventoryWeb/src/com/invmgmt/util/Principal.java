package com.invmgmt.util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.annotation.ManagedBean;

import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdfwriter.ContentStreamWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.springframework.util.ResourceUtils;

import com.invmgmt.entity.TaxInvoiceDetails;

@ManagedBean
public class Principal {
    // public static final String SRC =
    // "C:/Users/Uday/Desktop/Projects/Humdule/NewFolder/Invoice_No-HI-073.pdf";
    public static final String DESTINATION = System.getProperty("java.io.tmpdir") + "/TaxInvoice.pdf";

    public boolean createInvoice(TaxInvoiceDetails taxInvoiceDetails) {
	PDDocument document = null;
	boolean invoiceGenerated = false;
	try {
	    File file = ResourceUtils.getFile("classpath:InvoiceTemplate.pdf");

	    document = PDDocument.load(file);
	    
	    int rateInt = Integer.parseInt(taxInvoiceDetails.getRate());
	    int cGst = rateInt*9/100;
		    
	    String[] address = taxInvoiceDetails.getAddressedto1().split(",");
	    
	    for(int i=1;i<address.length;i++)
	    {
		document = replaceText(document, "addressedto"+i, address[i]);
	    }
	    
	    String amtWrd1 = taxInvoiceDetails.getAmtInwrd1().substring(0, 39);
	    String amtWrd2 = taxInvoiceDetails.getAmtInwrd1().substring(40);
	    String total = String.valueOf(rateInt + cGst*2);
	    String cGstString = String.valueOf(cGst);
	    	    
	    document = replaceText(document, "invoiceNo", taxInvoiceDetails.getInvoiceNo());
	    document = replaceText(document, "date", new Date().toString());
	    document = replaceText(document, "orderNo", taxInvoiceDetails.getOrderNo());
	    document = replaceText(document, "orderDate", taxInvoiceDetails.getOrderDate());
	    document = replaceText(document, "contactName", taxInvoiceDetails.getContactName());
	    document = replaceText(document, "mobileNo", taxInvoiceDetails.getMobileNo());
	    document = replaceText(document, "hsnOrSac", taxInvoiceDetails.getHsnOrSac());
	    document = replaceText(document, "rate", taxInvoiceDetails.getRate());
	    document = replaceText(document, "amtInwrd1", amtWrd1);
	    document = replaceText(document, "amtInwrd2", "-" + amtWrd2);
	    document = replaceText(document, "cGst", cGstString);
	    document = replaceText(document, "total", total);
	    document = replaceText(document, "gstNo", taxInvoiceDetails.getGstNo());

	    document.save(DESTINATION);
	    document.close();

	    invoiceGenerated = true;
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	return invoiceGenerated;
    }

    private PDDocument replaceText(PDDocument document, String searchString, String replacement) throws IOException {

	for (PDPage page : document.getPages()) {
	    System.out.println(page.getContents());
	    PDFStreamParser parser = new PDFStreamParser(page);
	    parser.parse();
	    List<?> tokens = parser.getTokens();

	    for (int j = 0; j < tokens.size(); j++) {
		Object next = tokens.get(j);
		if (next instanceof Operator) {
		    Operator op = (Operator) next;

		    String pstring = "";
		    int prej = 0;

		    if (op.getName().equals("Tj")) {
			COSString previous = (COSString) tokens.get(j - 1);
			String string = previous.getString();
			string = string.replaceFirst(searchString, replacement);
			previous.setValue(string.getBytes());
		    } else if (op.getName().equals("TJ")) {
			COSArray previous = (COSArray) tokens.get(j - 1);
			for (int k = 0; k < previous.size(); k++) {
			    Object arrElement = previous.getObject(k);
			    if (arrElement instanceof COSString) {
				COSString cosString = (COSString) arrElement;
				String string = cosString.getString();

				if (j == prej) {
				    pstring += string;
				} else {
				    prej = j;
				    pstring = string;
				}
			    }
			}

			if (searchString.equals(pstring.trim())) {
			    COSString cosString2 = (COSString) previous.getObject(0);
			    cosString2.setValue(replacement.getBytes());

			    int total = previous.size() - 1;
			    for (int k = total; k > 0; k--) {
				previous.remove(k);
			    }
			}
		    }
		}
	    }
	    PDStream updatedStream = new PDStream(document);
	    OutputStream out = updatedStream.createOutputStream(COSName.FLATE_DECODE);
	    ContentStreamWriter tokenWriter = new ContentStreamWriter(out);
	    tokenWriter.writeTokens(tokens);
	    out.close();
	    page.setContents(updatedStream);
	}

	return document;
    }
}