package com.invmgmt.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
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
    ProjectDao projectDao;

    @Autowired
    BOQLineDataDao boqLineDataDao;

    @Autowired
    InventoryUtils inventoryUtils;

    @PostMapping("import")
    public @ResponseBody String getBOQLine(@RequestParam("file") MultipartFile file) {

	List<Inventory> inventoryList = null;
	Map<BOQData, String> inventoryQtyMap = new HashMap<BOQData, String>();

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
		    int qty = inventoryDao.getAvailableQuantity(inventoryList.get(i));
		    System.out.println("Available Qty is : " + qty);
		    System.out.println(inventoryDao.getAvailableQuantity(inventoryList.get(i)));
		    inventoryQtyMap.put(inventoryList.get(i), String.valueOf(qty));
		}

	    }
	} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
	    e.printStackTrace();
	}

	String tableContent = createBOQContent(inventoryQtyMap, null, null, null, null, null, null);

	return tableContent;
    }

    @RequestMapping(value = "/downloadBoq", method = RequestMethod.GET)
    protected @ResponseBody String downloadBOQ(String boqName, String projectId) {
	ArrayList<BOQDetails> boqDetails = boqDao.getBOQFromName(boqName, projectId);
	List<Inventory> inventoryList = null;
	List<String> supplyAmtList = new ArrayList<String>();
	List<String> erectionAmtList = new ArrayList<String>();
	List<String> baseSupplyAmtList = new ArrayList<String>();
	List<String> baseErectionAmtList = new ArrayList<String>();

	List<String> supplyRateList = new ArrayList<String>();
	List<String> erectionRateList = new ArrayList<String>();

	String tableContent = "";
	try {
	    writer.writeExcel(boqDetails);

	    inventoryList = convertBOQDetailsToInventory(boqDetails);

	    for (BOQDetails boqDetail : boqDetails) {
		supplyAmtList.add(boqDetail.getSupplyAmount());
		erectionAmtList.add(boqDetail.getErectionAmount());
		supplyRateList.add(boqDetail.getSupplyRate());
		erectionRateList.add(boqDetail.getErectionRate());
		baseSupplyAmtList.add(boqDetail.getBaseSupplyRate());
		baseErectionAmtList.add(boqDetail.getBaseErectionRate());
	    }

	    Map<BOQData, String> inventoryQtyMap = new HashMap<BOQData, String>();

	    for (int i = 0; i < inventoryList.size(); i++) {
		if (inventoryList.get(i) instanceof Inventory) {
		    int qty = inventoryDao.getAvailableQuantity(inventoryList.get(i));
		    System.out.println("Available Qty is : " + qty);
		    inventoryQtyMap.put(inventoryList.get(i), String.valueOf(qty));
		}

	    }
	    tableContent = createBOQContent(inventoryQtyMap, supplyRateList, erectionRateList, supplyAmtList,
		    erectionAmtList, baseSupplyAmtList, baseErectionAmtList);

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
	    accessoryDetailsList = accessoryDetailsDao.getAvailableInventory();

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
    protected void generateBOQ(String projectId, String boqName, String[] inventoryName,
	    String[] material, String[] type, String[] manifMetod, String[] classOrGrade, String[] ends, String[] size,
	    String[] quantity, String[] supplyRate, String[] erectionRate, String[] supplyAmount,
	    String[] erectionAmount, String[] baseErectionRate, String[] baseSupplyRate, String isOffer,
	    BOQHeader header, RedirectAttributes redirectAttributes,HttpServletResponse response) throws IOException {

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

	ArrayList<BOQLineData> boqInventoryDetails = getBOQLineDataList(material, type, ends, classOrGrade);
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
		    erectionRate, supplyAmount, erectionAmount, baseErectionRate, baseSupplyRate);

	    for (BOQDetails boqDetails : boqInventoryDetailsList) {
		boqDao.saveBOQ(boqDetails);
	    }

	    boqNames = boqDao.getAssociatedBOQNames(String.valueOf(projectId));

	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	Project project = projectDao.getProject(Integer.valueOf(projectId));

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
	response.setHeader("Content-disposition","attachment; filename=" + boqNameRevisionStr+".xls");
	
	OutputStream out = response.getOutputStream();

	byte[] buffer = excelByts; // use bigger if you want
	int length = buffer.length;

	System.out.println("Buffer length is : "+length);
	out.write(buffer, 0, length);
	out.close();

    }

    private ArrayList<BOQLineData> getBOQLineDataList(String[] material, String[] type, String[] ends,
	    String[] classOrGrade) {
	ArrayList<BOQLineData> boqInventoryDetails = new ArrayList<>();
	int noOfEntries = material.length;

	for (int i = 0; i < noOfEntries; i++) {
	    System.out.println("Getting details for : " + material[i] + " , " + type[i] + " , " + classOrGrade[i]
		    + " , " + ends[i]);

	    if (material[i] != null && !material[i].trim().equals("")) {
		boqInventoryDetails.add(
			getBOQLineData(material[i].trim(), type[i].trim(), classOrGrade[i].trim(), ends[i].trim()));
	    }
	}

	return boqInventoryDetails;
    }

    private BOQLineData getBOQLineData(String material, String type, String classOrGrade, String ends) {
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

	BOQLineData boqLineData = boqLineDataDao.getLineData(material);

	boqLineData.setEndsLine(boqLineData.getEndsLine().replaceAll("ENDS_VAL", ends));
	boqLineData.setGrdLine(boqLineData.getGrdLine().replaceAll("CLASS_VAL", classOrGrade));
	boqLineData.setGrdLine(boqLineData.getGrdLine().replaceAll("TYPE_VAL", type));

	return boqLineData;
    }

    private ArrayList<BOQDetails> getBOQDetailsList(String projectId, String boqName, String[] inventoryName,
	    String[] material, String[] type, String[] manifMetod, String[] classOrGrade, String[] ends, String[] size,
	    String[] quantity, String[] supplyRate, String[] erectionRate, String[] supplyAmount,
	    String[] erectionAmount, String[] baseErectionRate, String[] baseSupplyRate) {
	int noOfEntries = inventoryName.length;
	ArrayList<BOQDetails> boqInventoryDetails = new ArrayList<>();

	for (int i = 0; i < noOfEntries; i++) {
	    boqInventoryDetails.add(new BOQDetails(projectId, boqName, inventoryName[i], material[i], type[i],
		    manifMetod[i], classOrGrade[i], ends[i], size[i], quantity[i],
		    supplyRate.length > 0 ? supplyRate[i] : "", erectionRate.length > 0 ? erectionRate[i] : "",
		    supplyAmount.length > 0 ? supplyAmount[i] : "", erectionAmount.length > 0 ? erectionAmount[i] : "",
		    baseErectionRate.length > 0 ? baseErectionRate[i] : "",
		    baseSupplyRate.length > 0 ? baseSupplyRate[i] : ""));
	    System.out.println(boqInventoryDetails.get(i).toString());
	}

	return boqInventoryDetails;
    }

    @RequestMapping(value = "/getDropdown", method = RequestMethod.POST)
    public @ResponseBody String getNextDropDownContent(@RequestParam(value = "value", required = true) String value,
	    @RequestParam(value = "currentTag", required = true) String currentTag,
	    @RequestParam(value = "nextTagName", required = true) String nextTagName) {

	StringBuilder dropdownContent = new StringBuilder();

	try {

	    ArrayList<String> results = (ArrayList<String>) mappingsDao.getAssociatedOptions(currentTag, value,
		    nextTagName);

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
	    List<String> baseSupplyRateList, List<String> baseErectionRateList) {

	StringBuilder tableContent = new StringBuilder();
	int index = 0;

	for (Map.Entry<BOQData, String> entry : inventoryMap.entrySet()) {
	    if (entry.getKey() instanceof Inventory) {
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
		+ "    <td style=\"padding:0rem\"><input type=\"checkbox\" name=\"checkbox\" class=\"checkbox\" /></td>"
		+ "    <td style=\"padding: 0px 0px;\">inventoryVal</td>"
		+ "    <td style=\"padding: 0px 0px;\">materialVal</td>"
		+ "    <td style=\"padding: 0px 0px;\">invenType</td>"
		+ "    <td style=\"padding: 0px 0px;\">manifMetodVal</td>"
		+ "    <td style=\"padding: 0px 0px;\">classOrGradeVal</td>"
		+ "    <td style=\"padding: 0px 0px;\">endsVal</td>"
		+ "    <td style=\"padding: 0px 0px;\">sizeVal</td>"
		+ "    <td style=\"padding: 0px 0px;\">availableQty</td>"
		+ "    <td><input type=\"text\" name=\"quantity\" value=\"quantityVal\" /></td>"
		+ "    <td><input type=\"text\" name=\"baseSupplyRate\" dblclick=\"updateVal();\" value=\"baseSupplyRateVal\" id=\"baseSupplyRate\" /></td>"
		+ "    <td><input type=\"text\" name=\"supplyRate\" value=\"supplyRate\" /></td>"
		+ "    <td><input type=\"text\" name=\"baseErectionRate\" value=\"baseErectionRateVal\" id=\"baseErectionRate\" /></td>"
		+ "    <td><input type=\"text\" name=\"erectionRate\" value=\"erectionRate\" /></td>"
		+ "    <td><input type=\"text\" name=\"supplyAmount\" value=\"supplyAmount\" /></td>"
		+ "    <td><input type=\"text\" name=\"erectionAmount\" value=\"erectionAmount\" /></td>"
		+ "    <td><input type=\"hidden\" name=\"inventoryName\" value=\"inventoryVal\" /></td>"
		+ "    <td><input type=\"hidden\" name=\"material\" value=\"materialVal\" /></td>"
		+ "    <td><input type=\"hidden\" name=\"type\" value=\"invenType\" /></td>"
		+ "    <td><input type=\"hidden\" name=\"manifMetod\" value=\"manifMetodVal\" /></td>"
		+ "    <td><input type=\"hidden\" name=\"classOrGrade\" value=\"classOrGradeVal\" /></td>"
		+ "    <td><input type=\"hidden\" name=\"ends\" value=\"endsVal\" /></td>"
		+ "    <td><input type=\"hidden\" name=\"size\" value=\"sizeVal\" /></td>" + "   </tr>";

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

	for (Inventory inv : inventoryList) {

	    tableContent.append(inventoryUtils.createInventoryRowTable(inv));
	}

	for (AccessoryDetails inv : accessoryDetailsList) {

	    tableContent.append(createInventoryRowTable(inv));
	}

	return tableContent.toString();
    }

    private String createInventoryRowTable(AccessoryDetails inv) {
	String template = "<tr>" + "<td></td><td>Inventory</td>" + "    <td>Material</td>" + "    <td>Type</td>"
		+ "    <td>ManifMethod</td>" + "<td>gradeOrClass</td>" + "    <td>ends</td>" + "    <td>size</td>"
		+ "    <td>availableQuantity</td>" + "    <td>purchaseRate</td>" + "<td>project</td>"
		+ "<td>location</td>";

	String rowToReturn = template;
	rowToReturn = rowToReturn.replace("Inventory", inv.getAccessoryName());
	rowToReturn = rowToReturn.replace("Material", inv.getDesc1());
	rowToReturn = rowToReturn.replace("Type", inv.getDesc2());
	rowToReturn = rowToReturn.replace("ManifMethod", inv.getDesc3());
	rowToReturn = rowToReturn.replace("gradeOrClass", inv.getDesc4());
	rowToReturn = rowToReturn.replace("ends", inv.getDesc5());
	rowToReturn = rowToReturn.replace("size", "-");

	rowToReturn = rowToReturn.replace("availableQuantity", "-");

	// Purchse Rate
	rowToReturn = rowToReturn.replace("purchaseRate", "-");
	rowToReturn = rowToReturn.replace("project", inv.getAssignedProject() != null ? inv.getAssignedProject() : "");
	rowToReturn = rowToReturn.replace("location", inv.getLocation() != null ? inv.getLocation() : "");
	return rowToReturn;
    }

    private String getBOQHeaderData(BOQHeader boqHeaderData) {
	String template = "<input type=\"hidden\" name=\"client\" value=\"client\" />"
		+ "<input type=\"hidden\" name=\"site\" value=\"site\" />"
		+ "<input type=\"hidden\" name=\"project\" value=\"project\" />"
		+ "<input type=\"hidden\" name=\"dName\" value=\"dName\" />"
		+ "<input type=\"hidden\" name=\"utility\" value=\"utility\" />"
		+ "<input type=\"hidden\" name=\"pressure\" value=\"pressure\" />"
		+ "<input type=\"hidden\" name=\"temp\" value=\"temp\" />"
		+ "<input type=\"hidden\" name=\"dNo\" value=\"dNo\" />";

	String htmlToReturn = template;

	htmlToReturn.replace("value=\"client", "value=\"" + boqHeaderData.getClient());
	htmlToReturn.replace("value=\"site", "value=\"" + boqHeaderData.getSite());
	htmlToReturn.replace("value=\"project", "value=\"" + boqHeaderData.getProject());
	htmlToReturn.replace("value=\"dName", "value=\"" + boqHeaderData.getdName());
	htmlToReturn.replace("value=\"utility", "value=\"" + boqHeaderData.getUtility());
	htmlToReturn.replace("value=\"pressure", "value=\"" + boqHeaderData.getPressure());
	htmlToReturn.replace("value=\"temp", "value=\"" + boqHeaderData.getTemp());
	htmlToReturn.replace("value=\"dNo", "value=\"" + boqHeaderData.getdNo());

	return htmlToReturn;
    }

    private List<Inventory> convertBOQDetailsToInventory(ArrayList<BOQDetails> boqDetailsList) {
	List<Inventory> inventoryList = new ArrayList<Inventory>();

	for (BOQDetails boqDetails : boqDetailsList) {
	    inventoryList
		    .add(new Inventory(
			    new InventorySpec(boqDetails.getInventoryName(), boqDetails.getMaterial(),
				    boqDetails.getType(), boqDetails.getManifacturingMethod(),
				    boqDetails.getClassOrGrade(), boqDetails.getEnds(), boqDetails.getSize()),
			    "", Integer.parseInt(boqDetails.getQuantity()), "", "", ""));
	}
	return inventoryList;
    }
}
