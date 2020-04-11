package com.invmgmt.controllers;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.invmgmt.entity.InventorySpec;

@Controller
@EnableWebMvc
public class TestController {
	
	public static void main(String[] args) 
	{
		
		InventorySpec spec = new InventorySpec();
		spec.setInventoryName("abc");
		
		InventorySpec spec0 = spec;

		System.out.println("spec before method is : "+spec);
		System.out.println("spec0 before method is : "+spec0);
		
		spec = changeThis(spec);
		
		System.out.println("spec after method is : "+spec);
		System.out.println("spec0 after method is : "+spec0);
		
	}
	
	public static InventorySpec changeThis(InventorySpec spec2)
	{
		spec2.setInventoryName("xyz");
		
		return spec2;
		
	}
	
	
	public static int findIndex(String[] a, String target)
	{
		for (int i = 0; i < a.length; i++)
		{
			if (a[i].equals(target))
				return i;
		}			

		return -1;
	}
	
	public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm) {
		// Create a list from elements of HashMap
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(hm.entrySet());

		// Sort the list
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		// put data from sorted list to hashmap
		HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> aa : list) {
			temp.put(aa.getKey(), aa.getValue());
		}
		return temp;
	}
	
	
	private static int rkoundOff(int price)
	{

		int remender = 10 - price%10;
		
		
		
		int value = ((price+remender)/10)*10;
		
		System.out.println(value);
			
		return 1;
		
	}
	
}
