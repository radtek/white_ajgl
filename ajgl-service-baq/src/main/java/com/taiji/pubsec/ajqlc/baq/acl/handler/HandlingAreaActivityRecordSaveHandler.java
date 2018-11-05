package com.taiji.pubsec.ajqlc.baq.acl.handler;

import javax.annotation.Resource;

import org.springframework.security.acls.domain.BasePermission;
import org.springframework.stereotype.Service;

import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaActivityRecord;
import com.taiji.pubsec.springsecurity.TjAclService;
import com.taiji.pubsec.springsecurity.annotation.handler.SecureBusDataHandler;
import com.taiji.pubsec.springsecurity.resource.util.MySecureUser;
import com.taiji.pubsec.springsecurity.resource.util.SessionUserDetailsUtil;

@Service
public class HandlingAreaActivityRecordSaveHandler implements SecureBusDataHandler{
	
	public static final String MARK = "SecureBusData_HandlingAreaActivityRecord_Save_Handler" ; 
	
	@Resource
	private TjAclService tjAclService;
	
	@Override
	public void handle(Object busData) {
		
		Object[] obj = (Object[])busData ;
		
		HandlingAreaActivityRecord entity = (HandlingAreaActivityRecord)obj[0] ;
		
		MySecureUser user = SessionUserDetailsUtil.getMySecureUser() ;
				
		//给将finallyLeaveTime设置值的人授予写的权限，并且除了他和超级管理员其他人不能修改
		tjAclService.grantPermissions(entity, user.getUsername(), true, BasePermission.WRITE, true);
		tjAclService.grantPermissions(entity, user.getUsername(), true, BasePermission.DELETE, true);
	}

	@Override
	public boolean support(String mark) {
		if(MARK.equals(mark)){
			return true;
		}
		return false;
	}
}
