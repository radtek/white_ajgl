package com.taiji.pubsec.ajgl.dbtx.task.util;

import com.taiji.pubsec.common.tool.mission.task.custom.pojo.JSONPojo;

public class CommonData implements JSONPojo{
	
	/**
	 * 文书Id
	 */
	private String businessId;
	/**
	 * 文书包类名
	 */
	private String businessType;
	/**
	 * 预警类型
	 */
	private String type;
	
	public CommonData(){
		
	}
	
	public CommonData(String type, String businessId, String businessType){
		this.type = type;
		this.businessId = businessId;
		this.businessType = businessType;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
}
