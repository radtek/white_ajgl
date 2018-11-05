package com.taiji.pubsec.ajqlc.sawp.action.bean;

import java.util.List;

/**
 * 入库单项Bean
 * 
 * @author WangLei
 *
 */
public class InStorageFormItemBean {

	private String id;// id

	private int numberRequested;// 要求入库数量

	private String formId;// 入库单id

	private String articleId; // 物品id

	private String caseCode; // 案件编号

	private String caseName; // 案件名称

	private String articleCode; // 物品编号

	private String detentionNumber; // v3文书中扣押数量

	private String feature; // 物品特征

	private String measurementUnit; // 计量单位

	private String articleName; // 物品名称

	private String paperId; // 文书id

	private String paperType;// 文书类型

	private String paperName;// 文书名称

	private String caseHandler; // 办案民警姓名
	
	private String suspectId;	//嫌疑人id

	private String suspectName; // 嫌疑人姓名

	private String suspectIdentifier; // 嫌疑人身份证号

	private String type; // 物品性质 字典项 多个

	private String typeName; // 物品性质名称

	private Long inStorageTime; // 入库时间

	private int numberIncome; // 已入库数量

	private String remark; // 备注

	private Long updatedTime; // 更新时间

	private List<StorageBean> storageBeans; // 位置信息

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getArticleCode() {
		return articleCode;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getCaseHandler() {
		return caseHandler;
	}

	public void setCaseHandler(String caseHandler) {
		this.caseHandler = caseHandler;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getDetentionNumber() {
		return detentionNumber;
	}

	public void setDetentionNumber(String detentionNumber) {
		this.detentionNumber = detentionNumber;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String getMeasurementUnit() {
		return measurementUnit;
	}

	public void setMeasurementUnit(String measurementUnit) {
		this.measurementUnit = measurementUnit;
	}

	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}

	public String getPaperName() {
		return paperName;
	}

	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}

	public String getPaperType() {
		return paperType;
	}

	public void setPaperType(String paperType) {
		this.paperType = paperType;
	}

	public String getSuspectIdentifier() {
		return suspectIdentifier;
	}

	public void setSuspectIdentifier(String suspectIdentifier) {
		this.suspectIdentifier = suspectIdentifier;
	}

	public String getSuspectName() {
		return suspectName;
	}

	public void setSuspectName(String suspectName) {
		this.suspectName = suspectName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Long getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Long updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Long getInStorageTime() {
		return inStorageTime;
	}

	public void setInStorageTime(Long inStorageTime) {
		this.inStorageTime = inStorageTime;
	}

	public List<StorageBean> getStorageBeans() {
		return storageBeans;
	}

	public void setStorageBeans(List<StorageBean> storageBeans) {
		this.storageBeans = storageBeans;
	}

	public int getNumberRequested() {
		return numberRequested;
	}

	public void setNumberRequested(int numberRequested) {
		this.numberRequested = numberRequested;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public int getNumberIncome() {
		return numberIncome;
	}

	public void setNumberIncome(int numberIncome) {
		this.numberIncome = numberIncome;
	}

	public String getPaperId() {
		return paperId;
	}

	public String getSuspectId() {
		return suspectId;
	}

	public void setSuspectId(String suspectId) {
		this.suspectId = suspectId;
	}

}
