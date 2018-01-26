package com.ieee.atml.test.resource;
import java.util.ArrayList;
import java.util.List;

public class PortClass {
	String portName;
	List<ConnectorPinClass> connectorPins=new ArrayList();
	String signalType;
	public String getSignalType() {
		return signalType;
	}
	public void setSignalType(String signalType) {
		this.signalType = signalType;
	}
	String signalFlow;
	
	public String getSignalFlow() {
		return signalFlow;
	}
	public void setSignalFlow(String signalFlow) {
		this.signalFlow = signalFlow;
	}
	public String getPortName() {
		return portName;
	}
	public void setPortName(String portName) {
		this.portName = portName;
	}
	public void AddConnector(ConnectorPinClass cp)
	{
		this.connectorPins.add(cp);
	}
	public List<ConnectorPinClass> getConnectorPins() {
		return connectorPins;
	}
}
