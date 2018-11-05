package com.taiji.pubsec.ajqlc.baq.bean;

/**
 * 使用单办案民警信息
 */
public class PoliceInfoBean {
	
	private String id;	// id
	
	private String policeName;	// 姓名
	
	private String policeNum;	// 警号
	
	private String ifMainPolice;	// 是否主办民警
	
	private String remark;	// 备注
	
	private String icNum;	// ic卡号
	
	private String sendCardPeopleName;	// 发卡人
	
	private String sendCardPeopleCode;	// 发卡人
	
	private Long sendCardTime;	// 发卡时间
	
	private boolean relationWithHar;	// 是否需要关联使用单
	
	private boolean relationWithCard;	// 是否需要发卡
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPoliceName() {
		return policeName;
	}

	public void setPoliceName(String policeName) {
		this.policeName = policeName;
	}

	public String getPoliceNum() {
		return policeNum;
	}

	public void setPoliceNum(String policeNum) {
		this.policeNum = policeNum;
	}

	public String getIfMainPolice() {
		return ifMainPolice;
	}

	public void setIfMainPolice(String ifMainPolice) {
		this.ifMainPolice = ifMainPolice;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIcNum() {
		return icNum;
	}

	public void setIcNum(String icNum) {
		this.icNum = icNum;
	}

	public String getSendCardPeopleName() {
		return sendCardPeopleName;
	}

	public void setSendCardPeopleName(String sendCardPeopleName) {
		this.sendCardPeopleName = sendCardPeopleName;
	}

	public Long getSendCardTime() {
		return sendCardTime;
	}

	public void setSendCardTime(Long sendCardTime) {
		this.sendCardTime = sendCardTime;
	}

	public String getSendCardPeopleCode() {
		return sendCardPeopleCode;
	}

	public void setSendCardPeopleCode(String sendCardPeopleCode) {
		this.sendCardPeopleCode = sendCardPeopleCode;
	}

	public boolean isRelationWithHar() {
		return relationWithHar;
	}

	public void setRelationWithHar(boolean relationWithHar) {
		this.relationWithHar = relationWithHar;
	}

	public boolean isRelationWithCard() {
		return relationWithCard;
	}

	public void setRelationWithCard(boolean relationWithCard) {
		this.relationWithCard = relationWithCard;
	}
	
}
