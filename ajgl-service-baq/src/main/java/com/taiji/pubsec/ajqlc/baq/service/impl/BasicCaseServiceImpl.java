package com.taiji.pubsec.ajqlc.baq.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.persistence.dao.Dao;
import com.taiji.pubsec.ajqlc.baq.model.BasicCase;
import com.taiji.pubsec.ajqlc.baq.service.IBasicCaseService;
import com.taiji.pubsec.ajqlc.baq.service.IHandlingAreaReceiptService;
@Service("basicCaseService")
@Transactional(rollbackFor = Exception.class)
public class BasicCaseServiceImpl implements IBasicCaseService {
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	@Resource
	private IHandlingAreaReceiptService handlingAreaReceiptService;
	
	

	@SuppressWarnings("unchecked")
	@Override
	public String createBasicCase(BasicCase basicCase) {
		this.dao.save(basicCase);
		return basicCase.getHandlingAreaReceipt().getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateBasicCase(BasicCase basicCase) {
		this.dao.update(basicCase);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BasicCase findById(String basicCaseId) {
		return (BasicCase)this.dao.findById(BasicCase.class, basicCaseId);
	}

	

}
