package com.taiji.pubsec.ajqlc.sawp.bean;

import java.util.ArrayList;
import java.util.List;

import com.taiji.pubsec.ajqlc.sawp.model.ArticleLocker;

public class BackStorageFormItemServiceBean {

	private String backItemId; // 返还单项id
	
	private String outItemId; // 出库单项id

	private Double outcomingNumber;// 出库数量
	
	private String involvedArticleId; // 涉案物品id

	private String involvedArticleName; // 涉案物品名称

	private String involvedArticleFeature; // 涉案物品特征

	private String involvedArticleCode; // 涉案物品编号

	private String detentionNumber; // 扣押数量

	private String measurementUnit;// 计量单位

	private String involvedArticleType;// 涉案物品性质，字典项id

	private String involvedArticleTypeName;// 涉案物品性质，字典项名称

	private String areaId; // 保管区id

	private String areaName; // 保管区名称

	private String suspectedName;// 嫌疑人姓名

	private String suspectIdentityNumber;// 嫌疑人身份证号

	private Double returnedNumber;// 返还数量

	private String remark;// 备注
	
	private String isReturend;//是否全部返还

	private Long maintainTime; // 维护时间

	private List<StorageServiceBean> storageServiceBeans = new ArrayList<StorageServiceBean>(); // 保管位置
	
	private List<ArticleLocker> articleLockerList = new ArrayList<ArticleLocker>();// 可用的全部储物柜信息，在更新入库单时使用

	public String getInvolvedArticleId() {
		return involvedArticleId;
	}

	public void setInvolvedArticleId(String involvedArticleId) {
		this.involvedArticleId = involvedArticleId;
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
	 * @return involvedArticleType 涉案物品性质
	 */
	public String getInvolvedArticleType() {
		return involvedArticleType;
	}

	/**
	 * @return returnedNumber 返回数量
	 */
	public Double getReturnedNumber() {
		return returnedNumber;
	}

	/**
	 * @return suspectedName 嫌疑人姓名
	 */
	public String getSuspectedName() {
		return suspectedName;
	}

	/**
	 * @return remark 备注
	 */
	public String getRemark() {
		return remark;
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

	public void setReturnedNumber(Double returnedNumber) {
		this.returnedNumber = returnedNumber;
	}

	public void setSuspectedName(String suspectedName) {
		this.suspectedName = suspectedName;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return maintainTime 维护时间
	 */
	public Long getMaintainTime() {
		return maintainTime;
	}

	public void setMaintainTime(Long maintainTime) {
		this.maintainTime = maintainTime;
	}

	public String getOutItemId() {
		return outItemId;
	}

	public void setOutItemId(String outItemId) {
		this.outItemId = outItemId;
	}

	public List<StorageServiceBean> getStorageServiceBeans() {
		return storageServiceBeans;
	}

	public void setStorageServiceBeans(List<StorageServiceBean> storageServiceBeans) {
		this.storageServiceBeans = storageServiceBeans;
	}

	public String getMeasurementUnit() {
		return measurementUnit;
	}

	public void setMeasurementUnit(String measurementUnit) {
		this.measurementUnit = measurementUnit;
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

	public String getIsReturend() {
		return isReturend;
	}

	public void setIsReturend(String isReturend) {
		this.isReturend = isReturend;
	}

	public String getBackItemId() {
		return backItemId;
	}

	public void setBackItemId(String backItemId) {
		this.backItemId = backItemId;
	}

	public List<ArticleLocker> getArticleLockerList() {
		return articleLockerList;
	}

	public void setArticleLockerList(List<ArticleLocker> articleLockerList) {
		this.articleLockerList = articleLockerList;
	}

	public Double getOutcomingNumber() {
		return outcomingNumber;
	}

	public void setOutcomingNumber(Double outcomingNumber) {
		this.outcomingNumber = outcomingNumber;
	}

}
