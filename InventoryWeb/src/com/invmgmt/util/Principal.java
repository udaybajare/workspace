package com.invmgmt.util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    
    public boolean createInvoice(TaxInvoiceDetails taxInvoiceDetails) {
	
	String[] destination = { System.getProperty("java.io.tmpdir") + "/TaxInvoice.pdf",
		System.getProperty("user.home") + "/Downloads/" + taxInvoiceDetails.getTaxInvoiceNo().replace("/", "_") + ".pdf" };
	boolean invoiceGenerated = false;
	
	String[] templates = {"InvoiceTemplate","InvoiceTemplateDwnld"};
	
	for(int i=0;i<templates.length;i++)
	{
	    PDDocument document = null;
		
		try {
		    //File file = ResourceUtils.getFile("classpath:InvoiceTemplate.pdf");
		    File file = ResourceUtils.getFile("classpath:"+templates[i]+".pdf");
		    document = PDDocument.load(file);
		    
		    double rateInt = Double.parseDouble(taxInvoiceDetails.getRate());
		    double cGst = rateInt*9/100;
			    
		    String[] address = taxInvoiceDetails.getAddressedto1().split(",");
		    
		    for(int j=0;j<address.length;j++)
		    {
			document = replaceText(document, "ADDRESSEDTO"+j+1, address[j]);
		    }		    
		    
		    int moreEle = 4 - address.length;
		    
		    for(int k=4;k<=moreEle;k++)
		    {
			document = replaceText(document, "ADDRESSEDTO"+k, " ");
		    }
		    
		    
		    String amtWrd1 = taxInvoiceDetails.getAmtInwrd1();
		    String amtWrd2 = taxInvoiceDetails.getAmtInwrd2();
		    String total = String.valueOf(rateInt + cGst*2);
		    String cGstString = String.valueOf(cGst);
		    	    
		    document = replaceText(document, "invoiceNo", taxInvoiceDetails.getTaxInvoiceNo());
		    document = replaceText(document, "date", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
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
		    document = replaceText(document, "Mahindra, Kanhe", taxInvoiceDetails.getProjectName());
		    document = replaceText(document, "Supply", taxInvoiceDetails.getInvoiceType());		    
		    document.save(destination[i]);
		    document.close();

		    invoiceGenerated = true;
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
   
	}
	
	return invoiceGenerated;
    }

    public boolean generatePO()
    {
	boolean poGenerated = true;
	
	String[] destination = {"C:/temp/POTemplate.pdf"};
	
	
	    PDDocument document = null;
		
		try {
		    File file = ResourceUtils.getFile("classpath:POTemplate.pdf");
		    document = PDDocument.load(file);
		    document = replaceText(document, "Mr.	PRATIK", "contactName");	    
		    document = replaceText(document, "CEASEFIRE	INDUSTRIES	PVT.	LTD.", "vendorName");
		    document = replaceText(document, "Mr.	PRATIK", "contactName");
		    document = replaceText(document, "8448390493", "contactNo");
		    document = replaceText(document, "pratikn.pun@ceasefire.in", "contactEmail");
		    document = replaceText(document, "HI/CSE/291218045", "poNumber");
		    document = replaceText(document, "29-Dec-18", "poDate");
		    document = replaceText(document, "CEASEFIRE	QUICK	RESPONSE	SYSTEM	PRE", "description1");
		    document = replaceText(document, "ENGINEERED	(HFC236fa)	2KG	KIT ", "description2");
		    document = replaceText(document, "84241000", "hsnNu");
		    document = replaceText(document, "NOS", "unit");
		    document = replaceText(document, "58,725", "unitPrice");
		    document = replaceText(document, "10,571" ,"cGst");
		    document = replaceText(document, "1,17,450", "amount");
	            document = replaceText(document, "21,141", "taxTotal"); 
		    document = replaceText(document, "1,38,591", "total");
		    document = replaceText(document, "1,38,591", "total");
		    document = replaceText(document, "ONE	LAKH	THIRTY	EIGHT	THOUSAND	FIVE	HUNDRED	", "amountInword1");
		    document = replaceText(document, "AND	NINETY	ONE	ONLY", "amountInword2");
		    document = replaceText(document, "Please	send	two	copies	of	your	invoice	with	enclosed	Purchase	Order	Cop", "term1");
		    document = replaceText(document, "Enter	this	order	in	accordance	with	the	prices,	terms,	delivery	method,	and	specifications	listed	above.", "term2");
		    document = replaceText(document, "Warranty	12	months", "term3");
		    document = replaceText(document, "Material	Test	Certificate	Required	along	with	invoice", "term4");
		    document = replaceText(document, "Taxes	as	applicable", "term5");
		    		    
		    document.save(destination[0]);
		    document.close();

		} catch (Exception ex) {
		    poGenerated = false;
		    ex.printStackTrace();
		}	
		return poGenerated;
    }
    public PDDocument replaceText(PDDocument document, String searchString, String replacement) throws IOException {

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