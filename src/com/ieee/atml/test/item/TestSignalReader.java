package com.ieee.atml.test.item;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import com.ieee.atml.info.InfoWrite;
import com.ieee.atml.test.assist.TestSignalVisitor;
import com.ieee.atml.util.StringUtil;

public class TestSignalReader extends InfoWrite {
	// private static final String[] testItem = { "ATML扩展信号检测" };

	public TestSignalReader() {
		testItem.add("STDBSC信号检测");
		testItem.add("STDTSF信号库检测");
		testItem.add("ExtTSF信号库检测");
	}

	// String errorInfo="";
	public String getInfoHTML() {
		return infoHTML;
	}
	
	public static void main(String[] args){
		TestSignalReader s = new TestSignalReader();
		s.VirtualMain("Untitled4.xml", "STDTSF", "ExtTSF");
	}

	List<String> ExtTSFLibSignals = new ArrayList();
	List<String> ExtTSFErrorList = new ArrayList();
	List<String> ExtTSFCorrectList = new ArrayList();

	List<String> STDBSCSignals = new ArrayList();
	List<String> STDBSCErrorList = new ArrayList();
	List<String> STDBSCCorrectList = new ArrayList();

	List<String> STDTSFLibSignals = new ArrayList();
	List<String> STDTSFErrorList = new ArrayList();
	List<String> STDTSFCorrectList = new ArrayList();

	public void VirtualMain(String STDBSCXmlPath, String STDTSFDir, String ExtTSFDir) {
		initialSTDTSFLib();
		initialSTDBSCSignal();
		initialExtTSFLib();

		SAXReader saxReader = new SAXReader();
		try {
			File STDBSCFile = new File(STDBSCXmlPath);
			getSTDTSFErrotList(STDTSFDir);
			getExtTSFErrotList(ExtTSFDir);
			Document doc = saxReader.read(STDBSCFile);
			TestSignalVisitor tsigVisitor = new TestSignalVisitor();
			doc.accept(tsigVisitor);

			String string = STDBSCFile.getName();
			String[] strings = string.split("\\.");
			fileName = strings[0];

			String txtpath = fileName + "检测结果文件.txt";
			initInfoMap();
			WriteTxtFile(txtpath, tsigVisitor);
			infoWriteWord(fileName);
		} catch (DocumentException e) {
			System.out.println(e.getMessage());
		}
	}

	private void initialExtTSFLib() {
		// TODO 自动生成的方法存根
		ExtTSFLibSignals.add("AC_POWER");
		ExtTSFLibSignals.add("ADF_EXT");
		ExtTSFLibSignals.add("AF_SIGNAL");
		ExtTSFLibSignals.add("AFDX");
		ExtTSFLibSignals.add("AIRDATA_ALT");
		ExtTSFLibSignals.add("AIRDATA_CAS");
		ExtTSFLibSignals.add("AM_SIGNAL_EXT");
		ExtTSFLibSignals.add("ARINC429_TX");
		ExtTSFLibSignals.add("ARINC429_RX");
		ExtTSFLibSignals.add("ARINC717");
		ExtTSFLibSignals.add("ATC_AC_RESPONSE");
		ExtTSFLibSignals.add("ATC_AC_INTERROGATION");
		ExtTSFLibSignals.add("DC_POWER");
		ExtTSFLibSignals.add("DOPPLER");
		ExtTSFLibSignals.add("DPS");
		ExtTSFLibSignals.add("DPS_POWER");
		ExtTSFLibSignals.add("ETHERNET");
		ExtTSFLibSignals.add("FM_SIGNAL_EXT");
		ExtTSFLibSignals.add("ILS_GLIDE_SLOPE_EXT");
		ExtTSFLibSignals.add("ILS_LOCALIZER_EXT");
		ExtTSFLibSignals.add("M1553B-BC");
		ExtTSFLibSignals.add("M1553B-BM");
		ExtTSFLibSignals.add("M1553B-RT");
		ExtTSFLibSignals.add("PEAK_POWER");
		ExtTSFLibSignals.add("PULSED_SIGNAL");
		ExtTSFLibSignals.add("RA-RESPONSE");
		ExtTSFLibSignals.add("RS_422");
		ExtTSFLibSignals.add("RS_485");
		ExtTSFLibSignals.add("MLS");
		ExtTSFLibSignals.add("SPECTRUM_TEST");
		ExtTSFLibSignals.add("SQUARE_WAVE_EXT");
		ExtTSFLibSignals.add("TACAN_EXT");
		ExtTSFLibSignals.add("VHF_AUDIO_DS");
		ExtTSFLibSignals.add("VHF_AUDIO_DSFH");
		ExtTSFLibSignals.add("VHF_AUDIO_FH");
		ExtTSFLibSignals.add("VHF_AUDIO_FM");
		ExtTSFLibSignals.add("VHF_DATA_DS");
		ExtTSFLibSignals.add("VHF_DATA_DSFH");
		ExtTSFLibSignals.add("VHF_DATA_DSFH");
		ExtTSFLibSignals.add("VIDEO_PAL");
		ExtTSFLibSignals.add("VIDEO_SOG");
		ExtTSFLibSignals.add("VIDEO_VGA");
		ExtTSFLibSignals.add("VIDEO_YC");
		ExtTSFLibSignals.add("VOR_EXT");
	}

	private void initialSTDBSCSignal() {
		STDBSCSignals.add("Constant");
		STDBSCSignals.add("Step");
		STDBSCSignals.add("SingleTrapezoid");
		STDBSCSignals.add("Noise");
		STDBSCSignals.add("SingleRamp");
		STDBSCSignals.add("Sinusoid");
		STDBSCSignals.add("Trapezoid");
		STDBSCSignals.add("Ramp");
		STDBSCSignals.add("Triangle");
		STDBSCSignals.add("SquareWave");
		STDBSCSignals.add("WaveformRamp");
		STDBSCSignals.add("WaveformStep");
		STDBSCSignals.add("BandPass");
		STDBSCSignals.add("LowPass");
		STDBSCSignals.add("HighPass");
		STDBSCSignals.add("Notch");
		STDBSCSignals.add("Sum");
		STDBSCSignals.add("Product");
		STDBSCSignals.add("Diff");
		STDBSCSignals.add("FM");
		STDBSCSignals.add("AM");
		STDBSCSignals.add("PM");
		STDBSCSignals.add("SignalDelay");
		STDBSCSignals.add("Exponential");
		STDBSCSignals.add("E");
		STDBSCSignals.add("Ln");
		STDBSCSignals.add("Negate");
		STDBSCSignals.add("Inverse");
		STDBSCSignals.add("PulseTrain");
		STDBSCSignals.add("Attenuator");
		STDBSCSignals.add("Load");
		STDBSCSignals.add("Limit");
		STDBSCSignals.add("FFT");
		STDBSCSignals.add("Clock");
		STDBSCSignals.add("TimedEvent");
		STDBSCSignals.add("PulsedEvent");
		STDBSCSignals.add("EventedEvent");
		STDBSCSignals.add("EventCount");
		STDBSCSignals.add("ProbabilityEvent");
		STDBSCSignals.add("NotEvent");
		STDBSCSignals.add("OrEvent");
		STDBSCSignals.add("XOrEvent");
		STDBSCSignals.add("AndEvent");
		STDBSCSignals.add("Counter");
		STDBSCSignals.add("Interval");
		STDBSCSignals.add("TimeInterval");
		STDBSCSignals.add("Instantaneous");
		STDBSCSignals.add("RMS");
		STDBSCSignals.add("Average");
		STDBSCSignals.add("PeakToPeak");
		STDBSCSignals.add("Peak");
		STDBSCSignals.add("PeakPos");
		STDBSCSignals.add("PeakNeg");
		STDBSCSignals.add("MaxInstantaneous");
		STDBSCSignals.add("MinInstantaneous");
		STDBSCSignals.add("Measure");
		STDBSCSignals.add("Decode");
		STDBSCSignals.add("SelectIf");
		STDBSCSignals.add("SelectCase");
		STDBSCSignals.add("Encode");
		STDBSCSignals.add("Channels");
		STDBSCSignals.add("SerialDigital");
		STDBSCSignals.add("ParallelDigital");
		STDBSCSignals.add("TwoWire");
		STDBSCSignals.add("TwoWireComp");
		STDBSCSignals.add("ThreeWireComp");
		STDBSCSignals.add("SinglePhase");
		STDBSCSignals.add("TwoPhase");
		STDBSCSignals.add("ThreePhaseDelta");
		STDBSCSignals.add("ThreePhaseWye");
		STDBSCSignals.add("ThreePhaseSynchro");
		STDBSCSignals.add("FourWireResolver");
		STDBSCSignals.add("SynchroResolver");
		STDBSCSignals.add("Series");
		STDBSCSignals.add("FourWire");
		STDBSCSignals.add("NonElectrical");
		STDBSCSignals.add("DigitalBus");
	}

	private void initialSTDTSFLib() {
		STDTSFLibSignals.add("AC_SIGNAL");
		STDTSFLibSignals.add("AM_SIGNAL");
		STDTSFLibSignals.add("DC_SIGNAL");
		STDTSFLibSignals.add("DIGITAL_PARALLEL");
		STDTSFLibSignals.add("DIGITAL_SERIAL");
		STDTSFLibSignals.add("DME_INTERROGATION");
		STDTSFLibSignals.add("DME_RESPONSE");
		STDTSFLibSignals.add("FM_SIGNAL");
		STDTSFLibSignals.add("ILS_GLIDE_SLOPE");
		STDTSFLibSignals.add("ILS_LOCALIZER");
		STDTSFLibSignals.add("ILS_MARKER");
		STDTSFLibSignals.add("PM_SIGNAL");
		STDTSFLibSignals.add("PULSED_AC_SIGNAL");
		STDTSFLibSignals.add("PULSED_AC_TRAIN");
		STDTSFLibSignals.add("PULSED_DC_SIGNAL");
		STDTSFLibSignals.add("PULSED_DC_TRAIN");
		STDTSFLibSignals.add("RADAR_RX_SIGNAL");
		STDTSFLibSignals.add("RADAR_TX_SIGNAL");
		STDTSFLibSignals.add("RAMP_SIGNAL");
		STDTSFLibSignals.add("RANDOM_NOISE");
		STDTSFLibSignals.add("RESOLVER");
		STDTSFLibSignals.add("RS_232");
		STDTSFLibSignals.add("SQUARE_WAVE");
		STDTSFLibSignals.add("SSR_INTERROGATION");
		STDTSFLibSignals.add("SSR_RESPONSE");
		STDTSFLibSignals.add("STEP_SIGNAL");
		STDTSFLibSignals.add("SUP_CAR_SIGNAL");
		STDTSFLibSignals.add("SYNCHRO");
		STDTSFLibSignals.add("TACAN");
		STDTSFLibSignals.add("TRIANGULAR_WAVE_SIGNAL");
		STDTSFLibSignals.add("VOR");
		STDTSFLibSignals.add("DIGITAL_TEST");
	}

	private void WriteTxtFile(String txtpath, TestSignalVisitor tsigVisitor) {
		// TODO 自动生成的方法存根
		File tsftxt = new File(txtpath);
		infoHTML = "";
		if (tsftxt.exists()) {
			tsftxt.delete();
		} else {
			try {
				tsftxt.createNewFile();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(tsftxt);
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		try {
			// String tempstr="**********Port及其管教connectorPin**********\n";
			// fos.write(tempstr.getBytes());
			getSTDBSCErrotList(tsigVisitor.getSignalList());
			// errorInfo+="检测完成，结果如下：\n";
			WriteTxtAndHTMLOnly("检测完成，结果如下：");
			// errorInfo+="ATML扩展信号的个数为：";
			WriteTestItem1(testItem.get(0));
			WriteNormalInfoWithoutEnter(testItem.get(0), "STDBSC信号的个数为：");
			String tempStr = STDBSCSignals.size() + "";
			WriteBoldInfo(testItem.get(0), tempStr);

			// errorInfo+="被测文件中录入的信号个数为：";
			WriteNormalInfoWithoutEnter(testItem.get(0), "被测文件中录入的信号个数为：");
			tempStr = tsigVisitor.getSignalList().size() + "";
			WriteBoldInfo(testItem.get(0), tempStr);

			// errorInfo+="被测文件中属于ATML扩展信号的信号个数为：";
			WriteNormalInfoWithoutEnter(testItem.get(0), "被测文件中属于STDBSC信号的信号个数为：");
			tempStr = STDBSCCorrectList.size() + "";
			WriteBoldInfo(testItem.get(0), tempStr);
			/*if (!STDBSCCorrectList.isEmpty()) {

				WriteNormalInfo(testItem.get(0), "被测文件中属于STDBSC信号的信号名称为：");
				for (Iterator iterator = STDBSCCorrectList.iterator(); iterator.hasNext();) {
					String string = (String) iterator.next();
					// errorInfo+=string+"\n";
					WriteBoldInfo(testItem.get(0), string);
				}
			}*/

			// errorInfo+="被测文件中不属于ATML扩展信号的信号个数为：";
			WriteNormalInfoWithoutEnter(testItem.get(0), "被测文件中不属于STDBSC信号的信号个数为：");
			tempStr = STDBSCErrorList.size() + "";
			WriteBoldInfo(testItem.get(0), tempStr);

			if (!STDBSCErrorList.isEmpty()) {
				// errorInfo+="被测文件中不匹配的信号名称为：\n";
				WriteNormalInfo(testItem.get(0), "被测文件中不属于STDBSC信号的信号名称为：");
				for (Iterator iterator = STDBSCErrorList.iterator(); iterator.hasNext();) {
					String string = (String) iterator.next();
					// errorInfo+=string+"\n";
					WriteBoldInfo(testItem.get(0), string);
				}
			}
			
			if ((STDBSCSignals.size() - STDBSCCorrectList.size()) > 0) {
				// errorInfo+="被测文件中不匹配的信号名称为：\n";
				WriteNormalInfo(testItem.get(0), "STDDSC信号文件中未被测试文件包含的信号为：");
				for (Iterator iterator = STDBSCSignals.iterator(); iterator.hasNext();) {
					String string = (String) iterator.next();
					// errorInfo+=string+"\n";
					if(STDBSCCorrectList.contains(string)){
						
					}else{
						WriteErrorInfo(testItem.get(0), string);
					}
				}
			}
			
			
			
			WriteTestItem1(testItem.get(1));
			WriteNormalInfoWithoutEnter(testItem.get(1), "STDTSF信号库中信号的个数为：");
			tempStr = STDTSFLibSignals.size() + "";
			WriteBoldInfo(testItem.get(1), tempStr);

			// errorInfo+="被测文件中录入的信号个数为：";
			WriteNormalInfoWithoutEnter(testItem.get(1), "被测文件夹中录入的信号个数为：");
			tempStr = (STDTSFCorrectList.size() + STDTSFErrorList.size()) + "";
			WriteBoldInfo(testItem.get(1), tempStr);

			// errorInfo+="被测文件中属于ATML扩展信号的信号个数为：";
			WriteNormalInfoWithoutEnter(testItem.get(1), "被测文件夹中属于STDTSF信号库的信号个数为：");
			tempStr = STDTSFCorrectList.size() + "";
			WriteBoldInfo(testItem.get(1), tempStr);
			/*if (!STDTSFCorrectList.isEmpty()) {

				WriteNormalInfo(testItem.get(1), "被测文件夹中属于STDTSF信号库的信号名称为：");
				for (Iterator iterator = STDTSFCorrectList.iterator(); iterator.hasNext();) {
					String string = (String) iterator.next();
					// errorInfo+=string+"\n";
					WriteBoldInfo(testItem.get(1), string);
				}
			}*/

			// errorInfo+="被测文件中不属于ATML扩展信号的信号个数为：";
			WriteNormalInfoWithoutEnter(testItem.get(1), "被测文件夹中不属于STDTSF信号库的信号个数为：");
			tempStr = STDTSFErrorList.size() + "";
			WriteBoldInfo(testItem.get(1), tempStr);

			if (!STDTSFErrorList.isEmpty()) {
				// errorInfo+="被测文件中不匹配的信号名称为：\n";
				WriteNormalInfo(testItem.get(1), "被测文件夹中不属于STDTSF信号库的信号名称为：");
				for (Iterator iterator = STDTSFErrorList.iterator(); iterator.hasNext();) {
					String string = (String) iterator.next();
					// errorInfo+=string+"\n";
					WriteBoldInfo(testItem.get(1), string);
				}
			}
			
			if ((STDTSFLibSignals.size() - STDTSFCorrectList.size()) > 0) {
				// errorInfo+="被测文件中不匹配的信号名称为：\n";
				WriteNormalInfo(testItem.get(1), "STDTSF信号库中未被测试文件夹包含的信号为：");
				for (Iterator iterator = STDTSFLibSignals.iterator(); iterator.hasNext();) {
					String string = (String) iterator.next();
					// errorInfo+=string+"\n";
					if(STDTSFCorrectList.contains(string)){
						
					}else{
						WriteErrorInfo(testItem.get(1), string);
					}
				}
			}
			
			
			
			WriteTestItem1(testItem.get(2));
			WriteNormalInfoWithoutEnter(testItem.get(2), "ExtTSF信号库中信号的个数为：");
			tempStr = ExtTSFLibSignals.size() + "";
			WriteBoldInfo(testItem.get(2), tempStr);

			// errorInfo+="被测文件中录入的信号个数为：";
			WriteNormalInfoWithoutEnter(testItem.get(2), "被测文件夹中录入的信号个数为：");
			tempStr = (ExtTSFCorrectList.size() + ExtTSFErrorList.size()) + "";
			WriteBoldInfo(testItem.get(2), tempStr);

			// errorInfo+="被测文件中属于ATML扩展信号的信号个数为：";
			WriteNormalInfoWithoutEnter(testItem.get(2), "被测文件夹中属于ExtTSF信号库的信号个数为：");
			tempStr = ExtTSFCorrectList.size() + "";
			WriteBoldInfo(testItem.get(2), tempStr);
			/*if (!ExtTSFCorrectList.isEmpty()) {

				WriteNormalInfo(testItem.get(2), "被测文件夹中属于ExtTSF信号库的信号名称为：");
				for (Iterator iterator = ExtTSFCorrectList.iterator(); iterator.hasNext();) {
					String string = (String) iterator.next();
					// errorInfo+=string+"\n";
					WriteBoldInfo(testItem.get(2), string);
				}
			}*/

			// errorInfo+="被测文件中不属于ATML扩展信号的信号个数为：";
			WriteNormalInfoWithoutEnter(testItem.get(2), "被测文件夹中不属于ExtTSF信号库的信号个数为：");
			tempStr = ExtTSFErrorList.size() + "";
			WriteBoldInfo(testItem.get(2), tempStr);

			if (!ExtTSFErrorList.isEmpty()) {
				// errorInfo+="被测文件中不匹配的信号名称为：\n";
				WriteNormalInfo(testItem.get(2), "被测文件夹中不属于ExtTSF信号库的信号名称为：");
				for (Iterator iterator = ExtTSFErrorList.iterator(); iterator.hasNext();) {
					String string = (String) iterator.next();
					// errorInfo+=string+"\n";
					WriteBoldInfo(testItem.get(2), string);
				}
			}
			
			if ((ExtTSFLibSignals.size() - ExtTSFCorrectList.size()) > 0) {
				// errorInfo+="被测文件中不匹配的信号名称为：\n";
				WriteNormalInfo(testItem.get(2), "ExtTSF信号库中未被测试文件夹包含的信号为：");
				for (Iterator iterator = ExtTSFLibSignals.iterator(); iterator.hasNext();) {
					String string = (String) iterator.next();
					// errorInfo+=string+"\n";
					if(ExtTSFCorrectList.contains(string)){
						
					}else{
						WriteErrorInfo(testItem.get(2), string);
					}
				}
			}
			
			//WriteRedInfo("-----------检测通过-----------","-----------检测通过-----------");
			// addErrorItemWithoutEnter(testItem[0], errorTxt);
			fos.write(infoTxt.getBytes());

		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		try {
			fos.close();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	private void getSTDBSCErrotList(List<String> tsfList) {
		// TODO 自动生成的方法存根
		for (Iterator iterator = tsfList.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			if (!STDBSCSignals.contains(string)) {
				STDBSCErrorList.add(string);
			} else {
				STDBSCCorrectList.add(string);
			}
		}

	}

	private int getSTDTSFErrotList(String dir) {
		// TODO 自动生成的方法存根
		int num = 0;
		File fileDir = new File(dir);
		if (fileDir.isDirectory()) {
			File[] files = fileDir.listFiles();
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				if (file.isFile()) {
					SAXReader saxReader = new SAXReader();
					try {
						Document doc = saxReader.read(file);
						String name = doc.getRootElement().getName();
						num++;
						if (!STDTSFLibSignals.contains(name)) {
							STDTSFErrorList.add(name);
						} else {
							STDTSFCorrectList.add(name);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		}
		return num;
	}

	private int getExtTSFErrotList(String dir) {
		// TODO 自动生成的方法存根
		int num = 0;
		File fileDir = new File(dir);
		if (fileDir.isDirectory()) {
			File[] files = fileDir.listFiles();
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				if(file.isFile()){
					SAXReader saxReader = new SAXReader();
					try {
						Document doc = saxReader.read(file);
						String name = doc.getRootElement().getName();
						num++;
						if (!ExtTSFLibSignals.contains(name)) {
							ExtTSFErrorList.add(name);
						} else {
							ExtTSFCorrectList.add(name);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		}
		return num;
	}
}
