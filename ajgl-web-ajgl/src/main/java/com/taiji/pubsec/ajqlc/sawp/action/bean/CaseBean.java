package com.taiji.pubsec.ajqlc.sawp.action.bean;

/**
 * 案件Bean
 * 
 * @author WangLei
 *
 */
public class CaseBean {

	private String caseCode;
	
	private String caseName;
	
	private Long caseTimeStart;
	
	private Long caseTimeEnd;
	
	private String policeName;
	
	private String unitName;
	
	private String suspectName;

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

	public String getPoliceName() {
		return policeName;
	}

	public void setPoliceName(String policeName) {
		this.policeName = policeName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getSuspectName() {
		return suspectName;
	}

	public void setSuspectName(String suspectName) {
		this.suspectName = suspectName;
	}
	
	
	
}
