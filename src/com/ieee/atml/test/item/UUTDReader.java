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
	// "Port��Connector��ƥ���Լ��" };

	List<String> errorPin = new ArrayList<String>();
	List<String> errorPin1 = new ArrayList<String>();
	// String errorInfo;

	public String getInfoHTML() {
		return infoHTML;
	}

	public UUTDReader() {
		// TODO �Զ����ɵĹ��캯�����
		this.infoHTML = "";
		testItem.add("UUT�ӿ���Connector����");
		testItem.add("UUT�ӿ���Port����");
		testItem.add("Port��Connector�ܽ�һ���Լ��");
		testItem.add("Port����������");
	}

	// ����������������dom����
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

			String txtpath = fileName + "������ļ�.txt";
			initInfoMap();
			Write(txtpath, uvisitor);
			infoWriteWord(fileName);
			//WriteRedInfo("-----------���ͨ��-----------","-----------���ͨ��-----------");
			// System.out.println("Connectionpins
			// num="+uvisitor.getConpinNum()+"\nUUID="+uvisitor.getUuid()+"\nSize:"+list.size()+"
			// "+list1.size());

		} catch (DocumentException e) {
			System.out.println(e.getMessage());
		}

	}

	private void Write(String txtpath, UUTDVisitor uvisitor) {
		// TODO �Զ����ɵķ������
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
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(uutdtxt);
		} catch (FileNotFoundException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		try {
			// String tempstr="UUID="+uvisitor.getUuid()+"\n";
			WriteTxtAndHTMLOnly("�����ɣ�������£�");
			// errorInfo+="�����ɣ�������£�\n";

			// errorInfo += "UUID=";
			WriteTxtAndHTMLOnly("UUID=" + uvisitor.getUuid().toString());
			// WriteBoldInfo(uvisitor.getUuid().toString());

			// fos.write(tempstr.getBytes());
			WriteTestItem1(testItem.get(0));

			for (Iterator<ConnectorClass> iterator = uvisitor.getConnectorList().iterator(); iterator.hasNext();) {
				ConnectorClass tempCon = iterator.next();
				// errorInfo+="�ڵ�c:ConnectorPin��connectorID����Ϊ"+connectorId+"����ĿΪ"+connectorNum.get(num++)+"\n";
				// errorInfo += "Connector " + tempCon.getConID() + "�ϵ������Ŀ=";
				WriteNormalInfoWithoutEnter(testItem.get(0), "Connector " + tempCon.getConID() + "�ϵ������Ŀ=");
				WriteBoldInfo(testItem.get(0), tempCon.getcPins().size());
				// addErrorItem(testItem[0],
				// "Connector " + tempCon.getConID() + "�ϵ������Ŀ=" +
				// tempCon.getcPins().size());
				// fos.write(tempstr.getBytes());
			}

			WriteTestItem1(testItem.get(1));
			// errorInfo += "Port�ĸ���=";
			WriteNormalInfoWithoutEnter(testItem.get(1), "Port�ĸ���=");
			WriteBoldInfo(testItem.get(1), uvisitor.getPortList().size());
			// addErrorItem(testItem[1],"Port�ĸ���=" +
			// uvisitor.getPortList().size());
			for (Iterator<ConnectorClass> iterator = uvisitor.getConnectorList().iterator(); iterator.hasNext();) {
				String conID = iterator.next().getConID();
				int tempNum = 0;
				// errorInfo+="�ڵ�c:ConnectorPin��connectorID����Ϊ"+connectorId+"����ĿΪ"+connectorNum.get(num++)+"\n";
				for (Iterator<PortClass> iterator2 = uvisitor.getPortList().iterator(); iterator2.hasNext();) {
					List<ConnectorPinClass> tempCPins = iterator2.next().getConnectorPins();
					for (Iterator<ConnectorPinClass> iterator3 = tempCPins.iterator(); iterator3.hasNext();) {
						ConnectorPinClass connectorPinClass = (ConnectorPinClass) iterator3.next();
						if (connectorPinClass.getConnectorID().equals(conID)) {

							tempNum++;
						}
					}
				}

				// errorInfo += "Port�ж��������connector " + conID + "�ϵ������Ŀ=";
				WriteNormalInfoWithoutEnter(testItem.get(1), "Port�ж��������connector " + conID + "�ϵ������Ŀ=");
				WriteBoldInfo(testItem.get(1), tempNum++);
				// addErrorItem(testItem[1],"Port�ж��������connector " + conID +
				// "�ϵ������Ŀ=" + tempNum);
				// fos.write(tempstr.getBytes());
			}

			CompareTwoList(uvisitor.getConnectorPins(), uvisitor.getConnectorPins1(), errorPin, errorPin1);

			WriteTestItem1(testItem.get(2));
			if ((!errorPin.isEmpty()) || (!errorPin1.isEmpty())) {
				// errorInfo+="��ƥ��ڵ��XPath·��Ϊ��\n";
				List<String> pathList = uvisitor.getcPinpath();
				List<String> pathList1 = uvisitor.getcPinpath1();
				if (!errorPin.isEmpty()) {
					WriteNormalInfo(testItem.get(2), "Connector��δ��Port���õ���ŵ�XPath����:");
					// addErrorItem(testItem[2],"Connector��δ��Port���õ���ŵ�XPath����:");
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
					WriteNormalInfo(testItem.get(2), "Port�ж���Ĳ�����connector�е���ŵ�XPath����:");
					// addErrorItem(testItem[2],"Port�ж���Ĳ�����connector�е���ŵ�XPath����:");
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
				WriteNormalInfo(testItem.get(2), "�ܽŶ���һ��!");
				// addErrorItem(testItem[2],"�ڵ���ȫƥ�䣡");
				// errorInfo+="�ڵ���ȫƥ�䣡\n";
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
								// error.add("Ӧ��" + pin.getSignalType());
							}
							if (tempCon.getSignalFlow().equals(pin.getSignalFlow())) {
								error.add("0");
							} else {
								if (tempCon.getSignalType().equals(PinType.ground)) {
									error.add("0");
								} else {
									error.add("Ӧ��" + pin.getSignalFlow());
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
								// error.add("Ӧ��" + pin.getSignalType());
							}
							if (tempCon.getSignalFlow().equals(pin.getSignalFlow())) {
								error.add("0");
							} else {
								if (tempCon.getSignalType().equals(PinType.ground)) {
									error.add("0");
								} else {
									error.add("Ӧ��" + pin.getSignalFlow());
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
				WriteNormalInfoWithoutEnter(testItem.get(3), "�����������������:");
				WriteBoldInfo(testItem.get(3), pinError.size() + "��");
				for (Iterator iterator = pinError.iterator(); iterator.hasNext();) {
					ArrayList<String> error = (ArrayList<String>) iterator.next();
					WriteErrorInfo(testItem.get(3),
							"connectorID:" + error.get(0) + " " + "pinID:" + error.get(1) + "��");
					if (error.get(2).equals("0") && !error.get(3).equals("0")) {
						WriteErrorInfo(testItem.get(3), "direction����" + error.get(3));
					} else if (!error.get(2).equals("0") && error.get(3).equals("0")) {
						WriteErrorInfo(testItem.get(3), "type����" + error.get(2));
					} else if (!error.get(2).equals("0") && !error.get(3).equals("0")) {
						WriteErrorInfo(testItem.get(3), "type����" + error.get(2) + "��direction����" + error.get(3));
					}
				}
			} else {
				WriteNormalInfo(testItem.get(3), "����ͨ����");
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
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		try {
			fos.close();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}

	private boolean IsInCPins(ConnectorClass connector, String pinID) {
		// TODO �Զ����ɵķ������
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
		// TODO �Զ����ɵķ������

	}

	/*
	 * private void WriteBoldInfo(int size) { // TODO �Զ����ɵķ������ errorInfo +=
	 * "<b><i>" + size + "</i></b><br>"; }
	 */

	private List GetSameConnectorPinNum(Document doc, List connectorIDs) {
		// TODO �Զ����ɵķ������
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
	 * connectorNum) { // TODO �Զ����ɵķ������ txtpath = txtpath + "UUTD������ļ�.txt";
	 * errorHTML = ""; File uutdtxt = new File(txtpath); if (uutdtxt.exists()) {
	 * uutdtxt.delete(); } else { try { uutdtxt.createNewFile(); } catch
	 * (IOException e) { // TODO �Զ����ɵ� catch �� e.printStackTrace(); } }
	 * FileOutputStream fos = null; try { fos = new FileOutputStream(uutdtxt); }
	 * catch (FileNotFoundException e) { // TODO �Զ����ɵ� catch ��
	 * e.printStackTrace(); } try { // String
	 * tempstr="UUID="+uvisitor.getUuid()+"\n"; WriteNormalInfo("�����ɣ�������£�");
	 * // errorInfo+="�����ɣ�������£�\n";
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
	 * errorInfo+="�ڵ�c:ConnectorPin��connectorID����Ϊ"+connectorId+"����ĿΪ"+
	 * connectorNum.get(num++)+"\n"; //errorInfo += "Connector " + connectorId +
	 * "�ϵĹܽ���ĿΪ"; WriteNormalInfoWithoutEnter("Connector " + connectorId +
	 * "�ϵĹܽ���ĿΪ"); WriteBoldInfo(connectorNum.get(num++).toString()); //
	 * fos.write(tempstr.getBytes()); }
	 * 
	 * if ((!errorPin1.isEmpty()) && (!errorPin2.isEmpty())) { //
	 * errorInfo+="��ƥ��ڵ��XPath·��Ϊ��\n"; WriteNormalInfo("��ƥ��ڵ��XPath·��Ϊ��"); //
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
	 * WriteNormalInfo("�ڵ���ȫƥ�䣡"); // errorInfo+="�ڵ���ȫƥ�䣡\n"; }
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
	 * } catch (IOException e) { // TODO �Զ����ɵ� catch �� e.printStackTrace(); }
	 * try { fos.close(); } catch (IOException e) { // TODO �Զ����ɵ� catch ��
	 * e.printStackTrace(); } }
	 */

	/*
	 * private void WriteErrorInfo(String string) { // TODO �Զ����ɵķ������ errorInfo
	 * += ("<font color='red'>" + string + "</font><br>"); }
	 * 
	 * private void WriteBoldInfo(String string) { // TODO �Զ����ɵķ������ errorInfo
	 * += "<b><i>" + string + "</i></b><br>"; }
	 * 
	 * private void WriteNormalInfo(String string) { // TODO �Զ����ɵķ������ errorInfo
	 * += string + "<br>"; }
	 */

	/*
	 * private void CompareTwoList(List list1, List list2) { // TODO �Զ����ɵķ������
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
