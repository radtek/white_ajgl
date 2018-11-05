package com.taiji.pubsec.ajgl.dbtx.task.util.subpoena.pojo;

import com.taiji.pubsec.ajgl.dbtx.task.util.BusinessData;

public class SubpoenaData extends BusinessData {

	/**
	 * 填发时间时间
	 */
	private Long sendTime;

	/**
	 * 嫌疑人姓名
	 */
	private String personName;
	
	private String suspectId;	//嫌疑人id
	
	/**
	 * 案件编号
	 */
	private String caseCode;
	
	/**
	 * 案件名称
	 */
	private String caseName;
	
	/**
	 * 提前预警时间（分钟）
	 */
	private Integer aheadTime;
	/**
	 * 弹窗时间（秒）
	 */
	private Integer showTime;
	/**
	 * 传唤证Id
	 */
	private String businessId;
	/**
	 * 传唤证包类名
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


	public Integer getAheadTime() {
		return aheadTime;
	}

	public void setAheadTime(Integer aheadTime) {
		this.aheadTime = aheadTime;
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

	public String getSuspectId() {
		return suspectId;
	}

	public void setSuspectId(String suspectId) {
		this.suspectId = suspectId;
	}

}
