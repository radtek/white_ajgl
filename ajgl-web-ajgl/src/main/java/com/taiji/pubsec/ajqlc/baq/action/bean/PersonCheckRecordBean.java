package com.taiji.pubsec.ajqlc.baq.action.bean;

import java.util.List;

import com.taiji.pubsec.ajgl.util.bean.FileObjectBean;

/**
 * 人身检查记录Bean
 * 
 * @author WL-PC
 *
 */
public class PersonCheckRecordBean {

	private String id;
	
	private String selfReportedSymptoms;// 自述病症
	
	private String checkCondition;// 检查情况
	
	private String checkPolice;// 检查民警
	
	private String eyewitness;// 见证人
	
	private String isCheckedPerson;// 被检查人
	
	private PersonBean modifyPeopleBean;// 最新修改人
	
	private String updateTime;// 最新修改时间
	
	private String handlingAreaReceiptId;// 办案区使用单id

	private List<FileObjectBean> files;// 附件

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSelfReportedSymptoms() {
		return selfReportedSymptoms;
	}

	public void setSelfReportedSymptoms(String selfReportedSymptoms) {
		this.selfReportedSymptoms = selfReportedSymptoms;
	}

	public String getCheckCondition() {
		return checkCondition;
	}

	public void setCheckCondition(String checkCondition) {
		this.checkCondition = checkCondition;
	}

	public String getCheckPolice() {
		return checkPolice;
	}

	public void setCheckPolice(String checkPolice) {
		this.checkPolice = checkPolice;
	}

	public String getEyewitness() {
		return eyewitness;
	}

	public void setEyewitness(String eyewitness) {
		this.eyewitness = eyewitness;
	}

	public String getIsCheckedPerson() {
		return isCheckedPerson;
	}

	public void setIsCheckedPerson(String isCheckedPerson) {
		this.isCheckedPerson = isCheckedPerson;
	}

	public PersonBean getModifyPeopleBean() {
		return modifyPeopleBean;
	}

	public void setModifyPeopleBean(PersonBean modifyPeopleBean) {
		this.modifyPeopleBean = modifyPeopleBean;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getHandlingAreaReceiptId() {
		return handlingAreaReceiptId;
	}

	public void setHandlingAreaReceiptId(String handlingAreaReceiptId) {
		this.handlingAreaReceiptId = handlingAreaReceiptId;
	}

	public List<FileObjectBean> getFiles() {
		return files;
	}

	public void setFiles(List<FileObjectBean> files) {
		this.files = files;
	}
}
