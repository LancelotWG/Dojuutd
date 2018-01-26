package com.ieee.atml.info;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.BodyElementType;
import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.VerticalAlign;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRelation;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlToken;
import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBackground;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBookmark;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTColor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDecimalNumber;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTEm;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFldChar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHeight;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHighlight;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHpsMeasure;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHyperlink;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTInd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTMarkupRange;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTOnOff;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTParaRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSignedTwipsMeasure;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblGrid;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblGridCol;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTextScale;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTrPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTUnderline;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVerticalJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STEm;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STFldCharType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHdrFtr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHeightRule;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHighlightColor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLineSpacingRule;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STOnOff;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STUnderline;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalAlignRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;

public abstract class WordAssist {

  // �滻 ���� ��� ����

  /*------------------------------------Word ������ǩ---------------------------------------------------  */
  /**
   * @Description: �����ǩ
   */
  public void addParagraphContentBookmarkBasicStyle(XWPFParagraph p,
      String content, BigInteger markId, String bookMarkName,
      boolean isInsert, boolean isNewLine, String fontFamily,
      String fontSize, String colorVal, boolean isBlod,
      boolean isUnderLine, String underLineColor,
      STUnderline.Enum underStyle, boolean isItalic, boolean isStrike) {
    CTBookmark bookStart = p.getCTP().addNewBookmarkStart();
    bookStart.setId(markId);
    bookStart.setName(bookMarkName);

    XWPFRun pRun = getOrAddParagraphFirstRun(p, isInsert, isNewLine);
    setParagraphRunFontInfo(p, pRun, content, fontFamily, fontSize);
    setParagraphTextStyleInfo(p, pRun, colorVal, isBlod, isUnderLine,
        underLineColor, underStyle, isItalic, isStrike, false, false,
        false, false, false, false, false, null, false, null, false,
        null, null, null, 0, 0, 0);
    CTMarkupRange bookEnd = p.getCTP().addNewBookmarkEnd();
    bookEnd.setId(markId);

  }

  /**
   * @Description: �����ǩ
   */
  public void addParagraphContentBookmark(XWPFParagraph p, String content,
      BigInteger markId, String bookMarkName, boolean isInsert,
      boolean isNewLine, String fontFamily, String fontSize,
      String colorVal, boolean isBlod, boolean isUnderLine,
      String underLineColor, STUnderline.Enum underStyle,
      boolean isItalic, boolean isStrike, boolean isDStrike,
      boolean isShadow, boolean isVanish, boolean isEmboss,
      boolean isImprint, boolean isOutline, boolean isEm,
      STEm.Enum emType, boolean isHightLight,
      STHighlightColor.Enum hightStyle, boolean isShd,
      STShd.Enum shdStyle, String shdColor, VerticalAlign verticalAlign,
      int position, int spacingValue, int indent) {
    CTBookmark bookStart = p.getCTP().addNewBookmarkStart();
    bookStart.setId(markId);
    bookStart.setName(bookMarkName);

    XWPFRun pRun = getOrAddParagraphFirstRun(p, isInsert, isNewLine);
    setParagraphRunFontInfo(p, pRun, content, fontFamily, fontSize);
    setParagraphTextStyleInfo(p, pRun, colorVal, isBlod, isUnderLine,
        underLineColor, underStyle, isItalic, isStrike, isDStrike,
        isShadow, isVanish, isEmboss, isImprint, isOutline, isEm,
        emType, isHightLight, hightStyle, isShd, shdStyle, shdColor,
        verticalAlign, position, spacingValue, indent);

    CTMarkupRange bookEnd = p.getCTP().addNewBookmarkEnd();
    bookEnd.setId(markId);

  }

  /*------------------------------------Word ���볬����---------------------------------------------------  */
  /**
   * @Description: Ĭ�ϵĳ�������ʽ
   */
  public void addParagraphTextHyperlinkBasicStyle(XWPFParagraph paragraph,
      String url, String text, String fontFamily, String fontSize,
      String colorVal, boolean isBlod, boolean isItalic, boolean isStrike) {
    addParagraphTextHyperlink(paragraph, url, text, fontFamily, fontSize,
        colorVal, isBlod, true, "0000FF", STUnderline.SINGLE, isItalic,
        isStrike, false, false, false, false, false, false, false,
        null, false, null, false, null, null, null, 0, 0, 0);
  }

  /**
   * @Description: ���ó�������ʽ
   */
  public void addParagraphTextHyperlink(XWPFParagraph paragraph, String url,
      String text, String fontFamily, String fontSize, String colorVal,
      boolean isBlod, boolean isUnderLine, String underLineColor,
      STUnderline.Enum underStyle, boolean isItalic, boolean isStrike,
      boolean isDStrike, boolean isShadow, boolean isVanish,
      boolean isEmboss, boolean isImprint, boolean isOutline,
      boolean isEm, STEm.Enum emType, boolean isHightLight,
      STHighlightColor.Enum hightStyle, boolean isShd,
      STShd.Enum shdStyle, String shdColor,
      STVerticalAlignRun.Enum verticalAlign, int position,
      int spacingValue, int indent) {
    // Add the link as External relationship
    String id = paragraph
        .getDocument()
        .getPackagePart()
        .addExternalRelationship(url,
            XWPFRelation.HYPERLINK.getRelation()).getId();
    // Append the link and bind it to the relationship
    CTHyperlink cLink = paragraph.getCTP().addNewHyperlink();
    cLink.setId(id);

    // Create the linked text
    CTText ctText = CTText.Factory.newInstance();
    ctText.setStringValue(text);
    CTR ctr = CTR.Factory.newInstance();
    CTRPr rpr = ctr.addNewRPr();

    if (StringUtils.isNotBlank(fontFamily)) {
      // ��������
      CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr
          .addNewRFonts();
      fonts.setAscii(fontFamily);
      fonts.setEastAsia(fontFamily);
      fonts.setHAnsi(fontFamily);
    }
    if (StringUtils.isNotBlank(fontSize)) {
      // ���������С
      CTHpsMeasure sz = rpr.isSetSz() ? rpr.getSz() : rpr.addNewSz();
      sz.setVal(new BigInteger(fontSize));

      CTHpsMeasure szCs = rpr.isSetSzCs() ? rpr.getSzCs() : rpr
          .addNewSzCs();
      szCs.setVal(new BigInteger(fontSize));
    }
    // ���ó�������ʽ
    // ������ɫ
    if (StringUtils.isNotBlank(colorVal)) {
      CTColor color = CTColor.Factory.newInstance();
      color.setVal(colorVal);
      rpr.setColor(color);
    }
    // �Ӵ�
    if (isBlod) {
      CTOnOff bCtOnOff = rpr.addNewB();
      bCtOnOff.setVal(STOnOff.TRUE);
    }
    // �»���
    if (isUnderLine) {
      CTUnderline udLine = rpr.addNewU();
      udLine.setVal(underStyle);
      udLine.setColor(underLineColor);
    }
    // ��б
    if (isItalic) {
      CTOnOff iCtOnOff = rpr.addNewI();
      iCtOnOff.setVal(STOnOff.TRUE);
    }
    // ɾ����
    if (isStrike) {
      CTOnOff sCtOnOff = rpr.addNewStrike();
      sCtOnOff.setVal(STOnOff.TRUE);
    }
    // ˫ɾ����
    if (isDStrike) {
      CTOnOff dsCtOnOff = rpr.addNewDstrike();
      dsCtOnOff.setVal(STOnOff.TRUE);
    }
    // ��Ӱ
    if (isShadow) {
      CTOnOff shadowCtOnOff = rpr.addNewShadow();
      shadowCtOnOff.setVal(STOnOff.TRUE);
    }
    // ����
    if (isVanish) {
      CTOnOff vanishCtOnOff = rpr.addNewVanish();
      vanishCtOnOff.setVal(STOnOff.TRUE);
    }
    // ����
    if (isEmboss) {
      CTOnOff embossCtOnOff = rpr.addNewEmboss();
      embossCtOnOff.setVal(STOnOff.TRUE);
    }
    // ����
    if (isImprint) {
      CTOnOff isImprintCtOnOff = rpr.addNewImprint();
      isImprintCtOnOff.setVal(STOnOff.TRUE);
    }
    // ����
    if (isOutline) {
      CTOnOff isOutlineCtOnOff = rpr.addNewOutline();
      isOutlineCtOnOff.setVal(STOnOff.TRUE);
    }
    // ���غ�
    if (isEm) {
      CTEm em = rpr.addNewEm();
      em.setVal(emType);
    }
    // ͻ����ʾ�ı�
    if (isHightLight) {
      if (hightStyle != null) {
        CTHighlight hightLight = rpr.addNewHighlight();
        hightLight.setVal(hightStyle);
      }
    }
    if (isShd) {
      // ���õ���
      CTShd shd = rpr.addNewShd();
      if (shdStyle != null) {
        shd.setVal(shdStyle);
      }
      if (shdColor != null) {
        shd.setColor(shdColor);
      }
    }
    // �ϱ��±�
    if (verticalAlign != null) {
      rpr.addNewVertAlign().setVal(verticalAlign);
    }
    // �����ı�λ��
    rpr.addNewPosition().setVal(new BigInteger(String.valueOf(position)));
    if (spacingValue != 0) {
      // �����ַ������Ϣ
      CTSignedTwipsMeasure ctSTwipsMeasure = rpr.addNewSpacing();
      ctSTwipsMeasure
          .setVal(new BigInteger(String.valueOf(spacingValue)));
    }
    // �����ַ��������
    if (indent > 0) {
      CTTextScale paramCTTextScale = rpr.addNewW();
      paramCTTextScale.setVal(indent);
    }
    ctr.setTArray(new CTText[] { ctText });
    cLink.setRArray(new CTR[] { ctr });
  }

  /*------------------------------------Word ҳüҳ�����---------------------------------------------------  */
  /**
   * @Description: ҳ��:��ʾҳ����Ϣ
   */
  public void simpleNumberFooter(XWPFDocument document) throws Exception {
    CTP ctp = CTP.Factory.newInstance();
    XWPFParagraph codePara = new XWPFParagraph(ctp, document);
    XWPFRun r1 = codePara.createRun();
    r1.setText("��");
    setParagraphRunFontInfo(codePara, r1, null, "΢���ź�", "22");

    r1 = codePara.createRun();
    CTFldChar fldChar = r1.getCTR().addNewFldChar();
    fldChar.setFldCharType(STFldCharType.BEGIN);

    r1 = codePara.createRun();
    CTText ctText = r1.getCTR().addNewInstrText();
    ctText.setStringValue("PAGE  \\* MERGEFORMAT");
    ctText.setSpace(SpaceAttribute.Space.PRESERVE);
    setParagraphRunFontInfo(codePara, r1, null, "΢���ź�", "22");

    fldChar = r1.getCTR().addNewFldChar();
    fldChar.setFldCharType(STFldCharType.END);

    r1 = codePara.createRun();
    r1.setText("ҳ �ܹ�");
    setParagraphRunFontInfo(codePara, r1, null, "΢���ź�", "22");

    r1 = codePara.createRun();
    fldChar = r1.getCTR().addNewFldChar();
    fldChar.setFldCharType(STFldCharType.BEGIN);

    r1 = codePara.createRun();
    ctText = r1.getCTR().addNewInstrText();
    ctText.setStringValue("NUMPAGES  \\* MERGEFORMAT ");
    ctText.setSpace(SpaceAttribute.Space.PRESERVE);
    setParagraphRunFontInfo(codePara, r1, null, "΢���ź�", "22");

    fldChar = r1.getCTR().addNewFldChar();
    fldChar.setFldCharType(STFldCharType.END);

    r1 = codePara.createRun();
    r1.setText("ҳ");
    setParagraphRunFontInfo(codePara, r1, null, "΢���ź�", "22");

    setParagraphAlignInfo(codePara, ParagraphAlignment.CENTER,
        TextAlignment.CENTER);
    codePara.setBorderTop(Borders.THICK);
    XWPFParagraph[] newparagraphs = new XWPFParagraph[1];
    newparagraphs[0] = codePara;
    CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
    XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(
        document, sectPr);
    headerFooterPolicy.createFooter(STHdrFtr.DEFAULT, newparagraphs);
  }

  /**
   * @Description: ҳü:��ʾʱ����Ϣ
   */
  public void simpleDateHeader(XWPFDocument document) throws Exception {
    CTP ctp = CTP.Factory.newInstance();
    XWPFParagraph codePara = new XWPFParagraph(ctp, document);

    XWPFRun r1 = codePara.createRun();
    CTFldChar fldChar = r1.getCTR().addNewFldChar();
    fldChar.setFldCharType(STFldCharType.BEGIN);

    r1 = codePara.createRun();
    CTText ctText = r1.getCTR().addNewInstrText();
    ctText.setStringValue("TIME \\@ \"EEEE\"");
    ctText.setSpace(SpaceAttribute.Space.PRESERVE);
    setParagraphRunFontInfo(codePara, r1, null, "΢���ź�", "22");

    fldChar = r1.getCTR().addNewFldChar();
    fldChar.setFldCharType(STFldCharType.END);

    r1 = codePara.createRun();
    r1.setText("��");
    setParagraphRunFontInfo(codePara, r1, null, "΢���ź�", "22");

    r1 = codePara.createRun();
    fldChar = r1.getCTR().addNewFldChar();
    fldChar.setFldCharType(STFldCharType.BEGIN);

    r1 = codePara.createRun();
    ctText = r1.getCTR().addNewInstrText();
    ctText.setStringValue("TIME \\@ \"O\"");
    ctText.setSpace(SpaceAttribute.Space.PRESERVE);
    setParagraphRunFontInfo(codePara, r1, null, "΢���ź�", "22");

    fldChar = r1.getCTR().addNewFldChar();
    fldChar.setFldCharType(STFldCharType.END);

    r1 = codePara.createRun();
    r1.setText("��");
    r1.setFontSize(11);
    setParagraphRunFontInfo(codePara, r1, null, "΢���ź�", "22");

    r1 = codePara.createRun();
    fldChar = r1.getCTR().addNewFldChar();
    fldChar.setFldCharType(STFldCharType.BEGIN);

    r1 = codePara.createRun();
    ctText = r1.getCTR().addNewInstrText();
    ctText.setStringValue("TIME \\@ \"A\"");
    ctText.setSpace(SpaceAttribute.Space.PRESERVE);
    setParagraphRunFontInfo(codePara, r1, null, "΢���ź�", "22");

    fldChar = r1.getCTR().addNewFldChar();
    fldChar.setFldCharType(STFldCharType.END);

    r1 = codePara.createRun();
    r1.setText("��");
    r1.setFontSize(11);
    setParagraphRunFontInfo(codePara, r1, null, "΢���ź�", "22");

    setParagraphAlignInfo(codePara, ParagraphAlignment.CENTER,
        TextAlignment.CENTER);
    codePara.setBorderBottom(Borders.THICK);
    XWPFParagraph[] newparagraphs = new XWPFParagraph[1];
    newparagraphs[0] = codePara;
    CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
    XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(
        document, sectPr);
    headerFooterPolicy.createHeader(STHdrFtr.DEFAULT, newparagraphs);
  }

  /*------------------------------------Word �������---------------------------------------------------  */
  /**
   * @Description: �õ�����CTPPr
   */
  public CTPPr getParagraphCTPPr(XWPFParagraph p) {
    CTPPr pPPr = null;
    if (p.getCTP() != null) {
      if (p.getCTP().getPPr() != null) {
        pPPr = p.getCTP().getPPr();
      } else {
        pPPr = p.getCTP().addNewPPr();
      }
    }
    return pPPr;
  }

  /**
   * @Description: �õ�XWPFRun��CTRPr
   */
  public CTRPr getRunCTRPr(XWPFParagraph p, XWPFRun pRun) {
    CTRPr pRpr = null;
    if (pRun.getCTR() != null) {
      pRpr = pRun.getCTR().getRPr();
      if (pRpr == null) {
        pRpr = pRun.getCTR().addNewRPr();
      }
    } else {
      pRpr = p.getCTP().addNewR().addNewRPr();
    }
    return pRpr;
  }

  public XWPFRun getOrAddParagraphFirstRun(XWPFParagraph p, boolean isInsert,
      boolean isNewLine) {
    XWPFRun pRun = null;
    if (isInsert) {
      pRun = p.createRun();
    } else {
      if (p.getRuns() != null && p.getRuns().size() > 0) {
        pRun = p.getRuns().get(0);
      } else {
        pRun = p.createRun();
      }
    }
    if (isNewLine) {
      pRun.addBreak();
    }
    return pRun;
  }

  public void setParagraphTextFontInfo(XWPFParagraph p, boolean isInsert,
      boolean isNewLine, String content, String fontFamily,
      String fontSize) {
    XWPFRun pRun = getOrAddParagraphFirstRun(p, isInsert, isNewLine);
    setParagraphRunFontInfo(p, pRun, content, fontFamily, fontSize);
  }

  /**
   * @Description ����������Ϣ
   */
  public void setParagraphRunFontInfo(XWPFParagraph p, XWPFRun pRun,
      String content, String fontFamily, String fontSize) {
    CTRPr pRpr = getRunCTRPr(p, pRun);
    if (StringUtils.isNotBlank(content)) {
      pRun.setText(content);
    }
    // ��������
    CTFonts fonts = pRpr.isSetRFonts() ? pRpr.getRFonts() : pRpr
        .addNewRFonts();
    fonts.setAscii(fontFamily);
    fonts.setEastAsia(fontFamily);
    fonts.setHAnsi(fontFamily);

    // ���������С
    CTHpsMeasure sz = pRpr.isSetSz() ? pRpr.getSz() : pRpr.addNewSz();
    sz.setVal(new BigInteger(fontSize));

    CTHpsMeasure szCs = pRpr.isSetSzCs() ? pRpr.getSzCs() : pRpr
        .addNewSzCs();
    szCs.setVal(new BigInteger(fontSize));
  }

  /**
   * @Description: ���ö��������ʽ
   */
  public void setParagraphTextBasicStyleInfo(XWPFParagraph p, XWPFRun pRun,
      String colorVal, boolean isBlod, boolean isUnderLine,
      String underLineColor, STUnderline.Enum underStyle,
      boolean isItalic, boolean isStrike, boolean isHightLight,
      STHighlightColor.Enum hightStyle, boolean isShd,
      STShd.Enum shdStyle, String shdColor) {
    setParagraphTextStyleInfo(p, pRun, colorVal, isBlod, isUnderLine,
        underLineColor, underStyle, isItalic, isStrike, false, false,
        false, false, false, false, false, null, isHightLight,
        hightStyle, isShd, shdStyle, shdColor, null, 0, 0, 0);
  }

  /**
   * @Description: ���ö����ı���ʽ(�����������ʾЧ����ͬ)�����ַ������Ϣ(CTSignedTwipsMeasure)
   * @param verticalAlign
   *            : SUPERSCRIPT�ϱ� SUBSCRIPT�±�
   * @param position
   *            :�ַ����λ�ã�>0���� <0����=��ֵ*2 ��3��=6
   * @param spacingValue
   *            :�ַ������ >0�ӿ� <0���� =��ֵ*20 ��2��=40
   * @param indent
   *            :�ַ�������� <100 ��
   */
  public void setParagraphTextSimpleStyleInfo(XWPFParagraph p, XWPFRun pRun,
      String colorVal, boolean isBlod, boolean isUnderLine,
      String underLineColor, STUnderline.Enum underStyle,
      boolean isItalic, boolean isStrike, boolean isHightLight,
      STHighlightColor.Enum hightStyle, boolean isShd,
      STShd.Enum shdStyle, String shdColor, VerticalAlign verticalAlign,
      int position, int spacingValue, int indent) {
    setParagraphTextStyleInfo(p, pRun, colorVal, isBlod, isUnderLine,
        underLineColor, underStyle, isItalic, isStrike, false, false,
        false, false, false, false, false, null, isHightLight,
        hightStyle, isShd, shdStyle, shdColor, verticalAlign, position,
        spacingValue, indent);
  }

  /**
   * @Description: ���ö����ı���ʽ(�����������ʾЧ����ͬ)�����ַ������Ϣ(CTSignedTwipsMeasure)
   * @param verticalAlign
   *            : SUPERSCRIPT�ϱ� SUBSCRIPT�±�
   * @param position
   *            :�ַ����λ�ã�>0���� <0����=��ֵ*2 ��3��=6
   * @param spacingValue
   *            :�ַ������ >0�ӿ� <0���� =��ֵ*20 ��2��=40
   * @param indent
   *            :�ַ�������� <100 ��
   */
  public void setParagraphTextStyleInfo(XWPFParagraph p, XWPFRun pRun,
      String colorVal, boolean isBlod, boolean isUnderLine,
      String underLineColor, STUnderline.Enum underStyle,
      boolean isItalic, boolean isStrike, boolean isDStrike,
      boolean isShadow, boolean isVanish, boolean isEmboss,
      boolean isImprint, boolean isOutline, boolean isEm,
      STEm.Enum emType, boolean isHightLight,
      STHighlightColor.Enum hightStyle, boolean isShd,
      STShd.Enum shdStyle, String shdColor, VerticalAlign verticalAlign,
      int position, int spacingValue, int indent) {
    if (pRun == null) {
      return;
    }
    CTRPr pRpr = getRunCTRPr(p, pRun);
    if (colorVal != null) {
      pRun.setColor(colorVal);
    }
    // ����������ʽ
    // �Ӵ�
    if (isBlod) {
      pRun.setBold(isBlod);
    }
    // ��б
    if (isItalic) {
      pRun.setItalic(isItalic);
    }
    // ɾ����
    if (isStrike) {
      pRun.setStrike(isStrike);
    }
    // ˫ɾ����
    if (isDStrike) {
      CTOnOff dsCtOnOff = pRpr.isSetDstrike() ? pRpr.getDstrike() : pRpr
          .addNewDstrike();
      dsCtOnOff.setVal(STOnOff.TRUE);
    }
    // ��Ӱ
    if (isShadow) {
      CTOnOff shadowCtOnOff = pRpr.isSetShadow() ? pRpr.getShadow()
          : pRpr.addNewShadow();
      shadowCtOnOff.setVal(STOnOff.TRUE);
    }
    // ����
    if (isVanish) {
      CTOnOff vanishCtOnOff = pRpr.isSetVanish() ? pRpr.getVanish()
          : pRpr.addNewVanish();
      vanishCtOnOff.setVal(STOnOff.TRUE);
    }
    // ����
    if (isEmboss) {
      CTOnOff embossCtOnOff = pRpr.isSetEmboss() ? pRpr.getEmboss()
          : pRpr.addNewEmboss();
      embossCtOnOff.setVal(STOnOff.TRUE);
    }
    // ����
    if (isImprint) {
      CTOnOff isImprintCtOnOff = pRpr.isSetImprint() ? pRpr.getImprint()
          : pRpr.addNewImprint();
      isImprintCtOnOff.setVal(STOnOff.TRUE);
    }
    // ����
    if (isOutline) {
      CTOnOff isOutlineCtOnOff = pRpr.isSetOutline() ? pRpr.getOutline()
          : pRpr.addNewOutline();
      isOutlineCtOnOff.setVal(STOnOff.TRUE);
    }
    // ���غ�
    if (isEm) {
      CTEm em = pRpr.isSetEm() ? pRpr.getEm() : pRpr.addNewEm();
      em.setVal(emType);
    }
    // �����»�����ʽ
    if (isUnderLine) {
      CTUnderline u = pRpr.isSetU() ? pRpr.getU() : pRpr.addNewU();
      if (underStyle != null) {
        u.setVal(underStyle);
      }
      if (underLineColor != null) {
        u.setColor(underLineColor);
      }
    }
    // ����ͻ����ʾ�ı�
    if (isHightLight) {
      if (hightStyle != null) {
        CTHighlight hightLight = pRpr.isSetHighlight() ? pRpr
            .getHighlight() : pRpr.addNewHighlight();
        hightLight.setVal(hightStyle);
      }
    }
    if (isShd) {
      // ���õ���
      CTShd shd = pRpr.isSetShd() ? pRpr.getShd() : pRpr.addNewShd();
      if (shdStyle != null) {
        shd.setVal(shdStyle);
      }
      if (shdColor != null) {
        shd.setColor(shdColor);
      }
    }
    // �ϱ��±�
    if (verticalAlign != null) {
      pRun.setSubscript(verticalAlign);
    }
    // �����ı�λ��
    pRun.setTextPosition(position);
    if (spacingValue > 0) {
      // �����ַ������Ϣ
      CTSignedTwipsMeasure ctSTwipsMeasure = pRpr.isSetSpacing() ? pRpr
          .getSpacing() : pRpr.addNewSpacing();
      ctSTwipsMeasure
          .setVal(new BigInteger(String.valueOf(spacingValue)));
    }
    if (indent > 0) {
      CTTextScale paramCTTextScale = pRpr.isSetW() ? pRpr.getW() : pRpr
          .addNewW();
      paramCTTextScale.setVal(indent);
    }
  }

  /**
   * @Description: ���ö������(����������������)
   */
  public void setParagraphShdStyle(XWPFParagraph p, boolean isShd,
      STShd.Enum shdStyle, String shdColor) {
    CTPPr pPpr = getParagraphCTPPr(p);
    CTShd shd = pPpr.isSetShd() ? pPpr.getShd() : pPpr.addNewShd();
    if (shdStyle != null) {
      shd.setVal(shdStyle);
    }
    if (shdColor != null) {
      shd.setColor(shdColor);
    }
  }

  /**
   * @Description: ���ö�������Ϣ,һ��=100 һ��=20
   */
  public void setParagraphSpacingInfo(XWPFParagraph p, boolean isSpace,
      String before, String after, String beforeLines, String afterLines,
      boolean isLine, String line, STLineSpacingRule.Enum lineValue) {
    CTPPr pPPr = getParagraphCTPPr(p);
    CTSpacing pSpacing = pPPr.getSpacing() != null ? pPPr.getSpacing()
        : pPPr.addNewSpacing();
    if (isSpace) {
      // ��ǰ����
      if (before != null) {
        pSpacing.setBefore(new BigInteger(before));
      }
      // �κ����
      if (after != null) {
        pSpacing.setAfter(new BigInteger(after));
      }
      // ��ǰ����
      if (beforeLines != null) {
        pSpacing.setBeforeLines(new BigInteger(beforeLines));
      }
      // �κ�����
      if (afterLines != null) {
        pSpacing.setAfterLines(new BigInteger(afterLines));
      }
    }
    // ���
    if (isLine) {
      if (line != null) {
        pSpacing.setLine(new BigInteger(line));
      }
      if (lineValue != null) {
        pSpacing.setLineRule(lineValue);
      }
    }
  }

  // ���ö���������Ϣ 1���ס�567
  public void setParagraphIndInfo(XWPFParagraph p, String firstLine,
      String firstLineChar, String hanging, String hangingChar,
      String right, String rigthChar, String left, String leftChar) {
    CTPPr pPPr = getParagraphCTPPr(p);
    CTInd pInd = pPPr.getInd() != null ? pPPr.getInd() : pPPr.addNewInd();
    if (firstLine != null) {
      pInd.setFirstLine(new BigInteger(firstLine));
    }
    if (firstLineChar != null) {
      pInd.setFirstLineChars(new BigInteger(firstLineChar));
    }
    if (hanging != null) {
      pInd.setHanging(new BigInteger(hanging));
    }
    if (hangingChar != null) {
      pInd.setHangingChars(new BigInteger(hangingChar));
    }
    if (left != null) {
      pInd.setLeft(new BigInteger(left));
    }
    if (leftChar != null) {
      pInd.setLeftChars(new BigInteger(leftChar));
    }
    if (right != null) {
      pInd.setRight(new BigInteger(right));
    }
    if (rigthChar != null) {
      pInd.setRightChars(new BigInteger(rigthChar));
    }
  }

  // ���ö���߿�
  public void setParagraphBorders(XWPFParagraph p, Borders lborder,
      Borders tBorders, Borders rBorders, Borders bBorders,
      Borders btborders) {
    if (lborder != null) {
      p.setBorderLeft(lborder);
    }
    if (tBorders != null) {
      p.setBorderTop(tBorders);
    }
    if (rBorders != null) {
      p.setBorderRight(rBorders);
    }
    if (bBorders != null) {
      p.setBorderBottom(bBorders);
    }
    if (btborders != null) {
      p.setBorderBetween(btborders);
    }
  }

  /**
   * @Description: ���ö������
   */
  public void setParagraphAlignInfo(XWPFParagraph p,
      ParagraphAlignment pAlign, TextAlignment valign) {
    if (pAlign != null) {
      p.setAlignment(pAlign);
    }
    if (valign != null) {
      p.setVerticalAlignment(valign);
    }
  }

  /*------------------------------------Word ������---------------------------------------------------  */
  /**
   * @Description:ɾ��ָ��λ�õı��,��ɾ�����������λ��--
   */
  public void deleteTableByIndex(XWPFDocument xdoc, int pos) {
    Iterator<IBodyElement> bodyElement = xdoc.getBodyElementsIterator();
    int eIndex = 0, tableIndex = -1;
    while (bodyElement.hasNext()) {
      IBodyElement element = bodyElement.next();
      BodyElementType elementType = element.getElementType();
      if (elementType == BodyElementType.TABLE) {
        tableIndex++;
        if (tableIndex == pos) {
          break;
        }
      }
      eIndex++;
    }
    xdoc.removeBodyElement(eIndex);
  }

  public XWPFTable getTableByIndex(XWPFDocument xdoc, int index) {
    List<XWPFTable> tablesList = getAllTable(xdoc);
    if (tablesList == null || index < 0 || index > tablesList.size()) {
      return null;
    }
    return tablesList.get(index);
  }

  public List<XWPFTable> getAllTable(XWPFDocument xdoc) {
    return xdoc.getTables();
  }

  /**
   * @Description: �õ��������(��һ�ο��е�Ԫ����Ϊһ�����ڶ����������кϲ��ĵ�Ԫ��)
   */
  public List<List<String>> getTableRContent(XWPFTable table) {
    List<List<String>> tableContentList = new ArrayList<List<String>>();
    for (int rowIndex = 0, rowLen = table.getNumberOfRows(); rowIndex < rowLen; rowIndex++) {
      XWPFTableRow row = table.getRow(rowIndex);
      List<String> cellContentList = new ArrayList<String>();
      for (int colIndex = 0, colLen = row.getTableCells().size(); colIndex < colLen; colIndex++) {
        XWPFTableCell cell = row.getCell(colIndex);
        CTTc ctTc = cell.getCTTc();
        if (ctTc.isSetTcPr()) {
          CTTcPr tcPr = ctTc.getTcPr();
          if (tcPr.isSetHMerge()) {
            CTHMerge hMerge = tcPr.getHMerge();
            if (STMerge.RESTART.equals(hMerge.getVal())) {
              cellContentList.add(getTableCellContent(cell));
            }
          } else if (tcPr.isSetVMerge()) {
            CTVMerge vMerge = tcPr.getVMerge();
            if (STMerge.RESTART.equals(vMerge.getVal())) {
              cellContentList.add(getTableCellContent(cell));
            }
          } else {
            cellContentList.add(getTableCellContent(cell));
          }
        }
      }
      tableContentList.add(cellContentList);
    }
    return tableContentList;
  }

  /**
   * @Description: �õ��������,�ϲ���ĵ�Ԫ����Ϊһ����Ԫ��
   */
  public List<List<String>> getTableContent(XWPFTable table) {
    List<List<String>> tableContentList = new ArrayList<List<String>>();
    for (int rowIndex = 0, rowLen = table.getNumberOfRows(); rowIndex < rowLen; rowIndex++) {
      XWPFTableRow row = table.getRow(rowIndex);
      List<String> cellContentList = new ArrayList<String>();
      for (int colIndex = 0, colLen = row.getTableCells().size(); colIndex < colLen; colIndex++) {
        XWPFTableCell cell = row.getCell(colIndex);
        cellContentList.add(getTableCellContent(cell));
      }
      tableContentList.add(cellContentList);
    }
    return tableContentList;
  }

  public String getTableCellContent(XWPFTableCell cell) {
    StringBuffer sb = new StringBuffer();
    List<XWPFParagraph> cellPList = cell.getParagraphs();
    if (cellPList != null && cellPList.size() > 0) {
      for (XWPFParagraph xwpfPr : cellPList) {
        List<XWPFRun> runs = xwpfPr.getRuns();
        if (runs != null && runs.size() > 0) {
          for (XWPFRun xwpfRun : runs) {
            sb.append(xwpfRun.getText(0));
          }
        }
      }
    }
    return sb.toString();
  }

  /**
   * @Description: �������,��������������1��1��,�����п�
   */
  public XWPFTable createTable(XWPFDocument xdoc, int rowSize, int cellSize,
      boolean isSetColWidth, int[] colWidths) {
    XWPFTable table = xdoc.createTable(rowSize, cellSize);
    if (isSetColWidth) {
      CTTbl ttbl = table.getCTTbl();
      CTTblGrid tblGrid = ttbl.addNewTblGrid();
      for (int j = 0, len = Math.min(cellSize, colWidths.length); j < len; j++) {
        CTTblGridCol gridCol = tblGrid.addNewGridCol();
        gridCol.setW(new BigInteger(String.valueOf(colWidths[j])));
      }
    }
    return table;
  }

  /**
   * @Description: ���ñ���ܿ����ˮƽ���뷽ʽ
   */
  public void setTableWidthAndHAlign(XWPFTable table, String width,
      STJc.Enum enumValue) {
    CTTblPr tblPr = getTableCTTblPr(table);
    CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr
        .addNewTblW();
    if (enumValue != null) {
      CTJc cTJc = tblPr.addNewJc();
      cTJc.setVal(enumValue);
    }
    tblWidth.setW(new BigInteger(width));
    tblWidth.setType(STTblWidth.DXA);
  }

  /**
   * @Description: �õ�Table��CTTblPr,���������½�
   */
  public CTTblPr getTableCTTblPr(XWPFTable table) {
    CTTbl ttbl = table.getCTTbl();
    CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl
        .getTblPr();
    return tblPr;
  }

  /**
   * @Description: �õ�Table�ı߿�,���������½�
   */
  public CTTblBorders getTableBorders(XWPFTable table) {
    CTTblPr tblPr = getTableCTTblPr(table);
    CTTblBorders tblBorders = tblPr.isSetTblBorders() ? tblPr
        .getTblBorders() : tblPr.addNewTblBorders();
    return tblBorders;
  }

  /**
   * @Description: ���ñ��߿���ʽ
   */
  public void setTableBorders(XWPFTable table, CTBorder left, CTBorder top,
      CTBorder right, CTBorder bottom) {
    CTTblBorders tblBorders = getTableBorders(table);
    if (left != null) {
      tblBorders.setLeft(left);
    }
    if (top != null) {
      tblBorders.setTop(top);
    }
    if (right != null) {
      tblBorders.setRight(right);
    }
    if (bottom != null) {
      tblBorders.setBottom(bottom);
    }
  }

  /**
   * @Description: ���ָ��λ�ò���һ��, indexΪ���������ڵ���λ��(���ܴ��ڱ�����)
   */
  public void insertTableRowAtIndex(XWPFTable table, int index) {
    XWPFTableRow firstRow = table.getRow(0);
    XWPFTableRow row = table.insertNewTableRow(index);
    if (row == null) {
      return;
    }
    CTTbl ctTbl = table.getCTTbl();
    CTTblGrid tblGrid = ctTbl.getTblGrid();
    int cellSize = 0;
    boolean isAdd = false;
    if (tblGrid != null) {
      List<CTTblGridCol> gridColList = tblGrid.getGridColList();
      if (gridColList != null && gridColList.size() > 0) {
        isAdd = true;
        for (CTTblGridCol ctlCol : gridColList) {
          XWPFTableCell cell = row.addNewTableCell();
          setCellWidthAndVAlign(cell, ctlCol.getW().toString(),
              STTblWidth.DXA, null);
        }
      }
    }
    // �󲿷ֶ������ߵ���һ��
    if (!isAdd) {
      cellSize = getCellSizeWithMergeNum(firstRow);
      for (int i = 0; i < cellSize; i++) {
        row.addNewTableCell();
      }
    }
  }

  /**
   * @Description: ɾ����һ��
   */
  public void deleteTableRow(XWPFTable table, int index) {
    table.removeRow(index);
  }

  /**
   * @Description: ͳ������(�����ϲ�������)
   */
  public int getCellSizeWithMergeNum(XWPFTableRow row) {
    List<XWPFTableCell> firstRowCellList = row.getTableCells();
    int cellSize = firstRowCellList.size();
    for (XWPFTableCell xwpfTableCell : firstRowCellList) {
      CTTc ctTc = xwpfTableCell.getCTTc();
      if (ctTc.isSetTcPr()) {
        CTTcPr tcPr = ctTc.getTcPr();
        if (tcPr.isSetGridSpan()) {
          CTDecimalNumber gridSpan = tcPr.getGridSpan();
          cellSize += gridSpan.getVal().intValue() - 1;
        }
      }
    }
    return cellSize;
  }

  /**
   * @Description: �õ�CTTrPr,���������½�
   */
  public CTTrPr getRowCTTrPr(XWPFTableRow row) {
    CTRow ctRow = row.getCtRow();
    CTTrPr trPr = ctRow.isSetTrPr() ? ctRow.getTrPr() : ctRow.addNewTrPr();
    return trPr;
  }

  /**
   * @Description: �����и�
   */
  public void setRowHeight(XWPFTableRow row, String hight,
      STHeightRule.Enum heigthEnum) {
    CTTrPr trPr = getRowCTTrPr(row);
    CTHeight trHeight;
    if (trPr.getTrHeightList() != null && trPr.getTrHeightList().size() > 0) {
      trHeight = trPr.getTrHeightList().get(0);
    } else {
      trHeight = trPr.addNewTrHeight();
    }
    trHeight.setVal(new BigInteger(hight));
    if (heigthEnum != null) {
      trHeight.setHRule(heigthEnum);
    }
  }

  /**
   * @Description: ������
   */
  public void setRowHidden(XWPFTableRow row, boolean hidden) {
    CTTrPr trPr = getRowCTTrPr(row);
    CTOnOff hiddenValue;
    if (trPr.getHiddenList() != null && trPr.getHiddenList().size() > 0) {
      hiddenValue = trPr.getHiddenList().get(0);
    } else {
      hiddenValue = trPr.addNewHidden();
    }
    if (hidden) {
      hiddenValue.setVal(STOnOff.TRUE);
    } else {
      hiddenValue.setVal(STOnOff.FALSE);
    }
    setRowAllCellHidden(row, hidden);
  }

  public void setRowAllCellHidden(XWPFTableRow row, boolean isVanish) {
    for (int colIndex = 0, colLen = row.getTableCells().size(); colIndex < colLen; colIndex++) {
      XWPFTableCell cell = row.getCell(colIndex);
      setCellHidden(cell, isVanish);
    }
  }

  /**
   * @Description: ���õ�Ԫ������
   */
  public void setCellNewContent(XWPFTable table, int rowIndex, int col,String content) {
    XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
    XWPFParagraph p = getCellFirstParagraph(cell);
    List<XWPFRun> cellRunList = p.getRuns();
    if (cellRunList == null || cellRunList.size() == 0) {
      return;
    }
    for (int i = cellRunList.size() - 1; i >= 1; i--) {
      p.removeRun(i);
    }
    XWPFRun run = cellRunList.get(0);
    run.setText(content);
  }
  
  /**
   * @Description: ɾ����Ԫ������
   */
  public void deleteCellContent(XWPFTable table, int rowIndex, int col) {
    XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
    XWPFParagraph p = getCellFirstParagraph(cell);
    List<XWPFRun> cellRunList = p.getRuns();
    if (cellRunList == null || cellRunList.size() == 0) {
      return;
    }
    for (int i = cellRunList.size() - 1; i >= 0; i--) {
      p.removeRun(i);
    }
  }

  /**
   * @Description: ���ص�Ԫ������
   */
  public void setHiddenCellContent(XWPFTable table, int rowIndex, int col) {
    XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
    setCellHidden(cell, true);
  }

  public void setCellHidden(XWPFTableCell cell, boolean isVanish) {
    XWPFParagraph p = getCellFirstParagraph(cell);
    CTPPr pPPr = getParagraphCTPPr(p);
    CTParaRPr paRpr = pPPr.isSetRPr() ? pPPr.getRPr() : pPPr.addNewRPr();
    CTOnOff vanishCtOnOff = paRpr.isSetVanish() ? paRpr.getVanish() : paRpr
        .addNewVanish();
    if (isVanish) {
      vanishCtOnOff.setVal(STOnOff.TRUE);
    } else {
      vanishCtOnOff.setVal(STOnOff.FALSE);
    }
    List<XWPFRun> cellRunList = p.getRuns();
    if (cellRunList == null || cellRunList.size() == 0) {
      return;
    }
    for (XWPFRun xwpfRun : cellRunList) {
      CTRPr pRpr = getRunCTRPr(p, xwpfRun);
      vanishCtOnOff = pRpr.isSetVanish() ? pRpr.getVanish() : pRpr
          .addNewVanish();
      if (isVanish) {
        vanishCtOnOff.setVal(STOnOff.TRUE);
      } else {
        vanishCtOnOff.setVal(STOnOff.FALSE);
      }
    }
  }

  /**
   * 
   * @Description: �õ�Cell��CTTcPr,���������½�
   */
  public CTTcPr getCellCTTcPr(XWPFTableCell cell) {
    CTTc cttc = cell.getCTTc();
    CTTcPr tcPr = cttc.isSetTcPr() ? cttc.getTcPr() : cttc.addNewTcPr();
    return tcPr;
  }

  /**
   * @Description: ���ô�ֱ���뷽ʽ
   */
  public void setCellVAlign(XWPFTableCell cell, STVerticalJc.Enum vAlign) {
    setCellWidthAndVAlign(cell, null, null, vAlign);
  }

  /**
   * @Description: �����п�ʹ�ֱ���뷽ʽ
   */
  public void setCellWidthAndVAlign(XWPFTableCell cell, String width,
      STTblWidth.Enum typeEnum, STVerticalJc.Enum vAlign) {
    CTTcPr tcPr = getCellCTTcPr(cell);
    CTTblWidth tcw = tcPr.isSetTcW() ? tcPr.getTcW() : tcPr.addNewTcW();
    if (typeEnum != null) {
      tcw.setType(typeEnum);
    }
    if (width != null) {
      tcw.setW(new BigInteger(width));
    }
    if (vAlign != null) {
      CTVerticalJc vJc = tcPr.isSetVAlign() ? tcPr.getVAlign() : tcPr
          .addNewVAlign();
      vJc.setVal(vAlign);
    }
  }

  /**
   * @Description: �õ���Ԫ���һ��Paragraph
   */
  public XWPFParagraph getCellFirstParagraph(XWPFTableCell cell) {
    XWPFParagraph p;
    if (cell.getParagraphs() != null && cell.getParagraphs().size() > 0) {
      p = cell.getParagraphs().get(0);
    } else {
      p = cell.addParagraph();
    }
    return p;
  }

  /**
   * @Description: ���кϲ�
   */
  public void mergeCellsHorizontal(XWPFTable table, int row, int fromCell,
      int toCell) {
    for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
      XWPFTableCell cell = table.getRow(row).getCell(cellIndex);
      if (cellIndex == fromCell) {
        // The first merged cell is set with RESTART merge value
        getCellCTTcPr(cell).addNewHMerge().setVal(STMerge.RESTART);
      } else {
        // Cells which join (merge) the first one,are set with CONTINUE
        getCellCTTcPr(cell).addNewHMerge().setVal(STMerge.CONTINUE);
      }
    }
  }

  /**
   * @Description: ���кϲ�
   * @see http://stackoverflow.com/questions/24907541/row-span-with-xwpftable
   */
  public void mergeCellsVertically(XWPFTable table, int col, int fromRow,
      int toRow) {
    for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
      XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
      if (rowIndex == fromRow) {
        // The first merged cell is set with RESTART merge value
        getCellCTTcPr(cell).addNewVMerge().setVal(STMerge.RESTART);
      } else {
        // Cells which join (merge) the first one,are set with CONTINUE
        getCellCTTcPr(cell).addNewVMerge().setVal(STMerge.CONTINUE);
      }
    }
  }

  /*------------------------------------Word �ĵ���Ϣ---------------------------------------------------  */
  /**
   * @Description: ����ҳ�汳��ɫ
   */
  public void setDocumentbackground(XWPFDocument document, String bgColor) {
    CTBackground bg = document.getDocument().isSetBackground() ? document
        .getDocument().getBackground() : document.getDocument()
        .addNewBackground();
    bg.setColor(bgColor);
  }

  public CTSectPr getDocumentCTSectPr(XWPFDocument document) {
    CTSectPr sectPr = document.getDocument().getBody().isSetSectPr() ? document
        .getDocument().getBody().getSectPr()
        : document.getDocument().getBody().addNewSectPr();
    return sectPr;
  }

  /**
   * @Description: ҳ��Break
   */
  public void addNewPageBreak(XWPFDocument document, BreakType breakType) {
    XWPFParagraph xp = document.createParagraph();
    xp.createRun().addBreak(breakType);
  }

  /**
   * @Description: ����ҳ��߿�
   */
  public void setPgBorders(XWPFDocument document, CTBorder top,
      CTBorder right, CTBorder bottom, CTBorder left) {
    CTSectPr sectPr = getDocumentCTSectPr(document);
    CTPageBorders pd = sectPr.isSetPgBorders() ? sectPr.getPgBorders()
        : sectPr.addNewPgBorders();
    /*
     * CTBorder bb = pd.addNewBottom(); bb.setVal(STBorder.SINGLE);
     * bb.setSz(new BigInteger("4")); bb.setSpace(new BigInteger("24"));
     * bb.setColor("FBB61F");
     */
    if (top != null) {
      pd.setTop(top);
    }
    if (right != null) {
      pd.setRight(right);
    }
    if (bottom != null) {
      pd.setBottom(bottom);
    }
    if (left != null) {
      pd.setLeft(left);
    }
  }

  /**
   * @Description: ����ҳ���С��ֽ�ŷ��� landscape����
   */
  public void setDocumentSize(XWPFDocument document, String width,
      String height, STPageOrientation.Enum stValue) {
    CTSectPr sectPr = getDocumentCTSectPr(document);
    CTPageSz pgsz = sectPr.isSetPgSz() ? sectPr.getPgSz() : sectPr
        .addNewPgSz();
    pgsz.setH(new BigInteger(height));
    pgsz.setW(new BigInteger(width));
    pgsz.setOrient(stValue);
  }

  /**
   * @Description: ����ҳ�߾� (word��1����Լ����567)
   */
  public void setDocumentMargin(XWPFDocument document, String left,
      String top, String right, String bottom) {
    CTSectPr sectPr = getDocumentCTSectPr(document);
    CTPageMar ctpagemar = sectPr.addNewPgMar();
    if (StringUtils.isNotBlank(left)) {
      ctpagemar.setLeft(new BigInteger(left));
    }
    if (StringUtils.isNotBlank(top)) {
      ctpagemar.setTop(new BigInteger(top));
    }
    if (StringUtils.isNotBlank(right)) {
      ctpagemar.setRight(new BigInteger(right));
    }
    if (StringUtils.isNotBlank(bottom)) {
      ctpagemar.setBottom(new BigInteger(bottom));
    }
  }

  /**
   * @Description: �����ĵ�
   */
  public void saveDocument(XWPFDocument document, String savePath)
      throws Exception {
    FileOutputStream fos = new FileOutputStream(savePath);
    document.write(fos);
    fos.close();
  }

  /**
   * @Description: ��word�ĵ�
   */
  public XWPFDocument openDocument(String filePath) throws Exception {
    XWPFDocument xdoc = new XWPFDocument(
        POIXMLDocument.openPackage(filePath));
    return xdoc;
  }

}

/*class CustomXWPFDocument_S_9 extends XWPFDocument {
  public CustomXWPFDocument_S_9() {
    super();
  }

  public CustomXWPFDocument_S_9(InputStream in) throws IOException {
    super(in);
  }

  public CustomXWPFDocument_S_9(OPCPackage pkg) throws IOException {
    super(pkg);
  }

  public void createPicture(String blipId, int id, int width, int height,
      XWPFParagraph paragraph) {
    final int EMU = 9525;
    width *= EMU;
    height *= EMU;
    // String blipId =
    // getAllPictures().get(id).getPackageRelationship().getId();
    if (paragraph == null) {
      paragraph = createParagraph();
    }
    CTInline inline = paragraph.createRun().getCTR().addNewDrawing()
        .addNewInline();
    String picXml = ""
        + "<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">"
        + "   <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
        + "      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
        + "         <pic:nvPicPr>" + "            <pic:cNvPr id=\""
        + id
        + "\" name=\"img_"
        + id
        + "\"/>"
        + "            <pic:cNvPicPr/>"
        + "         </pic:nvPicPr>"
        + "         <pic:blipFill>"
        + "            <a:blip r:embed=\""
        + blipId
        + "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>"
        + "            <a:stretch>"
        + "               <a:fillRect/>"
        + "            </a:stretch>"
        + "         </pic:blipFill>"
        + "         <pic:spPr>"
        + "            <a:xfrm>"
        + "               <a:off x=\"0\" y=\"0\"/>"
        + "               <a:ext cx=\""
        + width
        + "\" cy=\""
        + height
        + "\"/>"
        + "            </a:xfrm>"
        + "            <a:prstGeom prst=\"rect\">"
        + "               <a:avLst/>"
        + "            </a:prstGeom>"
        + "         </pic:spPr>"
        + "      </pic:pic>"
        + "   </a:graphicData>" + "</a:graphic>";
    // CTGraphicalObjectData graphicData =
    // inline.addNewGraphic().addNewGraphicData();
    XmlToken xmlToken = null;
    try {
      xmlToken = XmlToken.Factory.parse(picXml);
    } catch (XmlException xe) {
      xe.printStackTrace();
    }
    inline.set(xmlToken);
    // graphicData.set(xmlToken);
    inline.setDistT(0);
    inline.setDistB(0);
    inline.setDistL(0);
    inline.setDistR(0);
    CTPositiveSize2D extent = inline.addNewExtent();
    extent.setCx(width);
    extent.setCy(height);
    CTNonVisualDrawingProps docPr = inline.addNewDocPr();
    docPr.setId(id);
    docPr.setName("docx_img_ " + id);
    docPr.setDescr("docx Picture");
  }

  public void createPictureCxCy(String blipId, int id, long cx, long cy) {
    CTInline inline = createParagraph().createRun().getCTR()
        .addNewDrawing().addNewInline();
    String picXml = ""
        + "<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">"
        + "   <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
        + "      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
        + "         <pic:nvPicPr>" + "            <pic:cNvPr id=\""
        + id
        + "\" name=\"Generated\"/>"
        + "            <pic:cNvPicPr/>"
        + "         </pic:nvPicPr>"
        + "         <pic:blipFill>"
        + "            <a:blip r:embed=\""
        + blipId
        + "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>"
        + "            <a:stretch>"
        + "               <a:fillRect/>"
        + "            </a:stretch>"
        + "         </pic:blipFill>"
        + "         <pic:spPr>"
        + "            <a:xfrm>"
        + "               <a:off x=\"0\" y=\"0\"/>"
        + "               <a:ext cx=\""
        + cx
        + "\" cy=\""
        + cy
        + "\"/>"
        + "            </a:xfrm>"
        + "            <a:prstGeom prst=\"rect\">"
        + "               <a:avLst/>"
        + "            </a:prstGeom>"
        + "         </pic:spPr>"
        + "      </pic:pic>"
        + "   </a:graphicData>" + "</a:graphic>";
    // CTGraphicalObjectData graphicData =
    // inline.addNewGraphic().addNewGraphicData();
    XmlToken xmlToken = null;
    try {
      xmlToken = XmlToken.Factory.parse(picXml);
    } catch (XmlException xe) {
      xe.printStackTrace();
    }
    inline.set(xmlToken);
    // graphicData.set(xmlToken);
    inline.setDistT(0);
    inline.setDistB(0);
    inline.setDistL(0);
    inline.setDistR(0);
    CTPositiveSize2D extent = inline.addNewExtent();
    extent.setCx(cx);
    extent.setCy(cy);
    CTNonVisualDrawingProps docPr = inline.addNewDocPr();
    docPr.setId(id);
    docPr.setName("docx_img_ " + id);
    docPr.setDescr("docx Picture");
  }


//���Է�����
public static void main(String[] args) throws Exception {
    WordAssist t = new WordAssist();
    CustomXWPFDocument_S_9 xdoc = new CustomXWPFDocument_S_9();
    t.testCreateDocumentStyle(xdoc);
    t.testAddHeaderFooter(xdoc);
    t.testCreateSimpleParagraph(xdoc);
    t.addNewPageBreak(xdoc, BreakType.TEXT_WRAPPING);
    t.testCreateParagraphWithImg(xdoc);
    t.addNewPageBreak(xdoc, BreakType.TEXT_WRAPPING);
    t.testCreateParagraphWithHyLink(xdoc);
    t.addNewPageBreak(xdoc, BreakType.TEXT_WRAPPING);
    t.testCreateParagraphWithBookMark(xdoc);
    t.addNewPageBreak(xdoc, BreakType.TEXT_WRAPPING);
    t.testCreateTable(xdoc);
    t.addNewPageBreak(xdoc, BreakType.TEXT_WRAPPING);
    String filePath = "f:/saveFile/temp/sys_" + System.currentTimeMillis()
        + ".docx";
    t.saveDocument(xdoc, filePath);
    XWPFDocument xdoc2 = t.openDocument(filePath);
    XWPFTable table = t.getTableByIndex(xdoc2, 0);
    List<List<String>> tableList = t.getTableRContent(table);
    for (List<String> list : tableList) {
      System.out.println(list);
    }
    XWPFDocument xdoc2 = t
        .openDocument("f:/saveFile/temp/sys_1423892350239.docx");
    XWPFTable table = t.getTableByIndex(xdoc2, 0);
    List<List<String>> tableList = t.getTableRContent(table);
    for (List<String> list : tableList) {
      System.out.println(list);
    }
    t.insertTableRowAtIndex(table, 3);
    t.deleteTableRow(table, 4);
    t.deleteTableRow(table, 3);
    t.setCellNewContent(table, 0, 0,"������");
    t.deleteCellContent(table, 1, 0);
    t.setHiddenCellContent(table, 2, 1);
    t.deleteTableByIndex(xdoc2, 1);
    t.saveDocument(xdoc2,
        "f:/saveFile/temp/sys_" + System.currentTimeMillis() + ".docx");
  }

  public void testCreateTable(XWPFDocument xdoc) {
    int[] colWidthArr = new int[] { 1500, 3000, 1200, 850, 600, 850 };
    XWPFTable table = createTable(xdoc, 8, 6, true, colWidthArr);
    setTableWidthAndHAlign(table, "8000", STJc.CENTER);
    CTTblBorders tblBorders = getTableBorders(table);
    CTBorder rBorder = tblBorders.addNewTop();
    rBorder.setVal(STBorder.SINGLE);
    rBorder.setSz(new BigInteger("3"));
    rBorder.setColor("BF9C9C");
    CTBorder lBorder = tblBorders.addNewLeft();
    lBorder.setVal(STBorder.NONE);

    setTableBorders(table, lBorder, rBorder, lBorder, rBorder);
    testFillTableValue(table);
    mergeCellsVertically(table, 1, 1, 4);
    mergeCellsVertically(table, 4, 2, 4);
    mergeCellsHorizontal(table, 0, 3, 5);
    mergeCellsHorizontal(table, 2, 2, 3);
  }

  public void testFillTableValue(XWPFTable table) {
    for (int rowIndex = 0, rowLen = table.getNumberOfRows(); rowIndex < rowLen; rowIndex++) {
      XWPFTableRow row = table.getRow(rowIndex);
      if (rowIndex == rowLen - 4) {
        setRowHeight(row, "-1", null);//���ɼ�
      } else {
        setRowHeight(row, "480", null);
      }
      for (int colIndex = 0; colIndex < row.getTableCells().size(); colIndex++) {
        XWPFTableCell cell = row.getCell(colIndex);
        setCellVAlign(cell, STVerticalJc.CENTER);
        XWPFParagraph p = getCellFirstParagraph(cell);
        XWPFRun pRun = getOrAddParagraphFirstRun(p, false, false);
        setParagraphRunFontInfo(p, pRun, rowIndex + "_��Ԫ��_" + colIndex,
            "΢���ź�", "22");
        setParagraphTextBasicStyleInfo(p, pRun, null, false, false,
            null, null, true, false, false, null, true,
            STShd.HORZ_STRIPE, "F3C917");
        setParagraphAlignInfo(p, ParagraphAlignment.CENTER, null);
      }
      if (rowIndex == rowLen - 2) {
        setRowHidden(row, true);
      }
    }
  }

  public void testAddHeaderFooter(XWPFDocument doc) throws Exception {
    simpleDateHeader(doc);
    simpleNumberFooter(doc);
  }

  public void testCreateDocumentStyle(XWPFDocument xdoc) {
    // �����ĵ�����
    setDocumentbackground(xdoc, "E1E6F6");

    // �����ĵ��߿�
    CTSectPr sectPr = getDocumentCTSectPr(xdoc);
    CTPageBorders pd = sectPr.isSetPgBorders() ? sectPr.getPgBorders()
        : sectPr.addNewPgBorders();
    CTBorder bd = pd.addNewBottom();
    bd.setVal(STBorder.SINGLE);
    bd.setSz(new BigInteger("4"));
    bd.setSpace(new BigInteger("24"));
    bd.setColor("FBB61F");

    setPgBorders(xdoc, bd, bd, bd, bd);
    setDocumentMargin(xdoc, "1797", "1440", "1797", "1440");
  }

  // ���Դ���ǩ�ı�
  public void testCreateParagraphWithBookMark(XWPFDocument xdoc) {
    XWPFParagraph p = xdoc.createParagraph();
    setParagraphAlignInfo(p, ParagraphAlignment.LEFT, TextAlignment.CENTER);
    setParagraphSpacingInfo(p, true, "0", "0", "0", "0", true, "240",
        STLineSpacingRule.AUTO);
    BigInteger markId = BigInteger.valueOf(1);
    addParagraphContentBookmarkBasicStyle(p, "����", markId, "��ǩһ", false,
        false, "΢���ź�", "24", null, false, false, null, null, false,
        false);
    markId = markId.add(BigInteger.ONE);
    addParagraphContentBookmarkBasicStyle(p, "��ǩ", markId, "��ǩ��", true,
        false, "΢���ź�", "24", null, false, false, null, null, false,
        false);
  }

  // ���Դ������Ӷ���
  public void testCreateParagraphWithHyLink(XWPFDocument xdoc) {
    XWPFParagraph p = xdoc.createParagraph();
    setParagraphAlignInfo(p, ParagraphAlignment.LEFT, TextAlignment.CENTER);
    setParagraphSpacingInfo(p, true, "0", "0", "0", "0", true, "240",
        STLineSpacingRule.AUTO);
    XWPFRun pRun = getOrAddParagraphFirstRun(p, false, false);
    setParagraphRunFontInfo(p, pRun, "����", "����", "22");
    addParagraphTextHyperlinkBasicStyle(p,
        "mailto:123@qq.com?subject=����poi������", "������", "����", "22",
        "0000FF", true, true, false);
    pRun = getOrAddParagraphFirstRun(p, true, false);
    setParagraphRunFontInfo(p, pRun, "�����ı�", "����", "22");
  }

  // ���Լ��ı�����
  public void testCreateSimpleParagraph(XWPFDocument xdoc) {
    XWPFParagraph p = xdoc.createParagraph();
    setParagraphAlignInfo(p, ParagraphAlignment.CENTER,
        TextAlignment.CENTER);
    setParagraphBorders(p, Borders.NONE, Borders.NONE, Borders.NONE,
        Borders.SINGLE, null);
    XWPFRun pRun = getOrAddParagraphFirstRun(p, false, false);
    setParagraphTextSimpleStyleInfo(p, pRun, null, true, true, "FFA932",
        STUnderline.DASH, true, false, true,
        STHighlightColor.DARK_YELLOW, false, null, null, null, 0, 0, 0);
    setParagraphSpacingInfo(p, true, "0", "0", "0", "0", true, "240",
        STLineSpacingRule.AUTO);
    setParagraphRunFontInfo(p, pRun, "���Զ����±߿��»��߸�����ʾ�ı�", "΢���ź�", "22");
    pRun = getOrAddParagraphFirstRun(p, true, false);
    setParagraphRunFontInfo(p, pRun, "��������ı�", "����", "26");
    pRun = getOrAddParagraphFirstRun(p, true, false);
    setParagraphRunFontInfo(p, pRun, "�����ı����Ե���", "����", "22");
    setParagraphTextBasicStyleInfo(p, pRun, null, false, false, null, null,
        true, false, false, null, true, STShd.HORZ_STRIPE, "F3C917");
    pRun = getOrAddParagraphFirstRun(p, true, false);
    setParagraphRunFontInfo(p, pRun, "��", "����", "22");
    setParagraphTextSimpleStyleInfo(p, pRun, null, false, false, null,
        null, true, false, false, null, false, null, null, null, 6, 80,
        0);
    pRun = getOrAddParagraphFirstRun(p, true, false);
    setParagraphRunFontInfo(p, pRun, "�ϱ�", "����", "22");
    setParagraphTextSimpleStyleInfo(p, pRun, null, false, false, null,
        null, true, false, false, null, false, null, null,
        VerticalAlign.SUPERSCRIPT, 0, 0, 0);
    pRun = getOrAddParagraphFirstRun(p, true, false);
    setParagraphRunFontInfo(p, pRun, "�±�", "����", "22");
    setParagraphTextSimpleStyleInfo(p, pRun, null, false, false, null,
        null, true, false, false, null, false, null, null,
        VerticalAlign.SUBSCRIPT, 0, 0, 0);
    pRun = getOrAddParagraphFirstRun(p, true, false);
    setParagraphRunFontInfo(p, pRun, "��������ı��Ѷ������ݼ���ȥ", "����", "22");
    setParagraphTextBasicStyleInfo(p, pRun, null, false, false, null, null,
        true, false, false, null, false, null, null);
    pRun = getOrAddParagraphFirstRun(p, true, true);
    setParagraphRunFontInfo(p, pRun, "���Ի���", "����", "22");
    setParagraphTextBasicStyleInfo(p, pRun, null, false, false, null, null,
        true, false, false, null, false, null, null);
    p = xdoc.createParagraph();
    setParagraphAlignInfo(p, ParagraphAlignment.LEFT, TextAlignment.CENTER);
    pRun = getOrAddParagraphFirstRun(p, false, false);
    setParagraphRunFontInfo(p, pRun, "����һ�β��Զ������", "����", "22");
    setParagraphShdStyle(p, true, STShd.REVERSE_DIAG_STRIPE, "DD999D");
    setParagraphSpacingInfo(p, true, "0", "0", "200", "200", true, "240",
        STLineSpacingRule.AUTO);
  }

  // ���Դ�ͼƬ����
  public void testCreateParagraphWithImg(CustomXWPFDocument_S_9 xdoc)
      throws Exception {
    XWPFParagraph p = xdoc.createParagraph();
    setParagraphAlignInfo(p, ParagraphAlignment.LEFT, TextAlignment.CENTER);
    setParagraphSpacingInfo(p, true, "0", "0", "0", "0", true, "240",
        STLineSpacingRule.AUTO);
    XWPFRun pRun = getOrAddParagraphFirstRun(p, false, false);
    setParagraphRunFontInfo(p, pRun, "1.����ʵ������У���ȷ����(    ) ", "����", "22");
    pRun = getOrAddParagraphFirstRun(p, true, true);
    setParagraphRunFontInfo(p, pRun, "A", "����", "22");
    String blipId = p.getDocument().addPictureData(
        new FileInputStream(new File("f:/saveFile/temp/image1.png")),
        Document.PICTURE_TYPE_PNG);
    xdoc.createPicture(blipId,
        xdoc.getNextPicNameNumber(Document.PICTURE_TYPE_PNG), 90, 93, p);
    pRun = getOrAddParagraphFirstRun(p, true, false);
    setParagraphRunFontInfo(p, pRun, StringUtils.leftPad("B.", 10), "����",
        "22");
    blipId = p.getDocument().addPictureData(
        new FileInputStream(new File("f:/saveFile/temp/image2.png")),
        Document.PICTURE_TYPE_PNG);
    xdoc.createPicture(blipId,
        xdoc.getNextPicNameNumber(Document.PICTURE_TYPE_PNG), 90, 93, p);
    pRun = getOrAddParagraphFirstRun(p, true, true);
    setParagraphRunFontInfo(p, pRun, "C", "����", "22");
    blipId = p.getDocument().addPictureData(
        new FileInputStream(new File("f:/saveFile/temp/image3.png")),
        Document.PICTURE_TYPE_PNG);
    xdoc.createPicture(blipId,
        xdoc.getNextPicNameNumber(Document.PICTURE_TYPE_PNG), 90, 93, p);
    pRun = getOrAddParagraphFirstRun(p, true, false);
    setParagraphRunFontInfo(p, pRun, StringUtils.leftPad("D.", 10), "����",
        "22");
    blipId = p.getDocument().addPictureData(
        new FileInputStream(new File("f:/saveFile/temp/image4.png")),
        Document.PICTURE_TYPE_PNG);
    xdoc.createPicture(blipId,
        xdoc.getNextPicNameNumber(Document.PICTURE_TYPE_PNG), 90, 93, p);
  }
}*/

