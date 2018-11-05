package com.taiji.pubsec.ajqlc.sawp.action.bean;


/**
 *超时未返还物品清单Bean
 * @author 
 */
public class TransitStorageReturnTimeOutExcelBean {
	
	private String count;
	
	public String storageInDateTime; // 超时时长


	public String caseCode; // 案件编号

	public String caseName; // 案件名称

	public String solvePolice; // 办案民警

	public String objectOwnerPerson;// 物品持有人

	public String inventory;// 物品清单
	
	public String inStorageSelf;//所在储物箱
	
	public String inStorageArea;//所在储物区
	
	public String remark; // 备注

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getStorageInDateTime() {
		return storageInDateTime;
	}

	public void setStorageInDateTime(String storageInDateTime) {
		this.storageInDateTime = storageInDateTime;
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

	public String getSolvePolice() {
		return solvePolice;
	}

	public void setSolvePolice(String solvePolice) {
		this.solvePolice = solvePolice;
	}

	public String getObjectOwnerPerson() {
		return objectOwnerPerson;
	}

	public void setObjectOwnerPerson(String objectOwnerPerson) {
		this.objectOwnerPerson = objectOwnerPerson;
	}

	public String getInventory() {
		return inventory;
	}

	public void setInventory(String inventory) {
		this.inventory = inventory;
	}

	public String getInStorageSelf() {
		return inStorageSelf;
	}

	public void setInStorageSelf(String inStorageSelf) {
		this.inStorageSelf = inStorageSelf;
	}

	public String getInStorageArea() {
		return inStorageArea;
	}

	public void setInStorageArea(String inStorageArea) {
		this.inStorageArea = inStorageArea;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	}
