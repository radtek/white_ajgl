package com.taiji.pubsec.ajgl.dbtx.task.util.entercasearea.pojo;

import java.util.List;

import com.taiji.pubsec.ajgl.dbtx.task.util.BusinessData;

public class EnterCaseAreaData extends BusinessData {

	/**
	 * 进入办公区时间
	 */
	private Long enterIntoTime;

	/**
	 * 接收对象
	 */
	private String receiveId;
	
	/**
	 * 距进入办案区时间（h）
	 */
	private Double fromEnterIntoTime;
	
	/**
	 * 接收对象list
	 */
	private List<String> receiveIdLst;
	
	/**
	 * 接收对象
	 */
	private String receiveType;
	/**
	 * 提前预警时间（分钟）
	 */
	private Integer aheadTime;
	/**
	 * 弹窗时间（秒）
	 */
	private Integer showTime;
	/**
	 * 使用单Id
	 */
	private String businessId;
	/**
	 * 包类名
	 */
	private String businessType;
	
	private String personName;
	
	private String harCode;
	/**
	 * 预警方式
	 */
	private String way;
	
	private String caseCode;	//案件编号
	
	private String suspectId;	//嫌疑人id

	public Long getEnterIntoTime() {
		return enterIntoTime;
	}

	public void setEnterIntoTime(Long enterIntoTime) {
		this.enterIntoTime = enterIntoTime;
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

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getHarCode() {
		return harCode;
	}

	public void setHarCode(String harCode) {
		this.harCode = harCode;
	}

	public String getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(String receiveId) {
		this.receiveId = receiveId;
	}

	public String getReceiveType() {
		return receiveType;
	}

	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getSuspectId() {
		return suspectId;
	}

	public void setSuspectId(String suspectId) {
		this.suspectId = suspectId;
	}

	public List<String> getReceiveIdLst() {
		return receiveIdLst;
	}

	public void setReceiveIdLst(List<String> receiveIdLst) {
		this.receiveIdLst = receiveIdLst;
	}

	public Double getFromEnterIntoTime() {
		return fromEnterIntoTime;
	}

	public void setFromEnterIntoTime(Double fromEnterIntoTime) {
		this.fromEnterIntoTime = fromEnterIntoTime;
	}

}
