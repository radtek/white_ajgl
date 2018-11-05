package com.taiji.pubsec.ajgl.dbtx.task.util.replenishDetectBuilder.pojo;

import com.taiji.pubsec.common.tool.mission.task.custom.pojo.JSONPojo;

public class ReplenishDetectBuilderRule implements JSONPojo {

	/**
	 * 执行时间
	 */
	private Long executingTime;
	
	/**
	 * 案件名称
	 */
	private String caseName;
	
	private String context;
	
	private String caseCode;
	
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

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	
}
