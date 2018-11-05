package com.taiji.pubsec.ajqlc.baq.action.bean;

import org.apache.commons.lang3.StringUtils;


/**
 * 问询室分配记录查询Bean
 * @author sunjd
 *
 */
public class AskRoomAllocationRecordQueryBean {
	private String activityRoomName;	//询问室名称
	private String handlingAreaReceiptNum;	//使用单编号
	private String harId;	//使用单编号
	private String enterAreaReasons;	//进入办案区原由
	private String byQuestioningPeopleName;	//被问讯人姓名
	private String byQuestioningPeopleIdentifyNum;	//被问讯人身份证件号码
	private String startAllocationTime;	//分配时间开始
	private String endAllocationTime;	//分配时间结束
	private String leaveTime;	//最终离开问询室时间
	private String allocationPeople; //分配人
	private String handlingPolice;	//办案民警
	private String unitId;	//所属单位ID
	private String unitName;	//所属单位名称
	private String orderCondition;
	public String getActivityRoomName() {
		return StringUtils.isBlank(activityRoomName)?"":activityRoomName  ;
	}
	public void setActivityRoomName(String activityRoomName) {
		this.activityRoomName = activityRoomName;
	}
	public String getHandlingAreaReceiptNum() {
		return StringUtils.isBlank(handlingAreaReceiptNum)?"":handlingAreaReceiptNum;
	}
	public void setHandlingAreaReceiptNum(String handlingAreaReceiptNum) {
		this.handlingAreaReceiptNum = handlingAreaReceiptNum;
	}
	
	public String getByQuestioningPeopleName() {
		return StringUtils.isBlank(byQuestioningPeopleName)?"":byQuestioningPeopleName;
	}
	public void setByQuestioningPeopleName(String byQuestioningPeopleName) {
		this.byQuestioningPeopleName = byQuestioningPeopleName;
	}
	public String getByQuestioningPeopleIdentifyNum() {
		return StringUtils.isBlank(byQuestioningPeopleIdentifyNum)?"":byQuestioningPeopleIdentifyNum;
	}
	public void setByQuestioningPeopleIdentifyNum(
			String byQuestioningPeopleIdentifyNum) {
		this.byQuestioningPeopleIdentifyNum = byQuestioningPeopleIdentifyNum;
	}
	public String getStartAllocationTime() {
		return StringUtils.isBlank(startAllocationTime)?"":startAllocationTime;
	}
	public void setStartAllocationTime(String startAllocationTime) {
		this.startAllocationTime = startAllocationTime;
	}
	public String getEndAllocationTime() {
		return StringUtils.isBlank(endAllocationTime)?"":endAllocationTime;
	}
	public void setEndAllocationTime(String endAllocationTime) {
		this.endAllocationTime = endAllocationTime;
	}
	public String getAllocationPeople() {
		return StringUtils.isBlank(allocationPeople)?"":allocationPeople;
	}
	public void setAllocationPeople(String allocationPeople) {
		this.allocationPeople = allocationPeople;
	}
	public String getHandlingPolice() {
		return StringUtils.isBlank(handlingPolice)?"":handlingPolice;
	}
	public void setHandlingPolice(String handlingPolice) {
		this.handlingPolice = handlingPolice;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getUnitName() {
		return StringUtils.isBlank(unitName)?"":unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getHarId() {
		return harId;
	}
	public void setHarId(String harId) {
		this.harId = harId;
	}
	public String getLeaveTime() {
		return leaveTime;
	}
	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime;
	}
	public String getOrderCondition() {
		return orderCondition;
	}
	public void setOrderCondition(String orderCondition) {
		this.orderCondition = orderCondition;
	}
	public String getEnterAreaReasons() {
		return enterAreaReasons;
	}
	public void setEnterAreaReasons(String enterAreaReasons) {
		this.enterAreaReasons = enterAreaReasons;
	}
	
}
