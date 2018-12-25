package com.invmgmt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BOQLineData")
public class BOQLineData {

	@Id
	@Column(nullable=false)
	private String material;
	private String stdLine;
	private String specLine;
	private String grdLine;
	private String endsLine;
	private String makesLine;
	
	public BOQLineData()
	{}	
	
	public BOQLineData(String material, String stdLine, String specLine, String grdLine, String endsLine, String makesLine) {
		super();
		this.material = material;
		this.stdLine = stdLine;
		this.specLine = specLine;
		this.grdLine = grdLine;
		this.endsLine = endsLine;
		this.makesLine = makesLine;
	}
	
	
	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}	
	public String getStdLine() {
		return stdLine;
	}
	public void setStdLine(String stdLine) {
		this.stdLine = stdLine;
	}
	public String getSpecLine() {
		return specLine;
	}
	public void setSpecLine(String specLine) {
		this.specLine = specLine;
	}
	public String getGrdLine() {
		return grdLine;
	}
	public void setGrdLine(String grdLine) {
		this.grdLine = grdLine;
	}
	public String getEndsLine() {
		return endsLine;
	}
	public void setEndsLine(String endsLine) {
		this.endsLine = endsLine;
	}
	public String getMakesLine() {
		return makesLine;
	}
	public void setMakesLine(String makesLine) {
		this.makesLine = makesLine;
	}
	
}
