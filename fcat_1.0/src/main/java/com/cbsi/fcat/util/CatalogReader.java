package com.cbsi.fcat.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cbsi.fcat.pageobject.catatlogpage.MappingPage;
import com.cbsi.tests.FCatMongoObject.Product;

public class CatalogReader {

	public StringBuilder readFrom(String filename) throws IOException{
		 StringBuilder sb = null;
		  BufferedReader br = new BufferedReader(new FileReader(filename)); 
		    try{ 
		    	sb = new StringBuilder();
		    
		        String line = br.readLine();

		        while (line != null) {
		            sb.append(line);
		           // sb.append(System.lineSeparator());
		            line = br.readLine();
		        }
		        String everything = sb.toString();
		    }
		  finally{
			  br.close();
		  }
		    return sb;
	}
	
	public static void main(String args[]) throws Exception{
		StringBuilder sb = new CatalogReader().readFrom("src/test/resources/Catalogs/London.csv");
		new CatalogReader().convertRawDataToProducts(sb, true);
	
	}

	public List<Product> convertRawDataToProducts(StringBuilder sb, boolean hasHeader) throws Exception{
		String[] lines = sb.toString().split("\\n");
		//System.out.println("how many products: " + lines.length);
		
		//System.out.println(products[0]);
		List<String> headerList = new ArrayList<String>();
		if(hasHeader){
			//System.out.println(lines[0]);
				String[] ByHeader = lines[0].toLowerCase().split("\t");
				for(int i=0; i<ByHeader.length; i++){
					String header = MappingPage.getMatchingCNETFields(ByHeader[i], false, false);
					if(header.isEmpty()){
						headerList.add("###");
					}
					else{
						headerList.add(header);
					}
				}
				
				headerList.toArray();
				
		
		}
		
		List<Product> productList = new ArrayList<Product>();
		for(int i=1; i< lines.length; i++){
			Product product = new Product();
			String[] values = lines[i].split("\t");
			//System.out.println("line: " + lines[i]);

			for(int j=0; j<values.length; j++){
				//System.out.println("j:  " + j);
				String headerFilter= headerList.get(j);
				if(headerFilter.equals("product id")){
					product.setId(values[j]);
				}
				else if(headerFilter.equals("manufacturer name")){
					product.setMf(values[j]);
				}
				else if(headerFilter.equals("manufacturer part number")){
					product.setMfpn(values[j]);
				}
				else if(headerFilter.equals("cnet sku id")){
					product.setCentSkuId(values[j]);
				}
				else if(headerFilter.equals("upc/ean")){
					product.setUpcEan(values[j]);
				}
				else if(headerFilter.equals("msrp")){
					product.setMsrp(values[j]);
				}
				else if(headerFilter.equals("price")){
					product.setPrice(values[j]);
				}
				else if(headerFilter.equals("inventory")){
					product.setInventory(values[j]);
				}
				else if(headerFilter.equals("product url")){
					product.setProductUrl(values[j]);
				}
			
			}
			productList.add(product);
		}	         
			System.out.println("product size: "  + productList.size());
			
		for(Product p: productList){
		//	System.out.println(p.toString());		
		}
		

	
		return productList;
	}
	
}
