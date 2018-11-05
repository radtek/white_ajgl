package com.taiji.pubsec.ajqlc.sawp.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 调整单项bean
 * 
 * @author chengkai
 */
public class AdjustmentStorageFormItemServiceBean {

	private String id; // id

	private String articleId; // 涉案物品id
	
	private String articleName;// 涉案物品名称
	
	private String articleFeature;// 涉案物品特征
	
	private String articleCode; // 涉案物品编号
	
	private String detentionNumber; // 扣押数量

	private String measurementUnit;// 计量单位
	
	private Double existingNumber; // 在库数量
	
	private String caseCode; // 案件编号

	private String caseName; // 案件名称
	
	private String polices;	//办案民警姓名
	
	private String suspectName;	//嫌疑人姓名
	
	private String suspectIdentityNumber;	// 嫌疑人身份证号

	private Double adjustmentNumber; // 涉案物品调整前的在库数量
	
	private String storageId; //原保管位置id
	
	private String oldAreaName; // 原保管区名称
	
	private String oldLockerName;// 原储物架名称

	private String remark; // 备注
	
	private List<StorageServiceBean> storageServiceBeans = new ArrayList<StorageServiceBean>(); // 保管位置

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getStorageId() {
		return storageId;
	}

	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getAdjustmentNumber() {
		return adjustmentNumber;
	}

	public void setAdjustmentNumber(Double adjustmentNumber) {
		this.adjustmentNumber = adjustmentNumber;
	}

	public List<StorageServiceBean> getStorageServiceBeans() {
		return storageServiceBeans;
	}

	public void setStorageServiceBeans(List<StorageServiceBean> storageServiceBeans) {
		this.storageServiceBeans = storageServiceBeans;
	}

	public String getOldAreaName() {
		return oldAreaName;
	}

	public void setOldAreaName(String oldAreaName) {
		this.oldAreaName = oldAreaName;
	}

	public String getOldLockerName() {
		return oldLockerName;
	}

	public void setOldLockerName(String oldLockerName) {
		this.oldLockerName = oldLockerName;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public String getArticleFeature() {
		return articleFeature;
	}

	public void setArticleFeature(String articleFeature) {
		this.articleFeature = articleFeature;
	}

	public String getArticleCode() {
		return articleCode;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	public String getDetentionNumber() {
		return detentionNumber;
	}

	public void setDetentionNumber(String detentionNumber) {
		this.detentionNumber = detentionNumber;
	}

	public String getMeasurementUnit() {
		return measurementUnit;
	}

	public void setMeasurementUnit(String measurementUnit) {
		this.measurementUnit = measurementUnit;
	}

	public Double getExistingNumber() {
		return existingNumber;
	}

	public void setExistingNumber(Double existingNumber) {
		this.existingNumber = existingNumber;
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

	public String getPolices() {
		return polices;
	}

	public void setPolices(String polices) {
		this.polices = polices;
	}

	public String getSuspectName() {
		return suspectName;
	}

	public void setSuspectName(String suspectName) {
		this.suspectName = suspectName;
	}

	public String getSuspectIdentityNumber() {
		return suspectIdentityNumber;
	}

	public void setSuspectIdentityNumber(String suspectIdentityNumber) {
		this.suspectIdentityNumber = suspectIdentityNumber;
	}

}
