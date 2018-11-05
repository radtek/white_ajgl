package com.taiji.pubsec.ajqlc.httpinterface.action.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 单子bean
 * 
 * @author chengkai
 */
public class FormBean {

	private String formCode; // 单号

	private String caseCode; // 案件编码

	private String caseName; // 案件名称

	private String suspectName; // 嫌疑人姓名

	private String papers; // 文书名称，多个逗号隔开

	private Long operateTime; // 操作时间

	private String type; // 出库类型

	private String outStorageFormCode; // 出库单编码

	private List<FormItemBean> items = new ArrayList<FormItemBean>(); // 单项beanList

	private List<OutStorageItemBean> outItems = new ArrayList<OutStorageItemBean>();// 出库单项Bean集合

	public FormBean() {

	}

	public String getFormCode() {
		return formCode;
	}

	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}

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

	public String getSuspectName() {
		return suspectName;
	}

	public void setSuspectName(String suspectName) {
		this.suspectName = suspectName;
	}

	public String getPapers() {
		return papers;
	}

	public void setPapers(String papers) {
		this.papers = papers;
	}

	public Long getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Long operateTime) {
		this.operateTime = operateTime;
	}

	public List<FormItemBean> getItems() {
		return items;
	}

	public void setItems(List<FormItemBean> items) {
		this.items = items;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOutStorageFormCode() {
		return outStorageFormCode;
	}

	public void setOutStorageFormCode(String outStorageFormCode) {
		this.outStorageFormCode = outStorageFormCode;
	}

	public List<OutStorageItemBean> getOutItems() {
		return outItems;
	}

	public void setOutItems(List<OutStorageItemBean> outItems) {
		this.outItems = outItems;
	}

}