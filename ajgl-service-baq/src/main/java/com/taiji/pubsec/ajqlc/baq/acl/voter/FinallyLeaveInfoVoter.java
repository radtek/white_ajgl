package com.taiji.pubsec.ajqlc.baq.acl.voter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.taiji.pubsec.ajqlc.baq.acl.Constant;
import com.taiji.pubsec.springsecurity.voter.CustomizedAclEntryVoter;

public class FinallyLeaveInfoVoter extends CustomizedAclEntryVoter{

	public FinallyLeaveInfoVoter(AclService aclService,
			String processConfigAttribute, Permission[] requirePermission,
			boolean isDefaultAdmin) {
		super(aclService, processConfigAttribute, requirePermission, isDefaultAdmin);
	}

	public int vote(Authentication authentication, MethodInvocation object, Collection<ConfigAttribute> attributes) {
		
		for(ConfigAttribute attr : attributes) {
            if (!this.supports(attr)) {
                continue;
            }
    		
    		Iterator<GrantedAuthority> it = (Iterator<GrantedAuthority>) authentication.getAuthorities().iterator();
    		
    		while(it.hasNext()){
    			String auth = it.next().getAuthority() ;
    			for(String uastr:Constant.LEAVE_INFO_ADMIN_AUTH){
    				if(auth.equals(uastr)){
    					return ACCESS_GRANTED;
    				}
    			}
    		}
		}
		
		return super.vote(authentication, object, attributes);
	}
	
	public static void main(String[] args) {
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

		
	}
}
