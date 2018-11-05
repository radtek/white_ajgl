package com.taiji.pubsec.ajqlc.sawp.bean;

import java.util.List;

/**
 * 暂存入库单Bean
 * 
 * @author xinfan
 *
 */
public class TempStorageInInfoBean {
	
	public String id; 

	public String storageInCode; // 入库单编码

	public String storageInDateTime; // 入库时间
	
	public String harCode;//使用单编码

	public String caseCode; // 案件编号

	public String caseName; // 案件名称

	public String solvePolice; // 办案民警

	public String objectOwnerPerson;// 物品持有人

	public String remark; // 备注

	public String modifyTime;// 最新修改时间

	public String modifyPerson;// 最新修改人

	public String createPerson;//创建人
	
	public String idCard;// 身份证号

	public List<TemporaryStorageShelfBean> inSaveSelfList;//所属储物架 目前只需取第0个就可以

	public List<TempObjectBean> tempObjectBeanList;// 物品清单
	
	public String storageOutStatus; //出库单状态
	
	public String inStorageSelf;//所在储物架
	
	public String inStorageArea;//所在储物区

	public String getStorageInCode() {
		return storageInCode;
	}

	public void setStorageInCode(String storageInCode) {
		this.storageInCode = storageInCode;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyPerson() {
		return modifyPerson;
	}

	public void setModifyPerson(String modifyPerson) {
		this.modifyPerson = modifyPerson;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public List<TempObjectBean> getTempObjectBeanList() {
		return tempObjectBeanList;
	}

	public void setTempObjectBeanList(List<TempObjectBean> tempObjectBeanList) {
		this.tempObjectBeanList = tempObjectBeanList;
	}

	public List<TemporaryStorageShelfBean> getInSaveSelfList() {
		return inSaveSelfList;
	}

	public void setInSaveSelfList(List<TemporaryStorageShelfBean> inSaveSelfList) {
		this.inSaveSelfList = inSaveSelfList;
	}

	public String getHarCode() {
		return harCode;
	}

	public void setHarCode(String harCode) {
		this.harCode = harCode;
	}

	public String getStorageOutStatus() {
		return storageOutStatus;
	}

	public void setStorageOutStatus(String storageOutStatus) {
		this.storageOutStatus = storageOutStatus;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

}
