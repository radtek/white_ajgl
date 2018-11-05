package com.taiji.pubsec.ajqlc.sawp.bean;

import java.util.List;

public class SuspectPersonBean {
	
	private String suspectName;	//嫌疑人姓名
	
	private String suspectId;	//嫌疑人id 
	
	private List<DocBean> docs;	//对应文书

	public String getSuspectName() {
		return suspectName;
	}

	public void setSuspectName(String suspectName) {
		this.suspectName = suspectName;
	}

	public String getSuspectId() {
		return suspectId;
	}

	public void setSuspectId(String suspectId) {
		this.suspectId = suspectId;
	}

	public List<DocBean> getDocs() {
		return docs;
	}

	public void setDocs(List<DocBean> docs) {
		this.docs = docs;
	}
	
	
	
}
