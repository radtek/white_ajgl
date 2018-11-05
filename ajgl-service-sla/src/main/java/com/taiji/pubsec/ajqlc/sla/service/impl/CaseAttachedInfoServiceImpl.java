package com.taiji.pubsec.ajqlc.sla.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taiji.persistence.dao.Dao;
import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.sla.bean.AdministrationPersonServiceBean;
import com.taiji.pubsec.ajqlc.sla.bean.CaseServiceBean;
import com.taiji.pubsec.ajqlc.sla.bean.PenalPersonServiceBean;
import com.taiji.pubsec.ajqlc.sla.model.AdministrationPersonRecord;
import com.taiji.pubsec.ajqlc.sla.model.CaseAttachedInfo;
import com.taiji.pubsec.ajqlc.sla.model.CriminalBasicCase;
import com.taiji.pubsec.ajqlc.sla.model.PenalPersonRecord;
import com.taiji.pubsec.ajqlc.sla.service.ICaseAttachedInfoService;
import com.taiji.pubsec.ajqlc.sla.service.ICriminalCaseService;
import com.taiji.pubsec.ajqlc.util.ParamMapUtil;
import com.taiji.pubsec.businesscomponent.organization.model.Person;
import com.taiji.pubsec.businesscomponent.organization.model.Unit;
import com.taiji.pubsec.businesscomponent.organization.service.IPersonService;
import com.taiji.pubsec.businesscomponent.organization.service.IUnitService;
import com.taiji.pubsec.common.tool.sql.SQLTool;

@Service("caseAttachedInfoService")
public class CaseAttachedInfoServiceImpl implements ICaseAttachedInfoService{
	
	@SuppressWarnings("rawtypes")
	@Resource 
	private Dao dao;
	
	@Resource
	IPersonService personService;
	
	@Resource
	ICriminalCaseService criminalCaseService;
	
	@Resource
	IUnitService unitService;
	
	public  static final String ADMIN_CASE = "行政案件"; //案件类型：行政案件
	public static final String PENAL_CASE = "刑事案件"; //案件类型：刑事案件
	public static final String TX_TYPE_XJ = "xj";	//提醒类型：刑拘
	public static final String TX_TYPE_BGJY = "bgjy";	//提醒类型：变更羁押
	public static final String TX_TYPE_TQDB = "tqdb";	//提醒类型：提请逮捕
	public static final String TX_TYPE_PB = "pb";	//提醒类型：批捕
	public static final String TX_TYPE_YSQS = "ysqs";	//提醒类型：移送起诉
	public static final String TX_TYPE_QBHS = "qbhs";	//提醒类型：取保候审
	public static final String TX_TYPE_JSJZ = "jsjz";	//提醒类型：监视居住
	
	public static final String TX_TYPE_XZJL = "xzjl";	//提醒类型：行政拘留
	public static final String TX_TYPE_SQJD = "sqjd";	//提醒类型：社区戒毒
	public static final String TX_TYPE_QZJD = "qzjd";	//提醒类型：强制戒毒

	@SuppressWarnings("unchecked")
	@Override
	public void createCaseAttachedInfo(CaseAttachedInfo caseAttachedInfo, String caseId) {
		CaseAttachedInfo caseInfo = this.findByCaseCode(caseId);
		if(caseInfo != null){
			caseInfo.setDoPerson(caseAttachedInfo.getDoPerson());
			caseInfo.setOneRefundInvestigationTime(caseAttachedInfo.getOneRefundInvestigationTime());
			caseInfo.setTwoRefundInvestigationTime(caseAttachedInfo.getTwoRefundInvestigationTime());
			this.dao.update(caseInfo);
		}else{
			caseInfo = new CaseAttachedInfo();
			caseInfo.setCaseCode(caseId);
			caseInfo.setDoPerson(caseAttachedInfo.getDoPerson());
			caseInfo.setOneRefundInvestigationTime(caseAttachedInfo.getOneRefundInvestigationTime());
			caseInfo.setTwoRefundInvestigationTime(caseAttachedInfo.getTwoRefundInvestigationTime());
			this.dao.save(caseInfo);
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pager<AdministrationPersonServiceBean> findAdminPersonBeanByConditions(Map<String, Object> paramMap, int pageNo,
			int pageSize) {
		StringBuilder xql = new StringBuilder("select a from AdministrationPersonRecord as a, CriminalBasicCase as c where c.caseCode = a.caseCode ");
		Map<String, Object> xqlMap = new HashMap<String, Object>();
		xql.append(" and c.caseClass = :caseClass ");
		xqlMap.put("caseClass", ADMIN_CASE);
		if(ParamMapUtil.isNotBlank(paramMap.get("queryTimeStart"))){
			xql.append(" and a.enterAreaTime >= :queryTimeStart ");
			xqlMap.put("queryTimeStart", new Date((long) paramMap.get("queryTimeStart")));
		}
		if(ParamMapUtil.isNotBlank(paramMap.get("queryTimeEnd"))){
			xql.append(" and a.enterAreaTime < :queryTimeEnd ");
			xqlMap.put("queryTimeEnd", new Date((long) paramMap.get("queryTimeEnd")));
		}
		if(ParamMapUtil.isNotBlank(paramMap.get("attendUnit"))) {
			List<String> unitCodes = new ArrayList<String>();
			unitCodes.add((String) paramMap.get("attendUnit"));
			Unit unit = unitService.findUnitByCode((String) paramMap.get("attendUnit"));
			//查询该单位的所有下级单位 
			List<Unit> subUnits = unitService.findSubUnitsByUnitId(unit.getId());
			for(Unit u: subUnits){
				unitCodes.add(u.getCode());
			}
			
			xql.append(" and c.dqbldwCode in (:attendUnit) ");
			xqlMap.put("attendUnit", unitCodes);
		}
		if(ParamMapUtil.isNotBlank(paramMap.get("caseName"))) {
			xql.append(" and c.caseName like :caseName");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("caseName", "%" + SQLTool.SQLSpecialChTranfer((String) paramMap.get("caseName")) + "%");
		}
		if(ParamMapUtil.isNotBlank(paramMap.get("caseCode"))){
			xql.append(" and c.caseCode like :caseCode ");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("caseCode", "%" + SQLTool.SQLSpecialChTranfer((String) paramMap.get("caseCode")) + "%");
		}
		if(ParamMapUtil.isNotBlank(paramMap.get("ifArchive"))){
			xql.append(" and c.ifArchive = :ifArchive ");
			xqlMap.put("ifArchive", paramMap.get("ifArchive"));
		}
		if(ParamMapUtil.isNotBlank(paramMap.get("disposePerson"))){
			xql.append(" and (c.HandlingPeople1 like :disposePerson1 ");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("disposePerson1", "%" + SQLTool.SQLSpecialChTranfer((String)paramMap.get("disposePerson")) + "%");
			xql.append(" or c.HandlingPeople2 like :disposePerson2 ");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("disposePerson2", "%" + SQLTool.SQLSpecialChTranfer((String)paramMap.get("disposePerson")) + "%");
			xql.append(" or c.HandlingPeople1Num like :disposePerson3 ");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("disposePerson3", "%" + SQLTool.SQLSpecialChTranfer((String)paramMap.get("disposePerson")) + "%");
			xql.append(" or c.HandlingPeople2Num like :disposePerson4 ");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("disposePerson4", "%" + SQLTool.SQLSpecialChTranfer((String)paramMap.get("disposePerson")) + "%");
			xql.append(")");
		}
		
//		xql.append(" and a.enterAreaTime is not null ");
		xql.append(" order by a.enterAreaTime ");
		Pager<AdministrationPersonRecord> adminPager = this.dao.findByPage(AdministrationPersonRecord.class, xql.toString(), xqlMap, pageNo, pageSize);
		Pager<AdministrationPersonServiceBean> adminBeanPager = new Pager<AdministrationPersonServiceBean>();
		for(int i = 0; i < adminPager.getPageList().size(); i++){
			AdministrationPersonServiceBean bean = new AdministrationPersonServiceBean();
			if(adminPager.getPageList().get(i).getSuspectName() != null){
				bean.setSuspectName(adminPager.getPageList().get(i).getSuspectName());
			}else{
				bean.setSuspectName("");
			}
			bean.setSuspectName(adminPager.getPageList().get(i).getSuspectName());
			if(adminPager.getPageList().get(i).getAdministrationArrestTime() != null) {
				bean.setAdministrationArrest(adminPager.getPageList().get(i).getAdministrationArrest());
			}
			if(adminPager.getPageList().get(i).getCommunityDrugStartTime() != null){
				bean.setCommunityDrugDeadline(adminPager.getPageList().get(i).getCommunityDrugDeadline().getTime());
			}
			if(adminPager.getPageList().get(i).getIsolationDrugStartTime() != null){
				bean.setIsolationDrugDeadline(adminPager.getPageList().get(i).getIsolationDrugDeadline().getTime());
			}
			if(adminPager.getPageList().get(i).getEnterAreaTime() != null){
				bean.setEnterAreaTime(adminPager.getPageList().get(i).getEnterAreaTime().getTime());
			}
			
			CriminalBasicCase basicCase = criminalCaseService.findCriminalCaseByCaseId(adminPager.getPageList().get(i).getCaseCode());
			CaseServiceBean caseBean = new CaseServiceBean();
			caseBean.setCaseCode(basicCase.getCaseCode());
			caseBean.setCaseName(basicCase.getCaseName());
			caseBean.setHandlingUnit(basicCase.getDqbldw());
			caseBean.setIfCloseCase(adminPager.getPageList().get(i).getIfCloseCase());
			String polices = "";
			if(basicCase.getHandlingPeople1() != null){
				polices += basicCase.getHandlingPeople1();
				if(basicCase.getHandlingPeople2() != null){
					polices += "," + basicCase.getHandlingPeople2();
				}
			}else if(basicCase.getHandlingPeople2() != null){
				polices += basicCase.getHandlingPeople2();
			}
			caseBean.setHandlingPolice(polices);
			caseBean.setIfArchive(basicCase.getIfArchive());
			CaseAttachedInfo caseInfo = this.findByCaseCode(adminPager.getPageList().get(i).getCaseCode());
			if(caseInfo != null){
				if(caseInfo.getDoPerson() != null){
					Person p = personService.findById(caseInfo.getDoPerson());
					if(p != null){
						caseBean.setDoPeople(p.getName());
					}
				}
			}
			
			bean.setCaseInfo(caseBean);
			
			adminBeanPager.getPageList().add(bean);
		}
			
		adminBeanPager.setTotalNum(adminPager.getTotalNum());
		return adminBeanPager;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CaseAttachedInfo findByCaseCode(String caseCode) {
		String xql = "select c from CaseAttachedInfo as c where c.caseCode = ?";
		return (CaseAttachedInfo) this.dao.findByParams(CaseAttachedInfo.class, xql, new Object[]{caseCode});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdministrationPersonRecord> findAdminPersonRecordByCaseCode(String caseCode) {
		String xql = "select a from AdministrationPersonRecord as a where a.caseCode = ?";
		return this.dao.findAllByParams(AdministrationPersonRecord.class, xql, new Object[]{caseCode});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pager<PenalPersonServiceBean> findPenalPersonServiceBeanByConditions(Map<String, Object> paramMap,
			int pageNo, int pageSize) {
		StringBuilder xql = new StringBuilder("select a from PenalPersonRecord as a, CriminalBasicCase as c where c.caseCode = a.caseCode ");
		Map<String, Object> xqlMap = new HashMap<String, Object>();
		xql.append(" and c.caseClass = :caseClass ");
		xqlMap.put("caseClass", PENAL_CASE);
		if(ParamMapUtil.isNotBlank(paramMap.get("queryTimeStart"))){
			xql.append(" and a.enterAreaTime >= :queryTimeStart ");
			xqlMap.put("queryTimeStart", new Date((long) paramMap.get("queryTimeStart")));
		}
		if(ParamMapUtil.isNotBlank(paramMap.get("queryTimeEnd"))){
			xql.append(" and a.enterAreaTime < :queryTimeEnd ");
			xqlMap.put("queryTimeEnd", new Date((long) paramMap.get("queryTimeEnd")));
		}
		if(ParamMapUtil.isNotBlank(paramMap.get("attendUnit"))) {
			List<String> unitCodes = new ArrayList<String>();
			unitCodes.add((String) paramMap.get("attendUnit"));
			Unit unit = unitService.findUnitByCode((String) paramMap.get("attendUnit"));
			//查询该单位的所有下级单位 
			List<Unit> subUnits = unitService.findSubUnitsByUnitId(unit.getId());
			for(Unit u: subUnits){
				unitCodes.add(u.getCode());
			}
			
			xql.append(" and c.dqbldwCode in (:attendUnit) ");
			xqlMap.put("attendUnit", unitCodes);
		}
		if(ParamMapUtil.isNotBlank(paramMap.get("caseName"))) {
			xql.append(" and c.caseName like :caseName");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("caseName", "%" + SQLTool.SQLSpecialChTranfer((String) paramMap.get("caseName")) + "%");
		}
		if(ParamMapUtil.isNotBlank(paramMap.get("caseCode"))){
			xql.append(" and c.caseCode like :caseCode ");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("caseCode", "%" + SQLTool.SQLSpecialChTranfer((String) paramMap.get("caseCode")) + "%");
		}
		if(ParamMapUtil.isNotBlank(paramMap.get("ifArchive"))){
			xql.append(" and c.ifArchive = :ifArchive ");
			xqlMap.put("ifArchive", paramMap.get("ifArchive"));
		}
		if(ParamMapUtil.isNotBlank(paramMap.get("disposePerson"))){
			xql.append(" and (c.HandlingPeople1 like :disposePerson1 ");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("disposePerson1", "%" + SQLTool.SQLSpecialChTranfer((String)paramMap.get("disposePerson")) + "%");
			xql.append(" or c.HandlingPeople2 like :disposePerson2 ");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("disposePerson2", "%" + SQLTool.SQLSpecialChTranfer((String)paramMap.get("disposePerson")) + "%");
			xql.append(" or c.HandlingPeople1Num like :disposePerson3 ");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("disposePerson3", "%" + SQLTool.SQLSpecialChTranfer((String)paramMap.get("disposePerson")) + "%");
			xql.append(" or c.HandlingPeople2Num like :disposePerson4 ");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("disposePerson4", "%" + SQLTool.SQLSpecialChTranfer((String)paramMap.get("disposePerson")) + "%");
			xql.append(")");
		}
		
//		xql.append(" and a.enterAreaTime is not null ");
		xql.append(" order by a.enterAreaTime, a.caseCode");
		Pager<PenalPersonRecord> penalPager = this.dao.findByPage(PenalPersonRecord.class, xql.toString(), xqlMap, pageNo, pageSize);
		Pager<PenalPersonServiceBean> penalBeanPager = new Pager<PenalPersonServiceBean>();
		for(int i = 0; i < penalPager.getPageList().size(); i++){
			PenalPersonServiceBean bean = new PenalPersonServiceBean();
			if(penalPager.getPageList().get(i).getSuspectName() != null){
				bean.setSuspectName(penalPager.getPageList().get(i).getSuspectName());
			}else{
				bean.setSuspectName("");
			}
			if(penalPager.getPageList().get(i).getDetainedDeadline() != null){
				Calendar cal = Calendar.getInstance();
				cal.setTime(penalPager.getPageList().get(i).getDetainedDeadline());
				cal.add(Calendar.DAY_OF_MONTH, 3);
				bean.setDetainedDeadline(cal.getTime().getTime());
			}
			if(penalPager.getPageList().get(i).getExtendedDetainedStartTime() != null){
				bean.setExtendedDetainedDeadline(penalPager.getPageList().get(i).getExtendedDetainedDeadline().getTime());
			}
			if(penalPager.getPageList().get(i).getArrestDeadline() != null){
				Calendar cal = Calendar.getInstance();
				cal.setTime(penalPager.getPageList().get(i).getArrestDeadline());
				cal.add(Calendar.MONTH, 2);
				bean.setRemovalDeadline(cal.getTime().getTime());
			}
			if(penalPager.getPageList().get(i).getBailDeadline() != null){
				bean.setBailDeadline(penalPager.getPageList().get(i).getBailDeadline().getTime());
			}
			if(penalPager.getPageList().get(i).getResidentialSurveillanceDeadline() != null){
				bean.setResidentialSurveillanceDeadline(penalPager.getPageList().get(i).getResidentialSurveillanceDeadline().getTime());
			}
			if(bean.getExtendedDetainedDeadline() != null){
				bean.setToArrestDeadline(bean.getExtendedDetainedDeadline());
			}else if(bean.getDetainedDeadline() != null){
				bean.setToArrestDeadline(bean.getDetainedDeadline());
			}
			if(bean.getToArrestDeadline() != null){
				Date date = new Date(bean.getToArrestDeadline());
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.DAY_OF_MONTH, 7);
				bean.setApprovingArrestDeadline(cal.getTime().getTime());
			}
			if(penalPager.getPageList().get(i).getEnterAreaTime() != null){
				bean.setEnterAreaTime(penalPager.getPageList().get(i).getEnterAreaTime().getTime());
			}
			
			CriminalBasicCase basicCase = criminalCaseService.findCriminalCaseByCaseId(penalPager.getPageList().get(i).getCaseCode());
			CaseServiceBean caseBean = new CaseServiceBean();
			caseBean.setCaseCode(basicCase.getCaseCode());
			caseBean.setCaseName(basicCase.getCaseName());
			caseBean.setHandlingUnit(basicCase.getDqbldw());
			String polices = "";
			if(basicCase.getHandlingPeople1() != null){
				polices += basicCase.getHandlingPeople1();
				if(basicCase.getHandlingPeople2() != null){
					polices += "," + basicCase.getHandlingPeople2();
				}
			}else if(basicCase.getHandlingPeople2() != null){
				polices += basicCase.getHandlingPeople2();
			}
			caseBean.setHandlingPolice(polices);
			caseBean.setIfArchive(basicCase.getIfArchive());
			CaseAttachedInfo caseInfo = this.findByCaseCode(penalPager.getPageList().get(i).getCaseCode());
			if(caseInfo != null){
				if(caseInfo.getDoPerson() != null){
					Person p = personService.findById(caseInfo.getDoPerson());
					if(p != null){
						caseBean.setDoPeople(p.getName());
					}
				}
				if(caseInfo.getOneRefundInvestigationTime() != null){
//					Calendar cal = Calendar.getInstance();
//					cal.setTime(caseInfo.getOneRefundInvestigationTime());
//					cal.add(Calendar.DAY_OF_MONTH, 30);
					caseBean.setOneRefundInvestigationTime(caseInfo.getOneRefundInvestigationTime().getTime());
				}
				if(caseInfo.getTwoRefundInvestigationTime() != null){
//					Calendar cal = Calendar.getInstance();
//					cal.setTime(caseInfo.getTwoRefundInvestigationTime());
//					cal.add(Calendar.DAY_OF_MONTH, 30);
					caseBean.setTwoRefundInvestigationTime(caseInfo.getTwoRefundInvestigationTime().getTime());
				}
				if(caseInfo.getSolvedTime() != null){
					caseBean.setIfSolved("是");
				}else{
					caseBean.setIfSolved("否");
				}
			}
			
			bean.setCaseInfo(caseBean);
			
			penalBeanPager.getPageList().add(bean);
		}
			
		penalBeanPager.setTotalNum(penalPager.getTotalNum());
		return penalBeanPager;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pager<PenalPersonServiceBean> findPenalDqtxByConditions(String txType, Date from, Date to, int pageNo, int pageSize) {
		StringBuilder xql = new StringBuilder("select p from PenalPersonRecord as p where 1 = 1 ");
		Map<String,  Object> xqlMap = new HashMap<String, Object>();
		if(TX_TYPE_XJ.equals(txType)){
			xql.append(" and p.detainedDeadline >= :from ");
			xqlMap.put("from", from);
			xql.append(" and p.detainedDeadline < :to ");
			xqlMap.put("to", to);
		}else if(TX_TYPE_BGJY.equals(txType)){
			xql.append(" and p.extendedDetainedDeadline >= :from ");
			xqlMap.put("from", from);
			xql.append(" and p.extendedDetainedDeadline < :to ");
			xqlMap.put("to", to);
		}else if(TX_TYPE_TQDB.equals(txType)){
			xql.append(" and (p.id in (select p1.id from PenalPersonRecord as p1 where p1.extendedDetainedDeadline is null ");
			xql.append(" and p.detainedDeadline >= :from ");
			xqlMap.put("from", from);
			xql.append(" and p.detainedDeadline < :to ");
			xqlMap.put("to", to);
			xql.append(") or p.id in (select p2.id from PenalPersonRecord as p2 where p2.extendedDetainedDeadline is not null ");
			xql.append(" and p.extendedDetainedDeadline >= :from1 ");
			xqlMap.put("from1", from);
			xql.append(" and p.extendedDetainedDeadline < :to1 ");
			xqlMap.put("to1", to);
			xql.append("))");
		}else if(TX_TYPE_PB.equals(txType)){
			Calendar cal = Calendar.getInstance();
			cal.setTime(from);
			cal.add(Calendar.DAY_OF_MONTH, -7);
			from = cal.getTime();
			cal.setTime(to);
			cal.add(Calendar.DAY_OF_MONTH, -7);
			to = cal.getTime();
			
			xql.append(" and (p.id in (select p1.id from PenalPersonRecord as p1 where p1.extendedDetainedDeadline is null ");
			xql.append(" and p.detainedDeadline >= :from ");
			xqlMap.put("from", from);
			xql.append(" and p.detainedDeadline < :to ");
			xqlMap.put("to", to);
			xql.append(") or p.id in (select p2.id from PenalPersonRecord as p2 where p2.extendedDetainedDeadline is not null ");
			xql.append(" and p.extendedDetainedDeadline >= :from ");
			xqlMap.put("from", from);
			xql.append(" and p.extendedDetainedDeadline < :to ");
			xqlMap.put("to", to);
			xql.append("))");
		}else if(TX_TYPE_YSQS.equals(txType)){
			Calendar cal = Calendar.getInstance();
			cal.setTime(from);
			cal.add(Calendar.MONTH, -2);
			from = cal.getTime();
			cal.setTime(to);
			cal.add(Calendar.MONTH, -2);
			to = cal.getTime();
			xql.append(" and p.arrestDeadline >= :from ");
			xqlMap.put("from", from);
			xql.append(" and p.arrestDeadline < :to ");
			xqlMap.put("to", to);
		}else if(TX_TYPE_QBHS.equals(txType)){
			xql.append(" and p.bailDeadline >= :from ");
			xqlMap.put("from", from);
			xql.append(" and p.bailDeadline < :to ");
			xqlMap.put("to", to);
		}else if(TX_TYPE_JSJZ.equals(txType)){
			xql.append(" and p.residentialSurveillanceDeadline >= :from ");
			xqlMap.put("from", from);
			xql.append(" and p.residentialSurveillanceDeadline < :to ");
			xqlMap.put("to", to);
		}
		
//		xql.append(" and p.enterAreaTime is not null ");
		xql.append(" order by p.enterAreaTime desc, p.caseCode");
		Pager<PenalPersonRecord> records = dao.findByPage(PenalPersonRecord.class, xql.toString(), xqlMap, pageNo, pageSize);
		
		Pager<PenalPersonServiceBean> beans = new Pager<PenalPersonServiceBean>();
		for(PenalPersonRecord record: records.getPageList()){
			PenalPersonServiceBean bean = new PenalPersonServiceBean();
			bean.setSuspectName(record.getSuspectName());
			
			CriminalBasicCase basicCase = criminalCaseService.findCriminalCaseByCaseId(record.getCaseCode());
			CaseServiceBean caseBean = new CaseServiceBean();
			if(basicCase != null){
				caseBean.setCaseCode(basicCase.getCaseCode());
				caseBean.setCaseName(basicCase.getCaseName());
				caseBean.setHandlingUnit(basicCase.getDqbldw());
				
				String polices = "";
				if(basicCase.getHandlingPeople1() != null){
					polices += basicCase.getHandlingPeople1();
					if(basicCase.getHandlingPeople2() != null){
						polices += "," + basicCase.getHandlingPeople2();
					}
				}else if(basicCase.getHandlingPeople2() != null){
					polices += basicCase.getHandlingPeople2();
				}
				caseBean.setHandlingPolice(polices);
			}
			
			CaseAttachedInfo caseInfo = this.findByCaseCode(record.getCaseCode());
			if(caseInfo != null){
				if(caseInfo.getDoPerson() != null){
					Person p = personService.findById(caseInfo.getDoPerson());
					if(p != null){
						caseBean.setDoPeople(p.getName());
					}
				}
			}
			bean.setCaseInfo(caseBean);
			beans.getPageList().add(bean);
		}
		beans.setTotalNum(records.getTotalNum());
		return beans;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pager<AdministrationPersonServiceBean> findAdminDqtxByConditions(String txType, Date from, Date to,
			int pageNo, int pageSize) {
		StringBuilder xql = new StringBuilder("select a from AdministrationPersonRecord as a where 1 = 1 ");
		Map<String,  Object> xqlMap = new HashMap<String, Object>();
		if(TX_TYPE_XZJL.equals(txType)){
			xql.append(" and a.administrationArrestTime >= :from ");
			xqlMap.put("from", from);
			xql.append(" and a.administrationArrestTime < :to ");
			xqlMap.put("to", to);
		}else if(TX_TYPE_SQJD.equals(txType)){
			xql.append(" and a.communityDrugDeadline >= :from ");
			xqlMap.put("from", from);
			xql.append(" and a.communityDrugDeadline < :to ");
			xqlMap.put("to", to);
		}else if(TX_TYPE_QZJD.equals(txType)){
			xql.append(" and a.isolationDrugDeadline >= :from ");
			xqlMap.put("from", from);
			xql.append(" and a.isolationDrugDeadline < :to ");
			xqlMap.put("to", to);
		}
		
//		xql.append(" and a.enterAreaTime is not null ");
		xql.append(" order by a.enterAreaTime desc, a.caseCode");
		Pager<AdministrationPersonRecord> records = dao.findByPage(AdministrationPersonRecord.class, xql.toString(), xqlMap, pageNo, pageSize);
		
		Pager<AdministrationPersonServiceBean> beans = new Pager<AdministrationPersonServiceBean>();
		for(AdministrationPersonRecord record: records.getPageList()){
			AdministrationPersonServiceBean bean = new AdministrationPersonServiceBean();
			bean.setSuspectName(record.getSuspectName());
			
			CriminalBasicCase basicCase = criminalCaseService.findCriminalCaseByCaseId(record.getCaseCode());
			CaseServiceBean caseBean = new CaseServiceBean();
			if(basicCase != null){
				caseBean.setCaseCode(basicCase.getCaseCode());
				caseBean.setCaseName(basicCase.getCaseName());
				caseBean.setHandlingUnit(basicCase.getDqbldw());
				
				String polices = "";
				if(basicCase.getHandlingPeople1() != null){
					polices += basicCase.getHandlingPeople1();
					if(basicCase.getHandlingPeople2() != null){
						polices += "," + basicCase.getHandlingPeople2();
					}
				}else if(basicCase.getHandlingPeople2() != null){
					polices += basicCase.getHandlingPeople2();
				}
				caseBean.setHandlingPolice(polices);
			}
			
			CaseAttachedInfo caseInfo = this.findByCaseCode(record.getCaseCode());
			if(caseInfo != null){
				if(caseInfo.getDoPerson() != null){
					Person p = personService.findById(caseInfo.getDoPerson());
					if(p != null){
						caseBean.setDoPeople(p.getName());
					}
				}
			}
			bean.setCaseInfo(caseBean);
			beans.getPageList().add(bean);
		}
		beans.setTotalNum(records.getTotalNum());
		return beans;
	}


}
