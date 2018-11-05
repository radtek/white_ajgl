package com.taiji.pubsec.ajqlc.httpinterface.action.bean;

/**
 * 手机端出库单项Bean
 * 
 * @author WangLei
 *
 */
public class OutStorageItemBean {

	private String itemId;// 出库单项id

	private String articleName;// 物品名称

	private String articleCode;// 物品编号
	
	private String articleId;// 物品id

	private String feature;// 物品特征描述
	
	private double numberInStorage;// 在库数量
	
	private String measureUnit;// 计量单位
	
	private double outcomingNumber;// 出库数量
	
	private String areaCode;// 保管区编码
	
	private String areaName;// 保管区名称
	
	private String lockerCode;// 储物架编码
	
	private String lockerName;// 储物架名称
	
	private String lockerStatus; //储物架状态

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

	public String getLockerCode() {
		return lockerCode;
	}

	public void setLockerCode(String lockerCode) {
		this.lockerCode = lockerCode;
	}

	public String getLockerName() {
		return lockerName;
	}

	public void setLockerName(String lockerName) {
		this.lockerName = lockerName;
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public double getNumberInStorage() {
		return numberInStorage;
	}

	public void setNumberInStorage(double numberInStorage) {
		this.numberInStorage = numberInStorage;
	}

	public double getOutcomingNumber() {
		return outcomingNumber;
	}

	public void setOutcomingNumber(double outcomingNumber) {
		this.outcomingNumber = outcomingNumber;
	}

	public String getLockerStatus() {
		return lockerStatus;
	}

	public void setLockerStatus(String lockerStatus) {
		this.lockerStatus = lockerStatus;
	}
	
}
