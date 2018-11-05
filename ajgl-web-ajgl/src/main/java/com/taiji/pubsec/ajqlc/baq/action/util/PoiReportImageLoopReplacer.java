package com.taiji.pubsec.ajqlc.baq.action.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.PositionInParagraph;
import org.apache.poi.xwpf.usermodel.TextSegement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.impl.values.XmlValueDisconnectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.taiji.pubsec.common.tools.doc.core.model.BookMarkConfig;
import com.taiji.pubsec.common.tools.doc.core.report.core.Report;
import com.taiji.pubsec.common.tools.doc.msoffice.impl.poi.pojo.ImagePojo;
import com.taiji.pubsec.common.tools.doc.msoffice.impl.poi.pojo.ImageType;
import com.taiji.pubsec.common.tools.doc.msoffice.impl.poi.replacer.PoiReportLoopTextReplacer;
import com.taiji.pubsec.common.tools.doc.msoffice.impl.poi.report.PoiReport;

import net.sf.json.JSONObject;

@Service
public class PoiReportImageLoopReplacer extends PoiReportLoopTextReplacer{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PoiReportImageLoopReplacer.class);
	
	public static final String MARK = "poiReportImageLoopReplacer" ;
	
	public static final double DEFAULT_WIDTH = ImageType.DEFAULT_WIDTH ;
	public static final double DEFAULT_HEIGHT = ImageType.DEFAULT_HEIGHT ;
	
	public static final Integer DEFAULT_TYPE = ImageType.PICTURE_TYPE_PNG ;

	@Override
	public void replace(Object loopTemplate, BookMarkConfig bookMarkConfig, Object value, Report reporter) {
		PoiReport rp = (PoiReport)reporter ;
		XWPFDocument doc = rp.getReportContent();
		String bmName = bookMarkConfig.getName();
		
		XWPFTableRow row = (XWPFTableRow)loopTemplate;
		
		String bookmark = new StringBuffer("${").append(bmName).append("}").toString();
		
		ImagePojo imagePojo = (ImagePojo)JSONObject.toBean(JSONObject.fromObject(value), ImagePojo.class);
		
		replaceRows(bookmark, imagePojo, row, reporter);
	}
	
	protected void replaceRows(String bookmark, ImagePojo imagePojo, XWPFTableRow row, Report reporter){
		for(XWPFTableCell cell:row.getTableCells()){
			try{
				replaceParagraphs(bookmark, imagePojo, cell.getParagraphs());
			}catch(XmlValueDisconnectedException e){
				e.printStackTrace();
			}
			
		}
	}	
	
	protected void replaceParagraphs(String bookmark, ImagePojo imagePojo, List<XWPFParagraph> paragraphs){
		PositionInParagraph pos = new PositionInParagraph();
		pos.setRun(0);
		pos.setText(0);
		pos.setChar(0);
		
		for (int i = 0; i < paragraphs.size(); i++) {
			XWPFParagraph para = paragraphs.get(i);
			TextSegement ts = para.searchText(bookmark, pos);
			if (ts != null) {
				XWPFRun run = para.getRuns().get(ts.getBeginRun());
				run.setText("", 0);  
				
				InputStream is = null;
				try {
					is = new ByteArrayInputStream(imagePojo.getImage()) ;
					run.addPicture(is, XWPFDocument.PICTURE_TYPE_PNG, imagePojo.getName(), (imagePojo.getWidth()==null)?Units.toEMU(DEFAULT_WIDTH):Units.toEMU(imagePojo.getWidth()), (imagePojo.getHeight()==null)?Units.toEMU(DEFAULT_HEIGHT):Units.toEMU(imagePojo.getHeight()));	
				} catch (Exception e) {
					LOGGER.error("插入图片错误", e);
				}
			}
		}
	}
	
	protected void replaceTables(String bookmark, ImagePojo imagePojo, XWPFDocument doc){
		
		List<XWPFTable> tables = doc.getTables();
		
		for(XWPFTable table:tables){
			for(XWPFTableRow row:table.getRows()){
				for(XWPFTableCell cell:row.getTableCells()){
					replaceParagraphs(bookmark, imagePojo, cell.getParagraphs()) ;
				}
			}
		}
		
	}

	@Override
	public boolean support(String mark) {
		if(MARK.equals(mark)){
			return true ;
		}
		
		if(PoiReportImageLoopReplacer.class.getName().equals(mark)){
			return true ;
		}
		
		return false;
	}

}
