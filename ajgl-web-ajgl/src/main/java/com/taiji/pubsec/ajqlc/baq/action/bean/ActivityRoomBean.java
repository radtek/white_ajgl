package com.taiji.pubsec.ajqlc.baq.action.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 问（询）讯室管理Bean
 * 
 * @author WL-PC
 *
 */
public class ActivityRoomBean {

	private String id;

	private String unit;// 单位

	private String unitName;// 单位名称

	private String monitorNum;// 监控视频号

	private String name;// 名称

	private String note;// 备注

	private String code;// 编号

	private String status;// 状态

	private String statusName;// 状态名称

	private String type;// 类型

	private String typeName;// 类型名称

	private boolean useRecord;// 是否有使用记录

	private String sxId;// 当前审讯的分配记录Id
	
	private HarInfoBean harInfo = new HarInfoBean();//使用单信息
	
	private Map<String, String> xyrMap = new HashMap<String, String>();//嫌疑人列表
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
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

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public boolean isUseRecord() {
		return useRecord;
	}

	public void setUseRecord(boolean useRecord) {
		this.useRecord = useRecord;
	}

	public HarInfoBean getHarInfo() {
		return harInfo;
	}

	public void setHarInfo(HarInfoBean harInfo) {
		this.harInfo = harInfo;
	}

	public String getSxId() {
		return sxId;
	}

	public void setSxId(String sxId) {
		this.sxId = sxId;
	}

	public Map<String, String> getXyrMap() {
		return xyrMap;
	}

	public void setXyrMap(Map<String, String> xyrMap) {
		this.xyrMap = xyrMap;
	}

}
