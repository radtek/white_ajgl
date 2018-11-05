package com.taiji.pubsec.ajqlc.baq.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taiji.persistence.dao.Dao;
import com.taiji.pubsec.ajqlc.baq.acl.handler.TmpLeaveInfoSaveHandler;
import com.taiji.pubsec.ajqlc.baq.model.TmpLeaveInfo;
import com.taiji.pubsec.ajqlc.baq.service.ITmpLeaveInfoService;
import com.taiji.pubsec.springsecurity.annotation.SecureBusData;

/**
 * @author chengkai
 */
@Service("tmpLeaveInfoService")
public class TmpLeaveInfoServiceImpl implements ITmpLeaveInfoService {

	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;

	@SecureBusData(mark=TmpLeaveInfoSaveHandler.MARK, busData="new Object[]{#arg0}")
	@SuppressWarnings("unchecked")
	@Override
	public void save(TmpLeaveInfo tmpLeaveInfo) {
		this.dao.save(tmpLeaveInfo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(TmpLeaveInfo tmpLeaveInfo) {
		this.dao.update(tmpLeaveInfo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void delete(TmpLeaveInfo entity) {
		if(entity!=null){}{
			this.dao.delete(entity);
		}	
	}

	@SuppressWarnings("unchecked")
	@Override
	public TmpLeaveInfo findById(String tmpLeaveInfoId) {
		return (TmpLeaveInfo) this.dao.findById(TmpLeaveInfo.class, tmpLeaveInfoId);
	}
	
}
