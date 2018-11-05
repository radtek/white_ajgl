package com.taiji.pubsec.ajqlc.message.action.bean;

/**
 * 接处警信息Bean
 */
public class AlertMessageBean{

	private String id;	// id
	
	private String content;	// 消息内容
	
	private String createdTime;	//创建时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	
}