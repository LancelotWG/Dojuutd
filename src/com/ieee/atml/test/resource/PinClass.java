package com.ieee.atml.test.resource;

public class PinClass {
	String pinID;
	String xpath;
	String signalType;
	public String getSignalType() {
		return signalType;
	}
	public void setSignalType(String signalType) {
		this.signalType = signalType;
	}
	String signalFlow;
	public PinClass(){
		super();
	}
	public PinClass(String signalType, String signalFlow){
		this.signalType = signalType;
		this.signalFlow = signalFlow;
	}
	public String getSignalFlow() {
		return signalFlow;
	}
	public void setSignalFlow(String signalFlow) {
		this.signalFlow = signalFlow;
	}
	public String getPinID() {
		return pinID;
	}
	public void setPinID(String pinID) {
		this.pinID = pinID;
	}
	public String getXpath() {
		return xpath;
	}
	public void setXpath(String xpath) {
		this.xpath = xpath;
	}
	

}
