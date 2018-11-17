package com.invmgmt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.invmgmt.dao.InventoryDao;
import com.invmgmt.entity.Inventory;
import com.invmgmt.entity.InventorySpec;

@Controller
@EnableWebMvc
public class InventoryController {

	@Autowired
	private InventoryDao inventoryDao;
	
	private static final String updateViewName = "updateInventory";

	@RequestMapping(value = "/updateInventoryForm", method = RequestMethod.GET)
	protected ModelAndView updateInventoryForm() {
		ModelAndView view = new ModelAndView(updateViewName);

		return view;
	}

	@RequestMapping(value = "/updateInventory", method = RequestMethod.POST)
	protected ModelAndView updateInventory(InventorySpec inventorySpec,
			@RequestParam(value = "quantity", required = true) int quantity,
			@RequestParam(value = "purchaseRate", required = true) String purchaseRate) {
		ModelAndView view = new ModelAndView();

		Inventory inventory = new Inventory();
		
		inventory.setInventorySpec(inventorySpec);
		inventory.setPurchaseRate(purchaseRate);
		inventory.setQuantity(quantity);
		
		inventoryDao.saveInventory(inventory);
		
		return view;
	}
}
