package com.ieee.atml.test.assist;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.VisitorSupport;

public class TestSignalVisitor extends VisitorSupport {

	List<String> signalList=new ArrayList();
	@Override
	public void visit(Element node) {
		// TODO 自动生成的方法存根
		super.visit(node);
		if (node.getName().equals("Signal")&&node.getNamespacePrefix().equals("std")) {
			List<Element> elements = node.elements();
			for (Iterator iterator = elements.iterator(); iterator.hasNext();) {
				Element element = (Element) iterator.next();
				signalList.add(element.getName());
			}
		}
	}
	public List<String> getSignalList() {
		return signalList;
	}

}
