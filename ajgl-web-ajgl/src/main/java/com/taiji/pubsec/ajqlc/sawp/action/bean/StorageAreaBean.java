package com.taiji.pubsec.ajqlc.sawp.action.bean;

/**
 * 物证管理区Bean
 * 
 * @author WangLei
 *
 */
public class StorageAreaBean {

	private String id;

	private String address;// 详细位置

	private String code;// 编码

	private String name;// 名称

	private String remark;// 备注

	private String state;// 状态，字典项id
	
	private String stateName;// 状态，字典项名称

	private String unitId;// 单位id

	private String unitName;// 单位名称

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

}
