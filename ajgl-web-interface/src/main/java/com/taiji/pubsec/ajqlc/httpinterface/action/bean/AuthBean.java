package com.taiji.pubsec.ajqlc.httpinterface.action.bean;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class AuthBean {

	private String username;
	private String password;
	private String sessionId;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public JSONObject toJson() {
		JSONObject authPojo = new JSONObject();
		authPojo.put("appId", getUsername());
		authPojo.put("token", getPassword());
		authPojo.put("sessionId", getSessionId());
		return authPojo;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
