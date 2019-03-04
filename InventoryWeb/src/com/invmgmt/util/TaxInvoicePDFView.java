package com.invmgmt.util;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.view.AbstractView;

import com.invmgmt.entity.TaxInvoiceDetails;

@Component("taxInvoiceView")
public class TaxInvoicePDFView extends AbstractView {

    @Autowired
    Principal principal;

  @Override
  protected void renderMergedOutputModel(Map<String, Object> model,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws Exception 
  {
      
      TaxInvoiceDetails taxInvoiceDetails = (TaxInvoiceDetails) model.get("taxInvoiceDetails");
      
      response.setHeader("Content-Disposition", "attachment; filename="+taxInvoiceDetails.getTaxInvoiceNo()+".pdf");
      
      PDDocument document = null;
	
	try {
	    File file = ResourceUtils.getFile("classpath:InvoiceTemplate.pdf");
	    document = PDDocument.load(file);
	    
	    int rateInt = Integer.parseInt(taxInvoiceDetails.getRate());
	    int cGst = rateInt*9/100;
		    
	    String[] address = taxInvoiceDetails.getAddressedto1().split(",");
	    
	    for(int j=0;j<address.length;j++)
	    {
		document = principal.replaceText(document, "addressedto"+j+1, address[j]);
	    }		    
	    
	    int moreEle = 4 - address.length;
	    
	    for(int k=4;k<=moreEle;k++)
	    {
		document = principal.replaceText(document, "addressedto"+k, " ");
	    }
	    
	    
	    String amtWrd1 = taxInvoiceDetails.getAmtInwrd1().substring(0, 39);
	    String amtWrd2 = taxInvoiceDetails.getAmtInwrd1().substring(40);
	    String total = String.valueOf(rateInt + cGst*2);
	    String cGstString = String.valueOf(cGst);
	    	    
	    document = principal.replaceText(document, "invoiceNo", taxInvoiceDetails.getInvoiceNo());
	    document = principal.replaceText(document, "date", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
	    document = principal.replaceText(document, "orderNo", taxInvoiceDetails.getOrderNo());
	    document = principal.replaceText(document, "orderDate", taxInvoiceDetails.getOrderDate());
	    document = principal.replaceText(document, "contactName", taxInvoiceDetails.getContactName());
	    document = principal.replaceText(document, "mobileNo", taxInvoiceDetails.getMobileNo());
	    document = principal.replaceText(document, "hsnOrSac", taxInvoiceDetails.getHsnOrSac());
	    document = principal.replaceText(document, "rate", taxInvoiceDetails.getRate());
	    document = principal.replaceText(document, "amtInwrd1", amtWrd1);
	    document = principal.replaceText(document, "amtInwrd2", "-" + amtWrd2);
	    document = principal.replaceText(document, "cGst", cGstString);
	    document = principal.replaceText(document, "total", total);
	    document = principal.replaceText(document, "gstNo", taxInvoiceDetails.getGstNo());
	    
	    document.save(response.getOutputStream());
	    document.close();
	} catch (Exception ex) {
	    ex.printStackTrace();
	}

  }
}
