package com.taiji.pubsec.ajgl.dbtx.task.util.arrest.pojo;

import com.taiji.pubsec.ajgl.dbtx.task.util.BusinessData;

public class ArrestData extends BusinessData {

	/**
	 * 填发时间时间
	 */
	private Long sendTime;

	/**
	 * 嫌疑人姓名
	 */
	private String personName;
	
	private String personId;	//嫌疑人id
	
	/**
	 * 案件编号
	 */
	private String caseCode;
	
	/**
	 * 案件名称
	 */
	private String caseName;
	
	/**
	 * 预警时间
	 */
	private String alertTimeAt;
	/**
	 * 弹窗时间（秒）
	 */
	private Integer showTime;
	/**
	 * 逮捕证Id
	 */
	private String businessId;
	/**
	 * 逮捕证包类名
	 */
	private String businessType;

	/**
	 * 预警方式
	 */
	private String way;

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
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

	public Integer getShowTime() {
		return showTime;
	}

	public void setShowTime(Integer showTime) {
		this.showTime = showTime;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getWay() {
		return way;
	}

	public void setWay(String way) {
		this.way = way;
	}

	public Long getSendTime() {
		return sendTime;
	}

	public void setSendTime(Long sendTime) {
		this.sendTime = sendTime;
	}

	public String getAlertTimeAt() {
		return alertTimeAt;
	}

	public void setAlertTimeAt(String alertTimeAt) {
		this.alertTimeAt = alertTimeAt;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

}
