package com.taiji.pubsec.ajqlc.operationLog.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taiji.persistence.dao.Dao;
import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.operationLog.model.OperationLog;
import com.taiji.pubsec.ajqlc.util.ParamMapUtil;
import com.taiji.pubsec.common.tool.sql.SQLTool;

@Service("operationLogService")
public class OperationLogServiceImpl implements IOperationLogService {
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@SuppressWarnings("unchecked")
	@Override
	public void save(OperationLog operationLog) {
		dao.save(operationLog);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pager<OperationLog> findOperationLogByConditions(Map<String, Object> xqlMap, int pageNo, int pageSize) {
		StringBuilder xql = new StringBuilder("select o from OperationLog as o where 1 = 1 ");
		Map<String, Object> xqlMapCondition = new HashMap<String, Object>();
		
		if(ParamMapUtil.isNotBlank(xqlMap.get("queryTimeStart"))){
			xql.append(" and o.operatingTime >= :queryTimeStart ");
			xqlMapCondition.put("queryTimeStart", xqlMap.get("queryTimeStart"));
		}
		if(ParamMapUtil.isNotBlank(xqlMap.get("queryTimeEnd"))){
			xql.append(" and o.operatingTime < :queryTimeEnd ");
			xqlMapCondition.put("queryTimeEnd", xqlMap.get("queryTimeEnd"));
		}
		if(ParamMapUtil.isNotBlank(xqlMap.get("userName"))){
			xql.append(" and o.userName like :userName ");
			SQLTool.SQLAddEscape(xql);
			xqlMapCondition.put("userName", "%" + SQLTool.SQLSpecialChTranfer((String) xqlMap.get("userName")) + "%");
		}
		if(ParamMapUtil.isNotBlank(xqlMap.get("functionMenuName"))){
			xql.append(" and o.functionMenuName like :functionMenuName ");
			SQLTool.SQLAddEscape(xql);
			xqlMapCondition.put("functionMenuName", "%" + SQLTool.SQLSpecialChTranfer((String) xqlMap.get("functionMenuName")) + "%");
		}
		if(ParamMapUtil.isNotBlank(xqlMap.get("clientIp"))){
			xql.append(" and o.clientIp like :clientIp ");
			SQLTool.SQLAddEscape(xql);
			xqlMapCondition.put("clientIp", "%" + SQLTool.SQLSpecialChTranfer((String) xqlMap.get("clientIp")) + "%");
		}
		xql.append(" order by o.operatingTime desc ");
		
		return dao.findByPage(OperationLog.class, xql.toString(), xqlMapCondition, pageNo, pageSize);
	}

}
