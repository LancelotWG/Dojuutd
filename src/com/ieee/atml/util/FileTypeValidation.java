package com.ieee.atml.util;

import java.io.File;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public interface FileTypeValidation {
	default boolean fileTypeValidation(String fileDir, String fileType) {
		File file = new File(fileDir);
		// System.out.println(file.getName());
		org.dom4j.Document document = null;
		if (file.exists()) {
			SAXReader saxAdpaterReader = new SAXReader();
			document = null;
			try {
				document = saxAdpaterReader.read(file);
			} catch (DocumentException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			Element root = document.getRootElement();
			String type = root.getName();
			if (fileType.equals(StringUtil.configuration)) {
				if (type.equals(fileType)) {
					return true;
				}
			} else if (fileType.equals(StringUtil.adapter)) {
				if (type.equals(fileType)) {
					return true;
				}
			} else if (fileType.equals(StringUtil.station)) {
				if (type.equals(fileType)) {
					return true;
				}
			} else if (fileType.equals(StringUtil.testDescription)) {
				if (type.equals(fileType)) {
					return true;
				}
			} else if (fileType.equals(StringUtil.UUT)) {
				if (type.equals(fileType)) {
					return true;
				}
			} else if (fileType.equals(StringUtil.testSignal)) {
				if (type.equals(fileType)) {
					return true;
				}
			} else if (fileType.equals(StringUtil.instrument)) {
				if (type.equals(fileType)) {
					return true;
				}
			} else if (fileType.equals(StringUtil.result)) {
				if (type.equals(fileType)) {
					return true;
				}
			} else if (fileType.equals(StringUtil.testSignal)) {
				if (type.equals(fileType)) {
					return true;
				}
			}
		} else {
			System.out.println("File " + fileType + " is not exist!");
			// error += ("File " + configuration + " is not exist!\n");
			// writeError("File " + fileType + " is not exist!");
		}
		return false;
	}
}
