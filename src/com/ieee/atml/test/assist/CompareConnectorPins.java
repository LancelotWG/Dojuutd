package com.ieee.atml.test.assist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public interface CompareConnectorPins {
	default void CompareTwoList(List list1, List list2, List errorPin, List errorPin1) {
		// TODO 自动生成的方法存根
		int size1 = list1.size();
		int size2 = list2.size();
		List<String> dellist = new ArrayList();
		List<String> templist1 = new ArrayList();
		List<String> templist2 = new ArrayList();

		templist1.addAll(list1);
		templist2.addAll(list2);

		if (size1 > size2) {
			for (Iterator<String> iterator = templist1.iterator(); iterator.hasNext();) {
				String tempstr = iterator.next();
				if (templist2.contains(tempstr)) {
					dellist.add(tempstr);

				}
			}
		} else {
			for (Iterator<String> iterator = templist2.iterator(); iterator.hasNext();) {
				String tempstr = iterator.next();
				if (templist1.contains(tempstr)) {
					dellist.add(tempstr);

				}
			}
		}

		templist1.removeAll(dellist);
		templist2.removeAll(dellist);

		errorPin.addAll(templist1);
		errorPin1.addAll(templist2);

	}
}
