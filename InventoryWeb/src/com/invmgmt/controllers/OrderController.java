package com.invmgmt.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.invmgmt.dao.TaxInvoiceDetailsDao;
import com.invmgmt.entity.TaxInvoiceDetails;
import com.invmgmt.util.InventoryUtils;

@Controller
@EnableWebMvc
public class OrderController {

    
    @Autowired
    InventoryUtils inventoryUtils;
    
    @Autowired
    TaxInvoiceDetailsDao invoiceDao;
    
    @RequestMapping(value = "/generateOrderForm", method = RequestMethod.POST)
    public ModelAndView generateOfferFrom(String[] inventoryName, String[] material, String[] type, String[] manifMethod,
	    String[] classOrGrade, String[] quantity, String[] supplyRate) 
    {
	StringBuffer lineItemData = new StringBuffer();

	ModelAndView mav = new ModelAndView("purchaseOrderForm");

	int length = inventoryName.length;
	
	System.out.println("inventoryName.length is : "+length);
	for (int i = 0; i < length; i++) {

	    String description = inventoryUtils.createDescriptionLine(material[i], type[i], inventoryName[i], classOrGrade[i]);
	    String lineItem = getInventoryDetailsRow(String.valueOf(i+1), description, quantity[i], supplyRate[i]);
	    lineItemData.append(lineItem);
	}

	mav.addObject("lineItemData", lineItemData.toString());
	return mav;
    }

    @RequestMapping(value = "/generateOrder", method = RequestMethod.POST)
    public ModelAndView generateOffer(String companyName, String location, String contactName, String contactNumber,
	    String contactEmail, String[] term, String lineItem) 
    {

	ModelAndView mav = new ModelAndView("purchaseOrder");

	StringBuffer terms = new StringBuffer();
	
	for(String termLine : term)
	{
	    terms.append(getTermHtml(termLine));
	}

	LocalDate date = LocalDate.now();
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yy");
	String poDate = date.format(formatter);
	
	mav.addObject("PONumber","HI/CSE/"+String.valueOf(Math.random()));
	mav.addObject("PODate", poDate);
	mav.addObject("companyName", companyName);
	mav.addObject("location", location);
	mav.addObject("contactName", contactName);
	mav.addObject("contactNumber", contactNumber);
	mav.addObject("contactEmail", contactEmail);
	mav.addObject("terms", terms.toString());
	
	System.out.println(lineItem);
	
	mav.addObject("lineItems", lineItem);
	return mav;
    }
    
    @RequestMapping(value = "/showInvoice", method = {RequestMethod.POST,RequestMethod.GET} )
    private String shiwInvoice(Model model, String invoiceName)
    {
	ArrayList<TaxInvoiceDetails> invoiceDetails = invoiceDao.getTaxIvoiceData("taxInvoiceNo", invoiceName);
	
	model.addAttribute("taxInvoiceDetails", invoiceDetails.get(0));
	
	return "taxInvoiceView";
    }
    
    
    private String getInventoryDetailsRow(String sr_no, String description, String quantity, String supplyRate) 
    {
	String template = "<TR>" + "	<TD class=\"tr8 td38\"><P class=\"p16 ft8\">sr_no</P></TD>               "
		+ "	<TD class=\"tr8 td27\"><P class=\"p0 ft0\"></P></TD>               "
		+ "	<TD class=\"tr8 td53\"><P class=\"p0 ft0\"></P></TD>               "
		+ "	<TD colspan=2 class=\"tr8 td54\"><P class=\"p4 ft8\">Description</P></TD>"
		+ "	<TD class=\"tr8 td41\"><P class=\"p0 ft0\"></P></TD>               "
		+ "	<TD class=\"tr8 td42\"><P class=\"p17 ft8\">quantity</P></TD>            "
		+ "	<TD class=\"tr8 td43\"><P class=\"p8 ft8\">NB</P></TD>                 "
		+ "	<TD class=\"tr8 td44\"><P class=\"p18 ft8\">supplyRate</P></TD>"
		+ "	<TD class=\"tr8 td55\"><P class=\"p5 ft9 gstCentral\" value=\"cgst\">cgst</P></TD>                 "
		+ "	<TD class=\"tr8 td55\"><P class=\"p5 ft9 gstState\" value=\"sgst\">sgst</P></TD>                 "
		+ "	<TD class=\"tr8 td17\"><P class=\"p5 ft9 lineAmt\" value=\"amount\">amount</P></TD>              "
		+ "</TR>                                                                       ";

	float amount = Float.parseFloat(quantity)*Float.parseFloat(supplyRate);
	float sgst = amount*9/100;
	float cgst = amount*9/100;

	String stringToReturn = template.replace("sr_no", sr_no)
		.replace("Description", description)
		.replace("quantity", quantity)
		.replace("supplyRate", supplyRate.contains(".")?supplyRate.substring(0,supplyRate.indexOf(".")):supplyRate)
		.replace("cgst", get2DecimalVal(cgst))
		.replace("sgst", get2DecimalVal(sgst))
		.replace("amount", get2DecimalVal(amount));

	return stringToReturn;
    }
    
    public String get2DecimalVal(float val)
    {
	String twoDecimalVal = String.valueOf(val);

	//twoDecimalVal = twoDecimalVal.substring(0, twoDecimalVal.indexOf(".") + 3);

	return twoDecimalVal;
    }
    private String getTermHtml(String termLine)
    {
	String template = "<TR>                                                                     "+
		"	<TD colspan=7 class=\"tr8 td58\"><P class=\"p23 ft3\">1 TERM_TEXT.</P></TD>"+
		"	<TD class=\"tr8 td30\"><P class=\"p0 ft0\"></P></TD>                "+
		"	<TD class=\"tr8 td31\"><P class=\"p0 ft0\"></P></TD>                "+
		"	<TD class=\"tr8 td16\"><P class=\"p0 ft0\"></P></TD>                "+
		"	<TD class=\"tr8 td16\"><P class=\"p0 ft0\"></P></TD>                "+
		"	<TD class=\"tr8 td17\"><P class=\"p0 ft0\"></P></TD>                "+
		"</TR>                                                                    ";
	
	String stringToReturn = template.replace("TERM_TEXT",termLine);
	
	return stringToReturn;
    }

}
