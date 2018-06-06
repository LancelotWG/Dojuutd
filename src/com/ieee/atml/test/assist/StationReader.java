package com.ieee.atml.test.assist;

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
import org.dom4j.io.SAXReader;

import com.ieee.atml.info.InfoWrite;
import com.ieee.atml.util.StringUtil;
import com.ieee.atml.util.XPathStandard;

public class StationReader extends InfoWrite implements XPathStandard {
	private static final String stationDir = "TestStationDescriptionDemoV6.xml";
	// private static final String regEX =
	// "(?:\\/(?:[a-zA-Z]{0,}:){0,1}[a-zA-Z]{0,}(?:(?:\\/){0,1}\\[@[a-zA-Z]{0,}=(['\"])[\\u4e00-\\u9fa5\\w\\+-]{0,}\\1\\]){0,}){1,}";
	// private List<ArrayList<String>> netWorkList = new ArrayList<>();

	private List<String> unStandardXpath = new ArrayList<String>();
	private List<String> standardXpath = new ArrayList<String>();
	private List<String> unExistXpath = new ArrayList<String>();
	private ArrayList<String> errorXpath = new ArrayList<String>();
	// private String documentID = "";

	/*
	 * public List<ArrayList<String>> getNetWorkList() { return netWorkList; }
	 */

	private org.dom4j.Document stationDocument;
	private static final String station = "TestStationDescription";

	public StationReader(ArrayList<String> testItem) {
		this.testItem = testItem;
	}

	public String getInfoHTML() {
		return infoHTML;
	}

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		StationReader test = new StationReader(new ArrayList<String>());
		test.stationReader(stationDir);
	}

	public String[] stationReader(String stationDir) {
		// TODO 自动生成的方法存根
		// error += "#####################File Path######################\n";
		// writeInfo("#####################File Path######################");
		File stationFile = new File(stationDir);
		if (stationFile.exists()) {
			SAXReader saxStationReader = new SAXReader();
			stationDocument = null;
			try {
				stationDocument = saxStationReader.read(stationFile);
			} catch (DocumentException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		} else {
			System.out.println("File " + station + " is not exist!");
			// error += ("File " + station + " is not exist!\n");
			// writeError("File " + station + " is not exist!");
		}
		initInfoMap();
		XMLNetWorkList2NetWorkList();
		checkNetWorkList();
		String[] info = { infoTxt, infoHTML, errorMap.get(testItem.get(5)).get(1) };
		return info;
		// write2TXT();
		/*
		 * String[] error = {"",""}; error[0] = infoHTML; error[1] = infoTxt;
		 * return error;
		 */
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
		Element root = stationDocument.getRootElement();
		List<Element> network = getElementWithNamespace(root, "NetworkList", "hc");
		// error += ("#################XPath standard
		// check#####################\n");
		// WriteNormalInfo("**********TestStation路径检测**********");
		WriteTestItem1(testItem.get(5));

		int textPathNum = 0;
		int notWorklistNum = 0;
		for (Iterator<Element> iterators = network.iterator(); iterators.hasNext();) {
			Element elements = (Element) iterators.next();
			List<Element> list = getElementWithNamespace(elements, "Network", "hc");
			for (Iterator<Element> iterator = list.iterator(); iterator.hasNext();) {
				Element element = (Element) iterator.next();
				System.out.println(element.getName());
				List<Element> nodes = getElementWithNamespace(element, "Node", "hc");
				System.out.println(nodes.size());
				List<String> netWorkNodes = new ArrayList<>();
				notWorklistNum++;
				for (Iterator<Element> iteratorNodes = nodes.iterator(); iteratorNodes.hasNext();) {
					Element path = (Element) iteratorNodes.next();
					String str = path.element("Path").getText();
					// String documentID = path.attributeValue("documentId");
					// 判断是否为TestStation自连路径，之后再做检测
					if (getXPathType(str).equals(StringUtil.station)) {
						/*
						 * if(documentID.equals("")){ documentID =
						 * path.attributeValue("documentId"); }
						 */
						if (!matchXPath(str)) {
							System.out.println(str + " is not standard XPath!");
							// error += (str + " is not standard XPath!\n");
							// writeError(str + " is not standard XPath!");
							unStandardXpath.add(str);
						} else {
							int index = str.indexOf("/[@");
							if (index != -1) {
								str = str.replace("/[", "[");
								System.out.println(str);
							}
							standardXpath.add(str);
						}
						textPathNum++;
					} else {
						if (getXPathType(str).equals(StringUtil.instrument)) {

						} else {
							errorXpath.add(str);
						}
						/*
						 * if(){
						 * 
						 * }
						 */
						// unStandardXpath.add(str);
					}
					// netWorkNodes.add(str);
				}
				// netWorkList.add((ArrayList<String>) netWorkNodes);
			}

		}
		WriteNormalInfoWithoutEnterS(testItem.get(5), "共存在Network节点对：");
		WriteBoldInfoI(testItem.get(5), notWorklistNum + "对");
		WriteNormalInfoWithoutEnterS(testItem.get(5), "错误的XPath路径有：");
		WriteBoldInfoI(testItem.get(5), errorXpath.size() + "个");
		if (errorXpath.size() > 0) {
			WriteNormalInfoS(testItem.get(5), "错误的XPath路径为：");
			for (int i = 0; i < errorXpath.size(); i++) {
				String errorXpath = this.errorXpath.get(i);
				WriteError1Info(testItem.get(5), errorXpath);
			}
		}
		WriteTestItem2(testItem.get(5), "TestStation的XPath路径检测");
		WriteNormalInfoWithoutEnterS(testItem.get(5), "待检测的XPath路径个数为：");
		WriteBoldInfoI(testItem.get(5), Integer.toString(textPathNum));
		WriteTestItem2(testItem.get(5), "XPath格式正确性检测");
		WriteNormalInfoWithoutEnterS(testItem.get(5), "标准的XPath路径个数为：");
		WriteBoldInfoI(testItem.get(5), standardXpath.size() + "");
		WriteNormalInfoWithoutEnterS(testItem.get(5), "不标准的XPath路径个数为：");
		WriteBoldInfoI(testItem.get(5), unStandardXpath.size() + "");

		if (unStandardXpath.size() > 0) {
			WriteNormalInfoS(testItem.get(5), "不标准的XPath路径为：");
			for (int i = 0; i < unStandardXpath.size(); i++) {
				String unstandardXpath = unStandardXpath.get(i);
				WriteError1Info(testItem.get(5), unstandardXpath);
			}
		}

	}

	/*
	 * private boolean IsInstType(String str) { // TODO 自动生成的方法存根 boolean b =
	 * false; if (!str.equals("")) { String[] dirs = str.split("/"); if(dirs[1]
	 * == null){ return false; } String[] dir = dirs[1].split(":");
	 * 
	 * if (dir.length == 1) { if (dir[0].equals(StringUtil.instrument)) { b =
	 * true; } } else { if (dir[1].equals(StringUtil.instrument)) { b = true; }
	 * } }
	 * 
	 * return b; }
	 */

	/*
	 * private boolean IsStationType(String str) { // TODO 自动生成的方法存根 boolean b =
	 * false; if (!str.equals("")) { String[] dirs = str.split("/"); if(dirs[1]
	 * == null){ return false; } String[] dir = dirs[1].split(":");
	 * 
	 * if (dir.length == 1) { if (dir[0].equals(station)) { b = true; } } else {
	 * if (dir[1].equals(station)) { b = true; } } }
	 * 
	 * return b; }
	 */

	private void checkNetWorkList() {
		System.out.println("####################check#####################");
		// error += "#####################XPath######################\n";
		// writeInfo("#####################XPath######################");
		WriteTestItem2(testItem.get(5), "XPath路径引用正确及匹配性检查");
		for (Iterator<String> iterator = standardXpath.iterator(); iterator.hasNext();) {
			String netWorkNodes = iterator.next();
			if (getXPathType(netWorkNodes).equals(StringUtil.station)) {
				checkPath(netWorkNodes);
			}
		}
		WriteNormalInfoWithoutEnterS(testItem.get(5), "标准的XPath路径中可达路径的个数为：");
		WriteBoldInfoI(testItem.get(5), (standardXpath.size() - unExistXpath.size()) + "");
		WriteNormalInfoWithoutEnterS(testItem.get(5), "标准的XPath路径中不可达路径的个数为：");
		WriteBoldInfoI(testItem.get(5), unExistXpath.size() + "");
		if (unExistXpath.size() > 0) {
			WriteNormalInfoS(testItem.get(5), "标准的XPath路径中不可达路径如下所示：");
			for (int i = 0; i < unExistXpath.size(); i++) {
				String unstandardXpath = unExistXpath.get(i);
				WriteError2Info(testItem.get(5), unstandardXpath);
			}
		}

	}

	private void checkPath(String path) {
		String[] dirs = path.split("/");
		String[] dir = dirs[1].split(":");
		if (dir.length == 1) {
			if (dir[0].equals(station)) {
				checkNetWorkListInFile(path, FileType.station);
			} else {
				System.out.println(path + " is not exist!");
				// error += (path + " is not exist!\n");
				// writeError(path + " is not exist!");
				unExistXpath.add(path);
			}
		} else {
			if (dir[1].equals(station)) {
				checkNetWorkListInFile(path, FileType.station);
			} else {
				System.out.println(path + " is not exist!");
				// error += (path + " is not exist!\n");
				// writeError(path + " is not exist!");
				unExistXpath.add(path);
			}
		}
	}

	private void checkNetWorkListInFile(String path, FileType fileType) {
		// String dir = "";
		List<Element> list = null;
		switch (fileType) {
		case adapter:
			/*
			 * if (adapterDocument != null) { list =
			 * adapterDocument.selectNodes(path); } else {
			 * 
			 * }
			 */
			break;
		case UUT:
			/*
			 * if (UUTDocument != null) { list = UUTDocument.selectNodes(path);
			 * } else {
			 * 
			 * }
			 */
			break;
		case station:
			if (stationDocument != null) {
				try {
					list = stationDocument.selectNodes(path);
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("############" + fileType.toString() + "#############");
					System.out.println(path + " is not exist!");
					// error += (path + " is not exist!\n");
					// writeError(path + " is not exist!");
					unExistXpath.add(path);
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
			} else {
				// TODO link check

			}
		} else {

		}
	}

	/*
	 * private boolean matchXPath(String XPath) { Pattern pattern =
	 * Pattern.compile(regEX); Matcher matcher = pattern.matcher(XPath); return
	 * matcher.matches(); }
	 */

	private void write2TXT() {
		File dataFlie = new File("stationError.txt");
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
