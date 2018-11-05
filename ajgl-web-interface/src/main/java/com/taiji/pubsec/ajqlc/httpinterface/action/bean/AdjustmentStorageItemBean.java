package com.taiji.pubsec.ajqlc.httpinterface.action.bean;

/**
 * 手机端调整单项Bean 提交用
 * 
 * @author WangLei
 *
 */
public class AdjustmentStorageItemBean {

	private String itemId;// 单项id

	private String storageId;// 保存位置id

	private String lockerCode;// 储物架编码

	private double adjustNumber;// 调整数量

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getStorageId() {
		return storageId;
	}

	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}

	public String getLockerCode() {
		return lockerCode;
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
	
}
