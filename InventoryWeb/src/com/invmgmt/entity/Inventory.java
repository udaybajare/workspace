package com.invmgmt.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.invmgmt.interfaces.BOQData;

@Entity
@Table(name="inventory")
public class Inventory implements Serializable, BOQData {

	@EmbeddedId
	private InventorySpec inventorySpec;
		
	private int inventoryRowId;
	
	@Column(name="purchaseRate")
	private String purchaseRate;

	@Column(name="quantity")
	private int quantity;
	
	@Id
	@Column(name="assignedProject")
	private String assignedProject;

	//@Id
	private String status;
	
	@Column(name="location")
	private String location;
	
	public Inventory(InventorySpec inventorySpec, String purchaseRate, int quantity, String assignedProject,
		String location, String status) {
	    super();
	    this.inventorySpec = inventorySpec;
	    this.purchaseRate = purchaseRate;
	    this.quantity = quantity;
	    this.assignedProject = assignedProject;
	    this.location = location;
	    this.status = status;
	}

	public Inventory() 
	{

	}

	public int getInventoryRowId() {
	    return inventoryRowId;
	}

	public void setInventoryRowId(int inventoryRowId) {
	    this.inventoryRowId = inventoryRowId;
	}

	public InventorySpec getInventorySpec() {
		return inventorySpec;
	}

	public void setInventorySpec(InventorySpec inventorySpec) {
		this.inventorySpec = inventorySpec;
	}

	public String getPurchaseRate()
	{
		return this.purchaseRate;
	}

	public void setPurchaseRate(String purchaseRate)
	{
		this.purchaseRate = purchaseRate;
	}
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
	
	public String getStatus()
	{
	    return this.status;
	}
	
	public void setStatus(String status)
	{
	    this.status = status;
	}

	public Inventory copyObject(Inventory invt)
	{
	    Inventory toBeReturned = new Inventory();
	    
	    InventorySpec invSpecToBeReturned = invt.getInventorySpec();
	    toBeReturned.setAssignedProject(invt.getAssignedProject());
	    toBeReturned.setInventorySpec(invSpecToBeReturned);
	    toBeReturned.setLocation(invt.getLocation());
	    toBeReturned.setPurchaseRate(invt.getPurchaseRate());
	    toBeReturned.setQuantity(invt.getQuantity());
	    toBeReturned.setStatus(invt.getStatus());
	    	    
	    return toBeReturned;
	}

	@Override
	public String toString() {
	    return "Inventory [inventorySpec=" + inventorySpec.toString() + "; inventoryRowId=" + inventoryRowId
		    + "; purchaseRate=" + purchaseRate + "; quantity=" + quantity + "; assignedProject="
		    + assignedProject + "; location=" + location + "; status=" + status + "]";
	}

}
