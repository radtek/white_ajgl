package com.taiji.pubsec.ajgl.dbtx.task.util.detained.pojo;

import com.taiji.pubsec.common.tool.mission.task.custom.pojo.JSONPojo;

public class DetainedRule implements JSONPojo {
	
	/**
	 * 执行时间
	 */
	private Long executingTime;
	
	private String context;
	
	private String personId;
	
	private String caseCode;
	
	private String suspectName;	//嫌疑人姓名
	
	private boolean alertForSys = false;
	
	private boolean alertForPDA = false;

	private boolean alertForText = false;

	public Long getExecutingTime() {
		return executingTime;
	}

	public void setExecutingTime(Long executingTime) {
		this.executingTime = executingTime;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
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

	public String getSuspectName() {
		return suspectName;
	}

	public void setSuspectName(String suspectName) {
		this.suspectName = suspectName;
	}
	
	
	
}
