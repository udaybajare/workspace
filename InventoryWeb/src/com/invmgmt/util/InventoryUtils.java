package com.invmgmt.util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;

import com.invmgmt.entity.InventorySpec;

@ManagedBean
public class InventoryUtils {

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
}
