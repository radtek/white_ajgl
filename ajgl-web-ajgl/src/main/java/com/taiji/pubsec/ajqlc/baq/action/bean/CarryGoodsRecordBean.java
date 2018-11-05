package com.taiji.pubsec.ajqlc.baq.action.bean;

import com.taiji.pubsec.ajqlc.util.Constant;

/**
 * 随身物品记录Bean
 * @author sunjd
 *
 */
public class CarryGoodsRecordBean{
	
	private String id;

	private String goodsName;	// 物品名称
	
	private String features;	// 特征

	private String quantity;	// 数量
	
	private String unitOfMeasurement;	// 计量单位
	
	private String status;	// 状态：在库、离库 编码
	
	private String statusStr;	// 状态：在库、离库 字符串
	
	private String num;	// 编号
	
	private String strongboxNum;		//储物柜 Id
	
	private String strongboxNumStr;		//储物柜字符串
	
	private String strongboxName;		//储物柜名称
	
	private String position;        //存储位置字符串
	
	private String remark;		//物品返还备注
	
	private String operateFlag;		//是否可以编辑位置
	
	private String photoId;    //照片附件
	
	private String photoBase64;    //照片字符串
	
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public String getUnitOfMeasurement() {
		return unitOfMeasurement;
	}

	public void setUnitOfMeasurement(String unitOfMeasurement) {
		this.unitOfMeasurement = unitOfMeasurement;
	}

	public String getStrongboxNum() {
		return strongboxNum;
	}

	public void setStrongboxNum(String strongboxNum) {
		this.strongboxNum = strongboxNum;
	}

	public String getStrongboxNumStr() {
		return strongboxNumStr;
	}

	public void setStrongboxNumStr(String strongboxNumStr) {
		this.strongboxNumStr = strongboxNumStr;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getOperateFlag() {
		return operateFlag;
	}

	public void setOperateFlag(String operateFlag) {
		this.operateFlag = operateFlag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public String getPhotoBase64() {
		return photoBase64;
	}

	public void setPhotoBase64(String photoBase64) {
		this.photoBase64 = photoBase64;
	}

	public String getStrongboxName() {
		return strongboxName;
	}

	public void setStrongboxName(String strongboxName) {
		this.strongboxName = strongboxName;
	}
}
