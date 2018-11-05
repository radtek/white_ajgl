package com.taiji.pubsec.ajqlc.sla.bean;

public class PenalPersonServiceBean {
	
	private String suspectName;	//嫌疑人名称
	
	private Long detainedDeadline; //刑拘截止日期
	
	private Long extendedDetainedDeadline; //延长拘留截止日期
	
	private Long toArrestDeadline;	//提请批准逮捕截止日期
	
	private Long approvingArrestDeadline; //批捕截止日期
	
	private Long removalDeadline;	//移送起诉截止日期
	
	private Long bailDeadline;	//取保候审截止期限
	
	private Long residentialSurveillanceDeadline;	//监视居住截止期限
	
	private Long enterAreaTime;	//进入办案区时间
	
	private CaseServiceBean caseInfo = new CaseServiceBean(); //案件信息

	public String getSuspectName() {
		return suspectName;
	}

	public void setSuspectName(String suspectName) {
		this.suspectName = suspectName;
	}

	public Long getDetainedDeadline() {
		return detainedDeadline;
	}

	public void setDetainedDeadline(Long detainedDeadline) {
		this.detainedDeadline = detainedDeadline;
	}

	public Long getExtendedDetainedDeadline() {
		return extendedDetainedDeadline;
	}

	public void setExtendedDetainedDeadline(Long extendedDetainedDeadline) {
		this.extendedDetainedDeadline = extendedDetainedDeadline;
	}

	public Long getToArrestDeadline() {
		return toArrestDeadline;
	}

	public void setToArrestDeadline(Long toArrestDeadline) {
		this.toArrestDeadline = toArrestDeadline;
	}

	public Long getApprovingArrestDeadline() {
		return approvingArrestDeadline;
	}

	public void setApprovingArrestDeadline(Long approvingArrestDeadline) {
		this.approvingArrestDeadline = approvingArrestDeadline;
	}

	public Long getRemovalDeadline() {
		return removalDeadline;
	}

	public void setRemovalDeadline(Long removalDeadline) {
		this.removalDeadline = removalDeadline;
	}

	public Long getBailDeadline() {
		return bailDeadline;
	}

	public void setBailDeadline(Long bailDeadline) {
		this.bailDeadline = bailDeadline;
	}

	public Long getResidentialSurveillanceDeadline() {
		return residentialSurveillanceDeadline;
	}

	public void setResidentialSurveillanceDeadline(Long residentialSurveillanceDeadline) {
		this.residentialSurveillanceDeadline = residentialSurveillanceDeadline;
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
