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
import com.taiji.pubsec.ajgl.util.ReturnMessageAction;
import com.taiji.pubsec.ajqlc.sawp.action.bean.ArticleLockerBean;
import com.taiji.pubsec.ajqlc.sawp.action.bean.InvolvedArticleBean;
import com.taiji.pubsec.ajqlc.sawp.action.bean.StorageAreaBean;
import com.taiji.pubsec.ajqlc.sawp.action.bean.StorageBean;
import com.taiji.pubsec.ajqlc.sawp.action.bean.StorageExcelBean;
import com.taiji.pubsec.ajqlc.sawp.action.util.SawpModelBeanTransformUtil;
import com.taiji.pubsec.ajqlc.sawp.model.ArticleLocker;
import com.taiji.pubsec.ajqlc.sawp.model.Storage;
import com.taiji.pubsec.ajqlc.sawp.model.StorageArea;
import com.taiji.pubsec.ajqlc.sawp.service.IArticleLockerService;
import com.taiji.pubsec.ajqlc.sawp.service.IStorageAreaService;
import com.taiji.pubsec.ajqlc.sawp.service.IStorageQueryService;
import com.taiji.pubsec.ajqlc.util.DateFmtUtil;
import com.taiji.pubsec.ajqlc.util.ExcelUtil;
import com.taiji.pubsec.common.web.action.ExportInfoReq;

/**
 * 台账Action
 * @author WangLei
 *
 */
@Controller("standingBookAction")
@Scope("prototype")
public class StandingBookAction extends ReturnMessageAction{
	
	private static final long serialVersionUID = 1L;
	
	@Resource
	private SawpModelBeanTransformUtil sawpModelBeanTransformUtil;// model和bean互转类
	
	@Resource
	private IStorageAreaService storageAreaService;// 物品保管区接口
	
	@Resource
	private IArticleLockerService articleLockerService;// 储物箱接口
	
	@Resource
	private IStorageQueryService storageQueryService;
	
	private ExportInfoReq exportInfoReq = new ExportInfoReq();// 导出Excel用
	
	private Map<String, Object> conditions = new HashMap<String, Object>();// 查询条件集合
	
	private String storageAreaId;// 涉案物品保管区id
	
	private String status;// 状态
	
	private List<StorageAreaBean> storageAreaBeanList = new ArrayList<StorageAreaBean>();// 物品保管区Bean集合

	private List<ArticleLocker> lockerList = new ArrayList<ArticleLocker>();// 储物箱集合
	
	private List<StorageBean> storageBeanList = new ArrayList<StorageBean>();// 涉案物品存储位置Bean集合
	
	private List<StorageExcelBean> storageExcelBeanList = new ArrayList<StorageExcelBean>();// 台账ExcelBean集合
	
	/*
	 * 查询条件
	 */
	private String nameAndFeature;// 物品名称及特征
	
	private String caseCode;// 案件编号
	
	private String suspectNameOrNumber;// 对应犯罪嫌疑人姓名/身份证号
	
	private List<String> lockerIdList = new ArrayList<String>();// 储物箱id集合
	
	private boolean zero;// 是否显示在库数量为0的涉案物品
	
	private Long startTime;// 入库时间起始
	
	private Long endTime;// 入库时间结束
	
	private String lockAreaId; //涉案物品保管区id
	
	private String disposePerson; //警员名称或警号
	
	/**
	 * 查询所有涉案物品管理区
	 * 
	 * @param null 表示不过滤状态查询所有
	 * @return "success",成功返回storageAreaBeanList:物品保管区Bean集合
	 */
	public String findAllStorageArea(){
		String loginUnitId = this.findCurrentOrganization().getId();
		List<StorageArea> saList = storageAreaService.findStorageAreasByStatus(loginUnitId, status);
		for(StorageArea sa : saList){
			storageAreaBeanList.add(sawpModelBeanTransformUtil.storageAreaToStorageAreaBean(sa));
		}
		return SUCCESS;
	}
	
	/**
	 * 根据涉案物品保管区id查询所属所有的储物箱
	 * 
	 * @param storageAreaId 涉案物品保管区id
	 * @return "success",成功返回lockerList:储物箱集合
	 */
	public String findAllLockerByStorageAreaId(){
		lockerList = articleLockerService.findLockersByStorageAreaId(storageAreaId);
		return SUCCESS;
	}
	
	/**
	 * 根据条件查询涉案物品（分页）
	 * 
	 * @return "success",成功返回storageBeanList:涉案物品存储位置Bean集合
	 */
	public String findInvolvedArticleListByCondition(){
		this.conditionMap();
		Pager<Storage> pager = storageQueryService.queryArticleInvolvedInStorage(conditions, this.getStart()/this.getLength(), this.getLength());
		if(pager == null){
			return SUCCESS;
		}
		this.setTotalNum(pager.getTotalNum());
		for(Storage s : pager.getPageList()){
			storageBeanList.add(sawpModelBeanTransformUtil.storageToStorageBean(s));
		}
		return SUCCESS;
	}
	
	/**
	 * 台账列表导出EXCEL
	 * 
	 * @param
	 * @return
	 */
	public String exportExcel() throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		this.conditionMap();
		Pager<Storage> pager = storageQueryService.queryArticleInvolvedInStorage(conditions, 0, Integer.MAX_VALUE);
		int i = 1;
		for(Storage s : pager.getPageList()){
			StorageBean sb = sawpModelBeanTransformUtil.storageToStorageBean(s);
			StorageExcelBean seb = new StorageExcelBean();
			seb.setCount(i + "");
			InvolvedArticleBean iab = sb.getArticle();
			if(iab != null){
				seb.setName(iab.getName());
				seb.setFeature(iab.getFeature());
				seb.setCode(iab.getCode());
				seb.setDetentionNumber(iab.getDetentionNumber());
				seb.setTypeName(iab.getTypeName());
				seb.setNumberInStorage(s.getExistingNumber()+"");
				seb.setMeasurementUnit(iab.getMeasurementUnit());
				seb.setInStorageTime(sdf.format(new Date(iab.getInStorageTime())));
				if(iab.getBackStorageTime() != null && !"".equals(iab.getBackStorageTime())){
					seb.setBackStorageTime(sdf.format(new Date(iab.getBackStorageTime())));
				}else{
					seb.setBackStorageTime("");
				}
				seb.setCaseCode(iab.getCaseCode());
				seb.setCaseName(iab.getCaseName());
				seb.setPoliceName(iab.getPolices());
				seb.setSuspectName(iab.getSuspectName());
				seb.setPaper(iab.getPaper());
			}
			ArticleLockerBean alb = sb.getLocker();
			if(alb != null){
				StorageAreaBean sab = alb.getArea();
				seb.setLocation(alb.getLocation());
				if(sab == null){
					i++;
					storageExcelBeanList.add(seb);
					continue;
				}
				seb.setAreaName(sab.getName());
			}
			i++;
			storageExcelBeanList.add(seb);
		}
		
		ExcelUtil<StorageExcelBean> ex = new ExcelUtil<StorageExcelBean>();
		String[] headers = { "序号", "物品名称", "特征", "物品编号", "V3文书中物品数量", "物品性质", "在库数量", "计量单位",
				"所在物证保管区", "所在储物箱", "物品入库时间" , "返还入库时间", "对应案件编号" , "对应案件名称" ,"办案民警",
				"对应犯罪嫌疑人/物品持有人","对应入库单文书"};
		SimpleDateFormat sdfExcel = new SimpleDateFormat("yyyyMMddHHmmss");
		String name = sdfExcel.format(new Date());
		name = name + ".xls";
		
		// 创建ByteArrayOutputStream字节流
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		ex.exportExcel(headers, storageExcelBeanList, bos);
		byte[] bytes = bos.toByteArray();
		bos.close();
		ByteArrayInputStream ins=new ByteArrayInputStream(bytes);
		exportInfoReq.setIn(ins);
		exportInfoReq.setName(name);
		exportInfoReq.setLength(Long.valueOf(bytes.length));
		ins.close();
		this.creatCaseManagementLog("涉案物品管理 /涉案物品台账", "导出excel");
		return "done";
	}
	
	/**
	 * 装配查询条件map
	 */
	private void conditionMap(){
		conditions.put("nameAndFeature", nameAndFeature);
		conditions.put("caseCode", caseCode);
		conditions.put("suspectNameOrNumber", suspectNameOrNumber);
		conditions.put("lockerIds", lockerIdList);
		conditions.put("startTime", startTime == null?null:DateFmtUtil.longToDate(startTime));
		conditions.put("endTime", endTime == null?null:DateFmtUtil.longToDate(endTime));
		conditions.put("isZero", zero);
		conditions.put("lockArea", lockAreaId);
		conditions.put("disposePerson", disposePerson);
	}
	
	public List<StorageAreaBean> getStorageAreaBeanList() {
		return storageAreaBeanList;
	}

	public void setStorageAreaBeanList(List<StorageAreaBean> storageAreaBeanList) {
		this.storageAreaBeanList = storageAreaBeanList;
	}

	public String getStorageAreaId() {
		return storageAreaId;
	}

	public void setStorageAreaId(String storageAreaId) {
		this.storageAreaId = storageAreaId;
	}

	public List<ArticleLocker> getLockerList() {
		return lockerList;
	}

	public void setLockerList(List<ArticleLocker> lockerList) {
		this.lockerList = lockerList;
	}

	public Map<String, Object> getConditions() {
		return conditions;
	}

	public void setConditions(Map<String, Object> conditions) {
		this.conditions = conditions;
	}

	public List<StorageBean> getStorageBeanList() {
		return storageBeanList;
	}

	public void setStorageBeanList(List<StorageBean> storageBeanList) {
		this.storageBeanList = storageBeanList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNameAndFeature() {
		return nameAndFeature;
	}

	public void setNameAndFeature(String nameAndFeature) {
		this.nameAndFeature = nameAndFeature;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getSuspectNameOrNumber() {
		return suspectNameOrNumber;
	}

	public void setSuspectNameOrNumber(String suspectNameOrNumber) {
		this.suspectNameOrNumber = suspectNameOrNumber;
	}

	public List<String> getLockerIdList() {
		return lockerIdList;
	}

	public void setLockerIdList(List<String> lockerIdList) {
		this.lockerIdList = lockerIdList;
	}

	public boolean isZero() {
		return zero;
	}

	public void setZero(boolean zero) {
		this.zero = zero;
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

	public List<StorageExcelBean> getStorageExcelBeanList() {
		return storageExcelBeanList;
	}

	public void setStorageExcelBeanList(List<StorageExcelBean> storageExcelBeanList) {
		this.storageExcelBeanList = storageExcelBeanList;
	}

	public ExportInfoReq getExportInfoReq() {
		return exportInfoReq;
	}

	public void setExportInfoReq(ExportInfoReq exportInfoReq) {
		this.exportInfoReq = exportInfoReq;
	}

	public String getLockAreaId() {
		return lockAreaId;
	}

	public void setLockAreaId(String lockAreaId) {
		this.lockAreaId = lockAreaId;
	}

	public String getDisposePerson() {
		return disposePerson;
	}

	public void setDisposePerson(String disposePerson) {
		this.disposePerson = disposePerson;
	}
	
	
}
