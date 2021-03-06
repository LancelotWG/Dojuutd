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

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ieee.atml.info.InfoWrite;
import com.ieee.atml.test.assist.TDVisitor;
import com.ieee.atml.test.assist.UUTDVisitor;
import com.ieee.atml.util.StringUtil;

public class TDReader extends InfoWrite {
	// private String errorInfo="";

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		TDReader test = new TDReader();
		test.VirtualMain(testDescriptionDir, UUTDir);
	}

	private enum ParameterType {
		localParameter, globalParamter
	}

	private static final String adapterDir = "TestAdapterConvert_1_测评UUT_5_测评接口适配器1_11.xml";
	private static final String UUTDir = "UUTDescriptionConvert_1_测评UUT_5_测评UUT_5.xml";
	private static final String stationDir = "TestStationDescriptionConvert_1_测评UUT_5_5.xml";
	private static final String testDescriptionDir = "TestDescriptionConvert_1_测评UUT_5(20170121).xml";
	private static final String InstrumentsDir = "Instruments";

	private HashMap<String, List<String>> connectMap = new HashMap<String, List<String>>();
	private HashMap<String, List<String>> signalMap = new HashMap<String, List<String>>();
	private HashMap<String, List<String>> errorSignalMap = new HashMap<String, List<String>>();

	private HashMap<Element, ArrayList<ArrayList<Element>>> signalList = new HashMap<>();
	private HashMap<Element, List<List<String>>> signalListString = new HashMap<>();

	// private ArrayList<ArrayList<String>> instrumentLise = new ArrayList<>();

	// private org.dom4j.Document UUTDocument = null;

	// private static final String[] testItem = { "OperationConnect检测" };

	public TDReader() {
		testItem.add("UUTDescription文件UUID匹配检测");
		testItem.add("OperationConnect检测");
	}

	private void createSignalList(HashMap<String, Element> UUTPortMap) {
		HashMap<Element, ArrayList<ArrayList<Element>>> UUTNodeList = new HashMap<>();
		UUTNodeList = getUUTNode(UUTPortMap);
		Iterator iter1 = UUTNodeList.entrySet().iterator();

		AdapterReader adapterReader = new AdapterReader();
		adapterReader.adapterReader(adapterDir, UUTDir, stationDir);
		int index = 0;
		while (iter1.hasNext()) {
			Map.Entry<Element, List<List<Element>>> entry1 = (Map.Entry<Element, List<List<Element>>>) iter1.next();
			System.out.println("#################" + index + "################");
			System.out.println(entry1.getKey());
			List<List<Element>> portLists = entry1.getValue();
			for (Iterator iterator = portLists.iterator(); iterator.hasNext();) {
				System.out.println("----------------------------------");
				List<Element> portList = (List<Element>) iterator.next();
				for (Iterator iterator1 = portList.iterator(); iterator1.hasNext();) {
					Element port = (Element) iterator1.next();
					System.out.println(port.getUniquePath() + " " + port.attributeValue("name"));
				}
			}
			index++;
		}
		List<ArrayList<ArrayList<String>>> adapterLinkLists = adapterReader.getNetWorkLinks();
		HashMap<Element, ArrayList<ArrayList<String>>> stationNodeList = new HashMap<>();
		stationNodeList = stationNodeList(UUTNodeList, adapterLinkLists);
		readData2String(stationNodeList);
	}

	private void readData2String(HashMap<Element, ArrayList<ArrayList<String>>> nodeList) {
		Iterator iter1 = nodeList.entrySet().iterator();
		int index = 0;
		while (iter1.hasNext()) {
			Map.Entry<Element, List<List<String>>> entry1 = (Map.Entry<Element, List<List<String>>>) iter1.next();
			System.out.println("#################" + index + "################");
			System.out.println(entry1.getKey());
			List<List<String>> portLists = entry1.getValue();
			for (Iterator iterator = portLists.iterator(); iterator.hasNext();) {
				System.out.println("----------------------------------");
				List<String> portList = (List<String>) iterator.next();
				for (Iterator iterator1 = portList.iterator(); iterator1.hasNext();) {
					String port = (String) iterator1.next();
					System.out.println(port);
				}
			}
			index++;
		}
	}

	private HashMap<Element, ArrayList<ArrayList<String>>> stationNodeList(
			HashMap<Element, ArrayList<ArrayList<Element>>> UUTNodeList,
			List<ArrayList<ArrayList<String>>> adapterLinkLists) {
		HashMap<Element, ArrayList<ArrayList<String>>> stationNodeList = new HashMap<>();
		Iterator iter1 = UUTNodeList.entrySet().iterator();
		while (iter1.hasNext()) {
			Map.Entry<Element, List<List<Element>>> entry1 = (Map.Entry<Element, List<List<Element>>>) iter1.next();
			List<List<Element>> portLists = entry1.getValue();
			ArrayList<ArrayList<String>> stationPort = new ArrayList<>();
			for (Iterator iterator = portLists.iterator(); iterator.hasNext();) {
				List<Element> portList = (List<Element>) iterator.next();
				ArrayList<String> stationPortItem = new ArrayList<>();
				for (Iterator iterator1 = portList.iterator(); iterator1.hasNext();) {
					Element port = (Element) iterator1.next();
					for (Iterator iterator3 = adapterLinkLists.iterator(); iterator3.hasNext();) {
						ArrayList<ArrayList<String>> arrayLists = (ArrayList<ArrayList<String>>) iterator3.next();
						ArrayList<String> UUTNode = arrayLists.get(0);
						for (Iterator iterator2 = UUTNode.iterator(); iterator2.hasNext();) {
							String string = (String) iterator2.next();
							if (string.equals(port.getUniquePath())) {
								stationPortItem.addAll(arrayLists.get(1));
							}
						}
					}
				}
				stationPort.add(stationPortItem);
			}
			stationNodeList.put(entry1.getKey(), stationPort);
		}
		return stationNodeList;
	}

	private HashMap<Element, ArrayList<ArrayList<Element>>> getUUTNode(HashMap<String, Element> UUTPortMap) {
		HashMap<Element, ArrayList<ArrayList<Element>>> UUTNodeList = new HashMap<>();
		Iterator iter1 = signalListString.entrySet().iterator();
		while (iter1.hasNext()) {
			Map.Entry<Element, List<List<String>>> entry1 = (Map.Entry<Element, List<List<String>>>) iter1.next();
			List<List<String>> portLists = entry1.getValue();
			ArrayList<ArrayList<Element>> UUTPorts = new ArrayList<>();
			for (Iterator iterator = portLists.iterator(); iterator.hasNext();) {
				List<String> portList = (List<String>) iterator.next();
				ArrayList<Element> UUTPortItem = new ArrayList<>();
				for (Iterator iterator1 = portList.iterator(); iterator1.hasNext();) {
					String port = (String) iterator1.next();
					Iterator UUTPort = UUTPortMap.entrySet().iterator();
					while (UUTPort.hasNext()) {
						Map.Entry<String, Element> UUTPortEntry = (Map.Entry<String, Element>) UUTPort.next();
						String k = (String) UUTPortEntry.getKey();
						if (port.equals(k)) {
							UUTPortItem.add(UUTPortEntry.getValue());
						}
					}
				}
				UUTPorts.add(UUTPortItem);
			}
			UUTNodeList.put(entry1.getKey(), UUTPorts);
		}
		return UUTNodeList;
	}

	public String getInfoHTML() {
		return infoHTML;
	}

	public void VirtualMain(String tdPath, String uutPath) {
		// 初始化Connetction classes
		InstanceConnectMap();
		SAXReader saxReader = new SAXReader();
		File tdFile = new File(tdPath);
		File uutFile = new File(uutPath);
		if (tdFile.exists() && uutFile.exists()) {
			try {
				Document tdDoc = saxReader.read(tdFile);
				TDVisitor tdVisitor = new TDVisitor();
				tdDoc.accept(tdVisitor);

				Document uutDoc = saxReader.read(uutFile);
				// UUTDocument = uutDoc;
				UUTDVisitor uutVisitor = new UUTDVisitor();
				uutDoc.accept(uutVisitor);

				String string = tdFile.getName();
				String[] strings = string.split("\\.");
				fileName = strings[0];

				InstanceSignalMap(tdVisitor);

				MatchTest(uutVisitor.getConnectorPins1());

				// createSignalList(uutVisitor.getPortMap());

				String txtpath = fileName + "检测结果文件.txt";
				initInfoMap();
				WriteTxtFile(txtpath, tdVisitor, uutVisitor);
				infoWriteWord(fileName);

			} catch (DocumentException e) {
				// TODO: handle exception
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("File is not exist!");
		}
	}

	private void WriteTxtFile(String txtpath, TDVisitor tdVisitor, UUTDVisitor uutVisitor) {
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

			WriteTxtAndHTMLOnly("检测完成，结果如下:");
			WriteTestItem1(testItem.get(0));
			if(tdVisitor.getUuid().equals(uutVisitor.getUuid())){
				WriteNormalInfo(testItem.get(0), "UUID匹配正确!");
			}else{
				WriteErrorInfo(testItem.get(0), "TestDescription文件中UUTDescription的UUID为：" + tdVisitor.getUuid());
				WriteErrorInfo(testItem.get(0), "UUTDescription文件中的UUID为：" + uutVisitor.getUuid());
			}
			
			WriteTestItem1(testItem.get(1));
			if (errorSignalMap.size() == 0) {
				WriteNormalInfo(testItem.get(1), "测试需求同UUT管脚匹配正确!");
				// addErrorItem(testItem[0], "管脚完全匹配！");
			} else {
				// errorInfo+="管脚不匹配的OperationConnect数目为：";
				WriteNormalInfoWithoutEnter(testItem.get(1), "管脚不匹配的OperationConnect数目为：");
				// addErrorItemWithoutEnter(testItem[0],
				// "管脚不匹配的OperationConnect数目为：");

				String tempStr = errorSignalMap.size() + "";
				WriteBoldInfo(testItem.get(1), tempStr);
				// addErrorItem(testItem[0], tempStr);

				WriteNormalInfo(testItem.get(1), "管脚不匹配的OperationConnect的ID为:");
				// addErrorItem(testItem[0], "管脚不匹配的OperationConnect名称为:");
				Iterator iter = errorSignalMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry<String, List<String>> entry = (Map.Entry<String, List<String>>) iter.next();
					WriteErrorInfo(testItem.get(1), entry.getKey());
					// addErrorItem(testItem[0], entry.getKey());
				}
			}

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

	private void MatchTest(List<String> connectorPins) {
		// TODO 自动生成的方法存根
		boolean UNMATCHED = false;

		Iterator iter = signalMap.entrySet().iterator();
		while (iter.hasNext()) {
			UNMATCHED = false;
			Map.Entry<String, List<String>> entry = (Map.Entry<String, List<String>>) iter.next();
			List<String> attrValueList = entry.getValue();
			for (String attrValue : attrValueList) {
				attrValue = attrValue.replace("-", ":");
				String[] portList = attrValue.split(" ");
				for (int i = 0; i < portList.length; i++) {
					if (!connectorPins.contains(portList[i])) {
						UNMATCHED = true;
						break;
					}
				}

			}
			if (UNMATCHED)
				errorSignalMap.put(entry.getKey(), entry.getValue());
		}
	}

	private void InstanceSignalMap(TDVisitor tdVisitor) {
		// TODO 自动生成的方法存根
		String operationID;
		// List<String> attrValueList=new ArrayList<>();

		for (Element elt : tdVisitor.getSignalEltList()) {
			String nodeName = elt.getName();
			/* System.out.println(elt.getUniquePath()); */
			operationID = elt.getParent().getParent().getParent().attributeValue("ID");
			String actionID = elt.getParent().getParent().getParent().getParent().getParent().getParent()
					.attributeValue("ID");
			List<String> attrValueList = new ArrayList<>();
			if (connectMap.containsKey(nodeName)) {
				List<String> attrNameList = connectMap.get(nodeName);
				for (String attrName : attrNameList) {
					String attrVal = elt.attributeValue(attrName);
					if (attrVal.contains("localParameterID:") || attrVal.contains("globalParameterID:")) {
						ArrayList<String> portList = new ArrayList<>();
						String[] parameters = attrVal.split(" ");
						for (int i = 0; i < parameters.length; i++) {
							String string = parameters[i];
							ParameterType parameterType = ParameterType.localParameter;
							if (string.contains("localParameterID:")) {
								parameterType = ParameterType.localParameter;
								string = string.split("localParameterID:")[1];
							} else if (string.contains("globalParameterID:")) {
								parameterType = ParameterType.globalParamter;
								string = string.split("globalParameterID:")[1];
							}
							switch (parameterType) {
							case localParameter:
								HashMap<String, List<HashMap<String, String>>> parameterMap = tdVisitor
										.getParameterMap();
								List<HashMap<String, String>> item = parameterMap.get(actionID);
								if (item != null) {
									boolean isAdd = false;
									for (HashMap<String, String> hashMap : item) {
										if (hashMap.get(string) != null) {
											String value = hashMap.get(string);
											if (value != null) {
												portList.add(value);
												isAdd = true;
												break;
											}
										}
									}
									if (!isAdd) {
										portList.add("");
									}
								} else {
									portList.add("");
								}
								break;
							case globalParamter:
								HashMap<String, List<HashMap<String, String>>> globelParameterMap = tdVisitor
										.getGlobelParameterMap();
								List<HashMap<String, String>> globelItem = globelParameterMap.get(actionID);
								if (globelItem != null) {
									boolean isAdd = false;
									for (HashMap<String, String> hashMap : globelItem) {
										if (hashMap.get(string) != null) {
											String value = hashMap.get(string);
											if (value != null) {
												portList.add(value);
												isAdd = true;
												break;
											}
										}
									}
									if (!isAdd) {
										portList.add("");
									}
								} else {
									portList.add("");
								}
								break;
							default:
								break;
							}

						}
						String value = "";
						for (int i = 0; i < portList.size(); i++) {
							String string = portList.get(i);
							if (i != 0) {
								value = value + " ";
							}
							value = value + string;
						}
						attrValueList.add(value);
					} else {
						if (attrVal != null)
							attrValueList.add(attrVal);
						else
							attrValueList.add("");
					}
				}
				signalMap.put(operationID, attrValueList);
				List<List<String>> attr = new ArrayList<>();
				for (int i = 0; i < attrValueList.size(); i++) {
					String attrValue = attrValueList.get(i);
					String[] attrValues = attrValue.split(" ");
					List<String> attrList = new ArrayList<>();
					for (int j = 0; j < attrValues.length; j++) {
						String string = attrValues[j];
						attrList.add(string);
					}
					attr.add(attrList);
				}
				signalListString.put(elt, attr);
			} else
				System.out.println(nodeName);
		}
	}

	private void InstanceConnectMap() {
		// TODO 自动生成的方法存根
		String tempName;
		// List<String> tempAttr=new ArrayList<String>();

		tempName = "TwoWire";
		List<String> tempAttr = new ArrayList<String>();
		tempAttr.add("lo");
		tempAttr.add("hi");
		connectMap.put(tempName, tempAttr);

		tempName = "TwoWireComp";
		List<String> tempAttr1 = new ArrayList<String>();
		tempAttr1.add("true");
		tempAttr1.add("comp");
		connectMap.put(tempName, tempAttr1);

		tempName = "ThreeWireComp";
		List<String> tempAttr2 = new ArrayList<String>();
		tempAttr2.add("true");
		tempAttr2.add("comp");
		tempAttr2.add("lo");
		connectMap.put(tempName, tempAttr2);

		tempName = "SinglePhase";
		List<String> tempAttr3 = new ArrayList<String>();
		tempAttr3.add("a");
		tempAttr3.add("n");
		connectMap.put(tempName, tempAttr3);

		tempName = "TwoPhase";
		List<String> tempAttr4 = new ArrayList<String>();
		tempAttr4.add("a");
		tempAttr4.add("b");
		tempAttr4.add("n");
		connectMap.put(tempName, tempAttr4);

		tempName = "ThreePhaseDelta";
		List<String> tempAttr5 = new ArrayList<String>();
		tempAttr5.add("a");
		tempAttr5.add("b");
		tempAttr5.add("c");
		connectMap.put(tempName, tempAttr5);

		tempName = "ThreePhaseWye";
		List<String> tempAttr6 = new ArrayList<String>();
		tempAttr6.add("a");
		tempAttr6.add("b");
		tempAttr6.add("c");
		tempAttr6.add("n");
		connectMap.put(tempName, tempAttr6);

		tempName = "ThreePhaseSynchro";
		List<String> tempAttr7 = new ArrayList<String>();
		tempAttr7.add("x");
		tempAttr7.add("y");
		tempAttr7.add("z");
		connectMap.put(tempName, tempAttr7);

		tempName = "FourWireResolver";
		List<String> tempAttr8 = new ArrayList<String>();
		tempAttr8.add("s1");
		tempAttr8.add("s2");
		tempAttr8.add("s3");
		tempAttr8.add("s4");
		connectMap.put(tempName, tempAttr8);

		tempName = "SynchroResolver";
		List<String> tempAttr9 = new ArrayList<String>();
		tempAttr9.add("r1");
		tempAttr9.add("r2");
		tempAttr9.add("r3");
		tempAttr9.add("r4");
		connectMap.put(tempName, tempAttr9);

		tempName = "Series";
		List<String> tempAttr10 = new ArrayList<String>();
		tempAttr10.add("via");
		connectMap.put(tempName, tempAttr10);

		tempName = "FourWire";
		List<String> tempAttr11 = new ArrayList<String>();
		tempAttr11.add("hi");
		tempAttr11.add("lo");
		tempAttr11.add("hiRef");
		tempAttr11.add("loRef");
		connectMap.put(tempName, tempAttr11);

		tempName = "NonElectrical";
		List<String> tempAttr12 = new ArrayList<String>();
		tempAttr12.add("to");
		tempAttr12.add("from");
		connectMap.put(tempName, tempAttr12);

		tempName = "DigitalBus";
		List<String> tempAttr13 = new ArrayList<String>();
		tempAttr13.add("Pins");
		connectMap.put(tempName, tempAttr13);
	}
}
