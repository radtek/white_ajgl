package com.taiji.pubsec.ajqlc.xtgl.log;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.taiji.pubsec.ajqlc.xtgl.resource.ResourceBean;


/**
 * 操作日志Bean
 * 
 * @author 
 *
 */
public class OperationLogBean {
	
	private String id;
	
	private String operatingTime; // 操作时间
	
	private String userName;	//用户名
	
	private String functionMenuName;	//功能菜单名称
	
	private String operateContent;	//操作内容
	
	private String personName;	//人员姓名
	
	private String clientIp;	//客户端IP

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOperatingTime() {
		return operatingTime;
	}

	public void setOperatingTime(String operatingTime) {
		this.operatingTime = operatingTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFunctionMenuName() {
		return functionMenuName;
	}

	public void setFunctionMenuName(String functionMenuName) {
		this.functionMenuName = functionMenuName;
	}

	public String getOperateContent() {
		return operateContent;
	}

	public void setOperateContent(String operateContent) {
		this.operateContent = operateContent;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
}
