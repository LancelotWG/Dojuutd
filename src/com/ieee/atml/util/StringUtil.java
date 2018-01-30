package com.ieee.atml.util;

public interface StringUtil {
	public static final String configuration = "TestConfiguration";
	public static final String adapter = "TestAdapterDescription";
	public static final String station = "TestStationDescription";
	public static final String testDescription = "TestDescription";
	public static final String UUT = "UUTDescription";
	public static final String testSignal = "Signal";
	public static final String instrument = "InstrumentDescription";
	public static final String result = "TestResults";

	public static final String configurationTest =  "TestConfigurationTest";
	public static final String adapterTest = "TestAdapterTest";
	public static final String stationTest = "TestStationTest";
	public static final String testDescriptionTest = "TestDescriptionTest";
	public static final String UUTTest = "UUTDescriptionTest";
	public static final String testSignalTest = "SignalTest";
	public static final String instrumentTest = "InstrumentDescriptionTest";
	public static final String resultTest = "TestResultTest";
	public static final String[] testItem = new String[] { testDescriptionTest, instrumentTest, UUTTest, configurationTest,  adapterTest, stationTest,
			resultTest, testSignalTest};
	
	
	public static final String[] programRunningStateItem = new String[] {"Ready", "Testing", "Done"};
	public static enum programRunningState {
		Ready, Testing, Done
	};
}
