package com.taiji.pubsec.ajqlc.sawp.action.bean;

/**
 * 物品统计Excel导出Bean
 * 
 * @author WangLei
 *
 */
public class ArticleInvolvedExcelBean {

	private String count;

	private String articleName; // 物品名称

	private String feature; // 特征

	private String articleCode; // 物品编号

	private String detentionNumber; // v3文书中扣押数量

	private String typeName; // 物品性质名称

	private String numberInStorage; // 在库数量

	private String measurementUnit; // 计量单位

	private String outComingNumber; // 出库数量

	private String returnedNumber; // 返还数量

	private String caseCode;// 案件编号

	private String caseName; // 案件名称

	private String caseHandler; // 办案民警姓名

	private String suspectName; // 嫌疑人姓名/嫌疑人身份证号

	private String paper; // 文书名称

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
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

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getNumberInStorage() {
		return numberInStorage;
	}

	public void setNumberInStorage(String numberInStorage) {
		this.numberInStorage = numberInStorage;
	}

	public String getMeasurementUnit() {
		return measurementUnit;
	}

	public void setMeasurementUnit(String measurementUnit) {
		this.measurementUnit = measurementUnit;
	}

	public String getOutComingNumber() {
		return outComingNumber;
	}

	public void setOutComingNumber(String outComingNumber) {
		this.outComingNumber = outComingNumber;
	}

	public String getReturnedNumber() {
		return returnedNumber;
	}

	public void setReturnedNumber(String returnedNumber) {
		this.returnedNumber = returnedNumber;
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

	public String getCaseHandler() {
		return caseHandler;
	}

	public void setCaseHandler(String caseHandler) {
		this.caseHandler = caseHandler;
	}

	public String getSuspectName() {
		return suspectName;
	}

	public void setSuspectName(String suspectName) {
		this.suspectName = suspectName;
	}

	public String getPaper() {
		return paper;
	}

	public void setPaper(String paper) {
		this.paper = paper;
	}
	
	

}
