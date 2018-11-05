package com.taiji.pubsec.ajqlc.operationrecord.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taiji.persistence.dao.Dao;
import com.taiji.pubsec.ajqlc.operationrecord.model.OperationRecord;
import com.taiji.pubsec.ajqlc.operationrecord.service.IOperationRecordService;

@Service("operationRecordService")
public class OperationRecordServiceImpl implements IOperationRecordService {
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;

	@SuppressWarnings("unchecked")
	@Override
	public String saveOperationRecord(OperationRecord operationRecord) {
		dao.save(operationRecord);
		return operationRecord.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OperationRecord> findOperationRecordByarticleCode(String articleCode) {
		StringBuilder xql = new StringBuilder("select o from OperationRecord as o where o.articleCode = ? order by o.operationTime desc");
		return dao.findAllByParams(OperationRecord.class, xql.toString(), new Object[]{articleCode});
	}

}
