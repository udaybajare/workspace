package com.invmgmt.entity;

import java.io.File;
import java.io.IOException;

import javax.annotation.ManagedBean;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.invmgmt.dao.TaxInvoiceDetailsDao;
import com.invmgmt.util.EmailUtils;
import com.invmgmt.util.Principal;

@ManagedBean
public class TaxInvoiceGenerator {

	@Autowired
	TaxInvoiceDetailsDao taxInvoiceDetailsDao;

	@Autowired
	Principal invoiceGenerator;

	@Autowired
	EmailUtils emailUtils;

	public void generateAndSendTaxInvoice(TaxInvoiceDetails taxInvoiceDetails, String sender) {

		invoiceGenerator.createInvoice(taxInvoiceDetails);
		emailUtils.sendMessageWithAttachment(sender, taxInvoiceDetails.getEmailAddress(),
				taxInvoiceDetails.getTaxInvoiceNo(), false, taxInvoiceDetails.getInvoiceNo());

		taxInvoiceDetailsDao.saveTaxIvoice(taxInvoiceDetails);
		try {
			FileUtils.forceDelete(new File(System.getProperty("java.io.tmpdir") + "/"
					+ taxInvoiceDetails.getInvoiceNo().replace("/", "_") + ".pdf"));
			FileUtils.forceDelete(new File(System.getProperty("java.io.tmpdir") + "/"
					+ taxInvoiceDetails.getInvoiceNo().replace("/", "_") + "_Annexture.xls"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private final String TAX_INVOICE_ATTACHMENT_NAME = "TaxInvoice.pdf";
}
