package com.taiji.pubsec.ajqlc.baq.action.bean;

import com.taiji.pubsec.ajqlc.baq.model.ActivityRoom;

/**
 * 最终离开信息
 * @author huangda
 *
 */
public class FinallyLeaveInfoBean {
	
	private String id;
	
	private String finallyLeaveCause;	//最终离开原因
	
	private Long finallyLeaveTime;	//最终离开时间
	
	private ActivityRoom askRoom;	//最终离开前使用的问询室
	
	private PersonBean modifyPeopleBean;	//最新修改人
	
	private Long updatedTime;	//最新修改时间
	
	private String handlingAreaReceiptId;	//办案区使用单

	private boolean changePermissions; //修改权限
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFinallyLeaveCause() {
		return finallyLeaveCause;
	}

	public void setFinallyLeaveCause(String finallyLeaveCause) {
		this.finallyLeaveCause = finallyLeaveCause;
	}

	public Long getFinallyLeaveTime() {
		return finallyLeaveTime;
	}

	public void setFinallyLeaveTime(Long finallyLeaveTime) {
		this.finallyLeaveTime = finallyLeaveTime;
	}

	public ActivityRoom getAskRoom() {
		return askRoom;
	}

	public void setAskRoom(ActivityRoom askRoom) {
		this.askRoom = askRoom;
	}

	public PersonBean getModifyPeopleBean() {
		return modifyPeopleBean;
	}

	public void setModifyPeopleBean(PersonBean modifyPeopleBean) {
		this.modifyPeopleBean = modifyPeopleBean;
	}

	public Long getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Long updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getHandlingAreaReceiptId() {
		return handlingAreaReceiptId;
	}

	public void setHandlingAreaReceiptId(String handlingAreaReceiptId) {
		this.handlingAreaReceiptId = handlingAreaReceiptId;
	}

	public boolean isChangePermissions() {
		return changePermissions;
	}

	public void setChangePermissions(boolean changePermissions) {
		this.changePermissions = changePermissions;
	}
	
	
}
