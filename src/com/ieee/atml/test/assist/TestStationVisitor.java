package com.ieee.atml.test.assist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.VisitorSupport;

import com.ieee.atml.test.resource.ConnectorClass;
import com.ieee.atml.test.resource.ConnectorPinClass;
import com.ieee.atml.test.resource.PinClass;
import com.ieee.atml.test.resource.PortClass;
import com.ieee.atml.util.StringUtil;
import com.ieee.atml.util.XPathStandard;

public class TestStationVisitor extends VisitorSupport implements XPathStandard {

	List<PortClass> portList = new ArrayList();
	List<String> switchNames = new ArrayList();
	List<String> instrumentIDs = new ArrayList();

	List<String> instpathlist = new ArrayList();
	List<String> instDocumentId = new ArrayList();

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

	public List<String> getInstDocumentId() {
		return instDocumentId;
	}

	public List<String> getInstpathlist() {
		return instpathlist;
	}

	private HashMap<String, String> instDocIDUUIDMaps = new HashMap<String, String>();

	public HashMap<String, String> getInstDocIDUUIDMaps() {
		return instDocIDUUIDMaps;
	}

	public HashMap<String, ArrayList<String>> getInstDocIDXpathMaps() {
		return instDocIDXpathMaps;
	}

	private HashMap<String, ArrayList<String>> instDocIDXpathMaps = new HashMap<String, ArrayList<String>>();

	// private HashMap<String, ArrayList<String>> errorXpathMaps = new
	// HashMap<String, ArrayList<String>>();

	public List<String> getInstrumentIDs() {
		return instrumentIDs;
	}

	@Override
	public void visit(Element node) {
		// TODO 自动生成的方法存根

		// 获得uuid
		if (node.getName().equals("TestStationDescription")) {
			uuid = node.attributeValue("uuid");
		}
		// 访问Port接口并存储connectorpins信息
		if (node.getName().equals("Port") && node.getNamespacePrefix().equals("c")) {
			Element elt = node.getParent().getParent().getParent();
			if (elt.getName().equals("TestStationDescription") && elt.getNamespacePrefix().equals("ts")) {
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

		// 访问hc:Switch接口
		if (node.getName().equals("Switch") && node.getNamespacePrefix().equals("hc")
				&& node.getParent().getName().equals("Switching")
				&& node.getParent().getNamespacePrefix().equals("te")) {
			switchNames.add(node.attributeValue("name"));
		}
		// 检测仪器的个数和ID
		if (node.getName().equals("Instrument") && node.getNamespacePrefix().equals("ts")
				&& node.getParent().getName().equals("Instruments")
				&& node.getParent().getNamespacePrefix().equals("ts")) {
			instrumentIDs.add(node.attributeValue("ID"));
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

		// 构建仪器docid-uuid map
		if (node.getName().equals("DescriptionDocumentReference") && node.getNamespacePrefix().equals("c")
				&& node.getParent().getName().equals("Instrument")
				&& node.getParent().getNamespacePrefix().equals("ts")) {
			String tempuuid = node.attributeValue("uuid");
			String tempID = node.getParent().attributeValue("ID");
			instDocIDUUIDMaps.put(tempID, tempuuid);

			/*
			 * int tempnum=IsInstrumentExist(tempuuid); if (tempnum==-1) {
			 * InstrumentClass tempInst=new InstrumentClass();
			 * tempInst.setUuid(node.attributeValue("uuid"));
			 * tempInst.AddDocID(node.getParent().attributeValue("ID"));
			 * instrumentList.add(tempInst); }else {
			 * instrumentList.get(tempnum).AddDocID(node.getParent().
			 * attributeValue("ID")); }
			 */

		}

		// 构建仪器docID-xpath map
		if (node.getName().equals("Path") && node.getNamespacePrefix().equals("hc")
				&& node.getParent().getParent().getName().equals("Network")
				&& node.getParent().getNamespacePrefix().equals("hc")) {
			String nodeTxt = node.getText();
			String tempDocId = node.attributeValue("documentId");
			// System.out.println(nodeTxt+"\n");

			if (!nodeTxt.equals("")) {
				if (getXPathType(nodeTxt).equals(StringUtil.instrument)) {
					if (instDocIDXpathMaps.containsKey(tempDocId)) {
						instDocIDXpathMaps.get(tempDocId).add(nodeTxt);
					} else {
						ArrayList<String> xpath = new ArrayList<String>();
						xpath.add(nodeTxt);
						instDocIDXpathMaps.put(tempDocId, xpath);
					}
					// instDocIDXpathMaps.put(tempDocId, nodeTxt);
				} else {
					if (getXPathType(nodeTxt).equals(StringUtil.station)) {

					} else {
						/*
						 * if (errorXpathMaps.containsKey(tempDocId)) {
						 * instDocIDXpathMaps.get(tempDocId).add(nodeTxt); }
						 * else { ArrayList<String> xpath = new
						 * ArrayList<String>(); xpath.add(nodeTxt);
						 * errorXpathMaps.put(tempDocId, xpath); }
						 */
					}
				}
			}

		}

		if (node.getName().equals("PhysicalLocation") && node.getNamespacePrefix().equals("ts")
				&& node.getParent().getParent().getName().equals("Instruments")
				&& node.getParent().getNamespacePrefix().equals("ts")) {
			String nodeTxt = node.getText();
			String id = node.getParent().attributeValue("ID");
			if (!nodeTxt.equals("null")) {
				instpathlist.add(nodeTxt);
				instDocumentId.add(id);
				System.out.println(nodeTxt);
			}
		}

	}

	/*
	 * private boolean IsInstPath(String path) { // TODO 自动生成的方法存根 boolean b =
	 * false; if (!path.equals("")) { String[] dirs = path.split("/"); String[]
	 * dir = dirs[1].split(":"); if(dirs[1] == null){ return false; } if
	 * ((dir.length == 1) && (dir[0].equals("InstrumentDescription"))) { b =
	 * true; } else { if (dir[1].equals("InstrumentDescription")) { b = true; }
	 * } }
	 * 
	 * return b; } private boolean IsStationType(String str) { // TODO 自动生成的方法存根
	 * boolean b = false; if (!str.equals("")) { String[] dirs = str.split("/");
	 * if(dirs[1] == null){ return false; } String[] dir = dirs[1].split(":");
	 * 
	 * if (dir.length == 1) { if (dir[0].equals(StringUtil.station)) { b = true;
	 * } } else { if (dir[1].equals(StringUtil.station)) { b = true; } } }
	 * 
	 * return b; }
	 */
	/*
	 * private int IsInstrumentExist(String tempuuid) { // TODO 自动生成的方法存根 int
	 * marker=-1;
	 * 
	 * if (!instrumentList.isEmpty()) { for (Iterator<InstrumentClass> iterator
	 * = instrumentList.iterator(); iterator.hasNext();) { if
	 * (iterator.next().getUuid().equals(tempuuid)) {
	 * marker=instrumentList.indexOf(iterator.next()); break; } } } return
	 * marker; }
	 */

	public List<PortClass> getPortList() {
		return portList;
	}

	public List<String> getSwitchNames() {
		return switchNames;
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
