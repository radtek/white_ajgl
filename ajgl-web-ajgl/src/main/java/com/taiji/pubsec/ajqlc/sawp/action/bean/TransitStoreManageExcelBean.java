package com.taiji.pubsec.ajqlc.sawp.action.bean;

public class TransitStoreManageExcelBean {
	
	private String count;
	
	private String address;	//详细位置
	
	private String status;  //状态
	
	private String goodsList ;	//当前存放物品
	
	private int inStorageNum;	//在库总件数
	
	private String caseCode;//案件编号
	
	private String caseName;	//案件名称
	
	private String handlingPolices;	//办案民警
	
	private String suspectName; //嫌疑人姓名
	
	private String suspectIdCardNum;	//嫌疑人身份证号

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getInStorageNum() {
		return inStorageNum;
	}

	public void setInStorageNum(int inStorageNum) {
		this.inStorageNum = inStorageNum;
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

	public String getHandlingPolices() {
		return handlingPolices;
	}

	public void setHandlingPolices(String handlingPolices) {
		this.handlingPolices = handlingPolices;
	}

	public String getSuspectName() {
		return suspectName;
	}

	public void setSuspectName(String suspectName) {
		this.suspectName = suspectName;
	}

	public String getSuspectIdCardNum() {
		return suspectIdCardNum;
	}

	public void setSuspectIdCardNum(String suspectIdCardNum) {
		this.suspectIdCardNum = suspectIdCardNum;
	}

	public String getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(String goodsList) {
		this.goodsList = goodsList;
	}
}
