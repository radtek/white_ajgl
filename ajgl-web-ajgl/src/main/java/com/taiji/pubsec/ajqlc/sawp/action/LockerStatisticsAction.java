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
import com.taiji.pubsec.ajgl.util.bean.OptionItemBean;
import com.taiji.pubsec.ajqlc.sawp.action.bean.LockerStatisticsExcelBean;
import com.taiji.pubsec.ajqlc.sawp.action.util.SawpModelBeanTransformUtil;
import com.taiji.pubsec.ajqlc.sawp.bean.LockerStatisticsServiceBean;
import com.taiji.pubsec.ajqlc.sawp.model.ArticleLocker;
import com.taiji.pubsec.ajqlc.sawp.model.StorageArea;
import com.taiji.pubsec.ajqlc.sawp.service.IArticleLockerService;
import com.taiji.pubsec.ajqlc.sawp.service.IStorageAreaService;
import com.taiji.pubsec.ajqlc.sawp.service.IStorageQueryService;
import com.taiji.pubsec.ajqlc.util.Constant;
import com.taiji.pubsec.ajqlc.util.ExcelUtil;
import com.taiji.pubsec.businesscomponent.dictionary.model.DictionaryItem;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryItemService;
import com.taiji.pubsec.common.web.action.ExportInfoReq;

/**
 * 储物箱存储情况Action
 * 
 * @author WangLei
 *
 */
@Controller("lockerStatisticsAction")
@Scope("prototype")
public class LockerStatisticsAction extends ReturnMessageAction{

	private static final long serialVersionUID = 1L;
	
	@Resource
	private IStorageAreaService storageAreaService;// 物品保管区接口
	
	@Resource
	private IArticleLockerService articleLockerService;// 储物箱接口
	
	@Resource
	private IDictionaryItemService dictionaryItemService;// 字典项接口
	
	@Resource
	private IStorageQueryService storageQueryService;// 储物箱存储情况接口
	
	@Resource
	private SawpModelBeanTransformUtil sawpModelBeanTransformUtil;// model和bean互转类
	
	private String storageAreaId;// 保管区id
	
	private List<String> lockerIds = new ArrayList<String>();// 储物箱id
	
	private String status;// 是否空闲
	
	private String caseCodeOrName;// 案件编号或者名称
	
	private String police;// 办案民警
	
	private String suspectedNameOrIdcode;// 对应犯罪嫌疑人姓名/身份证号
	
	private ExportInfoReq exportInfoReq = new ExportInfoReq();// 导出Excel用
	
	private Map<String,Object> conditionMap = new HashMap<String,Object>();// 查询条件map
	
	private List<OptionItemBean> optionItemBeanList = new ArrayList<OptionItemBean>();// 字典项Bean集合
	
	private List<LockerStatisticsServiceBean> lockerStatisticsServiceBeanList = new ArrayList<LockerStatisticsServiceBean>();// 储物箱存储情况Bean集合
	
	private List<LockerStatisticsExcelBean> lockerStatisticsExcelBeanList = new ArrayList<LockerStatisticsExcelBean>();// 储物箱存储情况Excel Bean集合
	
	/**
	 * 查询储物箱存储情况（分页查询）
	 * @return
	 */
	public String findLockerStatistics(){
		this.conditionMap();
		Pager<LockerStatisticsServiceBean> pager = storageQueryService.findLockersByConditions(conditionMap, this.getStart()/this.getLength(), this.getLength());
		if(pager == null){
			this.setTotalNum(0);
			return SUCCESS;
		}
		this.setTotalNum(pager.getTotalNum());
		lockerStatisticsServiceBeanList = pager.getPageList();
		return SUCCESS;
	}
	
	/**
	 * 查询所有的保管区
	 * @return
	 */
	public String findAllArea(){
		this.findCurrentOrganization().getId();
		List<StorageArea> saList = storageAreaService.findStorageAreasByStatus(this.findCurrentOrganization().getId(), null);
		if(saList == null){
			return SUCCESS;
		}
		for(StorageArea sa : saList){
			OptionItemBean oib = new OptionItemBean();
			oib.setId(sa.getId());
			oib.setCode(sa.getCode());
			oib.setName(sa.getName());
			optionItemBeanList.add(oib);
		}
		return SUCCESS;
	}
	
	/**
	 * 根据保管区id查询所有储物箱
	 * @return
	 */
	public String findAllLockerByAreaId(){
		List<ArticleLocker> alList = articleLockerService.findLockersByStorageAreaId(storageAreaId);
		if(alList == null){
			return SUCCESS;
		}
		for(ArticleLocker al : alList){
			OptionItemBean oib = new OptionItemBean();
			oib.setId(al.getId());
			oib.setCode(al.getCode());
			oib.setName(al.getLocation());
			optionItemBeanList.add(oib);
		}
		return SUCCESS;
	}
	
	/**
	 * 查询是否空闲字典项集合
	 * @return
	 */
	public String findStatusSelect(){
		List<DictionaryItem> dictionaryItemList = dictionaryItemService.findAllSubDictionaryItemsByTypeCode(Constant.SF);
		if(dictionaryItemList == null){
			return SUCCESS;
		}
		for(DictionaryItem di : dictionaryItemList){
			OptionItemBean oib = new OptionItemBean();
			oib.setId(di.getId());
			oib.setCode(di.getCode());
			oib.setName(di.getName());
			optionItemBeanList.add(oib);
		}
		return SUCCESS;
	}
	
	/**
	 * 装配查询条件map
	 */
	private void conditionMap(){
		conditionMap.put("areaId", storageAreaId);
		conditionMap.put("isOrNotFree", status);
		conditionMap.put("caseCodeOrName", caseCodeOrName);
		conditionMap.put("suspectNameOrIdCardNo", suspectedNameOrIdcode);
		conditionMap.put("polices", police);
		conditionMap.put("lockerIds", lockerIds);
	}
	
	/**
	 * 调整单列表导出EXCEL
	 * 
	 * @param
	 * @return
	 */
	public String exportExcel() throws IOException {
		this.conditionMap();
		Pager<LockerStatisticsServiceBean> pager = storageQueryService.findLockersByConditions(conditionMap, 0, Integer.MAX_VALUE);
		int i = 1;
		for(LockerStatisticsServiceBean lssb : pager.getPageList()){
			LockerStatisticsExcelBean lseb = new LockerStatisticsExcelBean();
			lseb.setCount(i + "");
			lseb.setAreaName(lssb.getAreaName());
			lseb.setLockerName(lssb.getLockerName());
			lseb.setStatutsName(lssb.getStatutsName());
			List<String> articleList = lssb.getArticleList();
			if(articleList != null && !articleList.isEmpty()){
				String str = "";
				for(String article : articleList){
					str += article + "；";
				}
				str = str.substring(0, str.length()-1);
				lseb.setArticle(str);
			}
			lseb.setNumberInStorage(String.valueOf(lssb.getNumberInStorage()));
			lseb.setCaseCode(lssb.getCaseCode());
			lseb.setCaseName(lssb.getCaseName());
			lseb.setPolices(lssb.getPolices());
			i++;
			lockerStatisticsExcelBeanList.add(lseb);
		}
		
		ExcelUtil<LockerStatisticsExcelBean> ex = new ExcelUtil<LockerStatisticsExcelBean>();
		String[] headers = { "序号", "物证保管区", "储物箱", "是否空闲", "当前存放物品编号/名称/嫌疑人/嫌疑人身份证号", "在库总件数", "对应案件编号", "对应案件名称", "办案民警"};
		SimpleDateFormat sdfExcel = new SimpleDateFormat("yyyyMMddHHmmss");
		String name = sdfExcel.format(new Date());
		name = name + ".xls";
		
		// 创建ByteArrayOutputStream字节流
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		ex.exportExcel(headers, lockerStatisticsExcelBeanList, bos);
		byte[] bytes = bos.toByteArray();
		bos.close();
		ByteArrayInputStream ins=new ByteArrayInputStream(bytes);
		exportInfoReq.setIn(ins);
		exportInfoReq.setName(name);
		exportInfoReq.setLength(Long.valueOf(bytes.length));
		ins.close();
		this.creatCaseManagementLog("涉案物品管理 /储物箱存储情况查询", "导出excel");
		return "done";
	}

	public List<LockerStatisticsServiceBean> getLockerStatisticsServiceBeanList() {
		return lockerStatisticsServiceBeanList;
	}

	public void setLockerStatisticsServiceBeanList(List<LockerStatisticsServiceBean> lockerStatisticsServiceBeanList) {
		this.lockerStatisticsServiceBeanList = lockerStatisticsServiceBeanList;
	}

	public List<OptionItemBean> getOptionItemBeanList() {
		return optionItemBeanList;
	}

	public void setOptionItemBeanList(List<OptionItemBean> optionItemBeanList) {
		this.optionItemBeanList = optionItemBeanList;
	}

	public String getStorageAreaId() {
		return storageAreaId;
	}

	public void setStorageAreaId(String storageAreaId) {
		this.storageAreaId = storageAreaId;
	}

	public List<String> getLockerIds() {
		return lockerIds;
	}

	public void setLockerIds(List<String> lockerIds) {
		this.lockerIds = lockerIds;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCaseCodeOrName() {
		return caseCodeOrName;
	}

	public void setCaseCodeOrName(String caseCodeOrName) {
		this.caseCodeOrName = caseCodeOrName;
	}

	public String getPolice() {
		return police;
	}

	public void setPolice(String police) {
		this.police = police;
	}

	public String getSuspectedNameOrIdcode() {
		return suspectedNameOrIdcode;
	}

	public void setSuspectedNameOrIdcode(String suspectedNameOrIdcode) {
		this.suspectedNameOrIdcode = suspectedNameOrIdcode;
	}

	public ExportInfoReq getExportInfoReq() {
		return exportInfoReq;
	}

	public void setExportInfoReq(ExportInfoReq exportInfoReq) {
		this.exportInfoReq = exportInfoReq;
	}

	public List<LockerStatisticsExcelBean> getLockerStatisticsExcelBeanList() {
		return lockerStatisticsExcelBeanList;
	}

	public void setLockerStatisticsExcelBeanList(List<LockerStatisticsExcelBean> lockerStatisticsExcelBeanList) {
		this.lockerStatisticsExcelBeanList = lockerStatisticsExcelBeanList;
	}
	
	
}
