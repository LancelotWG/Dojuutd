package com.ieee.atml.test.item;

import java.io.File;
import java.util.*;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import com.ieee.atml.info.InfoWrite;
import com.ieee.atml.test.assist.*;
import com.ieee.atml.test.resource.*;
import com.ieee.atml.util.StringUtil;

public class WireListReader extends InfoWrite{
	
	private org.dom4j.Document wireListDocument;
	private org.dom4j.Document uutDocument;
	private org.dom4j.Document stationDocument;
	private org.dom4j.Document adapterDocument;
	
	private HashMap<String,List<String>> matchList = new HashMap<String,List<String>>();
	private HashMap<String,List<String>> unMatchList = new HashMap<String,List<String>>();
	
	/************************************************测试***************************************************************/
	public static void main(String[] args) {
		String wireListPath = "C:\\Users\\王宇磊\\eclipse-workspace\\HHH\\phase2\\DemoWireListV9.xml";
		String uutPath = "C:\\Users\\王宇磊\\eclipse-workspace\\HHH\\测评UUT-V8\\test\\UUTDescriptionConvert_1_测评UUT_5_测评UUT_5.xml";
		String stationPath = "C:\\Users\\王宇磊\\eclipse-workspace\\HHH\\测评UUT-V8\\test\\TestStationDescriptionConvert_1_测评UUT_5_5.xml";
		String adapterPath = "C:\\Users\\王宇磊\\eclipse-workspace\\HHH\\测评UUT-V8\\test\\TestAdapterConvert_1_测评UUT_5_测评接口适配器1_11.xml";
		WireListReader test = new WireListReader();
		test.Virtualmain(wireListPath, uutPath, stationPath, adapterPath);
	}
	/************************************************测试***************************************************************/	
	public void Virtualmain(String wirelistpath, String uutpath, String stationpath, String adapterpath) {
		wireListDocument = readXML(wirelistpath);
		uutDocument = readXML(uutpath);
		stationDocument = readXML(stationpath);
		adapterDocument = readXML(adapterpath);
		
		checkPath();
		
		if(matchList.size() != 0) {
			System.out.println("***************************MatchList************************************");
			Iterator<Map.Entry<String, List<String>>> iteratorMatch = matchList.entrySet().iterator();
			while(iteratorMatch.hasNext()) {
				Map.Entry<String, List<String>> tempMatch = iteratorMatch.next();
				System.out.println("***************************" + tempMatch.getKey() + "************************************");
				List<String> tempList = tempMatch.getValue();
				Iterator<String> iteratorPath = tempList.iterator();
				while(iteratorPath.hasNext()) {
					String tempPath = iteratorPath.next();
					System.out.println(tempPath);
				}
			}
		}
		
		if(unMatchList.size() != 0) {
			System.out.println("***************************UnMatchList************************************");
			Iterator<Map.Entry<String, List<String>>> iteratorUnMatch = unMatchList.entrySet().iterator();
			while(iteratorUnMatch.hasNext()) {
				Map.Entry<String, List<String>> tempUnMatch = iteratorUnMatch.next();
				System.out.println("***************************" + tempUnMatch.getKey() + "************************************");
				List<String> tempList = tempUnMatch.getValue();
				Iterator<String> iteratorPath = tempList.iterator();
				while(iteratorPath.hasNext()) {
					String tempPath = iteratorPath.next();
					System.out.println(tempPath);
				}
			}
		}
	}
	
	private void checkPath() {
		WireListVisitor wireListVisitor = new WireListVisitor();
		UUTDVisitor uutVisitor = new UUTDVisitor();
		TestStationVisitor stationVisitor = new TestStationVisitor();
		AdapterVisitor adapterVisitor = new AdapterVisitor();

		wireListDocument.accept(wireListVisitor);
		uutDocument.accept(uutVisitor);
		stationDocument.accept(stationVisitor);
		adapterDocument.accept(adapterVisitor);
		//
		List<String> uuidList = wireListVisitor.getUUIDList();
		/*
		*Iterator<String> iterator = uuidList.iterator();
		*while(iterator.hasNext())
			System.out.println(iterator.next());
		*/
		//
		List<String> taPortList = wireListVisitor.getTAPortList();
		HashMap<String,String> taPathList = wireListVisitor.getTAPathList();
		/*if(taPortList.size() == 0) 
			System.out.println("null");
		Iterator<String> iterator = taPortList.iterator();
		while(iterator.hasNext())
			System.out.println(iterator.next());
		*/
		//
		List<String> tsPortList = wireListVisitor.getTSPortList();
		HashMap<String,String> tsPathList = wireListVisitor.getTSPathList();
		//
		List<String> uutPortList = wireListVisitor.getUUTPortList();
		HashMap<String,String> uutPathList = wireListVisitor.getUUTPathList();
		
		if(wireListVisitor.getUUIDList().contains(uutVisitor.getUuid())){
			List<String> tempMatch = new ArrayList<String>();
			List<String> tempUnMatch = new ArrayList<String>();
			List<String> tempNameList = new ArrayList<String>();
			List<PortClass> portList = uutVisitor.getPortList();
			//获取uut中所有Port name 并用一定格式保存
			Iterator<PortClass> iteratorPort = portList.iterator();
			while(iteratorPort.hasNext()) {
				PortClass tempPort = iteratorPort.next();
				String tempName = creatName(tempPort.getPortName());  
				tempNameList.add(tempName);
			}
			//check
			Iterator<String> iteratorName = uutPortList.iterator();
			while(iteratorName.hasNext()) {
				String tempName = iteratorName.next();
				if(tempNameList.contains(tempName)) {
					tempMatch.add(uutPathList.get(tempName));
				}else {
					tempUnMatch.add(uutPathList.get(tempName));
				}	
			}
			//
			matchList.put(StringUtil.UUT, tempMatch);
			unMatchList.put(StringUtil.UUT, tempUnMatch);
		}
		
		if(wireListVisitor.getUUIDList().contains(adapterVisitor.getUUID())){
			List<String> tempMatch = new ArrayList<String>();
			List<String> tempUnMatch = new ArrayList<String>();
			List<String> tempNameList = new ArrayList<String>();
			List<PortClass> portList = adapterVisitor.getPortList();
			//获取adapter中所有Port name 并用一定格式保存
			Iterator<PortClass> iteratorPort = portList.iterator();
			while(iteratorPort.hasNext()) {
				PortClass tempPort = iteratorPort.next();
				String tempName = creatName(tempPort.getPortName());
				tempNameList.add(tempName);
			}
			//check
			Iterator<String> iteratorName = taPortList.iterator();
			while(iteratorName.hasNext()) {
				String tempName = iteratorName.next();
				if(tempNameList.contains(tempName)) {
					tempMatch.add(taPathList.get(tempName));
				}else {
					tempUnMatch.add(taPathList.get(tempName));
				}	
			}
			//
			matchList.put(StringUtil.adapter, tempMatch);
			unMatchList.put(StringUtil.adapter, tempUnMatch);
		}
			
		if(wireListVisitor.getUUIDList().contains(stationVisitor.getUUID())){
			List<String> tempMatch = new ArrayList<String>();
			List<String> tempUnMatch = new ArrayList<String>();
			List<String> tempNameList = new ArrayList<String>();
			List<PortClass> portList = stationVisitor.getPortList();
			//获取station中所有Port name 并用一定格式保存
			Iterator<PortClass> iteratorPort = portList.iterator();
			while(iteratorPort.hasNext()) {
				PortClass tempPort = iteratorPort.next();
				String tempName = creatName(tempPort.getPortName());
				tempNameList.add(tempName);
			}
			//check
			Iterator<String> iteratorName = tsPortList.iterator();
			while(iteratorName.hasNext()) {
				String tempName = iteratorName.next();
				if(tempNameList.contains(tempName)) {
					tempMatch.add(tsPathList.get(tempName));
				}else {
					tempUnMatch.add(tsPathList.get(tempName));
				}	
			}
			//
			matchList.put(StringUtil.station, tempMatch);
			unMatchList.put(StringUtil.station, tempUnMatch);
		}	
	}
		
	private String creatName(String portname) {
		String tempName = "[@name=\'" + portname + "\']";
		return tempName;
	}
	
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
	
	public String getInfoHTML() {
		return infoHTML;
	}

}
