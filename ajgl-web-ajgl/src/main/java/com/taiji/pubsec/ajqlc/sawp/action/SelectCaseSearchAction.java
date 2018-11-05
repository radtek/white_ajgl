package com.taiji.pubsec.ajqlc.sawp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajgl.util.ReturnMessageAction;
import com.taiji.pubsec.ajqlc.sawp.action.bean.CaseBean;
import com.taiji.pubsec.ajqlc.sla.model.CaseSupectRelation;
import com.taiji.pubsec.ajqlc.sla.model.CriminalBasicCase;
import com.taiji.pubsec.ajqlc.sla.model.CriminalPerson;
import com.taiji.pubsec.ajqlc.sla.service.ICriminalCaseService;
import com.taiji.pubsec.ajqlc.sla.service.ICriminalPersonService;
import com.taiji.pubsec.ajqlc.sla.service.ICriminalSuspectService;
import com.taiji.pubsec.ajqlc.util.DateFmtUtil;
import com.taiji.pubsec.businesscomponent.organization.model.Unit;
import com.taiji.pubsec.businesscomponent.organization.service.IUnitService;

/**
 * 案件查询Action
 * 
 * @author WangLei
 *
 */
@Controller("selectCaseSearchAction")
@Scope("prototype")
public class SelectCaseSearchAction extends ReturnMessageAction{

	private static final long serialVersionUID = 1L;
	
	@Resource
	private ICriminalCaseService criminalCaseService;// 案件接口
	
	@Resource
	private ICriminalSuspectService criminalSuspectService; // 案件嫌疑人关系接口
	
	@Resource
	private ICriminalPersonService criminalPersonService;// 涉案人员接口
	
	@Resource
	private IUnitService unitService;// 单位接口
	
	
	private String caseName;// 案件名称
	
	private String  caseCode;// 案件编号
	
	private String policeName;// 办案民警
	
	private String suspectName;// 嫌疑人姓名
	
	private Long startTime;// 开始时间
	
	private Long endTime;// 结束时间
	
	private String unitId;// 办案单位id
	
	private Map<String,Object> conditionMap = new HashMap<String,Object>();// 查询条件map
	
	private List<CaseBean> caseBeanList = new ArrayList<CaseBean>();// 案件Bean集合
	
	/**
	 * 根据自定义条件查询案件（分页）
	 * @return
	 */
	public String findCaseByCondition(){
		this.conditionMap();
		Pager<CriminalBasicCase> page = criminalCaseService.findCriminalCaseByCondition(conditionMap, this.getStart()/this.getLength(), this.getLength());
		if(page == null){
			this.setTotalNum(0);
			return SUCCESS;
		}
		this.setTotalNum(page.getTotalNum());
		for(CriminalBasicCase cbc : page.getPageList()){
			CaseBean cb = criminalBasicCaseToCaseBean(cbc);
			if(cb != null){
				caseBeanList.add(cb);
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 案件Mocel转Bean
	 * @param cbc
	 * @return
	 */
	public CaseBean criminalBasicCaseToCaseBean(CriminalBasicCase cbc){
		if(cbc == null){
			return null;
		}
		CaseBean cb = new CaseBean();
		cb.setCaseCode(cbc.getCaseCode());
		cb.setCaseName(cbc.getCaseName());
		cb.setCaseTimeStart(DateFmtUtil.dateToLong(cbc.getCaseTimeStart()));
		cb.setCaseTimeEnd(DateFmtUtil.dateToLong(cbc.getCaseTimeEnd()));
		String policeName = "";
		if(StringUtils.isNotBlank(cbc.getHandlingPeople1())){
			policeName += cbc.getHandlingPeople1();
		}
		if(StringUtils.isNotBlank(cbc.getHandlingPeople2())){
			policeName += "、" + cbc.getHandlingPeople2();
		}
		cb.setPoliceName(policeName);
		String unitName = cbc.getDqbldw();
		if(null!=unitName){
			unitName = unitName.replace("贵阳市公安局经济技术开发区分局", "");
		}else{
			unitName="";
		}
		cb.setUnitName(unitName);
		
		List<CaseSupectRelation> caseSupectRelations = criminalSuspectService.findCaseSuspectRelationByCase(cbc.getCaseCode());
		String suspectName = "";
		for(CaseSupectRelation csr : caseSupectRelations){
			if(!StringUtils.isNotBlank(csr.getPerson_id())){
				continue;
			}
			CriminalPerson cp = criminalPersonService.findById(csr.getPerson_id());
			if(cp == null){
				continue;
			}
			if(!"".equals(cp.getName().trim())){
				suspectName += cp.getName() + "、";
			}
		}
		if(suspectName.length() > 0){
			suspectName = suspectName.substring(0, suspectName.length()-1);
			cb.setSuspectName(suspectName);
		}else{
			cb.setSuspectName("");
		}
		return cb;
	}
	
	/**
	 * 装配查询条件map
	 */
	private void conditionMap(){
		String unitCode = null;
		Unit unit = unitService.findUnitById(unitId);
		if(unit != null){
			unitCode = unit.getCode();
		}
		conditionMap.put("caseName", caseName);
		conditionMap.put("caseCode", caseCode);
		conditionMap.put("disposePerson", policeName);
		conditionMap.put("suspectName", suspectName);
		conditionMap.put("handingUnit", unitCode);
		conditionMap.put("caseTimeStart", startTime==null?null:DateFmtUtil.longToDate(startTime));
		conditionMap.put("caseTimeEnd", endTime==null?null:DateFmtUtil.longToDate(endTime));
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getPoliceName() {
		return policeName;
	}

	public void setPoliceName(String policeName) {
		this.policeName = policeName;
	}

	public String getSuspectName() {
		return suspectName;
	}

	public void setSuspectName(String suspectName) {
		this.suspectName = suspectName;
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

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public Map<String, Object> getConditionMap() {
		return conditionMap;
	}

	public void setConditionMap(Map<String, Object> conditionMap) {
		this.conditionMap = conditionMap;
	}

	public List<CaseBean> getCaseBeanList() {
		return caseBeanList;
	}

	public void setCaseBeanList(List<CaseBean> caseBeanList) {
		this.caseBeanList = caseBeanList;
	}
	
	

}
