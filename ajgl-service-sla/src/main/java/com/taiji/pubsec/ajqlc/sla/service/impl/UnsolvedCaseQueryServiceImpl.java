package com.taiji.pubsec.ajqlc.sla.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.taiji.persistence.dao.Dao;
import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.sla.bean.UnsolvedCaseServiceBean;
import com.taiji.pubsec.ajqlc.sla.service.IUnsolvedCaseQueryService;
import com.taiji.pubsec.ajqlc.util.ParamMapUtil;
import com.taiji.pubsec.businesscomponent.dictionary.model.DictionaryItem;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryItemService;
import com.taiji.pubsec.businesscomponent.organization.model.Unit;
import com.taiji.pubsec.businesscomponent.organization.service.IUnitService;

@Service("unsolvedCaseQueryService")
public class UnsolvedCaseQueryServiceImpl implements IUnsolvedCaseQueryService {

	@SuppressWarnings("rawtypes")
	@Resource 
	private Dao dao;
	
	@Resource
	JdbcTemplate jdbcTemplate;
	
	@Resource
	IUnitService unitService;
	
	@Resource
	IDictionaryItemService dictionaryItemService;
	
	public static final String DICT_TYPE__CODE_CASESORT = "ajlb";//字典类型code：案件类别
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(UnsolvedCaseQueryServiceImpl.class);
	
	@Override
	public List<DictionaryItem> findAllSubDictItemsBySupCode(String supCode, String type) {
		List<DictionaryItem> dictList = new ArrayList<DictionaryItem>();
		DictionaryItem supItem = dictionaryItemService.findDictionaryItemByDicTypeCodeAndItemCode(type, supCode, null);
		
		List<DictionaryItem> tempList =  new ArrayList<DictionaryItem>();
		tempList.addAll(supItem.getSubItems());
		do{
			dictList.addAll(tempList);
			int size = tempList.size();
			for(int i = 0; i < tempList.size(); i++){
				tempList.addAll(tempList.get(i).getSubItems());
			}
			
			for(int j = size - 1; j >= 0; j--){
				tempList.remove(j);
			}
			
		}while(tempList.size() > 0);
		return dictList;
	}
	
	
	private String translateInParameter(List<String> list){
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		for(int i=0; i<list.size(); i++){
			sb.append("\"").append(list.get(i)).append("\"");
			if(i<list.size()-1){
				sb.append(",");
			}
		}
		sb.append(")");
		return sb.toString();
	}


	@Override
	public Map<Pager<UnsolvedCaseServiceBean>, Long> findUnsolvedCaseByConditions(Map<String, Object> paramMap, int pageNo,
			int pageSize) {
		Map<Pager<UnsolvedCaseServiceBean>, Long> resultMap = new HashMap<Pager<UnsolvedCaseServiceBean>, Long>();
		Pager<UnsolvedCaseServiceBean> beanPager = new Pager<UnsolvedCaseServiceBean>();
		
		StringBuilder sqlCount = new StringBuilder("select count(*) from t_sla_ajjbxx as c, t_sla_ajfjxx as x where c.caseId = x.caseCode ");
		StringBuilder sql = new StringBuilder("select * from t_sla_ajjbxx as c, t_sla_ajfjxx as x where c.caseId = x.caseCode ");
		StringBuilder sqlWhere = new StringBuilder(" ");
		List<Object> sqlList = new ArrayList<Object>();
		
		sqlWhere.append("and c.caseClass = ? ");
		sqlList.add(paramMap.get("caseClass"));
		if("刑事案件".equals(paramMap.get("caseClass"))){
			sqlWhere.append("and x.filingTime >= ? ");
			sqlList.add(paramMap.get("queryTimeStart"));
			sqlWhere.append("and x.filingTime < ? ");
			sqlList.add(paramMap.get("queryTimeEnd"));
		}else if("行政案件".equals(paramMap.get("caseClass"))){
			sqlWhere.append("and x.acceptingTime >= ? ");
			sqlList.add(paramMap.get("queryTimeStart"));
			sqlWhere.append("and x.acceptingTime < ? ");
			sqlList.add(paramMap.get("queryTimeEnd"));
		}
		if(ParamMapUtil.isNotBlank(paramMap.get("caseCodeOrName"))){
			sqlWhere.append(" and (c.caseId like ? ");
			sqlList.add("%" + paramMap.get("caseCodeOrName") + "%");
			sqlWhere.append(" or c.caseName like ? ");
			sqlList.add("%" + paramMap.get("caseCodeOrName") + "%");
			sqlWhere.append(" ) ");
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
			String indqbldwCode = translateInParameter(unitCodes);
			sqlWhere.append(" and c.dqbldwCode in ").append(indqbldwCode);
		}
		if(ParamMapUtil.isNotBlank(paramMap.get("disposePerson"))){
			sqlWhere.append("and (c.HandlingPeople1 like ? ");
			sqlList.add("%" + paramMap.get("disposePerson") + "%");
			sqlWhere.append(" or c.HandlingPeople2 like ? ");
			sqlList.add("%" + paramMap.get("disposePerson") + "%");
			sqlWhere.append(" or c.HandlingPeople1Num like ? ");
			sqlList.add("%" + paramMap.get("disposePerson") + "%");
			sqlWhere.append(" or c.HandlingPeople2Num like ? ");
			sqlList.add("%" + paramMap.get("disposePerson") + "%");
			sqlWhere.append(")");
		}
		if(ParamMapUtil.isNotBlank(paramMap.get("caseSort"))) {
			List<DictionaryItem> items = this.findAllSubDictItemsBySupCode((String) paramMap.get("caseSort"), DICT_TYPE__CODE_CASESORT);
			List<String> itemCodes = new ArrayList<String>();
			itemCodes.add((String) paramMap.get("caseSort"));
			for(DictionaryItem item: items){
				itemCodes.add(item.getCode());
			}
			
			String incaseSortCode = translateInParameter(itemCodes);
			sqlWhere.append(" and c.caseSortCode in ").append(incaseSortCode);
		}
		if(ParamMapUtil.isNotBlank(paramMap.get("ifSolved"))){
			sqlWhere.append("and x.ifSolved = ? ");
			sqlList.add(paramMap.get("ifSolved"));
		}
		
		sqlCount.append(sqlWhere);
		List<Integer> countNum = jdbcTemplate.query(sqlCount.toString(), sqlList.toArray(), new RowMapper<Integer>(){

			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				Integer i = Integer.valueOf(rs.getString(1));
				return i;
			}
			
		});
		
		sql.append(sqlWhere).append("limit ?,? ");
		sqlList.add(pageNo * pageSize);
		sqlList.add(pageSize);
		
		List<UnsolvedCaseServiceBean> caseList = jdbcTemplate.query(sql.toString(), sqlList.toArray(), new RowMapper<UnsolvedCaseServiceBean>(){

			@Override
			public UnsolvedCaseServiceBean mapRow(ResultSet rs, int rowNum) throws SQLException {
				UnsolvedCaseServiceBean bean = new UnsolvedCaseServiceBean();
				bean.setCaseCode(rs.getString("caseId"));
				bean.setCaseName(rs.getString("caseName"));
				bean.setCaseSort(rs.getString("caseSort"));
				bean.setDetail(rs.getString("details"));
				bean.setHappenedAddress(rs.getString("caseAddress"));
				bean.setHappenedTime(rs.getString("caseTimeStart"));
				bean.setSponsor(rs.getString("dqbldw"));
				bean.setCaseClass(rs.getString("caseClass"));
				bean.setCaseSortCode(rs.getString("caseSortCode"));
				bean.setDqbldwCode(rs.getString("dqbldwCode"));
				bean.setState(rs.getString("caseState"));
				String investigator = "";
				if(!StringUtils.isBlank(rs.getString("HandlingPeople1"))){
					investigator += rs.getString("HandlingPeople1");
					if(!StringUtils.isBlank(rs.getString("HandlingPeople2"))){
						investigator += "," + rs.getString("HandlingPeople2");
					}
				}else if(!StringUtils.isBlank(rs.getString("HandlingPeople2"))){
					investigator += rs.getString("HandlingPeople2");
				}
				bean.setInvestigator(investigator);
				bean.setIfSolved(rs.getString("ifSolved"));
				return bean;
			}
		});
		
		beanPager.getPageList().addAll(caseList);
		beanPager.setTotalNum(countNum.get(0));
		
		sqlList.remove(sqlList.size() - 1);
		sqlList.remove(sqlList.size() - 1);
		//当“是否破案”查询全部时，查询未破/未结案件的数量
		StringBuilder sqlCountUnsolved = new StringBuilder("select count(*) from t_sla_ajjbxx as c, t_sla_ajfjxx as x where c.caseId = x.caseCode ");
		sqlWhere.append("and x.ifSolved = ? ");
		sqlList.add("否");
		sqlCountUnsolved.append(sqlWhere);
		List<Long> countUnsolvedNum = jdbcTemplate.query(sqlCountUnsolved.toString(), sqlList.toArray(), new RowMapper<Long>(){

			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				Long i = Long.valueOf(rs.getString(1));
				return i;
			}
			
		});
		
		resultMap.put(beanPager, countUnsolvedNum.get(0));
		
		return resultMap;
	}
}
