package com.taiji.pubsec.ajqlc.yykl.action.bean;

/**
 * 刻录记录bean
 * @author 
 */
public class BurnRecordBean{
	
	private String 	recordPerson;
	
	private  Long  recordTime;
	
	private  String recordContent;

	public String getRecordPerson() {
		return recordPerson;
	}

	public void setRecordPerson(String recordPerson) {
		this.recordPerson = recordPerson;
	}

	public Long getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Long recordTime) {
		this.recordTime = recordTime;
	}

	public String getRecordContent() {
		return recordContent;
	}

	public void setRecordContent(String recordContent) {
		this.recordContent = recordContent;
	}
	
}