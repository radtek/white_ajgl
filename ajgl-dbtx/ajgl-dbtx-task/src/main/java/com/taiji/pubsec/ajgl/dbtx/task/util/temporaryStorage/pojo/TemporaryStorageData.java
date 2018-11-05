package com.taiji.pubsec.ajgl.dbtx.task.util.temporaryStorage.pojo;

import java.util.List;

import com.taiji.pubsec.ajgl.dbtx.task.util.BusinessData;

public class TemporaryStorageData extends BusinessData {
	
	/**
	 * 暂存物品保管入库单创建时间
	 */
	private Long temporaryStorageInCreatingTime;

	/**
	 * 接收对象
	 */
	private List<String> receiveIdList;
	
	/**
	 * 接收对象
	 */
	private String receiveType;
	/**
	 * 提前预警时间（分钟）
	 */
	private Integer aheadTime;
	/**
	 * 弹窗时间（秒）
	 */
	private Integer showTime;
	/**
	 * 使用单Id
	 */
	private String businessId;
	/**
	 * 包类名
	 */
	private String businessType;
	
	/**
	 * 预警方式
	 */
	private String way;
	
	private String storageInCode;	//暂存入库单编号

	public Long getTemporaryStorageInCreatingTime() {
		return temporaryStorageInCreatingTime;
	}

	public void setTemporaryStorageInCreatingTime(Long temporaryStorageInCreatingTime) {
		this.temporaryStorageInCreatingTime = temporaryStorageInCreatingTime;
	}

	public List<String> getReceiveIdList() {
		return receiveIdList;
	}

	public void setReceiveIdList(List<String> receiveIdList) {
		this.receiveIdList = receiveIdList;
	}

	public String getReceiveType() {
		return receiveType;
	}

	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}

	public Integer getAheadTime() {
		return aheadTime;
	}

	public void setAheadTime(Integer aheadTime) {
		this.aheadTime = aheadTime;
	}

	public Integer getShowTime() {
		return showTime;
	}

	public void setShowTime(Integer showTime) {
		this.showTime = showTime;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getWay() {
		return way;
	}

	public void setWay(String way) {
		this.way = way;
	}

	public String getStorageInCode() {
		return storageInCode;
	}

	public void setStorageInCode(String storageInCode) {
		this.storageInCode = storageInCode;
	}
	
}
