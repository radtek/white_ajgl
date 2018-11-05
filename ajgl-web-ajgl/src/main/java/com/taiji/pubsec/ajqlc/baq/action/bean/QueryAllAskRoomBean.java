package com.taiji.pubsec.ajqlc.baq.action.bean;

import java.util.List;

/**
 * 查询所有问询室(包含所有状态)Bean
 * 
 * @author sunjd
 *
 */
public class QueryAllAskRoomBean {
	
	private String unitName;	//单位名称
	private List<ActivityRoomBean> activityRoomBean;	//问询室
	
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public List<ActivityRoomBean> getActivityRoomBean() {
		return activityRoomBean;
	}
	public void setActivityRoomBean(List<ActivityRoomBean> activityRoomBean) {
		this.activityRoomBean = activityRoomBean;
	}
	
	
}
