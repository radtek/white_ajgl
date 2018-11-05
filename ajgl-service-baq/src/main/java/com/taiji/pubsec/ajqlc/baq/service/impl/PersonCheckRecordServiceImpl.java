package com.taiji.pubsec.ajqlc.baq.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.persistence.dao.Dao;
import com.taiji.pubsec.ajqlc.baq.model.PersonCheckRecord;
import com.taiji.pubsec.ajqlc.baq.service.IPersonCheckRecordService;
@Service("personCheckRecordService")
@Transactional(rollbackFor = Exception.class)
public class PersonCheckRecordServiceImpl implements IPersonCheckRecordService {
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;

	@SuppressWarnings("unchecked")
	@Override
	public String savePersonCheckRecord(PersonCheckRecord personCheckRecord) {
		this.dao.save(personCheckRecord);
		return personCheckRecord.getHandlingAreaReceipt().getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updatePersonCheckRecord(PersonCheckRecord personCheckRecord) {
		this.dao.update(personCheckRecord);
	}

}
