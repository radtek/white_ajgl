package com.taiji.pubsec.ajqlc.ajjk.action.bean;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 案件执行过程Bean
 *
 */
public class CaseExecutionProcessBean{
	
	private String caseId;	//案件id
	
	private String CaseName;
	
	private String personId;	//嫌疑人
	
	private String PersonName;
	
	private String stepName;	//步骤名称
	
	private String paperNumber;	//文书文号
	
	private String paperId;	//文书id	
	
	private String paperType;	//文书类型
	
	private String issuedPerson;	//填发人
	
	private String issuedUnit;	//填发单位
	
	private String issuedTime;	///填发时间

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public String getPaperNumber() {
		return paperNumber;
	}

	public void setPaperNumber(String paperNumber) {
		this.paperNumber = paperNumber;
	}

	public String getPaperId() {
		return paperId;
	}

	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}

	public String getPaperType() {
		return paperType;
	}

	public void setPaperType(String paperType) {
		this.paperType = paperType;
	}

	public String getIssuedPerson() {
		return issuedPerson;
	}

	public void setIssuedPerson(String issuedPerson) {
		this.issuedPerson = issuedPerson;
	}

	public String getIssuedUnit() {
		return issuedUnit;
	}

	public void setIssuedUnit(String issuedUnit) {
		this.issuedUnit = issuedUnit;
	}

	public String getCaseName() {
		return CaseName;
	}

	public void setCaseName(String caseName) {
		CaseName = caseName;
	}

	public String getPersonName() {
		return PersonName;
	}

	public void setPersonName(String personName) {
		PersonName = personName;
	}

	public String getIssuedTime() {
		return issuedTime;
	}

	public void setIssuedTime(String issuedTime) {
		this.issuedTime = issuedTime;
	}
	
}
