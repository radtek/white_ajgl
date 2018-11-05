package com.taiji.pubsec.ajqlc.ajjk.action.bean;

public class CaseDataBean {
	/**
	 * 案件编号
	 */
	private String caseCode;
	/**
	 * 包办人
	 */
	private String doPerson;
	
	/**
	 * 一次退侦时间
	 */
	private Long oneRefundInvestigationTime;
	
	/**
	 * 二次退侦时间
	 */
	private Long twoRefundInvestigationTime;

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getDoPerson() {
		return doPerson;
	}

	public void setDoPerson(String doPerson) {
		this.doPerson = doPerson;
	}

	public Long getOneRefundInvestigationTime() {
		return oneRefundInvestigationTime;
	}

	public void setOneRefundInvestigationTime(Long oneRefundInvestigationTime) {
		this.oneRefundInvestigationTime = oneRefundInvestigationTime;
	}

	public Long getTwoRefundInvestigationTime() {
		return twoRefundInvestigationTime;
	}

	public void setTwoRefundInvestigationTime(Long twoRefundInvestigationTime) {
		this.twoRefundInvestigationTime = twoRefundInvestigationTime;
	}
	
}
