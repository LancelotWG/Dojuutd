package com.ieee.atml.info;

public interface InfoWrite2Txt {
	default String WriteNormalTxt(String string) {
		// TODO �Զ����ɵķ������
		return (string + "\n");
	}

	default String WriteNormalTxt(int string) {
		// TODO �Զ����ɵķ������
		return (string + "\n");
	}

	default String WriteNormalTxtWithoutEnter(String string) {
		return string;
	}

	default String WriteNormalTxtWithoutEnter(int string) {
		return string + "";
	}
}
