package com.taiji.pubsec.ajqlc.sawp.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
import com.taiji.pubsec.ajqlc.operationrecord.model.OperationRecord;
import com.taiji.pubsec.ajqlc.operationrecord.service.IOperationRecordService;
import com.taiji.pubsec.ajqlc.sawp.action.bean.AdjustmentStorageFormExcelBean;
import com.taiji.pubsec.ajqlc.sawp.action.bean.StorageBean;
import com.taiji.pubsec.ajqlc.sawp.action.util.SawpModelBeanTransformUtil;
import com.taiji.pubsec.ajqlc.sawp.bean.AdjustmentStorageFormItemServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.AdjustmentStorageFormServiceBean;
import com.taiji.pubsec.ajqlc.sawp.model.AdjustgmentStorageForm;
import com.taiji.pubsec.ajqlc.sawp.model.ArticleLocker;
import com.taiji.pubsec.ajqlc.sawp.model.InvolvedArticle;
import com.taiji.pubsec.ajqlc.sawp.model.Storage;
import com.taiji.pubsec.ajqlc.sawp.service.IAdjustmentStorageFormService;
import com.taiji.pubsec.ajqlc.sawp.service.IArticleLockerService;
import com.taiji.pubsec.ajqlc.sawp.service.IInvolvedArticleService;
import com.taiji.pubsec.ajqlc.sawp.service.IStorageService;
import com.taiji.pubsec.ajqlc.util.DateFmtUtil;
import com.taiji.pubsec.ajqlc.util.ExcelUtil;
import com.taiji.pubsec.common.web.action.ExportInfoReq;

/**
 * 调整单管理Action
 * @author WangLei
 *
 */
@Controller("adjustmentStorageFormAction")
@Scope("prototype")
public class AdjustmentStorageFormAction extends ReturnMessageAction{
	
	private static final long serialVersionUID = 1L;
	
	@Resource
	private SawpModelBeanTransformUtil sawpModelBeanTransformUtil;// model和bean互转类
	
	@Resource
	private IAdjustmentStorageFormService adjustmentStorageFormService;// 调整单管理服务接口
	
	@Resource
	private IStorageService storageService;// 涉案物品保管位置服务接口
	
	@Resource
	private IInvolvedArticleService involvedArticleService;// 涉案物品接口
	
	@Resource
	private IArticleLockerService articleLockerService;// 储物箱服务接口
	
	@Resource
    private IOperationRecordService operationRecordService;// 操作记录接口
	
	private ExportInfoReq exportInfoReq = new ExportInfoReq();// 导出Excel用
	
	private String no;// 调整单流水号
	
	private Long startTime;// 开始时间
	
	private Long endTime;// 结束时间 
	
	private String caseCode;// 案件编号
	
	private String storageAreaId;// 保管区id
	
	private String adjustmentStorageFormId;// 涉案物品调整单id
	
	private AdjustmentStorageFormServiceBean asfsBean;// 涉案物品调整单Bean
	
	private List<String> storageIdList = new ArrayList<String>();// 涉案物品保管位置id集合
	
	private List<StorageBean> storageBeanList = new ArrayList<StorageBean>();// 涉案物品保管位置Bean集合
	
	private List<AdjustmentStorageFormServiceBean> adjustmentStorageFormServiceBeanList = new ArrayList<AdjustmentStorageFormServiceBean>();// 涉案物品调整单Bean集合
	
	private List<AdjustmentStorageFormExcelBean> adjustmentStorageFormExcelBeanList = new ArrayList<AdjustmentStorageFormExcelBean>();// 涉案物品调整单ExcelBean集合
	
	private Map<String,Object> conditionMap = new HashMap<String,Object>();// 查询条件map
	
	private List<ArticleLocker> articleLockerList = new ArrayList<ArticleLocker>(); // 储物箱List
	
	/**
	 * 根据涉案物品保管位置id集合查询所有涉案物品保管位置对象集合
	 * 
	 * @param storageIdList 保管位置id集合
	 * @return "success",成功返回storageBeanList:涉案物品保管位置Bean集合
	 */
	public String findSotrageByIdList(){
		for(String id : storageIdList){
			Storage s = storageService.findStorageByid(id);
			storageBeanList.add(sawpModelBeanTransformUtil.storageToStorageBean(s));
		}
		return SUCCESS;
	}
	
	/**
	 * 新增涉案物品调整单	 
	 * 
	 * @param adjustmentStorageFormBean 涉案物品调整单Bean
	 * @return "success",成功返回flag:true保存成功
	 */
	public String newAdjustmentStorageForm(){
		adjustmentStorageFormId = adjustmentStorageFormService.createForm(asfsBean, asfsBean.getAsfiBeanList());
		this.setFlag("true");
		
		//创建操作记录
		for(AdjustmentStorageFormItemServiceBean asfisb : asfsBean.getAsfiBeanList()){
			OperationRecord or = new OperationRecord();
			//涉案物品id查询code
			InvolvedArticle ia = involvedArticleService.findInvolvedArticleById(asfisb.getArticleId());
			if(ia != null){
				or.setArticleCode(ia.getCode());
			}
			or.setFormCode(asfsBean.getCode());
			or.setFormId(adjustmentStorageFormId);
			or.setFormType(AdjustgmentStorageForm.class.getName());
			
			//根据旧存储柜子id查询区域和柜子信息
			Storage storage = storageService.findStorageByid(asfisb.getStorageId());
			if(storage != null){
				or.setNumber(storage.getLocker().getArea().getName() + "，" + storage.getLocker().getLocation());
			}
			or.setOperation(OperationRecordConstant.WP_CZJL_TZ);
			or.setOperationTime(new Date());
			or.setOperator(this.findCurrentPerson().getName());
			operationRecordService.saveOperationRecord(or);
		}
		this.creatCaseManagementLog("涉案物品管理 /新增存储位置调整单", "新增存储位置调整单(调整单id:"+adjustmentStorageFormId+")");
		return SUCCESS;
	}
	
	/**
	 * 根据条件查询调整单（分页）
	 * 
	 * @param code 调整单编号
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return "success",成功返回adjustmentStorageFormBeanList:涉案物品调整单Bean集合
	 */
	public String findAdjustmentStorageFormByCondition(){
		this.conditionMap();
		Pager<AdjustgmentStorageForm> pager = adjustmentStorageFormService.queryByCondition(conditionMap, this.getStart()/this.getLength(), this.getLength());
		if(pager == null){
			return SUCCESS;
		}
		this.setTotalNum(pager.getTotalNum());
		for(AdjustgmentStorageForm asf : pager.getPageList()){
			adjustmentStorageFormServiceBeanList.add(sawpModelBeanTransformUtil.adjustgmentStorageFormToAdjustmentStorageFormServiceBean(asf));
		}
		return SUCCESS;
	}
	
	/**
	 * 根据id查询调整单
	 * 
	 * @param adjustmentStorageFormId 调整单id
	 * @return "success",成功返回adjustmentStorageFormBean:涉案物品调整单Bean
	 */
	public String findAdjustmentStorageFormById(){
		AdjustgmentStorageForm asf = adjustmentStorageFormService.findById(adjustmentStorageFormId);
		asfsBean = sawpModelBeanTransformUtil.adjustgmentStorageFormToAdjustmentStorageFormServiceBean(asf);
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
	 * 
	 * @return "success",成功返回no:调整单流水号
	 */
	public String acquireNum(){
		no = adjustmentStorageFormService.acquireNum();
		return SUCCESS;
	}
	
	/**
	 * 调整单列表导出EXCEL
	 * 
	 * @param
	 * @return
	 */
	public String exportExcel() throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		this.conditionMap();
		Pager<AdjustgmentStorageForm> pager = adjustmentStorageFormService.queryByCondition(conditionMap, 0, Integer.MAX_VALUE);
		int i = 1;
		for(AdjustgmentStorageForm asf : pager.getPageList()){
			AdjustmentStorageFormServiceBean asfsb = sawpModelBeanTransformUtil.adjustgmentStorageFormToAdjustmentStorageFormServiceBean(asf);
			AdjustmentStorageFormExcelBean asfeb = new AdjustmentStorageFormExcelBean();
			asfeb.setCount(i + "");
			asfeb.setCode(asfsb.getCode());
			asfeb.setReason(asfsb.getReason());
			asfeb.setUpdatedTime(sdf.format(new Date(asfsb.getUpdatedTime())));
			i++;
			adjustmentStorageFormExcelBeanList.add(asfeb);
		}
		
		ExcelUtil<AdjustmentStorageFormExcelBean> ex = new ExcelUtil<AdjustmentStorageFormExcelBean>();
		String[] headers = { "序号", "调整单编号", "调整时间", "调整原因"};
		SimpleDateFormat sdfExcel = new SimpleDateFormat("yyyyMMddHHmmss");
		String name = sdfExcel.format(new Date());
		name = name + ".xls";
		
		// 创建ByteArrayOutputStream字节流
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		ex.exportExcel(headers, adjustmentStorageFormExcelBeanList, bos);
		byte[] bytes = bos.toByteArray();
		bos.close();
		ByteArrayInputStream ins=new ByteArrayInputStream(bytes);
		exportInfoReq.setIn(ins);
		exportInfoReq.setName(name);
		exportInfoReq.setLength(Long.valueOf(bytes.length));
		ins.close();
		this.creatCaseManagementLog("涉案物品管理 /涉案物品存储位置调整单", "导出excel");
		return "done";
	}
	
	/**
	 * 装配查询条件map
	 */
	private void conditionMap(){
		conditionMap.put("code", no);
		conditionMap.put("startTime", startTime==null?null:DateFmtUtil.longToDate(startTime));
		conditionMap.put("endTime", endTime==null?null:DateFmtUtil.longToDate(endTime));
	}
	
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
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

	public String getAdjustmentStorageFormId() {
		return adjustmentStorageFormId;
	}

	public void setAdjustmentStorageFormId(String adjustmentStorageFormId) {
		this.adjustmentStorageFormId = adjustmentStorageFormId;
	}

	public AdjustmentStorageFormServiceBean getAdjustmentStorageFormServiceBean() {
		return asfsBean;
	}

	public void setAdjustmentStorageFormServiceBean(AdjustmentStorageFormServiceBean adjustmentStorageFormServiceBean) {
		this.asfsBean = adjustmentStorageFormServiceBean;
	}

	public List<String> getStorageIdList() {
		return storageIdList;
	}

	public void setStorageIdList(List<String> storageIdList) {
		this.storageIdList = storageIdList;
	}

	public List<StorageBean> getStorageBeanList() {
		return storageBeanList;
	}

	public void setStorageBeanList(List<StorageBean> storageBeanList) {
		this.storageBeanList = storageBeanList;
	}

	public List<AdjustmentStorageFormServiceBean> getAdjustmentStorageFormServiceBeanList() {
		return adjustmentStorageFormServiceBeanList;
	}

	public void setAdjustmentStorageFormServiceBeanList(
			List<AdjustmentStorageFormServiceBean> adjustmentStorageFormServiceBeanList) {
		this.adjustmentStorageFormServiceBeanList = adjustmentStorageFormServiceBeanList;
	}

	public Map<String, Object> getConditionMap() {
		return conditionMap;
	}

	public void setConditionMap(Map<String, Object> conditionMap) {
		this.conditionMap = conditionMap;
	}

	public ExportInfoReq getExportInfoReq() {
		return exportInfoReq;
	}

	public void setExportInfoReq(ExportInfoReq exportInfoReq) {
		this.exportInfoReq = exportInfoReq;
	}

	public List<AdjustmentStorageFormExcelBean> getAdjustmentStorageFormExcelBeanList() {
		return adjustmentStorageFormExcelBeanList;
	}

	public void setAdjustmentStorageFormExcelBeanList(
			List<AdjustmentStorageFormExcelBean> adjustmentStorageFormExcelBeanList) {
		this.adjustmentStorageFormExcelBeanList = adjustmentStorageFormExcelBeanList;
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

	public List<ArticleLocker> getArticleLockerList() {
		return articleLockerList;
	}

	public void setArticleLockerList(List<ArticleLocker> articleLockerList) {
		this.articleLockerList = articleLockerList;
	}

	public AdjustmentStorageFormServiceBean getAsfsBean() {
		return asfsBean;
	}

	public void setAsfsBean(AdjustmentStorageFormServiceBean asfsBean) {
		this.asfsBean = asfsBean;
	}
	
	
}
