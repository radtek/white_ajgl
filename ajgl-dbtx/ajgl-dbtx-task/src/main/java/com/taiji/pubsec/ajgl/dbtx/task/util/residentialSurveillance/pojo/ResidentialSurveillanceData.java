package com.taiji.pubsec.ajgl.dbtx.task.util.residentialSurveillance.pojo;

import com.taiji.pubsec.ajgl.dbtx.task.util.BusinessData;

public class ResidentialSurveillanceData extends BusinessData {
	
	/**
	 * 起算日期时间
	 */
	private Long sendDate;

	/**
	 * 嫌疑人姓名
	 */
	private String personName;
	
	/**
	 * 嫌疑人Id
	 */
	private String personId;
	
	/**
	 * 案件编号
	 */
	private String caseCode;
	
	/**
	 * 案件名称
	 */
	private String caseName;
	
	/**
	 * 案件名称
	 */
	private String alertTimeAt;
	
	/**
	 * 弹窗时间（秒）
	 */
	private Integer showTime;
	/**
	 * 监视居住文书Id
	 */
	private String businessId;
	/**
	 * 监视居住文书包类名
	 */
	private String businessType;

	/**
	 * 预警方式
	 */
	private String way;

	public Long getSendDate() {
		return sendDate;
	}

	public void setSendDate(Long sendDate) {
		this.sendDate = sendDate;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
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

	public String getAlertTimeAt() {
		return alertTimeAt;
	}

	public void setAlertTimeAt(String alertTimeAt) {
		this.alertTimeAt = alertTimeAt;
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
	
}
