package com.ieee.atml.info;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;

public class WordWriter extends WordAssist {
	private final static String[] tableHeader = { "序号", "测试项", "测试内容", "测试结果", "备注" };
	private final static String[] tableHeaderWidth = { "1134", "1701", "2268", "4536", "1984" };
	private String header = "";
	
	private XWPFDocument document = null;
	private FileOutputStream fileOutputStream = null;
	private XWPFTable testTable = null;
	public XWPFTable getTestTable() {
		return testTable;
	}

	private int index = 1;
	
	public static void main(String[] args) {
		WordWriter wordWriter = new WordWriter();
		wordWriter.createTestReport("测试");
		/*wordWriter.addTestItem("XPath路径标准检测", " ",
				"待检测的XPath路径个数为：386\n标准的XPath路径个数为：386\n不标准的XPath路径个数为：0", " ");*/
		wordWriter.addTestItem("XPath路径可达性检测", " ",
				"标准的XPath路径中可达路径的个数为：384\n标准的XPath路径中不可达路径的个数为：2\n标准的XPath路径中不可达路径如下所示：\n",
				" ");
		/*wordWriter.addTestItem("Adapter通路检测", " ",
				"Network连接节点总数为：191对\n已与Station，UUT建立连接的Network接节点总数为：191对\n未与Station，UUT建立连接的Network节点总数为：0对\n以下为未与Station，UUT建立连接的Network节点：",
				" ");*/
		wordWriter.writeComplete();
		//wordWriter.setCellWidth(2);
	}

	public void createTestReport(String title) {
		header = title + "检测结果";
		File testFile = new File(header + ".doc");
		document = new XWPFDocument();
		if (testFile.exists()) {
			testFile.delete();
		} else {
			try {
				testFile.createNewFile();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		try {
			fileOutputStream = new FileOutputStream(testFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		  CTBody body = document.getDocument().getBody();
		  if(!body.isSetSectPr()){ 
		  body.addNewSectPr(); 
		  } 
		  CTSectPr section = body.getSectPr(); 
		  if(!section.isSetPgSz()){ 
			  section.addNewPgSz();
		  } 
		  CTPageSz pageSize = section.getPgSz();
		  //横向A4纸张
		  pageSize.setH(new BigInteger("11907"));
		  pageSize.setW(new BigInteger("17010"));
		  //pageSize.setOrient(STPageOrientation.LANDSCAPE);
		 
		createTable();
	}

	public void writeComplete() {
		try {
			document.write(fileOutputStream);
			fileOutputStream.flush();
			// 操作结束，关闭文件
			fileOutputStream.close();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}

	private void createTable() {
		setTableHeader();

		testTable = document.createTable();
		setTableWidthAndHAlign(testTable, "12300", STJc.CENTER);
		CTTbl ttbl = testTable.getCTTbl();
		ttbl.addNewTblPr().addNewJc().setVal(STJc.CENTER);
		XWPFTableRow tableRow = testTable.getRow(0);
		for (int i = 0; i < tableHeader.length; i++) {
			XWPFTableCell cell = tableRow.getCell(i);
			/*if(i != 3){
				setCellWidthAndVAlign(cell, tableHeaderWidth[i], null, STVerticalJc.CENTER);	
			}else{*/
				setCellWidthAndVAlign(cell, tableHeaderWidth[i], STTblWidth.DXA, STVerticalJc.CENTER);
			/*}*/
			//cell.getCTTc().getTcPr().getTcW().setW(new BigInteger("100"));
			cell.setVerticalAlignment(XWPFVertAlign.CENTER);
			cell.setParagraph(createParagraphContentInNull(tableHeader[i], true, true));
			if (i == (tableHeader.length - 1)) {
				continue;
			}
			tableRow.addNewTableCell();
		}
	}
	
	protected void setCellWidth(int row){
		for (int i = 0; i < tableHeader.length; i++) {
			XWPFTableCell cell = testTable.getRow(row).getCell(i);
			setCellWidthAndVAlign(cell, tableHeaderWidth[i], STTblWidth.PCT, STVerticalJc.CENTER);
		}
	}

	private void createSingleCell(XWPFTableRow tableRow, int line, String content) {
		XWPFTableCell cell = tableRow.getCell(line);
		cell.setVerticalAlignment(XWPFVertAlign.CENTER);
		cell.setParagraph(createParagraphContentInNull(content, false, true));
		//setCellWidthAndVAlign(cell, tableHeaderWidth[line], STTblWidth.PCT, STVerticalJc.CENTER);
	}

	private void createMultipleCell(XWPFTableRow tableRow, int line, String content) {
		XWPFTableCell cell = tableRow.getCell(line);
		cell.setVerticalAlignment(XWPFVertAlign.CENTER);
		String[] contentItem = content.split("\n");
		cell.setParagraph(createParagraphContentInNull(contentItem[0], false, false));
		for (int i = 1; i < contentItem.length; i++) {
			String string = contentItem[i];
			XWPFParagraph paragraph = tableRow.getCell(line).addParagraph();
			// paragraph.setAlignment(ParagraphAlignment.CENTER);
			XWPFRun title = paragraph.createRun();
			title.setText(string);
			title.setFontFamily("宋体");
		}
		//setCellWidthAndVAlign(cell, tableHeaderWidth[line], STTblWidth.PCT, STVerticalJc.CENTER);
	}

	public void addTestItem(String testItem, String testContent, String testResults, String note) {
		XWPFTableRow tableRow = testTable.createRow();
		createSingleCell(tableRow, 0, String.valueOf(index));
		createSingleCell(tableRow, 1, testItem);

		createMultipleCell(tableRow, 2, testContent);
		createMultipleCell(tableRow, 3, testResults);
		createMultipleCell(tableRow, 4, note);
		index++;
	}

	private void setTableHeader() {
		createParagraphContent(header, true);
	}

	private XWPFParagraph createParagraphContentInNull(String content, boolean isBold, boolean isCenter) {
		XWPFDocument doc = new XWPFDocument();
		CTP p = doc.getDocument().getBody().addNewP();
		XWPFParagraph paragraph = new XWPFParagraph(p, doc);
		if (isCenter) {
			paragraph.setAlignment(ParagraphAlignment.CENTER);
		}
		XWPFRun title = paragraph.createRun();
		title.setText(content);
		title.setFontFamily("宋体");
		title.setBold(isBold);
		return paragraph;
	}

	private XWPFParagraph createParagraphContent(String content, boolean isBold) {
		XWPFParagraph paragraph = document.createParagraph();
		paragraph.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun title = paragraph.createRun();
		title.setText(content);
		title.setFontFamily("宋体");
		title.setBold(isBold);
		return paragraph;
	}
}