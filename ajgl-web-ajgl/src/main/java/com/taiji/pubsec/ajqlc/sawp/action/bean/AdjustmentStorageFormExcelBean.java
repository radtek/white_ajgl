package com.taiji.pubsec.ajqlc.sawp.action.bean;

/**
 * 涉案物品调整单ExcelBean
 * 
 * @author WangLei
 *
 */
public class AdjustmentStorageFormExcelBean {

	private String count;

	private String code; // 编码

	private String updatedTime; // 更新时间

	private String reason; // 调整原因

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
