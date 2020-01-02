package com.invmgmt.controllers;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@EnableWebMvc
public class TestController {
	
	public static void main(String[] args) 
	{
		rkoundOff(65);	
	}
	
	
	private static int rkoundOff(int price)
	{

		int reminder = ((price+5)/10)*10;
		
		System.out.println(reminder);
			
		return 1;
		
	}
	
}
