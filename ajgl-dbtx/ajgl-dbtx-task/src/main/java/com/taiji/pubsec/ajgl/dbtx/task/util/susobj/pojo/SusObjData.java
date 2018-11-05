package com.taiji.pubsec.ajgl.dbtx.task.util.susobj.pojo;

import com.taiji.pubsec.ajgl.dbtx.task.util.BusinessData;

public class SusObjData extends BusinessData {

	/**
	 * 填发时间时间
	 */
	private Long sendTime;

	/**
	 * 提前预警时间（分钟）
	 */
	private Integer aheadTime;
	
	/**
	 * 弹窗时间（秒）
	 */
	private Integer showTime;
	
	/**
	 * 文书Id
	 */
	private String businessId;
	
	/**
	 * 文书包名类名
	 */
	private String businessType;

	/**
	 * 预警方式
	 */
	private String way;
	
	private String caseCode;	//案件编号
	
	private String suspectId;	//嫌疑人id
	
	private String personName;//嫌疑人姓名

	public Long getSendTime() {
		return sendTime;
	}

	public void setSendTime(Long sendTime) {
		this.sendTime = sendTime;
	}

	public Integer getAheadTime() {
		return aheadTime;
	}

	public void setAheadTime(Integer aheadTime) {
		this.aheadTime = aheadTime;
	}

	public Integer getShowTime() {
		return showTime;
	}

	public void setShowTime(Integer showTime) {
		this.showTime = showTime;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getWay() {
		return way;
	}

	public void setWay(String way) {
		this.way = way;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getSuspectId() {
		return suspectId;
	}

	public void setSuspectId(String suspectId) {
		this.suspectId = suspectId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

}
