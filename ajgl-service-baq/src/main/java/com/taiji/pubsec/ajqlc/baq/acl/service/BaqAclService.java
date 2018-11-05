package com.taiji.pubsec.ajqlc.baq.acl.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taiji.pubsec.ajqlc.acl.CustomizedAclService;
import com.taiji.pubsec.springsecurity.TjAclService;
import com.taiji.pubsec.springsecurity.util.Constant;

@Service
public class BaqAclService {
	
	@Resource
	private CustomizedAclService customizedAclService ;
	
	@Resource
	private TjAclService tjAclService ;

	public List<String> findFinallyLeaveInfoBasePermissionNamesByCurrentUserAndAllPrincipal(Object object){
		
		List<String> list = new ArrayList<String>();
		
		if(!tjAclService.isAcled(object)){
			list.add(Constant.BasePermissionNames.ADMINISTRATION.name());
			return list;
		}
		
		return this.customizedAclService.findBasePermissionNamesByCurrentUserAndAllPrincipal(object);
	}
}
