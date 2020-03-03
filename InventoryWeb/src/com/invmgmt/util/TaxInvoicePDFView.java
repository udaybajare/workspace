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
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		TaxInvoiceDetails taxInvoiceDetails = (TaxInvoiceDetails) model.get("taxInvoiceDetails");

		response.setHeader("Content-Disposition",
				"attachment; filename=" + taxInvoiceDetails.getTaxInvoiceNo() + ".pdf");

		PDDocument document = null;

		try {
			File file = ResourceUtils.getFile("classpath:InvoiceTemplateUpdated.pdf");
			document = PDDocument.load(file);

			int rateInt = (int) Double.parseDouble(taxInvoiceDetails.getRate());
			int cGst = rateInt * 9 / 100;

			String[] address = taxInvoiceDetails.getAddressedto1().split(",");

			String amtWrd1 = "";
			String amtWrd2 = "";
			if (taxInvoiceDetails.getAmtInwrd1().length() > 40) {
				amtWrd1 = taxInvoiceDetails.getAmtInwrd1().substring(0, 39);
				amtWrd2 = taxInvoiceDetails.getAmtInwrd1().substring(40);
			} else {
				amtWrd1 = taxInvoiceDetails.getAmtInwrd1();
			}
			String total = String.valueOf(rateInt + cGst * 2);
			String cGstString = String.valueOf(cGst);

			if (address.length > 0)
				document = principal.replaceText(document, "ADDR1AAAAAAAAAAAAAAAA", address[0]);
			else
				document = principal.replaceText(document, "ADDR1AAAAAAAAAAAAAAAA", " ");

			if (address.length > 1)
				document = principal.replaceText(document, "ADDR2AAAAAAAAAAAAAAAA", address[1]);
			else
				document = principal.replaceText(document, "ADDR2AAAAAAAAAAAAAAAA", " ");

			if (address.length > 2)
				document = principal.replaceText(document, "ADDR3AAAAAAAAAAAAAAAA", address[2]);
			else
				document = principal.replaceText(document, "ADDR3AAAAAAAAAAAAAAAA", " ");

			if (address.length > 3)
				document = principal.replaceText(document, "ADDR4AAAAAAAAAAAAAAAA", address[3]);
			else
				document = principal.replaceText(document, "ADDR4AAAAAAAAAAAAAAAA", " ");

			document = principal.replaceText(document, "invoiceNoAAA", taxInvoiceDetails.getInvoiceNo());
			document = principal.replaceText(document, "date",
					LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			document = principal.replaceText(document, "orderNoAAA", taxInvoiceDetails.getOrderNo());
			document = principal.replaceText(document, "orderDate", taxInvoiceDetails.getOrderDate());
			document = principal.replaceText(document, "contactName", taxInvoiceDetails.getContactName());
			document = principal.replaceText(document, "mobileNo", taxInvoiceDetails.getMobileNo());
			document = principal.replaceText(document, "hsnOrSac", taxInvoiceDetails.getHsnOrSac());
			document = principal.replaceText(document, "rate", taxInvoiceDetails.getRate());

			if (taxInvoiceDetails.getMiscChargesDesc() != null) {
				document = principal.replaceText(document, "SRNO", "1");
				document = principal.replaceText(document, "MQTY", "1");
				document = principal.replaceText(document, "MUN", "Job");
				document = principal.replaceText(document, "MISCELLANEOUSDESCRIPTION Charges",
						taxInvoiceDetails.getMiscChargesDesc());
				document = principal.replaceText(document, "MISCRATE", taxInvoiceDetails.getMiscCharges());
				document = principal.replaceText(document, "MISCAMT", taxInvoiceDetails.getMiscCharges());
			} else {
				document = principal.replaceText(document, "SRNO", "");
				document = principal.replaceText(document, "MQTY", "");
				document = principal.replaceText(document, "MUN", "");
				document = principal.replaceText(document, "MISCELLANEOUSDESCRIPTION Charges", "");
				document = principal.replaceText(document, "MISCRATE", "");
				document = principal.replaceText(document, "MISCAMT", "");
			}

			document = principal.replaceText(document, "amtInwrd1", amtWrd1);
			document = principal.replaceText(document, "amtInwrd2", "-" + amtWrd2);
			document = principal.replaceText(document, "cGst", cGstString);
			document = principal.replaceText(document, "SUBTOTAL", String.valueOf(rateInt));
			document = principal.replaceText(document, "totalAMT", total);
			document = principal.replaceText(document, "GSTNUMBERAAAAAAAA",
					null != taxInvoiceDetails.getGstNo() ? taxInvoiceDetails.getGstNo() : "-");
			document = principal.replaceText(document, "PROJECTNAME", taxInvoiceDetails.getProjectName() + " )");
			document = principal.replaceText(document, "INOICETYPE", taxInvoiceDetails.getInvoiceType() + " )");

			document.save(response.getOutputStream());
			document.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
