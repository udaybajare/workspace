package com.invmgmt.util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;

import com.invmgmt.dao.InventoryDao;
import com.invmgmt.entity.AccessoryDetails;
import com.invmgmt.entity.Inventory;
import com.invmgmt.entity.InventorySpec;

@ManagedBean
public class InventoryUtils {

	@Autowired
	InventoryDao inventoryDao;

	public String createDescriptionLine(String material, String type, String inventory, String classOrgrade) {
		String templateDesc = "material type inventory of Grade(OR Class) as classOrgrade";

		String description = templateDesc;

		description = description.replace("material", material);

		if (!type.equalsIgnoreCase("Default"))
			description = description.replace("type", type);

		description = description.replace("inventory", inventory);
		description = description.replace("classOrgrade", classOrgrade);

		return description;
	}

	public List<InventorySpec> createInventorySpecList(String[] inventoryName, String[] material, String[] type,
			String[] manifMethod, String[] gradeOrClass, String[] ends, String[] size, String[] project,
			String[] status) {
		ArrayList<InventorySpec> inventorySpecList = new ArrayList<InventorySpec>();

		for (int i = 0; i < inventoryName.length; i++) {
			inventorySpecList.add(new InventorySpec(inventoryName[i], material[i], type[i], manifMethod[i],
					gradeOrClass[i], ends[i], size[i], project[i], status[i]));
		}

		return inventorySpecList;
	}

	public String createInventoryRowTable(Inventory inv) {
		return createInventoryRowTable(inv, false, null);
	}
	public String createInventoryRowTable(Inventory inv, boolean needProjectList, String index) {
		
		String inventoryId = "inventoryNo";
		
		if (null != index)
		{
			inventoryId = inventoryId + index;	
		}
		
		String template = "<tr id=\""+inventoryId+"\"><td></td>"
				+ "<td>InventoryVal</td>    <td>MaterialVal</td>    <td>TypeVal</td>"
				+ "<td>ManifMethodVal</td><td>gradeOrClassVal</td>    <td>endsVal</td>" + "<td>sizeVal</td>"
				+ "<td><input type=\"text\" name=\"quantity\" value=\"availableQuantity\" ></td>"
				+ "<td>purchaseRateVal</td>"
				+ (needProjectList? "<td><select class='form-control currentProjectList' name='project' name='projectName' ><option></option></td>" : "<td>projectVal</td>")
				+ "<td>locationVal</td>"
				+ "<td>"
				+ (needProjectList? "<input type=\"submit\" class=\"btn btn-default \" onClick=\"statusTo('"+inventoryId+"');\"  value=\"Assign\">" : "<select class=\"form-control statusTo\" name=\"statusTo\" > <option></option> <option value=\"release\">Release</option>" + "<option value=\"consumed\">Consumed</option></select>")
				+ "</td>" 
				+ "<input type=\"hidden\" name=\"inventoryStr\" value=\"InventoryVal\" >"
				+ "<input type=\"hidden\" name=\"materialStr\" value=\"MaterialVal\" >"
				+ "<input type=\"hidden\" name=\"typeStr\" value=\"TypeVal\" >"
				+ "<input type=\"hidden\" name=\"manifMethodStr\" value=\"ManifMethodVal\" >"
				+ "<input type=\"hidden\" name=\"gradeOrClassStr\" value=\"gradeOrClassVal\" >"
				+ "<input type=\"hidden\" name=\"endsStr\" value=\"endsVal\" >"
				+ "<input type=\"hidden\" name=\"sizeStr\" value=\"sizeVal\" >"
				+ "<input type=\"hidden\" name=\"purchaseRateStr\" value=\"purchaseRateVal\" >"
				+ "<input type=\"hidden\" name=\"projectStr\" value=\"projectVal\" >"
				+ "<input type=\"hidden\" name=\"locationStr\" value=\"locationVal\" > "
				+ "<input type=\"hidden\" name=\"status\" value=\"assigned\"> "
				+ "</tr>";

		String rowToReturn = template;
		rowToReturn = rowToReturn.replace("InventoryVal", inv.getInventorySpec().getInventoryName());
		rowToReturn = rowToReturn.replace("MaterialVal", inv.getInventorySpec().getMaterial());
		rowToReturn = rowToReturn.replace("TypeVal", inv.getInventorySpec().getType());
		rowToReturn = rowToReturn.replace("ManifMethodVal", inv.getInventorySpec().getManifMethod());
		rowToReturn = rowToReturn.replace("gradeOrClassVal", inv.getInventorySpec().getGradeOrClass());
		rowToReturn = rowToReturn.replace("endsVal", inv.getInventorySpec().getEnds());
		rowToReturn = rowToReturn.replace("sizeVal", inv.getInventorySpec().getSize());

		// check assigned quantity
		int availableQuantity = inventoryDao.getQuantityByStatus(inv, inv.getInventorySpec().getStatus());

		rowToReturn = rowToReturn.replace("availableQuantity", Integer.toString(availableQuantity));

		// Purchse Rate
		rowToReturn = rowToReturn.replace("purchaseRateVal", String.valueOf(inventoryDao.getPurchaseRate(inv)));
		rowToReturn = rowToReturn.replace("projectVal", inv.getInventorySpec().getAssignedProject());
		rowToReturn = rowToReturn.replace("locationVal", inv.getLocation());
		return rowToReturn;
	}

	public String createAccessoryRowTable(AccessoryDetails accessoryDetails) {
		String template = "<tr><form action=\"release\" method=\"POST\" ><td></td>" 
				+ "<td>desc1Val</td>"
				+ "<td>desc2Val</td>"
				+ "<td>desc3Val</td>"
				+ "<td>desc4Val</td>"
				+ "<td>desc5Val</td>"
				+ "<td>accessoryNameVal</td>"
				+ "<td>-</td>"				
				+ "<td><input type=\"text\" name=\"quantity\" value=\"availableQuantity\" ></td>"
				+ "<td>-</td>"
				+ "<td>projectVal</td>"
				+ "<td>locationVal</td>"
				+ "<td><select class=\"form-control accessoryStatusTo\" name=\"accessoryStatusTo\" >" + "<option></option>"
				+ "<option value=\"release\">Release</option>" + "<option value=\"consumed\">Consumed</option>"
				+ "</select></td>" 
				+ "<input type=\"hidden\" name=\"desc1\" value=\"desc1Val\" >"
				+ "<input type=\"hidden\" name=\"desc2\" value=\"desc2Val\" >"
				+ "<input type=\"hidden\" name=\"desc3\" value=\"desc3Val\" >"
				+ "<input type=\"hidden\" name=\"desc4\" value=\"desc4Val\" >"
				+ "<input type=\"hidden\" name=\"desc5\" value=\"desc5Val\" >"
				+ "<input type=\"hidden\" name=\"accessoryName\" value=\"accessoryNameVal\" >"
				+ "<input type=\"hidden\" name=\"project\" value=\"projectVal\" >"
				+ "<input type=\"hidden\" name=\"location\" value=\"locationVal\" >";

		String rowToReturn = template;

		rowToReturn = rowToReturn.replace("desc1Val", accessoryDetails.getDesc1());
		rowToReturn = rowToReturn.replace("desc2Val", accessoryDetails.getDesc2());
		rowToReturn = rowToReturn.replace("desc3Val", accessoryDetails.getDesc3());
		rowToReturn = rowToReturn.replace("desc4Val", accessoryDetails.getDesc4());
		rowToReturn = rowToReturn.replace("desc5Val", accessoryDetails.getDesc5());
		rowToReturn = rowToReturn.replace("accessoryNameVal", accessoryDetails.getAccessoryName());
		rowToReturn = rowToReturn.replace("availableQuantity", accessoryDetails.getQuantity());
		rowToReturn = rowToReturn.replace("projectVal", accessoryDetails.getAssignedProject());
		rowToReturn = rowToReturn.replace("locationVal", accessoryDetails.getLocation());

		return rowToReturn;

	}

	public InventorySpec copyInventorySpec(InventorySpec toCopy) {
		InventorySpec toReturn = new InventorySpec();

		toReturn.setAssignedProject(toCopy.getAssignedProject());
		toReturn.setEnds(toCopy.getEnds());
		toReturn.setGradeOrClass(toCopy.getGradeOrClass());
		toReturn.setInventoryName(toCopy.getInventoryName());
		toReturn.setManifMethod(toCopy.getManifMethod());
		toReturn.setMaterial(toCopy.getMaterial());
		toReturn.setSize(toCopy.getSize());
		toReturn.setStatus(toCopy.getStatus());
		toReturn.setType(toCopy.getType());

		return toReturn;
	}
	/*
	 * public static void main(String[] args) { String home =
	 * System.getProperty("user.home"); System.out.println(home); }
	 */
}
