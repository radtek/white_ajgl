package com.taiji.pubsec.ajqlc.sawp.bean;

import java.util.List;

public class CriminalBasicCaseBean {
	
	private String caseCode;	//案件编号
	
	private String caseName;	//案件名称
	
	private String handlePolices;	//办案民警
	
	private String policeNumber1;	//办案民警1警号
	
	private String policeNumber2;	//办案民警2警号
	
	private List<SuspectPersonBean> suspectPersons;	//涉案嫌疑人
	
	private List<DocBean> docs;	//对应文书

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

	public String getHandlePolices() {
		return handlePolices;
	}

	public void setHandlePolices(String handlePolices) {
		this.handlePolices = handlePolices;
	}

	public List<SuspectPersonBean> getSuspectPersons() {
		return suspectPersons;
	}

	public void setSuspectPersons(List<SuspectPersonBean> suspectPersons) {
		this.suspectPersons = suspectPersons;
	}

	public List<DocBean> getDocs() {
		return docs;
	}

	public void setDocs(List<DocBean> docs) {
		this.docs = docs;
	}

	public String getPoliceNumber1() {
		return policeNumber1;
	}

	public void setPoliceNumber1(String policeNumber1) {
		this.policeNumber1 = policeNumber1;
	}

	public String getPoliceNumber2() {
		return policeNumber2;
	}

	public void setPoliceNumber2(String policeNumber2) {
		this.policeNumber2 = policeNumber2;
	}
	
}
