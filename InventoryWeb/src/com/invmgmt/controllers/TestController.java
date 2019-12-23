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
public class TestController {/*

    @RequestMapping(value = "uploadForm", method = RequestMethod.GET)
    public ModelAndView uploadForm() {
	ModelAndView mav = new ModelAndView("fileUploadForm");
	return mav;
    }

    // Handling file upload request
    @PostMapping("fileUpload")
    public ResponseEntity<Object> fileUpload(@RequestParam("file") MultipartFile file) throws IOException {

	File convFile = new File(file.getOriginalFilename());
	file.transferTo(convFile);

	System.out.println("Got a non null file here? " + convFile.toString());
	return new ResponseEntity<>("File Uploaded Successfully.", HttpStatus.OK);
    }
    
    public static void main(String[] args) 
    {   	
    	System.out.println(Float.parseFloat(""));
	}
*/
	
	public static void main(String[] args) 
	{/*

		try{
			 Open the file input stream. 
			FileInputStream fis = new FileInputStream(ResourceUtils.getFile("classpath:BOQ_Template.xls"));

			 Get workbook. 
			Workbook excelWookBook = new HSSFWorkbook(fis);

			 Get sheet by name. 
			Sheet copySheet = excelWookBook.getSheet("sheet1");
			
			int fRowNum = copySheet.getFirstRowNum();
			int lRowNum = copySheet.getLastRowNum();
			
			Sheet newSheet = excelWookBook.createSheet("ABC");
			
			  First row is excel file header, so read data from row next to it. 
			for(int i=fRowNum+1; i<lRowNum+1; i++)
			{
				 Only get desired row data. 
				if(i>=0 && i<=80)
				{
					Row row = copySheet.getRow(i);
					
					Row newRow = newSheet.createRow(i);
					
					
					int fCellNum = row.getFirstCellNum();
					int lCellNum = row.getLastCellNum();
					
					 Loop in cells, add each cell value to the list.
					List<String> rowDataList = new ArrayList<String>();
					for(int j = fCellNum; j < lCellNum; j++)
					{
						String cValue = row.getCell(j).getStringCellValue();
						
						copyCell(row.createCell(j), row.getCell(j));
						
					}
					
				}
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	*/
		
	System.out.println();
	
	}
	
	 private static void copyCell(Cell oldCell, Cell newCell) {
		    newCell.setCellStyle(oldCell.getCellStyle());

		    switch (oldCell.getCellType()) {
		    case Cell.CELL_TYPE_STRING:
		      newCell.setCellValue(oldCell.getRichStringCellValue());
		      break;
		    case Cell.CELL_TYPE_NUMERIC:
		      newCell.setCellValue(oldCell.getNumericCellValue());
		      break;
		    case Cell.CELL_TYPE_BLANK:
		      newCell.setCellType(Cell.CELL_TYPE_BLANK);
		      break;
		    case Cell.CELL_TYPE_FORMULA:
		      newCell.setCellFormula(oldCell.getCellFormula());
		      break;
		    case Cell.CELL_TYPE_BOOLEAN:
		      newCell.setCellValue(oldCell.getBooleanCellValue());
		      break;
		    case Cell.CELL_TYPE_ERROR:
		      newCell.setCellErrorValue(oldCell.getErrorCellValue());
		      break;
		    default:
		      break;
		    }
		  }

}
