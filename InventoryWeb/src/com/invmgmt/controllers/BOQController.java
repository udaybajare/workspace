package com.invmgmt.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.invmgmt.dao.BOQDetailsDao;
import com.invmgmt.dao.BOQLineDataDao;
import com.invmgmt.dao.InventoryDao;
import com.invmgmt.dao.InventoryDefinitionDao;
import com.invmgmt.dao.MappingsDao;
import com.invmgmt.dao.ProjectDao;
import com.invmgmt.entity.BOQDetails;
import com.invmgmt.entity.BOQHeader;
import com.invmgmt.entity.BOQLineData;
import com.invmgmt.entity.Inventory;
import com.invmgmt.entity.InventorySpec;
import com.invmgmt.entity.Project;
import com.invmgmt.excel.ExcelReader;
import com.invmgmt.excel.ExcelWriter;

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
    BOQDetailsDao boqDao;

    @Autowired
    ProjectDao projectDao;

    @Autowired
    BOQLineDataDao boqLineDataDao;

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public @ResponseBody String getBOQLine(String location) {

	List inventoryList = null;

	try {
	    inventoryList = reader.readFile(location);
	}
	catch (EncryptedDocumentException | InvalidFormatException | IOException e) 
	{
	    e.printStackTrace();
	}

	String tableContent = createBOQContent(inventoryList, null, null, null, null, null, null);
	
	return tableContent;
    }

    @RequestMapping(value = "/downloadBoq", method = RequestMethod.GET)
    protected @ResponseBody String downloadBOQ(String boqName, String projectId) 
    {
	ArrayList<BOQDetails> boqDetails = boqDao.getBOQFromName(boqName, projectId);
	List inventoryList = null;
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

	    tableContent = createBOQContent(inventoryList, supplyRateList, erectionRateList, supplyAmtList,
		    erectionAmtList, baseSupplyAmtList, baseErectionAmtList);

	} catch (Exception ex) {
	    ex.printStackTrace();
	}

	return tableContent;
    }

    @RequestMapping(value = "/showInventory", method = RequestMethod.POST)
    public @ResponseBody String getAvailableInventory() {

	List inventoryList = null;
	try {
	    inventoryList = inventoryDao.getAvailableInventory();

	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	String tableContent = createInvtTable(inventoryList);
	return tableContent;
    }

    @RequestMapping(value = "/DEPRICATED", method = RequestMethod.POST)
    protected String generateBOQ(String projectId, String boqName, String[] standardType, String[] grade,
	    String[] schedule, String[] materialSpec, String[] ends, String[] size, String[] quantity,
	    String[] supplyRate, String[] erectionRate, String[] supplyAmount, String[] erectionAmount,
	    RedirectAttributes redirectAttributes) {/*
						     * ArrayList<String>
						     * boqRevisions = boqDao.
						     * getMatchingBOQNames(
						     * boqName+"_R");
						     * 
						     * String boqNameRevisionStr
						     * = "";
						     * 
						     * for(int i=1;i<20;i++) {
						     * boqNameRevisionStr =
						     * boqName+"_R"+String.
						     * valueOf(i);
						     * if(boqRevisions.contains(
						     * boqNameRevisionStr))
						     * continue; else break; }
						     * 
						     * System.out.
						     * println("Saving BOQ with name : "
						     * +boqNameRevisionStr);
						     * 
						     * ArrayList<BOQDetails>
						     * boqInventoryDetails =
						     * getBOQDetailsList(
						     * projectId,
						     * boqNameRevisionStr,
						     * standardType, grade,
						     * schedule, materialSpec,
						     * ends, size, quantity,
						     * supplyRate, erectionRate,
						     * supplyAmount,
						     * erectionAmount);
						     * 
						     * try { writer.writeExcel(
						     * boqInventoryDetails);
						     * 
						     * for(BOQDetails boqDetails
						     * : boqInventoryDetails) {
						     * boqDao.saveBOQ(boqDetails
						     * ); } } catch (IOException
						     * e) { // TODO
						     * Auto-generated catch
						     * block
						     * e.printStackTrace(); }
						     * 
						     * Project project =
						     * projectDao.getProject(
						     * Integer.valueOf(projectId
						     * ));
						     * 
						     * redirectAttributes.
						     * addAttribute("projectId",
						     * projectId);
						     * redirectAttributes.
						     * addAttribute(
						     * "projectName",project.
						     * getProjectName());
						     * redirectAttributes.
						     * addAttribute(
						     * "projectDesc",project.
						     * getProjectDesc());
						     * 
						     * return
						     * "redirect:/projectDetails";
						     */
	return "";
    }

    @RequestMapping(value = { "/generateNew", "/generate" }, method = RequestMethod.POST)
    protected ModelAndView generateBOQ(String projectId, String boqName, String[] inventoryName, String[] material,
	    String[] type, String[] manifMetod, String[] classOrGrade, String[] ends, String[] size, String[] quantity,
	    String[] supplyRate, String[] erectionRate, String[] supplyAmount, String[] erectionAmount,
	    String[] baseErectionRate, String[] baseSupplyRate, String isOffer, RedirectAttributes redirectAttributes) 
    {
	
	ArrayList<String> boqRevisions = boqDao.getMatchingBOQNames(boqName + "_R", projectId);

	ArrayList<String> quorationRevisions = boqDao.getMatchingBOQNames("Quotation_"+boqName + "_R", projectId);

	String boqNameRevisionStr = "";
	ArrayList<String> boqNames = null;
	ArrayList<String> quotationNames = new ArrayList<String>();
	
	if(!Boolean.valueOf(isOffer))
	{
	    for (int i = 1; i < 20; i++) {
		    boqNameRevisionStr = boqName + "_R" + String.valueOf(i);
		    if (boqRevisions.contains(boqNameRevisionStr))
			continue;
		    else
			break;
		}    
	}
	else
	{
	    for (int i = 1; i < 20; i++) {
		    boqNameRevisionStr = "Quotation_"+ boqName + "_R" + String.valueOf(i);
		    if (quorationRevisions.contains(boqNameRevisionStr))
			continue;
		    else
			break;
		}
	}
	

	if(boqName.endsWith("_final"))
	{
	    boqNameRevisionStr = boqName;
	}	
	/*else if(Boolean.valueOf(isOffer))
	{
	    boqNameRevisionStr = "Quotation_"+boqNameRevisionStr;
	}*/

	System.out.println("Saving BOQ with name : " + boqNameRevisionStr);

	ArrayList<BOQLineData> boqInventoryDetails = getBOQLineDataList(material, type, ends, classOrGrade);

	try {
	    writer.writeExcel(boqInventoryDetails, size, quantity, supplyRate, erectionRate, supplyAmount,
		    erectionAmount,boqNameRevisionStr);

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

	ModelAndView mav = new ModelAndView("redirect:/projectDetails");
	
	for(int i = 0; i < boqNames.size(); i++)
	{
	    if(boqNames.get(i)!=null&&boqNames.get(i).startsWith("Quotation_"))
	    {
		quotationNames.add(boqNames.get(i));
		boqNames.remove(i);
	    }
	    
	}
	
	
	if (boqNames != null && !boqNames.equals(""))
	{
	    mav.addObject("boqNameList", String.join(",", boqNames));
	}
	else 
	{
	    mav.addObject("boqNameList", "");
	}

	if (quotationNames != null && !quotationNames.equals(""))
	{
	    mav.addObject("quotationNamesList", String.join(",", quotationNames));
	}
	else 
	{
	    mav.addObject("quotationNamesList", "");
	}
	
	mav.addObject("projectId", projectId);
	mav.addObject("projectName", project.getProjectName());
	mav.addObject("projectDesc", project.getProjectDesc());

	return mav;
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
		    supplyAmount.length > 0 ? supplyAmount[i] : "",
		    erectionAmount.length > 0 ? erectionAmount[i] : "", baseErectionRate[i], baseSupplyRate[i]));
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

    private String createBOQContent(List inventoryList, List<String> supplyRateList,
	    List<String> erectionRateList, List<String> supplyAmtList, List<String> erectionAmtList,List<String> baseSupplyRateList,
	    List<String> baseErectionRateList) {

	StringBuilder tableContent = new StringBuilder();
	int index = 0;
	for (int i=0;i<inventoryList.size();i++) {

	    if(inventoryList.get(i) instanceof Inventory)
	    {
		if (erectionAmtList != null) {
		    tableContent.append(createInventoryRow((Inventory) inventoryList.get(i), supplyRateList.get(index),
			    erectionRateList.get(index), supplyAmtList.get(index), erectionAmtList.get(index),
			    baseSupplyRateList.get(index), baseErectionRateList.get(index)));
		} else {
		    tableContent.append(createInventoryRow((Inventory) inventoryList.get(i), "", "", "", "", "", ""));
		}

		index++;
	    }
	    else if(inventoryList.get(i) instanceof BOQHeader)
	    {
		tableContent.append(getBOQHeaderData((BOQHeader)inventoryList.get(i)));
	    }
	}

	return tableContent.toString();
    }

    private String createInventoryRow(Inventory inv, String supplyRate, String erectionRate, String supplyAmt,
	    String erectionAmt, String baseSupplyRate, String baseErectionRate) {
	String template = "<tr>" 
		+ "    <td style=\"padding:0rem\"><input type=\"checkbox\" name=\"checkbox\" class=\"checkbox\" /></td>"
		+ "    <td style=\"padding: 0px 0px;\">inventoryVal</td>"
		+ "    <td style=\"padding: 0px 0px;\">materialVal</td>"
		+ "    <td style=\"padding: 0px 0px;\">invenType</td>"
		+ "    <td style=\"padding: 0px 0px;\">manifMetodVal</td>"
		+ "    <td style=\"padding: 0px 0px;\">classOrGradeVal</td>"
		+ "    <td style=\"padding: 0px 0px;\">endsVal</td>"
		+ "    <td style=\"padding: 0px 0px;\">sizeVal</td>"
		+ "    <td style=\"padding: 0px 0px;\">quantityVal</td>"
		+ "    <td><input type=\"text\" name=\"baseSupplyRate\" value=\"baseSupplyRateVal\" id=\"baseSupplyRate\" /></td>"
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
		+ "    <td><input type=\"hidden\" name=\"size\" value=\"sizeVal\" /></td>"
		+ "    <td><input type=\"hidden\" name=\"quantity\" value=\"quantityVal\" /></td>" 
		+ "   </tr>";

	String rowToReturn = template;
	rowToReturn = rowToReturn.replace("inventoryVal", inv.getInventorySpec().getInventoryName());
	rowToReturn = rowToReturn.replace("materialVal", inv.getInventorySpec().getMaterial());
	rowToReturn = rowToReturn.replace("invenType", inv.getInventorySpec().getType());
	rowToReturn = rowToReturn.replace("manifMetodVal", inv.getInventorySpec().getManifMethod());
	rowToReturn = rowToReturn.replace("classOrGradeVal", inv.getInventorySpec().getGradeOrClass());
	rowToReturn = rowToReturn.replace("endsVal", inv.getInventorySpec().getEnds());
	rowToReturn = rowToReturn.replace("sizeVal", inv.getInventorySpec().getSize());

	String requiredQuantity = Integer.toString(inv.getQuantity());
	rowToReturn = rowToReturn.replace("quantityVal", requiredQuantity);

	rowToReturn = rowToReturn.replace("value=\"supplyRate", "value=\"" + supplyRate);
	rowToReturn = rowToReturn.replace("value=\"erectionRate", "value=\"" + erectionRate);
	rowToReturn = rowToReturn.replace("value=\"supplyAmount", "value=\"" + supplyAmt);
	rowToReturn = rowToReturn.replace("value=\"erectionAmount", "value=\"" + erectionAmt);

	rowToReturn = rowToReturn.replace("baseSupplyRateVal", baseSupplyRate);
	rowToReturn = rowToReturn.replace("baseErectionRateVal", baseErectionRate);
	
	
	System.out.println("rowToReturn is : "+rowToReturn);
	return rowToReturn;
    }

    private String createInvtTable(List<Inventory> inventoryList) {

	StringBuilder tableContent = new StringBuilder();

	for (Inventory inv : inventoryList) {

	    tableContent.append(createInventoryRowTable(inv));
	}

	return tableContent.toString();
    }

    private String createInventoryRowTable(Inventory inv) {
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
	rowToReturn = rowToReturn.replace("purchaseRate", Integer.toString(inventoryDao.getPurchaseRate(inv)));
	rowToReturn = rowToReturn.replace("project", inv.getAssignedProject());
	rowToReturn = rowToReturn.replace("location", inv.getLocation());
	return rowToReturn;
    }

    private String getBOQHeaderData(BOQHeader boqHeaderData)
    {
	String template = "<input type=\"hidden\" name=\"client\" value=\"client\" />" +
		"<input type=\"hidden\" name=\"site\" value=\"site\" />" +
		"<input type=\"hidden\" name=\"project\" value=\"project\" />" +
		"<input type=\"hidden\" name=\"dName\" value=\"dName\" />" +
		"<input type=\"hidden\" name=\"utility\" value=\"utility\" />" +
		"<input type=\"hidden\" name=\"pressure\" value=\"pressure\" />"+
		"<input type=\"hidden\" name=\"temp\" value=\"temp\" />"+
		"<input type=\"hidden\" name=\"dNo\" value=\"dNo\" />";
	
	String htmlToReturn = template;
	
	htmlToReturn.replace("value=\"client", "value=\""+boqHeaderData.getClient());
	htmlToReturn.replace("value=\"site", "value=\""+boqHeaderData.getSite());
	htmlToReturn.replace("value=\"project", "value=\""+boqHeaderData.getProject());
	htmlToReturn.replace("value=\"dName", "value=\""+boqHeaderData.getdName());
	htmlToReturn.replace("value=\"utility", "value=\""+boqHeaderData.getUtility());
	htmlToReturn.replace("value=\"pressure", "value=\""+boqHeaderData.getPressure());
	htmlToReturn.replace("value=\"temp", "value=\""+boqHeaderData.getTemp());
	htmlToReturn.replace("value=\"dNo", "value=\""+boqHeaderData.getdNo());
	
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
			    "", Integer.parseInt(boqDetails.getQuantity()), "", ""));
	}
	return inventoryList;
    }
}
