package com.taiji.pubsec.ajqlc.wrapper.jzpt.service.bean;

/**
 * 前科记录信息
 * 
 * @author dixiaofeng
 *
 */
public class CriminalRecordBean {

	private String caseCode; // 案件编码
	private String caseName; // 案件名称
	private String caseTime; // 涉案时间
	private String police; // 办案民警
	private String picture; // 嫌疑人照片

	/**
	 * 案件编码
	 * @return 案件编码
	 */
	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	/**
	 * 案件名称
	 * @return
	 */
	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	/**
	 * 涉案时间
	 * @return 涉案时间
	 */
	public String getCaseTime() {
		return caseTime;
	}

	public void setCaseTime(String caseTime) {
		this.caseTime = caseTime;
	}

	/**
	 * 办案民警
	 * @return 办案民警
	 */
	public String getPolice() {
		return police;
	}

	public void setPolice(String police) {
		this.police = police;
	}

	/**
	 * 嫌疑人照片
	 * @return 嫌疑人照片
	 */
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

}
