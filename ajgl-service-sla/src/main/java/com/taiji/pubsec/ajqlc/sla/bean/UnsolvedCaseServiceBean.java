package com.taiji.pubsec.ajqlc.sla.bean;

import java.util.Date;

public class UnsolvedCaseServiceBean {
	/**
	 * 序号（页面查询不使用，导出excel使用）
	 */
	private String num;
	/**
	 * 生成快照时间
	 */
	private Date snapshotTime;
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
	 * 案件类别code
	 */
	private String caseSortCode;
	
	/**
	 * 办案单位
	 */
	private String sponsor;
	
	/**
	 * 办案单位code
	 */
	private String dqbldwCode;
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
	 * 案件类型
	 */
	private String caseClass;
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
	public String getCaseSortCode() {
		return caseSortCode;
	}
	public void setCaseSortCode(String caseSortCode) {
		this.caseSortCode = caseSortCode;
	}
	public String getDqbldwCode() {
		return dqbldwCode;
	}
	public void setDqbldwCode(String dqbldwCode) {
		this.dqbldwCode = dqbldwCode;
	}
	public String getCaseClass() {
		return caseClass;
	}
	public void setCaseClass(String caseClass) {
		this.caseClass = caseClass;
	}
	public Date getSnapshotTime() {
		return snapshotTime;
	}
	public void setSnapshotTime(Date snapshotTime) {
		this.snapshotTime = snapshotTime;
	}
	public String getIfSolved() {
		return ifSolved;
	}
	public void setIfSolved(String ifSolved) {
		this.ifSolved = ifSolved;
	}
	
}