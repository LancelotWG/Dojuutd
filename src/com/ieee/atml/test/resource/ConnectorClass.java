package com.ieee.atml.test.resource;

import java.util.ArrayList;
import java.util.List;

public class ConnectorClass {
	String conID;
	List<PinClass> cPins=new ArrayList();
	public String getConID() {
		return conID;
	}
	public List<PinClass> getcPins() {
		return cPins;
	}
	public void setConID(String conID) {
		this.conID = conID;
	}
	public void AddConnector(PinClass pc)
	{
		this.cPins.add(pc);
	}

}
