package com.invmgmt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Mappings {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int Id;
	
	@Column
	private String inventoryName;
	
	@Column
	private String material;
	
	@Column
	private String type;
	
	@Column
	private String classOrGrade;
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}

	public String getInventoryName() {
		return inventoryName;
	}
	public void setInventoryName(String inventoryName) {
		this.inventoryName = inventoryName;
	}
	
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getClassOrGrade() {
		return classOrGrade;
	}
	public void setClassOrGrade(String classOrGrade) {
		this.classOrGrade = classOrGrade;
	}
		
}
