package com.cbsi.fcat.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Read V3 data exported to xlsx sheet.
 * @author alpark
 *
 */
public class FileHelper {

	public static List<HashMap<String, String>> readExcelFile(String path, String[] columns) throws IOException{
		List<HashMap<String, String>> columnMaps = new ArrayList<HashMap<String, String>>();
		
		FileInputStream file = new FileInputStream(new File(path));
		
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		
		XSSFSheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.iterator();
		
		while(rowIterator.hasNext()){
			HashMap<String, String> columnMap = new HashMap<String, String>();
			
			int columnCount = 1;
			Row currentRow =  rowIterator.next();
			
			if(currentRow.getRowNum() ==0) 
				continue;
			
			Iterator<Cell> cellIterator = currentRow.cellIterator();
			
			String mfId = "";
			String mfPn = "";
			
			while(cellIterator.hasNext()){
				
				Cell currentCell = cellIterator.next();
				if(columnCount == 19 ){
					mfId = new Double(currentCell.getNumericCellValue()).intValue() + "";
				}
				else if(columnCount ==20){
					mfPn = currentCell.getStringCellValue();
				}
				columnCount++;
				
			}
//			System.out.println("mfId: " + mfId + " | " + "mfPn: " + mfPn);

			if(mfId != null){
				columnMap.put("mfId", mfId);
				columnMap.put("mfPn", mfPn);
				
				columnMaps.add(columnMap);
			}
		}
		
		workbook.close();
		file.close();
		
		return columnMaps;
	}
	
	public static void main(String[] args){
		String excelPath = "/Users/alpark/Documents/110-28.xlsx";
		String[] columns = new String[] {"blah"};
		
		List<HashMap<String, String>> columnMaps = null;
		try {
			columnMaps = FileHelper.readExcelFile(excelPath, columns);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(columnMaps.size());
	}
}
