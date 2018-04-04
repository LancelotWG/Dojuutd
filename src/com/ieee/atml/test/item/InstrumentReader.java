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
	//private static final String[] testItem = { "Xpath Standard���", "Xpath�ɴ��Լ��" };
	private List<String> standardXPath = new ArrayList();
	private List<String> unstandardXPath = new ArrayList();
	private List<String> unExistXpath = new ArrayList();
	//private static final String regEX = "(?:\\/(?:[a-zA-Z]{0,}:){0,1}[a-zA-Z]{0,}(?:(?:\\/){0,1}\\[@[a-zA-Z]{0,}=(['\"])[\\u4e00-\\u9fa5\\w\\+-]{0,}\\1\\]){0,}){1,}";

	public InstrumentReader(){
		testItem.add("Instrument�ӿ���Connector����");
		testItem.add("Instrument�ӿ���Port����");
		testItem.add("Port��Connector�ܽ�һ���Լ��");
		testItem.add("XPath��ʽ��ȷ�Լ��");
		testItem.add("XPath·��������ȷ��ƥ���Լ��");
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
			
			String txtpath = fileName + "������ļ�.txt";
			initInfoMap();
			WriteTxtFile(txtpath, instVisitor, doc);
			infoWriteWord(fileName);

		} catch (DocumentException e) {
			System.out.println(e.getMessage());
		}
	}

	private void WriteTxtFile(String txtpath, TestInstrumentVisitor instVisitor, Document doc) {
		// TODO �Զ����ɵķ������
		File tstxt = new File(txtpath);
		infoHTML = "";
		if (tstxt.exists()) {
			tstxt.delete();
		} else {
			try {
				tstxt.createNewFile();
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(tstxt);
		} catch (FileNotFoundException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		try {
			// String tempstr="**********Port����ܽ�connectorPin**********\n";
			// fos.write(tempstr.getBytes());
			// errorInfo+="�����ɣ�������£�\n";
			WriteTxtAndHTMLOnly("�����ɣ��������:");
			
			
			WriteTestItem1(testItem.get(0));

			for (Iterator<ConnectorClass> iterator = instVisitor.getConnectorList().iterator(); iterator.hasNext();) {
				ConnectorClass tempCon = iterator.next();
				// errorInfo+="�ڵ�c:ConnectorPin��connectorID����Ϊ"+connectorId+"����ĿΪ"+connectorNum.get(num++)+"\n";
				//errorInfo += "Connector " + tempCon.getConID() + "�ϵ������Ŀ=";
				WriteNormalInfoWithoutEnter(testItem.get(0), "Connector " + tempCon.getConID() + "�ϵ������Ŀ=");
				WriteBoldInfo(testItem.get(0), tempCon.getcPins().size());
				//addErrorItem(testItem[0],
				//		"Connector " + tempCon.getConID() + "�ϵ������Ŀ=" + tempCon.getcPins().size());
				// fos.write(tempstr.getBytes());
			}

			WriteTestItem1(testItem.get(1));
			//errorInfo += "Port�ĸ���=";
			WriteNormalInfoWithoutEnter(testItem.get(1), "Port�ĸ���=");
			WriteBoldInfo(testItem.get(1), instVisitor.getPortList().size());
			//addErrorItem(testItem[1],"Port�ĸ���=" +  uvisitor.getPortList().size());
			for (Iterator<ConnectorClass> iterator = instVisitor.getConnectorList().iterator(); iterator.hasNext();) {
				String conID = iterator.next().getConID();
				int tempNum = 0;
				// errorInfo+="�ڵ�c:ConnectorPin��connectorID����Ϊ"+connectorId+"����ĿΪ"+connectorNum.get(num++)+"\n";
				for (Iterator<PortClass> iterator2 = instVisitor.getPortList().iterator(); iterator2.hasNext();) {
					List<ConnectorPinClass> tempCPins = iterator2.next().getConnectorPins();
					for (Iterator<ConnectorPinClass> iterator3 = tempCPins.iterator(); iterator3.hasNext();) {
						ConnectorPinClass connectorPinClass = (ConnectorPinClass) iterator3.next();
						if (connectorPinClass.getConnectorID().equals(conID)) {

							tempNum++;
						}
					}
				}

				//errorInfo += "Port�ж��������connector " + conID + "�ϵ������Ŀ=";
				WriteNormalInfoWithoutEnter(testItem.get(1), "Port�ж��������connector " + conID + "�ϵ������Ŀ=");
				WriteBoldInfo(testItem.get(1), tempNum++);
				//addErrorItem(testItem[1],"Port�ж��������connector " + conID + "�ϵ������Ŀ=" + tempNum);
				// fos.write(tempstr.getBytes());
			}
			
			
			WriteTestItem1(testItem.get(2));
			CompareTwoList(instVisitor.getConnectorPins(), instVisitor.getConnectorPins1(), errorPin, errorPin1);
			if ((!errorPin.isEmpty()) || (!errorPin1.isEmpty())) {
				// errorInfo+="��ƥ��ڵ��XPath·��Ϊ��\n";
				List<String> pathList = instVisitor.getcPinpath();
				List<String> pathList1 = instVisitor.getcPinpath1();
				if (!errorPin.isEmpty()) {
					WriteNormalInfo(testItem.get(2), "Connector��δ��Port���õ���ŵ�XPath����:");
					//addErrorItem(testItem[2],"Connector��δ��Port���õ���ŵ�XPath����:");
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
					WriteNormalInfo(testItem.get(2), "Port�ж���Ĳ�����connector�е���ŵ�XPath����:");
					//addErrorItem(testItem[2],"Port�ж���Ĳ�����connector�е���ŵ�XPath����:");
					for (Iterator<String> iterator = errorPin1.iterator(); iterator.hasNext();) {
						String tempID = iterator.next();
						WriteErrorInfo(testItem.get(2), pathList1.get(instVisitor.getConnectorPins1().indexOf(tempID)).toString());
						//addErrorItem(testItem[2],pathList1.get(uvisitor.getConnectorPins1().indexOf(tempID)).toString());
						// errorInfo+=pathList1.get(list1.indexOf(tempID))+"\n";
						// fos.write(tempstr.getBytes());
					}
				}

			} else {
				WriteNormalInfo(testItem.get(2), "�ڵ���ȫƥ�䣡");
				//addErrorItem(testItem[2],"�ڵ���ȫƥ�䣡");
				// errorInfo+="�ڵ���ȫƥ�䣡\n";
			}
			
			
			WriteTestItem1(testItem.get(3));
			// errorInfo+="������XPath·������Ϊ��";
			WriteNormalInfoWithoutEnter(testItem.get(3), "������XPath·������Ϊ��");
			//addErrorItemWithoutEnter(testItem[0], "������XPath·������Ϊ��");

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

			// errorInfo+="��׼��XPath·������Ϊ��";
			WriteNormalInfoWithoutEnter(testItem.get(3), "��׼��XPath·������Ϊ��");
			//addErrorItemWithoutEnter(testItem[0], "��׼��XPath·������Ϊ��");

			WriteBoldInfo(testItem.get(3), standardXPath.size() + "");
			//addErrorItem(testItem[0], standardXPath.size() + "");

			// errorInfo+="����׼��XPath·������Ϊ��";
			WriteNormalInfoWithoutEnter(testItem.get(3), "����׼��XPath·������Ϊ��");
			//addErrorItemWithoutEnter(testItem[0], "����׼��XPath·������Ϊ��");

			WriteBoldInfo(testItem.get(3), unstandardXPath.size() + "");
			//addErrorItem(testItem[0], unstandardXPath.size() + "");

			if (unstandardXPath.size() > 0) {
				WriteNormalInfo(testItem.get(3), "����׼��XPath·��Ϊ��");
				//addErrorItem(testItem[0], "����׼��XPath·��Ϊ��");
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

			// errorInfo+="��׼��XPath·���пɴ�·���ĸ���Ϊ��";
			WriteNormalInfoWithoutEnter(testItem.get(4), "��׼��XPath·���пɴ�·���ĸ���Ϊ��");
			//addErrorItemWithoutEnter(testItem[1], "��׼��XPath·���пɴ�·���ĸ���Ϊ��");

			WriteBoldInfo(testItem.get(4), (standardXPath.size() - unExistXpath.size()) + "");
			//addErrorItem(testItem[0], (standardXPath.size() - unExistXpath.size()) + "");

			// errorInfo+="��׼��XPath·���в��ɴ�·���ĸ���Ϊ��";
			WriteNormalInfoWithoutEnter(testItem.get(4), "��׼��XPath·���в��ɴ�·���ĸ���Ϊ��");
			//addErrorItemWithoutEnter(testItem[1], "��׼��XPath·���в��ɴ�·���ĸ���Ϊ��");

			WriteBoldInfo(testItem.get(4), unExistXpath.size() + "");
			//addErrorItem(testItem[0], unExistXpath.size() + "");

			if (unExistXpath.size() > 0) {
				WriteNormalInfo(testItem.get(4), "��׼��XPath·���в��ɴ�·��������ʾ��");
				//addErrorItem(testItem[0], "��׼��XPath·���в��ɴ�·��������ʾ��");
				Iterator<String> errorIter2 = unExistXpath.iterator();
				while (errorIter2.hasNext()) {
					WriteErrorInfo(testItem.get(4), errorIter2.next());
					//addErrorItem(testItem[0], errorIter2.next());
				}
			}
			//WriteRedInfo("-----------���ͨ��-----------","-----------���ͨ��-----------");
			fos.write(infoTxt.getBytes());

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

	/*
	 * private void WriteErrorInfo(String string) { // TODO �Զ����ɵķ������
	 * errorInfo+=("<font color='red'>" + string + "</font><br>"); }
	 */

	/*private boolean MatchXpath(String xpath) {
		// TODO �Զ����ɵķ������
		Pattern pattern = Pattern.compile(regEX);
		Matcher matcher = pattern.matcher(xpath);
		return matcher.matches();
	}*/

	/*
	 * private void WriteBoldInfo(String string) { // TODO �Զ����ɵķ������
	 * errorInfo+="<b><i>"+string+"</i></b><br>"; }
	 * 
	 * private void WriteNormalInfo(String string) { // TODO �Զ����ɵķ������
	 * errorInfo+=string+"<br>"; }
	 */
}
