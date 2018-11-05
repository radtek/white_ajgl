package com.taiji.pubsec.ajqlc.util.bean;

import java.util.List;

/**
 * 预警规则Bean
 * @author sunjd
 *
 */
public class AlertRuleBean {

	private String id;
	
	private String alertTimeAt;// 定点预警时间
	
	private Integer alertTimeBefore;	// 提前预警时间
	
	private String code;	// 预警规则编码
	
	private String name;	// 预警规则名称
	
	private String description;	// 预警条件描述
	
	private Integer popWindowDuaring;	// 弹窗保持时间
	
	private String status;// 状态
	
	private String statusName;// 状态名称
	
	private String way;	// 预警方式，字典项id
	
	private String trigger;	// 触发方式，字典项id
	
	private String createdTimeStr;  //创建时间
	
	private String updateTimeStr;  //更新时间
	
	private String theirModulu;	//所属模块
	
	private String alertUsersStr;	//要预警的用户id，多个用逗号隔开
	
	private String alertRolesStr;	//要预警的角色id，多个用逗号隔开
	
	private  List<List<NameAndIdBean>> alertUsers;
	
	private  List<List<NameAndIdBean>> alertRoles;
	
	private List<List<String>> tzyjData;  //退侦的预警时间
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAlertTimeAt() {
		return alertTimeAt;
	}

	public void setAlertTimeAt(String alertTimeAt) {
		this.alertTimeAt = alertTimeAt;
	}

	public Integer getAlertTimeBefore() {
		return alertTimeBefore;
	}

	public void setAlertTimeBefore(Integer alertTimeBefore) {
		this.alertTimeBefore = alertTimeBefore;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPopWindowDuaring() {
		return popWindowDuaring;
	}

	public void setPopWindowDuaring(Integer popWindowDuaring) {
		this.popWindowDuaring = popWindowDuaring;
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

	public String getWay() {
		return way;
	}

	public void setWay(String way) {
		this.way = way;
	}

	public String getCreatedTimeStr() {
		return createdTimeStr;
	}

	public void setCreatedTimeStr(String createdTimeStr) {
		this.createdTimeStr = createdTimeStr;
	}

	public String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTrigger() {
		return trigger;
	}

	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}

	public String getTheirModulu() {
		return theirModulu;
	}

	public void setTheirModulu(String theirModulu) {
		this.theirModulu = theirModulu;
	}

	public List<List<NameAndIdBean>> getAlertUsers() {
		return alertUsers;
	}

	public void setAlertUsers(List<List<NameAndIdBean>> alertUsers) {
		this.alertUsers = alertUsers;
	}

	public List<List<NameAndIdBean>> getAlertRoles() {
		return alertRoles;
	}

	public void setAlertRoles(List<List<NameAndIdBean>> alertRoles) {
		this.alertRoles = alertRoles;
	}

	public String getAlertUsersStr() {
		return alertUsersStr;
	}

	public void setAlertUsersStr(String alertUsersStr) {
		this.alertUsersStr = alertUsersStr;
	}

	public String getAlertRolesStr() {
		return alertRolesStr;
	}

	public void setAlertRolesStr(String alertRolesStr) {
		this.alertRolesStr = alertRolesStr;
	}

	public List<List<String>> getTzyjData() {
		return tzyjData;
	}

	public void setTzyjData(List<List<String>> tzyjData) {
		this.tzyjData = tzyjData;
	}


}
