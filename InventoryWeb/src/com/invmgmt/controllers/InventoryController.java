package com.invmgmt.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.invmgmt.dao.InventoryDao;
import com.invmgmt.entity.Inventory;
import com.invmgmt.entity.InventorySpec;
import com.invmgmt.util.InventoryUtils;

@Controller
@EnableWebMvc
public class InventoryController {

    @Autowired
    private InventoryDao inventoryDao;

    @Autowired
    private InventoryUtils inventoryUtils;

    private static final String updateViewName = "updateInventory";

    @RequestMapping(value = "/updateInventoryForm", method = RequestMethod.GET)
    protected ModelAndView updateInventoryForm() {
	ModelAndView view = new ModelAndView(updateViewName);

	return view;
    }

    @RequestMapping(value = "/updateInventory", method = RequestMethod.POST)
    protected ModelAndView updateInventory(String[] inventoryName, String[] material, String[] type,
	    String[] manifMethod, String[] gradeOrClass, String[] ends, String[] size, int[] quantity,
	    String[] purchaseRate, String[] project, String[] location) {
	ModelAndView view = new ModelAndView("challan");
	StringBuffer lineItemData = new StringBuffer();

	List<InventorySpec> inventorySpec = inventoryUtils.createInventorySpecList(inventoryName, material, type,
		manifMethod, gradeOrClass, ends, size);

	for (int i = 0; i < inventorySpec.size(); i++) {
	    Inventory inventory = new Inventory();
	    inventory.setInventorySpec(inventorySpec.get(i));
	    inventory.setPurchaseRate(purchaseRate[i]);
	    inventory.setQuantity(quantity[i]);
	    inventory.setAssignedProject(project[i]);
	    inventory.setLocation(location[i]);

	    inventoryDao.saveInventory(inventory);

	    String description = inventoryUtils.createDescriptionLine(material[i], type[i], inventoryName[i],
		    gradeOrClass[i]);
	    String lineItem = getInventoryDetailsRow(String.valueOf(i + 1), size[i], description, String.valueOf(quantity[i]), "NB");
	    lineItemData.append(lineItem);

	}
	
	view.addObject("itemList", lineItemData);
	return view;
    }

    private String getInventoryDetailsRow(String sr_no, String size, String description, String quantity, String unit) {
	String template = "<TR>                                                       "
		+ "	<TD class=\"tr4 td5\"><P class=\"p1 ft1\">sr_no</P></TD> "
		+ "	<TD class=\"tr4 td6\"><P class=\"p3 ft1\">size</P></TD> "
		+ "	<TD class=\"tr4 td7\"><P class=\"p3 ft1\">Description</P></TD> "
		+ "	<TD class=\"tr4 td18\"><P class=\"p3 ft1\">quantity</P></TD>"
		+ "	<TD class=\"tr4 td9\"><P class=\"p3 ft1\">unit</P></TD> "
		+ "</TR>                                                      ";

	String stringToReturn = template.replace("sr_no", sr_no).replace("size", size)
		.replace("Description", description).replace("quantity", quantity).replace("unit", unit);

	return stringToReturn;
    }
}
