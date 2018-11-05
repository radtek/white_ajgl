package com.taiji.pubsec.ajqlc.httpinterface.action.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 手机端调整单项Bean 查询用
 * 
 * @author chengkai
 */
public class AjustmentFormItemBean {

	private String itemId; // 入库单项id

	private String articleName; // 物品名称

	private String articleCode; // 物品编号

	private String feature; // 物品特征描述

	private double numDesc;// 在库数量

	private String measureUnit; // 计量单位
	
	private String caseCode;// 案件编码
	
	private String areaCode;// 原保管区编码

	private String areaName;// 原保管区名称

	private String lockerCode; // 原货架位置编码

	private String lockerName; // 原货架位置名称描述
	
	private String lockerStatus; //储物架状态

	private Long updatedTime; // 执行操作时间

	private String operatePerson;// 操作人

	private List<AdjustmentStorageRecordBean> adjustmentStorageRecordBeans = new ArrayList<AdjustmentStorageRecordBean>(); // 调整单操作记录

	public String getOperatePerson() {
		return operatePerson;
	}

	public void setOperatePerson(String operatePerson) {
		this.operatePerson = operatePerson;
	}

	public AjustmentFormItemBean() {

	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public String getArticleCode() {
		return articleCode;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String getMeasureUnit() {
		return measureUnit;
	}

	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}

	public Long getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Long updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getLockerCode() {
		return lockerCode;
	}

	public String getLockerName() {
		return lockerName;
	}

	public void setLockerCode(String lockerCode) {
		this.lockerCode = lockerCode;
	}

	public void setLockerName(String lockerName) {
		this.lockerName = lockerName;
	}

	public List<AdjustmentStorageRecordBean> getAdjustmentStorageRecordBeans() {
		return adjustmentStorageRecordBeans;
	}

	public void setAdjustmentStorageRecordBeans(List<AdjustmentStorageRecordBean> adjustmentStorageRecordBeans) {
		this.adjustmentStorageRecordBeans = adjustmentStorageRecordBeans;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public double getNumDesc() {
		return numDesc;
	}

	public void setNumDesc(double numDesc) {
		this.numDesc = numDesc;
	}

	public String getLockerStatus() {
		return lockerStatus;
	}

	public void setLockerStatus(String lockerStatus) {
		this.lockerStatus = lockerStatus;
	}

}