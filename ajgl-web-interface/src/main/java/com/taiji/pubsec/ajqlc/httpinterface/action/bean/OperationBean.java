package com.taiji.pubsec.ajqlc.httpinterface.action.bean;

/**
 * 入库操作
 * @author wangfx
 *
 */
public class OperationBean {
	
	/**
	 * 货架编码
	 */
	private String lockerCode;
	
	/**
	 * 存储区域名称
	 */
	private String area;
	
	/**
	 * 单项id
	 */
	private int itemId;
	
	/**
	 * 物品编码
	 */
	private String articleCode;
	
	/**
	 * 货架位置名称
	 */
	private String locker;
	
	/**
	 * 执行操作时间
	 */
	private String updatedTime;
	
	/***
	 * 执行操作（入库，出库，返还）数量
	 */
	private int count;

	public String getLockerCode() {
		return lockerCode;
	}

	public String getArea() {
		return area;
	}

	public int getItemId() {
		return itemId;
	}

	public String getArticleCode() {
		return articleCode;
	}

	public String getLocker() {
		return locker;
	}

	public String getUpdatedTime() {
		return updatedTime;
	}

	public int getCount() {
		return count;
	}

	public void setLockerCode(String lockerCode) {
		this.lockerCode = lockerCode;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	public void setLocker(String locker) {
		this.locker = locker;
	}

	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}

	public void setCount(int count) {
		this.count = count;
	}
	

}
