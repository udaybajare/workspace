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
		
	@Column(name="standard_type")
	public String standardType;
	
	@Column(name="grade")
	public String grade;
	
	@Column(name="schedule")
	public String schedule;
	
	@Column(name="material_spec")
	public String materialSpec;
	
	@Column(name="ends")
	public String ends;
	
	@Column(name="size")
	public int size;

	public InventorySpec()
	{
		
	}
	
	public InventorySpec(
			String standardType,
			String grade,
			String schedule,
			String materialSpec,
			String ends,
			int size
			)
	{
		this.standardType = standardType;
		this.grade = grade;
		this.schedule = schedule;
		this.materialSpec = materialSpec;
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
		
		return Objects.equals(getStandardType(), inv.getMaterialSpec())
				&& Objects.equals(getGrade(), inv.getGrade())
				&& Objects.equals(getMaterialSpec(), inv.getMaterialSpec())
				&& Objects.equals(getSchedule(), inv.getSchedule());
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(getGrade(), getMaterialSpec(), getSchedule(), getStandardType());
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
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
