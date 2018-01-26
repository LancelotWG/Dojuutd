package com.ieee.atml.test.assist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.VisitorSupport;
import org.ietf.jgss.MessageProp;

public class TDVisitor extends VisitorSupport {

	private List<Element> signalEltList = new ArrayList<>();
	private HashMap<String, List<HashMap<String, String>>> parameterMap = new HashMap<>();
	private HashMap<String, List<HashMap<String, String>>> globelParameterMap = new HashMap<>();
	String uuid;

	public String getUuid() {
		return uuid;
	}

	public HashMap<String, List<HashMap<String, String>>> getGlobelParameterMap() {
		return globelParameterMap;
	}

	public HashMap<String, List<HashMap<String, String>>> getParameterMap() {
		return parameterMap;
	}

	public List<Element> getSignalEltList() {
		return signalEltList;
	}

	@Override
	public void visit(Element node) {
		// TODO 自动生成的方法存根
		super.visit(node);
		
		if (node.getName().equals("DescriptionDocumentReference") && node.getNamespacePrefix().equals("c")) {
			if(node.getParent().getParent().getName().equals("UUT")){
				uuid = node.attributeValue("uuid");
			}
		}
		if (node.getName().equals("Operation") && node.getNamespacePrefix().equals("td")) {
			if (node.attributeValue("type", "xsi").equals("td:OperationConnect")) {
				Element signalNode = (Element) node.selectSingleNode("./td:Signal/std:Signal");
				if (signalNode != null) {
					try {
						signalEltList.addAll(signalNode.elements());
						// String actionID =
						// node.getParent().getParent().getParent().getParent().getParent().getParent().attributeValue("ID");
						// this.actionID.add(actionID);
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println(node.attributeValue("name"));
						System.out.println(e.getMessage());
					}
				} else {
					Element interfaceNode = (Element) node.selectSingleNode("./td:Signal/std:Signal");
				}
				// signalEltList=signalNode.elements();
			}
		}

		if (node.getName().equals("ParameterDescription") && node.getNamespacePrefix().equals("td")) {
			Element testGroup = node.getParent().getParent();
			String name = node.attributeValue("ID");
			List<Element> actionReferencesList = testGroup.elements("ActionReferences");
			List<String> testGroupIDs = new ArrayList<String>();
			for (Iterator iterator = actionReferencesList.iterator(); iterator.hasNext();) {
				Element actionReferences = (Element) iterator.next();
				if (actionReferences.getNamespacePrefix().equals("td")) {
					List<Element> actionReferenceList = actionReferences.elements("ActionReference");
					for (Iterator iterator2 = actionReferenceList.iterator(); iterator2.hasNext();) {
						Element actionReference = (Element) iterator2.next();
						if (actionReference.getNamespacePrefix().equals("td")) {
							String testGroupID = actionReference.attributeValue("actionID");
							if (testGroupID != null) {
								testGroupIDs.add(testGroupID);
							}
						}
					}
				}
			}
			List<Element> ValueDescriptionList = node.elements("ValueDescription");
			for (Iterator iterator2 = ValueDescriptionList.iterator(); iterator2.hasNext();) {
				Element ValueDescription = (Element) iterator2.next();
				if (ValueDescription.getNamespacePrefix().equals("td")) {
					List<Element> DatumDescriptionList = ValueDescription.elements("DatumDescription");
					for (Iterator iterator3 = DatumDescriptionList.iterator(); iterator3.hasNext();) {
						Element DatumDescription = (Element) iterator3.next();
						if (DatumDescription.getNamespacePrefix().equals("td")) {
							String type = DatumDescription.attributeValue("type", "xsi");
							if (type != null) {
								if (type.equals("td:stringDescription")) {
									List<Element> NominalValueList = DatumDescription.elements("NominalValue");
									for (Iterator iterator4 = NominalValueList.iterator(); iterator4.hasNext();) {
										Element NominalValue = (Element) iterator4.next();
										if (NominalValue.getNamespacePrefix().equals("td")) {
											List<Element> ValueList = NominalValue.elements("Value");
											for (Iterator iterator5 = ValueList.iterator(); iterator5.hasNext();) {
												Element Value = (Element) iterator5.next();
												if (Value.getNamespacePrefix().equals("c")) {
													String data = Value.getText();
													HashMap<String, String> item = new HashMap<String, String>();
													item.put(name, data);
													for (Iterator iterator6 = testGroupIDs.iterator(); iterator6
															.hasNext();) {
														String id = (String) iterator6.next();
														List itemList = globelParameterMap.get(id);
														if (itemList == null) {
															itemList = new ArrayList<HashMap<String, String>>();
														}
														itemList.add(item);
														globelParameterMap.put(id, itemList);
													}

												}
											}
										}
									}
								}
							}
						}
					}
				}
			}

		}

		if (node.getName().equals("Parameter") && node.getNamespacePrefix().equals("td")) {
			Element action = node.getParent().getParent();
			String id = action.attributeValue("ID");
			String name = node.attributeValue("ID");
			List<Element> values = node.elements("Value");
			for (Iterator iterator = values.iterator(); iterator.hasNext();) {
				Element element = (Element) iterator.next();
				if (element.getNamespacePrefix().equals("td")) {
					List<Element> datum = element.elements("Datum");
					for (Iterator iterator2 = datum.iterator(); iterator2.hasNext();) {
						Element element1 = (Element) iterator2.next();
						if (element1.getNamespacePrefix().equals("c")) {
							String type = element1.attributeValue("type", "xsi");
							if (type.equals("c:string")) {
								List<Element> value = element1.elements("Value");
								for (Iterator iterator3 = value.iterator(); iterator3.hasNext();) {
									Element element2 = (Element) iterator3.next();
									if (element2.getNamespacePrefix().equals("c")) {
										String data = element2.getText();
										HashMap<String, String> item = new HashMap<String, String>();
										item.put(name, data);
										List itemList = parameterMap.get(id);
										if (itemList == null) {
											itemList = new ArrayList<HashMap<String, String>>();
										}
										itemList.add(item);
										parameterMap.put(id, itemList);
									}
								}
							}
						}
					}
				}
			}

		}

	}

}
