package com.invmgmt.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity
public class PaymentDetails {

    @Id
    private String paymentId;
    private String taxInvoiceNumber;
    private String amount;
    private String dateReceived;
    private String projectId;

    public PaymentDetails() {

    }

    public PaymentDetails(String paymentId, String taxInvoiceNumber, String amount, String dateReceived,
	    String projectId) {
	super();
	this.paymentId = paymentId;
	this.taxInvoiceNumber = taxInvoiceNumber;
	this.amount = amount;
	this.dateReceived = dateReceived;
	this.projectId = projectId;
    }

    public String getPaymentId() {
	return paymentId;
    }

    public void setPaymentId(String paymentId) {
	this.paymentId = paymentId;
    }

    public String getTaxInvoiceNumber() {
	return taxInvoiceNumber;
    }

    public void setTaxInvoiceNumber(String taxInvoiceNumber) {
	this.taxInvoiceNumber = taxInvoiceNumber;
    }

    public String getAmount() {
	return amount;
    }

    public void setAmount(String amount) {
	this.amount = amount;
    }

    public String getDateReceived() {
	return dateReceived;
    }

    public void setDateReceived(String dateReceived) {
	this.dateReceived = dateReceived;
    }

    public String getProjectId() {
	return projectId;
    }

    public void setProjectId(String projectId) {
	this.projectId = projectId;
    }
}
