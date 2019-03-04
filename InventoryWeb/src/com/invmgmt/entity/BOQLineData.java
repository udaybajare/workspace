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
	
    public boolean isSameEntry(BOQLineData obj1, BOQLineData obj2) {
	boolean isEqual = false;

	if (obj1.getStdLine().equalsIgnoreCase(obj2.getStdLine())
		&& obj1.getEndsLine().equalsIgnoreCase(obj2.getEndsLine())
		&& obj1.getGrdLine().equalsIgnoreCase(obj2.getGrdLine())
		&& obj1.getMakesLine().equalsIgnoreCase(obj2.getMakesLine())
		&& obj1.getMaterial().equalsIgnoreCase(obj2.getMaterial())
		&& obj1.getSpecLine().equalsIgnoreCase(obj2.getSpecLine())) {
	    isEqual = true;
	}

	return isEqual;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == this)
	    return true;

	BOQLineData lineData = (BOQLineData) obj;

	return lineData.getEndsLine().equals(endsLine) && lineData.getGrdLine().equals(grdLine)
		&& lineData.getMakesLine().equals(makesLine) && lineData.getMaterial().equals(material)
		&& lineData.getSpecLine().equals(specLine) && lineData.getStdLine().equals(stdLine);
    }
    
    @Override
    public int hashCode() {
	int result = 17;
	result = 31 * result + endsLine.hashCode();
	result = 31 * result + grdLine.hashCode();
	result = 31 * result + makesLine.hashCode();
	result = 31 * result + material.hashCode();
	result = 31 * result + specLine.hashCode();
	result = 31 * result + stdLine.hashCode();
	return result;
    }
}
