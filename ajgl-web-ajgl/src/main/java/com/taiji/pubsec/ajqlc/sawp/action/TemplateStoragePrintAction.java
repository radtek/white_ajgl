package com.taiji.pubsec.ajqlc.sawp.action;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.pubsec.ajgl.util.ReturnMessageAction;
import com.taiji.pubsec.ajqlc.attachment.model.Attachment;
import com.taiji.pubsec.ajqlc.attachment.service.IAttachmentCustomizedService;
import com.taiji.pubsec.ajqlc.sawp.bean.TempObjectBean;
import com.taiji.pubsec.ajqlc.sawp.bean.TempStorageInInfoBean;
import com.taiji.pubsec.ajqlc.sawp.model.TemporaryStorageOut;
import com.taiji.pubsec.ajqlc.sawp.service.ITemporaryStorageInService;
import com.taiji.pubsec.ajqlc.sawp.service.ITemporaryStorageOutService;
import com.taiji.pubsec.ajqlc.sla.model.CriminalBasicCase;
import com.taiji.pubsec.ajqlc.sla.service.ICriminalCaseService;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryItemService;
import com.taiji.pubsec.businesscomponent.organization.model.Person;
import com.taiji.pubsec.businesscomponent.organization.service.IPersonService;
import com.taiji.pubsec.common.tools.doc.core.model.ReportConfig;
import com.taiji.pubsec.common.tools.doc.msoffice.impl.poicr.builder.PoiCrReportBuilder;
import com.taiji.pubsec.common.tools.doc.msoffice.impl.poicr.reporter.PoiCrReport;
import com.taiji.pubsec.common.web.action.ExportInfoReq;

/**
 * 暂存保管区打印Action
 * 
 * @author huangda
 *
 */
@Controller("templateStoragePrintAction")
@Scope("prototype")
public class TemplateStoragePrintAction extends ReturnMessageAction {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(TemplateStoragePrintAction.class);
	@Resource
	private ITemporaryStorageInService temporaryStorageInService;
	@Resource
	private ITemporaryStorageOutService temporaryStorageOutService;
	@Resource
	private IDictionaryItemService dictionaryItemService;
	@Resource
	private IPersonService personService;
	@Resource
	private PoiCrReportBuilder poiCrReportBuilder;
	@Resource
	private IAttachmentCustomizedService attachmentCustomizedService;
	@Resource
	private ICriminalCaseService criminalCaseService;
	
	private ExportInfoReq exportInfoReq = new ExportInfoReq();
	
	private String code;//单据编号
	
	/**
	 * 入库单打印
	 * @return
	 */
	public String printTempStorageIn(){
		TempStorageInInfoBean tsi = null;
		if(null != code){
			tsi = temporaryStorageInService.findTemporaryStorageInDeatailByCode(code);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("n1", this.basePrintInfoForTemporaryStorageIn(tsi));
		if(tsi != null){
			map.put("t1", this.storageInfoForTemporaryStorageIn(tsi.getTempObjectBeanList()));
		}else{
			map.put("t1", this.storageInfoForTemporaryStorageIn(new ArrayList<TempObjectBean>()));
		}
		ReportConfig rc = poiCrReportBuilder.createFromXmlFile(map, this.getRequest().getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "printAbout" + File.separator + "printXml" + File.separator + "TemplateStorageIn.xml");
		PoiCrReport report = new PoiCrReport(rc);
		poiCrReportBuilder.build(report) ;
		InputStream is = report.generateReportInputStream() ;
		try {
			exportInfoReq.setLength(Long.valueOf(String.valueOf(is.available())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		exportInfoReq.setIn(is);
		if(tsi != null){
			exportInfoReq.setName("暂存物品入库单"+tsi.getStorageInCode()+".docx");
		}else{
			exportInfoReq.setName("暂存物品入库单.docx");
		}
		this.creatCaseManagementLog("涉案物品管理/暂存物品管理/暂存物品入库/入库单详情", "打印入库单(入库单编码:"+code+")");
		return "done";
	}
	
	/**
	 * 出库单打印
	 * @return
	 */
	public String printTempStorageOut(){
		TempStorageInInfoBean tsi = null;
		TemporaryStorageOut tso = null;
		if(null != code){
			tso = temporaryStorageOutService.findByCode(code);
			if(null != tso){
				tsi = temporaryStorageInService.findTemporaryStorageInDeatailByCode(tso.getStorageIn().getCode());
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("n1", this.basePrintInfoForTemporaryStorageOut(tso, tsi));
		if(tsi != null){
			map.put("t1", this.storageInfoForTemporaryStorageIn(tsi.getTempObjectBeanList()));
		}else{
			map.put("t1", this.storageInfoForTemporaryStorageIn(new ArrayList<TempObjectBean>()));
		}
		ReportConfig rc = poiCrReportBuilder.createFromXmlFile(map, this.getRequest().getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "printAbout" + File.separator + "printXml" + File.separator + "TemplateStorageOut.xml");
		PoiCrReport report = new PoiCrReport(rc);
		poiCrReportBuilder.build(report) ;
		InputStream is = report.generateReportInputStream() ;
		try {
			exportInfoReq.setLength(Long.valueOf(String.valueOf(is.available())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		exportInfoReq.setIn(is);
		exportInfoReq.setName("暂存物品入库单"+tsi.getStorageInCode()+".docx");
		this.creatCaseManagementLog("涉案物品管理/暂存物品管理/暂存物品出库/出库单详情", "打印入库单(出库单编码:"+code+")");
		return "done";
	}
	
	/**
	 * 入库单基本情况转换
	 * @param tsi
	 * @return
	 */
	private Map<String,Object> basePrintInfoForTemporaryStorageIn(TempStorageInInfoBean tsi) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(tsi !=null){
			map.put("n1", dateToStr(new Date(), null));
			map.put("n2", StringUtils.isBlank(tsi.getHarCode())?"":tsi.getHarCode());
			map.put("n3", StringUtils.isBlank(tsi.getSolvePolice())?"":tsi.getSolvePolice());
			map.put("n4", StringUtils.isBlank(tsi.getStorageInCode())?"":tsi.getStorageInCode());
			map.put("n5", StringUtils.isBlank(tsi.getStorageInDateTime())?"":tsi.getStorageInDateTime());
			map.put("n6", StringUtils.isBlank(tsi.getCaseCode())?"":tsi.getCaseCode());
			if(!StringUtils.isBlank(tsi.getCaseCode())){
				CriminalBasicCase cbc = criminalCaseService.findCriminalCaseByCaseId(tsi.getCaseCode());
				if(cbc != null){
					map.put("n7", StringUtils.isBlank(cbc.getCaseName())?"":cbc.getCaseName());
				}else{
					map.put("n7", "");
				}
			}else{
				map.put("n7", "");
			}
			map.put("n8", StringUtils.isBlank(tsi.getObjectOwnerPerson())?"":tsi.getObjectOwnerPerson());
			map.put("n9", StringUtils.isBlank(tsi.getIdCard())?"":tsi.getIdCard());
			map.put("n10", StringUtils.isBlank(tsi.getRemark())?"":tsi.getRemark());
			Person p = personService.findById(tsi.getCreatePerson());
			map.put("n11", StringUtils.isBlank(p.getName())?"":p.getName());
		}else{
			map.put("n1", "");
			map.put("n2", "");
			map.put("n3", "");
			map.put("n4", "");
			map.put("n5", "");
			map.put("n6", "");
			map.put("n7", "");
			map.put("n8", "");
			map.put("n9", "");
			map.put("n10", "");
			map.put("n11", "");
		}
		return map;
	}

	/**
	 * 出库单基本情况转换
	 * @param tsi
	 * @return
	 */
	private Map<String,Object> basePrintInfoForTemporaryStorageOut(TemporaryStorageOut tso, TempStorageInInfoBean tsi) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(tsi !=null){
			map.put("n1", dateToStr(new Date(), null));
			map.put("n2", StringUtils.isBlank(tsi.getHarCode())?"":tsi.getHarCode());
			map.put("n3", StringUtils.isBlank(tsi.getSolvePolice())?"":tsi.getSolvePolice());
			map.put("n4", StringUtils.isBlank(tso.getCode())?"":tso.getCode());
			map.put("n5", StringUtils.isBlank(dateToStr(tso.getCreateDate(), null))?"":dateToStr(tso.getCreateDate(), null));
			map.put("n6", StringUtils.isBlank(tsi.getCaseCode())?"":tsi.getCaseCode());
			if(!StringUtils.isBlank(tsi.getCaseCode())){
				CriminalBasicCase cbc = criminalCaseService.findCriminalCaseByCaseId(tsi.getCaseCode());
				if(cbc != null){
					map.put("n7", StringUtils.isBlank(cbc.getCaseName())?"":cbc.getCaseName());
				}else{
					map.put("n7", "");
				}
			}else{
				map.put("n7", "");
			}
			map.put("n8", StringUtils.isBlank(tsi.getObjectOwnerPerson())?"":tsi.getObjectOwnerPerson());
			map.put("n9", StringUtils.isBlank(tsi.getIdCard())?"":tsi.getIdCard());
			String fileName = "";
			List<Attachment> attLst = attachmentCustomizedService.findByTargetIdAndType(tso.getId(), TemporaryStorageOut.class.getName());
			if(attLst != null){
				for(Attachment att : attLst){
					fileName = att.getName();
				}
			}
			map.put("n10", StringUtils.isBlank(fileName)?"":fileName);
			map.put("n11", StringUtils.isBlank(tsi.getRemark())?"":tsi.getRemark());
			Person p = personService.findById(tsi.getCreatePerson());
			map.put("n12", StringUtils.isBlank(p.getName())?"":p.getName());
		}else{
			map.put("n1", "");
			map.put("n2", "");
			map.put("n3", "");
			map.put("n4", "");
			map.put("n5", "");
			map.put("n6", "");
			map.put("n7", "");
			map.put("n8", "");
			map.put("n9", "");
			map.put("n10", "");
			map.put("n11", "");
			map.put("n12", "");
		}
		return map;
	}
	
	/**
	 * 物品转换
	 * @param tsi
	 * @return
	 */
	private List<Map<String, Object>> storageInfoForTemporaryStorageIn(List<TempObjectBean> tobLst){
		List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();
		if(tobLst == null || tobLst.size() <= 0){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("c1", "");
			map.put("c2", "");
			map.put("c3", "");
			map.put("c4", "");
			map.put("c5", "");
			lst.add(map);
			return lst;
		}else{
			int i = 1;
			for(TempObjectBean temp : tobLst){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("c1", i);
				map.put("c2", StringUtils.isBlank(temp.getObjectName())?"":temp.getObjectName());
				map.put("c3", StringUtils.isBlank(temp.getObjectCharacter())?"":temp.getObjectCharacter());
				map.put("c4", StringUtils.isBlank(temp.getInThisNum())?"":temp.getInThisNum());
				map.put("c5", StringUtils.isBlank(temp.getMeasureUnit())?"":temp.getMeasureUnit());
				lst.add(map);
				i++;
			}
		}
		return lst;
	}
	
	/**
	 * Date时间转化成打印字符串
	 * @param Date 毫秒数
	 * @param fmt 输出格式，默认为精确到分钟
	 * @return 
	 */
	private String dateToStr(Date date, String fmt){
		String str = "";
		if(date == null){
			return str;
		}
		if(StringUtils.isBlank(fmt)){
			fmt = "yyyy-MM-dd HH:mm";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		try {
			str = sdf.format(date);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return str;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ExportInfoReq getExportInfoReq() {
		return exportInfoReq;
	}

	public void setExportInfoReq(ExportInfoReq exportInfoReq) {
		this.exportInfoReq = exportInfoReq;
	}

}
