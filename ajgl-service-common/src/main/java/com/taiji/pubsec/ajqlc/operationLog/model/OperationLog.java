package com.taiji.pubsec.ajqlc.operationLog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 操作日志表
 * @author chengkai
 *
 */
@Entity
@Table(name = "t_czrz")
public class OperationLog {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid",strategy = "uuid2")
	private String id;
	
	@Column(name = "czsj")
	@Temporal(TemporalType.TIMESTAMP)
	private Date operatingTime; // 操作时间
	
	@Column(name = "yhm")
	private String userName;	//用户名
	
	@Column(name = "gncdmc")
	private String functionMenuName;	//功能菜单名称
	
	@Column(name = "cznr",length=4000)
	private String operateContent;	//操作内容
	
	@Column(name = "rymc")
	private String personName;	//人员姓名
	
	@Column(name = "khdip")
	private String clientIp;	//客户端IP

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getOperatingTime() {
		return operatingTime;
	}

	public void setOperatingTime(Date operatingTime) {
		this.operatingTime = operatingTime;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
