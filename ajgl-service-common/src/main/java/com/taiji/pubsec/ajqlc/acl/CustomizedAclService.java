package com.taiji.pubsec.ajqlc.acl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.security.acls.model.Permission;
import org.springframework.stereotype.Service;

import com.taiji.pubsec.springsecurity.TjAclService;
import com.taiji.pubsec.springsecurity.resource.util.MySecureUser;
import com.taiji.pubsec.springsecurity.resource.util.SessionUserDetailsUtil;

@Service
public class CustomizedAclService {
	@Resource
	private TjAclService tjAclService;
	
	/**
	 * 查询指定的subjectId对于领域对象实例的权限
	 * @param object 领域对象
	 * @param subjectId 用户的username(isPrincipal=true时)或者是authority的id(isPrincipal=false时)
	 * @param isPrincipal
	 * @return
	 */
	public List<String> findBasePermissionNames(Object object, String subjectId, boolean isPrincipal){
		
		List<String> list = new ArrayList<String>() ;
		
		List<Permission> ps = this.tjAclService.findPermissions(object, subjectId, isPrincipal) ;
		
		for(Permission p:ps){
			list.add(this.tjAclService.findBasePermissionName(p));
		}
		
		return list;
	}
	
	/**
	 * 查询当前用户对于领域对象实例的权限
	 * @param object 领域对象
	 * @return
	 */
	public List<String> findBasePermissionNamesByCurrentUserAndAllPrincipal(Object object){
		MySecureUser user = SessionUserDetailsUtil.getMySecureUser() ;
		Map<String, Object> userMap = user.getUserMap() ;
		
		List<String> auths = (List<String>)userMap.get("authorityCodes");
		
		List<String> list = this.findBasePermissionNames(object, user.getUsername(), true);
		
		for(String a:auths){
			List<String> alist = this.findBasePermissionNames(object, a, false);
			for(String alstr:alist){
				if(!list.contains(alstr)){
					list.add(alstr);
				}
			}
		}
		
		return list ;
	}
}
