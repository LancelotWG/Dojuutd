package com.ieee.atml.info;

public interface InfoWrite2HTML {
	default String WriteErrorHTML(String string) {
		// TODO 自动生成的方法存根
		return ("<font color='red'>" + "<b><i>" + string + "</i></b>" + "</font><br>");
	}

	default String WriteRedHTML(String string) {
		// TODO 自动生成的方法存根
		return ("<center><font color='red' size='5'>" + "<b><i>" + string + "</i></b>" + "</font></center><br>");
	}
	
	default String WriteBoldHTML(String string) {
		// TODO 自动生成的方法存根
		return "<b><i>" + string + "</i></b><br>";
	}

	default String WriteNormalHTML(String string) {
		// TODO 自动生成的方法存根
		return string + "<br>";
	}

	default String WriteNormalHTMLWithoutEnter(String string) {
		return string;
	}

	default String WriteBoldHTML(int size) {
		// TODO 自动生成的方法存根
		return "<b><i>" + size + "</i></b><br>";
	}
}
