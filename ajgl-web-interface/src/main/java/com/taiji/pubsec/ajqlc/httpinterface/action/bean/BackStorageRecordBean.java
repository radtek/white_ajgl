package com.taiji.pubsec.ajqlc.httpinterface.action.bean;

public class BackStorageRecordBean {
	
	private String id;
	
	private String backStorageFormItemId;	//返还单项id
	
	private double returnedNumber;	//返还数量
	
	private String lockerCode;	//储物柜编码
	
	private String lockerName;	//储物柜名称
	
	private String lockerStatus; //储物架状态

	public String getId() {
		return id;
	}

	public String getLockerCode() {
		return lockerCode;
	}

	public String getLockerName() {
		return lockerName;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLockerCode(String lockerCode) {
		this.lockerCode = lockerCode;
	}

	public void setLockerName(String lockerName) {
		this.lockerName = lockerName;
	}


	public String getBackStorageFormItemId() {
		return backStorageFormItemId;
	}


	public void setBackStorageFormItemId(String backStorageFormItemId) {
		this.backStorageFormItemId = backStorageFormItemId;
	}

	public double getReturnedNumber() {
		return returnedNumber;
	}

	public void setReturnedNumber(double returnedNumber) {
		this.returnedNumber = returnedNumber;
	}

	public String getLockerStatus() {
		return lockerStatus;
	}

	public void setLockerStatus(String lockerStatus) {
		this.lockerStatus = lockerStatus;
	}
}
