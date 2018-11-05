package com.taiji.pubsec.ajqlc.baq.action.bean;

/**
 * 随身物品返还记录Bean
 * @author sunjd
 *
 */
public class CarryGoodsReturnRecordBean {
	
	private String id;
	
	private String noReturnReason;	//未返还原因
	
	private String operater;	//操作人
	
	private String operateTime; 	//操作时间
	
	private String receiver;	//领取人

	private String receiverIdCard;	//领取人证件号码
	
	private String receiveTime;	//接收时间
	
	private String returnStatus;	//返还状态 0：返还；1：未返还
	
	private String returnStatusStr;	//返还状态 0：返还；1：未返还  字符串
	
	private CarryGoodsRecordBean arryGoodsRecordBean;	//随身物品入库记录Bean

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNoReturnReason() {
		return noReturnReason;
	}

	public void setNoReturnReason(String noReturnReason) {
		this.noReturnReason = noReturnReason;
	}

	public String getOperater() {
		return operater;
	}

	public void setOperater(String operater) {
		this.operater = operater;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getReceiverIdCard() {
		return receiverIdCard;
	}

	public void setReceiverIdCard(String receiverIdCard) {
		this.receiverIdCard = receiverIdCard;
	}

	public String getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}

	public String getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}

	public String getReturnStatusStr() {
		return returnStatusStr;
	}

	public void setReturnStatusStr(String returnStatusStr) {
		this.returnStatusStr = returnStatusStr;
	}

	public CarryGoodsRecordBean getArryGoodsRecordBean() {
		return arryGoodsRecordBean;
	}

	public void setArryGoodsRecordBean(CarryGoodsRecordBean arryGoodsRecordBean) {
		this.arryGoodsRecordBean = arryGoodsRecordBean;
	}
		
	
}
