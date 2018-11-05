package com.taiji.pubsec.ajqlc.sawp.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajgl.util.OperationRecordConstant;
import com.taiji.pubsec.ajgl.util.ReturnMessageAction;
import com.taiji.pubsec.ajgl.util.bean.OptionItemBean;
import com.taiji.pubsec.ajqlc.baq.action.HandlingAreaSupervisionAction;
import com.taiji.pubsec.ajqlc.operationrecord.model.OperationRecord;
import com.taiji.pubsec.ajqlc.operationrecord.service.IOperationRecordService;
import com.taiji.pubsec.ajqlc.sawp.action.bean.InComingSnapshootBean;
import com.taiji.pubsec.ajqlc.sawp.action.bean.InStorageFormItemBean;
import com.taiji.pubsec.ajqlc.sawp.action.bean.InStorageSnapshotBean;
import com.taiji.pubsec.ajqlc.sawp.action.bean.InvolvedArticleBean;
import com.taiji.pubsec.ajqlc.sawp.action.bean.StorageInBean;
import com.taiji.pubsec.ajqlc.sawp.action.bean.StorageInExcelBean;
import com.taiji.pubsec.ajqlc.sawp.action.util.SawpModelBeanTransformUtil;
import com.taiji.pubsec.ajqlc.sawp.action.util.StorageInAddUtil;
import com.taiji.pubsec.ajqlc.sawp.bean.CriminalBasicCaseBean;
import com.taiji.pubsec.ajqlc.sawp.bean.DocBean;
import com.taiji.pubsec.ajqlc.sawp.bean.InStorageFormItemServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.InStorageFormServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.StorageServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.SuspectPersonBean;
import com.taiji.pubsec.ajqlc.sawp.model.ArticleLocker;
import com.taiji.pubsec.ajqlc.sawp.model.ImpoundedObject;
import com.taiji.pubsec.ajqlc.sawp.model.InStorageForm;
import com.taiji.pubsec.ajqlc.sawp.model.InStorageFormItem;
import com.taiji.pubsec.ajqlc.sawp.model.InStorageRecord;
import com.taiji.pubsec.ajqlc.sawp.model.InStorageSnapshot;
import com.taiji.pubsec.ajqlc.sawp.model.IncomingSnapshot;
import com.taiji.pubsec.ajqlc.sawp.model.InvolvedArticle;
import com.taiji.pubsec.ajqlc.sawp.model.StorageArea;
import com.taiji.pubsec.ajqlc.sawp.service.IArticleLockerService;
import com.taiji.pubsec.ajqlc.sawp.service.IImpoundedObjectService;
import com.taiji.pubsec.ajqlc.sawp.service.IInStorageFormService;
import com.taiji.pubsec.ajqlc.sawp.service.IIncomingSnapshotService;
import com.taiji.pubsec.ajqlc.sawp.service.IInvolvedArticleService;
import com.taiji.pubsec.ajqlc.sawp.service.IStorageAreaService;
import com.taiji.pubsec.ajqlc.sawp.service.IStorageService;
import com.taiji.pubsec.ajqlc.util.Constant;
import com.taiji.pubsec.ajqlc.util.DateFmtUtil;
import com.taiji.pubsec.ajqlc.util.ExcelUtil;
import com.taiji.pubsec.businesscomponent.dictionary.model.DictionaryItem;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryItemService;
import com.taiji.pubsec.common.tool.spring.BeanCopierFmtUtil;
import com.taiji.pubsec.common.tools.doc.core.model.ReportConfig;
import com.taiji.pubsec.common.tools.doc.msoffice.impl.poicr.builder.PoiCrReportBuilder;
import com.taiji.pubsec.common.tools.doc.msoffice.impl.poicr.reporter.PoiCrReport;
import com.taiji.pubsec.common.web.action.ExportInfoReq;
import com.taiji.pubsec.springsecurity.resource.util.MySecureUser;
import com.taiji.pubsec.springsecurity.resource.util.SessionUserDetailsUtil;

/**
 * 涉案物品--入库单
 * 
 * @author XIEHF
 *
 */
@Controller("storageInAction")
@Scope("prototype")
public class StorageInAction extends ReturnMessageAction {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(HandlingAreaSupervisionAction.class);
	@Resource
	private PoiCrReportBuilder poiCrReportBuilder;
	@Resource
	private IInStorageFormService inStorageFormService;// 入库单接口
	@Resource
	private IStorageAreaService storageAreaService;// 涉案物品管理区接口
	@Resource
	private IArticleLockerService articleLockerService;// 储物箱接口
	@Resource
	private IStorageService storageService;// 保管位置接口
	@Resource
	private IDictionaryItemService dictionaryItemService; // 数据字典接口
	@Resource
	private IImpoundedObjectService impoundedObjectService;// 扣押物品接口
	
	@Resource
	private IInvolvedArticleService involvedArticleService;// 涉案物品接口

	@Resource
	private IOperationRecordService operationRecordService;// 操作记录接口

	@Resource
	private SawpModelBeanTransformUtil sawpModelBeanTransformUtil;// model和bean互转类

	@Resource
	private IIncomingSnapshotService incomingSnapshotService;// 入库打印的快照

	private List<InStorageFormItemServiceBean> isfisBeanList = new ArrayList<InStorageFormItemServiceBean>();// 入库单项Bean集合
	private List<InStorageFormItemServiceBean> oldIsfisBeanList = new ArrayList<InStorageFormItemServiceBean>();// 入库单项Bean集合
	private List<ArticleLocker> articleLockerList = new ArrayList<ArticleLocker>(); // 储物箱List
	private List<OptionItemBean> paperBeanLst = new ArrayList<OptionItemBean>();// 临时组装嫌疑人信息。
	private List<OptionItemBean> suspectBeanLst = new ArrayList<OptionItemBean>();// 临时组装嫌疑人信息。
	private List<OptionItemBean> optionItemBeanLst = new ArrayList<OptionItemBean>();// 临时组装案件列表。
	private List<StorageInBean> storageInBeanList = new ArrayList<StorageInBean>(); // 入库单BeanList
	private List<StorageInExcelBean> storageInExcelBeanList = new ArrayList<StorageInExcelBean>(); // 入库单导出BeanList
	private StorageInBean storageInBean; // 入库单Bean
	private StorageInExcelBean storageInExcelBean; // 入库单导出Bean
	private String sf; // 是否显示我的入库单 1：是 0：否
	private String startTime; // 入库时间 （查询区域开始时间）
	private String endTime; // 入库时间 （查询区域结束时间）
	private String pageFlag;
	private String code; // 入库单编号
	private String storageAreaId; // 保管区ID
	private String inStorageFormId; // 入库单ID
	private String operator; // 操作人员姓名
	private String suspectName; // 嫌疑人姓名
	private String suspectId; // 嫌疑人Id
	private String caseCode; // 案件编号
	private String caseName; // 案件名称
	private String remark; // 备注
	private String papers; // 文书
	private String articleCode;// 物品编号
	private InvolvedArticleBean involvedArticleBean;// 涉案物品Bean

	private InStorageForm form = new InStorageForm(); // 入库单
	private InStorageFormServiceBean inStorageFormBean; // 入库单
	private List<StorageArea> storageAreaList = new ArrayList<StorageArea>(); // 涉案物品保管区List

	private List<InStorageFormItemBean> itemBeanlist = new ArrayList<InStorageFormItemBean>(); // 入库单项Bean list
	private List<InStorageFormServiceBean> formBeanlist = new ArrayList<InStorageFormServiceBean>(); // 入库单Bean list
	private List<String> formIdList = new ArrayList<String>(); // 入库单id list
	private List<DocBean> paperList = new ArrayList<DocBean>(); // 文书 list
	private String path;
	private String id;
	private ExportInfoReq exportInfoReq = new ExportInfoReq();
	
	private InComingSnapshootBean inComingSnapshootBean=new InComingSnapshootBean(); //入库单快照
	private List<InStorageSnapshotBean> instorageLst=new ArrayList<InStorageSnapshotBean>();//list 集合

	private String policeNumber1;// 办案民警1
	private String policeNumber2;// 办案民警2
	
	/**
	 * 根据物品编号查询物品
	 * 
	 * @return
	 */
	public String findArticleByCode() {
		InvolvedArticle involvedArticle = involvedArticleService.findInvolvedArticleByCode(articleCode);
		involvedArticleBean = sawpModelBeanTransformUtil.involvedArticleToInvolvedArticleBean(involvedArticle);
		if(involvedArticleBean == null){
			ImpoundedObject io = impoundedObjectService.findImpoundedObjectByCode(articleCode);
			if(io == null){
				return SUCCESS;
			}
			involvedArticleBean = new InvolvedArticleBean();
			involvedArticleBean.setCode(io.getCode());
			involvedArticleBean.setName(io.getName());
			involvedArticleBean.setFeature(io.getFeature());
		}
		return SUCCESS;
	}

	/**
	 * 查询入库单列表
	 * 
	 * @return SUCCESS
	 */
	public String queryStorageInRecord() {
		storageInBeanList = new ArrayList<StorageInBean>();
		Map<String, Object> map = searchMap();

		Pager<InStorageForm> pager = inStorageFormService.queryByCondition(map, this.getStart() / this.getLength(),
				this.getLength());
		if (pager == null) {
			return SUCCESS;
		}
		this.setTotalNum(pager.getTotalNum());
		for (InStorageForm form : pager.getPageList()) {
			InStorageFormServiceBean bean = inStorageFormService.inStorageFormToBean(form);
			// 查询入库单对应文书列表
			bean.setDocs(impoundedObjectService.findArchivedFilesByCaseIdAndSuspectId(form.getCaseCode(),
					form.getSuspectId()));
			formBeanlist.add(bean);
		}
		return SUCCESS;
	}

	/**
	 * 查询案件相关信息
	 * 
	 * @return SUCCESS
	 */
	public String queryCaseInfo() {
		CriminalBasicCaseBean cbcBean = impoundedObjectService.findCriminalBasicCaseBeanForInStorageByCaseId(caseCode);
		if (cbcBean == null) {
			return SUCCESS;
		}
		// 案件名称
		caseName = cbcBean.getCaseName();
		// 办案民警
		operator = cbcBean.getHandlePolices();
		// 办案民警1警号
		policeNumber1 = cbcBean.getPoliceNumber1();
		// 办案民警2警号
		policeNumber2 = cbcBean.getPoliceNumber2();
		// 嫌疑人信息
		for (SuspectPersonBean spBean : cbcBean.getSuspectPersons()) {
			OptionItemBean oiBean = new OptionItemBean();
			oiBean.setId(spBean.getSuspectId());
			if(spBean.getSuspectName() == null){
				oiBean.setName("");
			}else{
				oiBean.setName(spBean.getSuspectName());
			}
			
			suspectBeanLst.add(oiBean);
		}
		return SUCCESS;
	}

	/**
	 * 根据id查询入库单
	 * 
	 * @return
	 */
	public String findInStorageFormById() {
		List<InStorageFormItem> items = inStorageFormService.findInStorageFormItemsByFormId(id);
		if (items == null) {
			return SUCCESS;
		}
		for (InStorageFormItem ii : items) {
			// 入库单项Model转Bean并添加
			InStorageFormItemServiceBean isfisb = inStorageFormItemToInStorageFormItemServiceBean(ii);
			if (isfisb != null) {
				isfisBeanList.add(isfisb);
			}
		}
		return SUCCESS;
	}

	/**
	 * 根据案件编号和嫌疑人查询扣押物品
	 * 
	 * @return
	 */
	public String findImpoundedObjectByCaseCodeAndSuspect() {
		InStorageForm form = inStorageFormService.findInStorageFormByCaseCodeAndSuspectId(caseCode, suspectId);
		List<ImpoundedObject> impoundedObjectList = impoundedObjectService
				.findImpodundedObjectsByCaseIdAndSuspectId(caseCode, suspectId);
		if (form == null) {// 无入库单，直接展示所有扣押物品
			if (impoundedObjectList == null || impoundedObjectList.isEmpty()) {
				return SUCCESS;
			}
			// 循环将扣押物品转换成入库单项数据
			for (ImpoundedObject io : impoundedObjectList) {
				InStorageFormItemServiceBean isfisb = impoundedObjectToInStorageFormItemBean(io);
				if (isfisb == null) {
					continue;
				}
				isfisBeanList.add(isfisb);
			}
		} else {// 已有入库单，用入库单项过滤掉扣押物品中相同的物品
			id = form.getId();
			code = form.getCode();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			startTime = sdf.format(form.getInStorageTime());
			remark = form.getRemark();
			for (ImpoundedObject io : impoundedObjectList) {
				List<InStorageFormItem> items = inStorageFormService.findInStorageFormItemsByFormId(form.getId());
				boolean flag = false;
				for (InStorageFormItem ii : items) {
					if (io.getCode().equals(ii.getArticle().getCode())) {
						// 入库单项Model转Bean并添加
						InStorageFormItemServiceBean isfisb = inStorageFormItemToInStorageFormItemServiceBean(ii);
						if (isfisb != null) {
							isfisBeanList.add(isfisb);
						}
						flag = true;
					}
				}
				if (flag) {
					continue;
				}
				// 当前循环扣押物品没有入库过，转换后添加
				InStorageFormItemServiceBean isfisb = impoundedObjectToInStorageFormItemBean(io);
				if (isfisb != null) {
					isfisBeanList.add(isfisb);
				}
			}
		}
		this.creatCaseManagementLog("涉案物品管理 /新增涉案物品入库单", "根据案件编号和嫌疑人查询扣押物品(案件编号:"+caseCode+"嫌疑人id:"+suspectId+")");
		return SUCCESS;
	}

	/**
	 * 入库单项Model转Bean
	 * 
	 * @param isfi
	 *            入库单Model
	 * @return isfisb 入库单Bean
	 */
	public InStorageFormItemServiceBean inStorageFormItemToInStorageFormItemServiceBean(InStorageFormItem isfi) {
		if (isfi == null) {
			return null;
		}
		InStorageFormItemServiceBean isfisb = new InStorageFormItemServiceBean();
		isfisb.setId(isfi.getId());

		// 设置涉案物品信息
		InvolvedArticle ia = isfi.getArticle();
		if (ia != null) {
			isfisb.setArticleId(ia.getId());
			isfisb.setCaseCode(ia.getCaseCode());
			isfisb.setCaseName(ia.getCaseName());
			isfisb.setArticleCode(ia.getCode());
			isfisb.setDetentionNumber(ia.getDetentionNumber());
			isfisb.setArticleFeature(ia.getFeature());
			isfisb.setArticleName(ia.getName());
			isfisb.setPaperId(ia.getPaperId());
			isfisb.setPaperType(ia.getPaperType());
			isfisb.setPaperName(ia.getPaper());
			isfisb.setPolices(ia.getPolices());
			isfisb.setSuspectName(ia.getSuspectName());
			isfisb.setSuspectIdentifier(ia.getSuspectIdentityNumber());
			isfisb.setMeasurementUnit(ia.getMeasurementUnit());
			isfisb.setArticleType(ia.getType());
			isfisb.setArticleTypeName(sawpModelBeanTransformUtil.articleTypeIdToName(ia.getType()));
		}
		isfisb.setNumberIncome(isfi.getNumberIncome());
		isfisb.setNumberRequested(isfi.getNumberRequested());
		isfisb.setFormId(isfi.getForm().getId());
		isfisb.setInStorageTime(DateFmtUtil.dateToLong(isfi.getUpdatedTime()));
		isfisb.setRemark(isfi.getRemark());
		isfisb.setUpdatedTime(DateFmtUtil.dateToLong(isfi.getUpdatedTime()));

		// 位置转换
		List<InStorageRecord> inStorageRecordList = isfi.getInStorageRecords();
		List<StorageServiceBean> ssbList = new ArrayList<StorageServiceBean>();
		isfisb.setStorageServiceBeans(ssbList);

		String areaId = null;
		if (inStorageRecordList != null && !inStorageRecordList.isEmpty()) {
			for (InStorageRecord isr : inStorageRecordList) {
				StorageServiceBean ssb = sawpModelBeanTransformUtil.inStorageRecordToStorageServiceBean(isr);
				if (ssb != null) {
					areaId = ssb.getAreaId();
					ssbList.add(ssb);
				}
			}
		}

		// 查询可用的全部储物箱信息，在更新入库单时使用
		if (areaId != null && ia != null) {
			List<ArticleLocker> alList = articleLockerService.findLockersByCaseCodeAndAreaId(ia.getCaseCode(), areaId);
			isfisb.setArticleLockerList(alList);
		}
		return isfisb;
	}

	/**
	 * 扣押物品Model转入库单项Bean
	 * 
	 * @param io
	 *            扣押物品Model
	 * @return isfisb 入库单项Bean
	 */
	public InStorageFormItemServiceBean impoundedObjectToInStorageFormItemBean(ImpoundedObject io) {
		if (io == null) {
			return null;
		}
		InStorageFormItemServiceBean isfisb = new InStorageFormItemServiceBean();
		isfisb.setCaseCode(io.getCaseId());
		isfisb.setCaseName(io.getCaseName());
		isfisb.setArticleCode(io.getCode());
		isfisb.setDetentionNumber(io.getNumber());
		
		String measurement = StorageInAddUtil.measurementTransition(io.getNumber());
		isfisb.setMeasurementUnit(measurement);
		isfisb.setNumberRequested(StorageInAddUtil.numTransition(io.getNumber().replace(measurement, "")));
		
		isfisb.setArticleFeature(io.getFeature());
		isfisb.setArticleName(io.getName());
		isfisb.setPaperId(io.getPaper());
		isfisb.setPaperType(io.getPaperType());
		isfisb.setPaperName(io.getPaperName());
		isfisb.setPolices(io.getPolice());
		isfisb.setSuspectId(io.getSuspectId());
		isfisb.setSuspectName(io.getSuspectName());
		isfisb.setSuspectIdentifier(io.getSuspectIDCardNo());
		return isfisb;
	}

	@SuppressWarnings("unused")
	private String findDictinaryItemNameByCodes(String[] codeArr) {
		StringBuffer dictionaryItemNames = new StringBuffer("");
		if (null != codeArr && codeArr.length > 0) {
			for (String code : codeArr) {
				DictionaryItem item = dictionaryItemService.findById(code);
				dictionaryItemNames.append(null == item ? "" : item.getName() + ",");
			}
			dictionaryItemNames.substring(0, dictionaryItemNames.length() - 1);
		}
		return (dictionaryItemNames.length() <= 0 ? ""
				: dictionaryItemNames.substring(0, dictionaryItemNames.length() - 1));
	}

	/**
	 * 获取本单位启用状态的物证保管区列表。
	 * 
	 * @return SUCCESS
	 */
	public String queryStorageAreaInfo() {
		storageAreaList = new ArrayList<StorageArea>();
		storageAreaList = storageAreaService.findStorageAreasByStatus(this.findCurrentOrganization().getId(),
				Constant.ZT_QY);
		return SUCCESS;
	}

	/**
	 * 保存（更新）入库单
	 * 
	 * @return SUCCESS
	 */
	public String saveStorageInfo() {
		if (inStorageFormBean.getId().isEmpty()) {
			try {
				inStorageFormBean.setCreatedTimeStr(
						dateToStr(longDateToDate(Long.valueOf(inStorageFormBean.getCreatedTime()), null), null));
				id = inStorageFormService.createForm(inStorageFormBean, isfisBeanList);
				this.creatCaseManagementLog("涉案物品管理 /新增涉案物品入库单", "新增入库单(新增入库单id:"+id+")");
			} catch (ParseException e) {
				this.creatCaseManagementLog("涉案物品管理 /新增涉案物品入库单", "新增入库单失败");
				logger.debug("生成入库单出错。");
			}
		} else {
			try {
				inStorageFormBean.setCreatedTimeStr(
						dateToStr(longDateToDate(Long.valueOf(inStorageFormBean.getCreatedTime()), null), null));
				inStorageFormService.updateForm(inStorageFormBean, isfisBeanList);
				id = inStorageFormBean.getId();
				this.creatCaseManagementLog("涉案物品管理 /新增涉案物品入库单", "更新入库单(更新入库单id:"+id+")");
			} catch (ParseException e) {
				logger.debug("更新入库单出错。");
				this.creatCaseManagementLog("涉案物品管理 /新增涉案物品入库单", "更新入库单失败");
			}
		}
		inComingSnapshootBean.setFormId(id);
		incomingSnapshotService.saveInStorageSnapshots(changeModel(inComingSnapshootBean), changeModel(instorageLst));

		// 创建操作记录
		for (InStorageFormItemServiceBean isfisb : isfisBeanList) {
			OperationRecord or = new OperationRecord();
			or.setArticleCode(isfisb.getArticleCode());
			or.setFormCode(inStorageFormBean.getCode());
			or.setFormId(id);
			or.setFormType(InStorageForm.class.getName());
			List<StorageServiceBean> ssbList = isfisb.getStorageServiceBeans();
			if (ssbList != null && !ssbList.isEmpty()) {
				int incomingNumberCount = 0;
				for (StorageServiceBean ssb : ssbList) {
					incomingNumberCount += ssb.getIncomingNumber();
				}
				or.setNumber(String.valueOf(incomingNumberCount));
			}
			or.setOperation(OperationRecordConstant.WP_CZJL_RK);
			or.setOperationTime(new Date());
			or.setOperator(this.findCurrentPerson().getName());
			operationRecordService.saveOperationRecord(or);
		}
		return SUCCESS;
	}

	/**
	 * 新增入库单页面数据初始化
	 * 
	 * @return
	 */
	public String initData() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		code = inStorageFormService.acquireNum();
		startTime = sdf.format(new Date());
		operator = this.findCurrentPerson().getId();
		return SUCCESS;
	}

	/**
	 * 入库单查看页面数据初始化
	 * 
	 * @return
	 */
	public String viewStorageIn() {
		// 查询入库单
		form = inStorageFormService.findById(inStorageFormId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		inStorageFormBean = new InStorageFormServiceBean();
		inStorageFormBean.setId(form.getId());
		inStorageFormBean.setCode(form.getCode());
		inStorageFormBean.setCreatedTime(DateFmtUtil.dateToLong(form.getCreatedTime()));
//		inStorageFormBean.setCreatedTimeStr(sdf.format(form.getCreatedTime()));
		inStorageFormBean.setCreatedTimeStr(sdf.format(form.getInStorageTime()));
		inStorageFormBean.setRemark(form.getRemark());
		inStorageFormBean.setCaseCode(form.getCaseCode());
		inStorageFormBean.setCaseName(form.getCaseName());
		inStorageFormBean.setCaseHandler(form.getHandlePolices());
		inStorageFormBean.setPapers(form.getPapers());
		inStorageFormBean.setSuspectId(form.getSuspectId());
		inStorageFormBean.setSuspectName(form.getSuspectName());
		this.creatCaseManagementLog("涉案物品管理 /涉案物品入库", "入库单页面查看(入库单id:"+inStorageFormId+")");
		return SUCCESS;
	}

	/**
	 * 批量删除入库单
	 * 
	 * @return
	 */
	public String deleteByIds() {
		if (null != formIdList && formIdList.size() > 0) {
			String str="";
			for (String id : formIdList) {
				inStorageFormService.deleteFormById(id);
				str+=id+",";
			}
			str.substring(0,str.length()-1);
			this.creatCaseManagementLog("涉案物品管理 /涉案物品入库", "删除涉案物品入库单(涉案物品入库:"+str+")");
		}
		return SUCCESS;
	}

	/**
	 * 打印入库单
	 * 
	 * @return
	 */
	public String printStorageInById() {
		//入库单基本信息 inStorageFormBean
		//遍历入库物品对象（物品中存储位置中的incomingNumber已经是差值，直接累加就获得此物品本次入库数量）
//		if(isfisBeanList != null){
//			for(int i = isfisBeanList.size()-1; i >= 0; i--){
//				List<StorageServiceBean> ssBeanLst = isfisBeanList.get(i).getStorageServiceBeans();
//				int count = 0;
//				if(ssBeanLst != null){
//					for(StorageServiceBean ssb : ssBeanLst){
//						count += ssb.getIncomingNumber();
//					}
//				}
//				//添加，returnedNumber记录本次入库数量
//				isfisBeanList.get(i).setAccumulateSum(String.valueOf(count));
//			}
//		}
//		
//		Map<String, Object> map = new HashMap<String, Object>();
//		Map<String, String> mapN1 = new HashMap<String, String>();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//		mapN1.put("n0", sdf.format(new Date()));
//		mapN1.put("n1", inStorageFormBean.getCode() == null ? "" : inStorageFormBean.getCode());
//		mapN1.put("n2", null == inStorageFormBean.getCreatedTime() ? "" : sdf.format(new Date(inStorageFormBean.getCreatedTime())));
//		mapN1.put("n3", inStorageFormBean.getCaseCode() == null ? "" : inStorageFormBean.getCaseCode());
//		CriminalBasicCaseBean cbcBean = impoundedObjectService
//				.findCriminalBasicCaseBeanForInStorageByCaseId(inStorageFormBean.getCaseCode());
//		mapN1.put("n4", (cbcBean.getCaseName()) == null ? "" : (cbcBean.getCaseName()));
//		mapN1.put("n5", (cbcBean.getHandlePolices()) == null ? "" : (cbcBean.getHandlePolices()));
//		mapN1.put("n6", inStorageFormBean.getSuspectName() == null ? "" : inStorageFormBean.getSuspectName());
//		mapN1.put("n7", null == inStorageFormBean.getPapers() ? "" : inStorageFormBean.getPapers());
//		mapN1.put("n8", null == inStorageFormBean.getRemark() ? "" : inStorageFormBean.getRemark());
//		mapN1.put("n9", this.findCurrentPerson().getName());
//	
//		map.put("n1", mapN1);
//
//		List<InStorageFormItemBean> itemBeanList = new ArrayList<InStorageFormItemBean>();
//		List<InStorageFormItem> items = inStorageFormService.findInStorageFormItemsByFormId(id);
//		InStorageFormItemBean itemBeanTemp = new InStorageFormItemBean();
//		for (InStorageFormItem item : items) {
//			InStorageFormItemBean itemBean = new InStorageFormItemBean();
//			InStorageFormItemServiceBean iStorageFormItemBean = inStorageFormService.inStorageFormItemToBean(item);
//			// 处理同一涉案物品的放在了多个位置上
//			if (null == itemBeanTemp.getId() || !itemBeanTemp.getId().equals(iStorageFormItemBean.getArticleId())) {
//				itemBeanTemp.setId(iStorageFormItemBean.getArticleId());
//				BeanCopierFmtUtil.copyBean(iStorageFormItemBean, itemBean, null);
//				itemBean.setId(iStorageFormItemBean.getArticleId());
//				List<StorageBean> storageBeanList = new ArrayList<StorageBean>();
//				List<Storage> storages = storageService
//						.findStoragesByInvolvedArticleCode(iStorageFormItemBean.getArticleCode());
//				if (null != storages && storages.size() > 0) {
//					for (Storage entity : storages) {
//						storageBeanList.add(sawpModelBeanTransformUtil.storageToStorageBean(entity));
//					}
//				}
//				itemBean.setPaperName(iStorageFormItemBean.getPaperName());
//				itemBean.setStorageBeans(storageBeanList);
//				itemBean.setFeature(iStorageFormItemBean.getArticleFeature());
//				itemBean.setTypeName(findDictinaryItemNameByCodes(iStorageFormItemBean.getArticleType().split(",")));
//				itemBean.setRemark(item.getArticle().getRemark());
//				itemBeanList.add(itemBean);
//			}
//		}
//
//		List<Map<String, String>> mapT1 = new ArrayList<Map<String, String>>();
//		if (isfisBeanList.size() <= 0) {
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
//			mapT1.add(mapLine);
//		} else {
//			int index = 1;
//			for (InStorageFormItemServiceBean tempBean : isfisBeanList) {
//				Map<String, String> mapLine = new HashMap<String, String>();
//				mapLine.put("c1", String.valueOf(index));
//				mapLine.put("c2", tempBean.getArticleName() == null ? "" : tempBean.getArticleName());
//				mapLine.put("c3", tempBean.getArticleFeature() == null ? "" : tempBean.getArticleFeature());
//				mapLine.put("c4", tempBean.getArticleCode() == null ? "" : tempBean.getArticleCode());
//				mapLine.put("c5", tempBean.getNumberRequested() + "");
//				mapLine.put("c6", tempBean.getArticleTypeName() == null ? "" : tempBean.getArticleTypeName());
//				mapLine.put("c7", tempBean.getAccumulateSum() + "");
//				mapLine.put("c8", tempBean.getMeasurementUnit());
//				mapLine.put("c9", tempBean.getPaperName());
//				mapT1.add(mapLine);
//				index++;
//			}
//		}
//		map.put("t1", mapT1);
//
//		ReportConfig rc = poiCrReportBuilder.createFromXmlFile(map,
//				this.getRequest().getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "printAbout"
//						+ File.separator + "printXml" + File.separator + "storageInRecord.xml");
//		PoiCrReport report = new PoiCrReport(rc);
//		poiCrReportBuilder.build(report);
//		InputStream is = report.generateReportInputStream();
//		try {
//			exportInfoReq.setLength(Long.valueOf(String.valueOf(is.available())));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		exportInfoReq.setIn(is);
//		exportInfoReq.setName("入库单" + inStorageFormBean.getCode() + ".docx");
//		return "done";
		InStorageForm istForm= inStorageFormService.findById(inStorageFormId);
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> mapN1 = new HashMap<String, String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		mapN1.put("n0", sdf.format(new Date()));
		mapN1.put("n1", istForm.getCode() == null ? "" : istForm.getCode());
		mapN1.put("n2", null == istForm.getInStorageTime() ? "" : sdf.format(istForm.getInStorageTime()));
		mapN1.put("n3", istForm.getCaseCode() == null ? "" : istForm.getCaseCode());
		mapN1.put("n4", (istForm.getCaseName()) == null ? "" : (istForm.getCaseName()));
		mapN1.put("n5", (istForm.getHandlePolices()) == null ? "" : (istForm.getHandlePolices()));
		mapN1.put("n6", istForm.getSuspectName() == null ? "" : istForm.getSuspectName());
		mapN1.put("n7", null == istForm.getPapers() ? "" : istForm.getPapers());
		mapN1.put("n8", null == istForm.getRemark() ? "" : istForm.getRemark());
		mapN1.put("n9", this.findCurrentPerson().getName());
	
		map.put("n1", mapN1);
		IncomingSnapshot ist= incomingSnapshotService.findLatestIncomingSnapshotByFormId(inStorageFormId);
		List<Map<String, String>> mapT1 = new ArrayList<Map<String, String>>();
		if (ist==null||ist.getInStorageSnapshots().size() <= 0) {
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
			mapT1.add(mapLine);
		} else {
			int index = 1;
			for (InStorageSnapshot tempBean : ist.getInStorageSnapshots()) {
				Map<String, String> mapLine = new HashMap<String, String>();
				mapLine.put("c1", String.valueOf(index));
				mapLine.put("c2", tempBean.getArticleName() == null ? "" : tempBean.getArticleName());
				
				mapLine.put("c3", tempBean.getArticleFeature() == null ? "" : tempBean.getArticleFeature());
				mapLine.put("c4", tempBean.getArticleCode() == null ? "" : tempBean.getArticleCode());
				mapLine.put("c5", tempBean.getNumberRequested()==null?"":tempBean.getNumberRequested()+"");
				mapLine.put("c6", tempBean.getArticleType() == null ? "" : tempBean.getArticleType());
				mapLine.put("c7", tempBean.getThisIncomingNum()==null?"":tempBean.getThisIncomingNum()+"");
				mapLine.put("c8", tempBean.getMeasurementUnit());
				mapLine.put("c9", tempBean.getPaper());
				mapT1.add(mapLine);
				index++;
			}
		}
		map.put("t1", mapT1);

		ReportConfig rc = poiCrReportBuilder.createFromXmlFile(map,
				this.getRequest().getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "printAbout"
						+ File.separator + "printXml" + File.separator + "storageInRecord.xml");
		PoiCrReport report = new PoiCrReport(rc);
		poiCrReportBuilder.build(report);
		InputStream is = report.generateReportInputStream();
		try {
			exportInfoReq.setLength(Long.valueOf(String.valueOf(is.available())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		exportInfoReq.setIn(is);
		exportInfoReq.setName("入库单" + istForm.getCode() + ".docx");
		return "done";
	}

	/**
	 * 组装条件查询信息
	 * 
	 * 
	 */
	public Map<String, Object> searchMapExcel() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("caseCode", caseCode);
		map.put("suspectName", suspectName);
		map.put("papers", papers);
		map.put("remark", remark);
		map.put("isShowMyForm", sf);
		map.put("loginUser", this.findCurrentPerson().getId());
		if (!StringUtils.isBlank(startTime) && !"null".equals(startTime)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			try {
				map.put("startTime", sdf.parse(startTime));
			} catch (ParseException e) {
				logger.error("时间转换出错：" + e);
			}
		}
		if (!StringUtils.isBlank(endTime) && !"null".equals(endTime)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			try {
				map.put("endTime", sdf.parse(endTime));
			} catch (ParseException e) {
				logger.error("时间转换出错：" + e);
			}
		}
		return map;
	}

	/**
	 * 入库单列表导出EXCEL
	 * 
	 * @param
	 * @return
	 */
	public String exportExcel() throws IOException {
		Map<String, Object> map = searchMapExcel();
		Pager<InStorageForm> pager = inStorageFormService.queryByCondition(map, 0, Integer.MAX_VALUE);
		int i = 1;
		if (null == pager.getPageList() || pager.getPageList().size() > 0) {
			for (InStorageForm form : pager.getPageList()) {
				StorageInExcelBean bean = storageInToExcelBean(form);
				bean.setCount(i + "");
				i++;
				storageInExcelBeanList.add(bean);
			}
		}

		ExcelUtil<StorageInExcelBean> ex = new ExcelUtil<StorageInExcelBean>();
		String[] headers = { "序号", "入库单编号", "入库时间", "对应案件编号", "案件名称", "办案民警", "对应犯罪嫌疑人/物品持有人", "对应文书", "备注" };
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String name = sdf.format(new Date());
		name = name + ".xls";
		// 创建ByteArrayOutputStream字节流
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ex.exportExcel(headers, storageInExcelBeanList, bos);
		byte[] bytes = bos.toByteArray();
		bos.close();
		ByteArrayInputStream ins = new ByteArrayInputStream(bytes);
		exportInfoReq.setIn(ins);
		exportInfoReq.setName(name);
		exportInfoReq.setLength(Long.valueOf(bytes.length));
		ins.close();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date start=new Date();
		Date end =new Date();
		try {
			start=s.parse(startTime);
			end=s.parse(endTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String query="";
		if(sf==null){
			query="否";
		}else{
			query ="是";
		}
		this.creatCaseManagementLog("涉案物品管理 /涉案物品入库", "导出查出的入库单,查询条件(入库单编号:"+code+"对应案件编号："+caseCode+"对应嫌疑人/物品持有人:"+suspectName+"对应文书:"+papers+"备注:"+remark+"入库时间:"+start+"--"+end+"只显示我的入库单:"+query+")");
		return "done";
	}

	/**
	 * 组装条件查询信息
	 * 
	 * 
	 */
	public Map<String, Object> searchMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", storageInBean.getCode());
		map.put("caseCode", storageInBean.getCaseCode());
		map.put("suspectName", storageInBean.getSuspectName());
		map.put("papers", storageInBean.getPapers());
		map.put("remark", storageInBean.getRemark());
		map.put("isShowMyForm", sf);
		map.put("loginUser", this.findCurrentPerson().getId());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if (!StringUtils.isBlank(startTime)) {
			try {
				map.put("startTime", sdf.parse(startTime));
			} catch (ParseException e) {
				logger.error("时间转换出错：" + e);
			}
		}
		if (!StringUtils.isBlank(endTime)) {
			try {
				map.put("endTime", sdf.parse(endTime));
			} catch (ParseException e) {
				logger.error("时间转换出错：" + e);
			}
		}
		return map;
	}

	/**
	 * model（InStorageForm）转换为bean （StorageInExcelBean）
	 * 
	 * @param entity
	 *            InStorageForm 入库单
	 * @return bean StorageInExcelBean 入库单导出Bean
	 */
	private StorageInExcelBean storageInToExcelBean(InStorageForm inStorageForm) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		StorageInExcelBean inStorageFormExcelBean = new StorageInExcelBean();
		try {
			BeanCopierFmtUtil.copyBean(inStorageForm, inStorageFormExcelBean, null);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		CriminalBasicCaseBean cbcBean = impoundedObjectService
				.findCriminalBasicCaseBeanForInStorageByCaseId(inStorageForm.getCaseCode());
		inStorageFormExcelBean.setCaseName(cbcBean.getCaseName());
		inStorageFormExcelBean.setCaseHandler(cbcBean.getHandlePolices());
		inStorageFormExcelBean.setCreatedTimeStr(sdf.format(inStorageForm.getCreatedTime()));

		Set<InStorageFormItem> isfiSet = inStorageForm.getInStorageFormItems();
		if (isfiSet != null && !isfiSet.isEmpty()) {
			for (InStorageFormItem isfi : isfiSet) {
				String suspectNameAndIdentityNumber = "";
				if(!StringUtils.isBlank(isfi.getArticle().getSuspectName()) && !"null".equals(isfi.getArticle().getSuspectName())){
					suspectNameAndIdentityNumber += isfi.getArticle().getSuspectName();
					if(!StringUtils.isBlank(isfi.getArticle().getSuspectIdentityNumber()) && !"null".equals(isfi.getArticle().getSuspectIdentityNumber())){
						suspectNameAndIdentityNumber += "/" +isfi.getArticle().getSuspectIdentityNumber();
					}
				}
				inStorageFormExcelBean.setSuspectName(suspectNameAndIdentityNumber);
				break;
			}
		}
		List<DocBean> docBeans = impoundedObjectService.findArchivedFilesByCaseIdAndSuspectId(inStorageForm.getCaseCode(),inStorageForm.getSuspectId());
		String paperNames = "";
		if(docBeans != null && !docBeans.isEmpty()){
			int index = 0;
			for(DocBean docBean : docBeans){
				paperNames += docBean.getPaperName();
				if(index < docBeans.size() - 1){
					paperNames += "；";
				}
				index ++;
			}
		}
		inStorageFormExcelBean.setPapers(paperNames);
		return inStorageFormExcelBean;
	}

	/**
	 * 储物箱选择页面数据初始化
	 * 
	 * @param storageAreaId
	 *            保管区Id
	 * @param caseCode
	 *            案件编号
	 * @return articleLockerList 储物箱List
	 */
	public String initDataForStorageSelectPage() {
		articleLockerList = articleLockerService.findLockersByCaseCodeAndAreaId(caseCode, storageAreaId);
		return SUCCESS;
	}

	/**
	 * 根据案件id和嫌疑人id查询对应文书
	 * 
	 * @return
	 */
	public String findDocBeanBySuspectIdAndCaseId() {
		paperList = impoundedObjectService.findArchivedFilesByCaseIdAndSuspectId(caseCode, suspectId);
		this.creatCaseManagementLog("涉案物品管理", "根据案件id和嫌疑人id查询对应文书(案件id:"+caseCode+"嫌疑人id:"+suspectId+")");
		return SUCCESS;
	}

	/**
	 * long时间转化Date
	 * 
	 * @param time
	 *            毫秒数
	 * @param fmt
	 *            输出格式，默认为精确到分钟
	 * @return
	 */
	private Date longDateToDate(Long time, String fmt) {
		if (time == null) {
			return new Date();
		}
		if (StringUtils.isBlank(fmt)) {
			fmt = "yyyy-MM-dd HH:mm";
		}
		Calendar ca = Calendar.getInstance();
		ca.setTimeInMillis(time);
		return ca.getTime();
	}

	/**
	 * Date时间转化成打印字符串
	 * 
	 * @param Date
	 *            毫秒数
	 * @param fmt
	 *            输出格式，默认为精确到分钟
	 * @return
	 */
	private String dateToStr(Date date, String fmt) {
		String str = "";
		if (date == null) {
			return str;
		}
		if (StringUtils.isBlank(fmt)) {
			fmt = "yyyy-MM-dd HH:mm";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		try {
			str = sdf.format(date);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return str;
	}

	
	private List<InStorageSnapshot> changeModel(List<InStorageSnapshotBean> LstBean) {
		List<InStorageSnapshot> lst=new ArrayList<InStorageSnapshot>();
		for(int i=0;i<LstBean.size();i++){
			InStorageSnapshot inst=new InStorageSnapshot();
			inst.setArticleCode(LstBean.get(i).getArticleCode());
			inst.setArticleFeature(LstBean.get(i).getArticleFeature());
			inst.setArticleName(LstBean.get(i).getArticleName());
			inst.setArticleType(LstBean.get(i).getArticleType());
			inst.setMeasurementUnit(LstBean.get(i).getMeasurementUnit());
			inst.setNumberRequested(LstBean.get(i).getNumberRequested());
			inst.setPaper(LstBean.get(i).getPaper());
			inst.setThisIncomingNum(LstBean.get(i).getThisIncomingNum());
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
	
	public List<StorageInBean> getStorageInBeanList() {
		return storageInBeanList;
	}

	public void setStorageInBeanList(List<StorageInBean> storageInBeanList) {
		this.storageInBeanList = storageInBeanList;
	}

	public StorageInBean getStorageInBean() {
		return storageInBean;
	}

	public void setStorageInBean(StorageInBean storageInBean) {
		this.storageInBean = storageInBean;
	}

	public String getSf() {
		return sf;
	}

	public void setSf(String sf) {
		this.sf = sf;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getPageFlag() {
		return pageFlag;
	}

	public void setPageFlag(String pageFlag) {
		this.pageFlag = pageFlag;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setStorageAreaList(List<StorageArea> storageAreaList) {
		this.storageAreaList = storageAreaList;
	}

	public List<StorageArea> getStorageAreaList() {
		return storageAreaList;
	}

	public String getStorageAreaId() {
		return storageAreaId;
	}

	public void setStorageAreaId(String storageAreaId) {
		this.storageAreaId = storageAreaId;
	}

	public List<ArticleLocker> getArticleLockerList() {
		return articleLockerList;
	}

	public void setArticleLockerList(List<ArticleLocker> articleLockerList) {
		this.articleLockerList = articleLockerList;
	}

	public List<InStorageFormItemBean> getItemBeanlist() {
		return itemBeanlist;
	}

	public void setItemBeanlist(List<InStorageFormItemBean> itemBeanlist) {
		this.itemBeanlist = itemBeanlist;
	}

	public InStorageFormServiceBean getInStorageFormBean() {
		return inStorageFormBean;
	}

	public void setInStorageFormBean(InStorageFormServiceBean inStorageFormBean) {
		this.inStorageFormBean = inStorageFormBean;
	}

	public String getInStorageFormId() {
		return inStorageFormId;
	}

	public void setInStorageFormId(String inStorageFormId) {
		this.inStorageFormId = inStorageFormId;
	}

	public List<InStorageFormServiceBean> getFormBeanlist() {
		return formBeanlist;
	}

	public void setFormBeanlist(List<InStorageFormServiceBean> formBeanlist) {
		this.formBeanlist = formBeanlist;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public InStorageForm getForm() {
		return form;
	}

	public void setForm(InStorageForm form) {
		this.form = form;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<StorageInExcelBean> getStorageInExcelBeanList() {
		return storageInExcelBeanList;
	}

	public void setStorageInExcelBeanList(List<StorageInExcelBean> storageInExcelBeanList) {
		this.storageInExcelBeanList = storageInExcelBeanList;
	}

	public StorageInExcelBean getStorageInExcelBean() {
		return storageInExcelBean;
	}

	public void setStorageInExcelBean(StorageInExcelBean storageInExcelBean) {
		this.storageInExcelBean = storageInExcelBean;
	}

	public List<DocBean> getPaperList() {
		return paperList;
	}

	public void setPaperList(List<DocBean> paperList) {
		this.paperList = paperList;
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

	public String getSuspectName() {
		return suspectName;
	}

	public void setSuspectName(String suspectName) {
		this.suspectName = suspectName;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPapers() {
		return papers;
	}

	public void setPapers(String papers) {
		this.papers = papers;
	}

	public List<String> getFormIdList() {
		return formIdList;
	}

	public void setFormIdList(List<String> formIdList) {
		this.formIdList = formIdList;
	}

	public List<OptionItemBean> getOptionItemBeanLst() {
		return optionItemBeanLst;
	}

	public void setOptionItemBeanLst(List<OptionItemBean> optionItemBeanLst) {
		this.optionItemBeanLst = optionItemBeanLst;
	}

	public List<OptionItemBean> getSuspectBeanLst() {
		return suspectBeanLst;
	}

	public void setSuspectBeanLst(List<OptionItemBean> suspectBeanLst) {
		this.suspectBeanLst = suspectBeanLst;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public List<OptionItemBean> getPaperBeanLst() {
		return paperBeanLst;
	}

	public void setPaperBeanLst(List<OptionItemBean> paperBeanLst) {
		this.paperBeanLst = paperBeanLst;
	}

	public String getSuspectId() {
		return suspectId;
	}

	public void setSuspectId(String suspectId) {
		this.suspectId = suspectId;
	}

	public List<InStorageFormItemServiceBean> getIsfisBeanList() {
		return isfisBeanList;
	}

	public void setIsfisBeanList(List<InStorageFormItemServiceBean> isfisBeanList) {
		this.isfisBeanList = isfisBeanList;
	}

	public String getArticleCode() {
		return articleCode;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	public InvolvedArticleBean getInvolvedArticleBean() {
		return involvedArticleBean;
	}

	public void setInvolvedArticleBean(InvolvedArticleBean involvedArticleBean) {
		this.involvedArticleBean = involvedArticleBean;
	}

	public List<InStorageFormItemServiceBean> getOldIsfisBeanList() {
		return oldIsfisBeanList;
	}

	public void setOldIsfisBeanList(List<InStorageFormItemServiceBean> oldIsfisBeanList) {
		this.oldIsfisBeanList = oldIsfisBeanList;
	}

	public InComingSnapshootBean getInComingSnapshootBean() {
		return inComingSnapshootBean;
	}

	public void setInComingSnapshootBean(InComingSnapshootBean inComingSnapshootBean) {
		this.inComingSnapshootBean = inComingSnapshootBean;
	}

	public List<InStorageSnapshotBean> getInstorageLst() {
		return instorageLst;
	}

	public void setInstorageLst(List<InStorageSnapshotBean> instorageLst) {
		this.instorageLst = instorageLst;
	}

	public String getPoliceNumber1() {
		return policeNumber1;
	}

	public void setPoliceNumber1(String policeNumber1) {
		this.policeNumber1 = policeNumber1;
	}

	public String getPoliceNumber2() {
		return policeNumber2;
	}

	public void setPoliceNumber2(String policeNumber2) {
		this.policeNumber2 = policeNumber2;
	}


}
