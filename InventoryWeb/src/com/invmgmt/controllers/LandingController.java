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

import com.invmgmt.excel.ExcelReader;

@Controller
@EnableWebMvc
public class LandingController extends AbstractController {

    final static String VIEW = "LandingPage";
    final static String PLACE_HOLDER = "welcomeMessage";
    final static String WELCOME_MESSAGE = "THIS IS LANDING PAGE FOR IPABOTS..!!";

    @Override
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
	    throws Exception {

	ModelAndView modelAndView = new ModelAndView(VIEW);
	modelAndView.addObject(PLACE_HOLDER, WELCOME_MESSAGE);
	return modelAndView;
    }

}
