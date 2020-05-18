package com.invmgmt.controllers;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.invmgmt.dao.AccessoryDetailsDao;
import com.invmgmt.dao.BOQDetailsDao;
import com.invmgmt.dao.BOQHeaderDao;
import com.invmgmt.dao.BOQLineDataDao;
import com.invmgmt.dao.InventoryDao;
import com.invmgmt.dao.InventoryDefinitionDao;
import com.invmgmt.dao.MappingsDao;
import com.invmgmt.dao.ProjectDao;
import com.invmgmt.dao.UserDetailsDao;
import com.invmgmt.dao.ValvesDao;
import com.invmgmt.dao.VendorDetailsDao;
import com.invmgmt.entity.AccessoryDetails;
import com.invmgmt.entity.BOQDetails;
import com.invmgmt.entity.BOQHeader;
import com.invmgmt.entity.BOQLineData;
import com.invmgmt.entity.Inventory;
import com.invmgmt.entity.InventorySpec;
import com.invmgmt.entity.Project;
import com.invmgmt.entity.Valves;
import com.invmgmt.entity.VendorDetails;
import com.invmgmt.excel.ExcelReader;
import com.invmgmt.excel.ExcelWriter;
import com.invmgmt.interfaces.BOQData;
import com.invmgmt.util.ConfigProperties;
import com.invmgmt.util.EmailUtils;
import com.invmgmt.util.HTMLElements;
import com.invmgmt.util.InventoryUtils;
import com.invmgmt.util.MappingsUtil;

@Controller
@EnableWebMvc
public class BOQController {

	@Autowired
	ExcelReader reader;

	@Autowired
	ExcelWriter writer;

	@Autowired
	InventoryDefinitionDao inventoryDefinitionDao;

	@Autowired
	MappingsDao mappingsDao;

	@Autowired
	ValvesDao valvesDao;

	@Autowired
	InventoryDao inventoryDao;

	@Autowired
	AccessoryDetailsDao accessoryDetailsDao;

	@Autowired
	BOQDetailsDao boqDao;

	@Autowired
	BOQDetailsDao boqDetailsDao;

	@Autowired
	BOQHeaderDao boqHeaderDao;

	@Autowired
	ProjectDao projectDao;

	@Autowired
	BOQLineDataDao boqLineDataDao;

	@Autowired
	UserDetailsDao userDetailsDao;

	@Autowired
	InventoryUtils inventoryUtils;

	@Autowired
	MappingsUtil mappingsUtil;

	@Autowired
	EmailUtils emailUtils;

	@Autowired
	VendorDetailsDao vendorDetailsDao;

	@Autowired
	ConfigProperties configProperties;

	ArrayList<Valves> valveDetailsList = null;

	@PostMapping("import")
	public @ResponseBody String getBOQLine(@RequestParam("file") MultipartFile file) {

		List inventoryList = null;
		Map<BOQData, String> inventoryQtyMap = new LinkedHashMap<BOQData, String>();

		File convFile = new File(file.getOriginalFilename());
		try {
			file.transferTo(convFile);
		} catch (IllegalStateException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			inventoryList = reader.readFile(convFile);

			for (int i = 0; i < inventoryList.size(); i++) {
				if (inventoryList.get(i) instanceof Inventory) {
					int qty = inventoryDao.getAvailableQuantity((Inventory) inventoryList.get(i));
					inventoryQtyMap.put((Inventory) inventoryList.get(i), String.valueOf(qty));
				} else if (inventoryList.get(i) instanceof BOQHeader) {
					inventoryQtyMap.put((BOQHeader) inventoryList.get(i), null);
				}

			}
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			e.printStackTrace();
		}

		String tableContent = createBOQContent(inventoryQtyMap, null, null, null, null, null, null, null);

		return tableContent;
	}

	@RequestMapping(value = "/downloadBoq", method = RequestMethod.GET)
	protected @ResponseBody String downloadBOQ(String boqName, String projectId) {
		ArrayList<BOQDetails> boqDetails = boqDao.getBOQFromName(boqName, projectId);

		BOQHeader header = boqHeaderDao.getBOQHeaderFromName(boqName, projectId);

		String sheetDetails = header.getSheetDetails();
		String[] sheetDetailsArray = sheetDetails.split(",");

		List<Inventory> inventoryList = null;
		List<String> supplyAmtList = new ArrayList<String>();
		List<String> erectionAmtList = new ArrayList<String>();
		List<String> baseSupplyAmtList = new ArrayList<String>();
		List<String> baseErectionAmtList = new ArrayList<String>();

		List<String> supplyRateList = new ArrayList<String>();
		List<String> erectionRateList = new ArrayList<String>();

		String tableContent = "";
		try {
			// writer.writeExcel(boqDetails);

			inventoryList = convertBOQDetailsToInventory(boqDetails);

			for (BOQDetails boqDetail : boqDetails) {
				supplyAmtList.add(boqDetail.getSupplyAmount());
				erectionAmtList.add(boqDetail.getErectionAmount());
				supplyRateList.add(boqDetail.getSupplyRate());
				erectionRateList.add(boqDetail.getErectionRate());
				baseSupplyAmtList.add(boqDetail.getBaseSupplyRate());
				baseErectionAmtList.add(boqDetail.getBaseErectionRate());
			}

			Map<BOQData, String> inventoryQtyMap = new LinkedHashMap<BOQData, String>();

			for (int i = 0; i < inventoryList.size(); i++) {
				if (inventoryList.get(i) instanceof Inventory) {
					int qty = inventoryDao.getAvailableQuantity(inventoryList.get(i));
					System.out.println("Available Qty is : " + qty);
					inventoryQtyMap.put(inventoryList.get(i), String.valueOf(qty));
				}

			}
			inventoryQtyMap.put(header, "header");
			tableContent = createBOQContent(inventoryQtyMap, supplyRateList, erectionRateList, supplyAmtList,
					erectionAmtList, baseSupplyAmtList, baseErectionAmtList, sheetDetailsArray);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return tableContent;
	}

	@RequestMapping(value = "/showInventory", method = RequestMethod.POST)
	public @ResponseBody String getAvailableInventory() {

		List inventoryList = null;
		ArrayList<AccessoryDetails> accessoryDetailsList = null;
		try {
			inventoryList = inventoryDao.getAvailableInventory();
			// accessoryDetailsList =
			// accessoryDetailsDao.getAccessoryDetailsByStatus();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String tableContent = createInvtTable(inventoryList, accessoryDetailsList);
		return tableContent;
	}

	@RequestMapping(value = "/getAssociatedFinalBOQ", method = RequestMethod.POST)
	public @ResponseBody String getAssociatedFinalBOQ(String projectName) {

		List inventoryList = null;
		try {
			int projectId = projectDao.getProjectId(projectName);

			ArrayList<String> boqNames = boqDao.getAssociatedBOQNames(String.valueOf(projectId));

			for (String boqName : boqNames) {
				if (boqName.contains("final")) {
					ArrayList<BOQDetails> boqDetails = boqDao.getBOQFromName(boqName, String.valueOf(projectId));
				}
			}

			inventoryList = inventoryDao.getAvailableInventory();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String tableContent = createInvtTable(inventoryList, null);
		return tableContent;
	}

	@RequestMapping(value = { "/delete" }, method = RequestMethod.POST)
	protected ModelAndView deleteRevision(String docNameToDownload, String projectId, HttpServletResponse response) {
		String isDeleted = "true";

		try {
			boqHeaderDao.deleteHeaderData(docNameToDownload, projectId);
			boqDao.deleteBoqData(docNameToDownload, projectId);
		} catch (Exception ex) {
			ex.printStackTrace();
			isDeleted = "false";
		}
		Project project = projectDao.getProject(Integer.parseInt(projectId));

		ModelAndView mav = new ModelAndView("redirect:/projectDetails");
		mav.addObject("projectId", project.getProjectId());
		mav.addObject("projectName", project.getProjectName());
		mav.addObject("projectDesc", project.getProjectDesc());

		return mav;

	}

	@RequestMapping(value = { "/download" }, method = RequestMethod.POST)
	protected void generateBOQ(String docNameToDownload, String projectId, HttpServletResponse response) {
		byte[] excelByts = null;

		ArrayList<BOQLineData> boqlineData = new ArrayList<BOQLineData>();

		ArrayList<BOQDetails> itemDetails = boqDetailsDao.getBOQFromName(docNameToDownload, projectId);

		int length = itemDetails.size();

		String[] material = new String[length];
		String[] type = new String[length];
		String[] ends = new String[length];
		String[] classOrGrade = new String[length];
		String[] inventoryName = new String[length];
		String[] manifMetod = new String[length];
		String[] size = new String[length];
		String[] quantity = new String[length];
		String[] supplyRate = new String[length];
		String[] erectionRate = new String[length];
		String[] supplyAmount = new String[length];
		String[] erectionAmount = new String[length];

		for (int i = 0; i < length; i++) {
			material[i] = itemDetails.get(i).getMaterial();
			type[i] = itemDetails.get(i).getType();
			ends[i] = itemDetails.get(i).getEnds();
			classOrGrade[i] = itemDetails.get(i).getClassOrGrade();
			inventoryName[i] = itemDetails.get(i).getInventoryName();
			manifMetod[i] = itemDetails.get(i).getManifacturingMethod();
			size[i] = itemDetails.get(i).getSize();
			quantity[i] = itemDetails.get(i).getQuantity();
			supplyRate[i] = itemDetails.get(i).getSupplyRate();
			erectionRate[i] = itemDetails.get(i).getErectionRate();
			supplyAmount[i] = itemDetails.get(i).getSupplyAmount();
			erectionAmount[i] = itemDetails.get(i).getErectionAmount();

		}

		BOQHeader header = boqHeaderDao.getBOQHeaderFromName(docNameToDownload, projectId);

		boqlineData = getBOQLineDataList(material, type, ends, classOrGrade, inventoryName, manifMetod);
		try {
			excelByts = writer.writeExcel(boqlineData, size, quantity, supplyRate, erectionRate, supplyAmount,
					erectionAmount, "", header, false);
			response.setHeader("Content-disposition", "attachment; filename=" + docNameToDownload + ".xls");

			OutputStream out = response.getOutputStream();

			byte[] buffer = excelByts; // use bigger if you want
			int bufferLength = buffer.length;

			System.out.println("Buffer length is : " + length);
			out.write(buffer, 0, bufferLength);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = { "/generateNew", "/generate" }, method = RequestMethod.POST)
	protected void generateBOQ(String projectId, String boqName, String[] inventoryName, String[] material,
			String[] type, String[] manifMetod, String[] classOrGrade, String[] ends, String[] size, String[] quantity,
			String[] supplyRate, String[] erectionRate, String[] supplyAmount, String[] erectionAmount,
			String[] baseErectionRate, String[] baseSupplyRate, String[] accessoryName, String[] desc1, String[] desc2,
			String[] desc3, String[] desc4, String[] desc5, String[] model, String[] materialVal, String[] typeVal,
			String[] pressureRatings, String[] endVal, String isOffer, String[] client, String[] site, String[] project,
			String[] dName, String[] utility, String[] pressure, String[] temp, String[] dNo, String[] sheetDetails,
			String venderName, RedirectAttributes redirectAttributes, HttpServletResponse response, HttpSession session)
			throws IOException {
		StringBuilder sheetdetailsStr = new StringBuilder();

		int accessIndex = 0;
		if (client != null) {
			accessIndex = client.length - 1;
		}

		if (null != sheetDetails && 0 != sheetDetails.length) {
			for (String name : sheetDetails) {
				sheetdetailsStr.append(name + ",");
			}
		}

		BOQHeader header = new BOQHeader(projectId, inventoryUtils.blankIfNull(client, accessIndex),
				inventoryUtils.blankIfNull(site, accessIndex), inventoryUtils.blankIfNull(project, accessIndex),
				inventoryUtils.blankIfNull(dName, accessIndex), inventoryUtils.blankIfNull(utility, accessIndex),
				inventoryUtils.blankIfNull(pressure, accessIndex), inventoryUtils.blankIfNull(temp, accessIndex),
				inventoryUtils.blankIfNull(dNo, accessIndex), boqName, sheetdetailsStr.toString());

		System.out.println("Sheet Details are : " + header.getSheetDetails());
		ArrayList<String> boqRevisions = boqDao.getMatchingBOQNames(boqName + "_R", projectId);

		ArrayList<String> quorationRevisions = boqDao.getMatchingBOQNames("Inquiry_" + boqName + "_R", projectId);

		String boqNameRevisionStr = "";
		ArrayList<String> boqNames = new ArrayList<String>();
		ArrayList<String> quotationNames = new ArrayList<String>();

		if (!Boolean.valueOf(isOffer)) {
			for (int i = 0; i < 50; i++) {
				boqNameRevisionStr = boqName + "_R" + String.valueOf(i);
				if (boqRevisions.contains(boqNameRevisionStr))
					continue;
				else
					break;
			}
		} else {
			for (int i = 0; i < 20; i++) {
				boqNameRevisionStr = "Inquiry_" + boqName + "_R" + String.valueOf(i);
				if (quorationRevisions.contains(boqNameRevisionStr))
					continue;
				else
					break;
			}
		}

		if (boqName.endsWith("_final")) {
			boqNameRevisionStr = boqName;
		}
		/*
		 * else if(Boolean.valueOf(isOffer)) { boqNameRevisionStr =
		 * "Quotation_"+boqNameRevisionStr; }
		 */

		System.out.println("Saving BOQ with name : " + boqNameRevisionStr);

		ArrayList<BOQLineData> boqInventoryDetails = new ArrayList<BOQLineData>();

		/*
		 * String[] sheetDetailsArray = header.getSheetDetails().split(",");
		 * 
		 * int noOfEntries = material.length;
		 * 
		 * for (int i = 0; i < noOfEntries; i++) {
		 * System.out.println("Getting details for : " + material[i] + " , " +
		 * type[i] + " , " + classOrGrade[i] + " , " + ends[i]);
		 * 
		 * if (material[i] != null && !material[i].trim().equals("")) {
		 * BOQLineData boqLineData = new BOQLineData(); try { boqLineData =
		 * getBOQLineData(material[i].trim(), type[i].trim(),
		 * classOrGrade[i].trim(), ends[i].trim(), inventoryName[i].trim()); }
		 * catch (IndexOutOfBoundsException ex) {
		 * 
		 * // If you are here it means we are dealing with Accessory boqLineData
		 * = new BOQLineData(material[i], type[i], manifMetod[i],
		 * classOrGrade[i], ends[i], "", inventoryName[i], ""); }
		 * boqInventoryDetails.add(boqLineData); } }
		 */

		if (material != null) {
			boqInventoryDetails = getBOQLineDataList(material, type, ends, classOrGrade, inventoryName, manifMetod);
		}

		byte[] excelByts = null;
		try {
			if (Boolean.valueOf(isOffer)) {
				supplyRate = new String[] {};
				erectionRate = new String[] {};
				supplyAmount = new String[] {};
				erectionAmount = new String[] {};
			}
			excelByts = writer.writeExcel(boqInventoryDetails, size, quantity, supplyRate, erectionRate, supplyAmount,
					erectionAmount, boqNameRevisionStr, header, Boolean.valueOf(isOffer));

			ArrayList<BOQDetails> boqInventoryDetailsList = new ArrayList<BOQDetails>();
			if (inventoryName != null) {
				boqInventoryDetailsList = inventoryUtils.getBOQDetailsList(projectId, boqNameRevisionStr, inventoryName,
						material, type, manifMetod, classOrGrade, ends, size, quantity, supplyRate, erectionRate,
						supplyAmount, erectionAmount, baseErectionRate, baseSupplyRate, header.getSheetDetails());
			}

			ArrayList<BOQDetails> boqAccessoryDetailsList = new ArrayList<>();
			if (accessoryName != null) {
				boqAccessoryDetailsList = inventoryUtils.getBOQDetailsList(projectId, boqNameRevisionStr, accessoryName,
						desc1, desc2, desc3, desc4, desc5, size, quantity, supplyRate, erectionRate, supplyAmount,
						erectionAmount, baseErectionRate, baseSupplyRate, header.getSheetDetails());
			}

			ArrayList<BOQDetails> boqValveDetailsList = new ArrayList<>();

			if (model != null) {
				boqValveDetailsList = inventoryUtils.getBOQDetailsList(projectId, boqNameRevisionStr, model,
						materialVal, typeVal, null, pressureRatings, endVal, size, quantity, supplyRate, erectionRate,
						supplyAmount, erectionAmount, baseErectionRate, baseSupplyRate, header.getSheetDetails());
			}

			if (boqValveDetailsList.size() > 0) {
				boqInventoryDetailsList.addAll(boqValveDetailsList);
			}

			if (boqAccessoryDetailsList.size() > 0) {
				boqInventoryDetailsList.addAll(boqAccessoryDetailsList);
			}

			// Save Pipe, Accessories and Fittings
			for (BOQDetails boqDetails : boqInventoryDetailsList) {
				boqDao.saveBOQ(boqDetails);
			}

			// Save BOQ header
			header.setBoqName(boqNameRevisionStr);
			boqHeaderDao.saveBOQHeader(header);

			// Save Valve details

			boqNames = boqDao.getAssociatedBOQNames(String.valueOf(projectId));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ModelAndView mav = new ModelAndView("redirect:/projectDetails");

		for (int i = 0; i < boqNames.size(); i++) {
			if (boqNames.get(i) != null && boqNames.get(i).startsWith("Inquiry_")) {
				quotationNames.add(boqNames.get(i));
				boqNames.remove(i);
			}

		}

		if (Boolean.valueOf(isOffer) && (venderName != null && !(venderName.isEmpty()))) {
			// Fetch associated email address
			String sender = userDetailsDao.getEmailAddress((String) session.getAttribute("userName"));

			VendorDetails vendorDetails = vendorDetailsDao.getVendorDetails(venderName);
			emailUtils.sendInquiry(sender, vendorDetails.getContactEmail(), excelByts, boqNameRevisionStr);
		}

		response.setHeader("Content-disposition", "attachment; filename=" + boqNameRevisionStr + ".xls");

		OutputStream out = response.getOutputStream();

		byte[] buffer = excelByts; // use bigger if you want
		int length = buffer.length;

		System.out.println("Buffer length is : " + length);
		out.write(buffer, 0, length);
		out.close();

	}

	protected ArrayList<BOQLineData> getBOQLineDataList(String[] material, String[] type, String[] ends,
			String[] classOrGrade, String[] inventoryName, String[] manifMethod) {
		ArrayList<BOQLineData> boqInventoryDetails = new ArrayList<>();
		int noOfEntries = material.length;

		for (int i = 0; i < noOfEntries; i++) {
			System.out.println("Getting details for : " + material[i] + " , " + type[i] + " , " + classOrGrade[i]
					+ " , " + ends[i]);

			if (material[i] != null && !material[i].trim().equals("")) {
				BOQLineData boqLineData = new BOQLineData();
				try {
					boqLineData = getBOQLineData(material[i].trim(), type[i].trim(), classOrGrade[i].trim(),
							ends[i].trim(), inventoryName[i].trim());
				} catch (IndexOutOfBoundsException ex) {

					// If you are here it means we are dealing with Accessory
					boqLineData = new BOQLineData(material[i], type[i], manifMethod[i], classOrGrade[i], ends[i], "",
							inventoryName[i], "");
				}
				boqInventoryDetails.add(boqLineData);
			}
		}

		return boqInventoryDetails;
	}

	private BOQLineData getBOQLineData(String material, String type, String classOrGrade, String ends,
			String inventoryName) {
		switch (classOrGrade) {
		case "C":
			classOrGrade = "Heavy";
		case "B":
			classOrGrade = "Medium";
		case "A":
			classOrGrade = "Light";
		default:
			break;
		}

		// BOQLineData boqLineData = boqLineDataDao.getLineData(material,
		// inventoryName);

		/*
		 * boqLineData.setEndsLine(boqLineData.getEndsLine().replaceAll(
		 * "ENDS_VAL", ends));
		 * boqLineData.setGrdLine(boqLineData.getGrdLine().replaceAll(
		 * "CLASS_VAL", classOrGrade));
		 * 
		 * if (boqLineData.getGrdLine().contains("TYPE_VAL")) {
		 * boqLineData.setGrdLine(boqLineData.getGrdLine().replaceAll(
		 * "TYPE_VAL", type)); }
		 * 
		 * if (boqLineData.getSpecLine().contains("TYPE_VAL")) {
		 * boqLineData.setSpecLine(boqLineData.getSpecLine().replaceAll(
		 * "TYPE_VAL", type)); }
		 * 
		 * boqLineData.setStdLine(boqLineData.getStdLine().replaceAll(
		 * "VALVE_NAME", inventoryName));
		 */

		String standardype = "";

		Set<Object> inventryKeys = configProperties.getStandardTypKeys();

		for (Object key : inventryKeys) {
			if (((String) key).contains(inventoryName)) {
				standardype = configProperties.getStandardTypePorperties(((String) key));
			}
		}
		BOQLineData boqLineData = new BOQLineData();

		boqLineData.setStdLine("Standard/Type: " + (standardype.isEmpty() ? inventoryName : standardype));
		boqLineData.setGrdLine("Grade/Class: " + classOrGrade);
		boqLineData.setSpecLine("Material Spec: " + type);
		boqLineData.setEndsLine("Ends: " + ends);
		boqLineData.setMakesLine("Recommended Makes: ");

		boqLineData.setInventoryName(inventoryName);

		if (inventoryName.equals("Elbow")) {
			boqLineData.setCategory(classOrGrade);
		}

		return boqLineData;
	}

	@RequestMapping(value = "/getDropdown", method = RequestMethod.POST)
	public @ResponseBody String getNextDropDownContent(@RequestParam(value = "value", required = true) String value,
			@RequestParam(value = "currentTag", required = true) String currentTag,
			@RequestParam(value = "nextTagName", required = true) String nextTagName,
			@RequestParam(value = "inventory", required = true) String inventory) {

		StringBuilder dropdownContent = new StringBuilder();

		try {

			ArrayList<String> results = new ArrayList<String>();

			if (inventory.equals("Pipe")) {
				results = (ArrayList<String>) mappingsDao.getAssociatedOptions(currentTag, value, nextTagName,
						inventory);
			} else {
				results = mappingsUtil.getDetails(inventory, nextTagName);
			}

			for (int i = 0; i < results.size(); i++) {
				String option = String.valueOf(results.get(i));

				dropdownContent.append("<option value=\"" + option + "\">" + option + "</option>" + "\n");

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dropdownContent.toString();
	}

	@RequestMapping(value = "/getValveDetails", method = RequestMethod.POST)
	public @ResponseBody String getValveDetails(
			@RequestParam(value = "nextTagName", required = true) String nextTagName,
			@RequestParam(value = "model", required = true) String model) {

		StringBuilder dropdownContent = new StringBuilder();

		String modelString = model.split(":").length > 0 ? model.split(":")[1] : model;
		ArrayList<String> results = new ArrayList<String>();
		try {

			if (valveDetailsList == null || valveDetailsList.size() < 0) {
				valveDetailsList = valvesDao.getValveDetails();
			}

			for (Valves vDetails : valveDetailsList) {

				if (vDetails.getModel().contains(modelString.trim())) {
					String option = "";

					switch (nextTagName) {
					case "material":
						option = vDetails.getMaterial();
						break;
					case "end":
						option = vDetails.getEnd();
						break;
					case "maxInletPressure":
						option = vDetails.getMaxInletPressure();
						break;
					case "operations":
						option = vDetails.getOperation();
						break;
					case "pressureRatings":
						option = vDetails.getPressureRatings();
						break;
					case "seatAndSeals":
						option = vDetails.getSeatAndSeals();
						break;
					case "type":
						option = vDetails.getType();
						break;
					}

					if (null != option && !(option.trim().equals(""))) {
						dropdownContent.append("<option value=\"" + option + "\">" + option + "</option>" + "\n");
					}
				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dropdownContent.toString();
	}

	private String createBOQContent(Map<BOQData, String> inventoryMap, List<String> supplyRateList,
			List<String> erectionRateList, List<String> supplyAmtList, List<String> erectionAmtList,
			List<String> baseSupplyRateList, List<String> baseErectionRateList, String[] sheetNameList) {

		StringBuilder tableContent = new StringBuilder();

		String sheetNames = "";

		int startIndex = 0;
		int stopIndex = 0;
		boolean isFirstSheet = true;
		BOQHeader header = null;
		int index = 0;

		for (int k = 0; k < sheetNameList.length; k++) {

			if (k % 2 != 0) {
				continue;
			}
			sheetNames = sheetNames + sheetNameList[k] + "::";
			System.out.println("Working on sheet : " + sheetNameList[k]);
			if (k != 0) {
				startIndex = stopIndex + 1;
			}

			stopIndex = startIndex + Integer.parseInt(sheetNameList[k + 1]) - 1;

			int count = 0;

			int loopIndex = 0;

			for (Map.Entry<BOQData, String> entry : inventoryMap.entrySet()) {

				if (loopIndex < startIndex) {
					System.out.println(loopIndex + "<" + startIndex);
				} else if (loopIndex > stopIndex) {
					System.out.println(loopIndex + ">" + stopIndex);
				} else {

					if (entry.getKey() instanceof Inventory) {

						if (loopIndex == startIndex) {

							String start = HTMLElements.SHEET_DETAILS_START.replace("SHEET_NAME", sheetNameList[k]);

							if (isFirstSheet) {
								start = start.replace("IS_FIRST", "active show");
							}
							tableContent = tableContent.append(start);
						}

						if (erectionAmtList != null) {
							tableContent.append(createInventoryRow((Inventory) entry.getKey(),
									supplyRateList.get(index), erectionRateList.get(index), supplyAmtList.get(index),
									erectionAmtList.get(index), baseSupplyRateList.get(index),
									baseErectionRateList.get(index), (String) entry.getValue()));
						} else {
							tableContent.append(createInventoryRow((Inventory) entry.getKey(), "", "", "", "", "", "",
									(String) entry.getValue()));
						}

						System.out.println(
								"Added pipe with size : " + ((Inventory) entry.getKey()).getInventorySpec().getSize());
						if (loopIndex == stopIndex) {
							tableContent = tableContent.append(HTMLElements.SHEET_DETAILS_END);
						}

						index++;
					} else if (entry.getKey() instanceof BOQHeader) {
						tableContent.append(getBOQHeaderData((BOQHeader) entry.getKey()));
					}
				}

				if (count == (inventoryMap.entrySet().size() - 1)) {
					header = (BOQHeader) entry.getKey();
				}
				loopIndex++;
				count++;
			}

			isFirstSheet = false;
		}

		String headerDetails = getBOQHeaderData(header);
		headerDetails = headerDetails + "::";

		return headerDetails + sheetNames + tableContent.toString();
	}

	private String createInventoryRow(Inventory inv, String supplyRate, String erectionRate, String supplyAmt,
			String erectionAmt, String baseSupplyRate, String baseErectionRate, String availableQty) {
		String template = "<tr>" + "    <td><input type=\"checkbox\" name=\"checkbox\" class=\"checkbox\" /></td>"
				+ "    <td style=\"padding: 0px 0px;text-align:center;\">inventoryVal</td>"
				+ "    <td style=\"padding: 0px 0px;text-align:center;\">materialVal</td>"
				+ "    <td style=\"padding: 0px 0px;text-align:center;\">invenType</td>"
				+ "    <td style=\"padding: 0px 0px;text-align:center;\">manifMetodVal</td>"
				+ "    <td style=\"padding: 0px 0px;text-align:center;\">classOrGradeVal</td>"
				+ "    <td style=\"padding: 0px 0px;text-align:center;\">endsVal</td>"
				+ "    <td style=\"padding: 0px 0px;text-align:center;\">sizeVal</td>"
				/*
				 * +
				 * "    <td style=\"padding: 0px 0px;text-align:center;\">availableQty</td>"
				 */
				+ "    <td><input type=\"text\" style=\"width:45px;\" name=\"quantity\" value=\"quantityVal\" /></td>"
				+ "    <td><input type=\"text\" style=\"width:45px;\" name=\"baseSupplyRate\" dblclick=\"updateVal();\" value=\"baseSupplyRateVal\" id=\"baseSupplyRate\" /></td>"
				+ "    <td><input type=\"text\" style=\"width:45px;\" name=\"supplyRate\" value=\"supplyRate\" /></td>"
				+ "    <td><input type=\"text\" style=\"width:45px;\" name=\"baseErectionRate\" value=\"baseErectionRateVal\" id=\"baseErectionRate\" /></td>"
				+ "    <td><input type=\"text\" style=\"width:45px;\" name=\"erectionRate\" value=\"erectionRate\" /></td>"
				+ "    <td><input type=\"text\" style=\"width:45px;\" name=\"supplyAmount\" value=\"supplyAmount\" /></td>"
				+ "    <td><input type=\"text\" style=\"width:45px;\" name=\"erectionAmount\" value=\"erectionAmount\" /></td>"
				+ "    <input type=\"hidden\" style=\"width:45px;\" name=\"inventoryName\" value=\"inventoryVal\" />"
				+ "    <input type=\"hidden\" style=\"width:45px;\" name=\"material\" value=\"materialVal\" />"
				+ "    <input type=\"hidden\" style=\"width:45px;\" name=\"type\" value=\"invenType\" />"
				+ "    <input type=\"hidden\" style=\"width:45px;\" name=\"manifMetod\" value=\"manifMetodVal\" />"
				+ "    <input type=\"hidden\" style=\"width:45px;\" name=\"classOrGrade\" value=\"classOrGradeVal\" />"
				+ "    <input type=\"hidden\" style=\"width:45px;\" name=\"ends\" value=\"endsVal\" />"
				+ "    <input type=\"hidden\" style=\"width:45px;\" name=\"size\" value='sizeVal' />" + "    </tr>";

		String rowToReturn = template;
		rowToReturn = rowToReturn.replace("inventoryVal", inv.getInventorySpec().getInventoryName());
		rowToReturn = rowToReturn.replace("materialVal", inv.getInventorySpec().getMaterial());
		rowToReturn = rowToReturn.replace("invenType", inv.getInventorySpec().getType());
		rowToReturn = rowToReturn.replace("manifMetodVal", inv.getInventorySpec().getManifMethod());
		rowToReturn = rowToReturn.replace("classOrGradeVal", inv.getInventorySpec().getGradeOrClass());
		rowToReturn = rowToReturn.replace("endsVal", inv.getInventorySpec().getEnds());
		rowToReturn = rowToReturn.replace("sizeVal", inv.getInventorySpec().getSize());
		/* rowToReturn = rowToReturn.replace("availableQty", availableQty); */

		String requiredQuantity = Integer.toString(inv.getQuantity());
		rowToReturn = rowToReturn.replace("quantityVal", returnStringIfNULL(requiredQuantity));

		rowToReturn = rowToReturn.replace("value=\"supplyRate", "value=\"" + returnStringIfNULL(supplyRate));
		rowToReturn = rowToReturn.replace("value=\"erectionRate", "value=\"" + returnStringIfNULL(erectionRate));
		rowToReturn = rowToReturn.replace("value=\"supplyAmount", "value=\"" + returnStringIfNULL(supplyAmt));
		rowToReturn = rowToReturn.replace("value=\"erectionAmount", "value=\"" + returnStringIfNULL(erectionAmt));

		rowToReturn = rowToReturn.replace("baseSupplyRateVal", returnStringIfNULL(baseSupplyRate));
		rowToReturn = rowToReturn.replace("baseErectionRateVal", returnStringIfNULL(baseErectionRate));

		System.out.println("rowToReturn is : " + rowToReturn);
		return rowToReturn;
	}

	private String returnStringIfNULL(String param) {
		return param != null ? param : "";
	}

	private String createInvtTable(List<Inventory> inventoryList, List<AccessoryDetails> accessoryDetailsList) {

		StringBuilder tableContent = new StringBuilder();

		if (inventoryList != null && inventoryList.size() != 0) {
			int index = 0;
			for (Inventory inv : inventoryList) {

				tableContent.append(inventoryUtils.createInventoryRowTable(inv, true, String.valueOf(index), false));
				index++;
			}
		}

		if (accessoryDetailsList != null && accessoryDetailsList.size() != 0) {
			int accessoryNo = 0;
			for (AccessoryDetails inv : accessoryDetailsList) {
				tableContent.append(createInventoryRowTable(inv, String.valueOf(accessoryNo)));
				accessoryNo++;
			}
		}

		return tableContent.toString();
	}

	private String createInventoryRowTable(AccessoryDetails inv, String accessoryNo) {

		String accessoryId = "accessoryNo" + accessoryNo;

		String template = "<tr id=\"" + accessoryId + "\" >" + "<td></td><td>Inventory</td>" + "<td>Material</td>"
				+ "<td>Type</td>" + "<td>ManifMethod</td>" + "<td>gradeOrClass</td>" + "    <td>ends</td>"
				+ "    <td>size</td>"
				+ "<td><input type=\"text\" style=\"width:45px;\" name=\"quantity\" value=\"availableQuantity\"></td>"
				+ "    <td>purchaseRate</td>"
				+ "<td><select class='form-control currentProjectList' name='assignedProject'><option></option></td>"
				+ "<td>locationVal</td> <td><input type=\"submit\" class=\"btn btn-default\" onClick=\"accessoryStatusTo('"
				+ accessoryId + "');\" value=\"Assign\"></td>"
				+ "<input type=\"hidden\" name=\"accessoryName\" value=\"Inventory\">"
				+ "<input type=\"hidden\" name=\"desc1\" value=\"Material\">"
				+ "<input type=\"hidden\" name=\"desc2\" value=\"Type\">"
				+ "<input type=\"hidden\" name=\"desc3\" value=\"ManifMethod\">"
				+ "<input type=\"hidden\" name=\"desc4\" value=\"gradeOrClass\">"
				+ "<input type=\"hidden\" name=\"desc5\" value=\"ends\">"
				+ "<input type=\"hidden\" name=\"locationStr\" value=\"locationVal\">"
				+ "<input type=\"hidden\" name=\"status\" value=\"assigned\"> " + " </tr>";

		String rowToReturn = template;
		rowToReturn = rowToReturn.replace("Inventory", inv.getAccessoryName());
		rowToReturn = rowToReturn.replace("Material", inv.getDesc1());
		rowToReturn = rowToReturn.replace("Type", inv.getDesc2());
		rowToReturn = rowToReturn.replace("ManifMethod", inv.getDesc3());
		rowToReturn = rowToReturn.replace("gradeOrClass", inv.getDesc4());
		rowToReturn = rowToReturn.replace("ends", inv.getDesc5());
		rowToReturn = rowToReturn.replace("size", "-");

		rowToReturn = rowToReturn.replace("availableQuantity", inv.getQuantity());

		// Purchse Rate
		rowToReturn = rowToReturn.replace("purchaseRate", "-");
		rowToReturn = rowToReturn.replace("project", inv.getAssignedProject() != null ? inv.getAssignedProject() : "");
		rowToReturn = rowToReturn.replace("locationVal", inv.getLocation() != null ? inv.getLocation() : "");
		return rowToReturn;
	}

	private String getBOQHeaderData(BOQHeader boqHeaderData) {
		String template = "<input type=\"hidden\" name=\"client\"  	id=\"iDclient\"  	value=\"clientVal\" />"
				+ "<input type=\"hidden\" name=\"site\" 		id=\"iDsite\" 	value=\"siteVal\" />"
				+ "<input type=\"hidden\" name=\"project\" 	id=\"iDproject\" 	value=\"projectVal\" />"
				+ "<input type=\"hidden\" name=\"dName\" 		id=\"iDdName\" 	value=\"dNameVal\" />"
				+ "<input type=\"hidden\" name=\"utility\" 	id=\"iDutility\" 	value=\"utilityVal\" />"
				+ "<input type=\"hidden\" name=\"pressure\" 	id=\"iDpressure\" value=\"pressureVal\" />"
				+ "<input type=\"hidden\" name=\"temp\" 		id=\"iDtemp\" 	value=\"tempVal\" />"
				+ "<input type=\"hidden\" name=\"dNo\" 		id=\"iDdNo\" 		value=\"dNoVal\" />"
				+ "<input type=\"hidden\" name=\"sheetDetails\" value=\"sheetDetailsVal\" />";

		String htmlToReturn = template;

		htmlToReturn = htmlToReturn.replace("value=\"clientVal", "value=\"" + boqHeaderData.getClient());
		htmlToReturn = htmlToReturn.replace("value=\"siteVal", "value=\"" + boqHeaderData.getSite());
		htmlToReturn = htmlToReturn.replace("value=\"projectVal", "value=\"" + boqHeaderData.getProject());
		htmlToReturn = htmlToReturn.replace("value=\"dNameVal", "value=\"" + boqHeaderData.getdName());
		htmlToReturn = htmlToReturn.replace("value=\"utilityVal", "value=\"" + boqHeaderData.getUtility());
		htmlToReturn = htmlToReturn.replace("value=\"pressureVal", "value=\"" + boqHeaderData.getPressure());
		htmlToReturn = htmlToReturn.replace("value=\"tempVal", "value=\"" + boqHeaderData.getTemp());
		htmlToReturn = htmlToReturn.replace("value=\"dNoVal", "value=\"" + boqHeaderData.getdNo());
		htmlToReturn = htmlToReturn.replace("value=\"sheetDetailsVal", "value=\"" + boqHeaderData.getSheetDetails());

		return htmlToReturn;
	}

	private List<Inventory> convertBOQDetailsToInventory(ArrayList<BOQDetails> boqDetailsList) {
		List<Inventory> inventoryList = new ArrayList<Inventory>();

		for (BOQDetails boqDetails : boqDetailsList) {
			inventoryList
					.add(new Inventory(
							new InventorySpec(boqDetails.getInventoryName(), boqDetails.getMaterial(),
									boqDetails.getType(), boqDetails.getManifacturingMethod(),
									boqDetails.getClassOrGrade(), boqDetails.getEnds(), boqDetails.getSize(), "", ""),
							"", Integer.parseInt(boqDetails.getQuantity()), "", "", ""));
		}
		return inventoryList;
	}
}
