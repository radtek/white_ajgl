package com.taiji.pubsec.ajqlc.baq.bean;

import java.util.Date;

public class HandlingAreaReceiptServiceBean {
	
	private String byQuestioningPeopleName;	// 被问讯人姓名
	
	private String lawCase;	// 所属案件
	
	private String sex;	// 性别
	
	private Long enterIntoTime;	// 进入办案区时间
	
	private String sponsorUnitCode;	//主办单位编码
	
	private String mainPolices;	//主办民警
	
	private String causeOfLawCase;	// 案由
	
	private String status;	//讯问状态
	
	private String id;//使用单id
	
	private String harCode;//使用单编号
	

	public String getByQuestioningPeopleName() {
		return byQuestioningPeopleName;
	}

	public void setByQuestioningPeopleName(String byQuestioningPeopleName) {
		this.byQuestioningPeopleName = byQuestioningPeopleName;
	}

	public String getLawCase() {
		return lawCase;
	}

	public void setLawCase(String lawCase) {
		this.lawCase = lawCase;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Long getEnterIntoTime() {
		return enterIntoTime;
	}

	public void setEnterIntoTime(Long enterIntoTime) {
		this.enterIntoTime = enterIntoTime;
	}

	public String getSponsorUnitCode() {
		return sponsorUnitCode;
	}

	public void setSponsorUnitCode(String sponsorUnitCode) {
		this.sponsorUnitCode = sponsorUnitCode;
	}

	public String getMainPolices() {
		return mainPolices;
	}

	public void setMainPolices(String mainPolices) {
		this.mainPolices = mainPolices;
	}

	public String getCauseOfLawCase() {
		return causeOfLawCase;
	}

	public void setCauseOfLawCase(String causeOfLawCase) {
		this.causeOfLawCase = causeOfLawCase;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHarCode() {
		return harCode;
	}

	public void setHarCode(String harCode) {
		this.harCode = harCode;
	}
	
	
}
