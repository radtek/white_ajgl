package com.taiji.pubsec.ajqlc.httpinterface.action.bean;

import java.util.ArrayList;
import java.util.List;

import com.taiji.pubsec.ajqlc.operationrecord.model.OperationRecord;

/**
 * 单项Bean 查询用
 * 
 * @author chengkai
 */
public class FormItemBean {

	private String itemId; // 入库单项id

	private String articleName; // 物品名称

	private String articleCode; // 物品编号

	private String feature; // 物品特征描述

	private String measureUnit; // 计量单位
	
	private String areaCode;// 保管区编码

	private String areaName;// 保管区名称

	private Long updatedTime; // 执行操作时间

	private double numDesc;// 在库数量

	private double requestNumber; // 要求数量

	private double operationNumber; // 实际完成数量

	private double numberReturned; // 已返还数量

	private List<StorageBean> storageBeans = new ArrayList<>(); // 物品位置

	/**
	 * 入库操作
	 */
	private List<OperationRecord> operations = new ArrayList<>(); // 入库操作

	/**
	 * 入库记录
	 */
	private List<InStorageRecordBean> inStorageRecordBeans = new ArrayList<>();

	/**
	 * 返还操作
	 */
	private List<BackStorageRecordBean> backStorageRecordBeans = new ArrayList<>();

	private String operatePerson;// 操作人

	public String getOperatePerson() {
		return operatePerson;
	}

	public void setOperatePerson(String operatePerson) {
		this.operatePerson = operatePerson;
	}

	public FormItemBean() {

	}

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

	public Long getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Long updatedTime) {
		this.updatedTime = updatedTime;
	}

	public double getOperationNumber() {
		return operationNumber;
	}

	public void setOperationNumber(double operationNumber) {
		this.operationNumber = operationNumber;
	}

	public List<OperationRecord> getOperations() {
		return operations;
	}

	public double getRequestNumber() {
		return requestNumber; 
	}

	public void setRequestNumber(double requestNumber) {
		this.requestNumber = requestNumber;
	}

	public void setOperations(List<OperationRecord> operations) {
		this.operations = operations;
	}

	public List<InStorageRecordBean> getInStorageRecordBeans() {
		return inStorageRecordBeans;
	}

	public void setInStorageRecordBeans(List<InStorageRecordBean> inStorageRecordBeans) {
		this.inStorageRecordBeans = inStorageRecordBeans;
	}

	public List<StorageBean> getStorageBeans() {
		return storageBeans;
	}

	public void setStorageBeans(List<StorageBean> storageBeans) {
		this.storageBeans = storageBeans;
	}

	public List<BackStorageRecordBean> getBackStorageRecordBeans() {
		return backStorageRecordBeans;
	}

	public void setBackStorageRecordBeans(List<BackStorageRecordBean> backStorageRecordBeans) {
		this.backStorageRecordBeans = backStorageRecordBeans;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public double getNumDesc() {
		return numDesc;
	}

	public void setNumDesc(double numDesc) {
		this.numDesc = numDesc;
	}

	public double getNumberReturned() {
		return numberReturned;
	}

	public void setNumberReturned(double numberReturned) {
		this.numberReturned = numberReturned;
	}
	
}