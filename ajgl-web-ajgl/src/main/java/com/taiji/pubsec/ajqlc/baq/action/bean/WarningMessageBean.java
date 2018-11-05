package com.taiji.pubsec.ajqlc.baq.action.bean;


/**
 * 预警消息bean
 * @author 
 */
public class WarningMessageBean{

	private String warningContent;	// 预警内容
	
	private String warningPlace;	// 预警位置
	
	private Long warningTime;	// 预警时间

	private String warningTimeStr;	// 预警时间

	public String getWarningContent() {
		return warningContent;
	}

	public void setWarningContent(String warningContent) {
		this.warningContent = warningContent;
	}

	public String getWarningPlace() {
		return warningPlace;
	}

	public void setWarningPlace(String warningPlace) {
		this.warningPlace = warningPlace;
	}

	public Long getWarningTime() {
		return warningTime;
	}

	public void setWarningTime(Long warningTime) {
		this.warningTime = warningTime;
	}

	public String getWarningTimeStr() {
		return warningTimeStr;
	}

	public void setWarningTimeStr(String warningTimeStr) {
		this.warningTimeStr = warningTimeStr;
	}

}