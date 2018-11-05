package com.taiji.pubsec.ajqlc.util;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.pubsec.ajgl.util.ReturnMessageAction;
import com.taiji.pubsec.ajqlc.login.UrlAfterLoginService;
import com.taiji.pubsec.businesscomponent.organization.model.Person;
import com.taiji.pubsec.springsecurity.resource.util.MySecureUser;
import com.taiji.pubsec.springsecurity.resource.util.SessionUserDetailsUtil;


/**
 * 登录页面获取
 * 
 * @author 
 *
 */
@Controller
@Scope("prototype")
public class UrlAfterLoginAction  extends ReturnMessageAction{
	
	@Resource
	private UrlAfterLoginService urlAfterLoginService;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String url;
	
	public String findUrlByRole() {
		Person p = this.findCurrentPerson();
		MySecureUser user = SessionUserDetailsUtil.getMySecureUser() ;
		Map<String, Object> userMap = user.getUserMap() ;
		@SuppressWarnings("unchecked")
		List<Map<String, String>> roles = (List<Map<String, String>>) userMap.get("roles");
		List<String> roleLst = new ArrayList<String>();
		for (Map<String, String> role : roles) {
			roleLst.add(role.get("code"));
		}
		setUrl(urlAfterLoginService.getJumpUrl(p.getId(), roleLst));
		return SUCCESS;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
