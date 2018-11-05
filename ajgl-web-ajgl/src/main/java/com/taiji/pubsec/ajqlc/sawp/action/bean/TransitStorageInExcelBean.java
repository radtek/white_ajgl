package com.taiji.pubsec.ajqlc.sawp.action.bean;
/**
 *  暂存入库单导出excel的Bean
 * @author 
 *
 */
public class TransitStorageInExcelBean {
	
  public String count;
	
  public String storageInCode;  //入库单编码
  
  public String storageInDateTime; //入库时间
  
  public String caseCode; //案件编号
  
  public String caseName; //案件名称
  
  public String solvePolice; //办案民警
  
  public String objectOwnerPerson;//物品持有人
  
  public String remark; //备注

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

public String getCount() {
	return count;
}

public void setCount(String count) {
	this.count = count;
}
}
