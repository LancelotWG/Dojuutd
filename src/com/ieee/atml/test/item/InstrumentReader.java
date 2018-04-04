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

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ieee.atml.info.InfoWrite;
import com.ieee.atml.test.assist.CompareConnectorPins;
import com.ieee.atml.test.assist.TestInstrumentVisitor;
import com.ieee.atml.test.resource.ConnectorClass;
import com.ieee.atml.test.resource.ConnectorPinClass;
import com.ieee.atml.test.resource.PortClass;
import com.ieee.atml.util.StringUtil;
import com.ieee.atml.util.XPathStandard;

public class InstrumentReader extends InfoWrite implements XPathStandard, CompareConnectorPins{
	// private String errorInfo="";
	//private static final String[] testItem = { "Xpath Standard检测", "Xpath可达性检测" };
	private List<String> standardXPath = new ArrayList();
	private List<String> unstandardXPath = new ArrayList();
	private List<String> unExistXpath = new ArrayList();
	//private static final String regEX = "(?:\\/(?:[a-zA-Z]{0,}:){0,1}[a-zA-Z]{0,}(?:(?:\\/){0,1}\\[@[a-zA-Z]{0,}=(['\"])[\\u4e00-\\u9fa5\\w\\+-]{0,}\\1\\]){0,}){1,}";

	public InstrumentReader(){
		testItem.add("Instrument接口中Connector描述");
		testItem.add("Instrument接口中Port描述");
		testItem.add("Port与Connector管脚一致性检查");
		testItem.add("XPath格式正确性检测");
		testItem.add("XPath路径引用正确及匹配性检查");
	}
	
	public String getInfoHTML() {
		return infoHTML;
	}

	public void VirtualMain(String instpath) {
		SAXReader saxReader = new SAXReader();
		File instfile = new File(instpath);
		try {
			Document doc = saxReader.read(instfile);
			TestInstrumentVisitor instVisitor = new TestInstrumentVisitor();
			doc.accept(instVisitor);
			
			String string  = instfile.getName();
			String[] strings = string.split("\\.");
			fileName = strings[0];	
			
			String txtpath = fileName + "检测结果文件.txt";
			initInfoMap();
			WriteTxtFile(txtpath, instVisitor, doc);
			infoWriteWord(fileName);

		} catch (DocumentException e) {
			System.out.println(e.getMessage());
		}
	}

	private void WriteTxtFile(String txtpath, TestInstrumentVisitor instVisitor, Document doc) {
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
			WriteTxtAndHTMLOnly("检测完成，结果如下:");
			
			
			WriteTestItem1(testItem.get(0));

			for (Iterator<ConnectorClass> iterator = instVisitor.getConnectorList().iterator(); iterator.hasNext();) {
				ConnectorClass tempCon = iterator.next();
				// errorInfo+="节点c:ConnectorPin的connectorID属性为"+connectorId+"的数目为"+connectorNum.get(num++)+"\n";
				//errorInfo += "Connector " + tempCon.getConID() + "上的针脚数目=";
				WriteNormalInfoWithoutEnter(testItem.get(0), "Connector " + tempCon.getConID() + "上的针脚数目=");
				WriteBoldInfo(testItem.get(0), tempCon.getcPins().size());
				//addErrorItem(testItem[0],
				//		"Connector " + tempCon.getConID() + "上的针脚数目=" + tempCon.getcPins().size());
				// fos.write(tempstr.getBytes());
			}

			WriteTestItem1(testItem.get(1));
			//errorInfo += "Port的个数=";
			WriteNormalInfoWithoutEnter(testItem.get(1), "Port的个数=");
			WriteBoldInfo(testItem.get(1), instVisitor.getPortList().size());
			//addErrorItem(testItem[1],"Port的个数=" +  uvisitor.getPortList().size());
			for (Iterator<ConnectorClass> iterator = instVisitor.getConnectorList().iterator(); iterator.hasNext();) {
				String conID = iterator.next().getConID();
				int tempNum = 0;
				// errorInfo+="节点c:ConnectorPin的connectorID属性为"+connectorId+"的数目为"+connectorNum.get(num++)+"\n";
				for (Iterator<PortClass> iterator2 = instVisitor.getPortList().iterator(); iterator2.hasNext();) {
					List<ConnectorPinClass> tempCPins = iterator2.next().getConnectorPins();
					for (Iterator<ConnectorPinClass> iterator3 = tempCPins.iterator(); iterator3.hasNext();) {
						ConnectorPinClass connectorPinClass = (ConnectorPinClass) iterator3.next();
						if (connectorPinClass.getConnectorID().equals(conID)) {

							tempNum++;
						}
					}
				}

				//errorInfo += "Port中定义的属于connector " + conID + "上的针脚数目=";
				WriteNormalInfoWithoutEnter(testItem.get(1), "Port中定义的属于connector " + conID + "上的针脚数目=");
				WriteBoldInfo(testItem.get(1), tempNum++);
				//addErrorItem(testItem[1],"Port中定义的属于connector " + conID + "上的针脚数目=" + tempNum);
				// fos.write(tempstr.getBytes());
			}
			
			
			WriteTestItem1(testItem.get(2));
			CompareTwoList(instVisitor.getConnectorPins(), instVisitor.getConnectorPins1(), errorPin, errorPin1);
			if ((!errorPin.isEmpty()) || (!errorPin1.isEmpty())) {
				// errorInfo+="不匹配节点的XPath路径为：\n";
				List<String> pathList = instVisitor.getcPinpath();
				List<String> pathList1 = instVisitor.getcPinpath1();
				if (!errorPin.isEmpty()) {
					WriteNormalInfo(testItem.get(2), "Connector上未被Port采用的针脚的XPath如下:");
					//addErrorItem(testItem[2],"Connector上未被Port采用的针脚的XPath如下:");
					// fos.write(tempstr.getBytes());

					for (Iterator<String> iterator = errorPin.iterator(); iterator.hasNext();) {
						String tempID = iterator.next();
						WriteErrorInfo(testItem.get(2), pathList.get(instVisitor.getConnectorPins().indexOf(tempID)).toString());
						//addErrorItem(testItem[2],pathList.get(uvisitor.getConnectorPins().indexOf(tempID)).toString());
						// errorInfo+=pathList.get(list.indexOf(tempID))+"\n";
						// fos.write(tempstr.getBytes());
					}
				}
				if (!errorPin1.isEmpty()) {
					WriteNormalInfo(testItem.get(2), "Port中定义的不属于connector中的针脚的XPath如下:");
					//addErrorItem(testItem[2],"Port中定义的不属于connector中的针脚的XPath如下:");
					for (Iterator<String> iterator = errorPin1.iterator(); iterator.hasNext();) {
						String tempID = iterator.next();
						WriteErrorInfo(testItem.get(2), pathList1.get(instVisitor.getConnectorPins1().indexOf(tempID)).toString());
						//addErrorItem(testItem[2],pathList1.get(uvisitor.getConnectorPins1().indexOf(tempID)).toString());
						// errorInfo+=pathList1.get(list1.indexOf(tempID))+"\n";
						// fos.write(tempstr.getBytes());
					}
				}

			} else {
				WriteNormalInfo(testItem.get(2), "节点完全匹配！");
				//addErrorItem(testItem[2],"节点完全匹配！");
				// errorInfo+="节点完全匹配！\n";
			}
			
			
			WriteTestItem1(testItem.get(3));
			// errorInfo+="待检测的XPath路径个数为：";
			WriteNormalInfoWithoutEnter(testItem.get(3), "待检测的XPath路径个数为：");
			//addErrorItemWithoutEnter(testItem[0], "待检测的XPath路径个数为：");

			WriteBoldInfo(testItem.get(3), instVisitor.getInstXPath().size() + "");
			//addErrorItem(testItem[0], instVisitor.getInstXPath().size() + "");

			for (Iterator<String> iterator = instVisitor.getInstXPath().iterator(); iterator.hasNext();) {
				String tempStr = iterator.next();
				if (matchXPath(tempStr)) {
					int index = tempStr.indexOf("/[@");
					if (index != -1) {
						tempStr = tempStr.replace("/[", "[");
						System.out.println(tempStr);
					}
					standardXPath.add(tempStr);
				} else {
					unstandardXPath.add(tempStr);
					// WriteErrorInfo(instxpath1+" is not a standard XPath!");
				}

			}

			// errorInfo+="标准的XPath路径个数为：";
			WriteNormalInfoWithoutEnter(testItem.get(3), "标准的XPath路径个数为：");
			//addErrorItemWithoutEnter(testItem[0], "标准的XPath路径个数为：");

			WriteBoldInfo(testItem.get(3), standardXPath.size() + "");
			//addErrorItem(testItem[0], standardXPath.size() + "");

			// errorInfo+="不标准的XPath路径个数为：";
			WriteNormalInfoWithoutEnter(testItem.get(3), "不标准的XPath路径个数为：");
			//addErrorItemWithoutEnter(testItem[0], "不标准的XPath路径个数为：");

			WriteBoldInfo(testItem.get(3), unstandardXPath.size() + "");
			//addErrorItem(testItem[0], unstandardXPath.size() + "");

			if (unstandardXPath.size() > 0) {
				WriteNormalInfo(testItem.get(3), "不标准的XPath路径为：");
				//addErrorItem(testItem[0], "不标准的XPath路径为：");
				for (Iterator<String> iterator = unstandardXPath.iterator(); iterator.hasNext();) {
					WriteErrorInfo(testItem.get(3), iterator.next());
					//addErrorItem(testItem[0], iterator.next());
				}
			}

			WriteTestItem1(testItem.get(4));
			for (Iterator<String> iterator = standardXPath.iterator(); iterator.hasNext();) {
				String tempStr = (String) iterator.next();
				List<Element> list = null;
				try {
					list = doc.selectNodes(tempStr);

				} catch (Exception e) {
					// TODO: handle exception
					// WriteErrorInfo(instxpath2+" is not EXIST!");
					unExistXpath.add(tempStr);
				}
				if (list != null) {
					if (list.size() == 0) {
						unExistXpath.add(tempStr);
					}
				}
			}

			// errorInfo+="标准的XPath路径中可达路径的个数为：";
			WriteNormalInfoWithoutEnter(testItem.get(4), "标准的XPath路径中可达路径的个数为：");
			//addErrorItemWithoutEnter(testItem[1], "标准的XPath路径中可达路径的个数为：");

			WriteBoldInfo(testItem.get(4), (standardXPath.size() - unExistXpath.size()) + "");
			//addErrorItem(testItem[0], (standardXPath.size() - unExistXpath.size()) + "");

			// errorInfo+="标准的XPath路径中不可达路径的个数为：";
			WriteNormalInfoWithoutEnter(testItem.get(4), "标准的XPath路径中不可达路径的个数为：");
			//addErrorItemWithoutEnter(testItem[1], "标准的XPath路径中不可达路径的个数为：");

			WriteBoldInfo(testItem.get(4), unExistXpath.size() + "");
			//addErrorItem(testItem[0], unExistXpath.size() + "");

			if (unExistXpath.size() > 0) {
				WriteNormalInfo(testItem.get(4), "标准的XPath路径中不可达路径如下所示：");
				//addErrorItem(testItem[0], "标准的XPath路径中不可达路径如下所示：");
				Iterator<String> errorIter2 = unExistXpath.iterator();
				while (errorIter2.hasNext()) {
					WriteErrorInfo(testItem.get(4), errorIter2.next());
					//addErrorItem(testItem[0], errorIter2.next());
				}
			}
			//WriteRedInfo("-----------检测通过-----------","-----------检测通过-----------");
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

	/*
	 * private void WriteErrorInfo(String string) { // TODO 自动生成的方法存根
	 * errorInfo+=("<font color='red'>" + string + "</font><br>"); }
	 */

	/*private boolean MatchXpath(String xpath) {
		// TODO 自动生成的方法存根
		Pattern pattern = Pattern.compile(regEX);
		Matcher matcher = pattern.matcher(xpath);
		return matcher.matches();
	}*/

	/*
	 * private void WriteBoldInfo(String string) { // TODO 自动生成的方法存根
	 * errorInfo+="<b><i>"+string+"</i></b><br>"; }
	 * 
	 * private void WriteNormalInfo(String string) { // TODO 自动生成的方法存根
	 * errorInfo+=string+"<br>"; }
	 */
}
