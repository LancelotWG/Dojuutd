package com.ieee.atml.info;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ieee.atml.util.ErrorInformationCollection;

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
	
	protected HashMap<String,ArrayList<ErrorInformationCollection>> errorInformations = new HashMap<String,ArrayList<ErrorInformationCollection>>();

	private String head = "检测完成，检测信息如下";
	
	private String informationType1 = "检测结论";
	private String informationType2 = "检测结果";
	
	protected void addTestResult(){
		String html = WriteBoldHTML(head);
		String txt = WriteNormalTxt(head);
		html += WriteBold0HTML("-┕" + informationType1);
		txt += WriteNormalTxt("-┕" + informationType1);
		if(errorInformations.size() == 0){
			html += WriteBold1HTML("-----┕检测通过");
			txt += WriteNormalTxt("-----┕检测通过");
		}else{
			html += WriteBold1HTML("-----┕检测不通过，原因如下");
			txt += WriteNormalTxt("-----┕检测不通过，原因如下");
			Iterator<Map.Entry<String, ArrayList<ErrorInformationCollection>>> entries = errorInformations.entrySet().iterator();  
			while (entries.hasNext()) {
			    Map.Entry<String, ArrayList<ErrorInformationCollection>> entry = entries.next();
			    ArrayList<ErrorInformationCollection> errorInformationCollections = entry.getValue();
			    html += WriteBold2HTML("---------┕" + entry.getKey());
				txt += WriteNormalTxt("---------┕" + entry.getKey());
				for (ErrorInformationCollection errorInformationCollection : errorInformationCollections) {
					if(errorInformationCollection.getErrorType() == ErrorInformationCollection.ErrorType.LogicalError){
						html += WriteError2HTML("-------------┕" + errorInformationCollection.getErrorInformation());
						txt += WriteNormalTxt("-------------┕" + errorInformationCollection.getErrorInformation());
					}else{
						html += WriteErrorHTML("-------------┕" + errorInformationCollection.getErrorInformation());
						txt += WriteNormalTxt("-------------┕" + errorInformationCollection.getErrorInformation());
					}
				}
			} 
		} 
		StringBuffer H = new StringBuffer(infoHTML);  
		H.insert(0,html); 
		StringBuffer T = new StringBuffer(infoTxt);  
		T.insert(0,txt);
		infoHTML = new String(H);
		infoTxt = new String(T);
	}
	
	protected void WriteTestItem0(String info) {
		// TODO 自动生成的方法存根
		info = ("-┕" + info);
		//WriteTxtAndHTMLOnly(info); 
		infoHTML += WriteBold0HTML(info);
		infoTxt += WriteNormalTxt(info);
	}
	
	protected void WriteTestItem1(String info) {
		// TODO 自动生成的方法存根
		info = ("-----┕" + info);
		//WriteTxtAndHTMLOnly(info); 
		infoHTML += WriteBold1HTML(info);
		infoTxt += WriteNormalTxt(info);
	}
	
	protected void WriteTestItem2(String testItem, String info) {
		// TODO 自动生成的方法存根
		info = ("---------┕" + info);
		infoHTML += WriteBold2HTML(info);
		infoTxt += WriteNormalTxt(info);
		//WriteTxtAndHTMLOnly(info);
		addInfoItem(testItem, info);
	}
	
	/*protected void WriteTxtAndHTMLOnly(String info) {
		// TODO 自动生成的方法存根
		info = ("-------------┕" + info);
		infoHTML += WriteNormalHTML(info);
		infoTxt += WriteNormalTxt(info);
	}*/
	
	protected void WriteError1Info(String testItem, String info) {
		// TODO 自动生成的方法存根
		addError1Item(testItem, info);
		addInfoItem(testItem, info);
		info = ("-------------┕" + info);
		infoHTML += WriteErrorHTML(info);
		infoTxt += WriteNormalTxt(info);
	}
	
	/*protected void WriteRedInfo(String testItem, String info) {
		// TODO 自动生成的方法存根
		infoHTML += WriteRedHTML(info);
		infoTxt += WriteNormalTxt(info);
	}*/
	
	protected void WriteError2Info(String testItem, String info) {
		// TODO 自动生成的方法存根
		addError2Item(testItem, info);
		addInfoItem(testItem, info);
		info = ("-------------┕" + info);
		infoHTML += WriteError2HTML(info);
		infoTxt += WriteNormalTxt(info);
	}

	protected void WriteBoldInfoS(String testItem, String info) {
		// TODO 自动生成的方法存根
		addInfoItem(testItem, info);
		info = ("-------------┕" + info);
		infoHTML += WriteBold3HTML(info);
		infoTxt += WriteNormalTxt(info);
	}
	
	protected void WriteBoldInfoI(String testItem, String info) {
		// TODO 自动生成的方法存根
		infoHTML += WriteBold3HTML(info);
		infoTxt += WriteNormalTxt(info);
		addInfoItem(testItem, info);
	}

	protected void WriteNormalInfoS(String testItem, String info) {
		// TODO 自动生成的方法存根
		addInfoItem(testItem, info);
		info = ("-------------┕" + info);
		infoHTML += WriteNormalHTML(info);
		infoTxt += WriteNormalTxt(info);
	}
	
	protected void WriteNormalInfoI(String testItem, String info) {
		// TODO 自动生成的方法存根
		addInfoItem(testItem, info);
		infoHTML += WriteNormalHTML(info);
		infoTxt += WriteNormalTxt(info);
	}
	
	/*protected void WriteNormalInfo(String info) {
		// TODO 自动生成的方法存根
		infoHTML += WriteNormalHTML(info);
		infoTxt += WriteNormalTxt(info);
	}*/

	protected void WriteNormalInfoWithoutEnterS(String testItem, String info) {
		addInfoItemWithoutEnter(testItem,info);
		info = ("-------------┕" + info);
		infoHTML += WriteNormalHTMLWithoutEnter(info);
		infoTxt += WriteNormalTxtWithoutEnter(info);
	}
	
	protected void WriteNormalInfoWithoutEnterI(String testItem, String info) {
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

	/*protected void WriteBoldInfo(String testItem, int info) {
		// TODO 自动生成的方法存根
		infoHTML += WriteBoldHTML(info);
		infoTxt += WriteNormalTxt(info);
		addInfoItem(testItem, info + "");
	}*/
	
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
	
	private void addError1Item(String testItem, String info) {
		// TODO 自动生成的方法存根
		ErrorInformationCollection errorInformation = new ErrorInformationCollection();
		errorInformation.setErrorInformation(info);
		errorInformation.setErrorType(ErrorInformationCollection.ErrorType.StandardError);
		if(errorInformations.get(testItem) == null){
			ArrayList<ErrorInformationCollection> errorInformationCollections = new ArrayList<>();
			errorInformationCollections.add(errorInformation);
			errorInformations.put(testItem, errorInformationCollections);
		}else{
			errorInformations.get(testItem).add(errorInformation);
		}
	}
	
	private void addError2Item(String testItem, String info) {
		// TODO 自动生成的方法存根
		ErrorInformationCollection errorInformation = new ErrorInformationCollection();
		errorInformation.setErrorInformation(info);
		errorInformation.setErrorType(ErrorInformationCollection.ErrorType.LogicalError);
		if(errorInformations.get(testItem) == null){
			ArrayList<ErrorInformationCollection> errorInformationCollections = new ArrayList<>();
			errorInformationCollections.add(errorInformation);
			errorInformations.put(testItem, errorInformationCollections);
		}else{
			errorInformations.get(testItem).add(errorInformation);
		}
	}
	
	public void errorClassification(String testItem, String testContent, String testResults, String note) {
		ArrayList<String> errorItem = new ArrayList<String>();
		errorItem.add(testContent);
		errorItem.add(testResults);
		errorItem.add(note);
		errorMap.put(testItem, errorItem);
	}
}
