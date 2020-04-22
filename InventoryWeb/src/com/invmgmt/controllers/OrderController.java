package com.invmgmt.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.invmgmt.dao.InventoryDao;
import com.invmgmt.dao.PODetailsDao;
import com.invmgmt.dao.ProjectDao;
import com.invmgmt.dao.ProjectDetailsDao;
import com.invmgmt.dao.TaxInvoiceDetailsDao;
import com.invmgmt.entity.Inventory;
import com.invmgmt.entity.InventorySpec;
import com.invmgmt.entity.PODetails;
import com.invmgmt.entity.Project;
import com.invmgmt.entity.ProjectDetails;
import com.invmgmt.entity.TaxInvoiceDetails;
import com.invmgmt.util.InventoryUtils;

@Controller
@EnableWebMvc
public class OrderController {

	@Autowired
	InventoryUtils inventoryUtils;

	@Autowired
	TaxInvoiceDetailsDao invoiceDao;

	@Autowired
	PODetailsDao poDetailsDao;

	@Autowired
	ProjectDao projectDao;
	
	@Autowired
	ProjectDetailsDao projectDetailsDao;
	
	@Autowired
	InventoryDao inventoryDao;

	@RequestMapping(value = "/generateOrderForm", method = RequestMethod.POST)
	public ModelAndView generateOfferFrom(String[] inventoryName, String[] material, String[] type, String[] manifMetod,
			String[] classOrGrade, String[] ends, String[] size, String[] quantity, String[] supplyRate,
			String projectId) {
		StringBuffer lineItemData = new StringBuffer();

		StringBuffer lineItemDataSiple = new StringBuffer();

		ModelAndView mav = new ModelAndView("purchaseOrderForm");

		int length = inventoryName.length;

		System.out.println("inventoryName.length is : " + length);
		for (int i = 0; i < length; i++) {

			String description = inventoryUtils.createDescriptionLine(material[i], type[i], inventoryName[i],
					classOrGrade[i], manifMetod[i], ends[i], size[i]);
			String index = String.valueOf(i + 1);
			String lineItem = getInventoryDetailsRow(index, description, quantity[i], supplyRate[i]);
			lineItemData.append(lineItem);

			String line = index + "," + description + "," + quantity[i] + "," + supplyRate[i] + ";";

			System.out.println("Simple line : " + line);
			lineItemDataSiple.append(line);
		}

		mav.addObject("lineItemData", lineItemData.toString());
		mav.addObject("lineItemDataSimple", lineItemDataSiple.toString());
		mav.addObject("projectId", projectId);
		return mav;
	}

	@RequestMapping(value = "/generateOrder", method = RequestMethod.POST)
	public String generateOffer(PODetails poDetails, String lineItemSimple, Model model) {

		ModelAndView mav = new ModelAndView("purchaseOrder");

		StringBuffer terms = new StringBuffer();

		for (String termLine : poDetails.getTerm()) {
			terms.append(getTermHtml(termLine));
		}

		LocalDate date = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yy");
		String poDate = date.format(formatter);

		ArrayList<String> poNumberList = (ArrayList<String>) poDetailsDao.getPoNumber(poDetails.getProjectId());
		ArrayList<String> vendorSpecificPoList = new ArrayList<String>();

		Collections.sort(poNumberList);

		System.out.println("Sorted LR list is : ");

		for (String poNo : poNumberList) {
			System.out.println(poNo);
			if (poNo.contains(poDetails.getVendorName())) {
				vendorSpecificPoList.add(poNo);
			}
		}

		String lastPoNo = "0";

		Collections.sort(vendorSpecificPoList);

		System.out.println("Sorted Vendor specific LR list is : ");
		for (String poStr : vendorSpecificPoList) {
			System.out.println(poStr);
		}

		if (vendorSpecificPoList.size() > 0) {
			lastPoNo = vendorSpecificPoList.get(vendorSpecificPoList.size() - 1);
			lastPoNo = lastPoNo.substring(lastPoNo.lastIndexOf("/") + 1);
		}

		int poNoInt = Integer.parseInt(lastPoNo) + 1;

		String poNumber = "HI/" + poDetails.getVendorName() + "/" + poDetails.getProjectId() + "/"
				+ String.valueOf(poNoInt);

		mav.addObject("PONumber", poNumber);
		mav.addObject("PODate", poDate);
		mav.addObject("companyName", poDetails.getVendorName());
		mav.addObject("location", poDetails.getLocation());
		mav.addObject("contactName", poDetails.getContactName());
		mav.addObject("contactNumber", poDetails.getContactNumber());
		mav.addObject("contactEmail", poDetails.getContactEmail());
		mav.addObject("terms", terms.toString());

		System.out.println(poDetails.getLineItem());

		poDetails.setPoNumber(poNumber);
		poDetails.setPoDate(poDate);

		poDetails.setLineItemNoHtml(lineItemSimple);

		System.out.println(poDetails.getLineItem());
		System.out.println(poDetails.getLineItem().length());

		poDetailsDao.savePO(poDetails);

		/*
		 * mav.addObject("lineItems", poDetails.getLineItem());
		 * mav.addObject("lineItemSimple", lineItemSimple); return mav;
		 */

		String lineItemSimpleStr = poDetails.getLineItemNoHtml();

		System.out.println("showPO : " + lineItemSimpleStr);

		model.addAttribute("poDetails", poDetails);

		model.addAttribute("poLineDetails", lineItemSimpleStr);

		return "purchaseOrderView";

	}

	@RequestMapping(value = "/showInvoice", method = { RequestMethod.POST, RequestMethod.GET })
	private String shiwInvoice(Model model, String invoiceName) {
		ArrayList<TaxInvoiceDetails> invoiceDetails = invoiceDao.getTaxIvoiceData("taxInvoiceNo", invoiceName);

		model.addAttribute("taxInvoiceDetails", invoiceDetails.get(0));

		return "taxInvoiceView";
	}

	@RequestMapping(value = "/showPO", method = { RequestMethod.POST, RequestMethod.GET })
	private String showPO(Model model, String poName) {
		ArrayList<PODetails> invoiceDetails = poDetailsDao.getPOData("poNumber", poName);

		System.out.println("showPO : " + poName);

		ArrayList<PODetails> detailsList = poDetailsDao.getPOData("poNumber", poName);

		String lineItemSimple = ((PODetails) detailsList.get(0)).getLineItemNoHtml();

		System.out.println("showPO : " + lineItemSimple);

		model.addAttribute("poDetails", invoiceDetails.get(0));

		model.addAttribute("poLineDetails", lineItemSimple);

		return "purchaseOrderView";
	}

	@RequestMapping(value = "/getPoList", method = { RequestMethod.POST, RequestMethod.GET })
	private @ResponseBody String getPoList(String projectName) {
		int projectID = projectDao.getProjectId(projectName);
		
		ProjectDetails projectDetails = projectDetailsDao.getProjectDetails(projectID);
		
		
		String poNames = inventoryUtils.getPONames(String.valueOf(projectID));

		return poNames+"::"+projectDetails.toString();
	}

	@RequestMapping(value = "/getPoDetails", method = { RequestMethod.POST, RequestMethod.GET })
	private @ResponseBody String getPoDetails(String poNumber) {
		ArrayList<PODetails> poDetailsList = poDetailsDao.getPOData("poNumber", poNumber);

		String rowToReturn = "";

		for (PODetails pODetails : poDetailsList) {
			Project project = projectDao.getProject(Integer.parseInt(pODetails.getProjectId()));

			String[] lineItems = pODetails.getLineItemNoHtml().split(";");

			for (String line : lineItems) {
				String itemsStr = poDetailsRow;

				String words[] = line.split(",");

				String[] details = words[1].split(" ");

				int instanceofOF = findIndex(details, "of");
				int instanceofAS = findIndex(details, "as");
				int instanceofEnds = findIndex(details, "Ends");
				int instanceofsize = findIndex(details, "size");
				int instanceofManifMethod = findIndex(details, "Method");

				String inventoryNameVal = details[instanceofOF - 1];
				String materialVal = details[0];

				String typeVal = "";

				for (int i = instanceofOF - 2; i > 0; i--) {
					typeVal = details[i] + typeVal;
				}

				String manifacturingMethod = details[instanceofManifMethod + 2];
				String gradeOrClassVal = details[instanceofAS + 1];
				String endsVal = details[instanceofEnds + 2];
				String sizeVal = details[instanceofsize + 1];
				String purchaseRate = words[3];
				String poQuantity = words[2];
				String projectNameVal = project.getProjectName();

				itemsStr = itemsStr.replace("inventoryNameVal", inventoryNameVal);
				itemsStr = itemsStr.replace("materialVal", materialVal);
				itemsStr = itemsStr.replace("typeVal", typeVal);
				itemsStr = itemsStr.replace("manifacturingMethod", manifacturingMethod);
				itemsStr = itemsStr.replace("gradeOrClassVal", gradeOrClassVal);
				itemsStr = itemsStr.replace("endsVal", endsVal);
				itemsStr = itemsStr.replace("sizeVal", sizeVal);
				itemsStr = itemsStr.replace("poQuantity", poQuantity);
				itemsStr = itemsStr.replace("projectNameVal", projectNameVal);
				itemsStr = itemsStr.replace("purchaseRateVal", purchaseRate);

				rowToReturn = rowToReturn + itemsStr;
			}
		}

		return rowToReturn+"::"+((PODetails)poDetailsList.get(0)).toString();
	}

	@RequestMapping(value = "/getNoInvoiceInventory", method = RequestMethod.POST)
	public @ResponseBody String getNoInvoiceInventory(int projectId) throws Exception {
		String rowToReturn = "";
		
		Project project = projectDao.getProject(projectId);
		ArrayList<Inventory> receivedInvListNoInvoice = inventoryDao.getNoInvoiceInventory(project.getProjectName());
		
		for (Inventory receivedInventory : receivedInvListNoInvoice) 
		{
			
			InventorySpec invSpec = receivedInventory.getInventorySpec();
			String itemsStr = noInvoiceInventoryRow;
			itemsStr = itemsStr.replace("inventoryNameVal", invSpec.getInventoryName());
			itemsStr = itemsStr.replace("materialVal", invSpec.getMaterial());
			itemsStr = itemsStr.replace("typeVal", invSpec.getType());
			itemsStr = itemsStr.replace("manifacturingMethod", invSpec.getManifMethod());
			itemsStr = itemsStr.replace("gradeOrClassVal", invSpec.getGradeOrClass());
			itemsStr = itemsStr.replace("endsVal", invSpec.getEnds());
			itemsStr = itemsStr.replace("sizeVal", invSpec.getSize());
			itemsStr = itemsStr.replace("receivedQuantityVal", String.valueOf(receivedInventory.getQuantity()));
			itemsStr = itemsStr.replace("projectNameVal", invSpec.getAssignedProject());
			itemsStr = itemsStr.replace("purchaseRateVal", receivedInventory.getPurchaseRate());
			itemsStr = itemsStr.replace("locationVal", receivedInventory.getLocation());
			itemsStr = itemsStr.replace("receivedDateVal", (receivedInventory.getReceivedDate()==null?"":receivedInventory.getReceivedDate().substring(0,10)));
			
			rowToReturn = rowToReturn + itemsStr;
		}

		return rowToReturn;
	}

	// Function to find the index of an element in a primitive array in Java
	public static int findIndex(String[] a, String target) {
		for (int i = 0; i < a.length; i++) {
			if (a[i].equals(target))
				return i;
		}
		return -1;
	}

	private String getInventoryDetailsRow(String sr_no, String description, String quantity, String supplyRate) {
		String template = "<TR>" + "	<TD class=\"tr8 td38\"><P class=\"p16 ft8\">sr_no</P></TD>               "
				+ "	<TD class=\"tr8 td27\"><P class=\"p0 ft0\"></P></TD>               "
				+ "	<TD class=\"tr8 td53\"><P class=\"p0 ft0\"></P></TD>               "
				+ "	<TD colspan=2 class=\"tr8 td54\"><P class=\"p4 ft8\">Description</P></TD>"
				+ "	<TD class=\"tr8 td41\"><P class=\"p0 ft0\"></P></TD>               "
				+ "	<TD class=\"tr8 td42\"><P class=\"p17 ft8\">quantity</P></TD>            "
				+ "	<TD class=\"tr8 td43\"><P class=\"p8 ft8\">NB</P></TD>                 "
				+ "	<TD class=\"tr8 td44\"><P class=\"p18 ft8\">supplyRate</P></TD>"
				+ "	<TD class=\"tr8 td55\"><P class=\"p5 ft9 gstCentral\" value=\"cgst\">cgst</P></TD>                 "
				+ "	<TD class=\"tr8 td55\"><P class=\"p5 ft9 gstState\" value=\"sgst\">sgst</P></TD>                 "
				+ "	<TD class=\"tr8 td17\"><P class=\"p5 ft9 lineAmt\" value=\"amount\">amount</P></TD>              "
				+ "</TR>                                                                       ";

		float amount = Float.parseFloat(quantity) * Float.parseFloat(supplyRate);
		float sgst = amount * 9 / 100;
		float cgst = amount * 9 / 100;

		String stringToReturn = template.replace("sr_no", sr_no).replace("Description", description)
				.replace("quantity", quantity)
				.replace("supplyRate",
						supplyRate.contains(".") ? supplyRate.substring(0, supplyRate.indexOf(".")) : supplyRate)
				.replace("cgst", get2DecimalVal(cgst)).replace("sgst", get2DecimalVal(sgst))
				.replace("amount", get2DecimalVal(amount));

		return stringToReturn;
	}

	public String get2DecimalVal(float val) {
		String twoDecimalVal = String.valueOf(val);

		// twoDecimalVal = twoDecimalVal.substring(0, twoDecimalVal.indexOf(".")
		// + 3);

		return twoDecimalVal;
	}

	private String getTermHtml(String termLine) {
		String template = "<TR>                                                                     "
				+ "	<TD colspan=7 class=\"tr8 td58\"><P class=\"p23 ft3\">1 TERM_TEXT.</P></TD>"
				+ "	<TD class=\"tr8 td30\"><P class=\"p0 ft0\"></P></TD>                "
				+ "	<TD class=\"tr8 td31\"><P class=\"p0 ft0\"></P></TD>                "
				+ "	<TD class=\"tr8 td16\"><P class=\"p0 ft0\"></P></TD>                "
				+ "	<TD class=\"tr8 td16\"><P class=\"p0 ft0\"></P></TD>                "
				+ "	<TD class=\"tr8 td17\"><P class=\"p0 ft0\"></P></TD>                "
				+ "</TR>                                                                    ";

		String stringToReturn = template.replace("TERM_TEXT", termLine);

		return stringToReturn;
	}

	private static final String poDetailsRow = "	   <tr>"
			+ "    <td> <input type='button' value='X' onClick='removeRow($(this));'></td>"
			+ "    <td> <input type='hidden' name='inventoryName' value='inventoryNameVal'></input> inventoryNameVal </td>"
			+ "    <td> <input type='hidden' name='material' value='materialVal'></input>materialVal</td>"
			+ "    <td> <input type='hidden' name='type' value='typeVal'></input>typeVal</td>"
			+ "    <td> <input type='hidden' name='manifMethod' value='manifacturingMethod'></input>manifacturingMethod</td>"
			+ "    <td> <input type='hidden' name='gradeOrClass' value='gradeOrClassVal'></input>gradeOrClassVal</td>"
			+ "    <td> <input type='hidden' name='ends' value='endsVal'></input>endsVal</td>"
			+ "    <td> <input type='hidden' name='size' value='sizeVal'></input>sizeVal</td>"
			+ "	   <td> <input type='hidden' name='purchaseRate' value='purchaseRateVal'></input> purchaseRateVal </td>"
			+ "	   <td> poQuantity </td>"
			+ "	   <td><input type='text' class='form-control' name='quantity' value=''></input><input type='hidden' name='projectName' id='projectNm' value='projectNameVal'></td>"
			+ "	   <td><input type='text' class='form-control' name='location' value=''></input></td>"
			+ "	   <input type='hidden' name='status' value='assigned'>" + "    </tr>";
	
	private static final String noInvoiceInventoryRow = "	   <tr>"
			+ "    <td> <input type=\"checkbox\" name=\"checkbox\" class=\"checkbox\" /></td>"
			+ "    <td> <input type='hidden' name='inventoryName' value='inventoryNameVal'></input> inventoryNameVal </td>"
			+ "    <td> <input type='hidden' name='material' value='materialVal'></input>materialVal</td>"
			+ "    <td> <input type='hidden' name='type' value='typeVal'></input>typeVal</td>"
			+ "    <td> <input type='hidden' name='manifMethod' value='manifacturingMethod'></input>manifacturingMethod</td>"
			+ "    <td> <input type='hidden' name='gradeOrClass' value='gradeOrClassVal'></input>gradeOrClassVal</td>"
			+ "    <td> <input type='hidden' name='ends' value='endsVal'></input>endsVal</td>"
			+ "    <td> <input type='hidden' name='size' value='sizeVal'></input>sizeVal</td>"
			+ "	   <td> <input type='hidden' name='purchaseRate' value='purchaseRateVal'></input> purchaseRateVal </td>"
			+ "	   <td> <input type='hidden' name='receivedQuantity' value='receivedQuantityVal'></input>"
			+ "			receivedQuantityVal <input type='hidden' name='projectName' id='projectNm' value='projectNameVal'></td>"
			+ "	   <td> <input type='hidden' name='location' value='locationVal'></input>locationVal</td>"
			+ "	   <td style='width:10%;'> <input type='hidden' name='receivedDate'  value='receivedDateVal'></input>receivedDateVal</td>"
			+ "	   <input type='hidden' name='status' value='assigned'>" + "    </tr>";


}
