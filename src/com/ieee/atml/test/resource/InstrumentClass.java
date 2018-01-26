package com.ieee.atml.test.resource;

import java.util.ArrayList;
import java.util.List;

public class InstrumentClass {
	private String uuid;
	private String filePath;
	private List<String> docIDs=new ArrayList();
	
	public void AddDocID(String docid)
	{
		this.docIDs.add(docid);
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public List<String> getDocIDs() {
		return docIDs;
	}
	public void setDocIDs(List<String> docIDs) {
		this.docIDs = docIDs;
	}

}
