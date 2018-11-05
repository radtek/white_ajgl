package com.taiji.pubsec.ajgl.dbtx.task.util.replenishDetectBuilder.pojo;

import com.taiji.pubsec.ajgl.dbtx.task.util.BusinessData;

public class ReplenishDetectBuilderData extends BusinessData {

	/**
	 * 起算日期时间
	 */
	private Long sendDate;

	/**
	 * 案件编号
	 */
	private String caseCode;
	
	/**
	 * 案件名称
	 */
	private String caseName;
	
	/**
	 * 预警要求时间
	 */
	private String alertTimeAt;
	
	/**
	 * 弹窗时间（秒）
	 */
	private Integer showTime;
	/**
	 * 案件编号
	 */
	private String businessId;
	/**
	 * 案件附加信息包类名
	 */
	private String businessType;

	/**
	 * 预警方式
	 */
	private String way;

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

	public String getAlertTimeAt() {
		return alertTimeAt;
	}

	public void setAlertTimeAt(String alertTimeAt) {
		this.alertTimeAt = alertTimeAt;
	}

	public Long getSendDate() {
		return sendDate;
	}

	public void setSendDate(Long sendDate) {
		this.sendDate = sendDate;
	}
	
}
