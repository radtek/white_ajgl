package com.taiji.pubsec.ajqlc.baq.action.bean;

import com.taiji.pubsec.ajqlc.baq.model.ActivityRoom;
/**
 * 临时离开信息
 * @author huangda
 *
 */
public class TmpLeaveInfoBean {
	
	private String id;
	
	private String handleCauseDepartmentLeader;	//办案部门负责人
	
	private String leaveCause;	//离开原因
	
	private Long leaveTime;	//离开时间
	
	private ActivityRoom askRoom; //离开前使用的问询室
	
	private String maintainer;	//维护人
	
	private Long maintainTime;	//维护时间
	
	private Long returnTime;	//返回时间

	private boolean changePermissions; //修改权限
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHandleCauseDepartmentLeader() {
		return handleCauseDepartmentLeader;
	}

	public void setHandleCauseDepartmentLeader(String handleCauseDepartmentLeader) {
		this.handleCauseDepartmentLeader = handleCauseDepartmentLeader;
	}

	public String getLeaveCause() {
		return leaveCause;
	}

	public void setLeaveCause(String leaveCause) {
		this.leaveCause = leaveCause;
	}

	public Long getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(Long leaveTime) {
		this.leaveTime = leaveTime;
	}

	public ActivityRoom getAskRoom() {
		return askRoom;
	}

	public void setAskRoom(ActivityRoom askRoom) {
		this.askRoom = askRoom;
	}

	public String getMaintainer() {
		return maintainer;
	}

	public void setMaintainer(String maintainer) {
		this.maintainer = maintainer;
	}

	public Long getMaintainTime() {
		return maintainTime;
	}

	public void setMaintainTime(Long maintainTime) {
		this.maintainTime = maintainTime;
	}

	public Long getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Long returnTime) {
		this.returnTime = returnTime;
	}

	public boolean isChangePermissions() {
		return changePermissions;
	}

	public void setChangePermissions(boolean changePermissions) {
		this.changePermissions = changePermissions;
	}
	
}
