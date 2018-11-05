package com.taiji.pubsec.ajgl.dbtx.task.util.susobj.pojo;

import com.taiji.pubsec.common.tool.mission.task.custom.pojo.JSONPojo;

public class SusObjRule implements JSONPojo{

	/**
	 * 执行时间
	 */
	private Long executingTime;
	
	private Integer aheadTime;
	
	private String caseCode;	//案件编号
	
	private String suspectId;	//嫌疑人id
	
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

	public Integer getAheadTime() {
		return aheadTime;
	}

	public void setAheadTime(Integer aheadTime) {
		this.aheadTime = aheadTime;
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
	
}

