package com.ieee.atml.info;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class InfoWrite implements InfoWrite2HTML, InfoWrite2Txt,  InfoWrite2Word {
	protected enum FileType {
		adapter, UUT, station, configuration, testDescription, testSignal, instrument
	}
	
	protected List<String> errorPin = new ArrayList<String>();
	protected List<String> errorPin1 = new ArrayList<String>();
	protected HashMap<String, ArrayList<String>> errorMap = new HashMap<String, ArrayList<String>>();
	protected String fileName = "";
	protected String infoHTML = "";
	protected String infoTxt = "";
	protected  ArrayList<String> testItem = new ArrayList<String>();

	protected void WriteTestItem1(String info) {
		// TODO 自动生成的方法存根
		info = ("**********" + info + "**********");
		//WriteTxtAndHTMLOnly(info);
		infoHTML += WriteBoldHTML(info);
		infoTxt += WriteNormalTxt(info);
	}
	
	protected void WriteTestItem2(String testItem, String info) {
		// TODO 自动生成的方法存根
		info = ("------------" + info + "------------");
		WriteTxtAndHTMLOnly(info);
		addInfoItem(testItem, info);
	}
	
	protected void WriteTxtAndHTMLOnly(String info) {
		// TODO 自动生成的方法存根
		infoHTML += WriteNormalHTML(info);
		infoTxt += WriteNormalTxt(info);
	}
	
	protected void WriteErrorInfo(String testItem, String info) {
		// TODO 自动生成的方法存根
		infoHTML += WriteErrorHTML(info);
		infoTxt += WriteNormalTxt(info);
		addInfoItem(testItem, info);
	}

	protected void WriteBoldInfo(String testItem, String info) {
		// TODO 自动生成的方法存根
		infoHTML += WriteBoldHTML(info);
		infoTxt += WriteNormalTxt(info);
		addInfoItem(testItem, info);
	}

	protected void WriteNormalInfo(String testItem, String info) {
		// TODO 自动生成的方法存根
		infoHTML += WriteNormalHTML(info);
		infoTxt += WriteNormalTxt(info);
		addInfoItem(testItem, info);
	}

	protected void WriteNormalInfoWithoutEnter(String testItem, String info) {
		infoHTML += WriteNormalHTMLWithoutEnter(info);
		infoTxt += WriteNormalTxtWithoutEnter(info);
		addInfoItemWithoutEnter(testItem,info);
	}
	
	protected void WriteNormalHTMLInfo(String testItem, String info) {
		infoHTML += WriteNormalHTMLWithoutEnter(info);
		//infoTxt += WriteNormalTxtWithoutEnter(info);
		//addInfoItemWithoutEnter(testItem,info);
	}
	
	protected void WriteNormalTxtInfo(String testItem, String info) {
		//infoHTML += WriteNormalHTMLWithoutEnter(info);
		infoTxt += WriteNormalTxtWithoutEnter(info);
		//addInfoItemWithoutEnter(testItem,info);
	}
	
	protected void WriteNormalWordInfo(String testItem, String info) {
		//infoHTML += WriteNormalHTMLWithoutEnter(info);
		//infoTxt += WriteNormalTxtWithoutEnter(info);
		addInfoItemWithoutEnter(testItem,info);
	}

	protected void WriteBoldInfo(String testItem, int info) {
		// TODO 自动生成的方法存根
		infoHTML += WriteBoldHTML(info);
		infoTxt += WriteNormalTxt(info);
		addInfoItem(testItem, info + "");
	}
	
	private void addInfoItemWithoutEnter(String testItem, String info) {
		// TODO 自动生成的方法存根
		String oldError = errorMap.get(testItem).get(1);
		errorMap.get(testItem).set(1, oldError + info);
	}

	@Override
	public void infoWriteWord(String fileTye) {
		// TODO 自动生成的方法存根
		WordWriter wordWriter = new WordWriter();
		wordWriter.createTestReport(fileTye);
		for (int i = 0; i < testItem.size(); i++) {
			String string = testItem.get(i);
			ArrayList<String> item = errorMap.get(string);
			if (item.get(1).equals("")) {
				item.set(1, "测试通过!");
			}
			wordWriter.addTestItem(string, item.get(0), item.get(1), item.get(2));
		}
		wordWriter.writeComplete();
	}

	public void initInfoMap() {
		// TODO 自动生成的方法存根
		for (int i = 0; i < testItem.size(); i++) {
			errorClassification(testItem.get(i), " ", "", " ");
		}
	}

	private void addInfoItem(String testItem, String info) {
		// TODO 自动生成的方法存根
		String oldError = errorMap.get(testItem).get(1);
		errorMap.get(testItem).set(1, oldError + info + "\n");
	}
	
	public void errorClassification(String testItem, String testContent, String testResults, String note) {
		ArrayList<String> errorItem = new ArrayList<String>();
		errorItem.add(testContent);
		errorItem.add(testResults);
		errorItem.add(note);
		errorMap.put(testItem, errorItem);
	}
}
