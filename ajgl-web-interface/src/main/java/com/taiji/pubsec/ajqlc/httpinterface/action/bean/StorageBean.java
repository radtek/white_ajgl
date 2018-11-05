package com.taiji.pubsec.ajqlc.httpinterface.action.bean;

/**
 * 保管位置bean
 * @author chengkai
 */
public class StorageBean {

	private double numDesc;	//在库数量（数量+单位）
	
	private String measureUnit;	//计量单位
	
	private String areaCode;	//存储区域编码
	
	private String areaName;	//存储区域名称
	
	private String lockerCode;	//货架编码
	
	private String lockerName;	//货架名称
	
	private String lockerStatus; //储物架状态
	
	private Long inTime;	//入库时间
	
	public StorageBean(){

	}

	public String getAreaCode() {
		return areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public String getLockerCode() {
		return lockerCode;
	}

	public String getLockerName() {
		return lockerName;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public void setLockerCode(String lockerCode) {
		this.lockerCode = lockerCode;
	}

	public void setLockerName(String lockerName) {
		this.lockerName = lockerName;
	}

	public Long getInTime() {
		return inTime;
	}

	public void setInTime(Long inTime) {
		this.inTime = inTime;
	}

	public String getMeasureUnit() {
		return measureUnit;
	}

	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}

	public double getNumDesc() {
		return numDesc;
	}

	public void setNumDesc(double numDesc) {
		this.numDesc = numDesc;
	}
	
	public String getLockerStatus() {
		return lockerStatus;
	}

	public void setLockerStatus(String lockerStatus) {
		this.lockerStatus = lockerStatus;
	}
	
}