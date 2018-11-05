package com.taiji.pubsec.ajqlc.dp.bean;


public class ActivityRecordBean {
	
	private String id;	// id
	
	private String handlingAreaReceiptNum;	//办案区使用单编码	
	
	private String description;	//活动描述中文说明，大阶段固定
	
	private Long startTime;	//开始时间
	
	private Long endTime;	//结束时间
	
	private String gridId;	//网格id
	
	private String gridType;	//网格类型
		
	private String gridName;	//网格名称
	
	private String passageId;	//通道id，调看视频用
	
	private String supRecordId;	//上级记录id

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHandlingAreaReceiptNum() {
		return handlingAreaReceiptNum;
	}

	public void setHandlingAreaReceiptNum(String handlingAreaReceiptNum) {
		this.handlingAreaReceiptNum = handlingAreaReceiptNum;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public String getGridId() {
		return gridId;
	}

	public void setGridId(String gridId) {
		this.gridId = gridId;
	}

	public String getGridType() {
		return gridType;
	}

	public void setGridType(String gridType) {
		this.gridType = gridType;
	}

	public String getGridName() {
		return gridName;
	}

	public void setGridName(String gridName) {
		this.gridName = gridName;
	}

	public String getPassageId() {
		return passageId;
	}

	public void setPassageId(String passageId) {
		this.passageId = passageId;
	}

	public String getSupRecordId() {
		return supRecordId;
	}

	public void setSupRecordId(String supRecordId) {
		this.supRecordId = supRecordId;
	}
	
}
