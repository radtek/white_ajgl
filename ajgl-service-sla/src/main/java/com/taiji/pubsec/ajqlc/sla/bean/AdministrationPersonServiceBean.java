package com.taiji.pubsec.ajqlc.sla.bean;

public class AdministrationPersonServiceBean {

	private String suspectName;	//嫌疑人名称
	
	private String administrationArrest;	//行政拘留
	
	private Long communityDrugDeadline;	//责令社区戒毒
	
	private Long isolationDrugDeadline;	//强制隔离戒毒
	
	private Long enterAreaTime;	//进入办案区时间

	private CaseServiceBean caseInfo = new CaseServiceBean(); //案件信息
	public String getSuspectName() {
		return suspectName;
	}

	public void setSuspectName(String suspectName) {
		this.suspectName = suspectName;
	}

	public String getAdministrationArrest() {
		return administrationArrest;
	}

	public void setAdministrationArrest(String administrationArrest) {
		this.administrationArrest = administrationArrest;
	}

	public Long getCommunityDrugDeadline() {
		return communityDrugDeadline;
	}

	public void setCommunityDrugDeadline(Long communityDrugDeadline) {
		this.communityDrugDeadline = communityDrugDeadline;
	}

	public Long getIsolationDrugDeadline() {
		return isolationDrugDeadline;
	}

	public void setIsolationDrugDeadline(Long isolationDrugDeadline) {
		this.isolationDrugDeadline = isolationDrugDeadline;
	}

	public CaseServiceBean getCaseInfo() {
		return caseInfo;
	}

	public void setCaseInfo(CaseServiceBean caseInfo) {
		this.caseInfo = caseInfo;
	}

	public Long getEnterAreaTime() {
		return enterAreaTime;
	}

	public void setEnterAreaTime(Long enterAreaTime) {
		this.enterAreaTime = enterAreaTime;
	}
	
	
	
}
