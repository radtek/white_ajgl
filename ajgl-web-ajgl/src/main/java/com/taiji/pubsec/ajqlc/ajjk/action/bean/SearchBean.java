package com.taiji.pubsec.ajqlc.ajjk.action.bean;

public class SearchBean {
	/**
	 * 查询时间
	 */
	private Long caseTimeStart;
	/**
	 * 查询时间
	 */
	private Long caseTimeEnd;
	/**
	 * 是否关注
	 */
	private String attention;
	/**
	 * 办案单位
	 */
	private String sponsor;
	/**
	 * 案件名称
	 */
	private String caseName;
	/**
	 * 案件编号
	 */
	private String caseCode;
	/**
	 * 备注
	 */
	private String remark;
	

    /**
	 * 办案民警
	 */
	private String disposePerson;
	
	public String getAttention() {
		return attention;
	}
	public void setAttention(String attention) {
		this.attention = attention;
	}
	public String getSponsor() {
		return sponsor;
	}
	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getCaseTimeStart() {
		return caseTimeStart;
	}
	public void setCaseTimeStart(Long caseTimeStart) {
		this.caseTimeStart = caseTimeStart;
	}
	public Long getCaseTimeEnd() {
		return caseTimeEnd;
	}
	public void setCaseTimeEnd(Long caseTimeEnd) {
		this.caseTimeEnd = caseTimeEnd;
	}
	public String getCaseCode() {
		return caseCode;
	}
	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}
	public String getDisposePerson() {
		return disposePerson;
	}
	public void setDisposePerson(String disposePerson) {
		this.disposePerson = disposePerson;
	}
}
