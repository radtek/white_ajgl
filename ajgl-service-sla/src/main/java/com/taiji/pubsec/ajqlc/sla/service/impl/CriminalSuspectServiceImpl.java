package com.taiji.pubsec.ajqlc.sla.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taiji.persistence.dao.Dao;
import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.sla.model.CaseExecutionProcess;
import com.taiji.pubsec.ajqlc.sla.model.CaseSupectRelation;
import com.taiji.pubsec.ajqlc.sla.model.CriminalBasicCase;
import com.taiji.pubsec.ajqlc.sla.model.CriminalPerson;
import com.taiji.pubsec.ajqlc.sla.model.SufferCaseRelation;
import com.taiji.pubsec.ajqlc.sla.service.ICriminalSuspectService;
import com.taiji.pubsec.ajqlc.sla.service.IUnsolvedCaseQueryService;
import com.taiji.pubsec.ajqlc.util.ParamMapUtil;
import com.taiji.pubsec.businesscomponent.dictionary.model.DictionaryItem;
import com.taiji.pubsec.common.tool.sql.SQLTool;

@Service("criminalSuspectService")
public class CriminalSuspectServiceImpl implements ICriminalSuspectService {
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@Resource
	private IUnsolvedCaseQueryService unsolvedCaseQueryService;
	
	public static final String DICT_TYPE__CODE_XZQH = "xzqh";//字典类型code：行政区划

	@SuppressWarnings("unchecked")
	@Override
	public Pager<CaseSupectRelation> findCriminalSuspectByCondition(Map<String, Object> paramMap, int pageNo,
			int pageSize) {
		StringBuilder xqlSelect = new StringBuilder("select csr from CriminalPerson as p, CaseSupectRelation as csr, CriminalBasicCase as xx ");
		
		StringBuilder xqlWhere = new StringBuilder(" where 1 = 1 and p.personId = csr.person_id and csr.case_id=xx.caseCode ");
		Map<String, Object> xqlMap = new HashMap<String, Object>(0);
		if(ParamMapUtil.isNotBlank(paramMap.get("name"))) {
			xqlWhere.append(" and p.name like :name");
			SQLTool.SQLAddEscape(xqlWhere);
			xqlMap.put("name", "%" + SQLTool.SQLSpecialChTranfer((String)paramMap.get("name")) + "%");
		}
		if(ParamMapUtil.isNotBlank(paramMap.get("sex"))) {
			xqlWhere.append(" and p.sexCode = :sex");
			xqlMap.put("sex", paramMap.get("sex"));
		}
		if(ParamMapUtil.isNotBlank(paramMap.get("birthdayStart"))) {
			xqlWhere.append(" and p.birthdayStart >= :birthdayStart");
			xqlMap.put("birthdayStart", paramMap.get("birthdayStart"));
		}
		if(ParamMapUtil.isNotBlank(paramMap.get("birthdayEnd"))) {
			xqlWhere.append(" and p.birthdayStart < :birthdayEnd");
			xqlMap.put("birthdayEnd", paramMap.get("birthdayEnd"));
		}
		if(ParamMapUtil.isNotBlank(paramMap.get("idNumber"))) {
			xqlWhere.append(" and p.idcardNo like :idNumber");
			SQLTool.SQLAddEscape(xqlWhere);
			xqlMap.put("idNumber", "%" + SQLTool.SQLSpecialChTranfer((String)paramMap.get("idNumber")) + "%");
		}
		if(ParamMapUtil.isNotBlank(paramMap.get("address"))) {
			xqlWhere.append(" and p.addressDetail like :address");
			SQLTool.SQLAddEscape(xqlWhere);
			xqlMap.put("address", "%" + SQLTool.SQLSpecialChTranfer((String)paramMap.get("address")) + "%");
		}
		if(ParamMapUtil.isNotBlank(paramMap.get("nativePlace"))) {
			xqlWhere.append(" and p.doorDetail like :nativePlace");
			SQLTool.SQLAddEscape(xqlWhere);
			xqlMap.put("nativePlace", "%" + SQLTool.SQLSpecialChTranfer((String)paramMap.get("nativePlace")) + "%");
		}
		if(ParamMapUtil.isNotBlank(paramMap.get("tone"))) {
			List<DictionaryItem> items = unsolvedCaseQueryService.findAllSubDictItemsBySupCode((String) paramMap.get("tone"), DICT_TYPE__CODE_XZQH);
			List<String> itemCodes = new ArrayList<String>();
			itemCodes.add((String) paramMap.get("tone"));
			for(DictionaryItem item: items){
				itemCodes.add(item.getCode());
			}
			
			xqlWhere.append(" and p.toneCode in (:tone) ");
			xqlMap.put("tone", itemCodes);
		}
		if(ParamMapUtil.isNotBlank(paramMap.get("cases"))) {
			xqlWhere.append(" and csr.case_id like :cases");
			SQLTool.SQLAddEscape(xqlWhere);
			xqlMap.put("cases", "%" + SQLTool.SQLSpecialChTranfer((String)paramMap.get("cases")) + "%");
			
		}
		if(ParamMapUtil.isNotBlank(paramMap.get("caseCode"))){
			xqlWhere.append(" and xx.caseCode like :caseCode ");
			SQLTool.SQLAddEscape(xqlWhere);
			xqlMap.put("caseCode", "%" + SQLTool.SQLSpecialChTranfer((String)paramMap.get("caseCode")) + "%");
		}
		if(ParamMapUtil.isNotBlank(paramMap.get("caseName"))){
			xqlWhere.append(" and xx.caseName like :caseName ");
			SQLTool.SQLAddEscape(xqlWhere);
			xqlMap.put("caseName", "%" + SQLTool.SQLSpecialChTranfer((String)paramMap.get("caseName")) + "%");
		}
		if(ParamMapUtil.isNotBlank(paramMap.get("disposePerson"))){
			xqlWhere.append(" and (xx.HandlingPeople1 like :disposePerson1 ");
			SQLTool.SQLAddEscape(xqlWhere);
			xqlMap.put("disposePerson1", "%" + SQLTool.SQLSpecialChTranfer((String)paramMap.get("disposePerson")) + "%");
			xqlWhere.append(" or xx.HandlingPeople2 like :disposePerson2 ");
			SQLTool.SQLAddEscape(xqlWhere);
			xqlMap.put("disposePerson2", "%" + SQLTool.SQLSpecialChTranfer((String)paramMap.get("disposePerson")) + "%");
			xqlWhere.append(" or xx.HandlingPeople1Num like :disposePerson3 ");
			SQLTool.SQLAddEscape(xqlWhere);
			xqlMap.put("disposePerson3", "%" + SQLTool.SQLSpecialChTranfer((String)paramMap.get("disposePerson")) + "%");
			xqlWhere.append(" or xx.HandlingPeople2Num like :disposePerson4 ");
			SQLTool.SQLAddEscape(xqlWhere);
			xqlMap.put("disposePerson4", "%" + SQLTool.SQLSpecialChTranfer((String)paramMap.get("disposePerson")) + "%");
			xqlWhere.append(")");
		}
		xqlWhere.append(" order by xx.caseTimeStart desc ");
		return this.dao.findByPage(CaseSupectRelation.class, xqlSelect.append(xqlWhere).toString(), xqlMap, pageNo, pageSize);
	}

	@SuppressWarnings("unchecked")
	@Override
	public CriminalPerson findCriminalSuspectById(String criminalSuspectId) {
		return (CriminalPerson) this.dao.findById(CriminalPerson.class, criminalSuspectId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CriminalBasicCase> findCriminalBasicCasesBySuspectId(String criminalSuspectId) {
		
		StringBuilder xql = new StringBuilder("select distinct c from CriminalBasicCase as c, CaseSupectRelation as cr, CriminalPerson as p where "
				+ "p.personId = cr.person_id and c.caseCode = cr.case_id and p.personId = :criminalSuspectId");
		Map<String, Object> xqlMap = new HashMap<String, Object>();
		xqlMap.put("criminalSuspectId", criminalSuspectId);
		return dao.findAllByParams(CriminalBasicCase.class, xql.toString(), xqlMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CaseSupectRelation> findCaseSupectRelationBySuspectId(String criminalSuspectId) {
		String xql = "select distinct csr from CaseSupectRelation as csr where csr.person_id = ? order by csr.updateTime desc";
		return this.dao.findAllByParams(CaseSupectRelation.class, xql, new Object[]{criminalSuspectId});
	}

	@SuppressWarnings("unchecked")
	@Override
	public CriminalBasicCase findCriminalBasicCaseByCaseSupectRelationId(String relationId) {
		CaseSupectRelation caseSupectRelation = (CaseSupectRelation) this.dao.findById(CaseSupectRelation.class, relationId);
		CriminalBasicCase criminalBasicCase = new CriminalBasicCase();
		if(caseSupectRelation != null){
			criminalBasicCase = caseSupectRelation.getBasicCase();
		}
		return criminalBasicCase;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CaseSupectRelation findCaseSupectRelationById(String relationId) {
		return (CaseSupectRelation) this.dao.findById(CaseSupectRelation.class, relationId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CaseSupectRelation> findCaseSuspectRelationByCase(String caseId) {
		String xql = "select csr from CaseSupectRelation as csr where csr.case_id = :caseId";
		Map<String, Object> xqlMap = new HashMap<String, Object>();
		xqlMap.put("caseId", caseId);
		return dao.findAllByParams(CaseExecutionProcess.class, xql, xqlMap) == null ? new ArrayList<CaseSupectRelation>() : dao.findAllByParams(CaseExecutionProcess.class, xql, xqlMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SufferCaseRelation> findSufferCaseRelationsByCase(String caseId) {
		String xql = "select s from SufferCaseRelation as s where s.case_id = :caseId";
		Map<String, Object> xqlMap = new HashMap<String, Object>();
		xqlMap.put("caseId", caseId);
		return dao.findAllByParams(SufferCaseRelation.class, xql, xqlMap) == null ? new ArrayList<SufferCaseRelation>() : dao.findAllByParams(SufferCaseRelation.class, xql, xqlMap);
	}


}
