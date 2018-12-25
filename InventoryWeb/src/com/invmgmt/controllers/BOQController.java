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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.invmgmt.dao.BOQDetailsDao;
import com.invmgmt.dao.BOQLineDataDao;
import com.invmgmt.dao.InventoryDao;
import com.invmgmt.dao.InventoryDefinitionDao;
import com.invmgmt.dao.MappingsDao;
import com.invmgmt.dao.ProjectDao;
import com.invmgmt.entity.BOQDetails;
import com.invmgmt.entity.BOQLineData;
import com.invmgmt.entity.Inventory;
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
	public @ResponseBody String getBOQLine(@RequestParam(value = "location", required = true) String location) {

		List inventoryList = null;
		try {
			inventoryList = reader.readFile(location);
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String tableContent = createBOQContent(inventoryList);
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
	
	@RequestMapping(value = "/downloadBoq", method = RequestMethod.GET)
	protected @ResponseBody void downloadBOQ(String boqName)
	{
		ArrayList<BOQDetails> boqDetails = boqDao.getBOQFromName(boqName);

		try 
		{
			writer.writeExcel(boqDetails);
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/generate", method = RequestMethod.POST)
	protected String generateBOQ(String projectId, String boqName, String[] standardType, String[] grade, String[] schedule, String[] materialSpec,
			String[] ends, String[] size, String[] quantity, String[] supplyRate, String[] erectionRate,
			String[] supplyAmount, String[] erectionAmount, RedirectAttributes redirectAttributes) 
	{
		ArrayList<String> boqRevisions = boqDao.getMatchingBOQNames(boqName+"_R");

		String boqNameRevisionStr = "";
		
		for(int i=1;i<20;i++)
		{
			boqNameRevisionStr = boqName+"_R"+String.valueOf(i);
			if(boqRevisions.contains(boqNameRevisionStr))
				continue;
			else
				break;
		}
		
		System.out.println("Saving BOQ with name : "+boqNameRevisionStr);
		
		ArrayList<BOQDetails> boqInventoryDetails = getBOQDetailsList(projectId,
				boqNameRevisionStr,
				standardType,
				grade,
				schedule,
				materialSpec,
				ends,
				size,
				quantity,
				supplyRate,
				erectionRate,
				supplyAmount,
				erectionAmount);
		
		try {
			writer.writeExcel(boqInventoryDetails);
			
			for(BOQDetails boqDetails : boqInventoryDetails)
			{				
				boqDao.saveBOQ(boqDetails);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Project project = projectDao.getProject(Integer.valueOf(projectId));
		
		redirectAttributes.addAttribute("projectId",projectId);
		redirectAttributes.addAttribute("projectName",project.getProjectName());
		redirectAttributes.addAttribute("projectDesc",project.getProjectDesc());
		
		return "redirect:/projectDetails";
	}

	@RequestMapping(value = "/generateNew", method = RequestMethod.POST)
	protected String generateBOQ(String projectId, String boqName, String[] inventoryName, String[] material, String[] type, String[] manifacturingMethod,
			String[] classOrGrade, String[] ends, String[] size, String[] quantity, String[] supplyRate, String[] erectionRate,
			String[] supplyAmount, String[] erectionAmount, RedirectAttributes redirectAttributes) 
	{
		ArrayList<String> boqRevisions = boqDao.getMatchingBOQNames(boqName+"_R");

		String boqNameRevisionStr = "";
		projectId = "73";
		for(int i=1;i<20;i++)
		{
			boqNameRevisionStr = boqName+"_R"+String.valueOf(i);
			if(boqRevisions.contains(boqNameRevisionStr))
				continue;
			else
				break;
		}
		
		System.out.println("Saving BOQ with name : "+boqNameRevisionStr);
		
		ArrayList<BOQLineData> boqInventoryDetails = getBOQLineDataList(material,type,ends,classOrGrade);
		
		try {
			writer.writeExcel(boqInventoryDetails, size, quantity, supplyRate, erectionRate);
			
			for(BOQLineData boqDetails : boqInventoryDetails)
			{				
				//boqDao.saveBOQ(boqDetails);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Project project = projectDao.getProject(Integer.valueOf(projectId));
		
		redirectAttributes.addAttribute("projectId",projectId);
		redirectAttributes.addAttribute("projectName",project.getProjectName());
		redirectAttributes.addAttribute("projectDesc",project.getProjectDesc());
		
		return "redirect:/projectDetails";
	}
	
	private ArrayList<BOQLineData> getBOQLineDataList(String[] material, String[] type,String[] ends, String[] classOrGrade)
	{
		ArrayList<BOQLineData> boqInventoryDetails = new ArrayList<>();
		int noOfEntries = material.length;
		
		for (int i = 0; i < noOfEntries; i++) 
		{
			boqInventoryDetails.add(getBOQLineData(material[i], type[i], classOrGrade[i], ends[i]));
		}
		
		return boqInventoryDetails;
	}
	
	
	private BOQLineData getBOQLineData(String material, String type, String classOrGrade, String ends)
	{
		switch(classOrGrade)
		{
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
	private ArrayList<BOQDetails> getBOQDetailsList(String projectId, String boqName, String[] standardType, String[] grade, String[] schedule,
			String[] materialSpec, String[] ends, String[] size, String[] quantity, String[] supplyRate,
			String[] erectionRate, String[] supplyAmount, String[] erectionAmount) 
	{
		int noOfEntries = standardType.length;
		ArrayList<BOQDetails> boqInventoryDetails = new ArrayList<>();
		
		for (int i = 0; i < noOfEntries; i++) 
		{
			boqInventoryDetails.add(new BOQDetails(projectId,
					boqName,
					standardType[i],
					grade[i],
					schedule[i],
					materialSpec[i],
					ends[i],
					size[i],
					quantity[i],
					supplyRate[i],
					erectionRate[i],
					supplyAmount[i],
					erectionAmount[i]));
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

			ArrayList<String> results = (ArrayList<String>) mappingsDao.getAssociatedOptions(currentTag,
					value, nextTagName);

			for (int i = 0; i < results.size(); i++) {
				String option =  String.valueOf(results.get(i));
				
				dropdownContent.append("<option value=\"" + option + "\">" + option + "</option>" + "\n");

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dropdownContent.toString();
	}

	private String createBOQContent(List<Inventory> inventoryList) {

		StringBuilder tableContent = new StringBuilder();

		for (Inventory inv : inventoryList) {

			tableContent.append(createInventoryRow(inv));
		}

		return tableContent.toString();
	}

	private String createInventoryRow(Inventory inv) {
		String template = "<tr>" 
				+ "    <td><input type=\"text\" name=\"standardType\" value=\"standardType\" /></td>"
				+ "    <td><input type=\"text\" name=\"grade\" value=\"grade\" /></td>"
				+ "    <td><input type=\"text\" name=\"schedule\" value=\"schedule\" /></td>"
				+ "    <td><input type=\"text\" name=\"materialSpec\" value=\"materialSpec\" /></td>"
				+ "    <td><input type=\"text\" name=\"ends\" value=\"ends\" /></td>"
				+ "    <td><input type=\"text\" name=\"size\" value=\"size\" /></td>"
				+ "    <td><input type=\"text\" name=\"quantity\" value=\"quantity\" /></td>"
				+ "    <td><input type=\"text\" name=\"supplyRate\" value=\"\" /></td>"
				+ "    <td><input type=\"text\" name=\"erectionRate\" value=\"\" /></td>"
				+ "    <td><input type=\"text\" name=\"supplyAmount\" value=\"\" /></td>"
				+ "    <td><input type=\"text\" name=\"erectionAmount\" value=\"\" /></td>" + "</tr>";

		String rowToReturn = template;
		rowToReturn = rowToReturn.replace("value=\"standardType", "value=\""+inv.getInventorySpec().getStandardType());
		rowToReturn = rowToReturn.replace("value=\"grade", "value=\""+inv.getInventorySpec().getGrade());
		rowToReturn = rowToReturn.replace("value=\"schedule", "value=\""+inv.getInventorySpec().getSchedule());
		rowToReturn = rowToReturn.replace("value=\"materialSpec", "value=\""+inv.getInventorySpec().getMaterialSpec());
		rowToReturn = rowToReturn.replace("value=\"ends", "value=\""+inv.getInventorySpec().getEnds());
		rowToReturn = rowToReturn.replace("value=\"size", "value=\""+Integer.toString(inv.getInventorySpec().getSize()));
		
		String requiredQuantity = Integer.toString(inv.getQuantity());
		rowToReturn = rowToReturn.replace("value=\"quantity", "value=\""+requiredQuantity);
			
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
		String template = "<tr>" 
				+ "    <td>standardType</td>"
				+ "    <td>grade</td>"
				+ "    <td>schedule</td>"
				+ "    <td>materialSpec</td>"
				+ "    <td>ends</td>"
				+ "    <td>size</td>"
				+ "    <td>availableQuantity</td>"
				+ "    <td>purchaseRate</td>";

		String rowToReturn = template;
		rowToReturn = rowToReturn.replace("standardType", inv.getInventorySpec().getStandardType());
		rowToReturn = rowToReturn.replace("grade", inv.getInventorySpec().getGrade());
		rowToReturn = rowToReturn.replace("schedule", inv.getInventorySpec().getSchedule());
		rowToReturn = rowToReturn.replace("materialSpec", inv.getInventorySpec().getMaterialSpec());
		rowToReturn = rowToReturn.replace("ends", inv.getInventorySpec().getEnds());
		rowToReturn = rowToReturn.replace("size", Integer.toString(inv.getInventorySpec().getSize()));
		
		//check available quantity		
		int availableQuantity = inventoryDao.getAvailableQuantity(inv);
		
		rowToReturn = rowToReturn.replace("availableQuantity", Integer.toString(availableQuantity));
		
		//Purchse Rate
		rowToReturn = rowToReturn.replace("purchaseRate", Integer.toString(inventoryDao.getPurchaseRate(inv)));
		return rowToReturn;
	}
}
