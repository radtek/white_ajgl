package com.taiji.pubsec.ajqlc.sla.bean;

import java.util.Calendar;

public class CaseServiceBean {

	private String caseCode;	//案件编号
	
	private String caseName;	//案件名称
	
	private String handlingPolice;		//办案民警
	
	private Long filingTime;	//立案时间
	
	private String doPeople;	//包办人
	
	private Long oneRefundInvestigationTime;	//一次退侦开始时间
	private Long oneRefundInvestigationTimeForShow;	//一次退侦结束时间
	
	private Long twoRefundInvestigationTime;	//二次退侦开始时间
	private Long twoRefundInvestigationTimeForShow;	//二次退侦结束时间
	
	private String ifSolved;	//是否破案
	
	private String ifArchive;	//是否归档
	
	private String handlingUnit;	//当前办理单位
	
	private String ifCloseCase; //是否结案
	
	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getHandlingPolice() {
		return handlingPolice;
	}

	public void setHandlingPolice(String handlingPolice) {
		this.handlingPolice = handlingPolice;
	}

	public String getDoPeople() {
		return doPeople;
	}

	public void setDoPeople(String doPeople) {
		this.doPeople = doPeople;
	}

	public Long getOneRefundInvestigationTime() {
		return oneRefundInvestigationTime;
	}

	public void setOneRefundInvestigationTime(Long oneRefundInvestigationTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(oneRefundInvestigationTime);
		cal.add(Calendar.MONTH, 1);
		this.oneRefundInvestigationTime = oneRefundInvestigationTime;
		this.oneRefundInvestigationTimeForShow = cal.getTimeInMillis();
	}

	public Long getTwoRefundInvestigationTime() {
		return twoRefundInvestigationTime;
	}

	public void setTwoRefundInvestigationTime(Long twoRefundInvestigationTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(twoRefundInvestigationTime);
		cal.add(Calendar.MONTH, 1);
		this.twoRefundInvestigationTime = twoRefundInvestigationTime;
		this.twoRefundInvestigationTimeForShow = cal.getTimeInMillis();
	}

	public String getIfArchive() {
		return ifArchive;
	}

	public void setIfArchive(String ifArchive) {
		this.ifArchive = ifArchive;
	}

	public Long getFilingTime() {
		return filingTime;
	}

	public void setFilingTime(Long filingTime) {
		this.filingTime = filingTime;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getIfSolved() {
		return ifSolved;
	}

	public void setIfSolved(String ifSolved) {
		this.ifSolved = ifSolved;
	}

	public Long getOneRefundInvestigationTimeForShow() {
		return oneRefundInvestigationTimeForShow;
	}

	public Long getTwoRefundInvestigationTimeForShow() {
		return twoRefundInvestigationTimeForShow;
	}

	public String getHandlingUnit() {
		return handlingUnit;
	}

	public void setHandlingUnit(String handlingUnit) {
		this.handlingUnit = handlingUnit;
	}

	public String getIfCloseCase() {
		return ifCloseCase;
	}

	public void setIfCloseCase(String ifCloseCase) {
		this.ifCloseCase = ifCloseCase;
	}

}
