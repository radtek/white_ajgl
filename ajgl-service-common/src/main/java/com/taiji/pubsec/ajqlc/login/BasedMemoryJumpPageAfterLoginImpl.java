/**
 * Copyright 2017 Taiji
 * All right reserved.
 * Created on 2017年3月13日 下午5:25:40
 */
package com.taiji.pubsec.ajqlc.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yucy
 *
 */
public class BasedMemoryJumpPageAfterLoginImpl implements UrlAfterLoginService {
	private static final Logger logger = LoggerFactory.getLogger(BasedMemoryJumpPageAfterLoginImpl.class);

	List<String> roleConfigs = new ArrayList<>();
	List<String> userConfigs = new ArrayList<>();
	
	Map<String, String> userJumpUrls = new HashMap<String, String>();
	
	Map<String, String> roleJumpUrls = new HashMap<String, String>();
	
	Map<String, Integer> rolePrioritys = new HashMap<String, Integer>();
	
	public void init(){
		for(String rc : roleConfigs){
			String[] cfg = rc.split(",");
			String role = cfg[0].trim();
			String url = cfg[1].trim();
			Integer priority = Integer.valueOf(cfg[2].trim());
			roleJumpUrls.put(role, url);
			rolePrioritys.put(role, priority);
		}
		for(String uc : userConfigs){
			String[] cfg = uc.split(",");
			String userid = cfg[0].trim();
			String url = cfg[1].trim();
			userJumpUrls.put(userid, url);
		}
		logger.info("角色登录跳转页面配置 : {}", roleJumpUrls);
		logger.info("角色优先级配置 : {}" + rolePrioritys);
		logger.info("用户登录跳转页面配置 : {}" + userJumpUrls);
	}

	@Override
	public String getJumpUrl(String userid, List<String> roles){
		String url = userJumpUrls.get(userid);
		if( null != url ){
			return url;
		}
		
		String role = getRoleByPriority(roles);
		return roleJumpUrls.get(role);
	}
	
	private String getRoleByPriority(List<String> roles){
		String role = null;
		Integer priority = -1;
		for(String r : roles){
			Integer p = rolePrioritys.get(r);
			if(p > priority){
				role = r;
				priority = p;
			}
		}
		return role;
	}

	public void setRoleConfigs(List<String> roleConfigs) {
		this.roleConfigs = roleConfigs;
	}

	public void setUserConfigs(List<String> userConfigs) {
		this.userConfigs = userConfigs;
	}

	public List<String> getRoleConfigs() {
		return roleConfigs;
	}

	public List<String> getUserConfigs() {
		return userConfigs;
	}

}
