package com.taiji.pubsec.ajgl.dbtx.task.util.temporaryStorage.pojo;

import com.taiji.pubsec.common.tool.mission.task.custom.pojo.JSONPojo;

public class TemporaryStorageRule implements JSONPojo {
	
	/**
	 * 执行时间
	 */
	private Long executingTime;
	
	private String context;
	
	/**
	 * 接收对象账户id
	 */
	private String receiveAccountId;
	
	private String storageInCode;	//暂存入库单编号
	
	private boolean alertForSys = false;
	
	private boolean alertForPDA = false;

	private boolean alertForText = false;

	public Long getExecutingTime() {
		return executingTime;
	}

	public void setExecutingTime(Long executingTime) {
		this.executingTime = executingTime;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getStorageInCode() {
		return storageInCode;
	}

	public void setStorageInCode(String storageInCode) {
		this.storageInCode = storageInCode;
	}

	public boolean isAlertForSys() {
		return alertForSys;
	}

	public void setAlertForSys(boolean alertForSys) {
		this.alertForSys = alertForSys;
	}

	public boolean isAlertForPDA() {
		return alertForPDA;
	}

	public void setAlertForPDA(boolean alertForPDA) {
		this.alertForPDA = alertForPDA;
	}

	public boolean isAlertForText() {
		return alertForText;
	}

	public void setAlertForText(boolean alertForText) {
		this.alertForText = alertForText;
	}

	public String getReceiveAccountId() {
		return receiveAccountId;
	}

	public void setReceiveAccountId(String receiveAccountId) {
		this.receiveAccountId = receiveAccountId;
	}

}
