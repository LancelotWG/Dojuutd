package com.ieee.atml.util;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FileFilterBySuffix extends FileFilter {

	private String suffix;
	@Override
	public boolean accept(File arg0) {
		// TODO �Զ����ɵķ������
		return arg0.getName().endsWith(suffix);
	}

	public FileFilterBySuffix(String suffix) {
		super();
		// TODO �Զ����ɵĹ��캯�����
		this.suffix=suffix;
	}

	@Override
	public String getDescription() {
		// TODO �Զ����ɵķ������
		return null;
	}

}
