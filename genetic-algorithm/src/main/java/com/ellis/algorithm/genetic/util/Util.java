package com.ellis.algorithm.genetic.util;

import java.util.ArrayList;
import java.util.List;

public class Util {
	
	
	public static List<String> slit(String str) {
		List<String> retList = new ArrayList<String>();
		if(str == null){
			return retList;
		}
		int size = str.length();
		int index = 0;
		while(index < size) {
		
			retList.add(str.substring(index, index + 2));
			index +=2;
		}
		return retList;
	}
	
	
	
}
