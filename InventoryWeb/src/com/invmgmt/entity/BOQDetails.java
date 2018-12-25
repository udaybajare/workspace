package com.invmgmt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BOQDetails")
public class BOQDetails {

	private String boqName;
	private String standardType;
	private String grade;
	private String schedule;
	private String materialSpec;
	private String ends;
	private String size;
	private String quantity;
	private String supplyRate;
	private String erectionRate;
	private String supplyAmount;
	private String erectionAmount;

	public BOQDetails()
	{
		
	}
	
	public BOQDetails(String projectId, String boqName, String standardType, String grade, String schedule, String materialSpec, String ends,
			String size, String quantity, String supplyRate, String erectionRate, String supplyAmount,
			String erectionAmount) {
		super();
		this.projectId = projectId;
		this.boqName = boqName;
		this.standardType = standardType;
		this.grade = grade;
		this.schedule = schedule;
		this.materialSpec = materialSpec;
		this.ends = ends;
		this.size = size;
		this.quantity = quantity;
		this.erectionAmount = erectionAmount;
		this.supplyAmount = supplyAmount;
		this.erectionRate = erectionRate;
		this.supplyRate = supplyRate;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	private String projectId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBoqName() {
		return boqName;
	}

	public void setBoqName(String boqName) {
		this.boqName = boqName;
	}
	
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getErectionAmount() {
		return erectionAmount;
	}

	public void setErectionAmount(String erectionAmount) {
		this.erectionAmount = erectionAmount;
	}

	public String getStandardType() {
		return standardType;
	}
	

	public void setStandardType(String standardType) {
		this.standardType = standardType;
	}


	public String getGrade() {
		return grade;
	}


	public void setGrade(String grade) {
		this.grade = grade;
	}


	public String getSchedule() {
		return schedule;
	}


	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}


	public String getMaterialSpec() {
		return materialSpec;
	}


	public void setMaterialSpec(String materialSpec) {
		this.materialSpec = materialSpec;
	}


	public String getEnds() {
		return ends;
	}


	public void setEnds(String ends) {
		this.ends = ends;
	}


	public String getSize() {
		return size;
	}


	public void setSize(String size) {
		this.size = size;
	}

	public String getSupplyRate() {
		return supplyRate;
	}

	public void setSupplyRate(String supplyRate) {
		this.supplyRate = supplyRate;
	}
	
	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getErectionRate() {
		return erectionRate;
	}

	public void setErectionRate(String erectionRate) {
		this.erectionRate = erectionRate;
	}

	public String getSupplyAmount() {
		return supplyAmount;
	}

	public void setSupplyAmount(String supplyAmount) {
		this.supplyAmount = supplyAmount;
	}

	public String getErecionAmount() {
		return erectionAmount;
	}

	public void setErecionAmount(String erectionAmount) {
		this.erectionAmount = erectionAmount;
	}	
}
