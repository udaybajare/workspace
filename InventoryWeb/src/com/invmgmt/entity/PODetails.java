package com.invmgmt.entity;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PODetails {

    @Id
    String poNumber;    
    String vendorName;
    String location;
    String contactName;
    String contactNumber;
    String contactEmail;
    
    @Column(length = 2046)
    String[] term;
    
    @Column(length = 8184)
    String lineItem;
    
    @Column(length = 8184)
    String lineItemNoHtml;
    
    String poDate;
    String projectId;
        
    
    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public String getPoDate() {
        return poDate;
    }

    public void setPoDate(String poDate) {
        this.poDate = poDate;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getVendorName() {
	return vendorName;
    }

    public void setVendorName(String vendorName) {
	this.vendorName = vendorName;
    }

    public String getLocation() {
	return location;
    }

    public void setLocation(String location) {
	this.location = location;
    }

    public String getContactName() {
	return contactName;
    }

    public void setContactName(String contactName) {
	this.contactName = contactName;
    }

    public String getContactNumber() {
	return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
	this.contactNumber = contactNumber;
    }

    public String getContactEmail() {
	return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
	this.contactEmail = contactEmail;
    }

    public String[] getTerm() {
	return term;
    }

    public void setTerm(String[] term) {
	this.term = term;
    }

    public String getLineItem() {
	return lineItem;
    }

    public void setLineItem(String lineItem) {
	this.lineItem = lineItem;
    }

    public String getLineItemNoHtml() {
	return lineItemNoHtml;
    }

    public void setLineItemNoHtml(String lineItemNoHtml) {
	this.lineItemNoHtml = lineItemNoHtml;
    }
    
    public PODetails() {
    }

    public PODetails(String poNumber, String vendorName, String location, String contactName, String contactNumber,
	    String contactEmail, String[] term, String lineItem, String lineItemNoHtml, String poDate, String projectId) {
	super();
	this.poNumber = poNumber;
	this.vendorName = vendorName;
	this.location = location;
	this.contactName = contactName;
	this.contactNumber = contactNumber;
	this.contactEmail = contactEmail;
	this.term = term;
	this.lineItem = lineItem;
	this.lineItemNoHtml = lineItemNoHtml;
	this.poDate = poDate;
	this.projectId = projectId;
    }

	@Override
	public String toString() {
		return "PODetails [poNumber=" + poNumber + ": vendorName=" + vendorName + ": location=" + location
				+ ": contactName=" + contactName + ": contactNumber=" + contactNumber + ": contactEmail=" + contactEmail
				+ ": term=" + Arrays.toString(term) + ": lineItem=" + lineItem + ": lineItemNoHtml=" + lineItemNoHtml
				+ ": poDate=" + poDate + ": projectId=" + projectId + "]";
	}
    
    
    
}
