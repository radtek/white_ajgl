package com.taiji.pubsec.ajqlc.sawp.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajgl.util.OperationRecordConstant;
import com.taiji.pubsec.ajgl.util.ReturnMessageAction;
import com.taiji.pubsec.ajgl.util.bean.OptionItemBean;
import com.taiji.pubsec.ajqlc.operationrecord.model.OperationRecord;
import com.taiji.pubsec.ajqlc.operationrecord.service.IOperationRecordService;
import com.taiji.pubsec.ajqlc.sawp.action.bean.BackStorageFormExcelBean;
import com.taiji.pubsec.ajqlc.sawp.action.bean.BackStorageSnapshotBean;
import com.taiji.pubsec.ajqlc.sawp.action.bean.InComingSnapshootBean;
import com.taiji.pubsec.ajqlc.sawp.action.util.SawpModelBeanTransformUtil;
import com.taiji.pubsec.ajqlc.sawp.bean.BackStorageFormItemServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.BackStorageFormServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.OutStorageFormItemServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.OutStorageFormServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.StorageServiceBean;
import com.taiji.pubsec.ajqlc.sawp.model.ArticleLocker;
import com.taiji.pubsec.ajqlc.sawp.model.BackStorageForm;
import com.taiji.pubsec.ajqlc.sawp.model.BackStorageSnapshot;
import com.taiji.pubsec.ajqlc.sawp.model.IncomingSnapshot;
import com.taiji.pubsec.ajqlc.sawp.model.OutStorageForm;
import com.taiji.pubsec.ajqlc.sawp.service.IArticleLockerService;
import com.taiji.pubsec.ajqlc.sawp.service.IBackStorageFormService;
import com.taiji.pubsec.ajqlc.sawp.service.IIncomingSnapshotService;
import com.taiji.pubsec.ajqlc.sawp.service.IInvolvedArticleService;
import com.taiji.pubsec.ajqlc.sawp.service.IOutStorageFormService;
import com.taiji.pubsec.ajqlc.util.Constant;
import com.taiji.pubsec.ajqlc.util.DateFmtUtil;
import com.taiji.pubsec.ajqlc.util.ExcelUtil;
import com.taiji.pubsec.common.tools.doc.core.model.ReportConfig;
import com.taiji.pubsec.common.tools.doc.msoffice.impl.poicr.builder.PoiCrReportBuilder;
import com.taiji.pubsec.common.tools.doc.msoffice.impl.poicr.reporter.PoiCrReport;
import com.taiji.pubsec.common.web.action.ExportInfoReq;
import com.taiji.pubsec.springsecurity.resource.util.MySecureUser;
import com.taiji.pubsec.springsecurity.resource.util.SessionUserDetailsUtil;

/**
 * 返还单Action
 * @author WangLei
 *
 */
@Controller("backStorageFormAction")
@Scope("prototype")
public class BackStorageFormAction extends ReturnMessageAction{

	private static final long serialVersionUID = 1L;
	
	@Resource
	private SawpModelBeanTransformUtil sawpModelBeanTransformUtil;// model和bean相互转换的类
	
	@Resource
	private IBackStorageFormService backStorageFormService;// 返还单服务接口
	
	@Resource
	private IOutStorageFormService outStorageFormService;// 出库单服务接口
	
	@Resource
    private IOperationRecordService operationRecordService;// 操作记录接口
	
	@Resource
	private PoiCrReportBuilder poiCrReportBuilder;
	
	@Resource
	private IInvolvedArticleService involvedArticleService;
	
	@Resource
	private IIncomingSnapshotService incomingSnapshotService;// 再入库快照
	
	@Resource
	private IArticleLockerService articleLockerService;// 储物箱接口
	
	private ExportInfoReq exportInfoReq = new ExportInfoReq();// 导出Excel用
	
	private String code;// 返还单流水号
	
	private String outStorageFormId;// 出库单id
	
	private String backStorageFormId;// 返还单id
	
	private Long startTime;// 开始时间
	
	private Long endTime;// 结束时间
	
	private String caseCode;// 案件编号
	
	private String storageAreaId;// 保管区id
	
	private OutStorageFormServiceBean outStorageFormServiceBean;// 出库单Bean
	
	private BackStorageFormServiceBean backStorageFormServiceBean;// 返还单ServiceBean
	
	private List<String> backStorageFormIdList = new ArrayList<String>();// 返还单id集合
	
	private List<String> storageOutTypeList = new ArrayList<String>();// 出库类型字典项id集合
	
	private List<OutStorageFormItemServiceBean> outStorageFormItemServiceBeanList = new ArrayList<OutStorageFormItemServiceBean>();// 出库单项Bean集合
	
	private List<BackStorageFormItemServiceBean> backStorageFormItemServiceBeanList = new ArrayList<BackStorageFormItemServiceBean>();// 返还单项ServiceBean
	
	private List<BackStorageFormServiceBean> backStorageFormServiceBeanList = new ArrayList<BackStorageFormServiceBean>();// 返还单ServiceBean集合
	
	private List<BackStorageFormExcelBean> backStorageFormExcelBeanList = new ArrayList<BackStorageFormExcelBean>();// 导出ExcelBean集合
	
	private Map<String,Object> conditionMap = new HashMap<String,Object>();// 查询条件map
	
	private List<OptionItemBean> outStorageFormIdAndCodeList = new ArrayList<OptionItemBean>();// 出库单select选项集合
	
	private List<ArticleLocker> articleLockerList = new ArrayList<ArticleLocker>(); // 储物箱List
	
	private InComingSnapshootBean inComingSnapshootBean=new InComingSnapshootBean(); //再入库单快照
	
	private List<BackStorageSnapshotBean> backstorageLst  = new ArrayList<BackStorageSnapshotBean>();
	/**
	 * 新建返还单
	 * 
	 * @param backStorageFormServiceBean 返还单Bean
	 * @param backStorageFormItemServiceBeanList 返还单项Bean集合
	 * @return "success",成功返回flag:true成功，false失败
	 */
	public String newBackStorageForm(){
		backStorageFormId = backStorageFormService.createForm(backStorageFormServiceBean, backStorageFormItemServiceBeanList);
		this.setFlag("true");
		//创建操作记录
		for(BackStorageFormItemServiceBean bsfisb : backStorageFormItemServiceBeanList){
			OperationRecord or = new OperationRecord();
			or.setArticleCode(bsfisb.getInvolvedArticleCode());
			or.setFormCode(backStorageFormServiceBean.getCode());
			or.setFormId(backStorageFormId);
			or.setFormType(BackStorageForm.class.getName());
			
			List<StorageServiceBean> ssbList = bsfisb.getStorageServiceBeans();
			if(ssbList != null && !ssbList.isEmpty()){
				int returnNumberCount = 0;
				for(StorageServiceBean ssb : ssbList){
					returnNumberCount += ssb.getReturnNumber();
				}
				or.setNumber(String.valueOf(returnNumberCount));
			}
			or.setOperation(OperationRecordConstant.WP_CZJL_FH);
			or.setOperationTime(new Date());
			or.setOperator(this.findCurrentPerson().getName());
			operationRecordService.saveOperationRecord(or);
		}
		//快照
		if(backStorageFormId!=null){//如果是新的再入库单的话
			inComingSnapshootBean.setFormId(backStorageFormId);
		}
		incomingSnapshotService.saveBackStorageSnapshots(changeModel(inComingSnapshootBean), changeModel(backstorageLst));
		this.creatCaseManagementLog("涉案物品管理 /涉案物品再入库", "新建涉案物品再入库单(再入库单id:"+backStorageFormId+")");
		return SUCCESS;
	}
	
	private List<BackStorageSnapshot> changeModel(List<BackStorageSnapshotBean> LstBean) {
		List<BackStorageSnapshot> lst=new ArrayList<BackStorageSnapshot>();
		for(int i=0;i<LstBean.size();i++){
			BackStorageSnapshot inst=new BackStorageSnapshot();
			inst.setArticleCode(LstBean.get(i).getArticleCode());
			inst.setArticleFeature(LstBean.get(i).getArticleFeature());
			inst.setArticleName(LstBean.get(i).getArticleName());
			inst.setMeasurementUnit(LstBean.get(i).getMeasurementUnit());
			inst.setNumberRequested(LstBean.get(i).getNumberRequested());
//			inst.setPaper(LstBean.get(i).getPaper());
			inst.setThisIncomingNum(LstBean.get(i).getThisIncomingNum());
			inst.setOutComingNum(LstBean.get(i).getOutComingNum());
			inst.setSuspectName(LstBean.get(i).getSuspectName());
			inst.setSuspectIdCardNo(LstBean.get(i).getSuspectIdCardNo());
			inst.setIfInStorageAll(LstBean.get(i).getIfInStorageAll());
			lst.add(inst);
		}
		return lst;
	}

	@SuppressWarnings("unchecked")
	private IncomingSnapshot changeModel(InComingSnapshootBean incomeBean) {
		IncomingSnapshot incom=new IncomingSnapshot();
		incom.setSnapshotTime(new Date(incomeBean.getSnapshotTime()));
		incom.setFormId(incomeBean.getFormId());
//		incom.setTransactor(incomeBean.getTransactor());
		MySecureUser user = SessionUserDetailsUtil.getMySecureUser();
		Map<String, Object> userMap = user.getUserMap();
		Map<String, String> mPerson = new HashMap<String, String>(0);
		if (userMap.get("person") != null) {
			mPerson = (Map<String, String>) userMap.get("person");
		}
		incom.setTransactor(mPerson.get("name"));
		return incom;
	}
	
	/**
	 * 返还单查询（分页）
	 * 
	 * @param 
	 * @return "success",成功返回backStorageFormServiceBeanList:返还单Bean集合
	 */
	public String findBackStorageFormByCondition(){
		this.conditionMap();
		Pager<BackStorageForm> pager = backStorageFormService.queryByCondition(conditionMap, this.getStart()/this.getLength(), this.getLength());
		if(pager == null){
			return SUCCESS;
		}
		this.setTotalNum(pager.getTotalNum());
		for(BackStorageForm bsf : pager.getPageList()){
			BackStorageFormServiceBean bsfsb = sawpModelBeanTransformUtil.backStorageFormToBackStorageFormServiceBean(bsf);
			backStorageFormServiceBeanList.add(bsfsb);
		}
		return SUCCESS;
	}
	
	/**
	 * 根据返还单id查询返还单
	 * 
	 * @param backStorageFormId 返还单id
	 * @return "success",成功返回backStorageFormServiceBean:返还单Bean;backStorageFormItemServiceBeanList:返还单项Bean集合
	 */
	public String findBackStorageFormById(){
		BackStorageForm bsf = backStorageFormService.findById(backStorageFormId);
		if(bsf == null){
			return SUCCESS;
		}
		backStorageFormServiceBean = sawpModelBeanTransformUtil.backStorageFormToBackStorageFormServiceBean(bsf);
		backStorageFormItemServiceBeanList = backStorageFormService.findBackStorageFormItemServiceBeanByOutFormId(backStorageFormServiceBean.getOutStorageFormId());
		return SUCCESS;
	}
	
	/**
	 * 根据出库单id查询返还项
	 * 
	 * @param outStorageFormId 出库单id
	 * @return "success",成功返回backStorageFormItemServiceBeanList:返还单ServiceBean集合
	 */
	public String findBackStorageFormByOsfId(){
		OutStorageForm osf = outStorageFormService.findById(outStorageFormId);
		BackStorageForm bsf = null;
		if(osf != null){
			bsf = backStorageFormService.findBackStorageFormByOutFormCode(osf.getCode());
			backStorageFormServiceBean = sawpModelBeanTransformUtil.outStorageFormToBackStorageFormServiceBean(osf);
		}
		if(bsf != null){
			backStorageFormServiceBean = sawpModelBeanTransformUtil.backStorageFormToBackStorageFormServiceBean(bsf);
		}
		backStorageFormItemServiceBeanList = backStorageFormService.findBackStorageFormItemServiceBeanByOutFormId(outStorageFormId);
		return SUCCESS;
	}
	
	/**
	 * 删除返还单
	 * 
	 * @param backStorageFormId 返还单id集合
	 * @return "success",成功无返回值
	 */
	public String deleteBackStorageFormByIds(){
		String str="";
		for(String bsfId : backStorageFormIdList){
			backStorageFormService.deleteForm(bsfId);
			str+=bsfId+",";
		}
		str=str.substring(0, str.length()-1);
		this.creatCaseManagementLog("涉案物品管理 /涉案物品再入库", "删除涉案物品再入库单(再入库单id:"+str+")");
		return SUCCESS;
	}
	
	/**
	 * 获取可选出库单编号集合
	 * 
	 * @param storageOutTypeList 出库类型字典项id集合
	 * @return "success",成功返回outStorageFormServiceBeanList:出库单Bean集合
	 */
	public String acquireStorageOutCodeList(){
		storageOutTypeList.add(Constant.CKLX_JC);
		storageOutTypeList.add(Constant.CKLX_QT);
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		List<OutStorageForm> osfList = outStorageFormService.findOutStorageFormsByType(caseCode,storageOutTypeList);
		for(OutStorageForm osf : osfList){
			OptionItemBean oib = new OptionItemBean();
			oib.setId(osf.getId());
			oib.setCode(osf.getCode());
			oib.setTime(formatter.format(osf.getOutStorageTime()));
			outStorageFormIdAndCodeList.add(oib);
		}
		return SUCCESS;
	}
	
	/**
	 * 储物箱选择页面数据初始化
	 * 
	 * @param storageAreaId 保管区Id
	 * @param caseCode 案件编号
	 * @return articleLockerList 储物箱List
	 */
	public String initDataForStorageSelectPage() {
		articleLockerList = articleLockerService.findLockersByCaseCodeAndAreaId(caseCode, storageAreaId);
		return SUCCESS;
	}
	
	/**
	 * 获得流水单号
	 * @return "success",成功返回code:调整单流水号
	 */
	public String acquireNum(){
		code = backStorageFormService.acquireNum();
		return SUCCESS;
	}
	
	/**
	 * 打印返还单
	 * 
	 * @return
	 */
	public String printBackStorageFormById(){
		//backStorageFormServiceBean
		//遍历入库物品对象（物品中存储位置中的incomingNumber已经是差值，直接累加就获得此物品本次入库数量）
//		if(backStorageFormItemServiceBeanList != null){
//			for(int i = backStorageFormItemServiceBeanList.size()-1; i >= 0; i--){
//				List<StorageServiceBean> ssBeanLst = backStorageFormItemServiceBeanList.get(i).getStorageServiceBeans();
//				int count = 0;
//				if(ssBeanLst != null){
//					for(StorageServiceBean ssb : ssBeanLst){
//						count += ssb.getReturnNumber();
//					}
//				}
//				//添加，returnedNumber记录本次入库数量
//				backStorageFormItemServiceBeanList.get(i).setReturnedNumber(count);
//			}
//		}
//		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//		
//		Map<String, Object> map = new HashMap<String, Object>();
//		Map<String, Object> mapN1 = new HashMap<String, Object>();
//		if(backStorageFormServiceBean == null){
//			mapN1.put("n1", sdf.format(new Date()));
//			mapN1.put("n2", "");
//			mapN1.put("n3", "");
//			mapN1.put("n4", "");
//			mapN1.put("n5", "");
//			mapN1.put("n6", "");
//			mapN1.put("n7", "");
//			mapN1.put("n8", "");
//			mapN1.put("n9", "");
//			
//		}else{
//			
//			mapN1.put("n1", sdf.format(new Date()));
//			mapN1.put("n2", null == backStorageFormServiceBean.getCode() ? "" : backStorageFormServiceBean.getCode());
//			mapN1.put("n3", null == backStorageFormServiceBean.getCreatedTime() ? "" : sdf.format(backStorageFormServiceBean.getCreatedTime()));
//			mapN1.put("n4", null == backStorageFormServiceBean.getOutStorageFormCode() ? "" :backStorageFormServiceBean.getOutStorageFormCode());
//			mapN1.put("n5", null == backStorageFormServiceBean.getCaseCode() ? "" : backStorageFormServiceBean.getCaseCode());
//			mapN1.put("n6", null == backStorageFormServiceBean.getCaseName() ? "" : backStorageFormServiceBean.getCaseName());
//			mapN1.put("n7", null == backStorageFormServiceBean.getPolices() ? "" : backStorageFormServiceBean.getPolices());
//			mapN1.put("n8", null == backStorageFormServiceBean.getRemark() ? "" : backStorageFormServiceBean.getRemark());
//			mapN1.put("n9", this.findCurrentPerson().getName());
//		}
//		map.put("n1", mapN1);
//		
//		List<Map<String, String>> listMapT1 = new ArrayList<Map<String, String>>();
//		
//		if (backStorageFormItemServiceBeanList == null || backStorageFormItemServiceBeanList.size() <= 0) {
//			Map<String, String> mapLine = new HashMap<String, String>();
//			mapLine.put("c1", "");
//			mapLine.put("c2", "");
//			mapLine.put("c3", "");
//			mapLine.put("c4", "");
//			mapLine.put("c5", "");
//			mapLine.put("c6", "");
//			mapLine.put("c7", "");
//			mapLine.put("c8", "");
//			mapLine.put("c9", "");
//			mapLine.put("c10", "");
//			mapLine.put("c11", "");
//			listMapT1.add(mapLine);
//		} else {
//			if(backStorageFormItemServiceBeanList != null){
//				int index = 1;
//				OutStorageForm outStorageForm = outStorageFormService.findByCode(backStorageFormServiceBean.getOutStorageFormCode());
//				for(BackStorageFormItemServiceBean bsfisb : backStorageFormItemServiceBeanList){
//					Map<String, String> mapLine = new HashMap<String, String>();
//					InvolvedArticle involvedArticle = involvedArticleService.findInvolvedArticleByCode(bsfisb.getInvolvedArticleCode());
//					mapLine.put("c1", String.valueOf(index));
//					mapLine.put("c2", null == involvedArticle.getName() ? "" : involvedArticle.getName());
//					mapLine.put("c3", null == involvedArticle.getFeature() ? "" : involvedArticle.getFeature());
//					mapLine.put("c4", bsfisb.getInvolvedArticleCode());
//					mapLine.put("c5", null == involvedArticle.getDetentionNumber() ? "" : involvedArticle.getDetentionNumber());
//					for(OutStorageFormItem o: outStorageForm.getOutStorageFormItems()){
//						if(o.getArticle().getCode().equals(bsfisb.getInvolvedArticleCode())){
//							mapLine.put("c6", String.valueOf(o.getOutcomingNumber()));
//						}
//					}
//					mapLine.put("c7", null == String.valueOf(bsfisb.getReturnedNumber()) ? "" :String.valueOf(bsfisb.getReturnedNumber()) );
//					mapLine.put("c8", null == involvedArticle.getMeasurementUnit() ? "" : involvedArticle.getMeasurementUnit());
//					mapLine.put("c9", null == involvedArticle.getSuspectName() ? "" : involvedArticle.getSuspectName());
//					mapLine.put("c10", involvedArticle.getSuspectIdentityNumber() == null?"":involvedArticle.getSuspectIdentityNumber());
//					mapLine.put("c11", null == bsfisb.getIsReturend() ? "" : bsfisb.getIsReturend() );
//					listMapT1.add(mapLine);
//					index ++;
//				}
//			}
//		}
//		map.put("t1", listMapT1);
//		
//		ReportConfig rc = poiCrReportBuilder.createFromXmlFile(map, this.getRequest().getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "printAbout" + File.separator + "printXml" + File.separator + "backStorageFormRecord.xml");
//		PoiCrReport report = new PoiCrReport(rc);
//		poiCrReportBuilder.build(report) ;
//		InputStream is = report.generateReportInputStream() ;
//		try {
//			exportInfoReq.setLength(Long.valueOf(String.valueOf(is.available())));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		exportInfoReq.setIn(is);
//		exportInfoReq.setName("涉案物品返还单.docx");
//		return "done" ;
		
		BackStorageForm istForm= backStorageFormService.findById(backStorageFormId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mapN1 = new HashMap<String, Object>();
		if(istForm == null){
			mapN1.put("n1", sdf.format(new Date()));
			mapN1.put("n2", "");
			mapN1.put("n3", "");
			mapN1.put("n4", "");
			mapN1.put("n5", "");
			mapN1.put("n6", "");
			mapN1.put("n7", "");
			mapN1.put("n8", "");
			mapN1.put("n9", "");
			
		}else{
			
			mapN1.put("n1", sdf.format(new Date()));
			mapN1.put("n2", null == istForm.getCode() ? "" : istForm.getCode());
			mapN1.put("n3", null == istForm.getCreatedTime() ? "" : sdf.format(istForm.getBackStorageTime()));
			mapN1.put("n4", null == istForm.getOutStorageFormCode() ? "" :istForm.getOutStorageFormCode());
			mapN1.put("n5", null == istForm.getCaseCode() ? "" : istForm.getCaseCode());
			mapN1.put("n6", null == istForm.getCaseName() ? "" : istForm.getCaseName());
			mapN1.put("n7", null == istForm.getHandlePolices() ? "" : istForm.getHandlePolices());
			mapN1.put("n8", null == istForm.getRemark() ? "" : istForm.getRemark());
			mapN1.put("n9", this.findCurrentPerson().getName());
		}
		map.put("n1", mapN1);
		
		List<Map<String, String>> listMapT1 = new ArrayList<Map<String, String>>();
		IncomingSnapshot ist= incomingSnapshotService.findLatestIncomingSnapshotByFormId(backStorageFormId);
		if (ist == null || ist.getBackStorageSnapshots().size() <= 0) {
			Map<String, String> mapLine = new HashMap<String, String>();
			mapLine.put("c1", "");
			mapLine.put("c2", "");
			mapLine.put("c3", "");
			mapLine.put("c4", "");
			mapLine.put("c5", "");
			mapLine.put("c6", "");
			mapLine.put("c7", "");
			mapLine.put("c8", "");
			mapLine.put("c9", "");
			mapLine.put("c10", "");
			mapLine.put("c11", "");
			listMapT1.add(mapLine);
		} else {
			int index = 1;
			for (BackStorageSnapshot tempBean : ist.getBackStorageSnapshots()) {
				Map<String, String> mapLine = new HashMap<String, String>();
				mapLine.put("c1", String.valueOf(index));
				mapLine.put("c2", null == tempBean.getArticleName() ? "" : tempBean.getArticleName());
				mapLine.put("c3", null == tempBean.getArticleFeature() ? "" : tempBean.getArticleFeature());
				mapLine.put("c4", null==tempBean.getArticleCode()?"":tempBean.getArticleCode());
				mapLine.put("c5", null == tempBean.getNumberRequested() ? "" : tempBean.getNumberRequested());
				mapLine.put("c6",String.valueOf((null == tempBean.getOutComingNum() ? "" : tempBean.getOutComingNum())));				
				mapLine.put("c7", String.valueOf((null ==tempBean.getThisIncomingNum()?"":tempBean.getThisIncomingNum())));
				mapLine.put("c8", null == tempBean.getMeasurementUnit() ? "" : tempBean.getMeasurementUnit());
				mapLine.put("c9", null == tempBean.getSuspectName() ? "" : tempBean.getSuspectName());
				mapLine.put("c10", tempBean.getSuspectIdCardNo() == null?"":tempBean.getSuspectIdCardNo());
				mapLine.put("c11", null == tempBean.getIfInStorageAll() ? "" : tempBean.getIfInStorageAll() );
				listMapT1.add(mapLine);
				index ++;
			}
		}
		map.put("t1", listMapT1);
		
		ReportConfig rc = poiCrReportBuilder.createFromXmlFile(map, this.getRequest().getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "printAbout" + File.separator + "printXml" + File.separator + "backStorageFormRecord.xml");
		PoiCrReport report = new PoiCrReport(rc);
		poiCrReportBuilder.build(report) ;
		InputStream is = report.generateReportInputStream() ;
		try {
			exportInfoReq.setLength(Long.valueOf(String.valueOf(is.available())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		exportInfoReq.setIn(is);
		exportInfoReq.setName("涉案物品返还单" + istForm.getCode() + ".docx");
		this.creatCaseManagementLog("涉案物品管理 /新增涉案物品再入库", "打印涉案物品再入库单(保管区id:"+backStorageFormId+")");
		return "done" ;
	}
	
	/**
	 * 返还单列表导出EXCEL
	 * 
	 * @param
	 * @return
	 */
	public String exportExcel() throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		this.conditionMap();
		Pager<BackStorageForm> pager = backStorageFormService.queryByCondition(conditionMap, 0, Integer.MAX_VALUE);
		int i = 1;
		for (BackStorageForm bsf : pager.getPageList()) {
			BackStorageFormServiceBean bsfsb = sawpModelBeanTransformUtil.backStorageFormToBackStorageFormServiceBean(bsf);
			BackStorageFormExcelBean bsfeb = new BackStorageFormExcelBean();
			bsfeb.setCount(i + "");
			bsfeb.setCode(bsfsb.getCode());
			bsfeb.setUpdatedTime(sdf.format(new Date(bsfsb.getUpdatedTime())));
			bsfeb.setOutStorageFormCode(bsfsb.getOutStorageFormCode());
			bsfeb.setCaseCode(bsfsb.getCaseCode());
			bsfeb.setCaseName(bsfsb.getCaseName());
			bsfeb.setPolices(bsfsb.getPolices());
			bsfeb.setTypeName(bsfsb.getTypeName());
			bsfeb.setRemark(bsfsb.getRemark());
			i++;
			backStorageFormExcelBeanList.add(bsfeb);
		}
		
		ExcelUtil<BackStorageFormExcelBean> ex = new ExcelUtil<BackStorageFormExcelBean>();
		String[] headers = { "序号", "再入库单编号", "再入库时间", "对应的借出或其他类型的出库单", "案件编号", "案件名称",
				"办案民警", "出库类型", "备注" };
		SimpleDateFormat sdfExcel = new SimpleDateFormat("yyyyMMddHHmmss");
		String name = sdfExcel.format(new Date());
		name = name + ".xls";
		
		// 创建ByteArrayOutputStream字节流
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		ex.exportExcel(headers, backStorageFormExcelBeanList, bos);
		byte[] bytes = bos.toByteArray();
		bos.close();
		ByteArrayInputStream ins=new ByteArrayInputStream(bytes);
		exportInfoReq.setIn(ins);
		exportInfoReq.setName(name);
		exportInfoReq.setLength(Long.valueOf(bytes.length));
		ins.close();
		String query="";
		if(backStorageFormServiceBean.getOperator()==null){
			query="否";
		}else{
			query ="是";
		}
		this.creatCaseManagementLog("涉案物品管理 /涉案物品再入库", "导出查出的再入库单,查询条件(再入库单编号:"+backStorageFormServiceBean.getCode()+"对应案件编号："+backStorageFormServiceBean.getCaseCode()+"对应的借出或其他类型的出库单:"+backStorageFormServiceBean.getOutStorageFormCode()+"出库类型:"+backStorageFormServiceBean.getType()+"备注:"+backStorageFormServiceBean.getRemark()+"入库时间:"+startTime == null?null:DateFmtUtil.longToDate(startTime)+"--"+startTime == null?null:DateFmtUtil.longToDate(endTime)+"只显示我的入库单:"+query+")");
		return "done";
	}
	
	/**
	 * 装配查询条件map
	 */
	private void conditionMap(){
		conditionMap.put("backStorageFormCode", backStorageFormServiceBean.getCode());
		conditionMap.put("backTimeStart", startTime == null?null:DateFmtUtil.longToDate(startTime));
		conditionMap.put("backTimeEnd", endTime == null?null:DateFmtUtil.longToDate(endTime));
		conditionMap.put("outStorageFormCode", backStorageFormServiceBean.getOutStorageFormCode());
		conditionMap.put("caseCode", backStorageFormServiceBean.getCaseCode());
		conditionMap.put("remark", backStorageFormServiceBean.getRemark());
		conditionMap.put("type", backStorageFormServiceBean.getType());
		conditionMap.put("loginUser", backStorageFormServiceBean.getOperator());
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<String> getStorageOutTypeList() {
		return storageOutTypeList;
	}

	public void setStorageOutTypeList(List<String> storageOutTypeList) {
		this.storageOutTypeList = storageOutTypeList;
	}

	public String getOutStorageFormId() {
		return outStorageFormId;
	}

	public void setOutStorageFormId(String outStorageFormId) {
		this.outStorageFormId = outStorageFormId;
	}

	public OutStorageFormServiceBean getOutStorageFormServiceBean() {
		return outStorageFormServiceBean;
	}

	public void setOutStorageFormServiceBean(OutStorageFormServiceBean outStorageFormServiceBean) {
		this.outStorageFormServiceBean = outStorageFormServiceBean;
	}

	public List<OutStorageFormItemServiceBean> getOutStorageFormItemServiceBeanList() {
		return outStorageFormItemServiceBeanList;
	}

	public void setOutStorageFormItemServiceBeanList(
			List<OutStorageFormItemServiceBean> outStorageFormItemServiceBeanList) {
		this.outStorageFormItemServiceBeanList = outStorageFormItemServiceBeanList;
	}

	public BackStorageFormServiceBean getBackStorageFormServiceBean() {
		return backStorageFormServiceBean;
	}

	public void setBackStorageFormServiceBean(BackStorageFormServiceBean backStorageFormServiceBean) {
		this.backStorageFormServiceBean = backStorageFormServiceBean;
	}

	public List<BackStorageFormItemServiceBean> getBackStorageFormItemServiceBeanList() {
		return backStorageFormItemServiceBeanList;
	}

	public void setBackStorageFormItemServiceBeanList(
			List<BackStorageFormItemServiceBean> backStorageFormItemServiceBeanList) {
		this.backStorageFormItemServiceBeanList = backStorageFormItemServiceBeanList;
	}

	public Map<String, Object> getConditionMap() {
		return conditionMap;
	}

	public void setConditionMap(Map<String, Object> conditionMap) {
		this.conditionMap = conditionMap;
	}

	public List<BackStorageFormServiceBean> getBackStorageFormServiceBeanList() {
		return backStorageFormServiceBeanList;
	}

	public void setBackStorageFormServiceBeanList(List<BackStorageFormServiceBean> backStorageFormServiceBeanList) {
		this.backStorageFormServiceBeanList = backStorageFormServiceBeanList;
	}

	public String getBackStorageFormId() {
		return backStorageFormId;
	}

	public void setBackStorageFormId(String backStorageFormId) {
		this.backStorageFormId = backStorageFormId;
	}

	public List<String> getBackStorageFormIdList() {
		return backStorageFormIdList;
	}

	public void setBackStorageFormIdList(List<String> backStorageFormIdList) {
		this.backStorageFormIdList = backStorageFormIdList;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public List<BackStorageFormExcelBean> getBackStorageFormExcelBeanList() {
		return backStorageFormExcelBeanList;
	}

	public void setBackStorageFormExcelBeanList(List<BackStorageFormExcelBean> backStorageFormExcelBeanList) {
		this.backStorageFormExcelBeanList = backStorageFormExcelBeanList;
	}

	public ExportInfoReq getExportInfoReq() {
		return exportInfoReq;
	}

	public void setExportInfoReq(ExportInfoReq exportInfoReq) {
		this.exportInfoReq = exportInfoReq;
	}

	public List<OptionItemBean> getOutStorageFormIdAndCodeList() {
		return outStorageFormIdAndCodeList;
	}

	public void setOutStorageFormIdAndCodeList(List<OptionItemBean> outStorageFormIdAndCodeList) {
		this.outStorageFormIdAndCodeList = outStorageFormIdAndCodeList;
	}

	public List<ArticleLocker> getArticleLockerList() {
		return articleLockerList;
	}

	public void setArticleLockerList(List<ArticleLocker> articleLockerList) {
		this.articleLockerList = articleLockerList;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getStorageAreaId() {
		return storageAreaId;
	}

	public void setStorageAreaId(String storageAreaId) {
		this.storageAreaId = storageAreaId;
	}

	public InComingSnapshootBean getInComingSnapshootBean() {
		return inComingSnapshootBean;
	}

	public void setInComingSnapshootBean(InComingSnapshootBean inComingSnapshootBean) {
		this.inComingSnapshootBean = inComingSnapshootBean;
	}

	public List<BackStorageSnapshotBean> getBackstorageLst() {
		return backstorageLst;
	}

	public void setBackstorageLst(List<BackStorageSnapshotBean> backstorageLst) {
		this.backstorageLst = backstorageLst;
	}


}
