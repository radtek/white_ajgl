package com.taiji.pubsec.ajqlc.sawp.bean;

import java.util.ArrayList;
import java.util.List;

import com.taiji.pubsec.ajqlc.sawp.model.ArticleLocker;

public class InStorageFormItemServiceBean {
	
	private String id;// id

	private Double numberRequested;// 要求入库数量

	private String formId;// 入库单id

	private String articleId; // 物品id

	private String caseCode; // 案件编号

	private String caseName; // 案件名称

	private String articleCode; // 物品编号

	private String detentionNumber; // v3文书中扣押数量

	private String articleFeature; // 物品特征

	private String measurementUnit; // 计量单位
	
	private String accumulateSum;  // 累计已入库数量

	private String articleName; // 物品名称

	private String paperId; // 文书id

	private String paperType;// 文书类型

	private String paperName;// 文书名称

	private String polices; // 办案民警姓名
	
	private String policeNumber1;	//办案民警1警号
	
	private String policeNumber2;	//办案民警2警号
	
	private String suspectId;	//嫌疑人id

	private String suspectName; // 嫌疑人姓名

	private String suspectIdentifier; // 嫌疑人身份证号

	private String articleType; // 物品性质 字典项 多个

	private String articleTypeName; // 物品性质名称

	private Long inStorageTime; // 入库时间

	private Double numberIncome; // 已入库数量

	private String remark; // 备注

	private Long updatedTime; // 更新时间
	
	private List<StorageServiceBean> storageServiceBeans = new ArrayList<StorageServiceBean>();// 位置信息
	
	private List<ArticleLocker> articleLockerList = new ArrayList<ArticleLocker>();// 可用的全部储物柜信息，在更新入库单时使用

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getNumberRequested() {
		return numberRequested;
	}

	public void setNumberRequested(Double numberRequested) {
		this.numberRequested = numberRequested;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
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

	public String getArticleFeature() {
		return articleFeature;
	}

	public void setArticleFeature(String articleFeature) {
		this.articleFeature = articleFeature;
	}

	public String getMeasurementUnit() {
		return measurementUnit;
	}

	public void setMeasurementUnit(String measurementUnit) {
		this.measurementUnit = measurementUnit;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public String getPaperId() {
		return paperId;
	}

	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}

	public String getPaperType() {
		return paperType;
	}

	public void setPaperType(String paperType) {
		this.paperType = paperType;
	}

	public String getPaperName() {
		return paperName;
	}

	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}

	public String getPolices() {
		return polices;
	}

	public void setPolices(String polices) {
		this.polices = polices;
	}

	public String getArticleType() {
		return articleType;
	}

	public void setArticleType(String articleType) {
		this.articleType = articleType;
	}

	public String getArticleTypeName() {
		return articleTypeName;
	}

	public void setArticleTypeName(String articleTypeName) {
		this.articleTypeName = articleTypeName;
	}

	public String getSuspectId() {
		return suspectId;
	}

	public void setSuspectId(String suspectId) {
		this.suspectId = suspectId;
	}

	public String getSuspectName() {
		return suspectName;
	}

	public void setSuspectName(String suspectName) {
		this.suspectName = suspectName;
	}

	public String getSuspectIdentifier() {
		return suspectIdentifier;
	}

	public void setSuspectIdentifier(String suspectIdentifier) {
		this.suspectIdentifier = suspectIdentifier;
	}

	public Long getInStorageTime() {
		return inStorageTime;
	}

	public void setInStorageTime(Long inStorageTime) {
		this.inStorageTime = inStorageTime;
	}

	public Double getNumberIncome() {
		return numberIncome;
	}

	public void setNumberIncome(Double numberIncome) {
		this.numberIncome = numberIncome;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Long updatedTime) {
		this.updatedTime = updatedTime;
	}

	public List<StorageServiceBean> getStorageServiceBeans() {
		return storageServiceBeans;
	}

	public void setStorageServiceBeans(List<StorageServiceBean> storageServiceBeans) {
		this.storageServiceBeans = storageServiceBeans;
	}

	public List<ArticleLocker> getArticleLockerList() {
		return articleLockerList;
	}

	public void setArticleLockerList(List<ArticleLocker> articleLockerList) {
		this.articleLockerList = articleLockerList;
	}

	public String getAccumulateSum() {
		return accumulateSum;
	}

	public void setAccumulateSum(String accumulateSum) {
		this.accumulateSum = accumulateSum;
	}

	public String getPoliceNumber1() {
		return policeNumber1;
	}

	public void setPoliceNumber1(String policeNumber1) {
		this.policeNumber1 = policeNumber1;
	}

	public String getPoliceNumber2() {
		return policeNumber2;
	}

	public void setPoliceNumber2(String policeNumber2) {
		this.policeNumber2 = policeNumber2;
	}
}
