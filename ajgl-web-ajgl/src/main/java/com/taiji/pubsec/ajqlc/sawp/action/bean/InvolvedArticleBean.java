package com.taiji.pubsec.ajqlc.sawp.action.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 涉案物品
 * 
 * @author WangLei
 *
 */
public class InvolvedArticleBean {

	private String id;

	private String caseCode; // 案件编号
	
	private String caseName; // 案件名称

	private String code; // 编号

	private String detentionNumber; // 扣押数量，文字描述，从v3获取

	private String feature; // 特征

	private String measurementUnit; // 计量单位

	private String name; // 名称

	private Double numberInStorage; // 在库数量

	private String paper; // 文书名称
	
	private String paperId;// 文书id
	
	private String paperType;// 文书类型

	private String polices; // 办案民警姓名

	private String suspectName; // 嫌疑人姓名
	
	private String suspectIdentityNumber; // 嫌疑人身份证号码

	private String type; // 物品性质，字典项id
	
	private String typeName; // 物品性质，字典项名称
	
	private Long inStorageTime; // 入库时间
	
	private Long backStorageTime; // 返还时间

	private List<StorageBean> storages = new ArrayList<StorageBean>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getNumberInStorage() {
		return numberInStorage;
	}

	public void setNumberInStorage(Double numberInStorage) {
		this.numberInStorage = numberInStorage;
	}

	public String getPaper() {
		return paper;
	}

	public void setPaper(String paper) {
		this.paper = paper;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<StorageBean> getStorages() {
		return storages;
	}

	public void setStorages(List<StorageBean> storages) {
		this.storages = storages;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Long getInStorageTime() {
		return inStorageTime;
	}

	public void setInStorageTime(Long inStorageTime) {
		this.inStorageTime = inStorageTime;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getSuspectIdentityNumber() {
		return suspectIdentityNumber;
	}

	public void setSuspectIdentityNumber(String suspectIdentityNumber) {
		this.suspectIdentityNumber = suspectIdentityNumber;
	}

	public Long getBackStorageTime() {
		return backStorageTime;
	}

	public void setBackStorageTime(Long backStorageTime) {
		this.backStorageTime = backStorageTime;
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

	
}
