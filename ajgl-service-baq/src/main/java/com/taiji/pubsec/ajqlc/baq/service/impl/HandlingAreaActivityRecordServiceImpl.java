package com.taiji.pubsec.ajqlc.baq.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taiji.persistence.dao.Dao;
import com.taiji.pubsec.ajqlc.baq.acl.handler.HandlingAreaActivityRecordSaveHandler;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaActivityRecord;
import com.taiji.pubsec.ajqlc.baq.service.IHandlingAreaActivityRecordService;
import com.taiji.pubsec.springsecurity.annotation.SecureBusData;

/**
 * @author chengkai
 */
@Service("handlingAreaActivityRecordService")
public class HandlingAreaActivityRecordServiceImpl implements IHandlingAreaActivityRecordService {
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;

	@SecureBusData(mark=HandlingAreaActivityRecordSaveHandler.MARK, busData="new Object[]{#arg0}")
	@SuppressWarnings("unchecked")
	@Override
	public void save(HandlingAreaActivityRecord handlingAreaActivityRecord) {
		this.dao.save(handlingAreaActivityRecord);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(HandlingAreaActivityRecord handlingAreaActivityRecord) {
		this.dao.update(handlingAreaActivityRecord);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void delete(String handlingAreaActivityRecordId) {
		this.dao.delete(this.findById(handlingAreaActivityRecordId));
	}

	@SuppressWarnings("unchecked")
	@Override
	public HandlingAreaActivityRecord findById(String handlingAreaActivityRecordId) {
		return (HandlingAreaActivityRecord) this.dao.findById(HandlingAreaActivityRecord.class, handlingAreaActivityRecordId);
	}
	
}
