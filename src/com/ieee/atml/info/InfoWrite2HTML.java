package com.ieee.atml.info;

public interface InfoWrite2HTML {
	default String WriteErrorHTML(String string) {
		// TODO �Զ����ɵķ������
		return ("<font color='red'>" + "<b><i>" + string + "</i></b>" + "</font><br>");
	}

	default String WriteRedHTML(String string) {
		// TODO �Զ����ɵķ������
		return ("<center><font color='red' size='5'>" + "<b><i>" + string + "</i></b>" + "</font></center><br>");
	}
	
	default String WriteError2HTML(String string) {
		// TODO �Զ����ɵķ������
		return ("<font color='orange'>" + "<b><i>" + string + "</i></b>" + "</font><br>");
	}
	
	default String WriteBoldHTML(String string) {
		// TODO �Զ����ɵķ������
		return "<center><font size='6'><b>" + string + "</b></font></center><br>";
	}
	
	default String WriteBold0HTML(String string) {
		// TODO �Զ����ɵķ������
		return "<font size='6'>" + string + "</font><br>";
	}
	
	default String WriteBold1HTML(String string) {
		// TODO �Զ����ɵķ������
		return "<font size='5'>" + string + "</font><br>";
	}
	
	default String WriteBold2HTML(String string) {
		// TODO �Զ����ɵķ������
		return "<font size='4'>" + string + "</font><br>";
	}
	
	default String WriteBold3HTML(String string) {
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
