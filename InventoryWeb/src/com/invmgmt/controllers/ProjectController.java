package com.invmgmt.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.invmgmt.dao.AccessoryDetailsDao;
import com.invmgmt.dao.BOQDetailsDao;
import com.invmgmt.dao.InventoryDao;
import com.invmgmt.dao.MappingsDao;
import com.invmgmt.dao.PODetailsDao;
import com.invmgmt.dao.PaymentDetailsDao;
import com.invmgmt.dao.ProjectDao;
import com.invmgmt.dao.ProjectDetailsDao;
import com.invmgmt.dao.TaxInvoiceDetailsDao;
import com.invmgmt.dao.ValvesDao;
import com.invmgmt.dao.VendorDetailsDao;
import com.invmgmt.entity.BOQDetails;
import com.invmgmt.entity.Inventory;
import com.invmgmt.entity.PaymentDetails;
import com.invmgmt.entity.Project;
import com.invmgmt.entity.ProjectDetails;
import com.invmgmt.entity.TaxInvoiceDetails;
import com.invmgmt.entity.VendorDetails;
import com.invmgmt.excel.ExcelReader;
import com.invmgmt.util.InventoryUtils;

@Controller
@EnableWebMvc
public class ProjectController {

	@Autowired
	private ProjectDao projectDao;

	@Autowired
	private ProjectDetailsDao projectDetailsDao;

	@Autowired
	private PaymentDetailsDao paymentDetailsDao;

	@Autowired
	private BOQDetailsDao boqDao;

	@Autowired
	private TaxInvoiceDetailsDao taxInvoiceDao;

	@Autowired
	private PODetailsDao poDetailsDao;

	@Autowired
	private InventoryDao inventoryDao;

	@Autowired
	private AccessoryDetailsDao accessoryDetailsDao;

	@Autowired
	private ValvesDao valveDetailsDao;

	@Autowired
	VendorDetailsDao vendorDetailsDao;

	@Autowired
	InventoryUtils inventoryUtils;

	@Autowired
	MappingsDao mappingsDao;
	@Autowired
	ExcelReader reader;

	private static final String updateProjectviewName = "updatedDetails";
	private static final String searchProjectviewName = "searchProjectResult";

	@RequestMapping(value = "/createProject", method = RequestMethod.POST)
	protected ModelAndView saveProject(Project project, RedirectAttributes redirectAttrs) throws Exception {

		ArrayList<Project> existing = projectDao.getProject("projectName", project.getProjectName());

		if (existing.size() > 0) {
			ModelAndView projectDetails = new ModelAndView("redirect:/projectDetails");

			redirectAttrs.addAttribute("projectId", String.valueOf(existing.get(0).getProjectId()));
			redirectAttrs.addAttribute("projectName", existing.get(0).getProjectName());
			redirectAttrs.addAttribute("projectDesc", existing.get(0).getProjectDesc());

			return projectDetails;
		}

		int projId = projectDao.addProject(project);
		StringBuilder items = new StringBuilder();

		ArrayList<String> inventoryList = (ArrayList<String>) mappingsDao.getAssociatedOptions("null", "null",
				"inventoryName", null);

		for (String inventory : inventoryList) {
			items.append(optionsHTMLOpen + inventory.trim() + "\">" + inventory.trim() + optionsHTMLClose);
		}
		System.out.println("New project created with ID : " + projId);

		ModelAndView mav = new ModelAndView(updateProjectviewName);

		mav.addObject("boqNameList", String.join(",", ""));
		mav.addObject("quotationNamesList", String.join(",", ""));
		mav.addObject("projectName", project.getProjectName());
		mav.addObject("projectDesc", project.getProjectDesc());
		mav.addObject("projectId", project.getProjectId());
		mav.addObject("taxInvoiceNamesList", "");
		mav.addObject("poNamesList", "");
		mav.addObject("projectId", project.getProjectId());
		mav.addObject("projectName", project.getProjectName());
		mav.addObject("projectDesc", project.getProjectDesc());
		mav.addObject("items", items.toString());
		mav.addObject("address", "");
		mav.addObject("contactEmail", "");
		mav.addObject("contactName", "");
		mav.addObject("contactPhone", "");
		mav.addObject("gstNumber", "");
		mav.addObject("poDate", "");
		mav.addObject("poNumber", "");
		mav.addObject("assignedInventory", "");
		mav.addObject("assignedAccessory", "");
		mav.addObject("paymentDetails", "");

		mav.addObject("consumedInventory", "");
		mav.addObject("consumedAccessory", "");

		mav.addObject("assignedInventory", "");
		mav.addObject("assignedAccessory", "");

		return mav;
	}

	@RequestMapping(value = "/updateProject", method = RequestMethod.POST)
	protected ModelAndView updateProject(ProjectDetails projectDetails) throws Exception {
		projectDetailsDao.updateProjet(projectDetails);

		projectDetails = projectDetailsDao.getProjectDetails(projectDetails.getProjectId());

		System.out.println("projectDetails.getProjectId() is : " + projectDetails.getProjectId());
		Project project = projectDao.getProject(projectDetails.getProjectId());

		ModelAndView mav = new ModelAndView("redirect:/projectDetails");
		mav.addObject("projectId", project.getProjectId());
		mav.addObject("projectName", project.getProjectName());
		mav.addObject("projectDesc", project.getProjectDesc());
		return mav;
	}

	@RequestMapping(value = "/searchProject", method = RequestMethod.POST)
	protected ModelAndView searchProject(String projectName, String projevtDesc, String companyName) throws Exception {

		List<Project> projectList = null;
		StringBuilder projectRows = new StringBuilder();

		if (null != projectName && projectName != "")
			projectList = projectDao.getProject("projectName", projectName);
		else if (null != projevtDesc && projevtDesc != "")
			projectList = projectDao.getProject("projevtDesc", projevtDesc);
		else if (null != companyName && companyName != "")
			projectList = projectDao.getProject("companyName", companyName);

		ModelAndView mav = new ModelAndView(searchProjectviewName);

		if (projectList != null) {
			for (int i = 0; i < projectList.size(); i++) {
				String projectRowSingle = projectRow;

				projectRowSingle = projectRowSingle.replace("projectNameVal", projectList.get(i).getProjectName());
				projectRowSingle = projectRowSingle.replace("projectDescVal", projectList.get(i).getProjectDesc());
				projectRowSingle = projectRowSingle.replace("projectIdVal",
						String.valueOf(projectList.get(i).getProjectId()));

				projectRows.append(projectRowSingle);
			}

		}
		mav.addObject("projects", projectRows);

		return mav;
	}

	@RequestMapping(value = "/projectDetails", method = { RequestMethod.POST, RequestMethod.GET })
	protected ModelAndView projectDetails(Project project, RedirectAttributes redirectAttributes) throws Exception {

		String projectId = String.valueOf(project.getProjectId());

		ProjectDetails projectDetails = projectDetailsDao.getProjectDetails(project.getProjectId());

		System.out.println("projectDetails Address is : " + projectDetails.getAddress());

		String taxInvoiceNames = getTaxInvoiceNames(project.getProjectId());

		ArrayList<PaymentDetails> payDetails = paymentDetailsDao.getPayentDetails(projectId);

		String payDetailsString = getPayDetailsString(payDetails);

		ArrayList<String> boqNames = boqDao.getAssociatedBOQNames(String.valueOf(project.getProjectId()));
		ArrayList<String> quotationNames = new ArrayList<String>();

		String poNames = inventoryUtils.getPONames(String.valueOf(project.getProjectId()));

		ArrayList<String> temp = new ArrayList<String>();

		String tableContent = "";

		Map<String, ?> flashAttributes = redirectAttributes.getFlashAttributes();

		for (int i = 0; i < flashAttributes.size(); i++) {
			System.out.println(flashAttributes.keySet().toArray().toString());
		}

		ArrayList<Inventory> assignedInventory = inventoryDao.getAssignedInventory(project.getProjectName());

		StringBuffer assignedInventoryStr = new StringBuffer();

		String projectDetalsHTML = inventoryRowHTML;

		projectDetalsHTML = projectDetalsHTML.replace("projectIdVal", projectId);
		projectDetalsHTML = projectDetalsHTML.replace("projectNameVal", project.getProjectName());
		projectDetalsHTML = projectDetalsHTML.replace("projectDescVal", project.getProjectDesc());

		for (Inventory inv : assignedInventory) {
			System.out.println(inv.toString());
			assignedInventoryStr.append(inventoryUtils.createInventoryRowTable(inv, false) + projectDetalsHTML);
		}

		// Assigned accessories
		/*
		 * ArrayList<AccessoryDetails> assignedAccessory = accessoryDetailsDao
		 * .getAccessoryDetailsByStatus(project.getProjectName(), "assigned");
		 */

		/*
		 * StringBuffer assignedAccessoryStr = new StringBuffer();
		 * 
		 * for (AccessoryDetails accessory : assignedAccessory) {
		 * assignedAccessoryStr.append(inventoryUtils.createAccessoryRowTable(
		 * accessory, false) + projectDetalsHTML); }
		 */

		// ArrayList<Valves> assignedValves =
		// valveDetailsDao.getValveDetailsByStatus(project.getProjectName(),"assigned");

		StringBuffer assignedValveStr = new StringBuffer();

		/*
		 * for (Valves valveDetails : assignedValves) {
		 * assignedValveStr.append(inventoryUtils.createAccessoryRowTable(
		 * valveDetails, true)+projectDetalsHTML); }
		 */

		ModelAndView mav = new ModelAndView(updateProjectviewName);

		StringBuffer consumedInventoryStr = new StringBuffer();

		ArrayList<Inventory> consumedInventory = inventoryDao.getConsumedInventory(project.getProjectName());

		for (Inventory inv : consumedInventory) {
			consumedInventoryStr.append(inventoryUtils.createInventoryRowTable(inv, true) + projectDetalsHTML);
		}

		/*
		 * ArrayList<AccessoryDetails> consumedAccessory = accessoryDetailsDao
		 * .getAccessoryDetailsByStatus(project.getProjectName(), "consumed");
		 * StringBuffer consumedAccessoryStr = new StringBuffer();
		 * 
		 * for (AccessoryDetails accessory : consumedAccessory) {
		 * consumedAccessoryStr.append(inventoryUtils.createAccessoryRowTable(
		 * accessory, true) + projectDetalsHTML); }
		 */
		StringBuilder items = new StringBuilder();

		ArrayList<String> inventoryList = (ArrayList<String>) mappingsDao.getAssociatedOptions("null", "null",
				"inventoryName", null);

		for (String inventory : inventoryList) {
			if (!(inventory.trim().equals("")))
				items.append(optionsHTMLOpen + inventory.trim() + "\">" + inventory.trim() + optionsHTMLClose);
		}

		mav.addObject("items", items.toString());

		mav.addObject("assignedInventory", assignedInventoryStr);
		mav.addObject("assignedAccessory", ""/* assignedAccessoryStr */);
		mav.addObject("assignedValves", assignedValveStr);

		// TODO : Add Consumed Inventory and Consumed Accessory
		mav.addObject("consumedInventory", consumedInventoryStr);
		mav.addObject("consumedAccessory", ""/* consumedAccessoryStr */);

		if (projectDetails.getAddress() == null) {
			mav.addObject("address", "No Details");
			mav.addObject("contactEmail", "No Details");
			mav.addObject("contactName", "No Details");
			mav.addObject("contactPhone", "No Details");
			mav.addObject("gstNumber", "No Details");
			mav.addObject("poDate", "No Details");
			mav.addObject("poNumber", "No Details");
		} else {
			mav.addObject("address", projectDetails.getAddress());
			mav.addObject("contactEmail", projectDetails.getContactEmail());
			mav.addObject("contactName", projectDetails.getContactName());
			mav.addObject("contactPhone", projectDetails.getContactPhone());
			mav.addObject("gstNumber", projectDetails.getGstNumber());
			mav.addObject("poDate", projectDetails.getPoDate());
			mav.addObject("poNumber", projectDetails.getPoNumber());

		}

		for (int i = 0; i < boqNames.size(); i++) {
			if (boqNames.get(i) != null && boqNames.get(i).startsWith("Inquiry_")) {
				quotationNames.add(boqNames.get(i));
			}
		}

		for (int i = 0; i < boqNames.size(); i++) {
			if (boqNames.get(i) != null && boqNames.get(i).startsWith("Inquiry_")) {
				continue;
			} else {
				temp.add(boqNames.get(i));
			}
		}

		boqNames = temp;

		if (boqNames != null && !boqNames.equals("")) {
			mav.addObject("boqNameList", String.join(",", boqNames));
		} else {
			mav.addObject("boqNameList", "");
		}

		if (quotationNames != null && !quotationNames.equals("")) {
			mav.addObject("quotationNamesList", String.join(",", quotationNames));
		} else {
			mav.addObject("quotationNamesList", "");
		}

		ArrayList<String> vendors = vendorDetailsDao.getVendorList();
		String vendorsList = vendors.toString();

		vendorsList = vendorsList.substring(1).substring(0, vendorsList.length() - 2);

		mav.addObject("venderList", vendorsList);

		mav.addObject("taxInvoiceNamesList", taxInvoiceNames != null ? taxInvoiceNames : " ");
		mav.addObject("projectId", project.getProjectId());
		mav.addObject("projectName", project.getProjectName());
		mav.addObject("projectDesc", project.getProjectDesc());
		mav.addObject("paymentDetails", payDetailsString);
		mav.addObject("poNamesList", poNames != null ? poNames : " ");

		project = projectDao.getProject(Integer.parseInt(projectId));

		mav.addObject("clientName", project.getCompanyName() == null ? "" : project.getCompanyName());

		mav.addObject("emailAddress", projectDetails.getContactEmail() == null ? "" : projectDetails.getContactEmail());
		mav.addObject("addressedTo", projectDetails.getAddress() == null ? "" : projectDetails.getAddress());
		mav.addObject("mobileNo", projectDetails.getContactPhone() == null ? "" : projectDetails.getContactPhone());
		mav.addObject("contactName", projectDetails.getContactName() == null ? "" : projectDetails.getContactName());

		return mav;
	}

	@RequestMapping(value = "/getProjectDetails", method = { RequestMethod.POST, RequestMethod.GET })
	protected @ResponseBody String getProjectDetails(String projectName) throws Exception {
		int projectId = projectDao.getProjectId(projectName);

		ProjectDetails projectDetails = projectDetailsDao.getProjectDetails(projectId);

		ArrayList<String> boqNameList = boqDao.getAssociatedBOQNames(String.valueOf(projectId));

		ArrayList<String> boqNameListFinal = new ArrayList<String>();

		for (String boqName : boqNameList) {
			if (boqName.contains("Final") || boqName.contains("final")) {
				System.out.println(boqName);
				boqNameListFinal.add(boqName);
			}
		}

		Collections.sort(boqNameListFinal);

		ArrayList<BOQDetails> finalBOQDetails = null;
		if (boqNameListFinal.size() > 0) {
			finalBOQDetails = boqDao.getBOQFromName(boqNameListFinal.get(0), String.valueOf(projectId));
		}

		double supplyAmount = 0.0;
		double erectionAmount = 0.0;

		if (finalBOQDetails != null) {
			for (BOQDetails boqD : finalBOQDetails) {
				supplyAmount = supplyAmount + Double.parseDouble(boqD.getSupplyAmount());
				erectionAmount = erectionAmount + Double.parseDouble(boqD.getErectionAmount());

				System.out.println("BOQName " + boqD.getBoqName());
			}
		}

		double finalRate = supplyAmount + erectionAmount;

		System.out.println("finalRate : " + finalRate);

		StringBuffer projectDetailsStr = new StringBuffer(projectDetails.toString());
		projectDetailsStr.replace(projectDetailsStr.indexOf("]"), projectDetailsStr.indexOf("]") + 1, "");
		projectDetailsStr.replace(0, projectDetailsStr.indexOf("[") + 1, "");
		return projectDetailsStr + ": finalRate=" + finalRate;
	}

	@RequestMapping(value = "/saveVendor", method = RequestMethod.POST)
	protected @ResponseBody void saveVendor(VendorDetails vendorDetails) {
		try {
			vendorDetailsDao.saveVendorDetails(vendorDetails);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@RequestMapping(value = "/getVendors", method = { RequestMethod.POST, RequestMethod.GET })
	protected @ResponseBody String getVendorNames() {
		String vendorsList = "";
		try {
			ArrayList<String> vendors = vendorDetailsDao.getVendorList();
			vendorsList = vendors.toString();

			vendorsList = vendorsList.substring(1).substring(0, vendorsList.length() - 2);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return vendorsList;
	}

	@RequestMapping(value = "/getVendorDetails", method = { RequestMethod.POST, RequestMethod.GET })
	protected @ResponseBody String getVendorDetails(String vendorName) {
		String vendorsList = "";
		try {
			VendorDetails vendors = vendorDetailsDao.getVendorDetails(vendorName);
			vendorsList = vendors.toString();

			System.out.println(vendorsList);

			vendorsList = vendorsList.substring(vendorsList.indexOf("["));
			vendorsList = vendorsList.substring(0, vendorsList.length() - 2);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return vendorsList;
	}

	@RequestMapping(value = "/updatePaymentDetails", method = { RequestMethod.POST, RequestMethod.POST })
	protected @ResponseBody String updatePaymentDetails(String taxInvoiceNumber, String receivedAmount,
			String paymentMode, String projectId) {
		try {
			List<PaymentDetails> paymentDetailsList = paymentDetailsDao.getPayentDetails(taxInvoiceNumber, projectId);
			PaymentDetails payDetails = null;

			// Get total Amount for the invoice
			ArrayList<TaxInvoiceDetails> taxinvoiceDetailsList = taxInvoiceDao.getTaxIvoiceData("taxInvoiceNo",
					taxInvoiceNumber);

			double totalAmount = Double.parseDouble(taxinvoiceDetailsList.get(0).getRate());
			totalAmount = totalAmount + Double.parseDouble(taxinvoiceDetailsList.get(0).getcGst()) * 2;

			if (taxinvoiceDetailsList.get(0).getMiscCharges() != null) {
				totalAmount = totalAmount + Double.parseDouble(taxinvoiceDetailsList.get(0).getMiscCharges());
			}

			String dateReceived = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss aa").format(new Date());

			String pendingAmount = "";

			if (paymentDetailsList.size() > 0) {
				payDetails = getLatestPaymentRecord(paymentDetailsList);
				pendingAmount = String.valueOf(
						Double.parseDouble(payDetails.getPendingAmount()) - Double.parseDouble(receivedAmount));

			} else {
				pendingAmount = String.valueOf(totalAmount - Double.parseDouble(receivedAmount));
				payDetails = new PaymentDetails(taxInvoiceNumber, String.valueOf(totalAmount), receivedAmount,
						pendingAmount, paymentMode, dateReceived, projectId);
			}

			paymentDetailsDao.savePaymentDetails(new PaymentDetails(taxInvoiceNumber, payDetails.getTotalAmount(),
					receivedAmount, pendingAmount, paymentMode, dateReceived, projectId));

		} catch (Exception ex) {
			ex.printStackTrace();
			return "Failure";
		}
		return "totalAmount";
	}

	private String getTaxInvoiceNames(int projectId) {
		ArrayList<TaxInvoiceDetails> invoiceNames = taxInvoiceDao.getTaxIvoiceData("projectId",
				String.valueOf(projectId));

		StringBuffer invoiceNamesString = new StringBuffer();
		for (TaxInvoiceDetails invoiceDetails : invoiceNames) {
			invoiceNamesString.append(invoiceDetails.getTaxInvoiceNo() + ",");
		}

		System.out.println("invoiceNamesString list is : " + invoiceNamesString.toString());
		return invoiceNamesString.toString();
	}

	private String getPayDetailsString(ArrayList<PaymentDetails> payDetailsList) {
		String payDetailsString = "";

		for (PaymentDetails paymentDetail : payDetailsList) {
			String tempRow = paymentRow;

			tempRow = tempRow.replace("paymentID", String.valueOf(paymentDetail.getPaymentId()));
			tempRow = tempRow.replace("taxInvoiceNumber", paymentDetail.getTaxInvoiceNumber());
			tempRow = tempRow.replace("totalAmount", paymentDetail.getTotalAmount());
			tempRow = tempRow.replace("amountReceived", paymentDetail.getReceivedAmount());
			tempRow = tempRow.replace("amountPrnding", paymentDetail.getPendingAmount());
			tempRow = tempRow.replace("paymentMethod", paymentDetail.getPaymentMode());
			tempRow = tempRow.replace("dateReceived", paymentDetail.getDateReceived());

			System.out.println("tempRow is : " + tempRow);
			payDetailsString = payDetailsString + tempRow;
		}

		return payDetailsString;
	}

	private PaymentDetails getLatestPaymentRecord(List<PaymentDetails> paymentDetailsList) {
		if (paymentDetailsList.size() == 1) {
			return paymentDetailsList.get(0);
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss aa");
		PaymentDetails paymentDetails = paymentDetailsList.get(0);

		try {
			Date latestDate = dateFormat.parse(paymentDetailsList.get(0).getDateReceived());

			for (PaymentDetails payDetails : paymentDetailsList) {
				if (dateFormat.parse(payDetails.getDateReceived()).compareTo(latestDate) > 0) {
					paymentDetails = payDetails;
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return paymentDetails;
	}

	private static final String paymentRow = "<tr><th>paymentID</th>" + "<th>taxInvoiceNumber</th>"
			+ "<th>totalAmount</th>" + "<th>amountReceived</th>" + "<th>amountPrnding</th>" + "<th>paymentMethod</th>"
			+ "<th>dateReceived</th>" + "</tr>";

	private static final String projectRow = "<form action=\"projectDetails\" onClick=\"this.submit();\" method=\"POST\"> <div class=\"row\">"
			+ " <div class=\"col-md-12 \">"
			+ "   <div class=\"projects pv-30 ph-20 feature-box bordered shadow text-center object-non-visible\" data-animation-effect=\"fadeInDownSmall\" data-effect-delay=\"100\">"
			+ "   <h3 name=\"projectName\">projectNameVal</h3>" + "   <div class=\"separator clearfix\"></div>"
			+ " <p name=\"projectDesc\">projectDescVal</p>" + " </div>" + "</div>   " + "</div>"
			+ "<input type=\"hidden\" name=\"projectId\" value=\"projectIdVal\"/>"
			+ "<input type=\"hidden\" name=\"projectName\" value=\"projectNameVal\"/>"
			+ "<input type=\"hidden\" name=\"projectDesc\" value=\"projectDescVal\"/> </form>";

	private static final String inventoryRowHTML = "<input type=\"hidden\" name=\"projectId\" value=\"projectIdVal\" >"
			+ "<input type=\"hidden\" name=\"projectName\" value=\"projectNameVal\" >"
			+ "<input type=\"hidden\" name=\"projectDesc\" value=\"projectDescVal\" ></form>";

	public static final String optionsHTMLOpen = "<option value=\"";
	public static final String optionsHTMLClose = "</option>";
}
