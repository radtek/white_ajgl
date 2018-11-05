package com.taiji.pubsec.ajqlc.sawp.action.bean;

/**
 * 物品统计Bean
 * 
 * @author WangLei
 *
 */
public class ArticleStatisticsBean {

	private String id;
	
	private String involvedArticleName;// 物品名称
	
	private String involvedArticleFeature;// 物品特征
	
	private String involvedArticleCode;// 物品编号
	
	private String detentionNumber;// 扣押数量，v3系统中物品数量
	
	private String involvedArticleType;// 物品性质，字典项id
	
	private String involvedArticleTypeName;// 涉案物品性质，字典项名称
	
	private int numberInStorage;// 在库数量
	
	private String measurementUnit;// 计量单位

	private int outcomingNumber;// 出库数量
	
	private int returnedNumber;	// 返还数量
	
	private String caseCode;// 案件编号
	
	private String caseName;// 案件名称
	
	private String handlePolices;// 办案民警
	
	private String suspectedName;// 嫌疑人姓名
	
	private String suspectIdentityNumber;// 嫌疑人身份证号
	
	private String papers;// 文书名称

	private String papersId; // 文书id
	
	private String papersType;// 文书类型

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInvolvedArticleName() {
		return involvedArticleName;
	}

	public void setInvolvedArticleName(String involvedArticleName) {
		this.involvedArticleName = involvedArticleName;
	}

	public String getInvolvedArticleFeature() {
		return involvedArticleFeature;
	}

	public void setInvolvedArticleFeature(String involvedArticleFeature) {
		this.involvedArticleFeature = involvedArticleFeature;
	}

	public String getInvolvedArticleCode() {
		return involvedArticleCode;
	}

	public void setInvolvedArticleCode(String involvedArticleCode) {
		this.involvedArticleCode = involvedArticleCode;
	}

	public String getDetentionNumber() {
		return detentionNumber;
	}

	public void setDetentionNumber(String detentionNumber) {
		this.detentionNumber = detentionNumber;
	}

	public String getInvolvedArticleType() {
		return involvedArticleType;
	}

	public void setInvolvedArticleType(String involvedArticleType) {
		this.involvedArticleType = involvedArticleType;
	}

	public String getInvolvedArticleTypeName() {
		return involvedArticleTypeName;
	}

	public void setInvolvedArticleTypeName(String involvedArticleTypeName) {
		this.involvedArticleTypeName = involvedArticleTypeName;
	}

	public int getNumberInStorage() {
		return numberInStorage;
	}

	public void setNumberInStorage(int numberInStorage) {
		this.numberInStorage = numberInStorage;
	}

	public String getMeasurementUnit() {
		return measurementUnit;
	}

	public void setMeasurementUnit(String measurementUnit) {
		this.measurementUnit = measurementUnit;
	}

	public int getOutcomingNumber() {
		return outcomingNumber;
	}

	public void setOutcomingNumber(int outcomingNumber) {
		this.outcomingNumber = outcomingNumber;
	}

	public int getReturnedNumber() {
		return returnedNumber;
	}

	public void setReturnedNumber(int returnedNumber) {
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

	public String getHandlePolices() {
		return handlePolices;
	}

	public void setHandlePolices(String handlePolices) {
		this.handlePolices = handlePolices;
	}

	public String getSuspectedName() {
		return suspectedName;
	}

	public void setSuspectedName(String suspectedName) {
		this.suspectedName = suspectedName;
	}

	public String getSuspectIdentityNumber() {
		return suspectIdentityNumber;
	}

	public void setSuspectIdentityNumber(String suspectIdentityNumber) {
		this.suspectIdentityNumber = suspectIdentityNumber;
	}

	public String getPapers() {
		return papers;
	}

	public void setPapers(String papers) {
		this.papers = papers;
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
	
	
	
}
