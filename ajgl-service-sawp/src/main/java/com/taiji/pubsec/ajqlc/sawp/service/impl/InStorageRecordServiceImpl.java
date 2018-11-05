package com.taiji.pubsec.ajqlc.sawp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taiji.persistence.dao.Dao;
import com.taiji.persistence.service.AbstractBaseService;
import com.taiji.pubsec.ajqlc.sawp.model.InStorageRecord;
import com.taiji.pubsec.ajqlc.sawp.service.IInStorageRecordService;

@Service("inStorageRecordService")
public class InStorageRecordServiceImpl extends AbstractBaseService<InStorageRecord, String> implements IInStorageRecordService {

	@Autowired
	public InStorageRecordServiceImpl(Dao<InStorageRecord, String> dao) {
		setDao(dao);
	}
	
	@Override
	public InStorageRecord findArticleOperationRecordByInStorageFormItemAndLocker(String inStorageFormItemId,
			String lockerCode) {
		String xql = "select i from InStorageRecord as i where i.formItem.id = :inStorageFormItemId and i.locker.code = :lockerCode";
		Map<String, Object> xqlMap  = new HashMap<>();
		xqlMap.put("inStorageFormItemId", inStorageFormItemId);
		xqlMap.put("lockerCode", lockerCode);
		List<InStorageRecord> list = this.findAllByParams(xql, xqlMap);
		return list.isEmpty() ? null : list.get(0);
	}

}
