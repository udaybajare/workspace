package com.invmgmt.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.invmgmt.dao.BOQDetailsDao;
import com.invmgmt.dao.ProjectDao;
import com.invmgmt.dao.ProjectDetailsDao;
import com.invmgmt.entity.BOQDetails;
import com.invmgmt.entity.Project;
import com.invmgmt.entity.ProjectDetails;
import com.invmgmt.excel.ExcelReader;

@Controller
@EnableWebMvc
public class ProjectController {

	@Autowired
	private ProjectDao projectDao;

	@Autowired
	private ProjectDetailsDao projectDetailsDao;
	
	@Autowired
	private BOQDetailsDao boqDao;

	@Autowired
	ExcelReader reader;

	private static final String updateProjectviewName = "updatedDetails";
	private static final String createProjectviewName = "projectDetails";
	private static final String searchProjectviewName = "searchProjectResult";

	
	@RequestMapping(value = "/createProject", method = RequestMethod.POST)
	protected ModelAndView saveProject(Project project) throws Exception {
		// TODO Auto-generated method stub

		int projId = projectDao.addProject(project);

		System.out.println("New project created with ID : " + projId);

		ModelAndView mav = new ModelAndView(createProjectviewName);

		mav.addObject("projectName", project.getProjectName());
		mav.addObject("projectDesc", project.getProjectDesc());
		mav.addObject("projectId", project.getProjectId());
		return mav;
	}

	@RequestMapping(value = "/updateProject", method = RequestMethod.POST)
	protected ModelAndView updateProject(ProjectDetails projectDetails) throws Exception {
		projectDetailsDao.updateProjet(projectDetails);

		Project project = projectDao.getProject(projectDetails.getProjectId());
		
		ArrayList<String> boqNames = boqDao.getAssociatedBOQNames(String.valueOf(project.getProjectId()));
		
		String tableContent = "";

		ModelAndView mav = new ModelAndView(updateProjectviewName);

		mav.addObject("boqNameList", String.join(",", boqNames));
		mav.addObject("projectId", project.getProjectId());
		mav.addObject("projectName", project.getProjectName());
		mav.addObject("projectDesc", project.getProjectDesc());
		mav.addObject("address", projectDetails.getAddress());
		mav.addObject("contactEmail", projectDetails.getContactEmail());
		mav.addObject("contactName", projectDetails.getContactName());
		mav.addObject("contactPhone", projectDetails.getContactPhone());
		mav.addObject("gstNumber", projectDetails.getGstNumber());
		mav.addObject("poDate", projectDetails.getPoDate());
		mav.addObject("poNumber", projectDetails.getPoNumber());

		return mav;
	}

	@RequestMapping(value = "/searchProject", method = RequestMethod.POST)
	protected ModelAndView searchProject(String projectName) throws Exception {

		List<Project> projectList = null;
		StringBuilder projectRows = new StringBuilder();
		projectList = projectDao.getProject(projectName);

		ModelAndView mav = new ModelAndView(searchProjectviewName);

		for (int i = 0; i < projectList.size(); i++) {
			String projectRowSingle = projectRow;

			projectRowSingle = projectRowSingle.replace("projectNameVal", projectList.get(i).getProjectName());
			projectRowSingle = projectRowSingle.replace("projectDescVal", projectList.get(i).getProjectDesc());
			projectRowSingle = projectRowSingle.replace("projectIdVal", String.valueOf(projectList.get(i).getProjectId()));
			
			projectRows.append(projectRowSingle);

		}
		mav.addObject("projects", projectRows);

		return mav;
	}

	@RequestMapping(value = "/projectDetails", method = {RequestMethod.POST,RequestMethod.GET})
	protected ModelAndView projectDetails(Project project) throws Exception {

		ProjectDetails projectDetails = projectDetailsDao.getProjectDetails(project.getProjectId());
		
		ArrayList<String> boqNames = boqDao.getAssociatedBOQNames(String.valueOf(project.getProjectId()));
				
		String tableContent = "";

		ModelAndView mav = new ModelAndView(updateProjectviewName);

		mav.addObject("boqNameList", String.join(",", boqNames));
		mav.addObject("projectId", project.getProjectId());
		mav.addObject("projectName", project.getProjectName());
		mav.addObject("projectDesc", project.getProjectDesc());
		mav.addObject("address", projectDetails.getAddress());
		mav.addObject("contactEmail", projectDetails.getContactEmail());
		mav.addObject("contactName", projectDetails.getContactName());
		mav.addObject("contactPhone", projectDetails.getContactPhone());
		mav.addObject("gstNumber", projectDetails.getGstNumber());
		mav.addObject("poDate", projectDetails.getPoDate());
		mav.addObject("poNumber", projectDetails.getPoNumber());

		return mav;
	}

	private static final String projectRow = "<form action=\"projectDetails\" onClick=\"this.submit();\" method=\"POST\"> <div class=\"row\">" + " <div class=\"col-md-12 \">"
			+ "   <div class=\"pv-30 ph-20 feature-box bordered shadow text-center object-non-visible\" data-animation-effect=\"fadeInDownSmall\" data-effect-delay=\"100\">"
			+ "   <h3 name=\"projectName\">projectNameVal</h3>" + "   <div class=\"separator clearfix\"></div>" + " <p name=\"projectDesc\">projectDescVal</p>"
			+ " </div>" + "</div>   " + "</div>"
			+ "<input type=\"hidden\" name=\"projectId\" value=\"projectIdVal\"/>"
			+ "<input type=\"hidden\" name=\"projectName\" value=\"projectNameVal\"/>"
			+ "<input type=\"hidden\" name=\"projectDesc\" value=\"projectDescVal\"/> </form>" ;
}
