package com.invmgmt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.invmgmt.util.InventoryUtils;

@Controller
@EnableWebMvc
public class OrderController {

    
    @Autowired
    InventoryUtils inventoryUtils;
    
    @RequestMapping(value = "/generateOrder", method = RequestMethod.POST)
    public ModelAndView generateOffer(String[] inventoryName, String[] material, String[] type, String[] manifMethod,
	    String[] classOrGrade, String[] quantity, String[] supplyRate) 
    {
	StringBuffer lineItemData = new StringBuffer();

	ModelAndView mav = new ModelAndView("purchaseOrder");

	int length = inventoryName.length;
	
	System.out.println("inventoryName.length is : "+length);
	for (int i = 0; i < length; i++) {

	    String description = inventoryUtils.createDescriptionLine(material[i], type[i], inventoryName[i], classOrGrade[i]);
	    String lineItem = getInventoryDetailsRow(String.valueOf(i+1), description, quantity[i], supplyRate[i]);
	    lineItemData.append(lineItem);
	}

	mav.addObject("lineItems", lineItemData.toString());
	return mav;
    }

    private String getInventoryDetailsRow(String sr_no, String description, String quantity, String supplyRate) 
    {
	String template = "<TR>" + "	<TD class=\"tr8 td38\"><P class=\"p16 ft8\">sr_no</P></TD>               "
		+ "	<TD class=\"tr8 td27\"><P class=\"p0 ft0\">&nbsp;</P></TD>               "
		+ "	<TD class=\"tr8 td53\"><P class=\"p0 ft0\">&nbsp;</P></TD>               "
		+ "	<TD colspan=2 class=\"tr8 td54\"><P class=\"p4 ft8\">Description</P></TD>"
		+ "	<TD class=\"tr8 td41\"><P class=\"p0 ft0\">&nbsp;</P></TD>               "
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
	
	String stringToReturn = template.replace("sr_no", sr_no).replace("Description", description).replace("quantity",
		quantity).replace("supplyRate",supplyRate).replace("cgst",String.valueOf(cgst)).replace("sgst",String.valueOf(sgst)).replace("amount",String.valueOf(amount));

	return stringToReturn;
    }

}
