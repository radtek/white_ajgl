package com.taiji.pubsec.ajqlc.baq.action.bean;

/**
 * 储物柜Bean
 * 
 * @author WL-PC
 *
 */
public class LockerBean {

	private String id;// 储物柜id

	private String unit;// 单位

	private String unitName;// 单位名称

	private String note;// 备注

	private String code;// 编号
	
	private String name;// 名称

	//private String position;// 位置
	
	private  String areaCode; //区号
	
	private String lockerCode; //柜号
	
	private String status;// 状态

	private String statusName;// 状态名称
	
	private boolean useRecord;// 是否有使用记录

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

//	public String getPosition() {
//		return position;
//	}
//
//	public void setPosition(String position) {
//		this.position = position;
//	}

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

	public boolean isUseRecord() {
		return useRecord;
	}

	public void setUseRecord(boolean useRecord) {
		this.useRecord = useRecord;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getLockerCode() {
		return lockerCode;
	}

	public void setLockerCode(String lockerCode) {
		this.lockerCode = lockerCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
