package com.ieee.atml.test.item;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.filechooser.FileFilter;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ieee.atml.info.InfoWrite;
import com.ieee.atml.test.assist.CompareConnectorPins;
import com.ieee.atml.test.assist.StationReader;
import com.ieee.atml.test.assist.TestStationVisitor;
import com.ieee.atml.test.resource.ConnectorClass;
import com.ieee.atml.test.resource.ConnectorPinClass;
import com.ieee.atml.test.resource.PortClass;
import com.ieee.atml.util.FileFilterBySuffix;
import com.ieee.atml.util.StringUtil;
import com.ieee.atml.util.XPathStandard;

public class TestStationReader extends InfoWrite implements XPathStandard, CompareConnectorPins {
	// private String errorInfo="";
	// private static final String regEX =
	// "(?:\\/(?:[a-zA-Z]{0,}:){0,1}[a-zA-Z]{0,}(?:(?:\\/){0,1}\\[@[a-zA-Z]{0,}=(['\"])[\\u4e00-\\u9fa5\\w\\+-]{0,}\\1\\]){0,}){1,}";

	// private static final String[] testItem = { "Port及其管脚connectorPin",
	// "Switch", "Instruments", "TestStation路径检测",
	// "Instruments路径检测" };

	private List<String> instpathlist = new ArrayList();
	private List<String>  instDocumentId = new ArrayList<>();
	private HashMap<String, Document> instUUIDXmlMap = new HashMap<String, Document>();
	private ArrayList<HashMap<String, String>> unStandardXpath = new ArrayList<HashMap<String, String>>();
	private ArrayList<HashMap<String, String>> standardXpath = new ArrayList<HashMap<String, String>>();
	private ArrayList<HashMap<String, String>> unExistXpath = new ArrayList<HashMap<String, String>>();
	
	private ArrayList<ArrayList<String>> unExistXFile = new ArrayList<>();

	public TestStationReader() {
		testItem.add("TestStation接口中Connector描述");
		testItem.add("TestStation接口中Port描述");
		testItem.add("Port与Connector管脚一致性检查");
		testItem.add("Switch");
		testItem.add("Instruments");
		testItem.add("TestStation路径配对检测");
		testItem.add("仪器文件引用正确性检测");
	}

	public String getInfoHTML() {
		return infoHTML;
	}

	public void VirtualMain(String xmlpath, String instpath) {
		SAXReader saxReader = new SAXReader();
		File stationfile = new File(xmlpath);
		try {
			Document doc = saxReader.read(stationfile);
			TestStationVisitor tsVisitor = new TestStationVisitor();
			doc.accept(tsVisitor);

			String string = stationfile.getName();
			String[] strings = string.split("\\.");
			fileName = strings[0];

			String txtpath = fileName + "检测结果文件.txt";

			// 获得Instrument文件夹下的所有仪器xml文件的路径
			if (instpath.equals("")) {
				instpathlist = tsVisitor.getInstpathlist();
				instDocumentId = tsVisitor.getInstDocumentId();
			} else {
				GenerateInstrumentFilePaths(instpath);
			}

			// 生成uuid-xml的HashMap
			GenerateUUIDDocMap(saxReader);

			initInfoMap();
			// 检测TS自连得路径信息(Lancelot)
			StationReader stationtest = new StationReader(testItem);
			String[] stationInfo = stationtest.stationReader(xmlpath);
			
			// System.out.println(stationErrorpath[1]);
			// 将Station文件的检测结果写入文件和“结果显示区”
			WriteTxtFile(txtpath, tsVisitor, stationInfo);
			infoWriteWord(fileName);
			//WriteRedInfo("-----------检测通过-----------","-----------检测通过-----------");
		} catch (DocumentException e) {
			System.out.println(e.getMessage());
		}

	}

	private void TestInstrumentXpath(HashMap<String, String> instDocIDUUIDMaps,
			HashMap<String, ArrayList<String>> instDocIDXpathMaps) {
		// TODO 自动生成的方法存根
		WriteTestItem1(testItem.get(6));
		// errorInfo+="待检测的XPath路径个数为：";
		WriteNormalInfoWithoutEnterS(testItem.get(6), "Station文件共存在有效仪器：");
		// addErrorItemWithoutEnter("Station文件共存在仪器：");
		WriteBoldInfoI(testItem.get(6), instDocIDXpathMaps.size() + "个");
		// addErrorItem(testItem[4], instDocIDXpathMaps.size() + "个");
		
		
		
		
		if(unExistXFile.size() > 0){
			WriteError2Info(testItem.get(6), "共有" + unExistXFile.size() + "仪器文件路径错误");
			WriteNormalInfoS(testItem.get(6), "仪器文件路径错误的仪器documentId为：");
		}else{
			WriteNormalInfoS(testItem.get(6), "仪器文件路径完全正确");
		}
		for (int i = 0; i < unExistXFile.size(); i++) {
			ArrayList<String> unExistXFileItem = unExistXFile.get(i);
			WriteBoldInfoS(testItem.get(6), unExistXFileItem.get(0));
		}
		
		WriteTestItem2(testItem.get(6), "仪器文件XPath路径检测");
		WriteNormalInfoWithoutEnterS(testItem.get(6), "待检测的XPath路径个数为：");
		
		// addErrorItem(testItem[4], "---------Xpath Standard检测--------");
		Iterator iter1 = instDocIDXpathMaps.entrySet().iterator();
		int num = 0;
		while (iter1.hasNext()) {
			Map.Entry<String, ArrayList<String>> entry1 = (Map.Entry<String, ArrayList<String>>) iter1.next();
			ArrayList<String> xpathList = entry1.getValue();
			boolean isExist = true;
			for (int i = 0; i < unExistXFile.size(); i++) {
				ArrayList<String> unExistXFileItem = unExistXFile.get(i);
				if(unExistXFileItem.get(0).equals(entry1.getKey())){
					isExist = false;
				}
			}
			if(!isExist){
				continue;
			}
			for (Iterator iterator = xpathList.iterator(); iterator.hasNext();) {
				String instxpath1 = (String) iterator.next();
				// String instxpath1 = entry1.getValue();
				if (matchXPath(instxpath1)) {
					int index = instxpath1.indexOf("/[@");
					if (index != -1) {
						instxpath1 = instxpath1.replace("/[", "[");
						System.out.println(instxpath1);
					}
					HashMap<String, String> xpath = new HashMap<String, String>();
					xpath.put(entry1.getKey(), instxpath1);
					standardXpath.add(xpath);
					/*
					 * if (standardXpath.containsKey(entry1.getKey())) {
					 * standardXpath.get(entry1.getKey()).add(instxpath1); }
					 * else { ArrayList<String> xpath = new ArrayList<String>();
					 * xpath.add(instxpath1); standardXpath.put(entry1.getKey(),
					 * xpath); }
					 */
					// standardXpath.put(entry1.getKey(), instxpath1);
				} else {
					/*
					 * if (unStandardXpath.containsKey(entry1.getKey())) {
					 * unStandardXpath.get(entry1.getKey()).add(instxpath1); }
					 * else { ArrayList<String> xpath = new ArrayList<String>();
					 * xpath.add(instxpath1);
					 * unStandardXpath.put(entry1.getKey(), xpath); }
					 */
					// unStandardXpath.put(entry1.getKey(), instxpath1);
					// WriteErrorInfo(instxpath1+" is not a standard XPath!");
					HashMap<String, String> xpath = new HashMap<String, String>();
					xpath.put(entry1.getKey(), instxpath1);
					unStandardXpath.add(xpath);
				}
				num++;
			}

		}
		WriteBoldInfoI(testItem.get(6), num + "");
		WriteTestItem2(testItem.get(6), "XPath格式正确性检测");
		// errorInfo+="标准的XPath路径个数为：";
		WriteNormalInfoWithoutEnterS(testItem.get(6), "标准的XPath路径个数为：");
		// addErrorItemWithoutEnter(testItem[4], "标准的XPath路径个数为：");
		WriteBoldInfoI(testItem.get(6), standardXpath.size() + "");
		// addErrorItem(testItem[4], standardXpath.size() + "");
		// errorInfo+="不标准的XPath路径个数为：";
		WriteNormalInfoWithoutEnterS(testItem.get(6), "不标准的XPath路径个数为：");
		// addErrorItemWithoutEnter(testItem[4], "标准的XPath路径个数为：");
		WriteBoldInfoI(testItem.get(6), unStandardXpath.size() + "");
		// addErrorItem(testItem[4], unStandardXpath.size() + "");
		if (unStandardXpath.size() > 0) {
			WriteNormalInfoS(testItem.get(6), "不标准的XPath路径为：");
			// addErrorItemWithoutEnter(testItem[4], "不标准的XPath路径为：");
			Iterator errorIter1 = unStandardXpath.iterator();
			while (errorIter1.hasNext()) {
				HashMap<String, String> xpath1 = (HashMap<String, String>) errorIter1.next();
				Iterator iter11 = xpath1.entrySet().iterator();
				while (iter11.hasNext()) {
					Map.Entry<String, String> tempEntry = (Map.Entry<String, String>) iter11.next();
					WriteError1Info(testItem.get(6), "DocumentID=" + tempEntry.getKey());
					// addErrorItem(testItem[4], "DocumentID=" +
					// tempEntry.getKey());
					WriteError1Info(testItem.get(6), "XPath=" + tempEntry.getValue());
					// addErrorItem(testItem[4], "XPath=" +
					// tempEntry.getValue());
				}
			}
		}
		
		
		
		WriteTestItem2(testItem.get(6), "XPath路径引用正确及匹配性检查");
		// addErrorItem(testItem[4], "---------Xpath可达性检测---------");
		Iterator iter2 = standardXpath.iterator();
		while (iter2.hasNext()) {
			HashMap<String, String> xpath1 = (HashMap<String, String>) iter2.next();
			Iterator iter11 = xpath1.entrySet().iterator();
			while (iter11.hasNext()) {
				Map.Entry<String, String> entry2 = (Map.Entry<String, String>) iter11.next();
				String docID2 = entry2.getKey();
				String instxpath2 = entry2.getValue();
				String tempuuid = "";

				if (instDocIDUUIDMaps.containsKey(docID2)) {
					
					tempuuid = instDocIDUUIDMaps.get(docID2);
					/*boolean isExist = true;
					 * for (int i = 0; i < unExistXFile.size(); i++) {
						ArrayList<String> unExistXFileItem = unExistXFile.get(i);
						if(unExistXFileItem .get(0).equals(docID2)){
							isExist = false;
						}
					}
					if(!isExist){
						continue;
					}*/
					if (instUUIDXmlMap.containsKey(tempuuid)) {
						Document tempdoc = instUUIDXmlMap.get(tempuuid);
						List<Element> list = null;

						if (tempdoc != null) {
							try {
								list = tempdoc.selectNodes(instxpath2);
							} catch (Exception e) {
								// TODO: handle exception
								// WriteErrorInfo(instxpath2+" is not EXIST!");
								HashMap<String, String> xpath = new HashMap<String, String>();
								xpath.put(docID2, instxpath2);
								unExistXpath.add(xpath);
								// unExistXpath.put(docID2, instxpath2);
							}
							if (list != null) {
								if (list.size() == 0) {
									HashMap<String, String> xpath = new HashMap<String, String>();
									xpath.put(docID2, instxpath2);
									unExistXpath.add(xpath);
								}
							}
						} else {
							System.out.println("名称为" + tempuuid + " 的文件为NULL!");
						}

					} else {
						// WriteErrorInfo(instxpath2+" is not EXIST!");
						HashMap<String, String> xpath = new HashMap<String, String>();
						xpath.put(docID2, instxpath2);
						unExistXpath.add(xpath);
						// unExistXpath.put(docID2, instxpath2);
					}

				} else {
					// WriteErrorInfo(instxpath2+" is not EXIST!");
					HashMap<String, String> xpath = new HashMap<String, String>();
					xpath.put(docID2, instxpath2);
					unExistXpath.add(xpath);
					// unExistXpath.put(docID2, instxpath2);
				}
			}

		}

		// errorInfo+="标准的XPath路径中可达路径的个数为：";
		WriteNormalInfoWithoutEnterS(testItem.get(6), "标准的XPath路径中可达路径的个数为：");
		// addErrorItemWithoutEnter(testItem[4], "标准的XPath路径中可达路径的个数为：");
		WriteBoldInfoI(testItem.get(6), (standardXpath.size() - unExistXpath.size()) + "");
		// addErrorItem(testItem[4], (standardXpath.size() -
		// unExistXpath.size()) + "");
		// errorInfo+="标准的XPath路径中不可达路径的个数为：";
		WriteNormalInfoWithoutEnterS(testItem.get(6), "标准的XPath路径中不可达路径的个数为：");
		// addErrorItemWithoutEnter(testItem[4], "标准的XPath路径中不可达路径的个数为：");
		WriteBoldInfoI(testItem.get(6), unExistXpath.size() + "");
		// addErrorItem(testItem[4], unExistXpath.size() + "");
		if (unExistXpath.size() > 0) {
			WriteNormalInfoS(testItem.get(6), "标准的XPath路径中不可达路径如下所示：");
			// addErrorItem(testItem[4], "标准的XPath路径中不可达路径如下所示：");
			Iterator errorIter2 = unExistXpath.iterator();
			while (errorIter2.hasNext()) {
				HashMap<String, String> xpath1 = (HashMap<String, String>) errorIter2.next();
				Iterator iter11 = xpath1.entrySet().iterator();
				while (iter11.hasNext()) {
					Map.Entry<String, String> tempEntry = (Map.Entry<String, String>) iter11.next();
					WriteError2Info(testItem.get(6), "DocumentID=" + tempEntry.getKey());
					// addErrorItem(testItem[4], "DocumentID=" +
					// tempEntry.getKey());
					WriteError2Info(testItem.get(6), "XPath=" + tempEntry.getValue());
					// addErrorItem(testItem[4], "XPath=" +
					// tempEntry.getValue());
				}
			}
		}

	}

	/*
	 * private boolean MatchXpath(String xpath) { // TODO 自动生成的方法存根 Pattern
	 * pattern = Pattern.compile(regEX); Matcher matcher =
	 * pattern.matcher(xpath); return matcher.matches(); }
	 */

	private void GenerateUUIDDocMap(SAXReader saxReader) {
		// TODO 自动生成的方法存根
		for (int i = 0; i < instpathlist.size(); i++) {
			String fileDir = instpathlist.get(i);
			File instrumentfile = new File(fileDir);
			if (!instrumentfile.exists()) {
				System.out.println(fileDir + "路径不存在！");
				ArrayList<String> unExistXFileItem = new ArrayList<>();
				unExistXFileItem.add(instDocumentId.get(i));
				unExistXFileItem.add(instpathlist.get(i));
				unExistXFile.add(unExistXFileItem);
				// continue;
			} else {
				try {
					Document doc = saxReader.read(instrumentfile);
					String tempuuid = doc.getRootElement().attributeValue("uuid");
					instUUIDXmlMap.put(tempuuid, doc);

				} catch (DocumentException e) {
					// TODO: handle exception
					System.out.println(e.getMessage());
				}
			}
		}
		/*for (Iterator iterator = instpathlist.iterator(); iterator.hasNext();) {
			System.out.println(instpathlist.size());
			File instrumentfile = new File((String) iterator.next());
			if (!instrumentfile.exists()) {
				System.out.println(iterator.next() + "路径不存在！");
				// continue;
			} else {
				try {
					Document doc = saxReader.read(instrumentfile);
					String tempuuid = doc.getRootElement().attributeValue("uuid");
					instUUIDXmlMap.put(tempuuid, doc);

				} catch (DocumentException e) {
					// TODO: handle exception
					System.out.println(e.getMessage());
				}
			}
		}*/

	}

	private void GenerateInstrumentFilePaths(String instpath) {
		// TODO 自动生成的方法存根
		try {
			File file = new File(instpath);
			if (!file.isDirectory()) {
				System.out.println("文件\n");
				System.out.println("绝对路径=" + file.getAbsolutePath());
			} else {
				System.out.println("文件夹\n");
				FileFilter xmlfilter = new FileFilterBySuffix(".xml");

				GetFilePathList(file, xmlfilter);

			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}

	private void GetFilePathList(File file, FileFilter xmlfilter) {
		// TODO 自动生成的方法存根
		File[] xmlfiles = file.listFiles();
		for (File tempfile : xmlfiles) {
			if (!tempfile.isDirectory() && xmlfilter.accept(tempfile)) {
				instpathlist.add(tempfile.getAbsolutePath());
			}
		}
	}

	private void WriteTxtFile(String txtpath,
			TestStationVisitor tsVisitor, String[] stationInfo ) {
		// TODO 自动生成的方法存根
		File tstxt = new File(txtpath);
		infoHTML = "";
		if (tstxt.exists()) {
			tstxt.delete();
		} else {
			try {
				tstxt.createNewFile();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(tstxt);
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		try {
			// String tempstr="**********Port及其管教connectorPin**********\n";
			// fos.write(tempstr.getBytes());
			// errorInfo+="检测完成，结果如下：\n";
			WriteTestItem0("检测结果");
			// errorInfo+="**********Port及其管脚connectorPin**********\n";
			String tempStr = tsVisitor.getPortList().size() + "";
			/*WriteTestItem1(testItem.get(0));
			// errorInfo+="Port的数目为：";
			WriteNormalInfoWithoutEnter(testItem.get(0), "Port的数目为：");
			// addErrorItemWithoutEnter(testItem[0], "Port的数目为：");

			

			WriteBoldInfo(testItem.get(0), tempStr);
			// addErrorItem(testItem[0], tempStr);

			// fos.write(tempstr.getBytes());
			for (Iterator<PortClass> iterator = tsVisitor.getPortList().iterator(); iterator.hasNext();) {
				PortClass tempPort = iterator.next();
				// errorInfo+="名称为"+tempPort.getPortName()+"的Port下的ConnectorPin数目为：";
				WriteNormalInfoWithoutEnter(testItem.get(0),
						"名称为" + tempPort.getPortName() + "的Port下的ConnectorPin数目为：");
				// addErrorItemWithoutEnter(testItem[0], "名称为" +
				// tempPort.getPortName() + "的Port下的ConnectorPin数目为：");
				tempStr = tempPort.getConnectorPins().size() + "";
				WriteBoldInfo(testItem.get(0), tempStr);
				// addErrorItem(testItem[0], tempStr);

				// fos.write(tempstr.getBytes());

				for (Iterator<ConnectorPinClass> iterator2 = tempPort.getConnectorPins().iterator(); iterator2
						.hasNext();) {
					ConnectorPinClass tempCP = iterator2.next();
					tempStr = "connectorID=" + tempCP.getConnectorID() + ";pinID=" + tempCP.getPinID();
					WriteNormalInfo(testItem.get(0), tempStr);
					// addErrorItem(testItem[0], tempStr);
					// errorInfo+="connectorID="+tempCP.getConnectorID()+";pinID="+tempCP.getPinID()+"\n";
					// fos.write(tempstr.getBytes());
				}

			}*/
			WriteTestItem1(testItem.get(0));

			for (Iterator<ConnectorClass> iterator = tsVisitor.getConnectorList().iterator(); iterator.hasNext();) {
				ConnectorClass tempCon = iterator.next();
				// errorInfo+="节点c:ConnectorPin的connectorID属性为"+connectorId+"的数目为"+connectorNum.get(num++)+"\n";
				//errorInfo += "Connector " + tempCon.getConID() + "上的针脚数目=";
				WriteNormalInfoWithoutEnterS(testItem.get(0), "Connector " + tempCon.getConID() + "上的针脚数目=");
				WriteBoldInfoI(testItem.get(0), tempCon.getcPins().size() + "");
				//addErrorItem(testItem[0],
				//		"Connector " + tempCon.getConID() + "上的针脚数目=" + tempCon.getcPins().size());
				// fos.write(tempstr.getBytes());
			}

			WriteTestItem1(testItem.get(1));
			//errorInfo += "Port的个数=";
			WriteNormalInfoWithoutEnterS(testItem.get(1), "Port的个数=");
			WriteBoldInfoI(testItem.get(1), tsVisitor.getPortList().size() + "");
			//addErrorItem(testItem[1],"Port的个数=" +  uvisitor.getPortList().size());
			for (Iterator<ConnectorClass> iterator = tsVisitor.getConnectorList().iterator(); iterator.hasNext();) {
				String conID = iterator.next().getConID();
				int tempNum = 0;
				// errorInfo+="节点c:ConnectorPin的connectorID属性为"+connectorId+"的数目为"+connectorNum.get(num++)+"\n";
				for (Iterator<PortClass> iterator2 = tsVisitor.getPortList().iterator(); iterator2.hasNext();) {
					List<ConnectorPinClass> tempCPins = iterator2.next().getConnectorPins();
					for (Iterator<ConnectorPinClass> iterator3 = tempCPins.iterator(); iterator3.hasNext();) {
						ConnectorPinClass connectorPinClass = (ConnectorPinClass) iterator3.next();
						if (connectorPinClass.getConnectorID().equals(conID)) {

							tempNum++;
						}
					}
				}

				//errorInfo += "Port中定义的属于connector " + conID + "上的针脚数目=";
				WriteNormalInfoWithoutEnterS(testItem.get(1), "Port中定义的属于connector " + conID + "上的针脚数目=");
				WriteBoldInfoI(testItem.get(1), tempNum++ + "");
				//addErrorItem(testItem[1],"Port中定义的属于connector " + conID + "上的针脚数目=" + tempNum);
				// fos.write(tempstr.getBytes());
			}
			
			
			WriteTestItem1(testItem.get(2));
			CompareTwoList(tsVisitor.getConnectorPins(), tsVisitor.getConnectorPins1(), errorPin, errorPin1);
			if ((!errorPin.isEmpty()) || (!errorPin1.isEmpty())) {
				// errorInfo+="不匹配节点的XPath路径为：\n";
				List<String> pathList = tsVisitor.getcPinpath();
				List<String> pathList1 = tsVisitor.getcPinpath1();
				if (!errorPin.isEmpty()) {
					WriteNormalInfoS(testItem.get(2), "Connector上未被Port采用的针脚的XPath如下:");
					//addErrorItem(testItem[2],"Connector上未被Port采用的针脚的XPath如下:");
					// fos.write(tempstr.getBytes());

					for (Iterator<String> iterator = errorPin.iterator(); iterator.hasNext();) {
						String tempID = iterator.next();
						WriteError2Info(testItem.get(2), pathList.get(tsVisitor.getConnectorPins().indexOf(tempID)).toString());
						//addErrorItem(testItem[2],pathList.get(uvisitor.getConnectorPins().indexOf(tempID)).toString());
						// errorInfo+=pathList.get(list.indexOf(tempID))+"\n";
						// fos.write(tempstr.getBytes());
					}
				}
				if (!errorPin1.isEmpty()) {
					WriteNormalInfoS(testItem.get(2), "Port中定义的不属于connector中的针脚的XPath如下:");
					//addErrorItem(testItem[2],"Port中定义的不属于connector中的针脚的XPath如下:");
					for (Iterator<String> iterator = errorPin1.iterator(); iterator.hasNext();) {
						String tempID = iterator.next();
						WriteError2Info(testItem.get(2), pathList1.get(tsVisitor.getConnectorPins1().indexOf(tempID)).toString());
						//addErrorItem(testItem[2],pathList1.get(uvisitor.getConnectorPins1().indexOf(tempID)).toString());
						// errorInfo+=pathList1.get(list1.indexOf(tempID))+"\n";
						// fos.write(tempstr.getBytes());
					}
				}

			} else {
				WriteNormalInfoS(testItem.get(2), "节点完全匹配！");
				//addErrorItem(testItem[2],"节点完全匹配！");
				// errorInfo+="节点完全匹配！\n";
			}
			// errorInfo+="**********Switch**********\n";
			WriteTestItem1(testItem.get(3));
			// fos.write(tempstr.getBytes());
			// errorInfo+="Swithc的个数为:";
			WriteNormalInfoWithoutEnterS(testItem.get(3), "Swithc的个数为:");
			// addErrorItemWithoutEnter(testItem[1], "Swithc的个数为:");
			tempStr = tsVisitor.getSwitchNames().size() + "";
			WriteBoldInfoI(testItem.get(3), tempStr);
			// addErrorItem(testItem[1], tempStr);
			// fos.write(tempstr.getBytes());
			if(tsVisitor.getSwitchNames().size() > 0){
				WriteNormalInfoS(testItem.get(3), "Swithc的名称为:");
				for (Iterator<String> iterator = tsVisitor.getSwitchNames().iterator(); iterator.hasNext();) {
					String swName = iterator.next();
					// errorInfo+=swName+"\n";
					WriteBoldInfoS(testItem.get(3), swName);
					// addErrorItem(testItem[1], swName);
				}
			}
			// addErrorItem(testItem[1], "Swithc的名称为:");
			// errorInfo+="Swithc的名称为:\n";
			// fos.write(tempstr.getBytes());

			// errorInfo+="**********Instruments**********\n";
			WriteTestItem1(testItem.get(4));
			// errorInfo+="Instruments的个数为:";
			WriteNormalInfoWithoutEnterS(testItem.get(4), "Instruments的个数为:");
			// addErrorItemWithoutEnter(testItem[2], "Instruments的个数为:");
			tempStr = tsVisitor.getInstrumentIDs().size() + "";
			WriteBoldInfoI(testItem.get(4), tempStr);
			// addErrorItem(testItem[2], tempStr);
			// errorInfo+="Instruments的ID为:\n";
			if( tsVisitor.getInstrumentIDs().size() > 0){
				WriteNormalInfoS(testItem.get(4), "Instruments的ID为:");
				// addErrorItem(testItem[2], "Instruments的ID为:");
				for (Iterator<String> iterator = tsVisitor.getInstrumentIDs().iterator(); iterator.hasNext();) {
					String insID = iterator.next();
					// errorInfo+=insID+"\n";
					WriteBoldInfoS(testItem.get(4), insID);
					// addErrorItem(testItem[2], insID);
				}
			}
			// errorHTML += "**********TestStation路径检测**********<br>" +
			// errorpath[0];
			// errorTxt += "**********TestStation路径检测**********\n" +
			// errorpath[1];
			WriteNormalTxtInfo(testItem.get(5), stationInfo[0]);
			WriteNormalWordInfo(testItem.get(5), stationInfo[2]);
			WriteNormalHTMLInfo(testItem.get(5), stationInfo[1]);
			// WriteNormalInfoWithoutEnter(errorpath);
			// 进行Instrument检测，并将检测结果写入文件和“结果显示区”
			TestInstrumentXpath(tsVisitor.getInstDocIDUUIDMaps(), tsVisitor.getInstDocIDXpathMaps());
			addTestResult();
			fos.write(infoTxt.getBytes());

		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		try {
			fos.close();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
