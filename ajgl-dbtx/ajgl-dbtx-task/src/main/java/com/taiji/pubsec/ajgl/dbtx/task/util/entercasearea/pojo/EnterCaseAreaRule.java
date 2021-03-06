package com.taiji.pubsec.ajgl.dbtx.task.util.entercasearea.pojo;

import com.taiji.pubsec.common.tool.mission.task.custom.pojo.JSONPojo;

public class EnterCaseAreaRule implements JSONPojo{

	/**
	 * 执行时间
	 */
	private Long executingTime;
	
	private String context;
	
	private String caseCode;	//案件编号
	
	private String suspectId;	//嫌疑人id
	
	private String suspectName;	//嫌疑人姓名
	
	private String receiptNum;	//使用单编号
	
	private boolean alertForSys = false;
	
	private boolean alertForPDA = false;

	private boolean alertForText = false;

	public Long getExecutingTime() {
		return executingTime;
	}

	public void setExecutingTime(Long executingTime) {
		this.executingTime = executingTime;
	}

	public boolean isAlertForSys() {
		return alertForSys;
	}

	public void setAlertForSys(boolean alertForSys) {
		this.alertForSys = alertForSys;
	}

	public boolean isAlertForPDA() {
		return alertForPDA;
	}

	public void setAlertForPDA(boolean alertForPDA) {
		this.alertForPDA = alertForPDA;
	}

	public boolean isAlertForText() {
		return alertForText;
	}

	public void setAlertForText(boolean alertForText) {
		this.alertForText = alertForText;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
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

	public String getSuspectName() {
		return suspectName;
	}

	public void setSuspectName(String suspectName) {
		this.suspectName = suspectName;
	}

	public String getReceiptNum() {
		return receiptNum;
	}

	public void setReceiptNum(String receiptNum) {
		this.receiptNum = receiptNum;
	}
	
}
