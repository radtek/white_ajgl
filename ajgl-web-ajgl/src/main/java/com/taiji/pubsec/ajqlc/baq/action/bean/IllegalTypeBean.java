package com.taiji.pubsec.ajqlc.baq.action.bean;

/**
 * 违规类型Bean
 * 
 * @author XIEHF
 *
 */
public class IllegalTypeBean {
	private String id;
	private String name;
	private String code;
	private String status;
	private String statusName;     // 状态名称
	private String description;
	private String isSystemData;  //是否系统预设字段。
	private String isSystemDataStr;  //是否系统预设字段。
	private String updatedTimeStr;
	private String operator;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



	public String getUpdatedTimeStr() {
		return updatedTimeStr;
	}

	public void setUpdatedTimeStr(String updatedTimeStr) {
		this.updatedTimeStr = updatedTimeStr;
	}

	public String getIsSystemData() {
		return isSystemData;
	}

	public void setIsSystemData(String isSystemData) {
		this.isSystemData = isSystemData;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getIsSystemDataStr() {
		return isSystemDataStr;
	}

	public void setIsSystemDataStr(String isSystemDataStr) {
		this.isSystemDataStr = isSystemDataStr;
	}
	

}
