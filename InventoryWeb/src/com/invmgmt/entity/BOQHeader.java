package com.invmgmt.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.invmgmt.interfaces.BOQData;

@Entity
public class BOQHeader implements BOQData {

    @Id
    private String boqName;
    
    private String client;
    private String site;
    private String project;
    private String dName;
    private String utility;
    private String pressure;
    private String temp;
    private String dNo;
    
    public BOQHeader()
    {
	
    }
    
    public BOQHeader(String client, String site, String project, String dName, String utility, String pressure,
	    String temp, String dNo, String boqName) {
	super();
	this.boqName = boqName;
	this.client = client;
	this.site = site;
	this.project = project;
	this.dName = dName;
	this.utility = utility;
	this.pressure = pressure;
	this.temp = temp;
	this.dNo = dNo;
    }
    public String getClient() {
        return client;
    }
    public void setClient(String client) {
        this.client = client;
    }
    public String getSite() {
        return site;
    }
    public void setSite(String site) {
        this.site = site;
    }
    public String getProject() {
        return project;
    }
    public void setProject(String project) {
        this.project = project;
    }
    public String getdName() {
        return dName;
    }
    public void setdName(String dName) {
        this.dName = dName;
    }
    public String getUtility() {
        return utility;
    }
    public void setUtility(String utility) {
        this.utility = utility;
    }
    public String getPressure() {
        return pressure;
    }
    public void setPressure(String pressure) {
        this.pressure = pressure;
    }
    public String getTemp() {
        return temp;
    }
    public void setTemp(String temp) {
        this.temp = temp;
    }
    public String getdNo() {
        return dNo;
    }
    public void setdNo(String dNo) {
        this.dNo = dNo;
    }
    public String getBoqName() {
        return boqName;
    }
    public void setBoqName(String boqName) {
        this.boqName = boqName;
    }
    
}
