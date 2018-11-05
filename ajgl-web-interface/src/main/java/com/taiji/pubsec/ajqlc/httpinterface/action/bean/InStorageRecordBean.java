package com.taiji.pubsec.ajqlc.httpinterface.action.bean;

/**
 * 入库记录bean
 * @author wangfx
 *
 */
public class InStorageRecordBean {
	
	private String id;//入库操作id

	private double incomingNumber;// 入库数量
	
	private String lockerCode;// 储物柜编码
	
	private String lockerName;// 储物柜名称
	
	private String lockerStatus ; //是否保险箱
	
	
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

	public double getIncomingNumber() {
		return incomingNumber;
	}

	public void setIncomingNumber(double incomingNumber) {
		this.incomingNumber = incomingNumber;
	}

	public String getLockerStatus() {
		return lockerStatus;
	}

	public void setLockerStatus(String lockerStatus) {
		this.lockerStatus = lockerStatus;
	}
	
}
