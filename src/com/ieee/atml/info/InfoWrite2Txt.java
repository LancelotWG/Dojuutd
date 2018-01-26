package com.ieee.atml.info;

public interface InfoWrite2Txt {
	default String WriteNormalTxt(String string) {
		// TODO 自动生成的方法存根
		return (string + "\n");
	}

	default String WriteNormalTxt(int string) {
		// TODO 自动生成的方法存根
		return (string + "\n");
	}

	default String WriteNormalTxtWithoutEnter(String string) {
		return string;
	}

	default String WriteNormalTxtWithoutEnter(int string) {
		return string + "";
	}
}
