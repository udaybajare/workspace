package com.invmgmt.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Embeddable
public class InventorySpec implements Serializable {
		
	@Column(name="inventoryName")
	public String inventoryName;
	
	@Column(name="material")
	public String material;
	
	@Column(name="type")
	public String type;
	
	@Column(name="manifMethod")
	public String manifMethod;
	
	@Column(name="gradeOrClass")
	public String gradeOrClass;
	
	@Column(name="size")
	public String size;

	@Column(name="ends")
	public String ends;
	
	public InventorySpec()
	{
		
	}
	
	public InventorySpec(String inventoryName, String material, String type, String manifMethod, String gradeOrClass,
			 String ends, String size) {
		super();
		this.inventoryName = inventoryName;
		this.material = material;
		this.type = type;
		this.manifMethod = manifMethod;
		this.gradeOrClass = gradeOrClass;
		this.ends = ends;
		this.size = size;
	}



	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (!(o instanceof InventorySpec))
			return false;
		
		InventorySpec inv = (InventorySpec) o;
		
		return Objects.equals(getInventoryName(), inv.getInventoryName())
				&& Objects.equals(getMaterial(), inv.getMaterial())
				&& Objects.equals(getType(), inv.getType())
				&& Objects.equals(getGradeOrClass(), inv.getGradeOrClass())
				&& Objects.equals(getSize(),inv.getSize());
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(getInventoryName(), getMaterial(), getGradeOrClass(), getType());
	}
	
	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
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

	public String getGradeOrClass() {
		return gradeOrClass;
	}

	public void setGradeOrClass(String gradeOrClass) {
		this.gradeOrClass = gradeOrClass;
	}

	public String getManifMethod() {
		return manifMethod;
	}

	public void setManifMethod(String manifMethod) {
		this.manifMethod = manifMethod;
	}

	public String getEnds() {
		return ends;
	}

	public void setEnds(String ends) {
		this.ends = ends;
	}

	
}
