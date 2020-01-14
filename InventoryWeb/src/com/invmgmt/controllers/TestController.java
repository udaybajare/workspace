package com.invmgmt.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@EnableWebMvc
public class TestController {
	
	public static void main(String[] args) 
	{
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}
	
	
	private static int rkoundOff(int price)
	{

		int remender = 10 - price%10;
		
		
		
		int value = ((price+remender)/10)*10;
		
		System.out.println(value);
			
		return 1;
		
	}
	
}
