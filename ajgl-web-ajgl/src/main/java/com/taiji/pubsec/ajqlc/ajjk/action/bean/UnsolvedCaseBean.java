package com.taiji.pubsec.ajqlc.ajjk.action.bean;

public class UnsolvedCaseBean {
	/**
	 * 序号（页面查询不使用，导出excel使用）
	 */
	private String num;
	/**
	 * 案件编码
	 */
	private String caseCode;
	/**
	 * 案件名称
	 */
	private String CaseName;
	/**
	 * 案件类别
	 */
	private String caseSort;
	/**
	 * 办案单位
	 */
	private String sponsor;
	/**
	 * 主办侦查员
	 */
	private String investigator;
	/**
	 * 案件状态
	 */
	private String state;
	/**
	 * 发案时间
	 */
	private String happenedTime;
	/**
	 * 发案地点
	 */
	private String happenedAddress;
	/**
	 * 简要案情
	 */
	private String detail;
	/**
	 * 是否破案
	 */
	private String ifSolved;
	
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getCaseCode() {
		return caseCode;
	}
	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}
	public String getCaseName() {
		return CaseName;
	}
	public void setCaseName(String caseName) {
		CaseName = caseName;
	}
	public String getCaseSort() {
		return caseSort;
	}
	public void setCaseSort(String caseSort) {
		this.caseSort = caseSort;
	}
	public String getSponsor() {
		return sponsor;
	}
	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}
	public String getInvestigator() {
		return investigator;
	}
	public void setInvestigator(String investigator) {
		this.investigator = investigator;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getHappenedTime() {
		return happenedTime;
	}
	public void setHappenedTime(String happenedTime) {
		this.happenedTime = happenedTime;
	}
	public String getHappenedAddress() {
		return happenedAddress;
	}
	public void setHappenedAddress(String happenedAddress) {
		this.happenedAddress = happenedAddress;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getIfSolved() {
		return ifSolved;
	}
	public void setIfSolved(String ifSolved) {
		this.ifSolved = ifSolved;
	}
	
}
