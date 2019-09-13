package com.invmgmt.util;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.pdmodel.PDDocument;
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

	response.setHeader("Content-Disposition", "attachment; filename=" + poDetails.getPoNumber() + ".pdf");

	PDDocument document = null;

	try {
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
	    document.close();
	} catch (Exception ex) {
	    ex.printStackTrace();
	}

    }
}
