
package com.boyaa.mf.util;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class TableUtil {
	public static String getSourceTableName(String name) {
		if(StringUtils.isEmpty(name))return null;
		int index = name.lastIndexOf("_");
		if(index == -1)
			return name;
		String[] names = name.split("_");
		String sub = names[names.length - 1];
		Pattern p = Pattern.compile("\\d+");
		boolean flag = p.matcher(sub).matches();
		if(flag) {
			return name.substring(0, index);
		} else {
			return name;
		}
	}
}
