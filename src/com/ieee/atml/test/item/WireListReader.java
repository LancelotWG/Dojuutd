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

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.ieee.atml.info.InfoWrite;
import com.ieee.atml.test.assist.*;
import com.ieee.atml.test.resource.*;
import com.ieee.atml.util.StringUtil;
import com.ieee.atml.util.XPathStandard;

public class WireListReader extends InfoWrite implements XPathStandard{
	
	private org.dom4j.Document wireListDocument;
	private org.dom4j.Document uutDocument;
	private org.dom4j.Document stationDocument;
	private org.dom4j.Document adapterDocument;
	
	private List<String> standardXpath = new ArrayList<String>();
	private List<String> unStandardXpath = new ArrayList<String>();
	private List<String> unExistXpath = new ArrayList<String>();
	
	
	
	/************************************************测试***************************************************************/
	public static void main(String[] args) {
		String wireListPath = "C:\\Users\\王宇磊\\eclipse-workspace\\HHH\\phase2\\DemoWireListV9.xml";
		String uutPath = "C:\\Users\\王宇磊\\eclipse-workspace\\HHH\\测评UUT-V8\\test\\UUTDescriptionConvert_1_测评UUT_5_测评UUT_5.xml";
		String stationPath = "C:\\Users\\王宇磊\\eclipse-workspace\\HHH\\测评UUT-V8\\test\\TestStationDescriptionConvert_1_测评UUT_5_5.xml";
		String adapterPath = "C:\\Users\\王宇磊\\eclipse-workspace\\HHH\\测评UUT-V8\\test\\TestAdapterConvert_1_测评UUT_5_测评接口适配器1_11.xml";
		WireListReader test = new WireListReader();
		test.Virtualmain(wireListPath, uutPath, /*stationPath,*/ adapterPath);
	}
	/************************************************测试***************************************************************/
	
	public WireListReader() {
		//testItem.add("文件匹配检测");
		testItem.add("XPath格式正确性检测");
		testItem.add("XPath路径引用正确及匹配性检查");
		fileName = "WireListTest";
	}
	
	public void Virtualmain(String wirelistpath, String uutpath, /*String stationpath,*/ String adapterpath) {
		wireListDocument = readXML(wirelistpath);
		uutDocument = readXML(uutpath);
		/*stationDocument = readXML(stationpath);*/
		adapterDocument = readXML(adapterpath);
		
		initInfoMap();
		WriteTxtAndHTMLOnly("检测完成，结果如下:");
		
		checkXpath();
		
		write2TXT();
		infoWriteWord(fileName);
	}
	
	private void checkXpath() {
		//WireListVisitor wireListVisitor = new WireListVisitor();
		//UUTDVisitor uutVisitor = new UUTDVisitor();
		//TestStationVisitor stationVisitor = new TestStationVisitor();
		//AdapterVisitor adapterVisitor = new AdapterVisitor();

		//wireListDocument.accept(wireListVisitor);
		//uutDocument.accept(uutVisitor);
		//stationDocument.accept(stationVisitor);
		//adapterDocument.accept(adapterVisitor);
		if(wireListDocument == null) {
			return;
		}
		Element root = wireListDocument.getRootElement();
		//
		WriteTestItem1(testItem.get(0)); 
		WriteNormalInfoWithoutEnter(testItem.get(0),"待检测的Xpath个数为：");
		int xpathNum = 0;
		//wirelist
		List<Element> wireList = root.elements("WireList");
		for(Iterator<Element> iteratorWireList = wireList.iterator();iteratorWireList.hasNext();) {
			Element tempWireList = iteratorWireList.next();
			List<Element> wire = tempWireList.elements("Wire");
			for(Iterator<Element> iteratorWire = wire.iterator();iteratorWire.hasNext();) {
				Element tempWire = iteratorWire.next();
				List<Element> node = getElementWithNamespace(tempWire,"Node","hc");
				for(Iterator<Element> iteratorNode = node.iterator();iteratorNode.hasNext();) {
					Element tempNode = iteratorNode.next();
					String path = tempNode.element("Path").getText();
					
					/*if (!matchXPath(path)) {
						//System.out.println(path + " is not standard XPath!");
						unStandardXpath.add(path);
					} else {
						int index = path.indexOf("/[@");
						if (index != -1) {
							path = path.replace("/[", "[");
							System.out.println(path);
						}
						standardXpath.add(path);
					}*/
					int index = path.indexOf("/[@");
					if (index != -1) {
						path = path.replace("/[", "[");
						System.out.println(path);
					}
					if (!matchXPath(path)) {
						System.out.println(path + " is not standard XPath!");
						unStandardXpath.add(path);
					} else {
						standardXpath.add(path);
					}
					xpathNum++;
				}
			}
		}
		
		List<Element> testWireList = root.elements("TestWireList");
		for(Iterator<Element> iteratorTestWireList = testWireList.iterator();iteratorTestWireList.hasNext();) {
			Element tempTestWireList = iteratorTestWireList.next();
			List<Element> assetWireList = tempTestWireList.elements("AssetWireList");
			for(Iterator<Element> iteratorAssetWireList = assetWireList.iterator();iteratorAssetWireList.hasNext();) {
				Element tempAssetWireList = iteratorAssetWireList.next();
				List<Element> wire = tempAssetWireList.elements("Wire");
				for(Iterator<Element> iteratorWire = wire.iterator();iteratorWire.hasNext();) {
					Element tempWire = iteratorWire.next();
					List<Element> node = getElementWithNamespace(tempWire,"Node","hc");
					for(Iterator<Element> iteratorNode = node.iterator();iteratorNode.hasNext();) {
						Element tempNode = iteratorNode.next();
						String path = tempNode.element("Path").getText();
						int index = path.indexOf("/[@");
						if (index != -1) {
							path = path.replace("/[", "[");
							System.out.println(path);
						}
						if (!matchXPath(path)) {
							System.out.println(path + " is not standard XPath!");
							unStandardXpath.add(path);
						} else {
							standardXpath.add(path);
						}
						
						/*if (!matchXPath(path)) {
							System.out.println(path + " is not standard XPath!");
							unStandardXpath.add(path);
						} else {
							int index = path.indexOf("/[@");
							if (index != -1) {
								path = path.replace("/[", "[");
								System.out.println(path);
							}
							standardXpath.add(path);
						}*/
						xpathNum++;
					}
				}
			}
		}
		
		WriteBoldInfo(testItem.get(0),xpathNum);
		WriteNormalInfoWithoutEnter(testItem.get(0),"标准的XPath路径个数为：");
		WriteBoldInfo(testItem.get(0),standardXpath.size());
		WriteNormalInfoWithoutEnter(testItem.get(0), "不标准的XPath路径个数为：");
		WriteBoldInfo(testItem.get(0),unStandardXpath.size());
		if (unStandardXpath.size() > 0) {
			WriteNormalInfo(testItem.get(0), "不标准的XPath路径为：");
			for (int i = 0; i < unStandardXpath.size(); i++) {
				String unstandardXpath = unStandardXpath.get(i);
				WriteErrorInfo(testItem.get(0), unstandardXpath);
			}
		}
		
		//
		WriteTestItem1(testItem.get(1)); 
		for (int j = 0; j < standardXpath.size(); j++) {
			String path = standardXpath.get(j);
			checkPath(path);
		}
		WriteNormalInfoWithoutEnter(testItem.get(1), "标准的XPath路径中可达路径的个数为：");
		WriteBoldInfo(testItem.get(1), (standardXpath.size() - unExistXpath.size()) + "");
		WriteNormalInfoWithoutEnter(testItem.get(1), "标准的XPath路径中不可达路径的个数为：");
		WriteBoldInfo(testItem.get(1), unExistXpath.size() + "");
		if (unExistXpath.size() > 0) {
			WriteNormalInfo(testItem.get(1), "标准的XPath路径中不可达路径如下所示：");
			for (int i = 0; i < unExistXpath.size(); i++) {
				String unstandardXpath = unExistXpath.get(i);
				WriteErrorInfo(testItem.get(1), unstandardXpath);
			}
		}
	}
	
	/*
	*private String creatName(String portname) {
	*	String tempName = "[@name=\'" + portname + "\']";
	*	return tempName;
	*}
	*/
	
	private org.dom4j.Document readXML(String fileDir) {
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
		}
		return document;
	}
	
	private List<Element> getElementWithNamespace(Element root, String tagName, String namespace){
		List<Element> list = new ArrayList<Element>();
		List<Element> tagList = root.elements(tagName);
		for(Element element:tagList) {
			if(element.getNamespacePrefix().equals(namespace)) {
				list.add(element);
			}
		}
		return list;
	}
	
	private boolean checkPath(String path) {
		String[] dirs = path.split("/");
		String[] dir = dirs[1].split(":");
		if (dir.length == 1) {
			if (dir[0].equals(StringUtil.adapter)) {
				return checkNetWorkListInFile(path, FileType.adapter);
			} else if (dir[0].equals(StringUtil.UUT)) {
				return checkNetWorkListInFile(path, FileType.UUT);
			} else if (dir[0].equals(StringUtil.station)) {
				return checkNetWorkListInFile(path, FileType.station);
			} else {
				System.out.println(path + " is not exist!");
				unExistXpath.add(path);
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
				unExistXpath.add(path);
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
			if (uutDocument != null) {
				try {
					list = uutDocument.selectNodes(path);
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
		/*case station:
			if (stationDocument != null) {
				try {
					list = stationDocument.selectNodes(path);
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("############" + fileType.toString() + "#############");
					System.out.println(e);
					System.out.println(path + " is not standard XPath!");
					unExistXpath.add(path);
				}
			} else {

			}
			break;*/
		default:
			break;
		}
		if (list != null) {
			if (list.size() == 0) {
				System.out.println("############" + fileType.toString() + "#############");
				System.out.println(path + " is not exist!");
				unExistXpath.add(path);
			} else {
				// TODO link check
				System.out.println("********************" + path + "*************************");
				return true;
			}
		} else {

		}
		return false;
	}
	
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
	public String getInfoHTML() {
		// TODO 自动生成的方法存根
		return infoHTML;
	}
}
