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
import com.taiji.pubsec.ajqlc.operationrecord.model.OperationRecord;
import com.taiji.pubsec.ajqlc.operationrecord.service.IOperationRecordService;
import com.taiji.pubsec.ajqlc.sawp.action.bean.ArticleInvolvedExcelBean;
import com.taiji.pubsec.ajqlc.sawp.action.bean.OperationRecordBean;
import com.taiji.pubsec.ajqlc.sawp.action.util.SawpModelBeanTransformUtil;
import com.taiji.pubsec.ajqlc.sawp.bean.ArticleInvolvedServiceBean;
import com.taiji.pubsec.ajqlc.sawp.service.IStorageQueryService;
import com.taiji.pubsec.ajqlc.util.ExcelUtil;
import com.taiji.pubsec.common.web.action.ExportInfoReq;

/**
 * 物品情况统计Action
 * 
 * @author WangLei
 *
 */
@Controller("articleStatisticsAction")
@Scope("prototype")
public class ArticleStatisticsAction extends ReturnMessageAction{

	private static final long serialVersionUID = 1L;

	@Resource
	private SawpModelBeanTransformUtil sawpModelBeanTransformUtil;// model和bean相互转换的类
	
	@Resource
	private IStorageQueryService storageQueryService;// 涉案物品接口
	
	@Resource
	private IOperationRecordService operationRecordService;// 操作记录接口
	
	private String wpCode;// 物品编码
	
	private String wpNameOrFeature;// 物品名称/特征
	
	private String caseCodeOrName;// 对应案件编号/名称
	
	private String suspectedNameOrIdcode;// 对应犯罪嫌疑人姓名/身份证号
	
	private String police;// 办案民警
	
	private String paperName;// 对应文书名称
	
	private ExportInfoReq exportInfoReq = new ExportInfoReq();// 导出Excel用
	
	private Map<String,Object> conditionMap = new HashMap<String,Object>();// 查询条件map
	
	private List<ArticleInvolvedServiceBean> articleInvolvedServiceBeanList = new ArrayList<ArticleInvolvedServiceBean>();// 物品统计Bean集合
	
	private List<ArticleInvolvedExcelBean> articleInvolvedExcelBeanList = new ArrayList<ArticleInvolvedExcelBean>();// 物品统计ExcelBean集合
	
	private List<OperationRecordBean> operationRecordBeanList = new ArrayList<OperationRecordBean>();// 历史记录Bean集合
	
	
	/**
	 * 查询所有的涉案物品（分页查询）
	 * @return
	 */
	public String findAllInvolvedArticle(){
		this.conditionMap();
		Pager<ArticleInvolvedServiceBean> pager = storageQueryService.queryArticleInvolved(conditionMap, this.getStart()/this.getLength(), this.getLength());
		if(pager == null){
			this.setTotalNum(0);
			return SUCCESS;
		}
		articleInvolvedServiceBeanList = pager.getPageList();
		this.setTotalNum(pager.getTotalNum());
		return SUCCESS;
	}
	
	/**
	 * 根据物品编码查询操作记录
	 * @return
	 */
	public String findAllOperationRecord(){
		List<OperationRecord> orList = operationRecordService.findOperationRecordByarticleCode(wpCode);
		if(orList == null){
			this.setTotalNum(0);
			return SUCCESS;
		}
		for(OperationRecord or : orList){
			OperationRecordBean orb = sawpModelBeanTransformUtil.operationRecordToOperationRecordBean(or);
			operationRecordBeanList.add(orb);
		}
		this.setTotalNum(operationRecordBeanList.size());
		return SUCCESS;
	}
	
	
	/**
	 * 装配查询条件map
	 */
	private void conditionMap(){
		conditionMap.put("code", wpCode);
		conditionMap.put("nameOrFeature", wpNameOrFeature);
		conditionMap.put("caseCodeOrName", caseCodeOrName);
		conditionMap.put("suspectNameOrIdCardNo", suspectedNameOrIdcode);
		conditionMap.put("paper", paperName);
		conditionMap.put("polices", police);
	}
	
	/**
	 * 调整单列表导出EXCEL
	 * 
	 * @param
	 * @return
	 */
	public String exportExcel() throws IOException {
		this.conditionMap();
		Pager<ArticleInvolvedServiceBean> pager = storageQueryService.queryArticleInvolved(conditionMap, 0, Integer.MAX_VALUE);
		int i = 1;
		for(ArticleInvolvedServiceBean aisb : pager.getPageList()){
			ArticleInvolvedExcelBean aieb = new ArticleInvolvedExcelBean();
			aieb.setCount(i + "");
			aieb.setArticleName(ifNull(aisb.getArticleName()));
			aieb.setFeature(ifNull(aisb.getFeature()));
			aieb.setArticleCode(ifNull(aisb.getArticleCode()));
			aieb.setDetentionNumber(ifNull(aisb.getDetentionNumber()));
			aieb.setTypeName(ifNull(aisb.getTypeName()));
			aieb.setNumberInStorage(String.valueOf(aisb.getNumberInStorage()));
			aieb.setMeasurementUnit(ifNull(aisb.getMeasurementUnit()));
			aieb.setOutComingNumber(String.valueOf(aisb.getOutComingNumber()));
			aieb.setReturnedNumber(String.valueOf(aisb.getReturnedNumber()));
			aieb.setCaseCode(ifNull(aisb.getCaseCode()));
			aieb.setCaseName(ifNull(aisb.getCaseName()));
			aieb.setCaseHandler(ifNull(aisb.getCaseHandler()));
			aieb.setSuspectName(ifNull(aisb.getSuspectName()) + "/" + ifNull(aisb.getSuspectIdentifier()));
			aieb.setPaper(ifNull(aisb.getPaper()));
			i++;
			articleInvolvedExcelBeanList.add(aieb);
		}
		
		ExcelUtil<ArticleInvolvedExcelBean> ex = new ExcelUtil<ArticleInvolvedExcelBean>();
		String[] headers = { "序号", "物品名称", "特征", "物品编号", "V3文书中物品数量", "物品性质", "在库数量", "计量单位", "出库数量", "返还数量", "对应案件编号", "对应案件名称", "办案民警", "对应犯罪嫌疑人/物品持有人", "对应入库文书"};
		SimpleDateFormat sdfExcel = new SimpleDateFormat("yyyyMMddHHmmss");
		String name = sdfExcel.format(new Date());
		name = name + ".xls";
		
		// 创建ByteArrayOutputStream字节流
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		ex.exportExcel(headers, articleInvolvedExcelBeanList, bos);
		byte[] bytes = bos.toByteArray();
		bos.close();
		ByteArrayInputStream ins=new ByteArrayInputStream(bytes);
		exportInfoReq.setIn(ins);
		exportInfoReq.setName(name);
		exportInfoReq.setLength(Long.valueOf(bytes.length));
		ins.close();
		this.creatCaseManagementLog("涉案物品管理 /涉案物品情况统计", "导出excel");
		return "done";
	}
	
	private String ifNull(String str){
		if(str==null){
			return "";
		}
		return str;
	}

	public List<ArticleInvolvedServiceBean> getArticleInvolvedServiceBeanList() {
		return articleInvolvedServiceBeanList;
	}

	public void setArticleInvolvedServiceBeanList(List<ArticleInvolvedServiceBean> articleInvolvedServiceBeanList) {
		this.articleInvolvedServiceBeanList = articleInvolvedServiceBeanList;
	}

	public String getWpCode() {
		return wpCode;
	}

	public void setWpCode(String wpCode) {
		this.wpCode = wpCode;
	}

	public String getWpNameOrFeature() {
		return wpNameOrFeature;
	}

	public void setWpNameOrFeature(String wpNameOrFeature) {
		this.wpNameOrFeature = wpNameOrFeature;
	}

	public String getCaseCodeOrName() {
		return caseCodeOrName;
	}

	public void setCaseCodeOrName(String caseCodeOrName) {
		this.caseCodeOrName = caseCodeOrName;
	}

	public String getSuspectedNameOrIdcode() {
		return suspectedNameOrIdcode;
	}

	public void setSuspectedNameOrIdcode(String suspectedNameOrIdcode) {
		this.suspectedNameOrIdcode = suspectedNameOrIdcode;
	}

	public String getPolice() {
		return police;
	}

	public void setPolice(String police) {
		this.police = police;
	}

	public String getPaperName() {
		return paperName;
	}

	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}

	public List<OperationRecordBean> getOperationRecordBeanList() {
		return operationRecordBeanList;
	}

	public void setOperationRecordBeanList(List<OperationRecordBean> operationRecordBeanList) {
		this.operationRecordBeanList = operationRecordBeanList;
	}

	public List<ArticleInvolvedExcelBean> getArticleInvolvedExcelBeanList() {
		return articleInvolvedExcelBeanList;
	}

	public void setArticleInvolvedExcelBeanList(List<ArticleInvolvedExcelBean> articleInvolvedExcelBeanList) {
		this.articleInvolvedExcelBeanList = articleInvolvedExcelBeanList;
	}

	public ExportInfoReq getExportInfoReq() {
		return exportInfoReq;
	}

	public void setExportInfoReq(ExportInfoReq exportInfoReq) {
		this.exportInfoReq = exportInfoReq;
	}
	
	
}
