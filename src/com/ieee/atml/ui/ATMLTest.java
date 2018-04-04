package com.ieee.atml.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.html.HTMLEditorKit;

import com.ieee.atml.test.item.AdapterReader;
import com.ieee.atml.test.item.ConfigurationReader;
import com.ieee.atml.test.item.InstrumentReader;
import com.ieee.atml.test.item.TDReader;
import com.ieee.atml.test.item.TestSignalReader;
import com.ieee.atml.test.item.TestStationReader;
import com.ieee.atml.test.item.UUTDReader;
import com.ieee.atml.test.item.WireListReader;
import com.ieee.atml.util.FileFilterBySuffix;
import com.ieee.atml.util.FileTypeValidation;
import com.ieee.atml.util.R;
import com.ieee.atml.util.StringUtil;

public class ATMLTest implements FileTypeValidation {
	// 界面主框架
	JFrame f = new JFrame("ATML检查工具");
	// 测试选项
	// String[] testItem = new String[] { "ConfigureTest", "UUTTest",
	// "AdapterTest", "StationTest",
	// "SignalTest","InstrumentTest","TestDescriptionTest" };
	JLabel TestItemLabel = new JLabel("测试项目：");
	JComboBox<String> TestItemChooser = new JComboBox<>(StringUtil.testItem);
	// 静态文本
	JLabel fileXmlSpyLabel = new JLabel("XMLSpy路径：", SwingConstants.LEFT);
	JLabel fileConfigureDirLabel = new JLabel("TestConfiguration文件路径：", SwingConstants.LEFT);
	JLabel fileUUTDirLabel = new JLabel("UUTDescription文件路径：");
	JLabel fileAdapterDirLabel = new JLabel("TestAdapter文件路径：");
	JLabel fileStationDirLabel = new JLabel("TestStationDescription文件路径：");
	JLabel fileDescriptionDirLabel = new JLabel("TestDescription文件路径：");
	JLabel fileWireListDirLabel = new JLabel("TestWireList文件路径：");
	JLabel fileSTDBSCDirLabel = new JLabel("STDBSC文件路径：");
	JLabel fileSTDTSFLibDirLabel = new JLabel("STDTSFLib文件夹路径：");
	JLabel fileExtTSFLibDirLabel = new JLabel("ExtTSFLib文件夹路径：");

	JLabel fileResultDirLabel = new JLabel("TestResult文件路径：");
	JLabel fileInstDirLabel = new JLabel("Instruments文件夹路径：");
	JLabel fileSingleInstDirLabel = new JLabel("Instrument文件路径：");

	// 文本框
	// 输入路径
	JTextField fileXmlSpyDir = new JTextField(80);
	JTextField fileConfigureDir = new JTextField(80);
	JTextField fileUUTDir = new JTextField(80);
	JTextField fileAdapterDir = new JTextField(80);
	JTextField fileStationDir = new JTextField(80);
	JTextField fileDescriptionDir = new JTextField(80);
	JTextField fileWireListDir = new JTextField(80);
	JTextField fileSTDBSCDir = new JTextField(80);
	JTextField fileSTDTSFLibDir = new JTextField(80);
	JTextField fileExtTSFLibDir = new JTextField(80);

	JTextField fileResultDir = new JTextField(80);
	JTextField fileInstDir = new JTextField(80);
	JTextField fileSingleInstDir = new JTextField(80);
	// 监测信息输出文本框
	JTextPane errorOutput = new JTextPane();
	// 按钮
	JButton xmlSpyTestButton = new JButton("XMLSpy格式检测");
	JButton startTestButton = new JButton("语义检测");
	// 图标
	Icon openIcon = new ImageIcon(R.class.getResource("/open.png"));
	ImageIcon logo = new ImageIcon(R.class.getResource("/logo.png"));
	// **********map图片*************
	// ScaleIcon mapIcon = new ScaleIcon(new ImageIcon("ico/map.jpg"));
	ScaleIcon mapIcon = new ScaleIcon(new ImageIcon(R.class.getResource("/CATIC.jpg")));
	// ImageIcon mapIcon = new ImageIcon("ico/map.jpg");
	// map框
	JLabel mapLabel = new JLabel();
	// 浏览弹出菜单
	// XMLSpy
	JPopupMenu browseXMLSpyPop = new JPopupMenu();
	JMenuItem browseXMLSpyItem = new JMenuItem("浏览", openIcon);
	// ConfigurePop
	JPopupMenu browseConfigurePop = new JPopupMenu();
	JMenuItem browseConfigureItem = new JMenuItem("浏览", openIcon);
	// UUTPop
	JPopupMenu browseUUTPop = new JPopupMenu();
	JMenuItem browseUUTItem = new JMenuItem("浏览", openIcon);
	// AdapterPop
	JPopupMenu browseAdapterPop = new JPopupMenu();
	JMenuItem browseAdapterItem = new JMenuItem("浏览", openIcon);
	// StationPop
	JPopupMenu browseStationPop = new JPopupMenu();
	JMenuItem browseStationItem = new JMenuItem("浏览", openIcon);
	// WireListPop
	JPopupMenu browseWireListPop = new JPopupMenu();
	JMenuItem browseWireListItem = new JMenuItem("浏览", openIcon);
	// DescriptionPop
	JPopupMenu browseDescriptionPop = new JPopupMenu();
	JMenuItem browseDescriptionItem = new JMenuItem("浏览", openIcon);
	// STDBSCPop
	JPopupMenu browseSTDBSCPop = new JPopupMenu();
	JMenuItem browseSTDBSCItem = new JMenuItem("浏览", openIcon);
	// STDTSFLibPop
	JPopupMenu browseSTDTSFLibPop = new JPopupMenu();
	JMenuItem browseSTDTSFLibItem = new JMenuItem("浏览", openIcon);
	// ExtTSFLibPop
	JPopupMenu browseExtTSFLibPop = new JPopupMenu();
	JMenuItem browseExtTSFLibItem = new JMenuItem("浏览", openIcon);
	// ResultPop
	JPopupMenu browseResultPop = new JPopupMenu();
	JMenuItem browseResultItem = new JMenuItem("浏览", openIcon);
	// Instruments
	JPopupMenu browseInstPop = new JPopupMenu();
	JMenuItem browseInstItem = new JMenuItem("浏览", openIcon);
	// Single Instrument
	JPopupMenu browseSingleInstPop = new JPopupMenu();
	JMenuItem browseSingleInstItem = new JMenuItem("浏览", openIcon);
	// 文件选择器
	JFileChooser chooser = new JFileChooser(".");
	ExtensionFileFilter fileFilter = new ExtensionFileFilter();
	ExtensionFileFilter fileExeFilter = new ExtensionFileFilter();
	ExtensionFileFilter fileTsgFilter = new ExtensionFileFilter();

	// Instrument路径列表
	private List<String> instpathlist = new ArrayList();

	private void addDirComponent(JPanel panel, JLabel label, JTextField textField) {
		label.setPreferredSize(new Dimension(200, 20));
		panel.add(label);
		panel.add(textField);
		textField.setEditable(false);
	}

	private void addPopMenu(JPopupMenu popupMenu, JMenuItem menuItem, JTextField textField, ActionListener openFlie) {
		popupMenu.add(menuItem);
		menuItem.addActionListener(openFlie);
		textField.setComponentPopupMenu(popupMenu);
	}

	private void addErrorField(JFrame frame) {
		// 测试结果边框设置
		Border lb = BorderFactory.createLineBorder(Color.BLACK, 1);
		TitledBorder tb = new TitledBorder(lb, "测试结果：", TitledBorder.LEFT, TitledBorder.TOP,
				new Font("StSong", Font.BOLD, 14), Color.BLACK);
		errorOutput.setPreferredSize(new Dimension(730, 220));

		errorOutput.setEditable(false);
		// 设置HTML解析器
		HTMLEditorKit htmlEdit = new HTMLEditorKit();
		errorOutput.setEditorKit(htmlEdit);
		errorOutput.setContentType("text/html");

		JScrollPane taJsp = new JScrollPane(errorOutput);
		JPanel errorPanel = new JPanel();
		errorPanel.setBorder(tb);
		errorPanel.add(taJsp);
		frame.add(errorPanel, BorderLayout.SOUTH);
		errorOutput.setText("");
	}

	private void clearTextField() {
		errorOutput.setText("");
		fileConfigureDir.setText("");
		fileUUTDir.setText("");
		fileAdapterDir.setText("");
		fileStationDir.setText("");
		fileDescriptionDir.setText("");
		fileSTDBSCDir.setText("");
		fileSTDTSFLibDir.setText("");
		fileExtTSFLibDir.setText("");
		fileResultDir.setText("");
		fileInstDir.setText("");
		fileSingleInstDir.setText("");
	}

	public void init() {
		// 设置图标
		f.setIconImage(logo.getImage());
		// 布局
		// map图片设置

		mapLabel.setIcon(mapIcon);

		mapLabel.setPreferredSize(new Dimension(745, 90));
		// map图片布局
		JPanel mapPanel = new JPanel();
		mapPanel.add(mapLabel);
		// 测试选项框布局
		JPanel testItemPanel = new JPanel();
		testItemPanel.add(TestItemLabel);
		testItemPanel.add(TestItemChooser);
		// top区布局
		Box topPanel = Box.createVerticalBox();
		topPanel.setAlignmentX(1);
		topPanel.add(mapPanel);
		topPanel.add(testItemPanel);

		f.add(topPanel, BorderLayout.NORTH);
		// configure路径布局
		JPanel configurePanel = new JPanel();
		addDirComponent(configurePanel, fileConfigureDirLabel, fileConfigureDir);
		// UUT路径布局
		JPanel UUTPanel = new JPanel();
		addDirComponent(UUTPanel, fileUUTDirLabel, fileUUTDir);
		// adapter路径布局
		JPanel adapterPanel = new JPanel();
		addDirComponent(adapterPanel, fileAdapterDirLabel, fileAdapterDir);
		// station路径布局
		JPanel stationPanel = new JPanel();
		addDirComponent(stationPanel, fileStationDirLabel, fileStationDir);
		// description路径布局
		JPanel descriptionPanel = new JPanel();
		addDirComponent(descriptionPanel, fileDescriptionDirLabel, fileDescriptionDir);
		// description路径布局
		JPanel wireListPanel = new JPanel();
		addDirComponent(wireListPanel, fileWireListDirLabel, fileWireListDir);
		// STDBSC路径布局
		JPanel STDBSCPanel = new JPanel();
		addDirComponent(STDBSCPanel, fileSTDBSCDirLabel, fileSTDBSCDir);
		// STDBSC路径布局
		JPanel STDTSFLibPanel = new JPanel();
		addDirComponent(STDTSFLibPanel, fileSTDTSFLibDirLabel, fileSTDTSFLibDir);
		// STDBSC路径布局
		JPanel ExtTSFLibPanel = new JPanel();
		addDirComponent(ExtTSFLibPanel, fileExtTSFLibDirLabel, fileExtTSFLibDir);
		// result路径布局
		JPanel resultPanel = new JPanel();
		addDirComponent(resultPanel, fileResultDirLabel, fileResultDir);
		// Instruments路径布局
		JPanel instPanel = new JPanel();
		addDirComponent(instPanel, fileInstDirLabel, fileInstDir);
		// Instruments路径布局
		JPanel singleInstPanel = new JPanel();
		addDirComponent(singleInstPanel, fileSingleInstDirLabel, fileSingleInstDir);

		// XMLSpy路径布局
		JPanel xmlSpyPanel = new JPanel();
		addDirComponent(xmlSpyPanel, fileXmlSpyLabel, fileXmlSpyDir);
		// 开始测试按钮布局
		JPanel startPanel = new JPanel();
		startPanel.add(xmlSpyTestButton);
		startPanel.add(startTestButton);
		// 开始测试按钮布局（无开始检测）
		// 测试区布局
		Box checkPanel = Box.createVerticalBox();
		checkPanel.add(configurePanel);
		checkPanel.add(UUTPanel);
		checkPanel.add(adapterPanel);
		checkPanel.add(stationPanel);
		checkPanel.add(descriptionPanel);
		checkPanel.add(wireListPanel);
		checkPanel.add(STDBSCPanel);
		checkPanel.add(STDTSFLibPanel);
		checkPanel.add(ExtTSFLibPanel);
		checkPanel.add(instPanel);
		checkPanel.add(singleInstPanel);
		checkPanel.add(resultPanel);
		checkPanel.add(xmlSpyPanel);
		// checkPanel.add(startWithOutTestPanel);
		checkPanel.add(startPanel);
		f.add(checkPanel);
		// 信息输出框
		addErrorField(f);
		// 测试项目切换时间响应
		ActionListener changeTestItem = e -> {
			startTestButton.setVisible(true);
			String testItem = TestItemChooser.getSelectedItem().toString();
			System.out.println(testItem);
			checkPanel.removeAll();
			clearTextField();
			if (testItem.equals(StringUtil.configurationTest)) {
				checkPanel.add(configurePanel);
				checkPanel.add(UUTPanel);
				checkPanel.add(adapterPanel);
				checkPanel.add(stationPanel);
				checkPanel.add(descriptionPanel);
				checkPanel.add(xmlSpyPanel);
				checkPanel.add(startPanel);
			} else if (testItem.equals(StringUtil.UUTTest)) {
				checkPanel.add(UUTPanel);
				checkPanel.add(xmlSpyPanel);
				checkPanel.add(startPanel);
			} else if (testItem.equals(StringUtil.adapterTest)) {
				checkPanel.add(UUTPanel);
				checkPanel.add(adapterPanel);
				checkPanel.add(stationPanel);
				checkPanel.add(xmlSpyPanel);
				checkPanel.add(startPanel);
			} else if (testItem.equals(StringUtil.stationTest)) {
				checkPanel.add(stationPanel);
				checkPanel.add(instPanel);
				checkPanel.add(xmlSpyPanel);
				checkPanel.add(startPanel);
			} else if (testItem.equals(StringUtil.testSignalTest)) {
				checkPanel.add(STDBSCPanel);
				checkPanel.add(STDTSFLibPanel);
				checkPanel.add(ExtTSFLibPanel);
				checkPanel.add(xmlSpyPanel);
				checkPanel.add(startPanel);
			} else if (testItem.equals(StringUtil.instrumentTest)) {
				checkPanel.add(singleInstPanel);
				checkPanel.add(xmlSpyPanel);
				checkPanel.add(startPanel);
			} else if (testItem.equals(StringUtil.testDescriptionTest)) {
				checkPanel.add(descriptionPanel);
				checkPanel.add(UUTPanel);
				checkPanel.add(xmlSpyPanel);
				checkPanel.add(startPanel);
			} else if (testItem.equals(StringUtil.wireListTest)) {
				checkPanel.add(wireListPanel);
				checkPanel.add(UUTPanel);
				checkPanel.add(stationPanel);
				checkPanel.add(adapterPanel);
				checkPanel.add(xmlSpyPanel);
				checkPanel.add(startPanel);
			} else if (testItem.equals(StringUtil.resultTest)) {
				checkPanel.add(resultPanel);
				checkPanel.add(xmlSpyPanel);
				startTestButton.setVisible(false);
				checkPanel.add(startPanel);
			}
			f.repaint();
			f.validate();
		};
		// 响应设置
		TestItemChooser.addActionListener(changeTestItem);
		// 文件过滤器设置
		fileFilter.addExtension("xml");
		fileFilter.setDescription("*.xml");

		fileExeFilter.addExtension("exe");
		fileExeFilter.setDescription("*.exe");

		fileTsgFilter.addExtension("tsg");
		fileTsgFilter.setDescription("*.tsg");

		chooser.addChoosableFileFilter(fileFilter);
		chooser.setAcceptAllFileFilterUsed(false);
		// 文件浏览事件响应
		ActionListener openFlie = e -> {
			int result;
			if (e.getSource() == browseXMLSpyItem) {
				chooser.resetChoosableFileFilters();
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.addChoosableFileFilter(fileExeFilter);
				result = chooser.showDialog(f, "选择XMLSpy程序");
			} else if (e.getSource() == browseInstItem || e.getSource() == browseExtTSFLibItem
					|| e.getSource() == browseSTDTSFLibItem) {
				chooser.resetChoosableFileFilters();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				result = chooser.showDialog(f, "选择文件夹");
				// chooser.addChoosableFileFilter(fileTsgFilter);
			} else {
				chooser.resetChoosableFileFilters();
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.addChoosableFileFilter(fileFilter);
				result = chooser.showDialog(f, "选择XML文件");
			}
			// int result = chooser.showDialog(f, "打开文件");
			if (result == JFileChooser.APPROVE_OPTION) {
				String path = chooser.getSelectedFile().getPath();
				System.out.println(path);
				if (e.getSource() == browseConfigureItem) {
					if (fileTypeValidation(path, StringUtil.configuration)) {
						fileConfigureDir.setText(path);
					} else {
						JOptionPane.showMessageDialog(f, "请输入有效的" + StringUtil.configuration + "类型文件", "文件类型错误",
								JOptionPane.ERROR_MESSAGE);
					}
				} else if (e.getSource() == browseUUTItem) {
					if (fileTypeValidation(path, StringUtil.UUT)) {
						fileUUTDir.setText(path);
					} else {
						JOptionPane.showMessageDialog(f, "请输入有效的" + StringUtil.UUT + "类型文件", "文件类型错误",
								JOptionPane.ERROR_MESSAGE);
					}
				} else if (e.getSource() == browseAdapterItem) {
					if (fileTypeValidation(path, StringUtil.adapter)) {
						fileAdapterDir.setText(path);
					} else {
						JOptionPane.showMessageDialog(f, "请输入有效的" + StringUtil.adapter + "类型文件", "文件类型错误",
								JOptionPane.ERROR_MESSAGE);
					}
				} else if (e.getSource() == browseStationItem) {
					if (fileTypeValidation(path, StringUtil.station)) {
						fileStationDir.setText(path);
					} else {
						JOptionPane.showMessageDialog(f, "请输入有效的" + StringUtil.station + "类型文件", "文件类型错误",
								JOptionPane.ERROR_MESSAGE);
					}
				} else if (e.getSource() == browseSTDBSCItem) {
					if (fileTypeValidation(path, StringUtil.testSignal)) {
						fileSTDBSCDir.setText(path);
					} else {
						JOptionPane.showMessageDialog(f, "请输入有效的" + StringUtil.testSignal + "类型文件", "文件类型错误",
								JOptionPane.ERROR_MESSAGE);
					}
				} else if (e.getSource() == browseSTDTSFLibItem) {
					fileSTDTSFLibDir.setText(path);
				} else if (e.getSource() == browseExtTSFLibItem) {
					fileExtTSFLibDir.setText(path);
				} else if (e.getSource() == browseDescriptionItem) {
					if (fileTypeValidation(path, StringUtil.testDescription)) {
						fileDescriptionDir.setText(path);
					} else {
						JOptionPane.showMessageDialog(f, "请输入有效的" + StringUtil.testDescription + "类型文件", "文件类型错误",
								JOptionPane.ERROR_MESSAGE);
					}
				} else if (e.getSource() == browseXMLSpyItem) {
					fileXmlSpyDir.setText(path);
				} else if (e.getSource() == browseInstItem) {
					fileInstDir.setText(path);
				} else if (e.getSource() == browseSingleInstItem) {
					if (fileTypeValidation(path, StringUtil.instrument)) {
						fileSingleInstDir.setText(path);
					} else {
						JOptionPane.showMessageDialog(f, "请输入有效的" + StringUtil.instrument + "类型文件", "文件类型错误",
								JOptionPane.ERROR_MESSAGE);
					}
				} else if (e.getSource() == browseResultItem) {
					if (fileTypeValidation(path, StringUtil.result)) {
						fileResultDir.setText(path);
					} else {
						JOptionPane.showMessageDialog(f, "请输入有效的" + StringUtil.result + "类型文件", "文件类型错误",
								JOptionPane.ERROR_MESSAGE);
					}
				} else if (e.getSource() == browseWireListItem) {
					if (fileTypeValidation(path, StringUtil.wireList)) {
						fileWireListDir.setText(path);
					} else {
						JOptionPane.showMessageDialog(f, "请输入有效的" + StringUtil.wireList + "类型文件", "文件类型错误",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		};
		addPopMenu(browseConfigurePop, browseConfigureItem, fileConfigureDir, openFlie);
		addPopMenu(browseUUTPop, browseUUTItem, fileUUTDir, openFlie);
		addPopMenu(browseAdapterPop, browseAdapterItem, fileAdapterDir, openFlie);
		addPopMenu(browseStationPop, browseStationItem, fileStationDir, openFlie);
		addPopMenu(browseDescriptionPop, browseDescriptionItem, fileDescriptionDir, openFlie);
		addPopMenu(browseWireListPop, browseWireListItem, fileWireListDir, openFlie);
		addPopMenu(browseSTDBSCPop, browseSTDBSCItem, fileSTDBSCDir, openFlie);
		addPopMenu(browseSTDTSFLibPop, browseSTDTSFLibItem, fileSTDTSFLibDir, openFlie);
		addPopMenu(browseExtTSFLibPop, browseExtTSFLibItem, fileExtTSFLibDir, openFlie);
		addPopMenu(browseXMLSpyPop, browseXMLSpyItem, fileXmlSpyDir, openFlie);
		addPopMenu(browseInstPop, browseInstItem, fileInstDir, openFlie);
		addPopMenu(browseSingleInstPop, browseSingleInstItem, fileSingleInstDir, openFlie);
		addPopMenu(browseResultPop, browseResultItem, fileResultDir, openFlie);
		// XMLSpy测试响应
		ActionListener xmlSpyListener = e -> {
			String testItem = TestItemChooser.getSelectedItem().toString();

			if (testItem.equals(StringUtil.configurationTest)) {
				if (fileConfigureDir.getText().equals("") || fileUUTDir.getText().equals("")
						|| fileStationDir.getText().equals("") || fileDescriptionDir.getText().equals("")
						|| fileXmlSpyDir.getText().equals("") || fileAdapterDir.getText().equals("")) {
					JOptionPane.showMessageDialog(f, "请确保路径填充完整！", "警告", JOptionPane.WARNING_MESSAGE);
				} else {
					StartXmlSpy(fileXmlSpyDir.getText(), fileConfigureDir.getText(), fileUUTDir.getText(),
							fileStationDir.getText(), fileDescriptionDir.getText(), fileAdapterDir.getText());

				}
			} else if (testItem.equals(StringUtil.UUTTest)) {
				if (fileUUTDir.getText().equals("") || fileXmlSpyDir.getText().equals("")) {
					JOptionPane.showMessageDialog(f, "请确保路径填充完整！", "警告", JOptionPane.WARNING_MESSAGE);
				} else {
					StartXmlSpy(fileXmlSpyDir.getText(), fileUUTDir.getText());

				}
			} else if (testItem.equals(StringUtil.adapterTest)) {
				if (fileUUTDir.getText().equals("") || fileAdapterDir.getText().equals("")
						|| fileStationDir.getText().equals("") || fileXmlSpyDir.getText().equals("")) {
					JOptionPane.showMessageDialog(f, "请确保路径填充完整！", "警告", JOptionPane.WARNING_MESSAGE);
				} else {

					StartXmlSpy(fileXmlSpyDir.getText(), fileUUTDir.getText(), fileAdapterDir.getText(),
							fileStationDir.getText());
				}
			} else if (testItem.equals(StringUtil.stationTest)) {
				if (fileStationDir.getText().equals("") || fileXmlSpyDir.getText().equals("")
						|| fileInstDir.getText().equals("")) {
					JOptionPane.showMessageDialog(f, "请确保路径填充完整！", "警告", JOptionPane.WARNING_MESSAGE);
				} else {
					StartStationXmlSpy(fileXmlSpyDir.getText(), fileStationDir.getText(), fileInstDir.getText());
				}
			} else if (testItem.equals(StringUtil.instrumentTest)) {
				if (fileSingleInstDir.getText().equals("") || fileXmlSpyDir.getText().equals("")) {
					JOptionPane.showMessageDialog(f, "请确保路径填充完整！", "警告", JOptionPane.WARNING_MESSAGE);
				} else {
					StartXmlSpy(fileXmlSpyDir.getText(), fileSingleInstDir.getText());
				}
			} else if (testItem.equals(StringUtil.testDescriptionTest)) {
				if (fileDescriptionDir.getText().equals("") || fileUUTDir.getText().equals("")
						|| fileXmlSpyDir.getText().equals("")) {
					JOptionPane.showMessageDialog(f, "请确保路径填充完整！", "警告", JOptionPane.WARNING_MESSAGE);
				} else {
					StartXmlSpy(fileXmlSpyDir.getText(), fileDescriptionDir.getText(), fileUUTDir.getText());
				}
			} else if (testItem.equals(StringUtil.resultTest)) {
				if (fileResultDir.getText().equals("") || fileXmlSpyDir.getText().equals("")) {
					JOptionPane.showMessageDialog(f, "请确保路径填充完整！", "警告", JOptionPane.WARNING_MESSAGE);
				} else {
					StartXmlSpy(fileXmlSpyDir.getText(), fileResultDir.getText());
				}
			} else if (testItem.equals(StringUtil.testSignalTest)) {
				if (fileSTDBSCDir.getText().equals("") || fileXmlSpyDir.getText().equals("")
						|| fileSTDTSFLibDir.getText().equals("") || fileExtTSFLibDir.getText().equals("")) {
					JOptionPane.showMessageDialog(f, "请确保路径填充完整！", "警告", JOptionPane.WARNING_MESSAGE);
				} else {
					StartXmlSpy(fileXmlSpyDir.getText(), fileSTDBSCDir.getText());
				}
			} else if (testItem.equals(StringUtil.wireListTest)) {
				if (fileUUTDir.getText().equals("") || fileAdapterDir.getText().equals("")
						|| fileStationDir.getText().equals("") ||fileWireListDir.getText().equals("") 
						|| fileXmlSpyDir.getText().equals("")) {
					JOptionPane.showMessageDialog(f, "请确保路径填充完整！", "警告", JOptionPane.WARNING_MESSAGE);
				} else {

					StartXmlSpy(fileXmlSpyDir.getText(), fileUUTDir.getText(), fileAdapterDir.getText(),
							fileStationDir.getText(), fileWireListDir.getText());
				}
			} 

		};
		// XMLSpy按钮响应设置
		xmlSpyTestButton.addActionListener(xmlSpyListener);
		// 开始测试响应
		ActionListener startAction = e -> {
			String testItem = TestItemChooser.getSelectedItem().toString();
			String errorInfo = "";
			if (testItem.equals(StringUtil.configurationTest)) {
				if (fileConfigureDir.getText().equals("") || fileUUTDir.getText().equals("")
						|| fileStationDir.getText().equals("") || fileDescriptionDir.getText().equals("")
						|| fileAdapterDir.getText().equals("") || fileXmlSpyDir.getText().equals("")) {
					JOptionPane.showMessageDialog(f, "请确保路径填充完整！", "警告", JOptionPane.WARNING_MESSAGE);
				} else {
					errorInfo = configureTest();
					JOptionPane.showMessageDialog(f, "测试完成");
				}
			} else if (testItem.equals(StringUtil.UUTTest)) {
				if (fileUUTDir.getText().equals("") || fileXmlSpyDir.getText().equals("")) {
					JOptionPane.showMessageDialog(f, "请确保路径填充完整！", "警告", JOptionPane.WARNING_MESSAGE);
				} else {
					errorInfo = UUTTest();
					JOptionPane.showMessageDialog(f, "测试完成");
				}
			} else if (testItem.equals(StringUtil.adapterTest)) {
				if (fileUUTDir.getText().equals("") || fileAdapterDir.getText().equals("")
						|| fileXmlSpyDir.getText().equals("") || fileStationDir.getText().equals("")) {
					JOptionPane.showMessageDialog(f, "请确保路径填充完整！", "警告", JOptionPane.WARNING_MESSAGE);
				} else {
					errorInfo = adapterTest();
					JOptionPane.showMessageDialog(f, "测试完成");
				}
			} else if (testItem.equals(StringUtil.stationTest)) {
				if (fileStationDir.getText().equals("") || fileXmlSpyDir.getText()
						.equals("")/* ||fileInstDir.getText().equals("") */) {
					JOptionPane.showMessageDialog(f, "请确保路径填充完整！", "警告", JOptionPane.WARNING_MESSAGE);
				} else {
					errorInfo = stationTest();
					JOptionPane.showMessageDialog(f, "测试完成");
				}
			} else if (testItem.equals(StringUtil.testSignalTest)) {
				if (fileSTDBSCDir.getText().equals("") || fileXmlSpyDir.getText().equals("")
						|| fileSTDTSFLibDir.getText().equals("") || fileExtTSFLibDir.getText().equals("")) {
					JOptionPane.showMessageDialog(f, "请确保路径填充完整！", "警告", JOptionPane.WARNING_MESSAGE);
				} else {
					errorInfo = signalTest();
					JOptionPane.showMessageDialog(f, "测试完成");
				}
			} else if (testItem.equals(StringUtil.instrumentTest)) {
				if (fileSingleInstDir.getText().equals("") || fileXmlSpyDir.getText().equals("")) {
					JOptionPane.showMessageDialog(f, "请确保路径填充完整！", "警告", JOptionPane.WARNING_MESSAGE);
				} else {
					errorInfo = InstrumentTest();
					JOptionPane.showMessageDialog(f, "测试完成");
				}
			} else if (testItem.equals(StringUtil.testDescriptionTest)) {
				if (fileDescriptionDir.getText().equals("") || fileUUTDir.getText().equals("")
						|| fileXmlSpyDir.getText().equals("")) {
					JOptionPane.showMessageDialog(f, "请确保路径填充完整！", "警告", JOptionPane.WARNING_MESSAGE);
				} else {
					errorInfo = TestDescription();
					JOptionPane.showMessageDialog(f, "测试完成");
				}
			} else if (testItem.equals(StringUtil.wireListTest)) {
				if (fileUUTDir.getText().equals("") || fileAdapterDir.getText().equals("")
						|| fileStationDir.getText().equals("") ||fileWireListDir.getText().equals("") 
						|| fileXmlSpyDir.getText().equals("")) {
					JOptionPane.showMessageDialog(f, "请确保路径填充完整！", "警告", JOptionPane.WARNING_MESSAGE);
				} else {
					errorInfo = wireListTest();
					JOptionPane.showMessageDialog(f, "测试完成");
				}
			}
			updateError(errorInfo);
		};
		// 响应设置
		startTestButton.addActionListener(startAction);
		// 设置窗口样式
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		// 更新组件UI
		SwingUtilities.updateComponentTreeUI(f.getContentPane());
		SwingUtilities.updateComponentTreeUI(browseConfigurePop);
		SwingUtilities.updateComponentTreeUI(browseUUTPop);
		SwingUtilities.updateComponentTreeUI(browseAdapterPop);
		SwingUtilities.updateComponentTreeUI(browseStationPop);
		SwingUtilities.updateComponentTreeUI(browseDescriptionPop);
		SwingUtilities.updateComponentTreeUI(browseSTDBSCPop);
		SwingUtilities.updateComponentTreeUI(chooser);
		// 设置窗口不可调节大小
		f.setResizable(false);
		// 设置关闭窗口时，退出程序
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO 自动生成的方法存根

			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO 自动生成的方法存根
				f.repaint();
				f.validate();
				updateError(errorOutput.getText());
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO 自动生成的方法存根
				f.repaint();
				f.validate();
				updateError(errorOutput.getText());
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO 自动生成的方法存根

			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO 自动生成的方法存根

			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO 自动生成的方法存根

			}

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO 自动生成的方法存根

			}
		});
		// 移除SignalDir
		// checkPanel.remove(2);
		checkPanel.remove(5);
		checkPanel.remove(5);
		checkPanel.remove(5);
		checkPanel.remove(5);
		checkPanel.remove(5);
		f.pack();
		// 设置窗口居中
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		checkPanel.removeAll();
		checkPanel.add(descriptionPanel);
		checkPanel.add(UUTPanel);
		checkPanel.add(xmlSpyPanel);
		checkPanel.add(startPanel);
	}

	private String TestDescription() {
		// TODO 自动生成的方法存根
		TDReader tdReader = new TDReader();
		tdReader.VirtualMain(fileDescriptionDir.getText(), fileUUTDir.getText());

		return tdReader.getInfoHTML();
	}

	private String InstrumentTest() {
		// TODO 自动生成的方法存根
		InstrumentReader instreader = new InstrumentReader();
		instreader.VirtualMain(fileSingleInstDir.getText());
		return instreader.getInfoHTML();

	}

	private void StartStationXmlSpy(String xmlSpyPath, String filepath1, String filepath2) {
		// TODO 自动生成的方法存根
		Runtime runtime = Runtime.getRuntime();

		GenerateInstrumentFilePaths(filepath2);

		String[] commandArgs = new String[2 + instpathlist.size()];
		commandArgs[0] = xmlSpyPath;
		commandArgs[1] = filepath1;
		for (int i = 0; i < instpathlist.size(); i++) {
			commandArgs[i + 2] = instpathlist.get(i);
		}

		try {
			Process process = runtime.exec(commandArgs);
			// exitcode=process.waitFor();
			// System.out.println("Finish:"+exitcode);

		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	private void StartXmlSpy(String xmlSpyPath, String filepath1, String filepath2, String filepath3) {
		// TODO 自动生成的方法存根
		Runtime runtime = Runtime.getRuntime();
		String[] commandArgs = { xmlSpyPath, filepath1, filepath2, filepath3 };
		try {
			Process process = runtime.exec(commandArgs);

		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	private void StartXmlSpy(String xmlSpyPath, String filepath1, String filepath2, String filepath3, String filepath4) {
		// TODO 自动生成的方法存根
		Runtime runtime = Runtime.getRuntime();
		String[] commandArgs = { xmlSpyPath, filepath1, filepath2, filepath3, filepath4};
		try {
			Process process = runtime.exec(commandArgs);

		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	private void StartXmlSpy(String xmlSpyPath, String filepath1, String filepath2, String filepath3, String filepath4,
			String filepath5) {
		// TODO 自动生成的方法存根

		Runtime runtime = Runtime.getRuntime();
		String[] commandArgs = { xmlSpyPath, filepath1, filepath2, filepath3, filepath4, filepath5 };
		try {
			Process process = runtime.exec(commandArgs);

		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	private void StartXmlSpy(String xmlSpyPath, String filepath1, String filepath2) {
		// TODO 自动生成的方法存根
		// int exitcode=-1;
		Runtime runtime = Runtime.getRuntime();

		// GenerateInstrumentFilePaths(filepath2);
		String[] commandArgs = { xmlSpyPath, filepath1, filepath2 };

		try {
			Process process = runtime.exec(commandArgs);
			// exitcode=process.waitFor();
			// System.out.println("Finish:"+exitcode);

		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	private void GenerateInstrumentFilePaths(String filepath2) {
		// TODO 自动生成的方法存根
		try {
			File file = new File(filepath2);
			if (!file.isDirectory()) {
				System.out.println("文件\n");
				System.out.println("绝对路径=" + file.getAbsolutePath());
			} else {
				System.out.println("文件夹\n");
				FileFilter xmlfilter = new FileFilterBySuffix(".xml");

				GetFilePathList(file, xmlfilter);

			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}

	private void GetFilePathList(File file, FileFilter xmlfilter) {
		// TODO 自动生成的方法存根
		File[] xmlfiles = file.listFiles();
		for (File tempfile : xmlfiles) {
			if (!tempfile.isDirectory() && xmlfilter.accept(tempfile)) {
				instpathlist.add(tempfile.getAbsolutePath());
			}
		}
	}

	private void StartXmlSpy(String xmlSpyPath, String filepath1) {
		// TODO 自动生成的方法存根
		// int exitcode=-1;
		Runtime runtime = Runtime.getRuntime();
		String[] commandArgs = { xmlSpyPath, filepath1 };
		try {
			Process process = runtime.exec(commandArgs);
			// exitcode=process.waitFor();
			// System.out.println("Finish:"+exitcode);

		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	private String configureTest() {
		ConfigurationReader configurationReader = new ConfigurationReader();
		configurationReader.configurationReader(fileConfigureDir.getText(), fileUUTDir.getText(),
				fileStationDir.getText(), fileAdapterDir.getText(), fileDescriptionDir.getText());
		return configurationReader.getInfoHTML();
	}

	private String UUTTest() {

		UUTDReader ureader = new UUTDReader();
		ureader.VirtualMain(fileUUTDir.getText());
		return ureader.getInfoHTML();
	}

	private String adapterTest() {
		AdapterReader adapterReader = new AdapterReader();
		adapterReader.adapterReader(fileAdapterDir.getText(), fileUUTDir.getText(), fileStationDir.getText());
		return adapterReader.getInfoHTML();
	}
	
	private String wireListTest() {
		WireListReader WireListReader = new WireListReader();
		WireListReader.Virtualmain(fileWireListDir.getText(), fileUUTDir.getText(), fileStationDir.getText(), fileAdapterDir.getText());
		return WireListReader.getInfoHTML();
	}

	private String stationTest() {
		TestStationReader tsReader = new TestStationReader();
		tsReader.VirtualMain(fileStationDir.getText(), fileInstDir.getText());
		return tsReader.getInfoHTML();
	}

	private String signalTest() {
		TestSignalReader tsigReader = new TestSignalReader();
		tsigReader.VirtualMain(fileSTDBSCDir.getText(), fileSTDTSFLibDir.getText(), fileExtTSFLibDir.getText());
		return tsigReader.getInfoHTML();
	}

	private void updateError(String errorInfo) {
		/*
		 * errorOutput.setFont(new Font("StSong", Font.BOLD, 14));
		 * errorOutput.setText("这是测试文本！\n"); errorOutput.setFont(new
		 * Font("StSong", Font.BOLD, 20)); errorOutput.setForeground(Color.RED);
		 */
		// errorOutput.setText("这是测试文本！这是测试文本！<h1>这是测试文本！</h1><br>这是测试文本！<font
		// color='red'>这是测试文本！</font>这是测试文本！\n这是测试文本！\n这是测试文本！\n这是测试文本！\n这是测试文本！\n这是测试文本！\n这是测试文本！\n这是测试文本！\n这是测试文本！\n这是测试文本！\n这是测试文本！\n这是测试文本！\n这是测试文本！\n这是测试文本！\n");
		errorOutput.setText(errorInfo);
	}

	public static void main(String[] args) {

		new ATMLTest().init();
	}

}

class ExtensionFileFilter extends FileFilter {
	private String description;
	private ArrayList<String> extensions = new ArrayList<>();

	// 自定义方法，用于添加文件扩展名
	public void addExtension(String extension) {
		if (!extension.startsWith(".")) {
			extension = "." + extension;
			extensions.add(extension.toLowerCase());
		}
	}

	// 用于设置该文件过滤器的描述文本
	public void setDescription(String aDescription) {
		description = aDescription;
	}

	// 继承FileFilter类必须实现的抽象方法，返回该文件过滤器的描述文本
	public String getDescription() {
		return description;
	}

	// 继承FileFilter类必须实现的抽象方法，判断该文件过滤器是否接受该文件
	public boolean accept(File f) {
		// 如果该文件是路径，接受该文件
		if (f.isDirectory())
			return true;
		// 将文件名转为小写（全部转为小写后比较，用于忽略文件名大小写）
		String name = f.getName().toLowerCase();
		// 遍历所有可接受的扩展名，如果扩展名相同，该文件就可接受。
		for (String extension : extensions) {
			if (name.endsWith(extension)) {
				return true;
			}
		}
		return false;
	}
}
