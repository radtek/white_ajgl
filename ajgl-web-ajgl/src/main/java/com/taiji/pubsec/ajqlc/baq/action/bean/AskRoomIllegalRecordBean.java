package com.taiji.pubsec.ajqlc.baq.action.bean;



/**
 * 询问室违规记录Bean
 * @author XIEHF
 *
 */
public class AskRoomIllegalRecordBean {
	
	private String id;
	private String commitPeople;	// 提交人
	private String illegalCause;	// 违规原因
	private String illegalCauseStr;	// 违规原因
	private String illegalTime;      //违规时间
	private String videoStartTime;      //视频开始时间
	private String videoEndTime;      //视频结束时间
	private String bz;               //备注
	private String handlingAreaReceiptNum; //使用单编号
	private AskRoomAllocationBean askRoomAllocationBean;	// 问询室分配记录
	private ActivityRoomBean activityRoomBean;	// 问询室
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCommitPeople() {
		return commitPeople;
	}
	public void setCommitPeople(String commitPeople) {
		this.commitPeople = commitPeople;
	}
	public String getIllegalCause() {
		return illegalCause;
	}
	public void setIllegalCause(String illegalCause) {
		this.illegalCause = illegalCause;
	}
	public String getIllegalTime() {
		return illegalTime;
	}
	public void setIllegalTime(String illegalTime) {
		this.illegalTime = illegalTime;
	}
	public String getVideoStartTime() {
		return videoStartTime;
	}
	public void setVideoStartTime(String videoStartTime) {
		this.videoStartTime = videoStartTime;
	}
	public String getVideoEndTime() {
		return videoEndTime;
	}
	public void setVideoEndTime(String videoEndTime) {
		this.videoEndTime = videoEndTime;
	}
	public AskRoomAllocationBean getAskRoomAllocationBean() {
		return askRoomAllocationBean;
	}
	public void setAskRoomAllocationBean(AskRoomAllocationBean askRoomAllocationBean) {
		this.askRoomAllocationBean = askRoomAllocationBean;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getHandlingAreaReceiptNum() {
		return handlingAreaReceiptNum;
	}
	public void setHandlingAreaReceiptNum(String handlingAreaReceiptNum) {
		this.handlingAreaReceiptNum = handlingAreaReceiptNum;
	}
	public String getIllegalCauseStr() {
		return illegalCauseStr;
	}
	public void setIllegalCauseStr(String illegalCauseStr) {
		this.illegalCauseStr = illegalCauseStr;
	}
	public ActivityRoomBean getActivityRoomBean() {
		return activityRoomBean;
	}
	public void setActivityRoomBean(ActivityRoomBean activityRoomBean) {
		this.activityRoomBean = activityRoomBean;
	}
	
	
}
