package com.invmgmt.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.invmgmt.dao.AccessoryDetailsDao;
import com.invmgmt.dao.BillDetailsDao;
import com.invmgmt.dao.ChallanDao;
import com.invmgmt.dao.InventoryDao;
import com.invmgmt.dao.ProjectDao;
import com.invmgmt.entity.AccessoryDetails;
import com.invmgmt.entity.BillDetails;
import com.invmgmt.entity.ChallanDetails;
import com.invmgmt.entity.Inventory;
import com.invmgmt.entity.InventorySpec;
import com.invmgmt.entity.Project;
import com.invmgmt.entity.TaxInvoiceDetails;
import com.invmgmt.entity.TaxInvoiceGenerator;
import com.invmgmt.util.InventoryUtils;

@Controller
@EnableWebMvc
public class InventoryController {

	@Autowired
	private InventoryDao inventoryDao;

	@Autowired
	private ChallanDao challanDao;

	@Autowired
	ProjectDao projectDao;

	@Autowired
	BillDetailsDao billDao;

	@Autowired
	AccessoryDetailsDao accessoryDetailsDao;

	@Autowired
	private InventoryUtils inventoryUtils;

	@Autowired
	TaxInvoiceGenerator taxInvoiceGenerator;

	private static final String updateViewName = "updateInventory";

	@RequestMapping(value = "/updateInventoryForm", method = RequestMethod.GET)
	protected ModelAndView updateInventoryForm() {
		ModelAndView view = new ModelAndView(updateViewName);

		ArrayList<Project> projectList = projectDao.getProject("projectName", "");
		StringBuffer projectNames = new StringBuffer();

		for (Project project : projectList) {
			projectNames.append(project.getProjectName() + ",");
		}

		view.addObject("projectNames", projectNames.toString());
		return view;
	}

	@RequestMapping(value = "/updateInventory", method = RequestMethod.POST)
	protected ModelAndView updateInventory(ChallanDetails challanDetails, String[] inventoryName, String[] material,
			String[] type, String[] manifMethod, String[] gradeOrClass, String[] ends, String[] size, int[] quantity,
			String[] rate, String[] project, String[] location, String[] status, String invoiceType,
			TaxInvoiceDetails taxInvoiceDetails, BillDetails billDetails, AccessoryDetails accessoryDetails,
			String generateChallan, String generateInvoice, String addAccessory, String addBillDetails) {

		System.out.println("generateChallan is " + generateChallan);
		System.out.println("generateInvoice is " + generateInvoice);
		System.out.println("addAccessory is " + addAccessory);
		System.out.println("addBillDetails is " + addBillDetails);

		ModelAndView view = new ModelAndView("challan");

		taxInvoiceDetails.setProjectName(project[0]);

		StringBuffer lineItemData = new StringBuffer();

		List<InventorySpec> inventorySpec = inventoryUtils.createInventorySpecList(inventoryName, material, type,
				manifMethod, gradeOrClass, ends, size);

		for (int i = 0; i < inventorySpec.size(); i++) {
			Inventory inventory = new Inventory();
			Inventory inventoryWOProj = null;
			inventory.setInventorySpec(inventorySpec.get(i));
			inventory.setPurchaseRate(rate[i]);
			inventory.setQuantity(quantity[i]);
			inventory.setAssignedProject(project[i]);
			inventory.setLocation(location[i]);
			inventory.setStatus(status[i]);

			int inventoryRowId = inventoryDao.isEntityPresent(inventory);

			System.out.println("inventoryRowId is : " + inventoryRowId);

			if (inventoryRowId != 0) {
				int qty = inventoryDao.getAvailableQuantity(inventory);

				System.out.println("project is : " + project[i]);
				if (project[i] != null && project[i] != "") {

					int assignedQty = inventoryDao.getAssignedQuantity(inventory);

					System.out.println("assignedQty is : " + assignedQty);

					if (status[i] != "delivered") {
						// update quantity from the existing inventory quantity
						// to reduce the quantity assigned in above code
						inventoryWOProj = inventory.copyObject(inventory);
						inventoryWOProj.setQuantity(qty - quantity[i]);
						inventoryWOProj.setAssignedProject("");
						inventoryWOProj.setStatus("available");
						inventoryWOProj.setInventoryRowId(inventoryRowId + 1);

						inventoryDao.saveInventory(inventoryWOProj);
					} else if (quantity[i] < assignedQty) {
						Inventory assignedInv = inventory.copyObject(inventory);

						assignedInv.setQuantity(assignedQty - quantity[i]);
						assignedInv.setStatus("assigned");
						assignedInv.setInventoryRowId(inventoryRowId + 1);
					}
				} else {
					inventory.setQuantity(quantity[i]);
				}

			} else {
				inventoryRowId = inventoryDao.getLatestInventoryEntryNo() + 1;
				inventory.setInventoryRowId(inventoryRowId);
			}

			inventoryDao.saveInventory(inventory);

			String projectName = project[0];
			String temp = projectName;

			String clientShortName = "";

			if (projectName.contains(" ") && projectName.length() >= 7) {
				clientShortName = projectName.substring(0, 3) + temp.substring(temp.indexOf(" "), 3);
			} else {
				clientShortName = projectName.substring(0, 3);
			}

			if (generateChallan.equals("1")) {
				// get projectId before saving challan details.
				int projectId = projectDao.getProjectId(project[0]);
				taxInvoiceDetails.setProjectId(projectId);
				challanDetails.setProjectId(String.valueOf(projectId));
				ArrayList<String> lrList = (ArrayList<String>) challanDao.getLrNo(String.valueOf(projectId));
				Collections.sort(lrList);

				String lastLrNo = "0";
				if (lrList.size() > 0) {
					lastLrNo = lrList.get(lrList.size() - 1);
					lastLrNo = lastLrNo.substring(lastLrNo.length() - 1);
				}
				int lrNoInt = Integer.parseInt(lastLrNo) + 1;
				String lrNumber = "HI/" + clientShortName + "/" + String.valueOf(lrNoInt);
				challanDetails.setInventoryRowId(inventoryRowId);
				challanDetails.setLrNumberDate(lrNumber);
				challanDao.saveChallan(challanDetails);

				if (generateInvoice.equals("1")) {
					String invoiceNo = "Invoice/" + clientShortName + "/" + String.valueOf(lrNoInt);
					taxInvoiceDetails.setInvoiceNo(invoiceNo);
					taxInvoiceDetails.setTaxInvoiceNo(invoiceNo);
					taxInvoiceGenerator.generateAndSendTaxInvoice(taxInvoiceDetails);
				}
			}
			if ("1".equals(addBillDetails) && billDetails.getBillNumber() != null
					&& billDetails.getBillNumber() != "") {
				billDao.saveBill(billDetails);
			}

			if ("1".equals(addAccessory) && accessoryDetails.getAccessoryName() != null
					&& accessoryDetails.getAccessoryName() != "") {
				accessoryDetailsDao.saveAccessory(accessoryDetails);
			}

			String description = inventoryUtils.createDescriptionLine(material[i], type[i], inventoryName[i],
					gradeOrClass[i]);
			String lineItem = getInventoryDetailsRow(String.valueOf(i + 1), size[i], description,
					String.valueOf(quantity[i]), "NB");
			lineItemData.append(lineItem);

		}

		view.addObject("itemList", lineItemData);

		view.addObject("challanNo", challanDetails.getInventoryRowId());
		view.addObject("date", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		view.addObject("consignee", challanDetails.getConsignee());
		view.addObject("from", challanDetails.getReceivedFrom());
		view.addObject("gstNo", challanDetails.getGstNo());
		view.addObject("lrNo", challanDetails.getLrNumberDate());
		view.addObject("poDate", challanDetails.getPoDate());
		view.addObject("poNo", challanDetails.getPoNo());
		view.addObject("transportMode", challanDetails.getTransportMode());
		view.addObject("vheicleNumber", challanDetails.getVheicleNumber());

		// Generate the Invoice if this is not a return i.e. taxInvoiceNo is
		// null.

		return view;
	}

	@RequestMapping(value = "/saveAccessory", method = RequestMethod.POST)
	private ModelAndView saveAccessory(AccessoryDetails accessoryDetails) {
		accessoryDetailsDao.saveAccessory(accessoryDetails);

		return new ModelAndView("redirect:/updateInventoryForm");
	}

	private String getInventoryDetailsRow(String sr_no, String size, String description, String quantity, String unit) {
		String template = "<tr><td>sr_no&emsp;size</td><td>Description</td><td>quantity</td><td>unit</td></tr>";

		String stringToReturn = template.replace("sr_no", sr_no).replace("size", size)
				.replace("Description", description).replace("quantity", quantity).replace("unit", unit);

		return stringToReturn;
	}
}
