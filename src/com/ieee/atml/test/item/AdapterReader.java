package com.ieee.atml.test.item;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.ieee.atml.info.InfoWrite;
import com.ieee.atml.test.assist.AdapterVisitor;
import com.ieee.atml.test.assist.CompareConnectorPins;
import com.ieee.atml.test.assist.TestInstrumentVisitor;
import com.ieee.atml.test.resource.ConnectorClass;
import com.ieee.atml.test.resource.ConnectorPinClass;
import com.ieee.atml.test.resource.PinFlow;
import com.ieee.atml.test.resource.PinType;
import com.ieee.atml.test.resource.PortClass;
import com.ieee.atml.util.StringUtil;
import com.ieee.atml.util.XPathStandard;

public class AdapterReader extends InfoWrite implements XPathStandard, CompareConnectorPins {

	private static final String adapterDir = "TestAdapterConvert_1_飞控计算机MDI_数字测试系统_2_数字ITA_4.xml";
	private static final String UUTDir = "UUTDescriptionConvert_1_测评UUT检测_4_测评UUT_5_formal.xml";
	private static final String stationDir = "TestStationDescriptionDemoV6.xml";
	// private static final String regEX =
	// "(?:\\/(?:[a-zA-Z]{0,}:){0,1}[a-zA-Z]{0,}(?:(?:\\/){0,1}\\[@[a-zA-Z]{0,}=(['\"])[\\u4e00-\\u9fa5\\w\\+-]{0,}\\1\\]){0,}){1,}";
	// private static final String[] testItem = { "XPath路径标准检测", "XPath路径可达性检测",
	// "Adapter通路检测" };

	private List<ArrayList<String>> netWorkList = new ArrayList<>();
	private List<ArrayList<String>> errorNetWorkList = new ArrayList<>();
	private List<ArrayList<String>> netWorkLinks = new ArrayList<>();
	private List<ArrayList<String>> tempNetWorkLinks = new ArrayList<>();
	// private List<ArrayList<String>> netWorkListPool = new ArrayList<>();
	// private int errorXPath = 0;
	// private int errorXPathLink = 0;

	public List<ArrayList<ArrayList<String>>> getNetWorkLinks() {
		List<ArrayList<ArrayList<String>>> linkLists = new ArrayList<>();
		for (int i = 0; i < netWorkLinks.size(); i++) {
			ArrayList<String> link = netWorkLinks.get(i);
			ArrayList<ArrayList<String>> linkList = new ArrayList<>();
			String UUTXPath = link.get(0);
			String StationXPath = link.get(link.size() - 1);
			List<Element> UUTlist = UUTDocument.selectNodes(UUTXPath);
			ArrayList<String> UUTLinkList = new ArrayList<>();
			ArrayList<String> StationLinkList = new ArrayList<>();
			for (Iterator iterator = UUTlist.iterator(); iterator.hasNext();) {
				Element element = (Element) iterator.next();
				String XPath = element.getUniquePath();
				UUTLinkList.add(XPath);
			}
			List<Element> Stationlist = stationDocument.selectNodes(StationXPath);
			for (Iterator iterator = Stationlist.iterator(); iterator.hasNext();) {
				Element element = (Element) iterator.next();
				String XPath = element.getUniquePath();
				StationLinkList.add(XPath);
			}
			linkList.add(UUTLinkList);
			linkList.add(StationLinkList);
			linkLists.add(linkList);
		}
		int index = 0;
		/*
		 * for (Iterator iterator = linkLists.iterator(); iterator.hasNext();) {
		 * ArrayList<ArrayList<String>> arrayList =
		 * (ArrayList<ArrayList<String>>) iterator.next();
		 * System.out.println("*************" + index + "****************"); for
		 * (Iterator iterator2 = arrayList.iterator(); iterator2.hasNext();) {
		 * ArrayList<String> string = (ArrayList<String>) iterator2.next();
		 * System.out.println("-------------------------------"); for (Iterator
		 * iterator3 = string.iterator(); iterator3.hasNext();) { String string2
		 * = (String) iterator3.next(); System.out.println(string2); } }
		 * index++; }
		 */
		return linkLists;
	}

	private List<String> unStandardXpath = new ArrayList<String>();
	private List<String> standardXpath = new ArrayList<String>();
	private List<String> unExistXpath = new ArrayList<String>();

	private org.dom4j.Document adapterDocument;
	private org.dom4j.Document UUTDocument;
	private org.dom4j.Document stationDocument;
	// private String error = "";
	// private HashMap<String, ArrayList<String>> errorMap = new HashMap<String,
	// ArrayList<String>>();

	public AdapterReader() {
		testItem.add("TestAdapter接口中Connector描述");
		testItem.add("TestAdapter接口中Port描述");
		testItem.add("Port与Connector管脚一致性检查");
		testItem.add("XPath格式正确性检测");
		testItem.add("XPath路径引用正确及匹配性检查");
		//testItem.add("TestAdapter连接关系及连通性检测");
		//testItem.add("TestStation，UUTDescription文件port数据类型与数据流向检测");
	}

	public String getInfoHTML() {
		return infoHTML;
	}

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		AdapterReader test = new AdapterReader();
		test.adapterReader(adapterDir, UUTDir, stationDir);
	}

	private org.dom4j.Document readXML(String fileDir, String fileType) {
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
			if (fileType.equals(StringUtil.adapter)) {
				String string = file.getName();
				String[] strings = string.split("\\.");
				fileName = strings[0];
			}
		} else {
			System.out.println("File " + fileType + " is not exist!");
			// error += ("File " + configuration + " is not exist!\n");
			// writeError("File " + fileType + " is not exist!");
		}
		return document;
	}

	private void addTempNetworkList(ArrayList<String> tempNetWorkLinks) {
		if (this.tempNetWorkLinks.contains(tempNetWorkLinks)) {
			return;
		} else {
			this.tempNetWorkLinks.add(tempNetWorkLinks);
		}
	}

	private void findLink(String start, ArrayList<String> networkList, ArrayList<String> networkListSelf,
			ArrayList<ArrayList<String>> errorNetworkListItem) {
		for (int i = 0; i < errorNetWorkList.size(); i++) {
			ArrayList<String> netWorkNodes = errorNetWorkList.get(i);
			boolean isEqu = true;
			if (netWorkNodes.size() == networkListSelf.size()) {
				for (int j = 0; j < netWorkNodes.size(); j++) {
					String element1 = netWorkNodes.get(j);
					String element2 = networkListSelf.get(j);
					if (!element1.equals(element2)) {
						isEqu = false;
					}
				}
			}
			if (isEqu) {
				boolean isContinue = false;
				for (int j = 0; j < netWorkNodes.size(); j++) {
					String path = netWorkNodes.get(j);
					String[] dirs = path.split("/");
					String[] dir = dirs[1].split(":");
					if (dir.length == 1) {
						if (dir[0].equals(StringUtil.adapter)) {
							isContinue = true;
						} else if (dir[0].equals(StringUtil.UUT)) {

						} else if (dir[0].equals(StringUtil.station)) {

						}
					} else {
						if (dir[1].equals(StringUtil.adapter)) {
							isContinue = true;
						} else if (dir[1].equals(StringUtil.UUT)) {

						} else if (dir[1].equals(StringUtil.station)) {

						}
					}
				}
				if (isContinue) {
					continue;
				}
			}
			for (int j = 0; j < netWorkNodes.size(); j++) {
				String path = netWorkNodes.get(j);
				if (start.equals(path)) {
					String[] dirs = path.split("/");
					String[] dir = dirs[1].split(":");
					if (dir.length == 1) {
						if (dir[0].equals(StringUtil.adapter)) {
							ArrayList<String> networkLists = new ArrayList<String>();
							for (int k = 0; k < netWorkNodes.size(); k++) {
								String string = netWorkNodes.get(k);
								if (!path.equals(string)) {
									for (int k2 = 0; k2 < networkList.size(); k2++) {
										String stringX = networkList.get(k2);
										networkLists.add(stringX);
									}
									networkList.add(string);
									String[] dirsX = string.split("/");
									String[] dirX = dirsX[1].split(":");
									if (dirX.length == 1) {
										if (dirX[0].equals(StringUtil.station)) {
											/*
											 * for (int l = 0; l <
											 * errorNetworkListItem.size(); l++)
											 * { ArrayList<String> string2 =
											 * errorNetworkListItem.get(l);
											 * errorNetWorkList.remove(string2);
											 * } // errorNetWorkList.remove(
											 * networkListSelf);
											 * errorNetWorkList.remove(
											 * netWorkNodes);
											 */
											addTempNetworkList(netWorkNodes);
											netWorkLinks.add(networkLists);
											// i--;
											// i--;
										} else if (dirX[0].equals(StringUtil.UUT)) {
											/*
											 * for (int l = 0; l <
											 * errorNetworkListItem.size(); l++)
											 * { ArrayList<String> string2 =
											 * errorNetworkListItem.get(l);
											 * errorNetWorkList.remove(string2);
											 * } // errorNetWorkList.remove(
											 * networkListSelf);
											 * errorNetWorkList.remove(
											 * netWorkNodes);
											 */
											//addTempNetworkList(netWorkNodes);
											//netWorkLinks.add(networkLists);
											// i--;
											// i--;
										} else {
											// errorNetworkListItem.add(netWorkNodes);
											addTempNetworkList(netWorkNodes);
											findLink(string, networkLists, netWorkNodes, errorNetworkListItem);
											return;
										}
									} else {
										if (dirX[1].equals(StringUtil.station)) {
											/*
											 * for (int l = 0; l <
											 * errorNetworkListItem.size(); l++)
											 * { ArrayList<String> string2 =
											 * errorNetworkListItem.get(l);
											 * errorNetWorkList.remove(string2);
											 * } // errorNetWorkList.remove(
											 * networkListSelf);
											 * errorNetWorkList.remove(
											 * netWorkNodes);
											 */
											addTempNetworkList(netWorkNodes);
											netWorkLinks.add(networkLists);
											// i--;
											// i--;
										} else if (dirX[1].equals(StringUtil.UUT)) {
											/*
											 * for (int l = 0; l <
											 * errorNetworkListItem.size(); l++)
											 * { ArrayList<String> string2 =
											 * errorNetworkListItem.get(l);
											 * errorNetWorkList.remove(string2);
											 * } // errorNetWorkList.remove(
											 * networkListSelf);
											 * errorNetWorkList.remove(
											 * netWorkNodes);
											 */
											//addTempNetworkList(netWorkNodes);
											//netWorkLinks.add(networkLists);
											// i--;
											// i--;
										} else {
											// errorNetworkListItem.add(netWorkNodes);
											addTempNetworkList(netWorkNodes);
											findLink(string, networkLists, netWorkNodes, errorNetworkListItem);
											return;
										}
									}
									// netWorkLinks.add(networkList);
								}
							}
						} else if (dir[0].equals(StringUtil.UUT)) {
							// errorNetWorkList.remove(networkListSelf);
							errorNetWorkList.remove(netWorkNodes);
							netWorkLinks.add(netWorkNodes);
							// i--;
						} else if (dir[0].equals(StringUtil.station)) {
							// errorNetWorkList.remove(networkListSelf);
							errorNetWorkList.remove(netWorkNodes);
							netWorkLinks.add(netWorkNodes);
							// i--;
							// netWorkLinks.add(networkList);
						}
					} else {
						if (dir[1].equals(StringUtil.adapter)) {
							ArrayList<String> networkLists = new ArrayList<String>();
							for (int k = 0; k < netWorkNodes.size(); k++) {
								String string = netWorkNodes.get(k);
								if (!path.equals(string)) {
									for (int k2 = 0; k2 < networkList.size(); k2++) {
										String stringX = networkList.get(k2);
										networkLists.add(stringX);
									}
									networkLists.add(string);
									String[] dirsX = string.split("/");
									String[] dirX = dirsX[1].split(":");
									if (dirX.length == 1) {
										if (dirX[0].equals(StringUtil.station)) {
											/*
											 * for (int l = 0; l <
											 * errorNetworkListItem.size(); l++)
											 * { ArrayList<String> string2 =
											 * errorNetworkListItem.get(l);
											 * errorNetWorkList.remove(string2);
											 * } // errorNetWorkList.remove(
											 * networkListSelf);
											 * errorNetWorkList.remove(
											 * netWorkNodes);
											 */
											addTempNetworkList(netWorkNodes);
											netWorkLinks.add(networkLists);
											// i--;
											// i--;
										} else if (dirX[0].equals(StringUtil.UUT)) {
											/*
											 * for (int l = 0; l <
											 * errorNetworkListItem.size(); l++)
											 * { ArrayList<String> string2 =
											 * errorNetworkListItem.get(l);
											 * errorNetWorkList.remove(string2);
											 * } // errorNetWorkList.remove(
											 * networkListSelf);
											 * errorNetWorkList.remove(
											 * netWorkNodes);
											 */
											//addTempNetworkList(netWorkNodes);
											//netWorkLinks.add(networkLists);
											// i--;
											// i--;
										} else {
											// errorNetworkListItem.add(netWorkNodes);
											addTempNetworkList(netWorkNodes);
											findLink(string, networkLists, netWorkNodes, errorNetworkListItem);
											return;
										}
									} else {
										if (dirX[1].equals(StringUtil.station)) {
											/*
											 * for (int l = 0; l <
											 * errorNetworkListItem.size(); l++)
											 * { ArrayList<String> string2 =
											 * errorNetworkListItem.get(l);
											 * errorNetWorkList.remove(string2);
											 * } // errorNetWorkList.remove(
											 * networkListSelf);
											 * errorNetWorkList.remove(
											 * netWorkNodes);
											 */
											addTempNetworkList(netWorkNodes);
											netWorkLinks.add(networkLists);
											// i--;
											// i--;
										} else if (dirX[1].equals(StringUtil.UUT)) {
											/*
											 * for (int l = 0; l <
											 * errorNetworkListItem.size(); l++)
											 * { ArrayList<String> string2 =
											 * errorNetworkListItem.get(l);
											 * errorNetWorkList.remove(string2);
											 * } // errorNetWorkList.remove(
											 * networkListSelf);
											 * errorNetWorkList.remove(
											 * netWorkNodes);
											 */
											//addTempNetworkList(netWorkNodes);
											//netWorkLinks.add(networkLists);
											// i--;
											// i--;
										} else {
											// errorNetworkListItem.add(netWorkNodes);
											addTempNetworkList(netWorkNodes);
											findLink(string, networkLists, netWorkNodes, errorNetworkListItem);
											return;
										}
									}
									// netWorkLinks.add(networkList);
								}
							}
						} else if (dir[1].equals(StringUtil.UUT)) {
							// errorNetWorkList.remove(networkListSelf);
							errorNetWorkList.remove(netWorkNodes);
							netWorkLinks.add(netWorkNodes);
							// i--;
						} else if (dir[1].equals(StringUtil.station)) {
							// errorNetWorkList.remove(networkListSelf);
							errorNetWorkList.remove(netWorkNodes);
							netWorkLinks.add(netWorkNodes);
							// i--;
							// netWorkLinks.add(networkList);
						}
					}
				}

			}
		}
	}

	private void extractNetworkLink() {
		for (int i = 0; i < netWorkList.size(); i++) {
			ArrayList<String> netWorkNodes = netWorkList.get(i);
			for (int j = 0; j < netWorkNodes.size(); j++) {
				String path = netWorkNodes.get(j);
				String[] dirs = path.split("/");
				String[] dir = dirs[1].split(":");
				if (dir.length == 1) {
					if (dir[0].equals(StringUtil.adapter)) {

					} else if (dir[0].equals(StringUtil.UUT)) {
						ArrayList<ArrayList<String>> errorNetworkListItem = new ArrayList<>();
						ArrayList<String> networkList = new ArrayList<String>();
						networkList.add(path);
						for (int k = 0; k < netWorkNodes.size(); k++) {
							String string = netWorkNodes.get(k);
							if (!path.equals(string)) {
								networkList.add(string);
								errorNetworkListItem.add(netWorkNodes);
								addTempNetworkList(netWorkNodes);
								findLink(string, networkList, netWorkNodes, errorNetworkListItem);
								// netWorkLinks.add(networkList);
							}
						}
					} else if (dir[0].equals(StringUtil.station)) {
						ArrayList<ArrayList<String>> errorNetworkListItem = new ArrayList<>();
						ArrayList<String> networkList = new ArrayList<String>();
						networkList.add(path);
						for (int k = 0; k < netWorkNodes.size(); k++) {
							String string = netWorkNodes.get(k);
							if (!path.equals(string)) {
								networkList.add(string);
								errorNetworkListItem.add(netWorkNodes);
								addTempNetworkList(netWorkNodes);
								findLink(string, networkList, netWorkNodes, errorNetworkListItem);
								// netWorkLinks.add(networkList);
							}
						}
						// findLink(path);
					}
				} else {
					if (dir[1].equals(StringUtil.adapter)) {

					} else if (dir[1].equals(StringUtil.UUT)) {
						ArrayList<ArrayList<String>> errorNetworkListItem = new ArrayList<>();
						ArrayList<String> networkList = new ArrayList<String>();
						networkList.add(path);
						for (int k = 0; k < netWorkNodes.size(); k++) {
							String string = netWorkNodes.get(k);
							if (!path.equals(string)) {
								networkList.add(string);
								errorNetworkListItem.add(netWorkNodes);
								addTempNetworkList(netWorkNodes);
								findLink(string, networkList, netWorkNodes, errorNetworkListItem);
								// netWorkLinks.add(networkList);
							}
						}
					} else if (dir[1].equals(StringUtil.station)) {
						ArrayList<ArrayList<String>> errorNetworkListItem = new ArrayList<>();
						ArrayList<String> networkList = new ArrayList<String>();
						networkList.add(path);
						for (int k = 0; k < netWorkNodes.size(); k++) {
							String string = netWorkNodes.get(k);
							if (!path.equals(string)) {
								networkList.add(string);
								errorNetworkListItem.add(netWorkNodes);
								addTempNetworkList(netWorkNodes);
								findLink(string, networkList, netWorkNodes, errorNetworkListItem);
								// netWorkLinks.add(networkList);
							}
						}
						// findLink(path);
					}
				}
			}
		}
		// writeInfo("##############Adapter通路检测###############");
		WriteTestItem1(testItem.get(5));
		WriteNormalInfoWithoutEnter(testItem.get(5), "Network连接节点总数为：");
		WriteBoldInfo(testItem.get(5), netWorkList.size() + "对");
		ArrayList<ArrayList<String>> notUnique = checkUnique();
		WriteNormalInfoWithoutEnter(testItem.get(5), "已连接的TestStation，UUTDescription总数为：");
		WriteBoldInfo(testItem.get(5), netWorkLinks.size() + "对");
		WriteNormalInfoWithoutEnter(testItem.get(5), "重复连接的TestStation，UUTDescription总数为：");
		WriteBoldInfo(testItem.get(5), notUnique.size() + "对");
		if (notUnique.size() > 0) {
			WriteNormalInfo(testItem.get(5), "重复连接的TestStation，UUTDescription分别为：");
			for (Iterator iterator = notUnique.iterator(); iterator.hasNext();) {
				ArrayList<String> arrayList = (ArrayList<String>) iterator.next();
				WriteTestItem2(testItem.get(5), "连接对");
				WriteErrorInfo(testItem.get(5), arrayList.get(0));
				WriteErrorInfo(testItem.get(5), arrayList.get(1));
				WriteErrorInfo(testItem.get(5), "出现" + arrayList.get(2) + "次");
			}
		}
		for (Iterator iterator = tempNetWorkLinks.iterator(); iterator.hasNext();) {
			ArrayList<String> arrayList = (ArrayList<String>) iterator.next();
			errorNetWorkList.remove(arrayList);
		}
		WriteNormalInfoWithoutEnter(testItem.get(5), "已与TestStation，UUTDescription建立连接的Network接节点总数为：");
		WriteBoldInfo(testItem.get(5), (netWorkList.size() - errorNetWorkList.size()) + "对");
		WriteNormalInfoWithoutEnter(testItem.get(5), "未与TestStation，UUTDescription建立连接的Network节点总数为：");
		WriteBoldInfo(testItem.get(5), errorNetWorkList.size() + "对");
		if (errorNetWorkList.size() > 0) {
			WriteNormalInfo(testItem.get(5), "以下为未与TestStation，UUTDescription建立连接的Network节点：");
			// addErrorItem(testItem[2], "未连接节点总数为：" + errorNetWorkList.size() +
			// "对\n以下为未连接节点：");
			for (int i = 0; i < errorNetWorkList.size(); i++) {
				ArrayList<String> items = errorNetWorkList.get(i);
				System.out.println("##############" + i + "##############");
				// writeInfo("------------未连接节点------------");
				WriteTestItem2(testItem.get(5), "未建立连接的Network节点对");
				// addErrorItem(testItem[2], "------------未连接节点对------------");
				for (int j = 0; j < items.size(); j++) {
					String path = items.get(j);
					System.out.println(path);
					// writeError(path);
					WriteErrorInfo(testItem.get(5), path);
					// addErrorItem(testItem[2], (path));
				}
			}
		}

	}

	private ArrayList<ArrayList<String>> checkUnique() {
		ArrayList<ArrayList<String>> notUnique = new ArrayList<>();
		for (int k = 0; k < netWorkLinks.size(); k++) {
			ArrayList<String> networkList = netWorkLinks.get(k);
			for (int j = 0; j < netWorkLinks.size(); j++) {
				ArrayList<String> list = netWorkLinks.get(j);
				if (networkList.size() == list.size()) {
					boolean isEqu = true;
					for (int i = 0; i < list.size(); i++) {
						if (!list.get(i).equals(networkList.get(networkList.size() - i - 1))) {
							isEqu = false;
						}
					}
					if (isEqu) {
						netWorkLinks.remove(list);
						break;
					}
				}
			}
		}
		for (Iterator iterator = netWorkLinks.iterator(); iterator.hasNext();) {
			ArrayList<String> networkList = (ArrayList<String>) iterator.next();
			ArrayList<String> item = new ArrayList<>();
			item.add(networkList.get(0));
			item.add(networkList.get(networkList.size() - 1));
			boolean isContinue = false;
			for (Iterator iterator2 = notUnique.iterator(); iterator2.hasNext();) {
				ArrayList<String> list = (ArrayList<String>) iterator2.next();
				if (item.get(0).equals(list.get(0)) && item.get(1).equals(list.get(list.size() - 2))) {
					isContinue = true;
				} else if (item.get(1).equals(list.get(0)) && item.get(0).equals(list.get(list.size() - 2))) {
					isContinue = true;
				}
			}
			if (isContinue) {
				continue;
			}
			int num = 0;
			for (Iterator iterator2 = netWorkLinks.iterator(); iterator2.hasNext();) {
				ArrayList<String> list = (ArrayList<String>) iterator2.next();
				if (item.get(0).equals(list.get(0)) && item.get(1).equals(list.get(list.size() - 1))) {
					num++;
				} else if (item.get(1).equals(list.get(0)) && item.get(0).equals(list.get(list.size() - 1))) {
					num++;
				}
			}
			if (num >= 2) {
				item.add(num + "");
				notUnique.add(item);
			}
		}
		return notUnique;
	}

	private void checkPort() {
		AdapterVisitor adapterVisitor = new AdapterVisitor();
		adapterDocument.accept(adapterVisitor);
		WriteTestItem1(testItem.get(0));

		for (Iterator<ConnectorClass> iterator = adapterVisitor.getConnectorList().iterator(); iterator.hasNext();) {
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
		WriteBoldInfo(testItem.get(1), adapterVisitor.getPortList().size());
		// addErrorItem(testItem[1],"Port的个数=" + uvisitor.getPortList().size());
		for (Iterator<ConnectorClass> iterator = adapterVisitor.getConnectorList().iterator(); iterator.hasNext();) {
			String conID = iterator.next().getConID();
			int tempNum = 0;
			// errorInfo+="节点c:ConnectorPin的connectorID属性为"+connectorId+"的数目为"+connectorNum.get(num++)+"\n";
			for (Iterator<PortClass> iterator2 = adapterVisitor.getPortList().iterator(); iterator2.hasNext();) {
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

		WriteTestItem1(testItem.get(2));
		CompareTwoList(adapterVisitor.getConnectorPins(), adapterVisitor.getConnectorPins1(), errorPin, errorPin1);
		if ((!errorPin.isEmpty()) || (!errorPin1.isEmpty())) {
			// errorInfo+="不匹配节点的XPath路径为：\n";
			List<String> pathList = adapterVisitor.getcPinpath();
			List<String> pathList1 = adapterVisitor.getcPinpath1();
			if (!errorPin.isEmpty()) {
				WriteNormalInfo(testItem.get(2), "Connector上未被Port采用的针脚的XPath如下:");
				// addErrorItem(testItem[2],"Connector上未被Port采用的针脚的XPath如下:");
				// fos.write(tempstr.getBytes());

				for (Iterator<String> iterator = errorPin.iterator(); iterator.hasNext();) {
					String tempID = iterator.next();
					WriteErrorInfo(testItem.get(2),
							pathList.get(adapterVisitor.getConnectorPins().indexOf(tempID)).toString());
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
							pathList1.get(adapterVisitor.getConnectorPins1().indexOf(tempID)).toString());
					// addErrorItem(testItem[2],pathList1.get(uvisitor.getConnectorPins1().indexOf(tempID)).toString());
					// errorInfo+=pathList1.get(list1.indexOf(tempID))+"\n";
					// fos.write(tempstr.getBytes());
				}
			}

		} else {
			WriteNormalInfo(testItem.get(2), "节点完全匹配！");
			// addErrorItem(testItem[2],"节点完全匹配！");
			// errorInfo+="节点完全匹配！\n";
		}

	}

	private Node getNode(String path) {
		Node node = null;
		boolean isUUT = true;
		try {
			node = UUTDocument.selectSingleNode(path);
		} catch (Exception e) {
			// TODO: handle exception
			isUUT = false;
		}
		if (!false) {
			try {
				node = stationDocument.selectSingleNode(path);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return node;
	}

	private String getDataType(String path) {
		Node node = getNode(path);
		return node.valueOf("@type");
	}

	private String getDataFlow(String path) {
		Node node = getNode(path);
		return node.valueOf("@direction");
	}

	private ArrayList<String> compareUUTAndTA(String XPath1, String XPath2) {
		ArrayList<String> error = new ArrayList<>();
		if (getDataFlow(XPath1).equals(PinFlow.input)) {
			if (getDataFlow(XPath2).equals(PinFlow.output) || getDataFlow(XPath2).equals(PinFlow.biDirectional)) {
				if (getDataType(XPath1).equals(getDataType(XPath2))) {

				} else {
					if (getDataType(XPath1).equals(PinType.ground) || getDataType(XPath2).equals(PinType.ground)) {

					} else {
						error.add(XPath1);
						error.add(XPath2);
						error.add("数据类型不匹配");
					}
				}
			} else {
				if (getDataType(XPath1).equals(getDataType(XPath2))) {
					if (getDataType(XPath1).equals(PinType.ground) || getDataType(XPath2).equals(PinType.ground)) {

					} else {
						error.add(XPath1);
						error.add(XPath2);
						error.add("数据流向不匹配");
					}
				} else {
					if (getDataType(XPath1).equals(PinType.ground) || getDataType(XPath2).equals(PinType.ground)) {

					} else {
						error.add(XPath1);
						error.add(XPath2);
						error.add("数据类型与数据流向均不匹配");
					}
				}
			}
		} else if (getDataFlow(XPath1).equals(PinFlow.output)) {
			if (getDataFlow(XPath2).equals(PinFlow.input) || getDataFlow(XPath2).equals(PinFlow.biDirectional)) {
				if (getDataType(XPath1).equals(getDataType(XPath2))) {

				} else {
					if (getDataType(XPath1).equals(PinType.ground) || getDataType(XPath2).equals(PinType.ground)) {

					} else {
						error.add(XPath1);
						error.add(XPath2);
						error.add("数据类型不匹配");
					}
				}
			} else {
				if (getDataType(XPath1).equals(getDataType(XPath2))) {
					if (getDataType(XPath1).equals(PinType.ground) || getDataType(XPath2).equals(PinType.ground)) {

					} else {
						error.add(XPath1);
						error.add(XPath2);
						error.add("数据流向不匹配");
					}
				} else {
					if (getDataType(XPath1).equals(PinType.ground) || getDataType(XPath2).equals(PinType.ground)) {

					} else {
						error.add(XPath1);
						error.add(XPath2);
						error.add("数据类型与数据流向均不匹配");
					}
				}
			}
		} else if (getDataFlow(XPath1).equals(PinFlow.biDirectional)) {
			if (getDataType(XPath1).equals(getDataType(XPath2))) {

			} else {
				if (getDataType(XPath1).equals(PinType.ground) || getDataType(XPath2).equals(PinType.ground)) {

				} else {
					error.add(XPath1);
					error.add(XPath2);
					error.add("数据类型不匹配");
				}
			}

		}
		return error;
	}

	private void checkPortTypeAndFlow() {
		WriteTestItem1(testItem.get(6));
		int num = 0;
		for (Iterator iterator = netWorkLinks.iterator(); iterator.hasNext();) {
			ArrayList<String> item = (ArrayList<String>) iterator.next();
			ArrayList<String> error = compareUUTAndTA(item.get(0), item.get(item.size() - 1));
			if (error.size() > 0) {
				num++;
			} else {

			}
		}
		if (num == 0) {
			WriteNormalInfo(testItem.get(6), "检测通过过！");
		} else {
			WriteNormalInfoWithoutEnter(testItem.get(6), "共有错误连接对：");
			WriteBoldInfo(testItem.get(6), num + "对");
			for (Iterator iterator = netWorkLinks.iterator(); iterator.hasNext();) {
				ArrayList<String> item = (ArrayList<String>) iterator.next();
				ArrayList<String> error = compareUUTAndTA(item.get(0), item.get(item.size() - 1));
				if (error.size() > 0) {
					WriteTestItem2(testItem.get(6), "连接对");
					WriteErrorInfo(testItem.get(6), error.get(0));
					WriteErrorInfo(testItem.get(6), error.get(1));
					WriteErrorInfo(testItem.get(6), error.get(2));
				} else {

				}
			}
		}
		
	}

	public void adapterReader(String adapterDir, String UUTDir, String stationDir) {
		// TODO 自动生成的方法存根
		// error += "#####################File Path######################\n";
		// writeInfo("#####################File Path######################");
		UUTDocument = readXML(UUTDir, StringUtil.UUT);
		adapterDocument = readXML(adapterDir, StringUtil.adapter);
		stationDocument = readXML(stationDir, StringUtil.station);
		initInfoMap();
		WriteTxtAndHTMLOnly("检测完成，结果如下:");
		checkPort();
		XMLNetWorkList2NetWorkList();
		System.out.println(netWorkList.size());
		checkNetWorkList();
		System.out.println(netWorkList.size());
		//extractNetworkLink();
		//WriteRedInfo("-----------检测通过-----------","-----------检测通过-----------");
		//checkPortTypeAndFlow();
		write2TXT();

		for (int i = 0; i < netWorkLinks.size(); i++) {
			ArrayList<String> items = netWorkLinks.get(i);
			System.out.println("##############" + i + "##############");
			for (int j = 0; j < items.size(); j++) {
				String path = items.get(j);
				System.out.println(path);
			}
		}
		for (int i = 0; i < errorNetWorkList.size(); i++) {
			ArrayList<String> items = errorNetWorkList.get(i);
			System.out.println("##############" + i + "##############");
			for (int j = 0; j < items.size(); j++) {
				String path = items.get(j);
				System.out.println(path);
			}
		}

		infoWriteWord(fileName);

		// System.out.println(matchXPath("/uut:UUTDescription/uut:Hardware/hc:Interface/c:Ports/c:Port/[@name=\"P9']"));
	}

	private List<Element> getElementWithNamespace(Element root, String tagName, String namespace) {
		List<Element> list = new ArrayList<Element>();
		List<Element> tagList = root.elements(tagName);
		for (Element element : tagList) {
			if (element.getNamespacePrefix().equals(namespace)) {
				list.add(element);
			}
		}
		return list;
	}

	private void XMLNetWorkList2NetWorkList() {
		if (adapterDocument == null) {
			return;
		}
		Element root = adapterDocument.getRootElement();
		List<Element> network = getElementWithNamespace(root, "NetworkList", "hc");
		// error += ("#################XPath standard
		// check#####################\n");
		// writeInfo("#################XPath standard
		// check#####################");
		WriteTestItem1(testItem.get(3));
		WriteNormalInfoWithoutEnter(testItem.get(3), "待检测的XPath路径个数为：");
		// addErrorItemWithoutEnter(testItem[0], "待检测的XPath路径个数为：");
		int textPathNum = 0;
		for (Iterator<Element> iterators = network.iterator(); iterators.hasNext();) {
			Element elements = (Element) iterators.next();
			List<Element> list = getElementWithNamespace(elements, "Network", "hc");
			for (Iterator<Element> iterator = list.iterator(); iterator.hasNext();) {
				Element element = (Element) iterator.next();
				System.out.println(element.getName());
				List<Element> nodes = getElementWithNamespace(element, "Node", "hc");
				System.out.println(nodes.size());
				List<String> netWorkNodes = new ArrayList<>();
				boolean isAdd = true;
				for (Iterator<Element> iteratorNodes = nodes.iterator(); iteratorNodes.hasNext();) {
					Element path = (Element) iteratorNodes.next();
					String str = path.element("Path").getText();
					if (!matchXPath(str)) {
						System.out.println(str + " is not standard XPath!");
						// error += (str + " is not standard XPath!\n");
						// writeError(str + " is not standard XPath!");
						unStandardXpath.add(str);
						// addErrorItemWithoutEnter(testItem[0], (str + " is not
						// standard XPath!\n"));
						isAdd = false;
					} else {
						int index = str.indexOf("/[@");
						if (index != -1) {
							str = str.replace("/[", "[");
							System.out.println(str);
						}
						standardXpath.add(str);
						netWorkNodes.add(str);
					}
					textPathNum++;
				}
				if (netWorkNodes.size() >= 2 && isAdd) {
					netWorkList.add((ArrayList<String>) netWorkNodes);
					errorNetWorkList.add((ArrayList<String>) netWorkNodes);
					// netWorkListPool.add((ArrayList<String>) netWorkNodes);
				}
			}

		}
		WriteBoldInfo(testItem.get(3), textPathNum);
		// addErrorItem(testItem[0], textPathNum + "");

		WriteNormalInfoWithoutEnter(testItem.get(3), "标准的XPath路径个数为：");
		// addErrorItemWithoutEnter(testItem[0], "标准的XPath路径个数为：");

		WriteBoldInfo(testItem.get(3), standardXpath.size() + "");
		// addErrorItem(testItem[0], standardXpath.size() + "");

		WriteNormalInfoWithoutEnter(testItem.get(3), "不标准的XPath路径个数为：");
		// addErrorItemWithoutEnter(testItem[0], "不标准的XPath路径个数为：");

		WriteBoldInfo(testItem.get(3), unStandardXpath.size() + "");
		// addErrorItem(testItem[0], unStandardXpath.size() + "");
		if (unStandardXpath.size() > 0) {
			WriteNormalInfo(testItem.get(3), "不标准的XPath路径为：");
			// addErrorItem(testItem[0], "不标准的XPath路径为：");
			for (int i = 0; i < unStandardXpath.size(); i++) {
				String unstandardXpath = unStandardXpath.get(i);
				WriteErrorInfo(testItem.get(3), unstandardXpath);
				// addErrorItem(testItem[0], unstandardXpath);
			}
		}
	}

	private void checkNetWorkList() {
		System.out.println("####################check#####################");
		// error += "#####################XPath######################\n";
		// writeInfo("#####################XPath######################");
		WriteTestItem1(testItem.get(4));
		/*
		 * for (Iterator<ArrayList<String>> iterator = netWorkList.iterator();
		 * iterator.hasNext();) { ArrayList<String> netWorkNodes =
		 * (ArrayList<String>) iterator.next(); for (Iterator<String>
		 * iteratorNodes = netWorkNodes.iterator(); iteratorNodes.hasNext();) {
		 * String path = (String) iteratorNodes.next(); if(!checkPath(path)){
		 * int s = netWorkList.indexOf(netWorkNodes);
		 * netWorkList.remove(netWorkList.indexOf(netWorkNodes)); } } }
		 */
		for (int i = 0; i < netWorkList.size(); i++) {
			ArrayList<String> netWorkNodes = netWorkList.get(i);
			for (int j = 0; j < netWorkNodes.size(); j++) {
				String path = netWorkNodes.get(j);
				if (!checkPath(path)) {
					int index = netWorkList.indexOf(netWorkNodes);
					if (index != -1) {
						netWorkList.remove(index);
						errorNetWorkList.remove(index);
						i--;
					}
				}
			}
		}
		WriteNormalInfoWithoutEnter(testItem.get(4), "标准的XPath路径中可达路径的个数为：");
		// addErrorItemWithoutEnter(testItem[1], "标准的XPath路径中可达路径的个数为：");

		WriteBoldInfo(testItem.get(4), (standardXpath.size() - unExistXpath.size()) + "");
		// addErrorItem(testItem[1], (standardXpath.size() -
		// unExistXpath.size()) + "");

		WriteNormalInfoWithoutEnter(testItem.get(4), "标准的XPath路径中不可达路径的个数为：");
		// addErrorItemWithoutEnter(testItem[1], "标准的XPath路径中不可达路径的个数为：");

		WriteBoldInfo(testItem.get(4), unExistXpath.size() + "");
		// addErrorItem(testItem[1], unExistXpath.size() + "");

		if (unExistXpath.size() > 0) {
			WriteNormalInfo(testItem.get(4), "标准的XPath路径中不可达路径如下所示：");
			// addErrorItem(testItem[1], "标准的XPath路径中不可达路径如下所示：");
			for (int i = 0; i < unExistXpath.size(); i++) {
				String unstandardXpath = unExistXpath.get(i);
				WriteErrorInfo(testItem.get(4), unstandardXpath);
				// addErrorItem(testItem[1], unstandardXpath);
			}
		}

	}

	private boolean checkPath(String path) {
		String[] dirs = path.split("/");
		String[] dir = dirs[1].split(":");
		// String errorItem = "";
		if (dir.length == 1) {
			if (dir[0].equals(StringUtil.adapter)) {
				return checkNetWorkListInFile(path, FileType.adapter);
			} else if (dir[0].equals(StringUtil.UUT)) {
				return checkNetWorkListInFile(path, FileType.UUT);
			} else if (dir[0].equals(StringUtil.station)) {
				return checkNetWorkListInFile(path, FileType.station);
			} else {
				System.out.println(path + " is not exist!");
				// error += (path + " is not exist!\n");
				// writeError(path + " is not exist!");
				unExistXpath.add(path);
				// addErrorItemWithoutEnter(testItem[1], (path + " is not
				// exist!\n"));
			}
		} else {
			if (dir[1].equals(StringUtil.adapter)) {
				return checkNetWorkListInFile(path, FileType.adapter);
			} else if (dir[1].equals(StringUtil.UUT)) {
				return checkNetWorkListInFile(path, FileType.UUT);
			} else if (dir[1].equals(StringUtil.station)) {
				return checkNetWorkListInFile(path, FileType.station);
			} else {
				System.out.println(path + " is not exist!");
				// error += (path + " is not exist!\n");
				// writeError(path + " is not exist!");
				unExistXpath.add(path);
				// addErrorItemWithoutEnter(testItem[1], (path + " is not
				// exist!\n"));
			}
		}
		return false;
	}

	private boolean checkNetWorkListInFile(String path, FileType fileType) {
		// String dir = "";
		List<Element> list = null;
		switch (fileType) {
		case adapter:
			if (adapterDocument != null) {
				try {
					list = adapterDocument.selectNodes(path);
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("############" + fileType.toString() + "#############");
					System.out.println(e);
					System.out.println(path + " is not standard XPath!");
					// error += (path + " is not exist!\n");
					// writeError(path + " is not exist!");

					unExistXpath.add(path);
					// addErrorItemWithoutEnter(testItem[1], (path + " is not
					// exist!\n"));
				}

			} else {

			}
			break;
		case UUT:
			if (UUTDocument != null) {
				try {
					list = UUTDocument.selectNodes(path);
				} catch (Exception e) {
					// TODO: handle exception

					System.out.println("############" + fileType.toString() + "#############");
					System.out.println(e);
					System.out.println(path + " is not standard XPath!");
					// error += (path + " is not exist!\n");
					// writeError(path + " is not exist!");
					unExistXpath.add(path);
					// addErrorItemWithoutEnter(testItem[1], (path + " is not
					// exist!\n"));
				}

			} else {

			}
			break;
		case station:
			if (stationDocument != null) {
				try {
					list = stationDocument.selectNodes(path);
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("############" + fileType.toString() + "#############");
					System.out.println(e);
					System.out.println(path + " is not standard XPath!");
					// error += (path + " is not exist!\n");
					// writeError(path + " is not exist!");
					unExistXpath.add(path);
					// addErrorItemWithoutEnter(testItem[1], (path + " is not
					// exist!\n"));
				}
			} else {

			}
			break;
		default:
			break;
		}
		if (list != null) {
			if (list.size() == 0) {
				System.out.println("############" + fileType.toString() + "#############");
				System.out.println(path + " is not exist!");
				// error += (path + " is not exist!\n");
				// writeError(path + " is not exist!");
				unExistXpath.add(path);
				// addErrorItemWithoutEnter(testItem[1], (path + " is not
				// exist!\n"));
			} else {
				// TODO link check
				return true;
			}
		} else {

		}
		return false;
	}

	/*
	 * private boolean matchXPath(String XPath) { // String regEX = //
	 * "(?:\\/(?:[a-zA-Z]{0,}:){0,1}[a-zA-Z]{0,}){1,}(?:(?:\\/){0,1}\\[@[a-zA-Z]{0,}=(['\"])[a-zA-Z_0-9\\+-]{0,}\\1\\]){0,}";
	 * // String regEX2 = "(\\/[a-zA-Z]{0,}:[a-zA-Z]{0,}){1,}"; // String regEX1
	 * = "(\\/\\[@name='[a-zA-Z_0-9]{0,}'\\]){0,}"; Pattern pattern =
	 * Pattern.compile(regEX); Matcher matcher = pattern.matcher(XPath); return
	 * matcher.matches(); }
	 */

	private void write2TXT() {
		File dataFlie = new File(fileName + "检测结果文件.txt");
		if (dataFlie.exists()) {
			dataFlie.delete();
		} else {
			try {
				dataFlie.createNewFile();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(dataFlie);
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		try {
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
