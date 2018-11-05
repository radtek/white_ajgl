package com.taiji.pubsec.ajqlc.sawp.bean;

/**
 * 暂存出库返还单Bean
 * @author chengkai
 */
public class TempStorageOutInfoBean {

	public String storageOutCode; // 出库单编码

	public String storageOutDateTime; // 出库时间
	
	public String caseCode; // 案件编号

	public String caseName; // 案件名称

	public String solvePolice; // 办案民警

	public String objectOwnerPerson;// 物品持有人

	public String ifAllOut;	//是否返还完成
	
	public String remark; // 备注

	public String getStorageOutCode() {
		return storageOutCode;
	}

	public void setStorageOutCode(String storageOutCode) {
		this.storageOutCode = storageOutCode;
	}

	public String getStorageOutDateTime() {
		return storageOutDateTime;
	}

	public void setStorageOutDateTime(String storageOutDateTime) {
		this.storageOutDateTime = storageOutDateTime;
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

	public String getIfAllOut() {
		return ifAllOut;
	}

	public void setIfAllOut(String ifAllOut) {
		this.ifAllOut = ifAllOut;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
