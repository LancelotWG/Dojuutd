package com.ieee.atml.test.assist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.CDATA;
import org.dom4j.Comment;
import org.dom4j.Document;
import org.dom4j.DocumentType;
import org.dom4j.Element;
import org.dom4j.Entity;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.ProcessingInstruction;
import org.dom4j.Text;
import org.dom4j.VisitorSupport;

import com.ieee.atml.test.resource.ConnectorClass;
import com.ieee.atml.test.resource.ConnectorPinClass;
import com.ieee.atml.test.resource.PinClass;
import com.ieee.atml.test.resource.PortClass;

public class UUTDVisitor extends VisitorSupport {

	// 变量声明
	int conpinNum;
	String uuid;
	List<String> connectorPins = new ArrayList();
	List<String> connectorPins1 = new ArrayList();
	List<String> cPinpath = new ArrayList();
	List<String> cPinpath1 = new ArrayList();
	List<String> connectorIDs = new ArrayList();

	List<PortClass> portList = new ArrayList();

	HashMap<String, Element> portMap = new HashMap<String, Element>();

	public HashMap<String, Element> getPortMap() {
		return portMap;
	}

	public List<PortClass> getPortList() {
		return portList;
	}

	public List<ConnectorClass> getConnectorList() {
		return connectorList;
	}

	List<ConnectorClass> connectorList = new ArrayList();

	public UUTDVisitor() {
		super();
		// TODO 自动生成的构造函数存根
		this.conpinNum = 0;
		this.uuid = null;

	}

	public int getConpinNum() {
		return conpinNum;
	}

	@Override
	public void visit(Document arg0) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void visit(DocumentType arg0) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void visit(Element arg0) {
		// TODO 自动生成的方法存根

		// 获得uuid
		if (arg0.getName().equals("UUTDescription")) {
			uuid = arg0.attributeValue("uuid");
		}
		// 访问Port接口获得其管教connectorpin
		if (arg0.getName().equals("Port") && arg0.getNamespacePrefix().equals("c")) {
			PortClass tempPort = new PortClass();
			tempPort.setPortName(arg0.attributeValue("name"));
			tempPort.setSignalType(arg0.attributeValue("type"));
			tempPort.setSignalFlow(arg0.attributeValue("direction"));
			List<Node> nodes = arg0.selectNodes("c:ConnectorPins/c:ConnectorPin");
			for (Iterator iterator = nodes.iterator(); iterator.hasNext();) {
				Node node = (Node) iterator.next();
				tempPort.AddConnector(CreateConnectorPinInstance(node));
			}
			portList.add(tempPort);
		}

		if (arg0.getName().equals("ConnectorPin") && arg0.getNamespacePrefix().equals("c")) {
			conpinNum++;
			// 从connectorPin节点中获得pinID和connectorID属性构成对
			connectorPins1.add(arg0.attributeValue("connectorID") + ":" + arg0.attributeValue("pinID"));
			cPinpath1.add(arg0.getPath() + "[@pinID='" + arg0.attributeValue("pinID") + "';connectorID='"
					+ arg0.attributeValue("connectorID") + "']");
			portMap.put(arg0.attributeValue("connectorID") + "-" + arg0.attributeValue("pinID"),
					arg0.getParent().getParent());
		}

		// 获得connector中所包含的Pin信息
		if (arg0.getName().equals("Connector") && arg0.getNamespacePrefix().equals("c")) {
			connectorIDs.add(arg0.attributeValue("ID"));

			ConnectorClass tempConnector = new ConnectorClass();
			tempConnector.setConID(arg0.attributeValue("ID"));
			List<Node> nodes = arg0.selectNodes("c:Pins/c:Pin");
			for (Iterator iterator = nodes.iterator(); iterator.hasNext();) {
				Node node = (Node) iterator.next();
				tempConnector.AddConnector(CreatePin(node));
			}
			connectorList.add(tempConnector);
		}

		// 获得Pin的ID及所从属的Connectors的ID
		if (arg0.getName().equals("Pin") && arg0.getNamespacePrefix().equals("c")) {

			Element connectorElt = arg0.getParent().getParent();
			connectorPins.add(connectorElt.attributeValue("ID") + ":" + arg0.attributeValue("ID"));
			cPinpath.add(arg0.getParent().getParent().getPath() + "[@ID='"
					+ arg0.getParent().getParent().attributeValue("ID") + "']" + "/c:Pins/c:Pin[@ID='"
					+ arg0.attributeValue("ID") + "']");
			// System.out.println(tempstr);
		}

		// System.out.println("parent:
		// "+arg0.getParent().getName()+"\n"+"path:"+arg0.getPath());
		// }
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

	@Override
	public void visit(Attribute arg0) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void visit(CDATA arg0) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void visit(Comment arg0) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void visit(Entity arg0) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void visit(Namespace arg0) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void visit(ProcessingInstruction arg0) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void visit(Text arg0) {
		// TODO 自动生成的方法存根

	}

	public String getUuid() {
		return uuid;
	}

	public List<String> getConnectorPins() {
		return connectorPins;
	}

	public List<String> getConnectorPins1() {
		return connectorPins1;
	}

	public List<String> getcPinpath() {
		return cPinpath;
	}

	public List<String> getcPinpath1() {
		return cPinpath1;
	}

	public List<String> getConnectorIDs() {
		return connectorIDs;
	}

	public ConnectorPinClass CreateConnectorPinInstance(Node node) {
		ConnectorPinClass cpc = new ConnectorPinClass();
		if (node != null) {
			cpc.setPinID(node.valueOf("@pinID"));
			cpc.setConnectorID(node.valueOf("@connectorID"));
			cpc.setXpath(node.getPath() + "[@pinID='" + node.valueOf("@pinID") + "';connectorID='"
					+ node.valueOf("@connectorID") + "']");
		}
		return cpc;
	}

}
