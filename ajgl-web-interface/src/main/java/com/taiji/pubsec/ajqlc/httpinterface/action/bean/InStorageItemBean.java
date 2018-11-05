package com.taiji.pubsec.ajqlc.httpinterface.action.bean;

/**
 * 手机端入库单项Bean
 * 
 * @author WangLei
 *
 */
public class InStorageItemBean {

	private String itemId;// 单项id

	private String storageId;// 保存位置id

	private String lockerCode;// 储物架编码

	private double incomingNumber;// 入库数量

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

	public double getIncomingNumber() {
		return incomingNumber;
	}

	public void setIncomingNumber(double incomingNumber) {
		this.incomingNumber = incomingNumber;
	}

}
