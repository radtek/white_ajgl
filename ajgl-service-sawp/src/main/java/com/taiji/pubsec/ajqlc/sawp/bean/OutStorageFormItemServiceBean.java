package com.taiji.pubsec.ajqlc.sawp.bean;

public class OutStorageFormItemServiceBean {

	private String id;

	private String involvedArticleId; // 涉案物品id

	private String involvedArticleName; // 涉案物品名称

	private String involvedArticleFeature; // 涉案物品特征

	private String involvedArticleCode; // 涉案物品编号

	private String detentionNumber; // 扣押数量

	private String involvedArticleType;// 涉案物品性质，字典项id

	private String involvedArticleTypeName;// 涉案物品性质，字典项名称

	private Double numberInStorage;// 在库数量

	private Double outcomingNumber;// 出库数量

	private String measurementUnit;// 计量单位
	
	private Double returnedNumber;	// 返还数量

	private String lockerId; // 储物架
	
	private String lockerCode;///储物架code

	private String lockerName; // 储物架名称
	
	private String lockerStatus; //储物架状态

	private String areaId; // 保管区id
	
	private String areaCode;//保管区code

	private String areaName; // 保管区名称

	private String suspectedName;// 嫌疑人姓名
	
	private String suspectIdentityNumber;// 嫌疑人身份证号

	private String papers;// 文书名称

	private String papersId; // 文书id
	
	private String papersType;// 文书类型

	private String remark;// 备注

	private Long updatedTime; // 更新时间

	/**
	 * @return id id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return involvedArticleName 涉案物品名称
	 */
	public String getInvolvedArticleName() {
		return involvedArticleName;
	}

	/**
	 * @return involvedArticleFeature 涉案物品特征
	 */
	public String getInvolvedArticleFeature() {
		return involvedArticleFeature;
	}

	/**
	 * @return involvedArticleCode 涉案物品编号
	 */
	public String getInvolvedArticleCode() {
		return involvedArticleCode;
	}

	/**
	 * @return detentionNumber 扣押数量
	 */
	public String getDetentionNumber() {
		return detentionNumber;
	}

	/**
	 * @return involvedArticle 涉案物品性质
	 */
	public String getInvolvedArticleType() {
		return involvedArticleType;
	}
	
	public Double getReturnedNumber() {
		return returnedNumber;
	}

	public void setReturnedNumber(Double returnedNumber) {
		this.returnedNumber = returnedNumber;
	}


	/**
	 * @return measurementUnit 计量单位
	 */
	public String getMeasurementUnit() {
		return measurementUnit;
	}

	public String getLockerId() {
		return lockerId;
	}

	public void setLockerId(String lockerId) {
		this.lockerId = lockerId;
	}

	public String getLockerName() {
		return lockerName;
	}

	public void setLockerName(String lockerName) {
		this.lockerName = lockerName;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	/**
	 * @return suspectedName 嫌疑人姓名
	 */
	public String getSuspectedName() {
		return suspectedName;
	}

	/**
	 * @return papers 文书名称
	 */
	public String getPapers() {
		return papers;
	}

	/**
	 * @return remark 备注
	 */
	public String getRemark() {
		return remark;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setInvolvedArticleName(String involvedArticleName) {
		this.involvedArticleName = involvedArticleName;
	}

	public void setInvolvedArticleFeature(String involvedArticleFeature) {
		this.involvedArticleFeature = involvedArticleFeature;
	}

	public void setInvolvedArticleCode(String involvedArticleCode) {
		this.involvedArticleCode = involvedArticleCode;
	}

	public void setDetentionNumber(String detentionNumber) {
		this.detentionNumber = detentionNumber;
	}

	public void setInvolvedArticleType(String involvedArticleType) {
		this.involvedArticleType = involvedArticleType;
	}

	public void setMeasurementUnit(String measurementUnit) {
		this.measurementUnit = measurementUnit;
	}

	public void setSuspectedName(String suspectedName) {
		this.suspectedName = suspectedName;
	}

	public void setPapers(String papers) {
		this.papers = papers;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getInvolvedArticleId() {
		return involvedArticleId;
	}

	public void setInvolvedArticleId(String involvedArticleId) {
		this.involvedArticleId = involvedArticleId;
	}

	public Long getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Long updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getPapersId() {
		return papersId;
	}

	public void setPapersId(String papersId) {
		this.papersId = papersId;
	}

	public String getInvolvedArticleTypeName() {
		return involvedArticleTypeName;
	}

	public void setInvolvedArticleTypeName(String involvedArticleTypeName) {
		this.involvedArticleTypeName = involvedArticleTypeName;
	}

	public String getSuspectIdentityNumber() {
		return suspectIdentityNumber;
	}

	public void setSuspectIdentityNumber(String suspectIdentityNumber) {
		this.suspectIdentityNumber = suspectIdentityNumber;
	}

	public String getPapersType() {
		return papersType;
	}

	public void setPapersType(String papersType) {
		this.papersType = papersType;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getLockerCode() {
		return lockerCode;
	}

	public void setLockerCode(String lockerCode) {
		this.lockerCode = lockerCode;
	}

	public Double getNumberInStorage() {
		return numberInStorage;
	}

	public void setNumberInStorage(Double numberInStorage) {
		this.numberInStorage = numberInStorage;
	}

	public Double getOutcomingNumber() {
		return outcomingNumber;
	}

	public void setOutcomingNumber(Double outcomingNumber) {
		this.outcomingNumber = outcomingNumber;
	}

	public String getLockerStatus() {
		return lockerStatus;
	}

	public void setLockerStatus(String lockerStatus) {
		this.lockerStatus = lockerStatus;
	}
	
}
