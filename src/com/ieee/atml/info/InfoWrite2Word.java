package com.ieee.atml.info;

import java.util.ArrayList;
import java.util.HashMap;

public interface InfoWrite2Word {
	
	//void initErrorMap();
	//void addErrorItemWithoutEnter(String testItem, String error);
	//void addInfoItem(String testItem, String error);
	void infoWriteWord(String fileType);
	void errorClassification(String testItem, String testContent, String testResults, String note) ;
}
