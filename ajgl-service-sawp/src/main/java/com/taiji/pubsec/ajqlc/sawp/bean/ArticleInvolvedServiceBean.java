package com.taiji.pubsec.ajqlc.sawp.bean;

import java.util.Date;

/**
 * 
 * @author wangfx
 *
 */
public class ArticleInvolvedServiceBean {

	private String area; // 保管区名称

	private String articleCode; // 物品编号

	private String articleId; // 物品id

	private String articleName; // 物品名称

	private String caseCode; // 案件编号

	private String caseHandler; // 办案民警姓名

	private String caseName; // 案件名称

	private String detentionNumber; // v3文书中扣押数量

	private String feature; // 特征

	private Date inStorageTime; // 入库时间

	private String lockerName;

	private String measurementUnit; // 计量单位

	private Double numberInStorage; // 在库数量

	private int outComingNumber; // 出库数量

	private String paper; // 文书名称

	private String papersId; // 文书id

	private String papersType;// 文书类型

	private int returnedNumber; // 返还数量

	private String suspectIdentifier; // 嫌疑人身份证号

	private String suspectName; // 嫌疑人姓名

	private String type; // 物品性质

	private String typeName; // 物品性质名称

	private String lockerId; // 储物架id

	public String getArea() {
		return area;
	}

	public String getArticleCode() {
		return articleCode;
	}

	public String getArticleId() {
		return articleId;
	}

	public String getArticleName() {
		return articleName;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public String getCaseHandler() {
		return caseHandler;
	}

	public String getCaseName() {
		return caseName;
	}

	public String getDetentionNumber() {
		return detentionNumber;
	}

	public String getFeature() {
		return feature;
	}

	public Date getInStorageTime() {
		return inStorageTime;
	}

	public String getLockerName() {
		return lockerName;
	}

	public String getMeasurementUnit() {
		return measurementUnit;
	}

	public int getOutComingNumber() {
		return outComingNumber;
	}

	public String getPaper() {
		return paper;
	}

	public int getReturnedNumber() {
		return returnedNumber;
	}

	public String getSuspectIdentifier() {
		return suspectIdentifier;
	}

	public String getSuspectName() {
		return suspectName;
	}

	public String getType() {
		return type;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public void setCaseHandler(String caseHandler) {
		this.caseHandler = caseHandler;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public void setDetentionNumber(String detentionNumber) {
		this.detentionNumber = detentionNumber;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public void setInStorageTime(Date inStorageTime) {
		this.inStorageTime = inStorageTime;
	}

	public void setLockerName(String lockerName) {
		this.lockerName = lockerName;
	}

	public void setMeasurementUnit(String measurementUnit) {
		this.measurementUnit = measurementUnit;
	}

	public void setOutComingNumber(int outComingNumber) {
		this.outComingNumber = outComingNumber;
	}

	public void setPaper(String paper) {
		this.paper = paper;
	}

	public void setReturnedNumber(int returnedNumber) {
		this.returnedNumber = returnedNumber;
	}

	public void setSuspectIdentifier(String suspectIdentifier) {
		this.suspectIdentifier = suspectIdentifier;
	}

	public void setSuspectName(String suspectName) {
		this.suspectName = suspectName;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ArticleInvolvedServiceBean() {

	}

	public String getLockerId() {
		return lockerId;
	}

	public void setLockerId(String lockerId) {
		this.lockerId = lockerId;
	}

	public ArticleInvolvedServiceBean(String area, String articleCode, String articleId, String articleName,
			String caseCode, String caseHandler, String caseName, String detentionNumber, String feature,
			Date inStorageTime, String lockerName, String measurementUnit, Number numberInStorage,
			Number outComingNumber, String paper, int returnedNumber, String suspectIdentifier, String suspectName,
			String type) {
		super();
		this.area = area;
		this.articleCode = articleCode;
		this.articleId = articleId;
		this.articleName = articleName;
		this.caseCode = caseCode;
		this.caseHandler = caseHandler;
		this.caseName = caseName;
		this.detentionNumber = detentionNumber;
		this.feature = feature;
		this.inStorageTime = inStorageTime;
		this.lockerName = lockerName;
		this.measurementUnit = measurementUnit;
		this.numberInStorage = (double) numberInStorage.intValue();
		this.outComingNumber = outComingNumber.intValue();
		this.paper = paper;
		this.returnedNumber = returnedNumber;
		this.suspectIdentifier = suspectIdentifier;
		this.suspectName = suspectName;
		this.type = type;
	}

	public String getPapersId() {
		return papersId;
	}

	public void setPapersId(String papersId) {
		this.papersId = papersId;
	}

	public String getPapersType() {
		return papersType;
	}

	public void setPapersType(String papersType) {
		this.papersType = papersType;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Double getNumberInStorage() {
		return numberInStorage;
	}

	public void setNumberInStorage(Double numberInStorage) {
		this.numberInStorage = numberInStorage;
	}

}
