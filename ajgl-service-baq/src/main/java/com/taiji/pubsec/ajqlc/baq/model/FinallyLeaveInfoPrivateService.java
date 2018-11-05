package com.taiji.pubsec.ajqlc.baq.model;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taiji.persistence.dao.Dao;

@Service
class FinallyLeaveInfoPrivateService {

	@Resource(name="jpaDao")
	private Dao dao ;
	
	public void update(FinallyLeaveInfo entity){
		this.dao.update(entity);
	}
}
