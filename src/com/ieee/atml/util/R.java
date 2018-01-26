package com.ieee.atml.util;


import java.io.IOException;

import javax.swing.ImageIcon;

public class R {
	ImageIcon icon;

	public ImageIcon getIcon() {
		return icon;
	}

	public R(String path) throws IOException {
		icon = new ImageIcon(R.class.getResource(path));
	}
}
