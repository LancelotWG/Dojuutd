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
	// ���������
	JFrame f = new JFrame("ATML��鹤��");
	// ����ѡ��
	// String[] testItem = new String[] { "ConfigureTest", "UUTTest",
	// "AdapterTest", "StationTest",
	// "SignalTest","InstrumentTest","TestDescriptionTest" };
	JLabel TestItemLabel = new JLabel("������Ŀ��");
	JComboBox<String> TestItemChooser = new JComboBox<>(StringUtil.testItem);
	// ��̬�ı�
	JLabel fileXmlSpyLabel = new JLabel("XMLSpy·����", SwingConstants.LEFT);
	JLabel fileConfigureDirLabel = new JLabel("TestConfiguration�ļ�·����", SwingConstants.LEFT);
	JLabel fileUUTDirLabel = new JLabel("UUTDescription�ļ�·����");
	JLabel fileAdapterDirLabel = new JLabel("TestAdapter�ļ�·����");
	JLabel fileStationDirLabel = new JLabel("TestStationDescription�ļ�·����");
	JLabel fileDescriptionDirLabel = new JLabel("TestDescription�ļ�·����");
	JLabel fileWireListDirLabel = new JLabel("TestWireList�ļ�·����");
	JLabel fileSTDBSCDirLabel = new JLabel("STDBSC�ļ�·����");
	JLabel fileSTDTSFLibDirLabel = new JLabel("STDTSFLib�ļ���·����");
	JLabel fileExtTSFLibDirLabel = new JLabel("ExtTSFLib�ļ���·����");

	JLabel fileResultDirLabel = new JLabel("TestResult�ļ�·����");
	JLabel fileInstDirLabel = new JLabel("Instruments�ļ���·����");
	JLabel fileSingleInstDirLabel = new JLabel("Instrument�ļ�·����");

	// �ı���
	// ����·��
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
	// �����Ϣ����ı���
	JTextPane errorOutput = new JTextPane();
	// ��ť
	JButton xmlSpyTestButton = new JButton("XMLSpy��ʽ���");
	JButton startTestButton = new JButton("������");
	// ͼ��
	Icon openIcon = new ImageIcon(R.class.getResource("/open.png"));
	ImageIcon logo = new ImageIcon(R.class.getResource("/logo.png"));
	// **********mapͼƬ*************
	// ScaleIcon mapIcon = new ScaleIcon(new ImageIcon("ico/map.jpg"));
	ScaleIcon mapIcon = new ScaleIcon(new ImageIcon(R.class.getResource("/CATIC.jpg")));
	// ImageIcon mapIcon = new ImageIcon("ico/map.jpg");
	// map��
	JLabel mapLabel = new JLabel();
	// ��������˵�
	// XMLSpy
	JPopupMenu browseXMLSpyPop = new JPopupMenu();
	JMenuItem browseXMLSpyItem = new JMenuItem("���", openIcon);
	// ConfigurePop
	JPopupMenu browseConfigurePop = new JPopupMenu();
	JMenuItem browseConfigureItem = new JMenuItem("���", openIcon);
	// UUTPop
	JPopupMenu browseUUTPop = new JPopupMenu();
	JMenuItem browseUUTItem = new JMenuItem("���", openIcon);
	// AdapterPop
	JPopupMenu browseAdapterPop = new JPopupMenu();
	JMenuItem browseAdapterItem = new JMenuItem("���", openIcon);
	// StationPop
	JPopupMenu browseStationPop = new JPopupMenu();
	JMenuItem browseStationItem = new JMenuItem("���", openIcon);
	// WireListPop
	JPopupMenu browseWireListPop = new JPopupMenu();
	JMenuItem browseWireListItem = new JMenuItem("���", openIcon);
	// DescriptionPop
	JPopupMenu browseDescriptionPop = new JPopupMenu();
	JMenuItem browseDescriptionItem = new JMenuItem("���", openIcon);
	// STDBSCPop
	JPopupMenu browseSTDBSCPop = new JPopupMenu();
	JMenuItem browseSTDBSCItem = new JMenuItem("���", openIcon);
	// STDTSFLibPop
	JPopupMenu browseSTDTSFLibPop = new JPopupMenu();
	JMenuItem browseSTDTSFLibItem = new JMenuItem("���", openIcon);
	// ExtTSFLibPop
	JPopupMenu browseExtTSFLibPop = new JPopupMenu();
	JMenuItem browseExtTSFLibItem = new JMenuItem("���", openIcon);
	// ResultPop
	JPopupMenu browseResultPop = new JPopupMenu();
	JMenuItem browseResultItem = new JMenuItem("���", openIcon);
	// Instruments
	JPopupMenu browseInstPop = new JPopupMenu();
	JMenuItem browseInstItem = new JMenuItem("���", openIcon);
	// Single Instrument
	JPopupMenu browseSingleInstPop = new JPopupMenu();
	JMenuItem browseSingleInstItem = new JMenuItem("���", openIcon);
	// �ļ�ѡ����
	JFileChooser chooser = new JFileChooser(".");
	ExtensionFileFilter fileFilter = new ExtensionFileFilter();
	ExtensionFileFilter fileExeFilter = new ExtensionFileFilter();
	ExtensionFileFilter fileTsgFilter = new ExtensionFileFilter();

	// Instrument·���б�
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
		// ���Խ���߿�����
		Border lb = BorderFactory.createLineBorder(Color.BLACK, 1);
		TitledBorder tb = new TitledBorder(lb, "���Խ����", TitledBorder.LEFT, TitledBorder.TOP,
				new Font("StSong", Font.BOLD, 14), Color.BLACK);
		errorOutput.setPreferredSize(new Dimension(730, 220));

		errorOutput.setEditable(false);
		// ����HTML������
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
		// ����ͼ��
		f.setIconImage(logo.getImage());
		// ����
		// mapͼƬ����

		mapLabel.setIcon(mapIcon);

		mapLabel.setPreferredSize(new Dimension(745, 90));
		// mapͼƬ����
		JPanel mapPanel = new JPanel();
		mapPanel.add(mapLabel);
		// ����ѡ��򲼾�
		JPanel testItemPanel = new JPanel();
		testItemPanel.add(TestItemLabel);
		testItemPanel.add(TestItemChooser);
		// top������
		Box topPanel = Box.createVerticalBox();
		topPanel.setAlignmentX(1);
		topPanel.add(mapPanel);
		topPanel.add(testItemPanel);

		f.add(topPanel, BorderLayout.NORTH);
		// configure·������
		JPanel configurePanel = new JPanel();
		addDirComponent(configurePanel, fileConfigureDirLabel, fileConfigureDir);
		// UUT·������
		JPanel UUTPanel = new JPanel();
		addDirComponent(UUTPanel, fileUUTDirLabel, fileUUTDir);
		// adapter·������
		JPanel adapterPanel = new JPanel();
		addDirComponent(adapterPanel, fileAdapterDirLabel, fileAdapterDir);
		// station·������
		JPanel stationPanel = new JPanel();
		addDirComponent(stationPanel, fileStationDirLabel, fileStationDir);
		// description·������
		JPanel descriptionPanel = new JPanel();
		addDirComponent(descriptionPanel, fileDescriptionDirLabel, fileDescriptionDir);
		// description·������
		JPanel wireListPanel = new JPanel();
		addDirComponent(wireListPanel, fileWireListDirLabel, fileWireListDir);
		// STDBSC·������
		JPanel STDBSCPanel = new JPanel();
		addDirComponent(STDBSCPanel, fileSTDBSCDirLabel, fileSTDBSCDir);
		// STDBSC·������
		JPanel STDTSFLibPanel = new JPanel();
		addDirComponent(STDTSFLibPanel, fileSTDTSFLibDirLabel, fileSTDTSFLibDir);
		// STDBSC·������
		JPanel ExtTSFLibPanel = new JPanel();
		addDirComponent(ExtTSFLibPanel, fileExtTSFLibDirLabel, fileExtTSFLibDir);
		// result·������
		JPanel resultPanel = new JPanel();
		addDirComponent(resultPanel, fileResultDirLabel, fileResultDir);
		// Instruments·������
		JPanel instPanel = new JPanel();
		addDirComponent(instPanel, fileInstDirLabel, fileInstDir);
		// Instruments·������
		JPanel singleInstPanel = new JPanel();
		addDirComponent(singleInstPanel, fileSingleInstDirLabel, fileSingleInstDir);

		// XMLSpy·������
		JPanel xmlSpyPanel = new JPanel();
		addDirComponent(xmlSpyPanel, fileXmlSpyLabel, fileXmlSpyDir);
		// ��ʼ���԰�ť����
		JPanel startPanel = new JPanel();
		startPanel.add(xmlSpyTestButton);
		startPanel.add(startTestButton);
		// ��ʼ���԰�ť���֣��޿�ʼ��⣩
		// ����������
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
		// ��Ϣ�����
		addErrorField(f);
		// ������Ŀ�л�ʱ����Ӧ
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
		// ��Ӧ����
		TestItemChooser.addActionListener(changeTestItem);
		// �ļ�����������
		fileFilter.addExtension("xml");
		fileFilter.setDescription("*.xml");

		fileExeFilter.addExtension("exe");
		fileExeFilter.setDescription("*.exe");

		fileTsgFilter.addExtension("tsg");
		fileTsgFilter.setDescription("*.tsg");

		chooser.addChoosableFileFilter(fileFilter);
		chooser.setAcceptAllFileFilterUsed(false);
		// �ļ�����¼���Ӧ
		ActionListener openFlie = e -> {
			int result;
			if (e.getSource() == browseXMLSpyItem) {
				chooser.resetChoosableFileFilters();
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.addChoosableFileFilter(fileExeFilter);
				result = chooser.showDialog(f, "ѡ��XMLSpy����");
			} else if (e.getSource() == browseInstItem || e.getSource() == browseExtTSFLibItem
					|| e.getSource() == browseSTDTSFLibItem) {
				chooser.resetChoosableFileFilters();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				result = chooser.showDialog(f, "ѡ���ļ���");
				// chooser.addChoosableFileFilter(fileTsgFilter);
			} else {
				chooser.resetChoosableFileFilters();
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.addChoosableFileFilter(fileFilter);
				result = chooser.showDialog(f, "ѡ��XML�ļ�");
			}
			// int result = chooser.showDialog(f, "���ļ�");
			if (result == JFileChooser.APPROVE_OPTION) {
				String path = chooser.getSelectedFile().getPath();
				System.out.println(path);
				if (e.getSource() == browseConfigureItem) {
					if (fileTypeValidation(path, StringUtil.configuration)) {
						fileConfigureDir.setText(path);
					} else {
						JOptionPane.showMessageDialog(f, "��������Ч��" + StringUtil.configuration + "�����ļ�", "�ļ����ʹ���",
								JOptionPane.ERROR_MESSAGE);
					}
				} else if (e.getSource() == browseUUTItem) {
					if (fileTypeValidation(path, StringUtil.UUT)) {
						fileUUTDir.setText(path);
					} else {
						JOptionPane.showMessageDialog(f, "��������Ч��" + StringUtil.UUT + "�����ļ�", "�ļ����ʹ���",
								JOptionPane.ERROR_MESSAGE);
					}
				} else if (e.getSource() == browseAdapterItem) {
					if (fileTypeValidation(path, StringUtil.adapter)) {
						fileAdapterDir.setText(path);
					} else {
						JOptionPane.showMessageDialog(f, "��������Ч��" + StringUtil.adapter + "�����ļ�", "�ļ����ʹ���",
								JOptionPane.ERROR_MESSAGE);
					}
				} else if (e.getSource() == browseStationItem) {
					if (fileTypeValidation(path, StringUtil.station)) {
						fileStationDir.setText(path);
					} else {
						JOptionPane.showMessageDialog(f, "��������Ч��" + StringUtil.station + "�����ļ�", "�ļ����ʹ���",
								JOptionPane.ERROR_MESSAGE);
					}
				} else if (e.getSource() == browseSTDBSCItem) {
					if (fileTypeValidation(path, StringUtil.testSignal)) {
						fileSTDBSCDir.setText(path);
					} else {
						JOptionPane.showMessageDialog(f, "��������Ч��" + StringUtil.testSignal + "�����ļ�", "�ļ����ʹ���",
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
						JOptionPane.showMessageDialog(f, "��������Ч��" + StringUtil.testDescription + "�����ļ�", "�ļ����ʹ���",
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
						JOptionPane.showMessageDialog(f, "��������Ч��" + StringUtil.instrument + "�����ļ�", "�ļ����ʹ���",
								JOptionPane.ERROR_MESSAGE);
					}
				} else if (e.getSource() == browseResultItem) {
					if (fileTypeValidation(path, StringUtil.result)) {
						fileResultDir.setText(path);
					} else {
						JOptionPane.showMessageDialog(f, "��������Ч��" + StringUtil.result + "�����ļ�", "�ļ����ʹ���",
								JOptionPane.ERROR_MESSAGE);
					}
				} else if (e.getSource() == browseWireListItem) {
					if (fileTypeValidation(path, StringUtil.wireList)) {
						fileWireListDir.setText(path);
					} else {
						JOptionPane.showMessageDialog(f, "��������Ч��" + StringUtil.wireList + "�����ļ�", "�ļ����ʹ���",
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
		// XMLSpy������Ӧ
		ActionListener xmlSpyListener = e -> {
			String testItem = TestItemChooser.getSelectedItem().toString();

			if (testItem.equals(StringUtil.configurationTest)) {
				if (fileConfigureDir.getText().equals("") || fileUUTDir.getText().equals("")
						|| fileStationDir.getText().equals("") || fileDescriptionDir.getText().equals("")
						|| fileXmlSpyDir.getText().equals("") || fileAdapterDir.getText().equals("")) {
					JOptionPane.showMessageDialog(f, "��ȷ��·�����������", "����", JOptionPane.WARNING_MESSAGE);
				} else {
					StartXmlSpy(fileXmlSpyDir.getText(), fileConfigureDir.getText(), fileUUTDir.getText(),
							fileStationDir.getText(), fileDescriptionDir.getText(), fileAdapterDir.getText());

				}
			} else if (testItem.equals(StringUtil.UUTTest)) {
				if (fileUUTDir.getText().equals("") || fileXmlSpyDir.getText().equals("")) {
					JOptionPane.showMessageDialog(f, "��ȷ��·�����������", "����", JOptionPane.WARNING_MESSAGE);
				} else {
					StartXmlSpy(fileXmlSpyDir.getText(), fileUUTDir.getText());

				}
			} else if (testItem.equals(StringUtil.adapterTest)) {
				if (fileUUTDir.getText().equals("") || fileAdapterDir.getText().equals("")
						|| fileStationDir.getText().equals("") || fileXmlSpyDir.getText().equals("")) {
					JOptionPane.showMessageDialog(f, "��ȷ��·�����������", "����", JOptionPane.WARNING_MESSAGE);
				} else {

					StartXmlSpy(fileXmlSpyDir.getText(), fileUUTDir.getText(), fileAdapterDir.getText(),
							fileStationDir.getText());
				}
			} else if (testItem.equals(StringUtil.stationTest)) {
				if (fileStationDir.getText().equals("") || fileXmlSpyDir.getText().equals("")
						|| fileInstDir.getText().equals("")) {
					JOptionPane.showMessageDialog(f, "��ȷ��·�����������", "����", JOptionPane.WARNING_MESSAGE);
				} else {
					StartStationXmlSpy(fileXmlSpyDir.getText(), fileStationDir.getText(), fileInstDir.getText());
				}
			} else if (testItem.equals(StringUtil.instrumentTest)) {
				if (fileSingleInstDir.getText().equals("") || fileXmlSpyDir.getText().equals("")) {
					JOptionPane.showMessageDialog(f, "��ȷ��·�����������", "����", JOptionPane.WARNING_MESSAGE);
				} else {
					StartXmlSpy(fileXmlSpyDir.getText(), fileSingleInstDir.getText());
				}
			} else if (testItem.equals(StringUtil.testDescriptionTest)) {
				if (fileDescriptionDir.getText().equals("") || fileUUTDir.getText().equals("")
						|| fileXmlSpyDir.getText().equals("")) {
					JOptionPane.showMessageDialog(f, "��ȷ��·�����������", "����", JOptionPane.WARNING_MESSAGE);
				} else {
					StartXmlSpy(fileXmlSpyDir.getText(), fileDescriptionDir.getText(), fileUUTDir.getText());
				}
			} else if (testItem.equals(StringUtil.resultTest)) {
				if (fileResultDir.getText().equals("") || fileXmlSpyDir.getText().equals("")) {
					JOptionPane.showMessageDialog(f, "��ȷ��·�����������", "����", JOptionPane.WARNING_MESSAGE);
				} else {
					StartXmlSpy(fileXmlSpyDir.getText(), fileResultDir.getText());
				}
			} else if (testItem.equals(StringUtil.testSignalTest)) {
				if (fileSTDBSCDir.getText().equals("") || fileXmlSpyDir.getText().equals("")
						|| fileSTDTSFLibDir.getText().equals("") || fileExtTSFLibDir.getText().equals("")) {
					JOptionPane.showMessageDialog(f, "��ȷ��·�����������", "����", JOptionPane.WARNING_MESSAGE);
				} else {
					StartXmlSpy(fileXmlSpyDir.getText(), fileSTDBSCDir.getText());
				}
			} else if (testItem.equals(StringUtil.wireListTest)) {
				if (fileUUTDir.getText().equals("") || fileAdapterDir.getText().equals("")
						|| fileStationDir.getText().equals("") ||fileWireListDir.getText().equals("") 
						|| fileXmlSpyDir.getText().equals("")) {
					JOptionPane.showMessageDialog(f, "��ȷ��·�����������", "����", JOptionPane.WARNING_MESSAGE);
				} else {

					StartXmlSpy(fileXmlSpyDir.getText(), fileUUTDir.getText(), fileAdapterDir.getText(),
							fileStationDir.getText(), fileWireListDir.getText());
				}
			} 

		};
		// XMLSpy��ť��Ӧ����
		xmlSpyTestButton.addActionListener(xmlSpyListener);
		// ��ʼ������Ӧ
		ActionListener startAction = e -> {
			String testItem = TestItemChooser.getSelectedItem().toString();
			String errorInfo = "";
			if (testItem.equals(StringUtil.configurationTest)) {
				if (fileConfigureDir.getText().equals("") || fileUUTDir.getText().equals("")
						|| fileStationDir.getText().equals("") || fileDescriptionDir.getText().equals("")
						|| fileAdapterDir.getText().equals("") || fileXmlSpyDir.getText().equals("")) {
					JOptionPane.showMessageDialog(f, "��ȷ��·�����������", "����", JOptionPane.WARNING_MESSAGE);
				} else {
					errorInfo = configureTest();
					JOptionPane.showMessageDialog(f, "�������");
				}
			} else if (testItem.equals(StringUtil.UUTTest)) {
				if (fileUUTDir.getText().equals("") || fileXmlSpyDir.getText().equals("")) {
					JOptionPane.showMessageDialog(f, "��ȷ��·�����������", "����", JOptionPane.WARNING_MESSAGE);
				} else {
					errorInfo = UUTTest();
					JOptionPane.showMessageDialog(f, "�������");
				}
			} else if (testItem.equals(StringUtil.adapterTest)) {
				if (fileUUTDir.getText().equals("") || fileAdapterDir.getText().equals("")
						|| fileXmlSpyDir.getText().equals("") || fileStationDir.getText().equals("")) {
					JOptionPane.showMessageDialog(f, "��ȷ��·�����������", "����", JOptionPane.WARNING_MESSAGE);
				} else {
					errorInfo = adapterTest();
					JOptionPane.showMessageDialog(f, "�������");
				}
			} else if (testItem.equals(StringUtil.stationTest)) {
				if (fileStationDir.getText().equals("") || fileXmlSpyDir.getText()
						.equals("")/* ||fileInstDir.getText().equals("") */) {
					JOptionPane.showMessageDialog(f, "��ȷ��·�����������", "����", JOptionPane.WARNING_MESSAGE);
				} else {
					errorInfo = stationTest();
					JOptionPane.showMessageDialog(f, "�������");
				}
			} else if (testItem.equals(StringUtil.testSignalTest)) {
				if (fileSTDBSCDir.getText().equals("") || fileXmlSpyDir.getText().equals("")
						|| fileSTDTSFLibDir.getText().equals("") || fileExtTSFLibDir.getText().equals("")) {
					JOptionPane.showMessageDialog(f, "��ȷ��·�����������", "����", JOptionPane.WARNING_MESSAGE);
				} else {
					errorInfo = signalTest();
					JOptionPane.showMessageDialog(f, "�������");
				}
			} else if (testItem.equals(StringUtil.instrumentTest)) {
				if (fileSingleInstDir.getText().equals("") || fileXmlSpyDir.getText().equals("")) {
					JOptionPane.showMessageDialog(f, "��ȷ��·�����������", "����", JOptionPane.WARNING_MESSAGE);
				} else {
					errorInfo = InstrumentTest();
					JOptionPane.showMessageDialog(f, "�������");
				}
			} else if (testItem.equals(StringUtil.testDescriptionTest)) {
				if (fileDescriptionDir.getText().equals("") || fileUUTDir.getText().equals("")
						|| fileXmlSpyDir.getText().equals("")) {
					JOptionPane.showMessageDialog(f, "��ȷ��·�����������", "����", JOptionPane.WARNING_MESSAGE);
				} else {
					errorInfo = TestDescription();
					JOptionPane.showMessageDialog(f, "�������");
				}
			} else if (testItem.equals(StringUtil.wireListTest)) {
				if (fileUUTDir.getText().equals("") || fileAdapterDir.getText().equals("")
						|| fileStationDir.getText().equals("") ||fileWireListDir.getText().equals("") 
						|| fileXmlSpyDir.getText().equals("")) {
					JOptionPane.showMessageDialog(f, "��ȷ��·�����������", "����", JOptionPane.WARNING_MESSAGE);
				} else {
					errorInfo = wireListTest();
					JOptionPane.showMessageDialog(f, "�������");
				}
			}
			updateError(errorInfo);
		};
		// ��Ӧ����
		startTestButton.addActionListener(startAction);
		// ���ô�����ʽ
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}
		// �������UI
		SwingUtilities.updateComponentTreeUI(f.getContentPane());
		SwingUtilities.updateComponentTreeUI(browseConfigurePop);
		SwingUtilities.updateComponentTreeUI(browseUUTPop);
		SwingUtilities.updateComponentTreeUI(browseAdapterPop);
		SwingUtilities.updateComponentTreeUI(browseStationPop);
		SwingUtilities.updateComponentTreeUI(browseDescriptionPop);
		SwingUtilities.updateComponentTreeUI(browseSTDBSCPop);
		SwingUtilities.updateComponentTreeUI(chooser);
		// ���ô��ڲ��ɵ��ڴ�С
		f.setResizable(false);
		// ���ùرմ���ʱ���˳�����
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO �Զ����ɵķ������

			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO �Զ����ɵķ������
				f.repaint();
				f.validate();
				updateError(errorOutput.getText());
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO �Զ����ɵķ������
				f.repaint();
				f.validate();
				updateError(errorOutput.getText());
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO �Զ����ɵķ������

			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO �Զ����ɵķ������

			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO �Զ����ɵķ������

			}

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO �Զ����ɵķ������

			}
		});
		// �Ƴ�SignalDir
		// checkPanel.remove(2);
		checkPanel.remove(5);
		checkPanel.remove(5);
		checkPanel.remove(5);
		checkPanel.remove(5);
		checkPanel.remove(5);
		f.pack();
		// ���ô��ھ���
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		checkPanel.removeAll();
		checkPanel.add(descriptionPanel);
		checkPanel.add(UUTPanel);
		checkPanel.add(xmlSpyPanel);
		checkPanel.add(startPanel);
	}

	private String TestDescription() {
		// TODO �Զ����ɵķ������
		TDReader tdReader = new TDReader();
		tdReader.VirtualMain(fileDescriptionDir.getText(), fileUUTDir.getText());

		return tdReader.getInfoHTML();
	}

	private String InstrumentTest() {
		// TODO �Զ����ɵķ������
		InstrumentReader instreader = new InstrumentReader();
		instreader.VirtualMain(fileSingleInstDir.getText());
		return instreader.getInfoHTML();

	}

	private void StartStationXmlSpy(String xmlSpyPath, String filepath1, String filepath2) {
		// TODO �Զ����ɵķ������
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
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}

	private void StartXmlSpy(String xmlSpyPath, String filepath1, String filepath2, String filepath3) {
		// TODO �Զ����ɵķ������
		Runtime runtime = Runtime.getRuntime();
		String[] commandArgs = { xmlSpyPath, filepath1, filepath2, filepath3 };
		try {
			Process process = runtime.exec(commandArgs);

		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	
	private void StartXmlSpy(String xmlSpyPath, String filepath1, String filepath2, String filepath3, String filepath4) {
		// TODO �Զ����ɵķ������
		Runtime runtime = Runtime.getRuntime();
		String[] commandArgs = { xmlSpyPath, filepath1, filepath2, filepath3, filepath4};
		try {
			Process process = runtime.exec(commandArgs);

		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}

	private void StartXmlSpy(String xmlSpyPath, String filepath1, String filepath2, String filepath3, String filepath4,
			String filepath5) {
		// TODO �Զ����ɵķ������

		Runtime runtime = Runtime.getRuntime();
		String[] commandArgs = { xmlSpyPath, filepath1, filepath2, filepath3, filepath4, filepath5 };
		try {
			Process process = runtime.exec(commandArgs);

		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}

	private void StartXmlSpy(String xmlSpyPath, String filepath1, String filepath2) {
		// TODO �Զ����ɵķ������
		// int exitcode=-1;
		Runtime runtime = Runtime.getRuntime();

		// GenerateInstrumentFilePaths(filepath2);
		String[] commandArgs = { xmlSpyPath, filepath1, filepath2 };

		try {
			Process process = runtime.exec(commandArgs);
			// exitcode=process.waitFor();
			// System.out.println("Finish:"+exitcode);

		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}

	private void GenerateInstrumentFilePaths(String filepath2) {
		// TODO �Զ����ɵķ������
		try {
			File file = new File(filepath2);
			if (!file.isDirectory()) {
				System.out.println("�ļ�\n");
				System.out.println("����·��=" + file.getAbsolutePath());
			} else {
				System.out.println("�ļ���\n");
				FileFilter xmlfilter = new FileFilterBySuffix(".xml");

				GetFilePathList(file, xmlfilter);

			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}

	private void GetFilePathList(File file, FileFilter xmlfilter) {
		// TODO �Զ����ɵķ������
		File[] xmlfiles = file.listFiles();
		for (File tempfile : xmlfiles) {
			if (!tempfile.isDirectory() && xmlfilter.accept(tempfile)) {
				instpathlist.add(tempfile.getAbsolutePath());
			}
		}
	}

	private void StartXmlSpy(String xmlSpyPath, String filepath1) {
		// TODO �Զ����ɵķ������
		// int exitcode=-1;
		Runtime runtime = Runtime.getRuntime();
		String[] commandArgs = { xmlSpyPath, filepath1 };
		try {
			Process process = runtime.exec(commandArgs);
			// exitcode=process.waitFor();
			// System.out.println("Finish:"+exitcode);

		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
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
		 * errorOutput.setText("���ǲ����ı���\n"); errorOutput.setFont(new
		 * Font("StSong", Font.BOLD, 20)); errorOutput.setForeground(Color.RED);
		 */
		// errorOutput.setText("���ǲ����ı������ǲ����ı���<h1>���ǲ����ı���</h1><br>���ǲ����ı���<font
		// color='red'>���ǲ����ı���</font>���ǲ����ı���\n���ǲ����ı���\n���ǲ����ı���\n���ǲ����ı���\n���ǲ����ı���\n���ǲ����ı���\n���ǲ����ı���\n���ǲ����ı���\n���ǲ����ı���\n���ǲ����ı���\n���ǲ����ı���\n���ǲ����ı���\n���ǲ����ı���\n���ǲ����ı���\n");
		errorOutput.setText(errorInfo);
	}

	public static void main(String[] args) {

		new ATMLTest().init();
	}

}

class ExtensionFileFilter extends FileFilter {
	private String description;
	private ArrayList<String> extensions = new ArrayList<>();

	// �Զ��巽������������ļ���չ��
	public void addExtension(String extension) {
		if (!extension.startsWith(".")) {
			extension = "." + extension;
			extensions.add(extension.toLowerCase());
		}
	}

	// �������ø��ļ��������������ı�
	public void setDescription(String aDescription) {
		description = aDescription;
	}

	// �̳�FileFilter�����ʵ�ֵĳ��󷽷������ظ��ļ��������������ı�
	public String getDescription() {
		return description;
	}

	// �̳�FileFilter�����ʵ�ֵĳ��󷽷����жϸ��ļ��������Ƿ���ܸ��ļ�
	public boolean accept(File f) {
		// ������ļ���·�������ܸ��ļ�
		if (f.isDirectory())
			return true;
		// ���ļ���תΪСд��ȫ��תΪСд��Ƚϣ����ں����ļ�����Сд��
		String name = f.getName().toLowerCase();
		// �������пɽ��ܵ���չ���������չ����ͬ�����ļ��Ϳɽ��ܡ�
		for (String extension : extensions) {
			if (name.endsWith(extension)) {
				return true;
			}
		}
		return false;
	}
}
