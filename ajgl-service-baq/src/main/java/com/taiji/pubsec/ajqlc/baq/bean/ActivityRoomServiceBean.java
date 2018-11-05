package com.taiji.pubsec.ajqlc.baq.bean;

import java.util.Date;

public class ActivityRoomServiceBean {
	
	private String id;	// id
	
	private String monitorNum;	// 监控视频号
	
	private String name;	// 名称
	
	private String type;	// 类型
	
	private Date updatedTime;	//最新修改时间
	
	private Date createdTime;	//最新修改时间
	
	private String dahuaRoomId;	//大华系统的房间id

	private String unitName;	// 单位名称
	
	private String note;	// 备注
	
	private String code;	// 编码
	
	private String status;	// 状态，0:空闲；1:使用中；2:不可用
	
	private int number;	//房间在用人数

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMonitorNum() {
		return monitorNum;
	}

	public void setMonitorNum(String monitorNum) {
		this.monitorNum = monitorNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getDahuaRoomId() {
		return dahuaRoomId;
	}

	public void setDahuaRoomId(String dahuaRoomId) {
		this.dahuaRoomId = dahuaRoomId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	
}
