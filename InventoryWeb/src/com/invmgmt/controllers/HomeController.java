package com.invmgmt.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.AbstractController;

import com.invmgmt.dao.BOQDetailsDao;
import com.invmgmt.dao.ProjectDao;
import com.invmgmt.entity.Project;

@Controller
@EnableWebMvc
public class HomeController extends AbstractController {

	@Autowired
	BOQDetailsDao boqDetailsDao;
	
	@Autowired
	ProjectDao projectDao;
	
	final static String VIEW = "Home";
	final static String NEW_HOME = "newHome";

	@Override
	@RequestMapping(value = "/home", method = RequestMethod.POST)
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView modelAndView = new ModelAndView(NEW_HOME);
		String projectIdVal = "";
		
		projectIdVal = boqDetailsDao.getRecentProject();

		Project project = projectDao.getProject(Integer.parseInt(projectIdVal));

		modelAndView.addObject("projectDesc",project.getProjectDesc());
		modelAndView.addObject("projectIdVal", projectIdVal);
		modelAndView.addObject("projectNameVal", project.getProjectName());

		
		return modelAndView;
	}
}
