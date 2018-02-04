package com.ieee.atml.test.assist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.VisitorSupport;

import com.ieee.atml.test.resource.ConnectorClass;
import com.ieee.atml.test.resource.ConnectorPinClass;
import com.ieee.atml.test.resource.PinClass;
import com.ieee.atml.test.resource.PortClass;

public class AdapterVisitor extends VisitorSupport {
	List<PortClass> portList = new ArrayList();
	List<String> connectorPins = new ArrayList();
	List<String> connectorPins1 = new ArrayList();

	List<String> cPinpath = new ArrayList();
	
	String uuid;

	public List<String> getcPinpath() {
		return cPinpath;
	}

	public List<ConnectorClass> getConnectorList() {
		return connectorList;
	}

	public List<PortClass> getPortList() {
		return portList;
	}

	List<ConnectorClass> connectorList = new ArrayList();

	List<String> cPinpath1 = new ArrayList();

	public List<String> getcPinpath1() {
		return cPinpath1;
	}

	public List<String> getConnectorPins() {
		return connectorPins;
	}

	public List<String> getConnectorPins1() {
		return connectorPins1;
	}

	@Override
	public void visit(Element node) {
		// TODO 自动生成的方法存根
		
		// 获得uuid
		if (node.getName().equals("TestAdapterDescription")) {
			uuid = node.attributeValue("uuid");
		}
		super.visit(node);
		if (node.getName().equals("Port") && node.getNamespacePrefix().equals("c")) {
			Element elt = node.getParent().getParent().getParent();
			if (elt.getName().equals("TestAdapterDescription") && elt.getNamespacePrefix().equals("ta")) {
				// System.out.println(elt.getPath());
				PortClass tempPort = new PortClass();
				tempPort.setPortName(node.attributeValue("name"));

				List<Element> cpsList = node.elements("ConnectorPins");
				if (!cpsList.isEmpty()) {
					for (Iterator iterator = cpsList.iterator(); iterator.hasNext();) {
						Element cpsElt = (Element) iterator.next();
						List<Element> cpList = cpsElt.elements("ConnectorPin");

						for (Iterator iterator2 = cpList.iterator(); iterator2.hasNext();) {
							Element cpElt = (Element) iterator2.next();
							ConnectorPinClass tempCp = new ConnectorPinClass();
							tempCp.setPinID(cpElt.attributeValue("pinID"));
							tempCp.setConnectorID(cpElt.attributeValue("connectorID"));

							tempPort.AddConnector(tempCp);
						}
					}
					portList.add(tempPort);
				}

			}
		}

		if (node.getName().equals("Connector") && node.getNamespacePrefix().equals("c")) {
			// connectorIDs.add(node.attributeValue("ID"));

			ConnectorClass tempConnector = new ConnectorClass();
			tempConnector.setConID(node.attributeValue("ID"));
			List<Node> nodes = node.selectNodes("c:Pins/c:Pin");
			for (Iterator iterator = nodes.iterator(); iterator.hasNext();) {
				Node nodes1 = (Node) iterator.next();
				tempConnector.AddConnector(CreatePin(nodes1));
			}
			connectorList.add(tempConnector);
		}

		if (node.getName().equals("ConnectorPin") && node.getNamespacePrefix().equals("c")) {
			// conpinNum++;
			// 从connectorPin节点中获得pinID和connectorID属性构成对
			connectorPins1.add(node.attributeValue("connectorID") + ":" + node.attributeValue("pinID"));
			cPinpath1.add(node.getPath() + "[@pinID='" + node.attributeValue("pinID") + "';connectorID='"
					+ node.attributeValue("connectorID") + "']");
			// portMap.put(arg0.attributeValue("connectorID")+"-"+arg0.attributeValue("pinID"),
			// arg0.getParent().getParent());
		}

		// 获得Pin的ID及所从属的Connectors的ID
		if (node.getName().equals("Pin") && node.getNamespacePrefix().equals("c")) {

			Element connectorElt = node.getParent().getParent();
			connectorPins.add(connectorElt.attributeValue("ID") + ":" + node.attributeValue("ID"));
			cPinpath.add(node.getParent().getParent().getPath() + "[@ID='"
					+ node.getParent().getParent().attributeValue("ID") + "']" + "/c:Pins/c:Pin[@ID='"
					+ node.attributeValue("ID") + "']");
			// System.out.println(tempstr);
		}
	}

	private PinClass CreatePin(Node node) {
		// TODO 自动生成的方法存根
		PinClass pc = new PinClass();
		if (node != null) {
			pc.setPinID(node.valueOf("@ID"));
			pc.setXpath(node.getParent().getParent().getPath() + "[@ID='"
					+ node.getParent().getParent().attributeValue("ID") + "']" + "/c:Pins/c:Pin[@ID='"
					+ node.valueOf("@ID") + "']");
		}

		return pc;
	}
	
	public String getUUID() {
		return uuid;
	}
}
