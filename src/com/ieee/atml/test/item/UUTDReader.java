package com.ieee.atml.test.item;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ieee.atml.info.InfoWrite;
import com.ieee.atml.test.assist.CompareConnectorPins;
import com.ieee.atml.test.assist.UUTDVisitor;
import com.ieee.atml.test.resource.ConnectorClass;
import com.ieee.atml.test.resource.ConnectorPinClass;
import com.ieee.atml.test.resource.PinClass;
import com.ieee.atml.test.resource.PinDenfinitionMap;
import com.ieee.atml.test.resource.PinType;
import com.ieee.atml.test.resource.PortClass;
import com.ieee.atml.util.StringUtil;
import com.microsoft.schemas.office.office.ConnectortypeAttribute;

public class UUTDReader extends InfoWrite implements CompareConnectorPins {

	// private static final String[] testItem = { "Connector", "Port",
	// "Port与Connector的匹配性检测" };

	List<String> errorPin = new ArrayList<String>();
	List<String> errorPin1 = new ArrayList<String>();
	// String errorInfo;

	public String getInfoHTML() {
		return infoHTML;
	}

	public UUTDReader() {
		// TODO 自动生成的构造函数存根
		this.infoHTML = "";
		testItem.add("UUT接口中Connector描述");
		testItem.add("UUT接口中Port描述");
		testItem.add("Port与Connector管脚一致性检查");
		testItem.add("Port数据流向检查");
	}

	// 虚拟主函数，创建dom树等
	public void VirtualMain(String xmlpath) {
		SAXReader saxReader = new SAXReader();
		File uutdfile = new File(xmlpath);
		try {
			Document doc = saxReader.read(uutdfile);
			UUTDVisitor uvisitor = new UUTDVisitor();
			doc.accept(uvisitor);

			/*
			 * List list=uvisitor.getConnectorPins(); List
			 * list1=uvisitor.getConnectorPins1();
			 * 
			 * List connectorIDs=uvisitor.getConnectorIDs(); List
			 * connectorNum=GetSameConnectorPinNum(doc,connectorIDs);
			 * if(!list.isEmpty()) { CompareTwoList(list,list1); }
			 * WriteTxt("",uvisitor.getConnectorPins(),uvisitor.
			 * getConnectorPins1(),errorPin,errorPin1,uvisitor,connectorNum);
			 */
			String string = uutdfile.getName();
			String[] strings = string.split("\\.");
			fileName = strings[0];

			String txtpath = fileName + "检测结果文件.txt";
			initInfoMap();
			Write(txtpath, uvisitor);
			infoWriteWord(fileName);
			//WriteRedInfo("-----------检测通过-----------","-----------检测通过-----------");
			// System.out.println("Connectionpins
			// num="+uvisitor.getConpinNum()+"\nUUID="+uvisitor.getUuid()+"\nSize:"+list.size()+"
			// "+list1.size());

		} catch (DocumentException e) {
			System.out.println(e.getMessage());
		}

	}

	private void Write(String txtpath, UUTDVisitor uvisitor) {
		// TODO 自动生成的方法存根
		List<String> portPins = new ArrayList();
		List<String> connectorPins = new ArrayList();

		infoHTML = "";
		File uutdtxt = new File(txtpath);
		if (uutdtxt.exists()) {
			uutdtxt.delete();
		} else {
			try {
				uutdtxt.createNewFile();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(uutdtxt);
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		try {
			// String tempstr="UUID="+uvisitor.getUuid()+"\n";
			WriteTxtAndHTMLOnly("检测完成，结果如下：");
			// errorInfo+="检测完成，结果如下：\n";

			// errorInfo += "UUID=";
			WriteTxtAndHTMLOnly("UUID=" + uvisitor.getUuid().toString());
			// WriteBoldInfo(uvisitor.getUuid().toString());

			// fos.write(tempstr.getBytes());
			WriteTestItem1(testItem.get(0));

			for (Iterator<ConnectorClass> iterator = uvisitor.getConnectorList().iterator(); iterator.hasNext();) {
				ConnectorClass tempCon = iterator.next();
				// errorInfo+="节点c:ConnectorPin的connectorID属性为"+connectorId+"的数目为"+connectorNum.get(num++)+"\n";
				// errorInfo += "Connector " + tempCon.getConID() + "上的针脚数目=";
				WriteNormalInfoWithoutEnter(testItem.get(0), "Connector " + tempCon.getConID() + "上的针脚数目=");
				WriteBoldInfo(testItem.get(0), tempCon.getcPins().size());
				// addErrorItem(testItem[0],
				// "Connector " + tempCon.getConID() + "上的针脚数目=" +
				// tempCon.getcPins().size());
				// fos.write(tempstr.getBytes());
			}

			WriteTestItem1(testItem.get(1));
			// errorInfo += "Port的个数=";
			WriteNormalInfoWithoutEnter(testItem.get(1), "Port的个数=");
			WriteBoldInfo(testItem.get(1), uvisitor.getPortList().size());
			// addErrorItem(testItem[1],"Port的个数=" +
			// uvisitor.getPortList().size());
			for (Iterator<ConnectorClass> iterator = uvisitor.getConnectorList().iterator(); iterator.hasNext();) {
				String conID = iterator.next().getConID();
				int tempNum = 0;
				// errorInfo+="节点c:ConnectorPin的connectorID属性为"+connectorId+"的数目为"+connectorNum.get(num++)+"\n";
				for (Iterator<PortClass> iterator2 = uvisitor.getPortList().iterator(); iterator2.hasNext();) {
					List<ConnectorPinClass> tempCPins = iterator2.next().getConnectorPins();
					for (Iterator<ConnectorPinClass> iterator3 = tempCPins.iterator(); iterator3.hasNext();) {
						ConnectorPinClass connectorPinClass = (ConnectorPinClass) iterator3.next();
						if (connectorPinClass.getConnectorID().equals(conID)) {

							tempNum++;
						}
					}
				}

				// errorInfo += "Port中定义的属于connector " + conID + "上的针脚数目=";
				WriteNormalInfoWithoutEnter(testItem.get(1), "Port中定义的属于connector " + conID + "上的针脚数目=");
				WriteBoldInfo(testItem.get(1), tempNum++);
				// addErrorItem(testItem[1],"Port中定义的属于connector " + conID +
				// "上的针脚数目=" + tempNum);
				// fos.write(tempstr.getBytes());
			}

			CompareTwoList(uvisitor.getConnectorPins(), uvisitor.getConnectorPins1(), errorPin, errorPin1);

			WriteTestItem1(testItem.get(2));
			if ((!errorPin.isEmpty()) || (!errorPin1.isEmpty())) {
				// errorInfo+="不匹配节点的XPath路径为：\n";
				List<String> pathList = uvisitor.getcPinpath();
				List<String> pathList1 = uvisitor.getcPinpath1();
				if (!errorPin.isEmpty()) {
					WriteNormalInfo(testItem.get(2), "Connector上未被Port采用的针脚的XPath如下:");
					// addErrorItem(testItem[2],"Connector上未被Port采用的针脚的XPath如下:");
					// fos.write(tempstr.getBytes());

					for (Iterator<String> iterator = errorPin.iterator(); iterator.hasNext();) {
						String tempID = iterator.next();
						WriteErrorInfo(testItem.get(2),
								pathList.get(uvisitor.getConnectorPins().indexOf(tempID)).toString());
						// addErrorItem(testItem[2],pathList.get(uvisitor.getConnectorPins().indexOf(tempID)).toString());
						// errorInfo+=pathList.get(list.indexOf(tempID))+"\n";
						// fos.write(tempstr.getBytes());
					}
				}
				if (!errorPin1.isEmpty()) {
					WriteNormalInfo(testItem.get(2), "Port中定义的不属于connector中的针脚的XPath如下:");
					// addErrorItem(testItem[2],"Port中定义的不属于connector中的针脚的XPath如下:");
					for (Iterator<String> iterator = errorPin1.iterator(); iterator.hasNext();) {
						String tempID = iterator.next();
						WriteErrorInfo(testItem.get(2),
								pathList1.get(uvisitor.getConnectorPins1().indexOf(tempID)).toString());
						// addErrorItem(testItem[2],pathList1.get(uvisitor.getConnectorPins1().indexOf(tempID)).toString());
						// errorInfo+=pathList1.get(list1.indexOf(tempID))+"\n";
						// fos.write(tempstr.getBytes());
					}
				}

			} else {
				WriteNormalInfo(testItem.get(2), "管脚定义一致!");
				// addErrorItem(testItem[2],"节点完全匹配！");
				// errorInfo+="节点完全匹配！\n";
			}

			WriteTestItem1(testItem.get(3));
			PinDenfinitionMap denfinitionMap = new PinDenfinitionMap();
			ArrayList<ArrayList<String>> pinError = new ArrayList<>();
			for (Iterator iterator = uvisitor.getPortList().iterator(); iterator.hasNext();) {
				PortClass tempCon = (PortClass) iterator.next();
				String connectorID = tempCon.getConnectorPins().get(0).getConnectorID();
				String pinID = tempCon.getConnectorPins().get(0).getPinID();
				if (!(connectorID.equals("X1") || connectorID.equals("X2"))) {
					continue;
				}
				Integer id = Integer.parseInt(pinID) - 1;
				if (connectorID.equals("X1")) {
					if (id >= 80) {

					} else {
						PinClass pin = PinDenfinitionMap.pinX1List.get(id);
						if (/*
							 * !tempCon.getSignalType().equals(pin.getSignalType
							 * ()) ||
							 */!tempCon.getSignalFlow().equals(pin.getSignalFlow())) {
							ArrayList<String> error = new ArrayList<>();
							error.add("X1");
							error.add(id + 1 + "");
							if (tempCon.getSignalType().equals(pin.getSignalType())) {
								error.add("0");
							} else {
								error.add("0");
								// error.add("应是" + pin.getSignalType());
							}
							if (tempCon.getSignalFlow().equals(pin.getSignalFlow())) {
								error.add("0");
							} else {
								if (tempCon.getSignalType().equals(PinType.ground)) {
									error.add("0");
								} else {
									error.add("应是" + pin.getSignalFlow());
								}
							}
							if (error.get(2).equals("0") && error.get(3).equals("0")) {

							} else {
								pinError.add(error);
							}
						}
					}

				} else if (connectorID.equals("X2")) {
					if (id >= 6) {

					} else {
						PinClass pin = PinDenfinitionMap.pinX2List.get(id);
						if (/*
							 * !tempCon.getSignalType().equals(pin.getSignalType
							 * ()) ||
							 */!tempCon.getSignalFlow().equals(pin.getSignalFlow())) {
							ArrayList<String> error = new ArrayList<>();
							error.add("X2");
							error.add(id + 1 + "");
							if (tempCon.getSignalType().equals(pin.getSignalType())) {
								error.add("0");
							} else {
								error.add("0");
								// error.add("应是" + pin.getSignalType());
							}
							if (tempCon.getSignalFlow().equals(pin.getSignalFlow())) {
								error.add("0");
							} else {
								if (tempCon.getSignalType().equals(PinType.ground)) {
									error.add("0");
								} else {
									error.add("应是" + pin.getSignalFlow());
								}
							}
							if (error.get(2).equals("0") && error.get(3).equals("0")) {

							} else {
								pinError.add(error);
							}
						}
					}
				}
			}
			if (pinError.size() > 0) {
				WriteNormalInfoWithoutEnter(testItem.get(3), "共存在数据流向错误:");
				WriteBoldInfo(testItem.get(3), pinError.size() + "个");
				for (Iterator iterator = pinError.iterator(); iterator.hasNext();) {
					ArrayList<String> error = (ArrayList<String>) iterator.next();
					WriteErrorInfo(testItem.get(3),
							"connectorID:" + error.get(0) + " " + "pinID:" + error.get(1) + "，");
					if (error.get(2).equals("0") && !error.get(3).equals("0")) {
						WriteErrorInfo(testItem.get(3), "direction属性" + error.get(3));
					} else if (!error.get(2).equals("0") && error.get(3).equals("0")) {
						WriteErrorInfo(testItem.get(3), "type属性" + error.get(2));
					} else if (!error.get(2).equals("0") && !error.get(3).equals("0")) {
						WriteErrorInfo(testItem.get(3), "type属性" + error.get(2) + "，direction属性" + error.get(3));
					}
				}
			} else {
				WriteNormalInfo(testItem.get(3), "测试通过！");
			}

			fos.write(infoTxt.getBytes());
			/*
			 * tempstr="ConnectorID:PinID\n"; for (Iterator<String> iterator =
			 * list.iterator(); iterator.hasNext();) {
			 * tempstr=tempstr+iterator.next()+"\n"; }
			 * fos.write(tempstr.getBytes());
			 * 
			 * tempstr="Error Connnectorpin connectorID:pinID\n"; for
			 * (Iterator<String> iterator = errorPin2.iterator();
			 * iterator.hasNext();) { tempstr=tempstr+iterator.next()+"\n"; }
			 * fos.write(tempstr.getBytes());
			 */
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

	private boolean IsInCPins(ConnectorClass connector, String pinID) {
		// TODO 自动生成的方法存根
		boolean b = false;
		for (Iterator<PinClass> iterator = connector.getcPins().iterator(); iterator.hasNext();) {

			if (pinID.equals(iterator.next().getPinID())) {
				b = true;
				break;
			}
		}
		return b;
	}

	private void GenerateUnmatchPin(List<ConnectorClass> connectorList, List<PortClass> portList) {
		// TODO 自动生成的方法存根

	}

	/*
	 * private void WriteBoldInfo(int size) { // TODO 自动生成的方法存根 errorInfo +=
	 * "<b><i>" + size + "</i></b><br>"; }
	 */

	private List GetSameConnectorPinNum(Document doc, List connectorIDs) {
		// TODO 自动生成的方法存根
		List numList = new ArrayList();

		Element rootElt = doc.getRootElement();

		for (Iterator<String> iterator = connectorIDs.iterator(); iterator.hasNext();) {
			String tempstr = iterator.next();
			List templist = rootElt.selectNodes("//c:ConnectorPins/c:ConnectorPin[@connectorID='" + tempstr + "']");
			numList.add(templist.size());
		}
		return numList;
	}

	/*
	 * private void WriteTxt(String txtpath, List list, List list1, List<String>
	 * errorPin1, List<String> errorPin2, UUTDVisitor uvisitor, List
	 * connectorNum) { // TODO 自动生成的方法存根 txtpath = txtpath + "UUTD检测结果文件.txt";
	 * errorHTML = ""; File uutdtxt = new File(txtpath); if (uutdtxt.exists()) {
	 * uutdtxt.delete(); } else { try { uutdtxt.createNewFile(); } catch
	 * (IOException e) { // TODO 自动生成的 catch 块 e.printStackTrace(); } }
	 * FileOutputStream fos = null; try { fos = new FileOutputStream(uutdtxt); }
	 * catch (FileNotFoundException e) { // TODO 自动生成的 catch 块
	 * e.printStackTrace(); } try { // String
	 * tempstr="UUID="+uvisitor.getUuid()+"\n"; WriteNormalInfo("检测完成，结果如下：");
	 * // errorInfo+="检测完成，结果如下：\n";
	 * 
	 * //errorInfo += "UUID="; WriteNormalInfoWithoutEnter("UUID=");
	 * WriteBoldInfo(uvisitor.getUuid().toString());
	 * 
	 * // fos.write(tempstr.getBytes());
	 * WriteNormalInfo("**********Connector**********");
	 * 
	 * int num = 0; for (Iterator<String> iterator =
	 * uvisitor.getConnectorIDs().iterator(); iterator.hasNext();) { String
	 * connectorId = iterator.next(); //
	 * errorInfo+="节点c:ConnectorPin的connectorID属性为"+connectorId+"的数目为"+
	 * connectorNum.get(num++)+"\n"; //errorInfo += "Connector " + connectorId +
	 * "上的管脚数目为"; WriteNormalInfoWithoutEnter("Connector " + connectorId +
	 * "上的管脚数目为"); WriteBoldInfo(connectorNum.get(num++).toString()); //
	 * fos.write(tempstr.getBytes()); }
	 * 
	 * if ((!errorPin1.isEmpty()) && (!errorPin2.isEmpty())) { //
	 * errorInfo+="不匹配节点的XPath路径为：\n"; WriteNormalInfo("不匹配节点的XPath路径为："); //
	 * fos.write(tempstr.getBytes());
	 * 
	 * List<String> pathList = uvisitor.getcPinpath(); List<String> pathList1 =
	 * uvisitor.getcPinpath1(); for (Iterator<String> iterator =
	 * errorPin1.iterator(); iterator.hasNext();) { String tempID =
	 * iterator.next();
	 * WriteErrorInfo(pathList.get(list.indexOf(tempID)).toString()); //
	 * errorInfo+=pathList.get(list.indexOf(tempID))+"\n"; //
	 * fos.write(tempstr.getBytes()); } for (Iterator<String> iterator =
	 * errorPin2.iterator(); iterator.hasNext();) { String tempID =
	 * iterator.next();
	 * WriteErrorInfo(pathList1.get(list1.indexOf(tempID)).toString()); //
	 * errorInfo+=pathList1.get(list1.indexOf(tempID))+"\n"; //
	 * fos.write(tempstr.getBytes()); } fos.write(errorTxt.getBytes()); } else {
	 * WriteNormalInfo("节点完全匹配！"); // errorInfo+="节点完全匹配！\n"; }
	 * 
	 * 
	 * tempstr="ConnectorID:PinID\n"; for (Iterator<String> iterator =
	 * list.iterator(); iterator.hasNext();) {
	 * tempstr=tempstr+iterator.next()+"\n"; } fos.write(tempstr.getBytes());
	 * 
	 * tempstr="Error Connnectorpin connectorID:pinID\n"; for (Iterator<String>
	 * iterator = errorPin2.iterator(); iterator.hasNext();) {
	 * tempstr=tempstr+iterator.next()+"\n"; } fos.write(tempstr.getBytes());
	 * 
	 * } catch (IOException e) { // TODO 自动生成的 catch 块 e.printStackTrace(); }
	 * try { fos.close(); } catch (IOException e) { // TODO 自动生成的 catch 块
	 * e.printStackTrace(); } }
	 */

	/*
	 * private void WriteErrorInfo(String string) { // TODO 自动生成的方法存根 errorInfo
	 * += ("<font color='red'>" + string + "</font><br>"); }
	 * 
	 * private void WriteBoldInfo(String string) { // TODO 自动生成的方法存根 errorInfo
	 * += "<b><i>" + string + "</i></b><br>"; }
	 * 
	 * private void WriteNormalInfo(String string) { // TODO 自动生成的方法存根 errorInfo
	 * += string + "<br>"; }
	 */

	/*
	 * private void CompareTwoList(List list1, List list2) { // TODO 自动生成的方法存根
	 * int size1 = list1.size(); int size2 = list2.size(); List<String> dellist
	 * = new ArrayList(); List<String> templist1 = new ArrayList(); List<String>
	 * templist2 = new ArrayList();
	 * 
	 * templist1.addAll(list1); templist2.addAll(list2);
	 * 
	 * if (size1 > size2) { for (Iterator<String> iterator =
	 * templist1.iterator(); iterator.hasNext();) { String tempstr =
	 * iterator.next(); if (templist2.contains(tempstr)) { dellist.add(tempstr);
	 * 
	 * } } } else { for (Iterator<String> iterator = templist2.iterator();
	 * iterator.hasNext();) { String tempstr = iterator.next(); if
	 * (templist1.contains(tempstr)) { dellist.add(tempstr);
	 * 
	 * } } }
	 * 
	 * templist1.removeAll(dellist); templist2.removeAll(dellist);
	 * 
	 * errorPin.addAll(templist1); errorPin1.addAll(templist2);
	 * 
	 * }
	 */
}
