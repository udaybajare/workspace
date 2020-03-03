package com.invmgmt.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.invmgmt.interfaces.BOQData;

@Entity
@Embeddable
@Table(name = "inventory")
public class Inventory implements Serializable, BOQData {

	@EmbeddedId
	private InventorySpec inventorySpec;

	private int inventoryRowId;

	@Column(name = "purchaseRate")
	private String purchaseRate;

	@Column(name = "quantity")
	private int quantity;

	@Column(name = "location")
	private String location;

	public Inventory(InventorySpec inventorySpec, String purchaseRate, int quantity, String location) {
		super();
		this.inventorySpec = inventorySpec;
		this.purchaseRate = purchaseRate;
		this.quantity = quantity;
		this.location = location;
	}

	public Inventory() {

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

	public String getPurchaseRate() {
		return this.purchaseRate;
	}

	public void setPurchaseRate(String purchaseRate) {
		this.purchaseRate = purchaseRate;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Inventory copyObject(Inventory invt) {
		Inventory toBeReturned = new Inventory();

		InventorySpec invSpecToBeReturned = invt.getInventorySpec();
		toBeReturned.setInventorySpec(invSpecToBeReturned);
		toBeReturned.setLocation(invt.getLocation());
		toBeReturned.setPurchaseRate(invt.getPurchaseRate());
		toBeReturned.setQuantity(invt.getQuantity());

		return toBeReturned;
	}

	@Override
	public String toString() {
		return "Inventory [inventorySpec=" + inventorySpec.toString() + "; inventoryRowId=" + inventoryRowId
				+ "; purchaseRate=" + purchaseRate + "; quantity=" + quantity + "; location=" + location + "]";
	}

}
