package com.ieee.atml.info;

public interface InfoWrite2HTML {
	default String WriteErrorHTML(String string) {
		// TODO �Զ����ɵķ������
		return ("<font color='red'>" + "<b><i>" + string + "</i></b>" + "</font><br>");
	}

	default String WriteBoldHTML(String string) {
		// TODO �Զ����ɵķ������
		return "<b><i>" + string + "</i></b><br>";
	}

	default String WriteNormalHTML(String string) {
		// TODO �Զ����ɵķ������
		return string + "<br>";
	}

	default String WriteNormalHTMLWithoutEnter(String string) {
		return string;
	}

	default String WriteBoldHTML(int size) {
		// TODO �Զ����ɵķ������
		return "<b><i>" + size + "</i></b><br>";
	}
}
