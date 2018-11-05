package com.taiji.pubsec.ajqlc.baq.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.persistence.dao.Dao;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceiptToPoliceInfo;
import com.taiji.pubsec.ajqlc.baq.service.IHandlingAreaReceiptToPoliceInfoService;

/**
 * @author huangda
 */
@Service("handlingAreaReceiptToPoliceInfoService")
@Transactional(rollbackFor = Exception.class)
public class HandlingAreaReceiptToPoliceInfoServiceImpl implements IHandlingAreaReceiptToPoliceInfoService {
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@Resource(name="jpaSqlDao")
	private com.taiji.persistence.dao.SqlDao jpaSqlDao ;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(HandlingAreaReceiptToPoliceInfoServiceImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public HandlingAreaReceiptToPoliceInfo findByHarIdAndPoliceInfoId(String harId, String policeInfoId) {
		return (HandlingAreaReceiptToPoliceInfo)dao.findByParams(HandlingAreaReceiptToPoliceInfo.class, "from HandlingAreaReceiptToPoliceInfo as htp where htp.handlingAreaReceipt.id = ? and htp.policeInfo.id = ?", new Object[]{harId, policeInfoId});
	}

	@SuppressWarnings("unchecked")
	@Override
	public String save(HandlingAreaReceiptToPoliceInfo entity) {
		this.dao.save(entity);
        return entity.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void delete(String id) {
		this.dao.delete(HandlingAreaReceiptToPoliceInfo.class, id);
	}
}
