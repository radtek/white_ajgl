package com.taiji.pubsec.ajqlc.baq.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.persistence.dao.Dao;
import com.taiji.pubsec.ajqlc.baq.model.OperateRecord;
import com.taiji.pubsec.ajqlc.baq.service.IOperateRecordService;

/**
 * @author chengkai
 */
@Service("operateRecordService")
@Transactional(rollbackFor = Exception.class)
public class OperateRecordServiceImpl implements IOperateRecordService {
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@SuppressWarnings("unchecked")
	@Override
	public void saveOperateRecord(OperateRecord operateRecord) {
		this.dao.save(operateRecord);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OperateRecord> findOperateRecordByModelObjectId(String modelObjectId, String modelObject) {
		String xql = "select a from OperateRecord as a where a.modelObjectId = ? and a.modelObject = ? order by a.operateTime desc";
		return this.dao.findAllByParams(OperateRecord.class, xql, new Object[]{modelObjectId,modelObject});
	}

}
