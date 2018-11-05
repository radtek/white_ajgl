package com.taiji.pubsec.ajqlc.sla.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taiji.persistence.dao.Dao;
import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.sla.bean.PersonServiceBean;
import com.taiji.pubsec.ajqlc.sla.model.AlarmInfo;
import com.taiji.pubsec.ajqlc.sla.model.CaseAttachedInfo;
import com.taiji.pubsec.ajqlc.sla.model.CriminalBasicCase;
import com.taiji.pubsec.ajqlc.sla.model.CriminalObject;
import com.taiji.pubsec.ajqlc.sla.model.CriminalPerson;
import com.taiji.pubsec.ajqlc.sla.model.SufferCaseRelation;
import com.taiji.pubsec.ajqlc.sla.service.ICriminalCaseService;
import com.taiji.pubsec.ajqlc.sla.service.IUnsolvedCaseQueryService;
import com.taiji.pubsec.ajqlc.util.ParamMapUtil;
import com.taiji.pubsec.businesscomponent.dictionary.model.DictionaryItem;
import com.taiji.pubsec.businesscomponent.organization.model.Unit;
import com.taiji.pubsec.businesscomponent.organization.service.IUnitService;
import com.taiji.pubsec.common.tool.sql.SQLTool;

@Service("criminalCaseService")
public class CriminalCaseServiceImpl implements ICriminalCaseService {
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@Resource
	IUnitService unitService;
	
	@Resource
	IUnsolvedCaseQueryService unsolvedCaseQueryService;
	
	private static final String KYJDS = "com.taiji.pubsec.ajqlc.sla.model.DocKouYaJueDingNew2012";
	private static final String CASECLASS_ADMINISTRATIONCASE = "行政案件";	//案件类型：行政案件
	private static final String CASECLASS_PENALCASE = "刑事案件";	//案件类型：刑事案件
	public static final String DICT_TYPE__CODE_CASESORT = "ajlb"	;//字典类型code：案件类别
	
	@SuppressWarnings("unchecked")
	@Override
	public Pager<CriminalBasicCase> findCriminalCaseByCondition(Map<String, Object> paramMap, int pageNo,
			int pageSize) {
		StringBuilder xqlSelecta = new StringBuilder("select c from CriminalBasicCase as c ");
		StringBuilder xqlSelectb = new StringBuilder("select a.basicCase from AlarmInfo as a left join a.basicCase as c ");
		StringBuilder xqlWhere = new StringBuilder();
		Map<String, Object> xqlMap = new HashMap<String, Object>(0);
		if(ParamMapUtil.isNotBlank(paramMap.get("suspectName"))){
			xqlSelecta.append(" , CaseSupectRelation as cr, CriminalPerson as cp where c.caseCode = cr.case_id and cr.person_id = cp.personId and cp.name like :name ");
			xqlSelectb.append(" , CaseSupectRelation as cr, CriminalPerson as cp where c.caseCode = cr.case_id and cr.person_id = cp.personId and cp.name like :name ");
			SQLTool.SQLAddEscape(xqlSelecta);
			SQLTool.SQLAddEscape(xqlSelectb);
			xqlMap.put("name", "%" + SQLTool.SQLSpecialChTranfer((String) paramMap.get("suspectName")) + "%");
		}else{
			xqlSelecta.append(" where 1 = 1 ");
			xqlSelectb.append(" where 1 = 1 ");
		}
		
		if(ParamMapUtil.isNotBlank(paramMap.get("caseTimeStart"))) {
			xqlWhere.append(" and c.caseTimeStart >= :caseTimeStart");
			xqlMap.put("caseTimeStart", paramMap.get("caseTimeStart"));
		}
		if(ParamMapUtil.isNotBlank(paramMap.get("caseTimeEnd"))) {
			xqlWhere.append(" and c.caseTimeStart < :caseTimeEnd");
			xqlMap.put("caseTimeEnd", paramMap.get("caseTimeEnd"));
		}
		if(ParamMapUtil.isNotBlank(paramMap.get("caseCode"))) {
			xqlWhere.append(" and c.caseCode like :caseCode");
			SQLTool.SQLAddEscape(xqlWhere);
			xqlMap.put("caseCode", "%" + SQLTool.SQLSpecialChTranfer((String)paramMap.get("caseCode")) + "%");
		}
		if(ParamMapUtil.isNotBlank(paramMap.get("caseName"))) {
			xqlWhere.append(" and c.caseName like :caseName");
			SQLTool.SQLAddEscape(xqlWhere);
			xqlMap.put("caseName", "%" + SQLTool.SQLSpecialChTranfer((String)paramMap.get("caseName")) + "%");
		}
		if(ParamMapUtil.isNotBlank(paramMap.get("caseClass"))) {
			xqlWhere.append(" and c.caseClass = :caseClass ");
			xqlMap.put("caseClass", paramMap.get("caseClass"));
		}else{
			xqlWhere.append(" and (c.caseClass = :caseClassAdministration ");
			xqlMap.put("caseClassAdministration", CASECLASS_ADMINISTRATIONCASE);
			xqlWhere.append(" or c.caseClass = :caseClassPenal) ");
			xqlMap.put("caseClassPenal", CASECLASS_PENALCASE);
		}
		if(ParamMapUtil.isNotBlank(paramMap.get("caseKind"))) {
			int count = 0;
			for(int i = ((String)paramMap.get("caseKind")).length(); i > 0; i--){
				if(((String)paramMap.get("caseKind")).charAt(i-1) != '0'){
					count = i;					break;
				}
			}
			xqlWhere.append(" and c.caseKindCode like :caseKind");
			SQLTool.SQLAddEscape(xqlWhere);
			xqlMap.put("caseKind", SQLTool.SQLSpecialChTranfer(((String)paramMap.get("caseKind")).substring(0, count)) + "%");
		}
		if(ParamMapUtil.isNotBlank(paramMap.get("caseSort"))) {
			List<DictionaryItem> items = unsolvedCaseQueryService.findAllSubDictItemsBySupCode((String) paramMap.get("caseSort"), DICT_TYPE__CODE_CASESORT);
			List<String> itemCodes = new ArrayList<String>();
			itemCodes.add((String) paramMap.get("caseSort"));
			for(DictionaryItem item: items){
				itemCodes.add(item.getCode());
			}
			
			xqlWhere.append(" and c.caseSortCode in (:caseSort) ");
			xqlMap.put("caseSort", itemCodes);
		}
		//TODO 案件时段筛选条件
		
		if(ParamMapUtil.isNotBlank(paramMap.get("community"))) {
			xqlWhere.append(" and c.communityCode = :community");
			xqlMap.put("community", paramMap.get("community"));
		}
		if(ParamMapUtil.isNotBlank(paramMap.get("caseState"))) {
			xqlWhere.append(" and c.caseStateCode in (:caseState)");
			xqlMap.put("caseState", paramMap.get("caseState"));
		}
		
		if(ParamMapUtil.isNotBlank(paramMap.get("caseAddress"))) {
			xqlWhere.append(" and c.caseAddress like :caseAddress");
			SQLTool.SQLAddEscape(xqlWhere);
			xqlMap.put("caseAddress", "%" + SQLTool.SQLSpecialChTranfer((String)paramMap.get("caseAddress")) + "%");
		}
		if(ParamMapUtil.isNotBlank(paramMap.get("handingUnit"))) {
			List<String> unitCodes = new ArrayList<String>();
			unitCodes.add((String) paramMap.get("handingUnit"));
			Unit unit = unitService.findUnitByCode((String) paramMap.get("handingUnit"));
			//查询该单位的所有下级单位 
			List<Unit> subUnits = unitService.findSubUnitsByUnitId(unit.getId());
			for(Unit u: subUnits){
				unitCodes.add(u.getCode());
			}
			
			xqlWhere.append(" and c.dqbldwCode in (:handingUnit) ");
			xqlMap.put("handingUnit", unitCodes);
		}
		if(ParamMapUtil.isNotBlank(paramMap.get("disposePerson"))){
			xqlWhere.append(" and (c.HandlingPeople1 like :disposePerson1 ");
			SQLTool.SQLAddEscape(xqlWhere);
			xqlMap.put("disposePerson1", "%" + SQLTool.SQLSpecialChTranfer((String)paramMap.get("disposePerson")) + "%");
			xqlWhere.append(" or c.HandlingPeople2 like :disposePerson2 ");
			SQLTool.SQLAddEscape(xqlWhere);
			xqlMap.put("disposePerson2", "%" + SQLTool.SQLSpecialChTranfer((String)paramMap.get("disposePerson")) + "%");
			xqlWhere.append(" or c.HandlingPeople1Num like :disposePerson3 ");
			SQLTool.SQLAddEscape(xqlWhere);
			xqlMap.put("disposePerson3", "%" + SQLTool.SQLSpecialChTranfer((String)paramMap.get("disposePerson")) + "%");
			xqlWhere.append(" or c.HandlingPeople2Num like :disposePerson4 ");
			SQLTool.SQLAddEscape(xqlWhere);
			xqlMap.put("disposePerson4", "%" + SQLTool.SQLSpecialChTranfer((String)paramMap.get("disposePerson")) + "%");
			xqlWhere.append(")");
		}
		if (ParamMapUtil.isNotBlank(paramMap.get("popedom"))) {
			xqlWhere.append(" and a.popedomCode = :popedom");
			xqlMap.put("popedom", paramMap.get("popedom"));
			xqlWhere.append(" order by c.caseTimeStart desc");
			return dao.findByPage(AlarmInfo.class, xqlSelectb.append(xqlWhere).toString(), xqlMap, pageNo, pageSize);
		}else {
			xqlWhere.append(" order by c.caseTimeStart desc");
			return this.dao.findByPage(CriminalBasicCase.class, xqlSelecta.append(xqlWhere).toString(), xqlMap, pageNo, pageSize);
		}
		

	}

	@SuppressWarnings("unchecked")
	@Override
	public CriminalBasicCase findCriminalCaseById(String criminalCaseId) {
		return (CriminalBasicCase) this.dao.findById(CriminalBasicCase.class, criminalCaseId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public CriminalBasicCase findCriminalCaseByCaseId(String caseId) {
		String xql = "select c from CriminalBasicCase as c where c.caseCode = :caseId";
		Map<String, Object> xqlMap = new HashMap<String, Object>(0);
		xqlMap.put("caseId", caseId);
		List<CriminalBasicCase> list = this.dao.findAllByParams(CriminalBasicCase.class, xql, xqlMap);
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public List<String> findDistinctConditionValues(String comditionKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public AlarmInfo findAlarmInfoById(String alarmId) {
		return (AlarmInfo) this.dao.findById(AlarmInfo.class, alarmId);
	}

	@Override
	public Map<CriminalBasicCase, String> findRelatedCriminalBasicCaseById(String caseId) {
		Map<CriminalBasicCase, String> aMap = new HashMap<CriminalBasicCase, String>();
		List<String> findCaseIds = new ArrayList<String>();
		findCaseIds.add(caseId);
		for(; findCaseIds.size() != 0; ){
			String caseId1 = findCaseIds.get(0);
			findCaseIds.remove(0);
			CriminalBasicCase criminalBasicCase = this.findMainCaseBySonCaseId(caseId1);
			if(criminalBasicCase != null && (!findCaseIds.contains(criminalBasicCase.getCaseCode()) && (!aMap.containsKey(criminalBasicCase)))){
				findCaseIds.add(criminalBasicCase.getCaseCode());
			}
			List<CriminalBasicCase> criminalBasicCases = this.findSonCasesByMainCaseId(caseId1);
			if(criminalBasicCases.size() == 0){
				aMap.put(this.findCriminalCaseByCaseId(caseId1), "否");
			}else{
				aMap.put(this.findCriminalCaseByCaseId(caseId1), "是");
			}
			for(CriminalBasicCase criminalBasicCase2: criminalBasicCases){
				if(!findCaseIds.contains(criminalBasicCase2.getCaseCode()) && (!aMap.containsKey(criminalBasicCase2))){
					findCaseIds.add(criminalBasicCase2.getCaseCode());
				}
			}
		}
		aMap.remove(this.findCriminalCaseByCaseId(caseId));
		return aMap;
	}
	
	@SuppressWarnings("unchecked")
	public CriminalBasicCase findMainCaseBySonCaseId(String caseId){
		CriminalBasicCase sonCase = (CriminalBasicCase) this.dao.findById(CriminalBasicCase.class, caseId);
		CriminalBasicCase mainCase = new CriminalBasicCase();
		if(sonCase != null && sonCase.getMianCaseId() != null){
			return (CriminalBasicCase) this.dao.findById(CriminalBasicCase.class, sonCase.getMianCaseId());
		}else{
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<CriminalBasicCase> findSonCasesByMainCaseId(String caseId){
		List<CriminalBasicCase> sonCases = new ArrayList<CriminalBasicCase>();
		String xql = "select c from CriminalBasicCase as c where c.mianCaseId = ?";
		sonCases = this.dao.findAllByParams(CriminalBasicCase.class, xql, new Object[]{caseId});
		return sonCases;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pager<CriminalBasicCase> findCriminalBasicCasesByQueryConditions(Map<String, Object> queryConditions,
			int pageNo, int pageSize) {
		
		StringBuilder xqlSelecta = new StringBuilder("select c from CriminalBasicCase as c where 1 = 1");
		StringBuilder xqlWhere = new StringBuilder();
		
		Map<String, Object> xqlMap = new HashMap<String, Object>(0);
		if(ParamMapUtil.isNotBlank(queryConditions.get("caseName"))) {
			xqlWhere.append(" and c.caseName like :caseName");
			SQLTool.SQLAddEscape(xqlWhere);
			xqlMap.put("caseName", "%" + SQLTool.SQLSpecialChTranfer((String)queryConditions.get("caseName")) + "%");
		}
		if(ParamMapUtil.isNotBlank(queryConditions.get("caseCode"))) {
			xqlWhere.append(" and c.caseCode like :caseCode");
			SQLTool.SQLAddEscape(xqlWhere);
			xqlMap.put("caseCode", "%" + SQLTool.SQLSpecialChTranfer((String)queryConditions.get("caseCode")) + "%");
		}
		if(ParamMapUtil.isNotBlank(queryConditions.get("disposePerson"))){
			xqlWhere.append(" and (c.HandlingPeople1 like :disposePerson1 ");
			SQLTool.SQLAddEscape(xqlWhere);
			xqlMap.put("disposePerson1", "%" + SQLTool.SQLSpecialChTranfer((String)queryConditions.get("disposePerson")) + "%");
			xqlWhere.append(" or c.HandlingPeople2 like :disposePerson2 ");
			SQLTool.SQLAddEscape(xqlWhere);
			xqlMap.put("disposePerson2", "%" + SQLTool.SQLSpecialChTranfer((String)queryConditions.get("disposePerson")) + "%");
			xqlWhere.append(" or c.HandlingPeople1Num like :disposePerson3 ");
			SQLTool.SQLAddEscape(xqlWhere);
			xqlMap.put("disposePerson3", "%" + SQLTool.SQLSpecialChTranfer((String)queryConditions.get("disposePerson")) + "%");
			xqlWhere.append(" or c.HandlingPeople2Num like :disposePerson4 ");
			SQLTool.SQLAddEscape(xqlWhere);
			xqlMap.put("disposePerson4", "%" + SQLTool.SQLSpecialChTranfer((String)queryConditions.get("disposePerson")) + "%");
			xqlWhere.append(")");
		}
		
		return this.dao.findByPage(CriminalBasicCase.class, xqlSelecta.append(xqlWhere).toString(), xqlMap, pageNo, pageSize);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SufferCaseRelation> findSufferCaseRelationByCase(String caseId) {
		String xql = "select scr from SufferCaseRelation as scr where scr.case_id = ?";
		return this.dao.findAllByParams(SufferCaseRelation.class, xql, new Object[]{caseId}) == null ? new ArrayList<SufferCaseRelation>() : this.dao.findAllByParams(SufferCaseRelation.class, xql, new Object[]{caseId});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CriminalObject> findCriminalObjectByCase(String caseId) {
		String xql = "select co from CriminalObject as co where co.basicCase_id = ?";
		return this.dao.findAllByParams(CriminalObject.class, xql, new Object[]{caseId}) == null ? new ArrayList<CriminalObject>() : this.dao.findAllByParams(CriminalObject.class, xql, new Object[]{caseId});
	}

	@SuppressWarnings("unchecked")
	@Override
	public CaseAttachedInfo findCaseAttachedInfoById(String caseId) {
		String xql = "select c from CaseAttachedInfo as c where c.caseCode = ?";
		return (CaseAttachedInfo) this.dao.findByParams(CaseAttachedInfo.class, xql, new Object[]{caseId});
	}

	@SuppressWarnings("unchecked")
	@Override
	public PersonServiceBean findPersonsByIdNum(String idNum) {
		PersonServiceBean personBean = new PersonServiceBean();
		String xql = "select p from CriminalPerson as p where p.idcardNo = ? order by p.modifiedTime desc";
		List<CriminalPerson> persons = dao.findAllByParams(CriminalPerson.class, xql, new Object[]{idNum});
		if(persons.size() > 0){
			CriminalPerson cp = persons.get(0);
			personBean = this.personModelToServiceBean(cp);
			
			String xql1 = "select c from CriminalBasicCase as c, CaseSupectRelation as r, CriminalPerson as p where c.caseCode = r.case_id and r.person_id = p.personId and p.personId = ? order by c.updateTime desc";
			List<CriminalBasicCase> cases = dao.findAllByParams(CriminalBasicCase.class, xql1, new Object[]{cp.getPersonId()});
			if(cases.size() > 0){
				personBean.setCaseCode(cases.get(0).getCaseCode());
			}
		}
		return personBean;
	}
	
	/**
	 * 将涉案人员基本信息转化为serviceBean
	 * @param cp 涉案人员model
	 * @return 返回PersonServiceBean
	 */
	public PersonServiceBean personModelToServiceBean(CriminalPerson cp){
		PersonServiceBean p = new PersonServiceBean();
		p.setPersonAddress(cp.getAddress());
		if(cp.getBirthdayStart() != null){
			p.setPersonBirthDate(cp.getBirthdayStart().getTime());
		}
		p.setPersonContact(cp.getTelephone());
		p.setPersonId(cp.getPersonId());
		p.setPersonIdNo(cp.getIdcardNo());
		p.setPersonIdType("");
		p.setPersonName(cp.getName());
		p.setPersonSex(cp.getSex());
		p.setDoor(cp.getDoor());
		if("1".equals(cp.getSexCode())){
			p.setPersonSexCode("0000000003001");
		}else if("2".equals(cp.getSexCode())){
			p.setPersonSexCode("0000000003002");
		}else if("0".equals(cp.getSexCode())){
			p.setPersonSexCode("0000000003003");
		}else{
			p.setPersonSexCode("");
		}
		
		return p;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DictionaryItem> findCommunityByUnitCode(String code) {
				String xql = " from DictionaryItem where description = ? ";
		return this.dao.findAllByParams(DictionaryItem.class, xql, new Object[]{code});
	}
}
