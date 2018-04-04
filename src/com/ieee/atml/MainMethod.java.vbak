package com.ieee.atml;


import com.ieee.atml.test.item.TestSignalReader;
import com.ieee.atml.test.item.TestStationReader;
import com.ieee.atml.test.item.UUTDReader;
import com.ieee.atml.ui.ATMLTest;

public class MainMethod {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		//String xmlpath="DemoSignalLibrary.xml";
		//HandleUUTD(xmlpath);
		//HandleAWG(); 
		//HandleTS(xmlpath);
		//HandleTSIG(xmlpath);
		new ATMLTest().init();
	}


	private static void HandleTSIG(String xmlpath) {
		// TODO 自动生成的方法存根
		TestSignalReader tsigReader=new TestSignalReader();
		tsigReader.VirtualMain(xmlpath,"","");
	}


	private static void HandleTS(String xmlpath) {
		// TODO 自动生成的方法存根
		TestStationReader tsReader=new TestStationReader();
		//tsReader.VirtualMain(xmlpath);
	}


	/*private static void HandleAWG() {
		// TODO 自动生成的方法存根
		String xmlpath="DCPS1_demo.xml";
		//String xmlpath="student.xml";
		AWGReader areader=new AWGReader();
		areader.VirtualMain(xmlpath);
	}*/

	private static void HandleUUTD(String xmlpath) {
		// TODO 自动生成的方法存根
		
		UUTDReader ureader=new UUTDReader();
		ureader.VirtualMain(xmlpath);
	}

}
