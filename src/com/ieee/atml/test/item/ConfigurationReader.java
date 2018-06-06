package com.ieee.atml.test.item;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ieee.atml.info.InfoWrite;
import com.ieee.atml.util.StringUtil;

public class ConfigurationReader extends InfoWrite {
	private static final String UUTDir = "UUTDescriptionConvert_1_�ɿؼ����MDI_���ֲ���ϵͳ_2_�ɿؼ����-MDIģ��_4.xml";
	private static final String configurationDir = "TestConfigurationConvert_1_�ɿؼ����MDI_���ֲ���ϵͳ_2.xml";
	private static final String adapterDir = "TestAdapterConvert_1_�ɿؼ����MDI_���ֲ���ϵͳ_2_����ITA_4.xml";
	private static final String stationDir = "TestConfigurationConvert_1_�ɿؼ����MDI_���ֲ���ϵͳ_2.xml";
	private static final String testDescriptionDir = "TestDescriptionConvert_1_�ɿؼ����MDI_���ֲ���ϵͳ_2(20161213).xml";

	// private static final String[] testItem = { "��Դ�ĵ���UUID��ƥ���Բ���" };

	// private static final String UUT = "UUTDescription";
	private static final String equipment = "equipmentDescription";
	private static final String programElementTpsHardware = "programElementDescriptionTpsHardware";
	private static final String programElementTpsSoftware = "programElementDescriptionTpsSoftware";

	private List<String> matchUUIDItem = new ArrayList<String>();
	private List<String> unMatchUUIDItem = new ArrayList<String>();
	private List<String> unExistUUIDItem = new ArrayList<String>();

	private org.dom4j.Document UUTDocument;
	private org.dom4j.Document configurationDocument;
	private org.dom4j.Document adapterDocument;
	private org.dom4j.Document stationDocument;
	private org.dom4j.Document testDescriptionDocument;
	// private String error = "";
	// private String txtError = "";

	public ConfigurationReader() {
		testItem.add("���������ļ�ƥ���Բ���");
	}

	public String getInfoHTML() {
		return infoHTML;
	}

	private HashMap<String, String> UUID = new HashMap<String, String>();
	/*
	 * private String UUTUUID = ""; private String adapterUUID = ""; private
	 * String stationUUID = "";
	 */
	private HashMap<String, ArrayList<String>> configurationUUID = new HashMap<String, ArrayList<String>>();

	/*
	 * private void writeError(String errorInfo) { txtError += (errorInfo+
	 * "\n"); error += ("<font color='red'>" + errorInfo + "</font><br>"); }
	 * 
	 * private void writeInfo(String info) { txtError += (info + "\n"); error +=
	 * (info + "<br>"); }
	 */

	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		ConfigurationReader test = new ConfigurationReader();
		test.configurationReader(configurationDir, UUTDir, stationDir, adapterDir, testDescriptionDir);
	}

	private org.dom4j.Document readXML(String fileDir, String fileType) {
		File file = new File(fileDir);
		org.dom4j.Document document = null;
		if (file.exists()) {
			SAXReader saxAdpaterReader = new SAXReader();
			document = null;
			try {
				document = saxAdpaterReader.read(file);
			} catch (DocumentException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			if(fileType.equals(StringUtil.configuration)){
				String string  = file.getName();
				String[] strings = string.split("\\.");
				fileName = strings[0];	
			}
		} else {
			System.out.println("File " + fileType + " is not exist!");
			// error += ("File " + configuration + " is not exist!\n");
			// writeError("File " + fileType + " is not exist!");
		}
		return document;
	}

	public void configurationReader(String configurationDir, String UUTDir, String stationDir, String adapterDir,
			String testDescriptionDir) {
		// TODO �Զ����ɵķ������
		// txtError += "#####################File Path######################\n";
		// writeInfo("#####################File Path######################");
		configurationDocument = readXML(configurationDir, StringUtil.configuration);
		UUTDocument = readXML(UUTDir, StringUtil.UUT);
		adapterDocument = readXML(adapterDir, StringUtil.adapter);
		stationDocument = readXML(stationDir, StringUtil.station);
		testDescriptionDocument = readXML(testDescriptionDir, StringUtil.testDescription);
		initInfoMap();
		WriteTestItem0("�����");
		getUUID();
		getFilesUUID();
		checkUUIDs();
		addTestResult();
		//WriteRedInfo("-----------���ͨ��-----------","-----------���ͨ��-----------");
		infoWriteWord(fileName);
		write2TXT();
	}

	private List<Element> getElementWithNamespace(Element root, String tagName, String namespace) {
		List<Element> list = new ArrayList<Element>();
		List<Element> tagList = root.elements(tagName);
		for (Element element : tagList) {
			if (element.getNamespacePrefix().equals(namespace)) {
				list.add(element);
			}
		}
		return list;
	}

	private void getUUID() {
		if (configurationDocument != null) {
			getConfigurationUUID();
		}
	}

	private void getConfigurationUUID() {
		Element root = configurationDocument.getRootElement();
		// UUTUUID
		List<Element> testedUUT = getElementWithNamespace(root, "TestedUUTs", "tc");
		for (Iterator<Element> iterators = testedUUT.iterator(); iterators.hasNext();) {
			Element elements = (Element) iterators.next();
			List<Element> list = getElementWithNamespace(elements, "TestedUUT", "tc");
			ArrayList<String> testUUTs = new ArrayList<String>();
			for (Iterator<Element> iterator = list.iterator(); iterator.hasNext();) {
				Element element = (Element) iterator.next();
				List<Element> nodes = getElementWithNamespace(element, "DescriptionDocumentReference", "c");
				for (Iterator<Element> iteratorNodes = nodes.iterator(); iteratorNodes.hasNext();) {
					Element descriptionDocumentReference = (Element) iteratorNodes.next();
					String UUID = descriptionDocumentReference.attributeValue("uuid");
					testUUTs.add(UUID);
				}
			}
			configurationUUID.put(StringUtil.UUT, testUUTs);
		}
		// equipmentUUID(TestStation)
		List<Element> testedEquipment = getElementWithNamespace(root, "TestEquipment", "tc");
		for (Iterator<Element> iterators = testedEquipment.iterator(); iterators.hasNext();) {
			Element elements = (Element) iterators.next();
			List<Element> list = getElementWithNamespace(elements, "TestEquipmentItem", "tc");
			ArrayList<String> testEquipment = new ArrayList<String>();
			for (Iterator<Element> iterator = list.iterator(); iterator.hasNext();) {
				Element element = (Element) iterator.next();
				List<Element> nodes = getElementWithNamespace(element, "DescriptionDocumentReference", "c");
				for (Iterator<Element> iteratorNodes = nodes.iterator(); iteratorNodes.hasNext();) {
					Element descriptionDocumentReference = (Element) iteratorNodes.next();
					String UUID = descriptionDocumentReference.attributeValue("uuid");
					testEquipment.add(UUID);
				}
			}
			configurationUUID.put(equipment, testEquipment);
		}
		// programElementsTpsHardwareUUID(TestAdapter)
		List<Element> testedProgramElementTpsHardware = getElementWithNamespace(root, "TestProgramElements", "tc");
		for (Iterator<Element> iterators = testedProgramElementTpsHardware.iterator(); iterators.hasNext();) {
			Element elements = (Element) iterators.next();
			ArrayList<String> testProgramElements = new ArrayList<String>();
			List<Element> list = getElementWithNamespace(elements, "TpsHardware", "tc");
			for (Iterator<Element> iterator = list.iterator(); iterator.hasNext();) {
				Element element = (Element) iterator.next();
				List<Element> descriptionDocumentReferences = getElementWithNamespace(element,
						"DescriptionDocumentReference", "c");
				for (Iterator<Element> iterator2 = descriptionDocumentReferences.iterator(); iterator2.hasNext();) {
					Element descriptionDocumentReference = (Element) iterator2.next();
					String UUID = descriptionDocumentReference.attributeValue("uuid");
					testProgramElements.add(UUID);
				}
			}
			configurationUUID.put(programElementTpsHardware, testProgramElements);
		}
		// programElementsTpsSoftwareUUID(TestDescription)
		List<Element> testedProgramElementTpsSoftware = getElementWithNamespace(root, "TestProgramElements", "tc");
		for (Iterator<Element> iterators = testedProgramElementTpsSoftware.iterator(); iterators.hasNext();) {
			Element elements = (Element) iterators.next();
			ArrayList<String> testProgramElements = new ArrayList<String>();
			List<Element> list = getElementWithNamespace(elements, "TpsSoftware", "tc");
			for (Iterator<Element> iterator = list.iterator(); iterator.hasNext();) {
				Element element = (Element) iterator.next();
				List<Element> descriptionDocumentReferences = getElementWithNamespace(element,
						"DescriptionDocumentReference", "c");
				for (Iterator<Element> iterator2 = descriptionDocumentReferences.iterator(); iterator2.hasNext();) {
					Element descriptionDocumentReference = (Element) iterator2.next();
					String UUID = descriptionDocumentReference.attributeValue("uuid");
					testProgramElements.add(UUID);
				}
			}
			configurationUUID.put(programElementTpsSoftware, testProgramElements);
		}
	}

	private void getFileUUID(org.dom4j.Document document, String fileType) {
		if (document != null) {
			Element root = document.getRootElement();
			String UUID = root.attributeValue("uuid");
			this.UUID.put(fileType, UUID);
		}
	}

	private void getFilesUUID() {
		getFileUUID(UUTDocument, StringUtil.UUT);
		getFileUUID(adapterDocument, StringUtil.adapter);
		getFileUUID(stationDocument, StringUtil.station);
		getFileUUID(testDescriptionDocument, StringUtil.testDescription);
	}

	private boolean checkUUID(String fileType) {
		String UUID = this.UUID.get(fileType);
		if (UUID == null)
			return false;
		if (fileType.equals(StringUtil.UUT)) {
			if (configurationUUID.get(StringUtil.UUT).size() == 0) {
				// writeError("Configuration�в�����" + StringUtil.UUT + "��UUID��");
				unExistUUIDItem.add("TestConfiguration�в�����" + StringUtil.UUT + "��UUID��");
				return false;
			}
			String cUUID = configurationUUID.get(StringUtil.UUT).get(0);
			if (UUID.equals(cUUID)) {
				// writeInfo(fileType +
				// "��UUID��ConfigurationDescription�е�UUIDƥ�䣻");
				matchUUIDItem.add(fileType + "��UUID��TestConfiguration�е�UUIDƥ�䣻");
				return true;
			} else {
				// writeError(fileType +
				// "��UUID��ConfigurationDescription�е�UUID��ƥ�䣺");
				// writeError(fileType + "��UUID:" + UUID);
				// writeError("Configuration�е�UUID:" + cUUID + "��");
				unMatchUUIDItem.add(fileType + "��UUID��TestConfiguration�е�UUID��ƥ�䣺");
				unMatchUUIDItem.add(fileType + "��UUID:" + UUID);
				unMatchUUIDItem.add("TestConfiguration�е�UUID:" + cUUID + "��");
			}
		} else if (fileType.equals(StringUtil.station)) {
			if (configurationUUID.get(equipment).size() == 0) {
				// writeError("Configuration�в�����" + StringUtil.station +
				// "��UUID��");
				unExistUUIDItem.add("TestConfiguration�в�����" + StringUtil.station + "��UUID��");
				return false;
			}
			List<String> cUUIDs = new ArrayList<String>();
			String cUUID = configurationUUID.get(equipment).get(0);
			if (UUID.equals(cUUID)) {
				// writeInfo(fileType +
				// "��UUID��ConfigurationDescription�е�UUIDƥ�䣻");
				matchUUIDItem.add(fileType + "��UUID��TestConfiguration�е�UUIDƥ�䣻");
				return true;
			} else {
				// writeError(fileType +
				// "��UUID��ConfigurationDescription�е�UUID��ƥ�䣺");
				// writeError(fileType + "��UUID:" + UUID);
				// writeError("Configuration�е�UUID:" + cUUID + "��");
				unMatchUUIDItem.add(fileType + "��UUID��TestConfiguration�е�UUID��ƥ�䣺");
				unMatchUUIDItem.add(fileType + "��UUID:" + UUID);
				unMatchUUIDItem.add("TestConfiguration�е�UUID:" + cUUID + "��");
			}
		} else if (fileType.equals(StringUtil.adapter)) {
			if (configurationUUID.get(programElementTpsHardware).size() == 0) {
				// writeError("Configuration�в�����" + StringUtil.adapter +
				// "��UUID��");
				unExistUUIDItem.add("TestConfiguration�в�����" + StringUtil.adapter + "��UUID��");
				return false;
			}
			String cUUID = configurationUUID.get(programElementTpsHardware).get(0);
			if (UUID.equals(cUUID)) {
				// writeInfo(fileType +
				// "��UUID��ConfigurationDescription�е�UUIDƥ�䣻");
				matchUUIDItem.add("TestAdapter" + "��UUID��TestConfiguration�е�UUIDƥ�䣻");
				return true;
			} else {
				// writeError(fileType +
				// "��UUID��ConfigurationDescription�е�UUID��ƥ�䣺");
				// writeError(fileType + "��UUID:" + UUID);
				// writeError("Configuration�е�UUID:" + cUUID + "��");
				unMatchUUIDItem.add(fileType + "��UUID��TestConfiguration�е�UUID��ƥ�䣺");
				unMatchUUIDItem.add(fileType + "��UUID:" + UUID);
				unMatchUUIDItem.add("TestConfiguration�е�UUID:" + cUUID + "��");
			}
		} else if (fileType.equals(StringUtil.testDescription)) {
			if (configurationUUID.get(programElementTpsSoftware).size() == 0) {
				// writeError("Configuration�в�����" + StringUtil.testDescription +
				// "��UUID��");
				unExistUUIDItem.add("TestConfiguration�в�����" + StringUtil.testDescription + "��UUID��");
				return false;
			}
			String cUUID = configurationUUID.get(programElementTpsSoftware).get(0);
			if (UUID.equals(cUUID)) {
				// writeInfo(fileType +
				// "��UUID��ConfigurationDescription�е�UUIDƥ�䣻");
				matchUUIDItem.add(fileType + "��UUID��TestConfiguration�е�UUIDƥ�䣻");
				return true;
			} else {
				// writeError(fileType +
				// "��UUID��ConfigurationDescription�е�UUID��ƥ�䣺");
				// writeError(fileType + "��UUID:" + UUID);
				// writeError("Configuration�е�UUID:" + cUUID + "��");
				unMatchUUIDItem.add(fileType + "��UUID��TestConfiguration�е�UUID��ƥ�䣺");
				unMatchUUIDItem.add(fileType + "��UUID:" + UUID);
				unMatchUUIDItem.add("TestConfiguration�е�UUID:" + cUUID + "��");
			}
		}
		return false;
	}

	private void checkUUIDs() {
		WriteTestItem1(testItem.get(0));
		checkUUID(StringUtil.UUT);
		checkUUID(StringUtil.station);
		checkUUID(StringUtil.adapter);
		checkUUID(StringUtil.testDescription);
		//WriteTestItem1(testItem.get(0));
		if (matchUUIDItem.size() > 0) {
			for (int i = 0; i < matchUUIDItem.size(); i++) {
				WriteNormalInfoS(testItem.get(0), matchUUIDItem.get(i));
			}
		}
		if (unMatchUUIDItem.size() > 0) {
			WriteNormalInfoS(testItem.get(0), "UUID��ⲻƥ�����£�");
			for (int i = 0; i < unMatchUUIDItem.size(); i++) {
				WriteError2Info(testItem.get(0), unMatchUUIDItem.get(i));
			}
		}
		if (unExistUUIDItem.size() > 0) {
			WriteNormalInfoS(testItem.get(0), "TestConfiguration�в����ڵ�UUID���£�");
			for (int i = 0; i < unExistUUIDItem.size(); i++) {
				WriteError1Info(testItem.get(0), unExistUUIDItem.get(i));
			}
		}
		// addErrorItemWithoutEnter(testItem[0], errorTxt);
	}

	private void write2TXT() {
		File dataFlie = new File(fileName + "������ļ�.txt");
		if (dataFlie.exists()) {
			dataFlie.delete();
		} else {
			try {
				dataFlie.createNewFile();
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(dataFlie);
		} catch (FileNotFoundException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		try {
			fos.write(infoTxt.getBytes());
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		try {
			fos.close();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
}
