package com.taiji.pubsec.ajqlc.sawp.bean;

/**
 * 暂存物品属性
 * 
 * @author xinfan
 *
 */
public class TempObjectBean {
	public String objectName;// 物品名称
	public String objectCharacter;// 物品特征
	public String inThisNum;// 本次入库数量
	public String measureUnit;// 计量单位
	public String objectPicture;// 物品图片
	public String objectId;//物品id
	public String objectClassName;//物品model的Class名称
	public String remark;// 备注
	public String status;//状态

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getObjectCharacter() {
		return objectCharacter;
	}

	public void setObjectCharacter(String objectCharacter) {
		this.objectCharacter = objectCharacter;
	}

	public String getInThisNum() {
		return inThisNum;
	}

	public void setInThisNum(String inThisNum) {
		this.inThisNum = inThisNum;
	}

	public String getMeasureUnit() {
		return measureUnit;
	}

	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}

	public String getObjectPicture() {
		return objectPicture;
	}

	public void setObjectPicture(String objectPicture) {
		this.objectPicture = objectPicture;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectClassName() {
		return objectClassName;
	}

	public void setObjectClassName(String objectClassName) {
		this.objectClassName = objectClassName;
	}

}
