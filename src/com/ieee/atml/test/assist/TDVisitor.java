package com.ieee.atml.test.assist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.VisitorSupport;
import org.ietf.jgss.MessageProp;

import com.ieee.atml.test.resource.Action;
import com.ieee.atml.test.resource.Signal;
import com.ieee.atml.test.resource.SignalType;

public class TDVisitor extends VisitorSupport {

	private List<Element> signalEltList = new ArrayList<>();
	private HashMap<String, List<HashMap<String, String>>> parameterMap = new HashMap<>();
	private HashMap<String, List<HashMap<String, String>>> globelParameterMap = new HashMap<>();

	private List<Signal> localSignal = new ArrayList();
	private List<Signal> globelSignal = new ArrayList();

	public List<Signal> getLocalSignal() {
		return localSignal;
	}

	public void setLocalSignal(List<Signal> localSignal) {
		this.localSignal = localSignal;
	}

	public List<Signal> getGlobelSignal() {
		return globelSignal;
	}

	public void setGlobelSignal(List<Signal> globelSignal) {
		this.globelSignal = globelSignal;
	}

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

	private List<Element> getNodes(Element node) {
		List<Element> listElement = node.elements();
		List<Element> aElements = new ArrayList<>();
		aElements.addAll(listElement);
		for (Element e : listElement) {
			List<Element> elememts = getNodes(e);
			aElements.addAll(elememts);
		}
		return aElements;
	}

	private SignalType getSignalName(Element operation) {
		SignalType signal = new SignalType();
/*		String operationType = operation.attributeValue("xsi:type");*/
		List<Element> childElement = getNodes(operation);

		for (Iterator iterator = childElement.iterator(); iterator.hasNext();) {
			Element element = (Element) iterator.next();
			List<Attribute> listAttr = element.attributes();
			for (Attribute attr : listAttr) {
				String name = attr.getName();
				String value = attr.getValue();
				if(name.contains("SignalID")){
					signal.setName(value);
					if(name.contains("global") || name.contains("Global")){
						signal.setGlobel(true);
					}else{
						signal.setGlobel(false);
					}
					break;
				}
			}
			
		}

		/*if (operationType.equals("td:OperationSetup")) {
			Node signalNode = operation.selectSingleNode("./td:Sensor/td:LocalSensorSignalReference");

			
			 * if(signal != null){
			 * 
			 * }else{ signalNode = operation.selectSingleNode(
			 * "./td:Source/td:LocalSourceSignalReference"); if(signal != null){
			 * 
			 * }else{ signalNode = operation.selectSingleNode(
			 * "./td:Monitor/td:LocalMonitorSignalReference"); if(signal !=
			 * null){
			 * 
			 * }else{ signalNode =
			 * operation.selectSingleNode("./td:Source/td:GlobalSignalReference"
			 * ); if(signal != null){
			 * 
			 * }else{
			 * 
			 * } } } }
			 

		} else if (operationType.equals("td:OperationConnect")) {

		} else if (operationType.equals("td:OperationRead")) {

		} else if (operationType.equals("td:OperationReset")) {

		} else if (operationType.equals("td:OperationSetValue")) {

		} else if (operationType.equals("td:OperationCalculate")) {

		} else if (operationType.equals("td:OperationWaitFor")) {

		} else if (operationType.equals("td:OperationChange")) {

		} else if (operationType.equals("td:OperationDisconnect")) {

		}*/
		return signal;
	}
	
	
	
	private void addSignal(SignalType signal, String actionName, String operation){
		Action action = null;
		Signal aSignal = null;
		if(signal.isGlobel()){
			for (Iterator iterator = globelSignal.iterator(); iterator.hasNext();) {
				Signal signal1 = (Signal) iterator.next();
				if(signal1.getName().equals(signal.getName())){
					aSignal = signal1;
					break;
				}
			}
			if(aSignal != null){
				action = aSignal.getAction(actionName);	
				if(action != null){
					action.getOperations().add(operation);
				}else{
					action = new Action();
					action.setName(actionName);
					action.setOperations(new ArrayList<>());
					action.getOperations().add(operation);
					aSignal.getActions().add(action);
				}
			}else{
				action = new Action();
				action.setOperations(new ArrayList<>());
				action.setName(actionName);
				action.getOperations().add(operation);
				aSignal = new Signal();
				aSignal.setName(signal.getName());
				aSignal.setActions(new ArrayList<Action>());
				aSignal.getActions().add(action);
				globelSignal.add(aSignal);
			}
		}else{
			for (Iterator iterator = localSignal.iterator(); iterator.hasNext();) {
				Signal signal1 = (Signal) iterator.next();
				if(signal1.getName() == null){
					int i = 1;
				}
				if(signal1.getName().equals(signal.getName())){
					aSignal = signal1;
					break;
				}
			}
			if(aSignal != null){
				action = aSignal.getAction(actionName);	
				if(action != null){
					action.getOperations().add(operation);
				}else{
					action = new Action();
					action.setName(actionName);
					action.setOperations(new ArrayList<>());
					action.getOperations().add(operation);
					aSignal.getActions().add(action);
				}
			}else{
				action = new Action();
				action.setOperations(new ArrayList<>());
				action.setName(actionName);
				action.getOperations().add(operation);
				aSignal = new Signal();
				aSignal.setName(signal.getName());
				aSignal.setActions(new ArrayList<Action>());
				aSignal.getActions().add(action);
				localSignal.add(aSignal);
			}
		}
	}

	@Override
	public void visit(Element node) {
		// TODO 自动生成的方法存根
		super.visit(node);

		if (node.getName().equals("DescriptionDocumentReference") && node.getNamespacePrefix().equals("c")) {
			if (node.getParent().getParent().getName().equals("UUT")) {
				uuid = node.attributeValue("uuid");
			}
		}

		if (node.getName().equals("Action") && node.getNamespacePrefix().equals("td")) {
			String actionName = node.attributeValue("name");
			HashMap<String, List<String>> signal = new HashMap<>();
			List<Element> operationNodes = node.selectNodes("./td:Behavior/td:Operations/td:Operation");
			String preSignalName = null;
			for (Iterator iterator = operationNodes.iterator(); iterator.hasNext();) {
				Element element = (Element) iterator.next();
				String operationType = element.attributeValue("type");
				SignalType signalName = getSignalName(element);
				if(signalName.getName() != null){
					preSignalName = signalName.getName();
				}else{
					if(signalName.getName() == null){
						preSignalName = actionName;
					}
					signalName.setName(preSignalName);
				}
				if (operationType.equals("td:OperationSetup")) {
					addSignal(signalName,actionName,"OperationSetup");
				} else if (operationType.equals("td:OperationConnect")) {
					addSignal(signalName,actionName,"OperationConnect");
				} else if (operationType.equals("td:OperationRead")) {
					addSignal(signalName,actionName,"OperationRead");
				} else if (operationType.equals("td:OperationReset")) {
					addSignal(signalName,actionName,"OperationReset");
				} else if (operationType.equals("td:OperationSetValue")) {
					addSignal(signalName,actionName,"OperationSetValue");
				} else if (operationType.equals("td:OperationCalculate")) {
					addSignal(signalName,actionName,"OperationCalculate");
				} else if (operationType.equals("td:OperationWaitFor")) {
					addSignal(signalName,actionName,"OperationWaitFor");
				} else if (operationType.equals("td:OperationChange")) {
					addSignal(signalName,actionName,"OperationChange");
				} else if (operationType.equals("td:OperationDisconnect")) {
					addSignal(signalName,actionName,"OperationDisconnect");
				}
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
