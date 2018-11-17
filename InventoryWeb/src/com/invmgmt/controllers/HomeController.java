package com.invmgmt.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.AbstractController;

import com.invmgmt.dao.InventoryDefinitionDao;
import com.invmgmt.excel.ExcelReader;

@Controller
@EnableWebMvc
public class HomeController extends AbstractController {

	@Autowired
	private ExcelReader reader;
	
	@Autowired
	private InventoryDefinitionDao inventoryDefinitionDao;
	
	final static String VIEW = "Home";

	@Override
	@RequestMapping(value = "/home", method = RequestMethod.POST)
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView modelAndView = new ModelAndView(VIEW);
		return modelAndView;
	}
}
