package com.ieee.atml.test.assist;

import java.util.*;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;
import com.ieee.atml.util.StringUtil;

public class WireListVisitor extends VisitorSupport implements StringUtil{

	String uuid;
	
	List<String> uuidList = new ArrayList<String>();
	List<String> taPortList = new ArrayList<String>();
	List<String> uutPortList = new ArrayList<String>();
	List<String> tsPortList = new ArrayList<String>();
	
	HashMap<String,String> taPathList = new HashMap<String,String>();
	HashMap<String,String> tsPathList = new HashMap<String,String>();
	HashMap<String,String> uutPathList = new HashMap<String,String>();
	
	public WireListVisitor() {
		super();
		//
		this.uuid = null;
	}
	
	public String getUUID() {
		return uuid;
	}
	
	public List<String> getUUIDList() {
		return uuidList;
	}
	
	public List<String> getTAPortList(){
		return taPortList;
	}
	
	public HashMap<String,String> getTAPathList(){
		return taPathList;
	}
	
	public List<String> getUUTPortList(){
		return uutPortList;
	}
	
	public HashMap<String,String> getUUTPathList(){
		return uutPathList;
	}
	
	public List<String> getTSPortList(){
		return tsPortList;
	}
	
	public HashMap<String,String> getTSPathList(){
		return tsPathList;
	}
	
	@Override
	public void visit(Element node) {
		// TODO 自动生成的方法存根
		super.visit(node);
		//uuid
		if(node.getName().equals("WireLists")) {
			uuid = node.attributeValue("uuid");
		}
		
		//
		if(node.getName().equals("Path") && node.getNamespacePrefix().equals("hc") &&
				node.getParent().getParent().getName().equals("Wire")) {
			String path = node.getText();
			//System.out.println(path);
			String[] info = pathReader(path);
			//System.out.println(info);
			if(info != null) {
				if(info[0].equals(StringUtil.adapter)) {
					if(!taPortList.contains(info[1])) {
						taPortList.add(info[1]);
						taPathList.put(info[1],path);
					}		
				}else if(info[0].equals(StringUtil.UUT)) {
					if(!uutPortList.contains(info[1])) {
						uutPortList.add(info[1]);
						uutPathList.put(info[1],path);
					}	
				}else if(info[0].equals(StringUtil.station)) {
					if(!tsPortList.contains(info[1])) {
						tsPortList.add(info[1]);
						tsPathList.put(info[1],path);
					}	
				}
			}
		}
		
		//uuidList
		if(node.getName().equals("Item")){
			uuidList.add(node.attributeValue("uuid"));
		}
	}
	
	String[] pathReader(String path) {
		String[] temp1 = path.split("/");
		//System.out.println(temp1[1]);
		String[] temp2 = temp1[1].split(":");
		//System.out.println(temp2[1]);
		try {
			if(temp2[1].equals(StringUtil.adapter)) {
				String[] info = {StringUtil.adapter, temp1[temp1.length-1]};
				return info;
			}
			if(temp2[1].equals(StringUtil.station)) {
				String[] info = {StringUtil.station, temp1[temp1.length-1]};
				return info;
			}
			if(temp2[1].equals(StringUtil.UUT)) {
				String[] info = {StringUtil.UUT, temp1[temp1.length-1]};
				return info;
			}
			
		}catch(Exception e) {
			//
		}
		String[] info = null;
		return info;
	}
}
