package com.taiji.pubsec.ajqlc.baq.action;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.taiji.pubsec.ajgl.util.bean.FileObjectBean;
import com.taiji.pubsec.ajqlc.attachment.model.Attachment;
import com.taiji.pubsec.ajqlc.attachment.service.IAttachmentCustomizedService;
import com.taiji.pubsec.ajqlc.baq.action.bean.ActivityRecordBean;
import com.taiji.pubsec.ajqlc.baq.action.bean.BasicCaseBean;
import com.taiji.pubsec.ajqlc.baq.action.bean.CarryGoodsRecordBean;
import com.taiji.pubsec.ajqlc.baq.action.bean.CollectInfoSituationBean;
import com.taiji.pubsec.ajqlc.baq.action.bean.FinallyLeaveInfoBean;
import com.taiji.pubsec.ajqlc.baq.action.bean.PersonCheckRecordBean;
import com.taiji.pubsec.ajqlc.baq.action.bean.ReturnDataBean;
import com.taiji.pubsec.ajqlc.baq.action.bean.ReturnPrintBean;
import com.taiji.pubsec.ajqlc.baq.action.bean.TmpLeaveInfoBean;
import com.taiji.pubsec.ajqlc.baq.model.ActivityRoom;
import com.taiji.pubsec.ajqlc.baq.model.BasicCase;
import com.taiji.pubsec.ajqlc.baq.model.CarryGoodsInfo;
import com.taiji.pubsec.ajqlc.baq.model.CarryGoodsManageSnapshot;
import com.taiji.pubsec.ajqlc.baq.model.CarryGoodsRecord;
import com.taiji.pubsec.ajqlc.baq.model.CarryGoodsReturnRecord;
import com.taiji.pubsec.ajqlc.baq.model.CollectInfoSituation;
import com.taiji.pubsec.ajqlc.baq.model.FinallyLeaveInfo;
import com.taiji.pubsec.ajqlc.baq.model.GoodsSnapshot;
import com.taiji.pubsec.ajqlc.baq.model.HandleAreaActivityRecordInfo;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaActivityRecord;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceipt;
import com.taiji.pubsec.ajqlc.baq.model.PersonCheckRecord;
import com.taiji.pubsec.ajqlc.baq.model.TmpLeaveInfo;
import com.taiji.pubsec.ajqlc.baq.service.IAskRoomService;
import com.taiji.pubsec.ajqlc.baq.service.ICarryGoodsInfoService;
import com.taiji.pubsec.ajqlc.baq.service.ICarryGoodsManageSnapshotService;
import com.taiji.pubsec.ajqlc.baq.service.ICarryGoodsReturnRecordService;
import com.taiji.pubsec.ajqlc.baq.service.IHandlingAreaReceiptService;
import com.taiji.pubsec.ajqlc.baq.service.ILockerService;
import com.taiji.pubsec.ajqlc.util.Constant;
import com.taiji.pubsec.businesscomponent.dictionary.model.DictionaryItem;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryItemService;
import com.taiji.pubsec.businesscomponent.organization.model.Organization;
import com.taiji.pubsec.businesscomponent.organization.service.IOrganizationService;
import com.taiji.pubsec.common.tool.spring.BeanCopierFmtUtil;
import com.taiji.pubsec.common.tools.doc.core.model.ReportConfig;
import com.taiji.pubsec.common.tools.doc.msoffice.impl.poicr.builder.PoiCrReportBuilder;
import com.taiji.pubsec.common.tools.doc.msoffice.impl.poicr.reporter.PoiCrReport;
import com.taiji.pubsec.common.web.action.ExportInfoReq;

/**
 * 办案区使用单Action
 * 
 * @author huangda
 *
 */
@Controller("hARPrintAction")
@Scope("prototype")
public class HARPrintAction extends ReturnMessageAction {

	private static final long serialVersionUID = 1L;
	
	private static final String SHI = "是";
	private static final String FOU = "否";
	private static final Logger LOGGER = LoggerFactory.getLogger(HARPrintAction.class);
	@Resource
	private IHandlingAreaReceiptService handlingAreaReceiptService;
	@Resource
	private IDictionaryItemService dictionaryItemService;
	@Resource
	private PoiCrReportBuilder poiCrReportBuilder;
	@Resource
	private IAttachmentCustomizedService attachmentCustomizedService;
	@Resource
	private IAskRoomService askRoomService;
	@Resource
	private ICarryGoodsInfoService carryGoodsInfoService;
	@Resource
	private ILockerService lockerService;// 储物柜接口
	@Resource
	private ICarryGoodsReturnRecordService carryGoodsReturnRecordService;		//随身物品返还记录接口
	@Resource
	private IOrganizationService organizationService;
	
	@Resource
	private ICarryGoodsManageSnapshotService carryGoodsManageSnapshotService; //快照
	
	private ExportInfoReq exportInfoReq = new ExportInfoReq();
	private String caseFormCode;
	
	private String id;                      //分配单ID
	private PersonCheckRecordBean personCheckRecordBean = new PersonCheckRecordBean();    //人身检查记录Bean
	private CollectInfoSituationBean collectInfoSituationBean = new CollectInfoSituationBean();   //信息采集情况
	private FinallyLeaveInfoBean fliBean = new FinallyLeaveInfoBean(); //临时离开记录Bean
	private List<TmpLeaveInfoBean> tliBeanLst = new ArrayList<TmpLeaveInfoBean>(); //最终离开记录Bean
	private List<ActivityRecordBean> activityRecordBeanLst = new ArrayList<ActivityRecordBean>();//活动记录Bean
	private List<CarryGoodsRecordBean> carryGoodsRecordBeanList = new ArrayList<CarryGoodsRecordBean>();//随身携带物品检查Bean
	private ReturnPrintBean returnPrintBean = new ReturnPrintBean();//随身物品返还Bean
	private boolean allReturnFlag = true;//随身物品全部返还标记
	private List<CarryGoodsRecordBean> temporaryLst = new  ArrayList<CarryGoodsRecordBean>();
	private ReturnDataBean returnMap = new ReturnDataBean();
	
	private String handlingAreaReceiptId;
	
	/**
	 * 使用单打印
	 * @return
	 */
	public String printHandlingAreaReceipt(){
		HandlingAreaReceipt har = null;
		if(null == id){
			har = handlingAreaReceiptService.findHandlingAreaReceiptByNum(caseFormCode);
			id = har.getId();
		}else{
			 har = handlingAreaReceiptService.findHandlingAreaReceiptById(id);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("a1", this.handlingAreaReceiptFixedForHar(har));//为打印单组装填表单位和单据标号
		map.put("b2", this.basicCaseInfoFixed(har));//为打印单组装基本情况		
		map.put("n3", this.personCheckRecordFixedForHar(har));//人身检查记录
		map.put("t4", this.collectInfoSituationPrintPhoneInfoForHar(har));//手机信息
		map.put("n5", this.collectInfoSituationPrintInfoForHar(har));//信息采集
		map.put("t6", this.activityRecordFixedForHar(har));//活动记录
		map.put("t7", this.tmpLeaveInfoFixedForHar(har));//临时离开记录
		map.put("n8", this.finallyLeaveInfoFixedForHar(har));//最终离开记录
		map.put("t9", this.carryGoodsInfoRecordFixed());//
	
		ReportConfig rc = poiCrReportBuilder.createFromXmlFile(map, this.getRequest().getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "printAbout" + File.separator + "printXml" + File.separator + "handlingAreaReceipt.xml");
		PoiCrReport report = new PoiCrReport(rc);
		poiCrReportBuilder.build(report) ;
		InputStream is = report.generateReportInputStream() ;
		try {
			exportInfoReq.setLength(Long.valueOf(String.valueOf(is.available())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		exportInfoReq.setIn(is);
		exportInfoReq.setName("办案区使用单登记表"+har.getBasicCase().getHandlingAreaReceiptNum()+".docx");
		this.creatCaseManagementLog("办案区管理 /办案区使用/ 办案区使用单/办案区使用单详情", "打印办案区使用单登记表(使用单ID:"+id+")");
		return "done";
	}
	
	/**
	 * 人身检查记录打印
	 * @return
	 */
	public String printPersonCheckRecord(){
		Map<String, Object> map = new HashMap<String, Object>();
		HandlingAreaReceipt har = handlingAreaReceiptService.findHandlingAreaReceiptById(id);
		map.put("a1", this.handlingAreaReceiptFixedForHar(har));//为打印单组装填表单位和单据标号
		map.put("b1", this.basicCaseInfoFixed(har));//为打印单组装基本情况
		map.put("n1", personCheckRecordPrintInfo());
		
		ReportConfig rc = poiCrReportBuilder.createFromXmlFile(map, this.getRequest().getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "printAbout" + File.separator + "printXml" + File.separator + "personCheckRecord.xml");
		PoiCrReport report = new PoiCrReport(rc);
		poiCrReportBuilder.build(report) ;
		InputStream is = report.generateReportInputStream() ;
		try {
			exportInfoReq.setLength(Long.valueOf(String.valueOf(is.available())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		exportInfoReq.setIn(is);
		exportInfoReq.setName("人身检查记录"+har.getBasicCase().getHandlingAreaReceiptNum()+".docx");
		this.creatCaseManagementLog("办案区管理 /办案区使用/ 办案区使用单/办案区使用单详情", "打印人身检查记录(使用单ID:"+id+")");
		return "done" ;
	}
	
	/**
	 * 随身物品检查记录打印
	 * @return
	 */
	public String printCarryGoodsCheckRecord(){
		Map<String, Object> map = new HashMap<String, Object>();
		HandlingAreaReceipt har = handlingAreaReceiptService.findHandlingAreaReceiptById(id);
		map.put("a1", this.handlingAreaReceiptFixedForHar(har));//为打印单组装填表单位和单据标号
		map.put("b1", this.basicCaseInfoFixed(har));//为打印单组装基本情况		
		map.put("t1", carryGoodsCheckRecordPrintInfo());
		
		ReportConfig rc = poiCrReportBuilder.createFromXmlFile(map, this.getRequest().getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "printAbout" + File.separator + "printXml" + File.separator + "carryGoodsCheckRecord.xml");
		PoiCrReport report = new PoiCrReport(rc);
		poiCrReportBuilder.build(report) ;
		InputStream is = report.generateReportInputStream() ;
		try {
			exportInfoReq.setLength(Long.valueOf(String.valueOf(is.available())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		exportInfoReq.setIn(is);
		exportInfoReq.setName("随身物品检查记录"+har.getBasicCase().getHandlingAreaReceiptNum()+".docx");
		this.creatCaseManagementLog("办案区管理 /办案区使用/ 办案区使用单/办案区使用单详情", "打印随身物品检查记录(使用单ID:"+id+")");
		return "done" ;
	}
	
	/**
	 * 信息采集情况
	 * @return
	 */
	public String printCollectInfoSituation(){
		HandlingAreaReceipt har = handlingAreaReceiptService.findHandlingAreaReceiptById(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("a1", this.handlingAreaReceiptFixedForHar(har));//为打印单组装填表单位和单据标号
		map.put("b1", this.basicCaseInfoFixed(har));//为打印单组装基本情况		
		map.put("n1", collectInfoSituationPrintInfo());
		map.put("t1", this.collectInfoSituationPrintPhoneInfoForHar(har));//手机信息

		
		ReportConfig rc = poiCrReportBuilder.createFromXmlFile(map, this.getRequest().getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "printAbout" + File.separator + "printXml" + File.separator + "collectInfoSituation.xml");
		PoiCrReport report = new PoiCrReport(rc);
		poiCrReportBuilder.build(report) ;
		InputStream is = report.generateReportInputStream() ;
		try {
			exportInfoReq.setLength(Long.valueOf(String.valueOf(is.available())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		exportInfoReq.setIn(is);
		exportInfoReq.setName("信息采集情况"+har.getBasicCase().getHandlingAreaReceiptNum()+".docx");
		this.creatCaseManagementLog("办案区管理 /办案区使用/ 办案区使用单/办案区使用单详情", "打印信息采集情况(使用单ID:"+id+")");
		return "done" ;
	}

	/**
	 * 临时物品取出记录打印
	 * @return
	 */
	public String printTemporaryLstation(){
		Map<String, Object> map = new HashMap<String, Object>();
		HandlingAreaReceipt har = handlingAreaReceiptService.findHandlingAreaReceiptById(handlingAreaReceiptId);
		map.put("a1", this.handlingAreaReceiptFixedForHar(har));//为打印单组装填表单位和单据标号
		map.put("b1", this.basicCaseInfoFixed(har));//为打印单组装基本情况
//		map.put("t1", this.temporaryLstInfoFixed()); //WPZT_LSQC
		map.put("t1", this.carryGoodsCommonMethod(Constant.WPZT_LSQC)); //临时取出
		ReportConfig rc = poiCrReportBuilder.createFromXmlFile(map, this.getRequest().getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "printAbout" + File.separator + "printXml" + File.separator + "temporaryGoodsTakeout.xml");
		PoiCrReport report = new PoiCrReport(rc);
		poiCrReportBuilder.build(report) ;
		InputStream is = report.generateReportInputStream() ;
		try {
			exportInfoReq.setLength(Long.valueOf(String.valueOf(is.available())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		exportInfoReq.setIn(is);
		exportInfoReq.setName("临时物品取出记录"+har.getBasicCase().getHandlingAreaReceiptNum()+".docx");
		this.creatCaseManagementLog("办案区管理 /办案区使用/ 办案区使用单/办案区使用单详情", "打印临时物品取出记录(使用单ID:"+handlingAreaReceiptId+")");
		return "done" ;
	}
	
	/**
	 * 离开记录打印
	 * @return
	 */
	public String printLeaveSituation(){
		Map<String, Object> map = new HashMap<String, Object>();
		HandlingAreaReceipt har = handlingAreaReceiptService.findHandlingAreaReceiptById(handlingAreaReceiptId);
		map.put("a1", this.handlingAreaReceiptFixedForHar(har));//为打印单组装填表单位和单据标号
		map.put("b1", this.basicCaseInfoFixed(har));//为打印单组装基本情况		
		map.put("t1", this.tmpLeaveInfoFixed());
		map.put("n1", this.finallyLeaveInfoFixed());
		
		ReportConfig rc = poiCrReportBuilder.createFromXmlFile(map, this.getRequest().getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "printAbout" + File.separator + "printXml" + File.separator + "leaveSituation.xml");
		PoiCrReport report = new PoiCrReport(rc);
		poiCrReportBuilder.build(report) ;
		InputStream is = report.generateReportInputStream() ;
		try {
			exportInfoReq.setLength(Long.valueOf(String.valueOf(is.available())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		exportInfoReq.setIn(is);
		exportInfoReq.setName("离开记录"+har.getBasicCase().getHandlingAreaReceiptNum()+".docx");
		this.creatCaseManagementLog("办案区管理 /办案区使用/ 办案区使用单/办案区使用单详情", "打印离开记录(使用单ID:"+handlingAreaReceiptId+")");
		return "done" ;
	}
	
	/**
	 * 物品返还记录打印
	 * @return
	 */
	public String printCarryGoodsReturnRecord(){
		Map<String, Object> map = new HashMap<String, Object>();	
		HandlingAreaReceipt har = handlingAreaReceiptService.findHandlingAreaReceiptById(handlingAreaReceiptId);
		map.put("a1", this.handlingAreaReceiptFixedForHar(har));//为打印单组装填表单位和单据标号
		map.put("b1", this.basicCaseInfoFixed(har));//为打印单组装基本情况		
//		map.put("t1", this.carryGoodsDetainRecordFixed());
//		map.put("z1", this.carryGoodsKeepFixed());
//		map.put("n1", this.carryGoodsReturnFixed());
		map.put("t1", this.carryGoodsCommonMethod(Constant.WPZT_YJKY));//移交扣押
		map.put("z1", this.carryGoodsCommonMethod(Constant.WPZT_YJZC));//移交暂存
		map.put("n1", this.carryGoodsCommonMethod(Constant.WPZT_YFH));//立即返还
		ReportConfig rc = poiCrReportBuilder.createFromXmlFile(map, this.getRequest().getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "printAbout" + File.separator + "printXml" + File.separator + "carryGoodsReturnRecord.xml");
		PoiCrReport report = new PoiCrReport(rc);
		poiCrReportBuilder.build(report) ;
		InputStream is = report.generateReportInputStream() ;
		try {
			exportInfoReq.setLength(Long.valueOf(String.valueOf(is.available())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		exportInfoReq.setIn(is);
		exportInfoReq.setName("物品移交取出记录"+har.getBasicCase().getHandlingAreaReceiptNum()+".docx");
		this.creatCaseManagementLog("办案区管理 /办案区使用/ 办案区使用单/办案区使用单详情", "打印物品移交取出记录(使用单ID:"+handlingAreaReceiptId+")");
		return "done" ;
	}
	/**
	 * 拼接的公共方法
	 * @param 快照状态
	 * @return
	 */
	private List<Map<String, Object>> carryGoodsCommonMethod(String stu) {
		List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();
		CarryGoodsManageSnapshot cst=carryGoodsManageSnapshotService.findCarryGoodsManageSnapshotByReceiptId(handlingAreaReceiptId,stu);
		if(cst==null||cst.getGoodsSnapshots().size() == 0){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("c1", "--");
			map.put("c2", "--");
			map.put("c3", "--");
			map.put("c4", "--");
			map.put("c5", "--");
			map.put("c6", "--");
			lst.add(map);
		}else if(cst.getGoodsSnapshots().size() > 0){
			for(GoodsSnapshot goods : cst.getGoodsSnapshots()){
				CarryGoodsRecord carryGoodsRecord = carryGoodsInfoService.findCarryGoodsRecordById(goods.getCarryGoodsRecordId());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("c1", carryGoodsRecord.getGoodsName() == null ? "" : carryGoodsRecord.getGoodsName());
				map.put("c2", carryGoodsRecord.getFeatures() == null ? "" : carryGoodsRecord.getFeatures());
				map.put("c3", carryGoodsRecord.getQuantity() == null ? "" : carryGoodsRecord.getQuantity());
				map.put("c4", carryGoodsRecord.getUnitOfMeasurement() == null ? "" : carryGoodsRecord.getUnitOfMeasurement());
				map.put("c5", carryGoodsRecord.getNum() == null ? "" : carryGoodsRecord.getNum());
				map.put("c6", carryGoodsRecord.getRemark() == null ? "" : carryGoodsRecord.getRemark());
				lst.add(map);
			}
		}
		return lst;
	}

	private Map<String,Object> collectInfoSituationPrintInfoForHar(HandlingAreaReceipt har) {
		Map<String, Object> map = new HashMap<String, Object>();
		CollectInfoSituation cis = har.getCollectInfoSituation();
		if(cis !=null){
			if (Constant.SF_S.equals(cis.getIsCollect())) {
				map.put("n1", SHI);
			} else if (Constant.SF_F.equals(cis.getIsCollect())) {
				map.put("n1", FOU);
			}
			String temp = cis.getCollectProject();
			if(!StringUtils.isBlank(cis.getCollectProjectOther())){
				if(StringUtils.isBlank(temp)){
					temp += "其他:" + cis.getCollectProjectOther();
				}else{
					temp += ",其他:" + cis.getCollectProjectOther();
				}
			}
			map.put("n2", temp);
			if (Constant.SF_S.equals(cis.getInfoIntoString())) {
				map.put("n3", SHI);
			} else if (Constant.SF_F.equals(cis.getInfoIntoString())) {
				map.put("n3", FOU);
			}
			if (Constant.SF_S.equals(cis.getInspectComparison())) {
				map.put("n4", SHI);
			} else if (Constant.SF_F.equals(cis.getInspectComparison())) {
				map.put("n4", FOU);
			}
			map.put("n5", cis.getQqNum() == null ? "" : cis.getQqNum());
			map.put("n6", cis.getWeixinNum() == null ? "" : cis.getWeixinNum());

		}else{
			map.put("n1", "");
			map.put("n2", "");
			map.put("n3", "");
			map.put("n4", "");
			map.put("n5", "");
			map.put("n6", "");
		}
		return map;
	}
	
	private List<Map<String, Object>> collectInfoSituationPrintPhoneInfoForHar(HandlingAreaReceipt har){ 
	List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();
	CollectInfoSituation cis = har.getCollectInfoSituation();
	if(null != cis && null != cis.getPhoneInfo()&& cis.getPhoneInfo().length() >0){
		String[] hponeInfos = cis.getPhoneInfo().split(";");
		int len = hponeInfos.length;
		for(int i = 0 ; i < len ;i++){
			String[] hponeInfo = hponeInfos[i].split(","); 
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("c1",hponeInfo[0]);
			map.put("c2",hponeInfo[1]);
			map.put("c3",hponeInfo[2]);
			lst.add(map);
		}
		
	}else{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("c1","");
		map.put("c2","");
		map.put("c3","");
		lst.add(map);
		return lst;
	}
	return lst;

	}
	
	
	private Map<String,Object> personCheckRecordFixedForHar(HandlingAreaReceipt har) {
		Map<String, Object> map = new HashMap<String, Object>();
		PersonCheckRecord pcr = har.getPersonCheckRecord();
		if(pcr !=null){
			map.put("n1", pcr.getSelfReportedSymptoms() == null ? "" : pcr.getSelfReportedSymptoms());
			map.put("n2", pcr.getCheckCondition() == null ? "" : pcr.getCheckCondition());
		}else{
			map.put("n1", "");
			map.put("n2", "");
		}
		return map;
	}
	
	//打印活动记录
	private List<Map<String, Object>> activityRecordFixedForHar(HandlingAreaReceipt har){
		List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();
		HandleAreaActivityRecordInfo haarInfo = har.getHandleAreaActivityRecordInfo();
		if(haarInfo == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("c1", "");
			map.put("c2", "");
			map.put("c3", "");
			map.put("c4", "");
			lst.add(map);
			return lst;
		}
		List<HandlingAreaActivityRecord> haarList = haarInfo.getHandlingAreaActivityRecords();
		if(haarList !=  null){
			for(HandlingAreaActivityRecord record : haarList){
				if(null != record.getAskRoom() && null != record.getAskRoom().getName()){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("c1", record.getActivityType() == null ? "" : record.getActivityType());
					map.put("c2", dateToStr(record.getStartTime(), null));
					map.put("c3", dateToStr(record.getEndTime(), null));
					map.put("c4", record.getAskRoom().getName());
					lst.add(map);
				}
			}
		}
		if(lst.size() <= 0){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("c1", "");
			map.put("c2", "");
			map.put("c3", "");
			map.put("c4", "");
			lst.add(map);
		}
		return lst;
	}
	
	private List<Map<String, Object>> tmpLeaveInfoFixedForHar(HandlingAreaReceipt har){
		List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();
		FinallyLeaveInfo fli = har.getFinallyLeaveInfo();
		if(fli == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("c1", "");
			map.put("c2", "");
			lst.add(map);
			return lst;
		}
		List<TmpLeaveInfo> tliLst = fli.getTmpLeaveInfos();
		if(tliLst == null || tliLst.size() == 0){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("c1", "");
			map.put("c2", "");
			lst.add(map);
			return lst;
		}
		for(TmpLeaveInfo bean : tliLst){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("c1", dateToStr(bean.getLeaveTime(), null));
			map.put("c2", bean.getLeaveCause() == null ? "" : bean.getLeaveCause());
			lst.add(map);
		}
		return lst;
	}
	
	private Map<String, Object> finallyLeaveInfoFixedForHar(HandlingAreaReceipt har){
		Map<String, Object> map = new HashMap<String, Object>();
		FinallyLeaveInfo fli = har.getFinallyLeaveInfo();
		if(fli != null){
			map.put("n1", dateToStr(fli.getFinallyLeaveTime(), null));
			map.put("n2", fli.getFinallyLeaveCause() == null ? "" : fli.getFinallyLeaveCause());
		}else{
			map.put("n1", "");
			map.put("n2", "");
		}
		return map;
	}
	
	//拼装物品返还记录
	private List<Map<String, Object>> carryGoodsDetainRecordFixed(){
		List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();
		if(returnMap.getDetain().size() > 0){
			for(String goodsid : returnMap.getDetain()){
				CarryGoodsRecord carryGoodsRecord = carryGoodsInfoService.findCarryGoodsRecordById(goodsid);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("c1", carryGoodsRecord.getGoodsName() == null ? "" : carryGoodsRecord.getGoodsName());
				map.put("c2", carryGoodsRecord.getFeatures() == null ? "" : carryGoodsRecord.getFeatures());
				map.put("c3", carryGoodsRecord.getQuantity() == null ? "" : carryGoodsRecord.getQuantity());
				map.put("c4", carryGoodsRecord.getUnitOfMeasurement() == null ? "" : carryGoodsRecord.getUnitOfMeasurement());
				map.put("c5", carryGoodsRecord.getNum() == null ? "" : carryGoodsRecord.getNum());
				map.put("c6", carryGoodsRecord.getRemark() == null ? "" : carryGoodsRecord.getRemark());
				lst.add(map);
			}
		}
		if(lst.size() == 0){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("c1", "--");
			map.put("c2", "--");
			map.put("c3", "--");
			map.put("c4", "--");
			map.put("c5", "--");
			map.put("c6", "--");
			lst.add(map);
		}
		return lst;
	}
	
	//拼装物品返还
	private List<Map<String, Object>> carryGoodsReturnFixed(){
		List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();
		if(returnMap.getReturnObj().size() > 0){
			for(String goodsid : returnMap.getReturnObj()){
				CarryGoodsRecord carryGoodsRecord = carryGoodsInfoService.findCarryGoodsRecordById(goodsid);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("c1", carryGoodsRecord.getGoodsName() == null ? "" : carryGoodsRecord.getGoodsName());
				map.put("c2", carryGoodsRecord.getFeatures() == null ? "" : carryGoodsRecord.getFeatures());
				map.put("c3", carryGoodsRecord.getQuantity() == null ? "" : carryGoodsRecord.getQuantity());
				map.put("c4", carryGoodsRecord.getUnitOfMeasurement() == null ? "" : carryGoodsRecord.getUnitOfMeasurement());
				map.put("c5", carryGoodsRecord.getNum() == null ? "" : carryGoodsRecord.getNum());
				map.put("c6", carryGoodsRecord.getRemark() == null ? "" : carryGoodsRecord.getRemark());
				lst.add(map);
			}
		}
		if(lst.size() == 0){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("c1", "--");
			map.put("c2", "--");
			map.put("c3", "--");
			map.put("c4", "--");
			map.put("c5", "--");
			map.put("c6", "--");
			lst.add(map);
		}
		return lst;
	}
	
	//拼装物品保留
	private List<Map<String, Object>> carryGoodsKeepFixed(){
		List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();
		if(returnMap.getKeep().size() > 0){
			for(String goodsid : returnMap.getKeep()){
				CarryGoodsRecord carryGoodsRecord = carryGoodsInfoService.findCarryGoodsRecordById(goodsid);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("c1", carryGoodsRecord.getGoodsName() == null ? "" : carryGoodsRecord.getGoodsName());
				map.put("c2", carryGoodsRecord.getFeatures() == null ? "" : carryGoodsRecord.getFeatures());
				map.put("c3", carryGoodsRecord.getQuantity() == null ? "" : carryGoodsRecord.getQuantity());
				map.put("c4", carryGoodsRecord.getUnitOfMeasurement() == null ? "" : carryGoodsRecord.getUnitOfMeasurement());
				map.put("c5", carryGoodsRecord.getNum() == null ? "" : carryGoodsRecord.getNum());
				map.put("c6", carryGoodsRecord.getRemark() == null ? "" : carryGoodsRecord.getRemark());
				lst.add(map);
			}
		}
		if(lst.size() == 0){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("c1", "--");
			map.put("c2", "--");
			map.put("c3", "--");
			map.put("c4", "--");
			map.put("c5", "--");
			map.put("c6", "--");
			lst.add(map);
		}
		return lst;
	}
	
	/**
	 * 私有方法  随身物品记录model转bean
	 * @return
	 */
	private CarryGoodsRecordBean carryGoodsRecordSwitchcarryGoodsInfoBean(CarryGoodsRecord cgr ,boolean returnReceipty){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			CarryGoodsRecordBean cgrb = new CarryGoodsRecordBean();
			cgrb.setId(cgr.getId());
			cgrb.setFeatures(cgr.getFeatures());
			cgrb.setGoodsName(cgr.getGoodsName());
			cgrb.setNum(cgr.getNum());
			cgrb.setQuantity(cgr.getQuantity());
			cgrb.setUnitOfMeasurement(cgr.getUnitOfMeasurement());
			CarryGoodsReturnRecord  cgrr = carryGoodsReturnRecordService.findReturnRecordByGoodsRecordId(cgr.getId());
		return cgrb;
	}
	
	private List<Map<String, Object>> tmpLeaveInfoFixed(){
		List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();
		if(tliBeanLst !=  null && tliBeanLst.size() > 0){
			for(TmpLeaveInfoBean bean : tliBeanLst){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("c1", longDateToStr(bean.getLeaveTime(), null));
				map.put("c2", bean.getLeaveCause() == null ? "" : bean.getLeaveCause());
				lst.add(map);
			}
		}else{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("c1", "");
			map.put("c2", "");
			lst.add(map);
		}
		return lst;
	}
	
	//临时物品取出记录
	private List<Map<String, Object>> temporaryLstInfoFixed(){
		List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();
		if(returnMap.getTemporary().size() > 0){
			for(String goodsid : returnMap.getTemporary()){
				CarryGoodsRecord carryGoodsRecord = carryGoodsInfoService.findCarryGoodsRecordById(goodsid);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("c1", carryGoodsRecord.getGoodsName() == null ? "" : carryGoodsRecord.getGoodsName());
				map.put("c2", carryGoodsRecord.getFeatures() == null ? "" : carryGoodsRecord.getFeatures());
				map.put("c3", carryGoodsRecord.getQuantity() == null ? "" : carryGoodsRecord.getQuantity());
				map.put("c4", carryGoodsRecord.getUnitOfMeasurement() == null ? "" : carryGoodsRecord.getUnitOfMeasurement());
				map.put("c5", carryGoodsRecord.getNum() == null ? "" : carryGoodsRecord.getNum());
				map.put("c6", carryGoodsRecord.getRemark() == null ? "" : carryGoodsRecord.getRemark());
				lst.add(map);
			}
		}
		if(lst.size() == 0){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("c1", "");
			map.put("c2", "");
			map.put("c3", "");
			map.put("c4", "");
			map.put("c5", "");
			map.put("c6", "");
			lst.add(map);
		}
		return lst;
	}
	
	private Map<String, Object> finallyLeaveInfoFixed(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("n12", longDateToStr(fliBean.getFinallyLeaveTime(), null));
		map.put("n13", fliBean.getFinallyLeaveCause() == null ? "" : fliBean.getFinallyLeaveCause());
		return map;
	}
	
	private List<Map<String, Object>> activityRecordFixed(){
		List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();
		if(activityRecordBeanLst !=  null && activityRecordBeanLst.size() > 0){
			for(ActivityRecordBean bean : activityRecordBeanLst){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("c1", longDateToStr(bean.getStartTime(), null));
				map.put("c2", longDateToStr(bean.getEndTime(), null));
				if(bean.getAskRoom() != null){
					ActivityRoom ar = askRoomService.findAskRoomById(bean.getAskRoom().getId());
					if(ar != null){
						map.put("c3", ar.getName() == null ? "" : ar.getName());
					}
				}else{
					map.put("c3", "");
				}
				map.put("c4", bean.getCaretaker() == null ? "" : bean.getCaretaker());
				map.put("c5", bean.getMaintainer() == null ? "" : bean.getMaintainer());
				map.put("c6", longDateToStr(bean.getMaintainTime(), null));
				map.put("c7", bean.getActivityContent() == null ? "" : bean.getActivityContent());
				map.put("c8", bean.getNote() == null ? "" : bean.getNote());
				lst.add(map);
			}
		}else{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("c1", "");
			map.put("c2", "");
			map.put("c3", "");
			map.put("c4", "");
			map.put("c5", "");
			map.put("c6", "");
			map.put("c7", "");
			map.put("c8", "");
			lst.add(map);
		}
		return lst;
	}
	
	/**
	 * long时间转化成打印字符串
	 * @param time 毫秒数
	 * @param fmt 输出格式，默认为精确到分钟
	 * @return 
	 */
	private String longDateToStr(Long time, String fmt){
		String str = "";
		if(time == null){
			return str;
		}
		if(StringUtils.isBlank(fmt)){
			fmt = "yyyy-MM-dd HH:mm";
		}
		Calendar ca = Calendar.getInstance();
		ca.setTimeInMillis(time);
		Date date = ca.getTime();
		return dateToStr(date, fmt);
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
	
	/**
	 * 通过查询基本信息
	 * 
	 * @return
	 */
	private BasicCaseBean findBasicCaseByHarId(HandlingAreaReceipt har) {
		BasicCaseBean basicCaseBean = new BasicCaseBean();
		try {
			BeanCopierFmtUtil.copyBean(har.getBasicCase(), basicCaseBean, null);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage(), e);
		}
		if (basicCaseBean.getByQuestioningPeopleIdentifyType() != null) {
			DictionaryItem di = dictionaryItemService.findById(basicCaseBean.getByQuestioningPeopleIdentifyType());
			if (di != null) {
				basicCaseBean.setByQuestioningPeopleIdentifyTypeStr(di.getName());
			} else {
				basicCaseBean.setByQuestioningPeopleIdentifyTypeStr("");
			}
		} else {
			basicCaseBean.setByQuestioningPeopleIdentifyTypeStr("");
		}
		DictionaryItem di = dictionaryItemService.findById(basicCaseBean.getSex());
		if (di != null) {
			basicCaseBean.setSexStr(di.getName());
		} else {
			basicCaseBean.setSexStr("");
		}
		basicCaseBean.setHandlingAreaReceiptNum(har.getBasicCase().getHandlingAreaReceiptNum());
		
		if (Constant.AY_QT.equals(basicCaseBean.getCauseOfLawCase())) {
			basicCaseBean.setCauseOfLawCase(basicCaseBean.getOtherCauseOfLawCase());
		} else if (basicCaseBean.getCauseOfLawCase() != null) {
			basicCaseBean.setCauseOfLawCase(dictionaryItemService.findById(basicCaseBean.getCauseOfLawCase()).getName());
		} else {
			basicCaseBean.setCauseOfLawCase("");
		}
		basicCaseBean.setEnterAreaReasons(har.getBasicCase().getEnterAreaReasons());
		if(har.getBasicCase().getLawCase() != null){
			basicCaseBean.setLawCase(har.getBasicCase().getLawCase());
		}else{
			basicCaseBean.setLawCase("");
		}
		List<Attachment> attLst = attachmentCustomizedService.findByTargetIdAndType(basicCaseBean.getId(),
				BasicCase.class.getName());
		if (!attLst.isEmpty()) {
			FileObjectBean b = new FileObjectBean();
			b.setId(attLst.get(0).getId());
			b.setName(attLst.get(0).getName());
			basicCaseBean.setAttach(b);
		}
		return basicCaseBean;
	}
	

	/**
	 * 组装填表单位和使用单编号。
	 * 
	 * @return
	 */
	private Map<String,Object> handlingAreaReceiptFixedForHar(HandlingAreaReceipt har) {
		Map<String, Object> map = new HashMap<String, Object>();
		Organization org = organizationService.findOrganizationById(har.getCreateUnitId());
		if(org != null){
			map.put("n1", org.getName());
		}
		map.put("n2", har.getBasicCase().getHandlingAreaReceiptNum());
		return map;
	}
	
	/**
	 * 组装打印单基本信息。
	 * 
	 * @return
	 */
	private Map<String,Object> basicCaseInfoFixed(HandlingAreaReceipt har) {
		Map<String, Object> map = new HashMap<String, Object>();
		BasicCaseBean bcBean = findBasicCaseByHarId(har);
		map.put("n1", bcBean.getByQuestioningPeopleName());
		map.put("n2", bcBean.getSexStr());
		if(null == bcBean.getByQuestioningPeopleIdentifyNum()){
			map.put("n3", "");
			//map.put("n6", bcBean.getByQuestioningPeopleIdentifyNum() == null ? "" : bcBean.getByQuestioningPeopleIdentifyNum());
			map.put("n5", "");
		}else if(bcBean.getByQuestioningPeopleIdentifyNum().length() == 18){
			String idCode = bcBean.getByQuestioningPeopleIdentifyNum();
			String year = idCode.substring(6, 10);
			String month = idCode.substring(10, 12);
			String day = idCode.substring(12, 14);
			map.put("n3", year + "-" + month + "-" + day);
			map.put("n5", "身份证");
		}else if(bcBean.getByQuestioningPeopleIdentifyNum().length() == 15){
			String idCode = bcBean.getByQuestioningPeopleIdentifyNum();
			String year = "19" + idCode.substring(6, 8);
			String month = idCode.substring(8, 10);
			String day = idCode.substring(10, 12);
			map.put("n3", year + "-" + month + "-" + day);
			map.put("n5", "身份证");
		}else{
			map.put("n3", "");
			map.put("n5", "身份证");
		}
		map.put("n4", bcBean.getByQuestioningPeopleTelphoneNumber() == null ? "" : bcBean.getByQuestioningPeopleTelphoneNumber());
		map.put("n6", bcBean.getByQuestioningPeopleIdentifyNum() == null ? "" : bcBean.getByQuestioningPeopleIdentifyNum());
		map.put("n7", bcBean.getByQuestioningPeopleAddress() == null ? "" : bcBean.getByQuestioningPeopleAddress());
		map.put("n8", bcBean.getLawCase() == null ? "" : bcBean.getLawCase());
		map.put("n9", bcBean.getCauseOfLawCase() == null ? "" : bcBean.getCauseOfLawCase());
		map.put("n10", bcBean.getAttach() == null ? "" : bcBean.getAttach().getName());
		map.put("n11", bcBean.getEnterAreaReasons() == null ? "" : bcBean.getEnterAreaReasons());
		return map;
	
	}
	/**
	 * 组装人身检查记录其他打印信息。
	 * 
	 * @return
	 */
	private Map<String,Object> personCheckRecordPrintInfo() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("n12", personCheckRecordBean.getSelfReportedSymptoms());
		map.put("n13", personCheckRecordBean.getCheckCondition());
		return map;
	}
	
	/**
	 * 组装信息采集情况其他打印信息。
	 * 
	 * @return
	 */
	private Map<String,Object> collectInfoSituationPrintInfo() {
		Map<String, Object> map = new HashMap<String, Object>();
		if (Constant.SF_S.equals(collectInfoSituationBean.getIsCollect())) {
			map.put("n12", SHI);
		} else if (Constant.SF_F.equals(collectInfoSituationBean.getIsCollect())) {
			map.put("n12", FOU);
		}
		String objects = collectInfoSituationBean.getCollectProject();	
		if(!StringUtils.isBlank(collectInfoSituationBean.getCollectProjectOther())){
			if(StringUtils.isBlank(objects)){
				objects += "其他:" + collectInfoSituationBean.getCollectProjectOther();
			}else{
				objects += "，其他:" + collectInfoSituationBean.getCollectProjectOther();
			}
		}
		map.put("n13", objects);
		if (Constant.SF_S.equals(collectInfoSituationBean.getInfoIntoString())) {
			map.put("n14", SHI);
		} else if (Constant.SF_F.equals(collectInfoSituationBean.getInfoIntoString())) {
			map.put("n14", FOU);
		}		
		if (Constant.SF_S.equals(collectInfoSituationBean.getInspectComparison())) {
			map.put("n15", SHI);
		} else if (Constant.SF_F.equals(collectInfoSituationBean.getInspectComparison())) {
			map.put("n15", FOU);
		}
		map.put("n16", collectInfoSituationBean.getQqNum() == null ? "" : collectInfoSituationBean.getQqNum());
		map.put("n17", collectInfoSituationBean.getWeixinNum() == null ? "" : collectInfoSituationBean.getWeixinNum());
		return map;
	}
	
	/**
	 * 组装信息采集情况手机打印信息。
	 * 
	 * @return
	 */
	private List<Map<String, Object>> collectInfoSituationPrintPhoneInfo() {
		List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();
		if(null != collectInfoSituationBean.getPhoneInfo()&& collectInfoSituationBean.getPhoneInfo().length() >0){
			String str = collectInfoSituationBean.getPhoneInfo();
			String[] hponeInfos = collectInfoSituationBean.getPhoneInfo().split(";");
			int len = hponeInfos.length;
			for(int i = 0 ; i < len ;i++){
				String[] hponeInfo = hponeInfos[i].split(","); 
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("c1",hponeInfo[0]);
				map.put("c2",hponeInfo[1]);
				map.put("c3",hponeInfo[2]);
				lst.add(map);
			}
			
		}else{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("c1","");
			map.put("c2","");
			map.put("c3","");
			lst.add(map);
			return lst;
		}
		return lst;
	}
	
	/**
	 * 组装随身物品检查记录其他打印信息。
	 * 
	 * @return
	 */
	private List<Map<String, Object>> carryGoodsCheckRecordPrintInfo() {
		List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();
		CarryGoodsInfo cgf = carryGoodsInfoService.findCarryGoodsInfoByHandlingAreaReceiptId(id);
		if(cgf == null || null == cgf.getCarryGoodsRecords() || cgf.getCarryGoodsRecords().size() < 1){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("c1","");
			map.put("c2","");
			map.put("c3","");
			map.put("c4","");
			map.put("c5","");
			map.put("c6","");
			map.put("c7","");
			lst.add(map);
			return lst;
		}
		carryGoodsRecordBeanList = new ArrayList<CarryGoodsRecordBean>();
		for(CarryGoodsRecord cgr : cgf.getCarryGoodsRecords()){
			CarryGoodsRecordBean ca = carryGoodsRecordSwitchcarryGoodsInfoBean(cgr ,false);
			carryGoodsRecordBeanList.add(ca);
		}
		
		if(carryGoodsRecordBeanList !=  null){
			for(CarryGoodsRecordBean bean : carryGoodsRecordBeanList){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("c1",bean.getGoodsName());
				map.put("c2",bean.getFeatures());
				map.put("c3",bean.getNum() );
				map.put("c4",bean.getQuantity());
				map.put("c5",bean.getUnitOfMeasurement());
				//map.put("c6",bean.getUnitOfMeasurement());
				map.put("c7",bean.getRemark());
				lst.add(map);
			}
		}
		return lst;
	}
	private List<Map<String, Object>> carryGoodsInfoRecordFixed() {
		List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();
		CarryGoodsInfo cgf = carryGoodsInfoService.findCarryGoodsInfoByHandlingAreaReceiptId(id);
		if (null != cgf) {
			List<CarryGoodsRecord> temporaryLst = cgf.getCarryGoodsRecords();
			if (temporaryLst != null && temporaryLst.size() > 0) {
				for (CarryGoodsRecord bean : temporaryLst) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("c1",
							bean.getGoodsName() == null ? "" : bean
									.getGoodsName());
					map.put("c2",
							bean.getFeatures() == null ? "" : bean
									.getFeatures());
					map.put("c3", bean.getNum() == null ? "" : bean.getNum());
					map.put("c4",
							bean.getQuantity() == null ? "" : bean
									.getQuantity());
					map.put("c5", bean.getUnitOfMeasurement() == null ? ""
							: bean.getUnitOfMeasurement());
					map.put("c6", "");
					map.put("c7",
							bean.getRemark() == null ? "" : bean.getRemark());
					lst.add(map);
				}
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("c1", "");
				map.put("c2", "");
				map.put("c3", "");
				map.put("c4", "");
				map.put("c5", "");
				map.put("c6", "");
				map.put("c7", "");
				lst.add(map);
			}
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("c1", "");
			map.put("c2", "");
			map.put("c3", "");
			map.put("c4", "");
			map.put("c5", "");
			map.put("c6", "");
			map.put("c7", "");
			lst.add(map);
		}
		return lst;
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ExportInfoReq getExportInfoReq() {
		return exportInfoReq;
	}

	public void setExportInfoReq(ExportInfoReq exportInfoReq) {
		this.exportInfoReq = exportInfoReq;
	}


	public PersonCheckRecordBean getPersonCheckRecordBean() {
		return personCheckRecordBean;
	}


	public void setPersonCheckRecordBean(PersonCheckRecordBean personCheckRecordBean) {
		this.personCheckRecordBean = personCheckRecordBean;
	}

	public CollectInfoSituationBean getCollectInfoSituationBean() {
		return collectInfoSituationBean;
	}

	public void setCollectInfoSituationBean(CollectInfoSituationBean collectInfoSituationBean) {
		this.collectInfoSituationBean = collectInfoSituationBean;
	}


	public FinallyLeaveInfoBean getFliBean() {
		return fliBean;
	}


	public void setFliBean(FinallyLeaveInfoBean fliBean) {
		this.fliBean = fliBean;
	}


	public List<TmpLeaveInfoBean> getTliBeanLst() {
		return tliBeanLst;
	}


	public void setTliBeanLst(List<TmpLeaveInfoBean> tliBeanLst) {
		this.tliBeanLst = tliBeanLst;
	}

	public List<ActivityRecordBean> getActivityRecordBeanLst() {
		return activityRecordBeanLst;
	}

	public void setActivityRecordBeanLst(List<ActivityRecordBean> activityRecordBeanLst) {
		this.activityRecordBeanLst = activityRecordBeanLst;
	}

	public List<CarryGoodsRecordBean> getCarryGoodsRecordBeanList() {
		return carryGoodsRecordBeanList;
	}

	public void setCarryGoodsRecordBeanList(List<CarryGoodsRecordBean> carryGoodsRecordBeanList) {
		this.carryGoodsRecordBeanList = carryGoodsRecordBeanList;
	}

	public ReturnPrintBean getReturnPrintBean() {
		return returnPrintBean;
	}

	public void setReturnPrintBean(ReturnPrintBean returnPrintBean) {
		this.returnPrintBean = returnPrintBean;
	}

	public List<CarryGoodsRecordBean> getTemporaryLst() {
		return temporaryLst;
	}

	public void setTemporaryLst(List<CarryGoodsRecordBean> temporaryLst) {
		this.temporaryLst = temporaryLst;
	}

	public String getCaseFormCode() {
		return caseFormCode;
	}

	public void setCaseFormCode(String caseFormCode) {
		this.caseFormCode = caseFormCode;
	}

	public ReturnDataBean getReturnMap() {
		return returnMap;
	}

	public void setReturnMap(ReturnDataBean returnMap) {
		this.returnMap = returnMap;
	}

	public String getHandlingAreaReceiptId() {
		return handlingAreaReceiptId;
	}

	public void setHandlingAreaReceiptId(String handlingAreaReceiptId) {
		this.handlingAreaReceiptId = handlingAreaReceiptId;
	}
	
	
	
}
