package com.invmgmt.util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;

import com.invmgmt.dao.InventoryDao;
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
	
	if(!type.equalsIgnoreCase("Default"))
	description = description.replace("type", type);
	
	description = description.replace("inventory", inventory);
	description = description.replace("classOrgrade", classOrgrade);
	
	return description;
    }
    
    public List<InventorySpec> createInventorySpecList(String[] inventoryName, String[] material, String[] type, String[] manifMethod, String[] gradeOrClass,
	 	String[] ends, String[] size)
    {
	ArrayList<InventorySpec> inventorySpecList = new ArrayList<InventorySpec>();
	
	for(int i=0;i<inventoryName.length;i++)
	{
	    inventorySpecList.add(new InventorySpec( inventoryName[i],  material[i],  type[i],  manifMethod[i],  gradeOrClass[i],
		 	 ends[i], size[i]));
	}
	
	return inventorySpecList;
    }
    
    public String createInventoryRowTable(Inventory inv) {
	String template = "<tr>" + "<td></td><td>Inventory</td>" + "    <td>Material</td>" + "    <td>Type</td>"
		+ "    <td>ManifMethod</td>" +"<td>gradeOrClass</td>"+ "    <td>ends</td>" + "    <td>size</td>"
		+ "    <td>availableQuantity</td>" + "    <td>purchaseRate</td>" + "<td>project</td>" + "<td>location</td>";

	String rowToReturn = template;
	rowToReturn = rowToReturn.replace("Inventory", inv.getInventorySpec().getInventoryName());
	rowToReturn = rowToReturn.replace("Material", inv.getInventorySpec().getMaterial());
	rowToReturn = rowToReturn.replace("Type", inv.getInventorySpec().getType());
	rowToReturn = rowToReturn.replace("ManifMethod", inv.getInventorySpec().getManifMethod());
	rowToReturn = rowToReturn.replace("gradeOrClass", inv.getInventorySpec().getGradeOrClass());
	rowToReturn = rowToReturn.replace("ends", inv.getInventorySpec().getEnds());
	rowToReturn = rowToReturn.replace("size", inv.getInventorySpec().getSize());

	// check available quantity
	int availableQuantity = inventoryDao.getAvailableQuantity(inv);

	rowToReturn = rowToReturn.replace("availableQuantity", Integer.toString(availableQuantity));

	// Purchse Rate
	rowToReturn = rowToReturn.replace("purchaseRate", String.valueOf(inventoryDao.getPurchaseRate(inv)));
	rowToReturn = rowToReturn.replace("project", inv.getAssignedProject());
	rowToReturn = rowToReturn.replace("location", inv.getLocation());
	return rowToReturn;
    }
    
    /*public static void main(String[] args) {
	String home = System.getProperty("user.home");
	System.out.println(home);
    }*/
}
