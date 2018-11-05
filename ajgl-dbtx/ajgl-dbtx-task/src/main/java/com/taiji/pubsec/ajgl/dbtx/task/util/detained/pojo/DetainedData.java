package com.taiji.pubsec.ajgl.dbtx.task.util.detained.pojo;

import com.taiji.pubsec.ajgl.dbtx.task.util.BusinessData;
import com.taiji.pubsec.common.tool.mission.task.core.ReceiveSubject;

public class DetainedData extends BusinessData {
	
	/**
	 * 批准时间
	 */
	private Long approvalTime;

	/**
	 * 接收对象（填发人帐号）
	 */
	private ReceiveSubject receiveSubject;

	/**
	 * 嫌疑人姓名
	 */
	private String personName;
	
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
	 * 嫌疑人Id
	 */
	private String personId;
	/**
	 * 弹窗时间（秒）
	 */
	private Integer showTime;
	/**
	 * 拘留证Id
	 */
	private String businessId;
	/**
	 * 拘留证包类名
	 */
	private String businessType;

	/**
	 * 预警方式
	 */
	private String way;

	public Long getApprovalTime() {
		return approvalTime;
	}

	public void setApprovalTime(Long approvalTime) {
		this.approvalTime = approvalTime;
	}

	public ReceiveSubject getReceiveSubject() {
		return receiveSubject;
	}

	public void setReceiveSubject(ReceiveSubject receiveSubject) {
		this.receiveSubject = receiveSubject;
	}

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

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	
	
	
}
