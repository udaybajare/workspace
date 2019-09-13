package com.invmgmt.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity
public class AccessoryDetails implements Serializable {
    
    @Id
    private String accessoryName;
    @Id
    private String desc1;
    @Id
    private String desc2;
    @Id
    private String desc3;
    @Id
    private String desc4;
    @Id
    private String desc5;
    
    private String assignedProject;
    
    private String location;

    public AccessoryDetails()
    {
	
    }
    
    public AccessoryDetails(String accessoryName, String desc1, String desc2, String desc3, String desc4, String desc5,
	    String assignedProject, String location) {
	super();
	this.accessoryName = accessoryName;
	this.desc1 = desc1;
	this.desc2 = desc2;
	this.desc3 = desc3;
	this.desc4 = desc4;
	this.desc5 = desc5;
	this.assignedProject = assignedProject;
	this.location = location;
    }

    public String getAccessoryName() {
        return accessoryName;
    }

    public void setAccessoryName(String accessoryName) {
        this.accessoryName = accessoryName;
    }

    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }

    public String getDesc2() {
        return desc2;
    }

    public void setDesc2(String desc2) {
        this.desc2 = desc2;
    }

    public String getDesc3() {
        return desc3;
    }

    public void setDesc3(String desc3) {
        this.desc3 = desc3;
    }

    public String getDesc4() {
        return desc4;
    }

    public void setDesc4(String desc4) {
        this.desc4 = desc4;
    }

    public String getDesc5() {
        return desc5;
    }

    public void setDesc5(String desc5) {
        this.desc5 = desc5;
    }

    public String getAssignedProject() {
        return assignedProject;
    }

    public void setAssignedProject(String assignedProject) {
        this.assignedProject = assignedProject;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
        
}
