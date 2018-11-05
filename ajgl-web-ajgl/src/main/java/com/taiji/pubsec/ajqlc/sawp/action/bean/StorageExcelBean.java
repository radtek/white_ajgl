package com.taiji.pubsec.ajqlc.sawp.action.bean;

/**
 * 台账ExcelBean
 * 
 * @author WangLei
 *
 */
public class StorageExcelBean {

	private String count;

	private String name; // 物品名称

	private String feature; // 物品特征

	private String code; // 编号

	private String detentionNumber; // 扣押数量，文字描述，从v3获取

	private String typeName; // 物品性质，字典项名称

	private String numberInStorage; // 在库数量
	
	private String measurementUnit; // 计量单位
	
	private String areaName;// 区域名称

	private String location; // 储物箱位置
	
	private String inStorageTime; // 入库时间
	
	private String backStorageTime; // 返还库时间
	
	private String caseCode; // 案件编号
	
	private String caseName; // 案件名称
	
	private String policeName;//办案民警
	
	private String suspectName; // 嫌疑人姓名
	
	private String paper; // 文书名称

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getInStorageTime() {
		return inStorageTime;
	}

	public void setInStorageTime(String inStorageTime) {
		this.inStorageTime = inStorageTime;
	}

	public String getBackStorageTime() {
		return backStorageTime;
	}

	public void setBackStorageTime(String backStorageTime) {
		this.backStorageTime = backStorageTime;
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

	public String getMeasurementUnit() {
		return measurementUnit;
	}

	public void setMeasurementUnit(String measurementUnit) {
		this.measurementUnit = measurementUnit;
	}

	public String getPoliceName() {
		return policeName;
	}

	public void setPoliceName(String policeName) {
		this.policeName = policeName;
	}

	
}
