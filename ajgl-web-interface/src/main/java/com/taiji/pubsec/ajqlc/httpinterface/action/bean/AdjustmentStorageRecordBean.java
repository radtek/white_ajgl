package com.taiji.pubsec.ajqlc.httpinterface.action.bean;

/**
 * 调整单操作记录
 * @author wangfx
 *
 */
public class AdjustmentStorageRecordBean {
	
	private String id;
	
	private double adjustNumber;
	
	private String lockerName;
	
	private String lockerCode;
	
	private String lockerStatus; //储物架状态

	public String getId() {
		return id;
	}

	public String getLockerName() {
		return lockerName;
	}

	public String getLockerCode() {
		return lockerCode;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLockerName(String lockerName) {
		this.lockerName = lockerName;
	}

	public void setLockerCode(String lockerCode) {
		this.lockerCode = lockerCode;
	}

	public double getAdjustNumber() {
		return adjustNumber;
	}

	public void setAdjustNumber(double adjustNumber) {
		this.adjustNumber = adjustNumber;
	}

	public String getLockerStatus() {
		return lockerStatus;
	}

	public void setLockerStatus(String lockerStatus) {
		this.lockerStatus = lockerStatus;
	}

}
