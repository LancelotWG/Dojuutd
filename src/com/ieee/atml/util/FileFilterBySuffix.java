package com.ieee.atml.util;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FileFilterBySuffix extends FileFilter {

	private String suffix;
	@Override
	public boolean accept(File arg0) {
		// TODO 自动生成的方法存根
		return arg0.getName().endsWith(suffix);
	}

	public FileFilterBySuffix(String suffix) {
		super();
		// TODO 自动生成的构造函数存根
		this.suffix=suffix;
	}

	@Override
	public String getDescription() {
		// TODO 自动生成的方法存根
		return null;
	}

}
