package com.cbsi.col.test.util;

import java.util.LinkedHashMap;
import java.util.List;

public class TableUtil {
	public static boolean tableMapHasWordStartsWith(List<LinkedHashMap<String, String>> maps, String keyword){
		return tableMapHasWord(maps, keyword, true);
	}
	
	public static boolean tableMapHasWordContains(List<LinkedHashMap<String, String>> maps, String keyword){
		return tableMapHasWord(maps, keyword, false);
	}
	
	public static boolean tableMapHasWord(List<LinkedHashMap<String, String>> maps, String keyword, boolean startsWith){
		return tableMapHasWord(maps, null, keyword, startsWith);
	}
	
	public static boolean tableMapHasWord(List<LinkedHashMap<String, String>> maps, String keyName, String keyword, boolean startsWith){
		if(maps.size() ==0 || maps == null){
			return false;
		}
		
		keyword = StringUtil.cleanRegexChars(keyword);
		
		if(keyName == null){
			for(LinkedHashMap<String, String> map: maps){
				boolean wordFound = false;
				for(String key: map.keySet()){
					
					if(!startsWith){
//						System.out.println("Compairson: " + map.get(key) + " | " + keyword);
						if(map.get(key).toLowerCase().contains(keyword.toLowerCase())){
							wordFound=true;
							continue;
						}
					}else{
						if(map.get(key).toLowerCase().startsWith(keyword.toLowerCase())){
							wordFound=true;
							continue;
						}
					}
				}
				if(!wordFound){
					System.err.println("\"" + keyword + "\" was not found");
					return false;
				}
			}
		}
		else{
			for(LinkedHashMap<String, String> map: maps){
				boolean wordFound = false;
//				System.out.println(map.get(keyName) + " | " + keyword);

				if(!startsWith){
//					System.out.println(map.get(keyName) + " | " + keyword);
					if(map.get(keyName).toLowerCase().contains(keyword.toLowerCase())){
						wordFound=true;
					}
				}else{
					if(map.get(keyName).toLowerCase().startsWith(keyword.toLowerCase())){
						wordFound=true;
					}
				}
				
				if(!wordFound){
					System.err.println("\"" + keyword + "\" was not found");
					return false;
				}
			}
		}
		
		return true;
	}

}
