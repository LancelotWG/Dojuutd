package com.ieee.atml.util;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.xpath.XPathConstants;

import org.dom4j.DocumentException;
import org.dom4j.XPathException;
import org.dom4j.io.SAXReader;

public interface XPathStandard {
	public static final String regEX = "(?:\\/(?:[a-zA-Z]{0,}:){0,1}[a-zA-Z]{1,}(?:(?:\\/){0,1}\\[@[a-zA-Z]{0,}=(['\"])[\\u4e00-\\u9fa5\\w\\+\\#-]{0,}\\1\\]){0,}){1,}";

	default boolean matchXPath(String XPath) {
		// String regEX =
		// "(?:\\/(?:[a-zA-Z]{0,}:){0,1}[a-zA-Z]{0,}){1,}(?:(?:\\/){0,1}\\[@[a-zA-Z]{0,}=(['\"])[a-zA-Z_0-9\\+-]{0,}\\1\\]){0,}";
		// String regEX2 = "(\\/[a-zA-Z]{0,}:[a-zA-Z]{0,}){1,}";
		// String regEX1 = "(\\/\\[@name='[a-zA-Z_0-9]{0,}'\\]){0,}";

		//Pattern pattern = Pattern.compile(regEX);
		//Matcher matcher = pattern.matcher(XPath);
		SAXReader saxReader = new SAXReader();
		org.dom4j.Document document = null;
		try {
			document = saxReader.read(R.class.getResource("/template.xml"));
		} catch (DocumentException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		try {
			document.selectNodes(XPath);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			return false;
		}
		return true;
		//return matcher.matches();
	}

	default String getXPathType(String XPath) {
		if(!XPath.contains("/")){
			return "";
		}
		String[] dirs = XPath.split("/");
		String[] dir = dirs[1].split(":");
		if ((dir.length == 1)) {
			return dir[0];
		} else if (dir.length >= 2) {
			return dir[1];
		}
		return "";
	}
}
