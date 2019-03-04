package com.invmgmt.controllers;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@EnableWebMvc
public class TestController {

    @RequestMapping(value = "uploadForm", method = RequestMethod.GET)
    public ModelAndView uploadForm() {
	ModelAndView mav = new ModelAndView("fileUploadForm");
	return mav;
    }

    @RequestMapping(value = "uploadFile", method = RequestMethod.POST)
    public ModelAndView submit(@RequestParam("file") MultipartFile file) throws IOException {

	System.out.println(file.getName());
	System.out.println(file.getInputStream());
	ModelAndView model = new ModelAndView("dummy");
	return model;
    }
    
    public static void main(String[] args)
    {
	//Format formatter = new SimpleDateFormat("dd/MM/yyyy");
	DateTimeFormatter.ofPattern("dd/MM/yyyy");
	//String today = formatter.format();
	
	System.out.println(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }
}
