package com.taiji.pubsec.ajqlc.baq.bean;


/**
 * 临时取出物品Bean
 * @author 
 */
public class TemporaryRemovalBean{

	private String name;	// 物品名称
	
	private String policeName;	// 取出民警名称

	private Long removalTime;	// 	取出时间

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPoliceName() {
		return policeName;
	}

	public void setPoliceName(String policeName) {
		this.policeName = policeName;
	}

	public Long getRemovalTime() {
		return removalTime;
	}

	public void setRemovalTime(Long removalTime) {
		this.removalTime = removalTime;
	}
	
}