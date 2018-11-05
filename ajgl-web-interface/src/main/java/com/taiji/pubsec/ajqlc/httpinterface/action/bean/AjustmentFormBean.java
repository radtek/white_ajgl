package com.taiji.pubsec.ajqlc.httpinterface.action.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 调整单bean
 * 
 * @author chengkai
 */
public class AjustmentFormBean {

	private String formCode; // 单号

	private String reason; // 调整原因

	private Long operateTime; // 操作时间

	private List<AjustmentFormItemBean> items = new ArrayList<AjustmentFormItemBean>(); // 调整单项list
	
	private List<Map<String,String>> lockers = new ArrayList<Map<String,String>>();// 储物架和案件编号关系集合

	public AjustmentFormBean() {

	}

	public String getFormCode() {
		return formCode;
	}

	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Long getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Long operateTime) {
		this.operateTime = operateTime;
	}

	public List<AjustmentFormItemBean> getItems() {
		return items;
	}

	public void setItems(List<AjustmentFormItemBean> items) {
		this.items = items;
	}

	public List<Map<String, String>> getLockers() {
		return lockers;
	}

	public void setLockers(List<Map<String, String>> lockers) {
		this.lockers = lockers;
	}

}