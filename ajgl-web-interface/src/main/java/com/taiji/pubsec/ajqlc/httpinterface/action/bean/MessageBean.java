package com.taiji.pubsec.ajqlc.httpinterface.action.bean;

import java.util.Date;

/**
 * 预警信息Bean
 */
public class MessageBean {

	private String id;	// id
	
	private String content;	// 消息内容
	
	private Date createdTime;	//创建时间
	
	private String title;	//消息标题
	
	private String source;	//信息来源
	
	private String type;	//消息类型
	
	private String suspectName;	//嫌疑人名称
	
	private String caseCode;	//案件编号
	
	private String caseName;	//案件名称
	
	private String details;	//简要案情
	
	private String isSigned;	//是否签收，未签收：状态为“0”；已签收状态为“1”

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSuspectName() {
		return suspectName;
	}

	public void setSuspectName(String suspectName) {
		this.suspectName = suspectName;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getIsSigned() {
		return isSigned;
	}

	public void setIsSigned(String isSigned) {
		this.isSigned = isSigned;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
