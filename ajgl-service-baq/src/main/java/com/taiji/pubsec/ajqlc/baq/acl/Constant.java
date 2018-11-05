package com.taiji.pubsec.ajqlc.baq.acl;

import com.taiji.pubsec.springsecurity.resource.service.AuthorityHolderService;

public class Constant implements AuthorityHolderService{
	
	private Constant(){
		
	}

	public static String[] LEAVE_INFO_ADMIN_AUTH = new String[]{"auth_baqgjgl"} ;
	
	public static String[] HANDLING_AREA_ACTIVITY_RECORD_ADMIN_AUTH = new String[]{"auth_baqgjgl"} ;

	@Override
	public void reloadAuthorities() {
		// TODO Auto-generated method stub
		
	}
}
