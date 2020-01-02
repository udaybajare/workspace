package com.invmgmt.controllers;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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
import com.invmgmt.entity.AccessoryDetails;
import com.invmgmt.entity.BOQDetails;
import com.invmgmt.entity.BOQHeader;
import com.invmgmt.entity.BOQLineData;
import com.invmgmt.entity.Inventory;
import com.invmgmt.entity.InventorySpec;
import com.invmgmt.entity.Project;
import com.invmgmt.excel.ExcelReader;
import com.invmgmt.excel.ExcelWriter;
import com.invmgmt.interfaces.BOQData;
import com.invmgmt.util.InventoryUtils;

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
	InventoryDao inventoryDao;

	@Autowired
	AccessoryDetailsDao accessoryDetailsDao;

	@Autowired
	BOQDetailsDao boqDao;

	@Autowired
	BOQHeaderDao boqHeaderDao;
	
	@Autowired
	ProjectDao projectDao;

	@Autowired
	BOQLineDataDao boqLineDataDao;

	@Autowired
	InventoryUtils inventoryUtils;

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
					int qty = inventoryDao.getAvailableQuantity((Inventory)inventoryList.get(i));
					inventoryQtyMap.put((Inventory)inventoryList.get(i), String.valueOf(qty));
				}
				else if(inventoryList.get(i) instanceof BOQHeader)
				{
					inventoryQtyMap.put((BOQHeader)inventoryList.get(i), null);
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
		
		BOQHeader header = boqHeaderDao.getBOQHeaderFromName(boqName);
		
		List<Inventory> inventoryList = null;
		List<String> supplyAmtList = new ArrayList<String>();
		List<String> erectionAmtList = new ArrayList<String>();
		List<String> baseSupplyAmtList = new ArrayList<String>();
		List<String> baseErectionAmtList = new ArrayList<String>();

		List<String> supplyRateList = new ArrayList<String>();
		List<String> erectionRateList = new ArrayList<String>();
		List<String> sheetNameList = new ArrayList<String>();

		String tableContent = "";
		try {
			//writer.writeExcel(boqDetails);

			inventoryList = convertBOQDetailsToInventory(boqDetails);

			for (BOQDetails boqDetail : boqDetails) {
				supplyAmtList.add(boqDetail.getSupplyAmount());
				erectionAmtList.add(boqDetail.getErectionAmount());
				supplyRateList.add(boqDetail.getSupplyRate());
				erectionRateList.add(boqDetail.getErectionRate());
				baseSupplyAmtList.add(boqDetail.getBaseSupplyRate());
				baseErectionAmtList.add(boqDetail.getBaseErectionRate());
				sheetNameList.add(boqDetail.getSheetName());
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
					erectionAmtList, baseSupplyAmtList, baseErectionAmtList, sheetNameList);

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
			accessoryDetailsList = accessoryDetailsDao.getAccessoryDetailsByStatus();

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

	@RequestMapping(value = { "/generateNew", "/generate" }, method = RequestMethod.POST)
	protected void generateBOQ(String projectId, String boqName, String[] inventoryName, String[] material,
			String[] type, String[] manifMetod, String[] classOrGrade, String[] ends, String[] size, String[] quantity,
			String[] supplyRate, String[] erectionRate, String[] supplyAmount, String[] erectionAmount,
			String[] baseErectionRate, String[] baseSupplyRate, String isOffer, String[] client, String[] site, String[] project, String[] dName, String[] utility, String[] pressure,
		    String[] temp, String[] dNo, String[] sheetDetails,
			RedirectAttributes redirectAttributes, HttpServletResponse response) throws IOException 
	{
		
		StringBuilder sheetdetailsStr = new StringBuilder();
		int accessIndex = client.length - 1;
		if(null!=sheetDetails && 0!=sheetDetails.length)
		{
			for(String name : sheetDetails)
			{
				sheetdetailsStr.append(name+",");
			}	
		}		
		
		BOQHeader header = new BOQHeader(inventoryUtils.blankIfNull(client,accessIndex), inventoryUtils.blankIfNull(site,accessIndex), inventoryUtils.blankIfNull(project,accessIndex), inventoryUtils.blankIfNull(dName,accessIndex), inventoryUtils.blankIfNull(utility,accessIndex), inventoryUtils.blankIfNull(pressure,accessIndex),
				inventoryUtils.blankIfNull(temp,accessIndex), inventoryUtils.blankIfNull(dNo,accessIndex), boqName, sheetdetailsStr.toString());

		System.out.println("Sheet Details are : "+header.getSheetDetails());
		ArrayList<String> boqRevisions = boqDao.getMatchingBOQNames(boqName + "_R", projectId);

		ArrayList<String> quorationRevisions = boqDao.getMatchingBOQNames("Inquiry_" + boqName + "_R", projectId);

		String boqNameRevisionStr = "";
		ArrayList<String> boqNames = new ArrayList<String>();
		ArrayList<String> quotationNames = new ArrayList<String>();

		if (!Boolean.valueOf(isOffer)) {
			for (int i = 1; i < 20; i++) {
				boqNameRevisionStr = boqName + "_R" + String.valueOf(i);
				if (boqRevisions.contains(boqNameRevisionStr))
					continue;
				else
					break;
			}
		} else {
			for (int i = 1; i < 20; i++) {
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

		ArrayList<BOQLineData> boqInventoryDetails = getBOQLineDataList(material, type, ends, classOrGrade, inventoryName);
		byte[] excelByts = null;
		try {
			if (Boolean.valueOf(isOffer)) {
				supplyRate = new String[] {};
				erectionRate = new String[] {};
				supplyAmount = new String[] {};
				erectionAmount = new String[] {};
			}
			excelByts = writer.writeExcel(boqInventoryDetails, size, quantity, supplyRate, erectionRate, supplyAmount,
					erectionAmount, boqNameRevisionStr, header);

			ArrayList<BOQDetails> boqInventoryDetailsList = getBOQDetailsList(projectId, boqNameRevisionStr,
					inventoryName, material, type, manifMetod, classOrGrade, ends, size, quantity, supplyRate,
					erectionRate, supplyAmount, erectionAmount, baseErectionRate, baseSupplyRate, header.getSheetDetails());

			for (BOQDetails boqDetails : boqInventoryDetailsList) {
				boqDao.saveBOQ(boqDetails);
			}
			
			header.setBoqName(boqNameRevisionStr);
			
			boqHeaderDao.saveBOQHeader(header);

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

		/*
		 * if (boqNames != null && !boqNames.equals("")) {
		 * mav.addObject("boqNameList", String.join(",", boqNames)); } else {
		 * mav.addObject("boqNameList", ""); }
		 * 
		 * if (quotationNames != null && !quotationNames.equals("")) {
		 * mav.addObject("quotationNamesList", String.join(",",
		 * quotationNames)); } else { mav.addObject("quotationNamesList", ""); }
		 * 
		 * mav.addObject("projectId", projectId); mav.addObject("projectName",
		 * project.getProjectName()); mav.addObject("projectDesc",
		 * project.getProjectDesc());
		 * 
		 * return mav;
		 */
		response.setHeader("Content-disposition", "attachment; filename=" + boqNameRevisionStr + ".xls");

		OutputStream out = response.getOutputStream();

		byte[] buffer = excelByts; // use bigger if you want
		int length = buffer.length;

		System.out.println("Buffer length is : " + length);
		out.write(buffer, 0, length);
		out.close();

	}

	private ArrayList<BOQLineData> getBOQLineDataList(String[] material, String[] type, String[] ends,
			String[] classOrGrade, String[] inventoryName) {
		ArrayList<BOQLineData> boqInventoryDetails = new ArrayList<>();
		int noOfEntries = material.length;

		for (int i = 0; i < noOfEntries; i++) {
			System.out.println("Getting details for : " + material[i] + " , " + type[i] + " , " + classOrGrade[i]
					+ " , " + ends[i]);

			if (material[i] != null && !material[i].trim().equals("")) {
				boqInventoryDetails.add(
						getBOQLineData(material[i].trim(), type[i].trim(), classOrGrade[i].trim(), ends[i].trim(), inventoryName[i].trim()));
			}
		}

		return boqInventoryDetails;
	}

	private BOQLineData getBOQLineData(String material, String type, String classOrGrade, String ends, String inventoryName) {
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

		BOQLineData boqLineData = boqLineDataDao.getLineData(material, inventoryName);

		boqLineData.setEndsLine(boqLineData.getEndsLine().replaceAll("ENDS_VAL", ends));
		boqLineData.setGrdLine(boqLineData.getGrdLine().replaceAll("CLASS_VAL", classOrGrade));
		boqLineData.setGrdLine(boqLineData.getGrdLine().replaceAll("TYPE_VAL", type));

		return boqLineData;
	}

	private ArrayList<BOQDetails> getBOQDetailsList(String projectId, String boqName, String[] inventoryName,
			String[] material, String[] type, String[] manifMetod, String[] classOrGrade, String[] ends, String[] size,
			String[] quantity, String[] supplyRate, String[] erectionRate, String[] supplyAmount,
			String[] erectionAmount, String[] baseErectionRate, String[] baseSupplyRate, String sheetDetails) {
		int noOfEntries = inventoryName.length;
		ArrayList<BOQDetails> boqInventoryDetails = new ArrayList<>();

		String[] sheetDetailsArray = sheetDetails.split(",");
		ArrayList<String> sheetNames = new ArrayList<String>();
		ArrayList<Integer> sheetInventoryCount = new ArrayList<Integer>();
		
		int total = 0;
		
		for(int i=0;i<sheetDetailsArray.length;i++)
		{
			if(i%2==0)
			{
				sheetNames.add(sheetDetailsArray[i]);
			}
			else
			{
				total = total + Integer.valueOf(sheetDetailsArray[i]);
				
				sheetInventoryCount.add(total);
			}
			 	
		}		
		String sheetName = "";
		for (int i = 0; i < noOfEntries; i++) {

			for (int j = 0; j < sheetInventoryCount.size(); j++) {
				if (i < sheetInventoryCount.get(j)) {
					sheetName = sheetNames.get(j);
					break;
				}
			}

			boqInventoryDetails.add(new BOQDetails(projectId, boqName, inventoryName[i], material[i], type[i],
					manifMetod[i], classOrGrade[i], ends[i], size[i], quantity[i],
					supplyRate.length > 0 ? supplyRate[i] : "", erectionRate.length > 0 ? erectionRate[i] : "",
					supplyAmount.length > 0 ? supplyAmount[i] : "", erectionAmount.length > 0 ? erectionAmount[i] : "",
					baseErectionRate.length > 0 ? baseErectionRate[i] : "",
					baseSupplyRate.length > 0 ? baseSupplyRate[i] : "", sheetName));
			System.out.println(boqInventoryDetails.get(i).toString());
		}

		return boqInventoryDetails;
	}

	@RequestMapping(value = "/getDropdown", method = RequestMethod.POST)
	public @ResponseBody String getNextDropDownContent(@RequestParam(value = "value", required = true) String value,
			@RequestParam(value = "currentTag", required = true) String currentTag,
			@RequestParam(value = "nextTagName", required = true) String nextTagName,
			@RequestParam(value = "inventory", required = true) String inventory) {

		StringBuilder dropdownContent = new StringBuilder();

		try {

			ArrayList<String> results = (ArrayList<String>) mappingsDao.getAssociatedOptions(currentTag, value,
					nextTagName, inventory);

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

	private String createBOQContent(Map<BOQData, String> inventoryMap, List<String> supplyRateList,
			List<String> erectionRateList, List<String> supplyAmtList, List<String> erectionAmtList,
			List<String> baseSupplyRateList, List<String> baseErectionRateList, List<String> sheetNameList) {

		StringBuilder tableContent = new StringBuilder();
		int index = 0;

		for (Map.Entry<BOQData, String> entry : inventoryMap.entrySet()) {
			if (entry.getKey() instanceof Inventory) 
			{
				if (erectionAmtList != null) {
					tableContent.append(createInventoryRow((Inventory) entry.getKey(), supplyRateList.get(index),
							erectionRateList.get(index), supplyAmtList.get(index), erectionAmtList.get(index),
							baseSupplyRateList.get(index), baseErectionRateList.get(index), (String) entry.getValue()));
				} else {
					tableContent.append(createInventoryRow((Inventory) entry.getKey(), "", "", "", "", "", "",
							(String) entry.getValue()));
				}

				index++;
			} else if (entry.getKey() instanceof BOQHeader) {
				tableContent.append(getBOQHeaderData((BOQHeader) entry.getKey()));
			}
		}

		return tableContent.toString();
	}

	private String createInventoryRow(Inventory inv, String supplyRate, String erectionRate, String supplyAmt,
			String erectionAmt, String baseSupplyRate, String baseErectionRate, String availableQty) {
		String template = "<tr>"
				+ "    <td><input type=\"checkbox\" name=\"checkbox\" class=\"checkbox\" /></td>"
				+ "    <td style=\"padding: 0px 0px;\">inventoryVal</td>"
				+ "    <td style=\"padding: 0px 0px;\">materialVal</td>"
				+ "    <td style=\"padding: 0px 0px;\">invenType</td>"
				+ "    <td style=\"padding: 0px 0px;\">manifMetodVal</td>"
				+ "    <td style=\"padding: 0px 0px;\">classOrGradeVal</td>"
				+ "    <td style=\"padding: 0px 0px;\">endsVal</td>"
				+ "    <td style=\"padding: 0px 0px;\">sizeVal</td>"
				+ "    <td style=\"padding: 0px 0px;\">availableQty</td>"
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
				+ "    <input type=\"hidden\" style=\"width:45px;\" name=\"size\" value=\"sizeVal\" />"
				+ "    </tr>";

		String rowToReturn = template;
		rowToReturn = rowToReturn.replace("inventoryVal", inv.getInventorySpec().getInventoryName());
		rowToReturn = rowToReturn.replace("materialVal", inv.getInventorySpec().getMaterial());
		rowToReturn = rowToReturn.replace("invenType", inv.getInventorySpec().getType());
		rowToReturn = rowToReturn.replace("manifMetodVal", inv.getInventorySpec().getManifMethod());
		rowToReturn = rowToReturn.replace("classOrGradeVal", inv.getInventorySpec().getGradeOrClass());
		rowToReturn = rowToReturn.replace("endsVal", inv.getInventorySpec().getEnds());
		rowToReturn = rowToReturn.replace("sizeVal", inv.getInventorySpec().getSize());
		rowToReturn = rowToReturn.replace("availableQty", availableQty);

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

		int index = 0;
		for (Inventory inv : inventoryList) {

			tableContent.append(inventoryUtils.createInventoryRowTable(inv, true, String.valueOf(index), false));
		}
		
		int accessoryNo = 0;
		for (AccessoryDetails inv : accessoryDetailsList) 
		{
			tableContent.append(createInventoryRowTable(inv, String.valueOf(accessoryNo)));
			accessoryNo++;
		}

		return tableContent.toString();
	}

	private String createInventoryRowTable(AccessoryDetails inv, String accessoryNo) {
		
		String accessoryId = "accessoryNo"+accessoryNo;
		
		String template = "<tr id=\""+accessoryId+"\" >" 
				+ "<td></td><td>Inventory</td>" 
				+ "<td>Material</td>" 
				+ "<td>Type</td>"
				+ "<td>ManifMethod</td>" + "<td>gradeOrClass</td>" + "    <td>ends</td>" + "    <td>size</td>"
				+ "<td><input type=\"text\" name=\"quantity\" value=\"availableQuantity\"></td>" + "    <td>purchaseRate</td>" 
				+ "<td><select class='form-control currentProjectList' name='assignedProject'><option></option></td>"
				+ "<td>locationVal</td> <td><input type=\"submit\" class=\"btn btn-default\" onClick=\"accessoryStatusTo('"+accessoryId+"');\" value=\"Assign\"></td>"
				+ "<input type=\"hidden\" name=\"accessoryName\" value=\"Inventory\">"
				+ "<input type=\"hidden\" name=\"desc1\" value=\"Material\">"
				+ "<input type=\"hidden\" name=\"desc2\" value=\"Type\">"
				+ "<input type=\"hidden\" name=\"desc3\" value=\"ManifMethod\">"
				+ "<input type=\"hidden\" name=\"desc4\" value=\"gradeOrClass\">"
				+ "<input type=\"hidden\" name=\"desc5\" value=\"ends\">"
				+ "<input type=\"hidden\" name=\"locationStr\" value=\"locationVal\">"
				+ "<input type=\"hidden\" name=\"status\" value=\"assigned\"> "
				+ " </tr>";

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
		String template =
				  "<input type=\"hidden\" name=\"client\"  	id=\"iDclient\"  	value=\"clientVal\" />"
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
							"", Integer.parseInt(boqDetails.getQuantity()), ""));
		}
		return inventoryList;
	}
}
